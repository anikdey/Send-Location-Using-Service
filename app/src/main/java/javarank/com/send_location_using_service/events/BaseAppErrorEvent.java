package javarank.com.send_location_using_service.events;


public class BaseAppErrorEvent extends BaseEvent {

    private int errorType;
    private String message;

    public int getErrorType() {
        return errorType;
    }

    public void setErrorType(int errorType) {
        this.errorType = errorType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
