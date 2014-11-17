package daria.lesson3.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import daria.lesson3.R;

/**
 * Created by daria on 16.11.14.
 */
public class StartActivity extends Activity {
    public static final String QUERY = "QUERY";

    private EditText wordField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        wordField = (EditText) findViewById(R.id.word_field);
    }

    public void startTranslation(View view) {
        Intent intent = new Intent(this, ResultActivity.class);
        String query = wordField.getText().toString();
        intent.putExtra(QUERY, query);
        startActivity(intent);
    }
}
