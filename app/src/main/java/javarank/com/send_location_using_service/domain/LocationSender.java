package javarank.com.send_location_using_service.domain;

import com.android.volley.RequestQueue;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import javarank.com.send_location_using_service.App;
import javarank.com.send_location_using_service.events.SendLocationApiErrorEvent;
import javarank.com.send_location_using_service.events.SendLocationApiSuccessEvent;
import javarank.com.send_location_using_service.events.SendLocationAppErrorEvent;
import javarank.com.send_location_using_service.events.SendLocationErrorEvent;
import javarank.com.send_location_using_service.events.SendLocationSuccessEvent;
import javarank.com.send_location_using_service.request.LocationRequest;
import javarank.com.send_location_using_service.util.EventHandler;
import javarank.com.send_location_using_service.util.HttpErrorHelper;
import javarank.com.send_location_using_service.util.HttpURLs;
import javarank.com.send_location_using_service.util.Json;
import javarank.com.send_location_using_service.util.RequestGenerator;
import javarank.com.send_location_using_service.util.RequestMethod;
import javarank.com.send_location_using_service.webservice.IWebRequestManager;
import javarank.com.send_location_using_service.webservice.WebServiceStringRequestManager;

public class LocationSender implements ILocationSender {

    private EventBus eventBus;
    private RequestQueue requestQueue;

    public LocationSender() {
        eventBus = EventBus.getDefault();
        requestQueue = App.getInstance().getRequestQueue();
    }

    @Override
    public void sendLocation(LocationRequest request) {
        EventHandler.register(eventBus, this);
        String url = HttpURLs.URL;
        String requestStr = Json.serialize(request);
        IWebRequestManager requestManager = new WebServiceStringRequestManager<>(RequestMethod.POST, requestQueue, url, SendLocationSuccessEvent.class, SendLocationErrorEvent.class);
        RequestGenerator.addAuthHeaderForPostRequest(requestManager, requestStr);
        requestManager.execute();
    }

    @Subscribe
    public void onGetAuditEquipmentSuccessEvent(SendLocationSuccessEvent successEvent) {
        EventHandler.unRegister(eventBus, this);
        SendLocationApiSuccessEvent apiSuccessEvent = new SendLocationApiSuccessEvent();
        apiSuccessEvent.setResponse(successEvent.getResponse());
        eventBus.post(apiSuccessEvent);
    }

    @Subscribe
    public void onGetAuditEquipmentErrorEvent(SendLocationErrorEvent errorEvent) {
        EventHandler.unRegister(eventBus, this);
        if (errorEvent.getMessage().equals(HttpErrorHelper.NO_NETWORK_ERROR) || errorEvent.getMessage().equals(HttpErrorHelper.TIME_OUT_ERROR) || errorEvent.getMessage().equals(HttpErrorHelper.UNKNOWN)) {
            SendLocationAppErrorEvent appErrorEvent = new SendLocationAppErrorEvent();
            eventBus.post(appErrorEvent);
        } else {
            SendLocationApiErrorEvent apiErrorEvent = new SendLocationApiErrorEvent();
            apiErrorEvent.setMessage(errorEvent.getMessage());
            eventBus.post(apiErrorEvent);
        }
    }
}