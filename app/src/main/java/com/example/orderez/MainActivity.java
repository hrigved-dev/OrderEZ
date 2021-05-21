package com.example.orderez;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    Thread timer;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        timer = new Thread() {
            @Override
            public void run() {
                try {
                    synchronized (this) {
                        wait(3000);
                    }
                } catch(InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    FirebaseUser currentUser = mAuth.getCurrentUser();
                    if(currentUser == null) {
                        Intent intent = new Intent(MainActivity.this, AuthScreen.class);
                        startActivity(intent);
                        finish();
                    }
                    else {
                        Intent intent = new Intent(MainActivity.this, Dashboard.class);
                        startActivity(intent);
                        finish();
                    }

                }
            }
        };
        timer.start();
    }
}