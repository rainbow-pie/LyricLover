package com.example.lyricslover;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

public class SplashScreen extends AppCompatActivity {

    private boolean mAlreadyOpen = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Handler handler = new Handler();

        handler.postDelayed(() -> {
            if(!mAlreadyOpen)
                startMainActivity();
        }, 3000);

        ConstraintLayout layout = findViewById(R.id.splashScreenLayout);
        layout.setOnClickListener((View view)-> startMainActivity());
    }

    private void startMainActivity()
    {
        mAlreadyOpen = true;
        Intent intent = new Intent(SplashScreen.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}