package com.example.tinternshipbackend.services.httpBackendCommunicator;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.Map;

public class GsonRequest<T> extends Request<T> {
    private final Gson gson = new Gson();
    private final Class<T> mClazz;
    private final Map<String, String> headers;
    private final Response.Listener<T> listener;
    private final java.lang.reflect.Type mType;
    private final String TAG = "GsonRequest<T>";
    private Object body;

    /** Make a HTTP request and return a parsed object from JSON.
     * @param url URL of the request to make
     * @param clazz Relevant class object, for Gson's reflection
     * @param headers Map of request headers     */
    public GsonRequest(String url, Class<T> clazz, Map<String, String> headers,
                       Response.Listener<T> listener, Response.ErrorListener errorListener) {
        super(Method.GET, url, errorListener);
        this.mClazz = clazz;
        this.mType = null;
        this.headers = headers;
        this.listener = listener;
    }


    public GsonRequest(String url, Type type, Map<String, String> headers,
                       Response.Listener<T> listener, Response.ErrorListener errorListener) {
        super(Method.GET, url, errorListener);
        this.mClazz = null;
        this.mType = type;
        this.headers = headers;
        this.listener = listener;
    }

    public GsonRequest(
       String url,
       Type type,
       Map<String, String> headers,
       int method,
       Object body,
       Response.Listener<T> listener,
       Response.ErrorListener errorListener
    ) {
        super(method, url, errorListener);
        this.mClazz = null;
        this.body = body;
        this.mType = type;
        this.headers = headers;
        this.listener = listener;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return headers != null ? headers : super.getHeaders();
    }

    @Override
    protected void deliverResponse(T response) {
        listener.onResponse(response);
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        return gson.toJson(this.body).getBytes();
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(response.data,HttpHeaderParser.parseCharset(response.headers));
            Log.d(TAG, "RESPONSE = " + json);

            if (mClazz != null) {
                return Response.success(gson.fromJson(json, mClazz), HttpHeaderParser.parseCacheHeaders(response));
            } else {
                return Response.success( gson.fromJson(json, mType), HttpHeaderParser.parseCacheHeaders(response));
            }
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        } catch (ClassCastException e) {
            return Response.error(new ParseError(e));
        }
    }
}
