package javarank.com.send_location_using_service;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashScreenActivity extends AppCompatActivity {

    static final int SPLASH_TIMEOUT = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startHomeScreen();
            }
        }, SPLASH_TIMEOUT);
    }

    private void startHomeScreen() {
        startActivity(MainActivity.newIntent(SplashScreenActivity.this));
        finish();
    }
}
