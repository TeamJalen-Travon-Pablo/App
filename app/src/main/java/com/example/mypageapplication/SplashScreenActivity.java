package com.example.mypageapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreenActivity extends AppCompatActivity {
    FirebaseAuth auth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();


        new Handler().postDelayed(new Runnable() {
            @NonNull
            public void run(){
                if (user !=null){
                    startActivity(new Intent(SplashScreenActivity.this, HomeActivity.class ));
                }else{
                    startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class));}
            }
        }, 4000);

    }
}