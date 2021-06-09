package com.example.tinternshipbackend.services.httpBackendCommunicator;


import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.tinternshipbackend.controllers.authentication.AuthController;
import com.google.gson.reflect.TypeToken;
import com.android.volley.Request;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HttpClient<T> {
    // TODO set dynamicly from env or something
    public static String BASE_URL = "https://7facef5a17be.ngrok.io/";
    Context context;

    public HttpClient(Context context) {
        this.context = context;
    }

    public void get(String relativeUrl, HttpResponse<T> httpResponse, Class clazz) {
        Type type = TypeToken.getParameterized(clazz).getType();

        GsonRequest<T> request = new GsonRequest<T>(
                getUrl(relativeUrl),
                type,
                getHeaders(),
                new Response.Listener<T>() {
                    @Override
                    public void onResponse(T response) {
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

    public void getList(String relativeUrl, HttpResponse<ArrayList<Object>> httpResponse) {
        GsonRequest<ArrayList<Object>> request = new GsonRequest<ArrayList<Object>>(
                getUrl(relativeUrl),
                ArrayList.class,
                getHeaders(),
                new Response.Listener<ArrayList<Object>>() {
                    @Override
                    public void onResponse(ArrayList<Object> response) {
                        httpResponse.onSuccess(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        httpResponse.onError(error.getMessage());
                    }
                }
        );
        VolleyHelper.getInstance(context).addToRequestQueue(request);
    }

    public void post(String relativeUrl, Object bodyData, HttpResponse<T> httpResponse, Class<T> clazz) {
        sendRequestWithData(Request.Method.POST, relativeUrl, bodyData, httpResponse, clazz);
    }

    public void put(String relativeUrl, Object bodyData, HttpResponse<T> httpResponse, Class<T> clazz) {
        sendRequestWithData(Request.Method.PUT, relativeUrl, bodyData, httpResponse, clazz);
    }

    public void delete(String relativeUrl, Object bodyData, HttpResponse<T> httpResponse, Class<T> clazz) {
        sendRequestWithData(Request.Method.DELETE, relativeUrl, bodyData, httpResponse, clazz);
    }

    private void sendRequestWithData(int method, String relativeUrl, Object bodyData, HttpResponse<T> httpResponse, Class<T> clazz) {
        Type type = TypeToken.getParameterized(clazz).getType();
        GsonRequest<T> request = new GsonRequest<T>(
                getUrl(relativeUrl),
                type,
                getHeaders(),
                method,
                bodyData,
                new Response.Listener<T>() {
                    @Override
                    public void onResponse(T response) {
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
        Map<String, String> map = new HashMap<>();
        map.put("Authorization", "Bearer " +  new AuthController(context).getJWTKey());
        map.put("Content-Type", "application/json");
        map.put("Accept", "application/json");

        return map;
    }
}
