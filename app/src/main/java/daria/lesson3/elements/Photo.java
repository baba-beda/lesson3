package daria.lesson3.elements;

import android.graphics.drawable.Drawable;

/**
 * Created by daria on 16.11.14.
 */

public class Photo {
    private String url;
    private Drawable drawable;

    public Photo(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public Drawable getDrawable() {
        return  drawable;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }
}
