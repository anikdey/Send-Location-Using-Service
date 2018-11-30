package javarank.com.send_location_using_service.webservice;

import android.text.TextUtils;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;


import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javarank.com.send_location_using_service.events.BaseErrorEvent;
import javarank.com.send_location_using_service.events.BaseSuccessEvent;
import javarank.com.send_location_using_service.util.HttpErrorHelper;
import javarank.com.send_location_using_service.util.RequestMethod;


public class WebServiceStringRequestManager<P extends BaseSuccessEvent, Q extends BaseErrorEvent> implements IWebRequestManager {
    public static final String TAG = WebServiceStringRequestManager.class.getSimpleName();

    public static final int REQUEST_CODE_NOTE = -1;
    private RequestMethod method;

    Response.Listener<String> jsonResponseListener;
    Response.ErrorListener errorListener;

    EventBus eventBus = EventBus.getDefault();

    private HashMap<String, String> urlParams;
    private HashMap<String, String> params;
    private HashMap<String, String> headers;


    private String requestBody;
    private String baseUrl;

    private Class<P> pClass;
    private Class<Q> qClass;

    private int requestCode = REQUEST_CODE_NOTE;
    private RequestQueue requestQueue;
    private String tag;

    public WebServiceStringRequestManager(RequestQueue requestQueue, String baseUrl, Class<P> pClass, Class<Q> qClass) {

        urlParams = new HashMap<>();
        params = new HashMap<>();
        headers = new HashMap<>();

        this.baseUrl = baseUrl;
        this.requestQueue = requestQueue;
        this.pClass = pClass;
        this.qClass = qClass;

        setListeners();
    }

    public WebServiceStringRequestManager(RequestMethod method, RequestQueue requestQueue, String baseUrl, Class<P> pClass, Class<Q> qClass) {

        urlParams = new HashMap<>();
        params = new HashMap<>();
        headers = new HashMap<>();

        this.method = method;
        this.requestQueue = requestQueue;
        this.baseUrl = baseUrl;
        this.pClass = pClass;
        this.qClass = qClass;

        setListeners();

    }
    private void setListeners() {
        jsonResponseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    sendLog(response.toString());
                    P res = pClass.newInstance();
                    res.setResponse(response.toString());
                    res.setRequestCode(requestCode);
                    Log.d(TAG, "Response " + response);

                    eventBus.post(res);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                String errorMessage = HttpErrorHelper.processErrorMessage(error);
                sendLog(errorMessage);
                try {
                    Q err = qClass.newInstance();
                    err.setMessage(errorMessage);
                    err.setRequestCode(requestCode);
                    eventBus.post(err);
                    Log.d(TAG, " ErrorMessage " + errorMessage);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        };
    }

    private void sendLog(String errorMessage) {
        try {
            String url = getUrl();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }




    private String getUrl() throws UnsupportedEncodingException {
        String combinedParams = "";
        if (!urlParams.isEmpty()) {
            combinedParams += "?";
            for (Map.Entry<String, String> entry : urlParams.entrySet()) {
                String paramString = entry.getKey() + "=" + URLEncoder.encode(entry.getValue(), "UTF-8");
                if (combinedParams.length() > 1) {
                    combinedParams += "&" + paramString;
                } else {
                    combinedParams += paramString;
                }
            }
        }
        return baseUrl + combinedParams;
    }

    public void setRequestCode(int requestCode) {
        this.requestCode = requestCode;
    }

    private String getTag() {
        return TextUtils.isEmpty(tag) ? TAG : tag;
    }

    private int getMethod() {
        switch (method) {
            case GET:
                return Request.Method.GET;
            case POST:
                return Request.Method.POST;
            case PUT:
                return Request.Method.PUT;
            case DELETE:
                return Request.Method.DELETE;
        }
        return -1;
    }

    private void makeRequest() {
        try{
            requestQueue.add(getStringRequest());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private StringRequest getStringRequest() throws UnsupportedEncodingException, JSONException {
        String url = getUrl();
        Log.d(TAG, "URL: " + url);

        StringRequest stringRequest;

        if( method != null ) {
            stringRequest = new StringRequest(Request.Method.POST, getUrl(), jsonResponseListener, errorListener) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    for (String name: headers.keySet()){
                        if(name!=null && headers.get(name)!=null){
                            Log.d(TAG, "header: " + name + " " + headers.get(name));
                        }
                    }
                    return headers;
                }

                @Override
                public byte[] getBody() throws com.android.volley.AuthFailureError {
                    String str = requestBody;
                    return str.getBytes();
                }

                public String getBodyContentType()
                {
                    return "application/json";
                }

            };
        } else {
            stringRequest = new StringRequest(getUrl(), jsonResponseListener, errorListener) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    return headers;
                }

                @Override
                public byte[] getBody() throws com.android.volley.AuthFailureError {
                    String str = requestBody;
                    return str.getBytes();
                }

                public String getBodyContentType()
                {
                    return "application/json";
                }
            };
        }

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0,0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        if(tag!=null) {
            stringRequest.setTag(tag);
        }
        return stringRequest;
    }


    @Override
    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

    @Override
    public void addUrlParams(String key, String value) {
        urlParams.put(key, value);
    }

    @Override
    public void addParams(String key, String value) {
        params.put(key, value);
    }

    @Override
    public void addRequestBody(String requestBody) {
        this.requestBody = requestBody;
        Log.d(TAG, "RequestBody: " + requestBody);
    }

    @Override
    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    public void execute() {
        makeRequest();
    }
}
