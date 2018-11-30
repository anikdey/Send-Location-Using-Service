package javarank.com.send_location_using_service.webservice;


public interface IWebRequestManager {
    void addHeader(String key, String value);

    void addUrlParams(String key, String value);

    void addParams(String key, String value);

    void addRequestBody(String requestBody);

    void setTag(String tag);

    void execute();
}
