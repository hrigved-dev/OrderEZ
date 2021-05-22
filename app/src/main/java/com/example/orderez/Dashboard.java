package com.example.orderez;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class Dashboard extends AppCompatActivity {

    private FirebaseAuth mAuth;
    RecyclerView recyclerView;
    myadapter adapter;
    ProgressBar loading;
    Thread timer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        mAuth = FirebaseAuth.getInstance();
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        loading = (ProgressBar)findViewById(R.id.loading);

        FirebaseRecyclerOptions<model> options =
                new FirebaseRecyclerOptions.Builder<model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("users"), model.class)
                        .build();

        adapter = new myadapter(options);
        recyclerView.setAdapter(adapter);
        loading.setVisibility(View.VISIBLE);

        timer = new Thread() {
            @Override
            public void run() {
                try {
                    synchronized (this) {
                        wait(25000);
                    }
                } catch(InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    loading.setVisibility(View.INVISIBLE);

                }
            }
        };
        timer.start();

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    public void signOut(View view) {
        mAuth.signOut();
        Toast.makeText(getApplicationContext(), "Logged Out Successfully", Toast.LENGTH_LONG).show();
        Intent myIntent = new Intent(Dashboard.this, AuthScreen.class);
        startActivity(myIntent);
        finish();
    }
}