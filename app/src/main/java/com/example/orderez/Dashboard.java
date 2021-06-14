package com.example.orderez;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import org.w3c.dom.Text;

public class Dashboard extends AppCompatActivity {

    private FirebaseAuth mAuth;
    RecyclerView recyclerView;
    myadapter adapter;
    ProgressBar loading;
    TextView logo;
    Thread timer;
    FloatingActionButton fab;
    EditText inputSearch;
    DatabaseReference Dataref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        mAuth = FirebaseAuth.getInstance();
        logo = (TextView)findViewById(R.id.logo);
        Dataref = FirebaseDatabase.getInstance().getReference().child("users");
        inputSearch = (EditText)findViewById(R.id.inputSearch);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setHasFixedSize(true);
        fab = (FloatingActionButton)findViewById(R.id.fAdd);
        loading = (ProgressBar)findViewById(R.id.loading);


        Spannable wordtoSpan = new SpannableString("OrderEZ");
        wordtoSpan.setSpan(new ForegroundColorSpan(Color.rgb(245, 92, 71)), 5, 7, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        logo.setText(wordtoSpan);

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

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Dashboard.this, AddData.class);
                startActivity(myIntent);
            }
        });

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
        adapter.startListening();
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