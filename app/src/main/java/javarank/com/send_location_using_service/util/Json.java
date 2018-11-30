package javarank.com.send_location_using_service.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;

public class Json {

    public static <T> String serialize(T obj) {
        Gson gson = new Gson();
        return gson.toJson(obj);
    }

    public static <T> T deSerialize(String jsonString, Type tClass) {
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(jsonString, tClass);
    }
}