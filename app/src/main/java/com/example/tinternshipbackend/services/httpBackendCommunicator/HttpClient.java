package com.example.tinternshipbackend.services.httpBackendCommunicator;


import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.tinternshipbackend.models.User;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Method;
import com.android.volley.Request;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpClient<T> {
    // TODO set dynamicly from env or something
    String BASE_URL = "http://192.168.0.86:8080";
    Context context;

    public HttpClient(Context context) {
        this.context = context;
    }

    public void get(String relativeUrl, HttpResponse<T> httpResponse) {
        Type type = new TypeToken<Class<T>>(){}.getType();

        GsonRequest<Class<T>> request = new GsonRequest<Class<T>>(
                getUrl(relativeUrl),
                type,
                getHeaders(),
                new Response.Listener<Class<T>>() {
                    @Override
                    public void onResponse(Class<T> response) {
                        httpResponse.onSuccess(response);
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        httpResponse.onError(error.getMessage());
                    }
                }
        );
        VolleyHelper.getInstance(context).addToRequestQueue(request);
    }

    public void post(String relativeUrl, Object bodyData, HttpResponse<T> httpResponse) {
        sendRequestWithData(Request.Method.POST, relativeUrl, bodyData, httpResponse);
    }

    public void put(String relativeUrl, Object bodyData, HttpResponse<T> httpResponse) {
        sendRequestWithData(Request.Method.PUT, relativeUrl, bodyData, httpResponse);
    }

    public void delete(String relativeUrl, Object bodyData, HttpResponse<T> httpResponse) {
        sendRequestWithData(Request.Method.DELETE, relativeUrl, bodyData, httpResponse);
    }

    private void sendRequestWithData(int method, String relativeUrl, Object bodyData, HttpResponse<T> httpResponse) {
        Type type = new TypeToken<Class<T>>(){}.getType();
        GsonRequest<Class<T>> request = new GsonRequest<Class<T>>(
                getUrl(relativeUrl),
                type,
                getHeaders(),
                method,
                bodyData,
                new Response.Listener<Class<T>>() {
                    @Override
                    public void onResponse(Class<T> response) {
                        httpResponse.onSuccess(response);
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        httpResponse.onError(error.getMessage());
                    }
                }
        );
        VolleyHelper.getInstance(context).addToRequestQueue(request);
    }


    private String getUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }

    private Map<String, String> getHeaders() {
        // TODO add authorization header.
        Map<String, String> map = new HashMap<>();
        map.put("Authorization", "");

        return map;
    }
}
