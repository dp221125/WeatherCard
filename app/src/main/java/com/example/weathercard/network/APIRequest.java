package com.example.weathercard.network;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;
import com.google.gson.JsonSyntaxException;

import net.oauth.OAuth;
import net.oauth.OAuthAccessor;
import net.oauth.OAuthConsumer;
import net.oauth.OAuthException;
import net.oauth.OAuthMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class APIRequest<T> extends JsonRequest<T> {

    final String appId = "5pIWechk";
    final String CONSUMER_KEY = "dj0yJmk9bTYzMDA5WU1GREEzJmQ9WVdrOU5YQkpWMlZqYUdzbWNHbzlNQT09JnM9Y29uc3VtZXJzZWNyZXQmc3Y9MCZ4PWI3";
    final String CONSUMER_SECRET = "d9be33bf0bf6fd581a87f645adf1c55f98c00821";
    final String baseUrl = "https://weather-ydn-yql.media.yahoo.com/forecastrss";

    public APIRequest(int method, String url, String requestBody, Response.Listener<T> listener, Response.ErrorListener errorListener) {
        super(method, url, requestBody, listener, errorListener);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers = new HashMap<>();
        OAuthConsumer consumer = new OAuthConsumer(null, CONSUMER_KEY, CONSUMER_SECRET, null);
        consumer.setProperty(OAuth.OAUTH_SIGNATURE_METHOD, OAuth.HMAC_SHA1);
        OAuthAccessor accessor = new OAuthAccessor(consumer);
        try {
            OAuthMessage request = accessor.newRequestMessage(OAuthMessage.GET, getUrl(), null);
            String authorization = request.getAuthorizationHeader(null);
            headers.put("Authorization", authorization);

        } catch (OAuthException e) {
            Log.e( "Error ", " : " + e );
            e.printStackTrace();

        } catch (URISyntaxException e) {
            Log.e( "Error ", " : " + e );
            e.printStackTrace();
        } catch (IOException e) {
            Log.e( "Error ", " : " + e );
            e.printStackTrace();
        }
        headers.put("X-Yahoo-App-Id", appId);
        headers.put("Content-Type", "application/json");
        return headers;
    }

    @Override
    public String getUrl() {
        return baseUrl + "?location=sunnyvale,ca&format=json";
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(
                    response.data,
                    HttpHeaderParser.parseCharset(response.headers));

            T parsedResponse = parseResponse(json);
            return Response.success(
                    parsedResponse,
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException | JsonSyntaxException e) {
            Log.e("실패 ", ":" + e);
            return Response.error(new ParseError(e));
        } catch (JSONException e) {
            return  Response.error(new ParseError(e));
        }
    }

    private T parseResponse(String jsonObject) throws JSONException {
        JSONObject jsonObj = new JSONObject(jsonObject.toString());
        return (T) jsonObj;

    }
}
