package com.example.fleischerfoundation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.airbnb.lottie.LottieAnimationView;

public class Splash extends AppCompatActivity {
    ImageView logo, splashImage, appName;
    LottieAnimationView lottieAnimationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        logo = findViewById(R.id.logo);
        splashImage = findViewById(R.id.img);
        lottieAnimationView = findViewById(R.id.lottie);
        appName = findViewById(R.id.app_name);

        splashImage.animate().translationY(-2400).setDuration(1000).setStartDelay(4000);
        logo.animate().translationY(1800).setDuration(1000).setStartDelay(4000);
        lottieAnimationView.animate().translationY(1800).setDuration(1000).setStartDelay(4000);
        appName.animate().translationY(1800).setDuration(1000).setStartDelay(4000);


    }
}