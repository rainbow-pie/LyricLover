package com.example.lyricslover;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
public class MainActivity extends AppCompatActivity {

    private EditText mFindText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFindText = findViewById(R.id.findText);

        findViewById(R.id.findButton).setOnClickListener(view -> {
            String query = mFindText.getText().toString();

            if (!query.isEmpty()) {
                Intent intent = new Intent(MainActivity.this, ResultsActivity.class);
                intent.putExtra(ResultsActivity.ARG_PARAM, query);
                startActivity(intent);
            } else startActivity(new Intent(MainActivity.this, ResultsActivity.class));
        });
    }
}