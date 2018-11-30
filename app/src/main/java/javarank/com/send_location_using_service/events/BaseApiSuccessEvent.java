package javarank.com.send_location_using_service.events;

import javarank.com.send_location_using_service.util.Json;

public class BaseApiSuccessEvent extends BaseEvent {

    private String response;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public <T> T getTypedResponse(Class<T> tClass) {
        return Json.deSerialize(response, tClass);
    }
}
