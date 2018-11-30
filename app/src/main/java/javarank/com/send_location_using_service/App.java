package javarank.com.send_location_using_service;

import android.app.Application;
import android.support.multidex.MultiDexApplication;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class App  extends MultiDexApplication {
    private static App mInstance;
    private RequestQueue mRequestQueue;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static synchronized App getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return mRequestQueue;
    }
}