package javarank.com.send_location_using_service;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import java.util.regex.Pattern;

import butterknife.ButterKnife;
import butterknife.OnClick;
import javarank.com.send_location_using_service.services.LocationService;

public class MainActivity extends AppCompatActivity {
    private static final int APP_REQUEST_PERMISSION = 111;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        if (ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.GET_ACCOUNTS) != PackageManager.PERMISSION_GRANTED ||
                 ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.GET_ACCOUNTS, android.Manifest.permission.ACCESS_COARSE_LOCATION}, APP_REQUEST_PERMISSION);
        } else {
            initViewAfterPermission();
        }
    }

    public void initViewAfterPermission() {

    }

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }

}