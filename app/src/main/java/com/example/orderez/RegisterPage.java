package com.example.orderez;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterPage extends AppCompatActivity {

    TextInputLayout t1, t2;
    ProgressBar bar;
    private FirebaseAuth mAuth;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);
        t1 = (TextInputLayout)findViewById(R.id.email);
        t2 = (TextInputLayout)findViewById(R.id.password);
        bar = (ProgressBar)findViewById(R.id.progressBar);
        textView = (TextView)findViewById(R.id.textView);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RegisterPage.this, "Moving to LoginPage", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RegisterPage.this, AuthScreen.class);
                startActivity(intent);
                finish();
            }
        });

    }
}