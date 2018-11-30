package javarank.com.send_location_using_service.events;

public class BaseErrorEvent extends BaseEvent {

    //private String message;
    private String error;
    private int errorType;

    public String getMessage() {
        return error;
    }

    public void setMessage(String message) {
        this.error = message;
    }

    public int getErrorType() {
        return errorType;
    }

    public void setErrorType(int errorType) {
        this.errorType = errorType;
    }
}
