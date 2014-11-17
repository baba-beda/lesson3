package daria.lesson3.api;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by daria on 16.11.14.
 */
public class YandexQuery {
    public static final String HOST = "https://translate.yandex.net/api/v1.5/tr.json/translate?key=";
    public static final String TAG = "Yandex";
    private String apiKey;
    private String params;

    public YandexQuery(String apiKey) {
        this.apiKey = apiKey;
        this.params = "";
    }

    public JSONObject get() {
        final String finalUrl = String.format("%s%s%s", HOST, this.apiKey, params).replaceAll(" ", "%20");
        return handle(new HttpGet(finalUrl));
    }

    public void addParameter(String param, String value) {
        params += "&" + param + "=" + value;
    }

    private JSONObject handle(HttpUriRequest request) {
        try {
            DefaultHttpClient client = new DefaultHttpClient();

            HttpResponse response = client.execute(request);
            final int statusCode = response.getStatusLine().getStatusCode();

            if (statusCode != HttpStatus.SC_OK) {
                final String msg = String.format(
                        "Error, statusCode not OK(%d). for url: %s",
                        statusCode, request.getURI().toString());
                Log.e(TAG, msg);
                return null;
            }

            HttpEntity responseEntity = response.getEntity();
            InputStream inputStream = responseEntity.getContent();
            BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder total = new StringBuilder();
            String line;
            while ((line = r.readLine()) != null) {
                total.append(line);
            }

            return new JSONObject(total.toString());
        } catch (Exception e) {
            Log.e(TAG, "Error obtaining response from Yandex api.", e);
        }
        return null;
    }
}
