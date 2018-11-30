package javarank.com.send_location_using_service.util;

import javarank.com.send_location_using_service.webservice.IWebRequestManager;

public class RequestGenerator {
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String CONTENT_TYPE_JSON = "application/json";

    public static void addAuthHeaderForPostRequest(IWebRequestManager requestManager, String requestBody) {
        addJsonContentAuthHeader(requestManager);
        requestManager.addRequestBody(requestBody);
    }

    private static void addJsonContentAuthHeader(IWebRequestManager requestManager) {
        requestManager.addHeader(CONTENT_TYPE, CONTENT_TYPE_JSON);
    }

}
