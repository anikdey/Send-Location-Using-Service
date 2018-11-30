package javarank.com.send_location_using_service.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.OnClick;
import javarank.com.send_location_using_service.MainActivity;
import javarank.com.send_location_using_service.R;
import javarank.com.send_location_using_service.services.LocationService;

public class MainFragment extends Fragment{
    public static final String TAG = MainFragment.class.getSimpleName();
    private static final int APP_REQUEST_PERMISSION = 111;

    public static MainFragment getInstance() {
        MainFragment fragment = new MainFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.GET_ACCOUNTS) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.GET_ACCOUNTS, android.Manifest.permission.ACCESS_COARSE_LOCATION}, APP_REQUEST_PERMISSION);
        } else {
            initViewAfterPermission();
        }
    }

    public void initViewAfterPermission() {
        //initializeViewPager();
        //initTabLayout();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == APP_REQUEST_PERMISSION) {
            boolean grantedAllPermission = isAllPermissionGranted(grantResults);
            if (grantedAllPermission) {
                initViewAfterPermission();
            } else {
                Toast.makeText(getContext(), "Not enough permission granted.", Toast.LENGTH_LONG).show();
                getActivity().finish();
            }
        }
    }

    private boolean isAllPermissionGranted(int[] grantResults) {
        boolean grantedAllPermission = true;
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                grantedAllPermission = false;
                break;
            }
        }
        return grantedAllPermission;
    }

    @OnClick(R.id.connect_button)
    protected void startService() {
        showMessage("Connected");
        getActivity().startService(new Intent(getActivity(), LocationService.class));
    }

    @OnClick(R.id.disconnect_button)
    protected void stopService() {
        showMessage("Disconnected");
        getActivity().stopService(new Intent(getActivity(), LocationService.class));
    }

    private void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
