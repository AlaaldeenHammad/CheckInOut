package com.hbusiness.fbtest1122;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;

public class splash extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        firebaseAuth=FirebaseAuth.getInstance();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (firebaseAuth.getCurrentUser()!=null){
                Intent intent =new Intent(splash.this,MainActivity.class);
                startActivity(intent);
                finish();}
                else {
                    Intent intent =new Intent(splash.this,Login.class);
                    startActivity(intent);
                    finish();
                }
            }
        },2500);
    }
}