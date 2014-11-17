package daria.lesson3.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import daria.lesson3.R;
import daria.lesson3.actions.DownloadImage;
import daria.lesson3.actions.FiveHundredSearch;
import daria.lesson3.actions.Translation;
import daria.lesson3.elements.Photo;

/**
 * Created by daria on 16.11.14.
 */
public class ResultActivity extends Activity {
    private ArrayList<Photo> downloaded;
    private int curImage;
    private int imagesNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        String query = getIntent().getStringExtra(StartActivity.QUERY);
        TextView textView = (TextView) findViewById(R.id.query);
        textView.setText(query);
        downloaded = new ArrayList<Photo>();
        new Translation(ResultActivity.this).execute(query);
    }

    public void onTranslationFinished(String translation) {
        TextView textView = (TextView) findViewById(R.id.translation);
        textView.setText(translation);
        new FiveHundredSearch(ResultActivity.this).execute(translation);
    }

    public void onSearchFinished(ArrayList<Photo> photos) {
        for (Photo photo : photos) {
            new DownloadImage(ResultActivity.this).execute(photo);
        }
    }


    public void onImageDownloaded(Photo photo) {
        downloaded.add(photo);
        imagesNumber = downloaded.size();
        if (downloaded.size() == 1) {
            ImageView image = (ImageView) findViewById(R.id.image);
            image.setImageDrawable(downloaded.get(0).getDrawable());
            curImage = 0;
        }
        TextView counter = (TextView) findViewById(R.id.counter);
        counter.setText(Integer.toString(curImage + 1) + "/" + Integer.toString(imagesNumber));
    }

    public void nextImage(View view) {
        ImageView image = (ImageView) findViewById(R.id.image);
        curImage = (curImage + 1) % downloaded.size();
        image.setImageDrawable(downloaded.get(curImage).getDrawable());
        TextView counter = (TextView) findViewById(R.id.counter);
        counter.setText(Integer.toString(curImage + 1) + "/" + Integer.toString(imagesNumber));
    }
}
