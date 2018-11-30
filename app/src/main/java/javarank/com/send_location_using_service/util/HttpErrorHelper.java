package javarank.com.send_location_using_service.util;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;

public class HttpErrorHelper {

    public static final String TIME_OUT_ERROR = "time_out_error";
    public static final String NO_NETWORK_ERROR = "no_network_error";
    public static final String UNKNOWN = "unknown";
    public static final String AUTHORIZATION_ERROR = "auth_error";


    public static boolean isServerProblem(Object error) {
        return (error instanceof ServerError);
    }

    public static boolean isNetworkProblem (Object error){
        return (error instanceof NetworkError || error instanceof NoConnectionError);
    }
    public static boolean isTimeOutProblem(Object error){
        return (error instanceof TimeoutError);
    }

    public static String processErrorMessage(Object error) {
        VolleyError volleyError = (VolleyError) error;
        if(HttpErrorHelper.isNetworkProblem(volleyError)){
            return NO_NETWORK_ERROR;
        }else if(HttpErrorHelper.isAuthorizationError(volleyError)){
            return AUTHORIZATION_ERROR;

        }
        else if(HttpErrorHelper.isServerProblem(volleyError)){
            return new String(volleyError.networkResponse.data);

        }else if(HttpErrorHelper.isTimeOutProblem(error)){
            return TIME_OUT_ERROR;
        }
        return UNKNOWN;
    }

    private static boolean isAuthorizationError(VolleyError error) {
        return (error instanceof AuthFailureError);
    }
}

