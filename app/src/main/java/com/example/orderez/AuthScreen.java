package com.example.orderez;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.nfc.Tag;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthScreen extends AppCompatActivity {

    TextInputLayout t1, t2;
    ProgressBar bar;
    private FirebaseAuth mAuth;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_screen);
        t1 = (TextInputLayout)findViewById(R.id.email);
        t2 = (TextInputLayout)findViewById(R.id.password);
        bar = (ProgressBar)findViewById(R.id.progressBar);
        textView = (TextView)findViewById(R.id.textView);
        Spannable wordToSpan = new SpannableString("New user? Sign Up.");
        wordToSpan.setSpan(new ForegroundColorSpan(Color.rgb(245, 92, 71)), 10, 17, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(wordToSpan);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AuthScreen.this, "Moving to RegisterPage", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AuthScreen.this, RegisterPage.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void signIn(View view) {
        String email = t1.getEditText().getText().toString();
        String password = t2.getEditText().getText().toString();
        bar.setVisibility(View.VISIBLE);

        mAuth = FirebaseAuth.getInstance();

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(AuthScreen.this,  new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    bar.setVisibility(View.INVISIBLE);
                    t1.getEditText().setText("");
                    t2.getEditText().setText("");
                    Toast.makeText(getApplicationContext(), "Logged In Successfully", Toast.LENGTH_LONG).show();
                    FirebaseUser user = mAuth.getCurrentUser();
//                    updateUI(user);
                    Intent myIntent = new Intent(AuthScreen.this, Dashboard.class);
                    myIntent.putExtra("email", user.getEmail());
                    myIntent.putExtra("uid", user.getUid());
                    startActivity(myIntent);
                    finish();

                } else {
                    bar.setVisibility(View.INVISIBLE);
                    t1.getEditText().setText("");
                    t2.getEditText().setText("");
                    Toast.makeText(getApplicationContext(), "Invalid Email or Password", Toast.LENGTH_LONG).show();
                }
            }
        });
    }






}