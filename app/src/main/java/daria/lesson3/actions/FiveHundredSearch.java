package daria.lesson3.actions;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;

import java.util.ArrayList;

import daria.lesson3.activities.ResultActivity;
import daria.lesson3.api.FiveHundredQuery;
import daria.lesson3.elements.Photo;

/**
 * Created by daria on 16.11.14.
 */
public class FiveHundredSearch extends AsyncTask<String, Void, ArrayList<Photo>> {
    ResultActivity activity;

    public static final String API_KEY = "I9xyEPPXQKl7cyuhps5UCQr8AmHcne2rOi6f1TOa";

    public FiveHundredSearch(ResultActivity activity) {
        this.activity = activity;
    }

    @Override
    protected ArrayList<Photo> doInBackground(String... strings) {
        FiveHundredQuery fiveHundredQuery = new FiveHundredQuery(API_KEY);
        String query = strings[0];
        fiveHundredQuery.addParameter("image_size", "4");
        fiveHundredQuery.addParameter("term", query);
        ArrayList<Photo> pictures = new ArrayList<Photo>();
        try {
            JSONArray searchResults = fiveHundredQuery.get().getJSONArray("photos");
            for (int i = 0; i < searchResults.length(); ++i) {
                JSONArray current = searchResults.getJSONObject(i).getJSONArray("images");
                String url = current.getJSONObject(0).getString("https_url");
                Log.i("url", url);
                pictures.add(new Photo(url));
                if (pictures.size() == 10) {
                    break;
                }
            }
        } catch (Exception e) {
            Log.e("FlickrSearch", "Search failed.", e);
        }
        return pictures;
    }

    @Override
    protected void onPostExecute(ArrayList<Photo> photos) {
        super.onPostExecute(photos);
        activity.onSearchFinished(photos);
    }
}
