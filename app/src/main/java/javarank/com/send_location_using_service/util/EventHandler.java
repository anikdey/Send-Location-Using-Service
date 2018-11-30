package javarank.com.send_location_using_service.util;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;

public class EventHandler {

    private static HashMap<String, Integer> eventSubscriber = new HashMap<>();

    public static void register(EventBus eventBus, Object objects) {
        String key = objects.getClass().getCanonicalName();

        /*
        if (eventSubscriber.containsKey(key)) {
            eventSubscriber.put(key, eventSubscriber.get(key) + 1);
        } else {
            eventSubscriber.put(key, 1);
        }
        */

        if (eventSubscriber.containsKey(key)){
            eventSubscriber.put(key, 1);
        }
        if (eventBus.isRegistered(objects) == false) {
            eventBus.register(objects);
        }

//        for(Map.Entry entry : eventSubscriber.entrySet()) {
//            Log.d("EventHandler", entry.getKey().toString()+ " "+ entry.getValue().toString());
//        }

    }

    public static void unRegister(EventBus eventBus, Object objects) {
        String key = objects.getClass().getCanonicalName();
        if (eventSubscriber.containsKey(key)) {
            eventSubscriber.remove(key);
//            int value = eventSubscriber.get(key);
//            eventSubscriber.put(key, eventSubscriber.get(key) - 1);
//            if (value < 1) {
//                if (eventBus.isRegistered(objects)) {
//                    eventBus.unregister(objects);
//                }
//                eventSubscriber.remove(key);
//            }
        }

        if(eventBus.isRegistered(objects)) {
            eventBus.unregister(objects);
        }


    }
}
