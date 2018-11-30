package javarank.com.send_location_using_service.domain;

import javarank.com.send_location_using_service.request.LocationRequest;

public interface ILocationSender {
    void sendLocation(LocationRequest request);
}
