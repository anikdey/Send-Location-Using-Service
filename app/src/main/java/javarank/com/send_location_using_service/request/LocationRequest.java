package javarank.com.send_location_using_service.request;

import javarank.com.send_location_using_service.models.Location;
import javarank.com.send_location_using_service.models.User;

public class LocationRequest {

    private Location Location;
    private User User;

    public javarank.com.send_location_using_service.models.Location getLocation() {
        return Location;
    }

    public void setLocation(javarank.com.send_location_using_service.models.Location location) {
        Location = location;
    }

    public javarank.com.send_location_using_service.models.User getUser() {
        return User;
    }

    public void setUser(javarank.com.send_location_using_service.models.User user) {
        User = user;
    }
}
