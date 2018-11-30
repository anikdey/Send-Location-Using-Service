package javarank.com.send_location_using_service.events;

import com.google.gson.annotations.SerializedName;

public class BaseApiErrorEvent extends BaseEvent {
    private int code;
    private String message;

    @SerializedName("error")
    private String error;
    @SerializedName("error_description")
    private String errorDescription;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }
}
