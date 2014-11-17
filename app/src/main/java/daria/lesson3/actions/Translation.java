package daria.lesson3.actions;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import daria.lesson3.activities.ResultActivity;
import daria.lesson3.api.YandexQuery;

/**
 * Created by daria on 16.11.14.
 */
public class Translation extends AsyncTask<String, Void, String> {
    ResultActivity activity;

    public static final String API_KEY = "trnsl.1.1.20141116T003748Z.eee7fabb056a1208.79a26a05fc368340260ce80c884e93cf6c2b713e";

    public Translation(ResultActivity activity) {
        this.activity = activity;
    }

    @Override
    protected String doInBackground(String... strings) {
        YandexQuery yandexQuery = new YandexQuery(API_KEY);
        String query = strings[0];
        yandexQuery.addParameter("lang", "en-ru");
        yandexQuery.addParameter("text", query);

        try {
            JSONObject searchResult = yandexQuery.get();
            String translation = searchResult.optString("text");
            translation = translation.substring(2, translation.length() - 2);
            return translation;
        } catch (Exception e) {
            Log.e("SearchTask", "Search failed", e);
        }
        return "error";
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        activity.onTranslationFinished(s);
    }
}
