package javarank.com.send_location_using_service.events;


public class BaseSuccessEvent extends BaseEvent {

    private String response;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }


}
