package javarank.com.send_location_using_service.presenter;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.util.Patterns;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.regex.Pattern;

import javarank.com.send_location_using_service.App;
import javarank.com.send_location_using_service.domain.ILocationSender;
import javarank.com.send_location_using_service.domain.LocationSender;
import javarank.com.send_location_using_service.events.SendLocationApiErrorEvent;
import javarank.com.send_location_using_service.events.SendLocationApiSuccessEvent;
import javarank.com.send_location_using_service.events.SendLocationAppErrorEvent;
import javarank.com.send_location_using_service.models.Location;
import javarank.com.send_location_using_service.models.User;
import javarank.com.send_location_using_service.request.LocationRequest;
import javarank.com.send_location_using_service.util.EventHandler;

public class MainPresenter implements IMainPresenter {

    private ILocationSender locationSender;
    private EventBus eventBus;

    public MainPresenter() {
        eventBus = EventBus.getDefault();
    }

    @Override
    public void sendLocation(double latitude, double longitude) {
        EventHandler.register(eventBus, this);
        LocationRequest request = new LocationRequest();
        request.setLocation(getLocation(latitude, longitude));
        request.setUser(getUser());

       locationSender = new LocationSender();
       locationSender.sendLocation(request);
    }

    private Location getLocation(double latitude, double longitude) {
        Location locationObject = new Location();
        locationObject.setLatitude(latitude);
        locationObject.setLongitude(longitude);
        return locationObject;
    }

    private User getUser() {
        User user = new User();
        String email = getEmail();
        String name = getName(email);
        user.setEmail(email);
        user.setName(name);
        return user;
    }

    private String getEmail() {
        String email = null;
        Pattern emailPattern = Patterns.EMAIL_ADDRESS;
        Account[] accounts = AccountManager.get(App.getInstance().getApplicationContext()).getAccounts();
        for (Account account : accounts) {
            if (emailPattern.matcher(account.name).matches()) {
                email = account.name;
                return email;
            }
        }
        return email;
    }

    private String getName(String email) {
        String name = null;
        if( email != null ) {
            String[] parts = email.split("@");
            if (parts.length > 1)
                name = parts[0];
        }
        return name;
    }

    @Subscribe
    public void onSendLocationApiSuccessEvent(SendLocationApiSuccessEvent successEvent) {
        //showMessage("Success");
    }

    @Subscribe
    public void onSendLocationApiErrorEvent(SendLocationApiErrorEvent errorEvent) {
        EventHandler.unRegister(eventBus, this);
        showMessage("Something went wrong");
    }

    @Subscribe
    public void onSendLocationAppErrorEvent(SendLocationAppErrorEvent errorEvent) {
        EventHandler.unRegister(eventBus, this);
        showMessage("Check your internet connection and try again.");
    }

    private void showMessage(String message) {
        Toast.makeText(App.getInstance().getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}