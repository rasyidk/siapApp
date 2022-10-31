package com.example.siap.splashScreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.siap.Main.MainActivity;
import com.example.siap.R;

import java.util.Timer;
import java.util.TimerTask;

public class splashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Timer t =  new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent i =  new Intent(splashScreen.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        },2000);
    }
}