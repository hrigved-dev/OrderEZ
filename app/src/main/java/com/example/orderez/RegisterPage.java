package com.example.orderez;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterPage extends AppCompatActivity {

    TextInputLayout t1, t2, t3;
    ProgressBar bar;
    private FirebaseAuth mAuth;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);
        t3 = (TextInputLayout)findViewById(R.id.passwordCheck);
        t1 = (TextInputLayout)findViewById(R.id.email);
        t2 = (TextInputLayout)findViewById(R.id.password);
        bar = (ProgressBar)findViewById(R.id.progressBar);
        textView = (TextView)findViewById(R.id.textView);

        Spannable wordToSpan = new SpannableString("Already a User? Sign in.");
        wordToSpan.setSpan(new ForegroundColorSpan(Color.rgb(245, 92, 71)), 16, 23, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(wordToSpan);

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
    public void signUp(View view) {
        String email = t1.getEditText().getText().toString();
        String password = t2.getEditText().getText().toString();
        String passwordCheck = t3.getEditText().getText().toString();
        bar.setVisibility(View.VISIBLE);

        mAuth = FirebaseAuth.getInstance();

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(RegisterPage.this,  new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    bar.setVisibility(View.INVISIBLE);
                    t1.getEditText().setText("");
                    t2.getEditText().setText("");
                    t3.getEditText().setText("");
                    Toast.makeText(getApplicationContext(), "Registered Successfully", Toast.LENGTH_LONG).show();
                    Intent myIntent = new Intent(RegisterPage.this, Dashboard.class);
                    startActivity(myIntent);
                    finish();

                } else {
                    bar.setVisibility(View.INVISIBLE);
                    t1.getEditText().setText("");
                    t2.getEditText().setText("");
                    t3.getEditText().setText("");
                    Toast.makeText(getApplicationContext(), "Process Error", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}