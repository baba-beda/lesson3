package daria.lesson3.actions;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;

import java.io.InputStream;
import java.net.URL;

import daria.lesson3.activities.ResultActivity;
import daria.lesson3.elements.Photo;

/**
 * Created by daria on 16.11.14.
 */
public class DownloadImage extends AsyncTask<Photo, Void, Photo> {
    ResultActivity activity;

    public DownloadImage(ResultActivity activity) {
        this.activity = activity;
    }

    @Override
    protected Photo doInBackground(Photo... photos) {
        Photo photo = photos[0];
        try {
            String url = photo.getUrl();
            InputStream is = (InputStream) new URL(url).getContent();
            String[] path = url.split("/");
            photo.setDrawable(Drawable.createFromStream(is, path[path.length - 2]));
            return photo;
        } catch (Exception e) {
            Log.e("DownloadImageTask", "Download Failed.", e);
            return null;
        }
    }

    @Override
    protected void onPostExecute(Photo photo) {
        super.onPostExecute(photo);
        activity.onImageDownloaded(photo);
    }
}
