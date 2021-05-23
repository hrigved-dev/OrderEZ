package com.example.orderez;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class Dashboard extends AppCompatActivity {

    private FirebaseAuth mAuth;
    RecyclerView recyclerView;
    myadapter adapter;
    ProgressBar loading;
    Thread timer;
    EditText inputSearch;
    DatabaseReference Dataref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        mAuth = FirebaseAuth.getInstance();
        Dataref = FirebaseDatabase.getInstance().getReference().child("users");
        inputSearch = (EditText)findViewById(R.id.inputSearch);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setHasFixedSize(true);
        loading = (ProgressBar)findViewById(R.id.loading);

        LoadData("");
        inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString() != null) {
                    LoadData(s.toString());
                }
                else {
                    LoadData("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString() != null) {
                    LoadData(s.toString());
                }
                else {
                    LoadData("");
                }
            }
        });


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
                    loading.setVisibility(View.INVISIBLE);

                }
            }
        };
        timer.start();

    }

    private void LoadData(String s) {
        Query query = Dataref.orderByChild("name").startAt(s).endAt(s+"\uf8ff");
        FirebaseRecyclerOptions<model> options =
                new FirebaseRecyclerOptions.Builder<model>()
                        .setQuery(query, model.class)
                        .build();

        adapter = new myadapter(options);
        recyclerView.setAdapter(adapter);
        loading.setVisibility(View.VISIBLE);
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