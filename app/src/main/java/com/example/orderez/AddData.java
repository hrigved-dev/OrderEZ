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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class AddData extends AppCompatActivity {

    ImageView back;
    TextView logo;
    TextInputLayout addName, addOrder, addExtra;
    Button addData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data);

        addName = (TextInputLayout) findViewById(R.id.nameText);
        addOrder = (TextInputLayout) findViewById(R.id.orderText);
        addExtra = (TextInputLayout) findViewById(R.id.extraText);

        addData = (Button) findViewById(R.id.addData);

        back = (ImageView)findViewById(R.id.back);

        logo = (TextView)findViewById(R.id.logo);
        Spannable wordtoSpan = new SpannableString("OrderEZ");
        wordtoSpan.setSpan(new ForegroundColorSpan(Color.rgb(245, 92, 71)), 5, 7, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        logo.setText(wordtoSpan);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(AddData.this, Dashboard.class);
                startActivity(myIntent);
                finish();
            }
        });

        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processInsert();
            }
        });
    }

    private void processInsert() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", addName.getEditText().getText().toString());
        map.put("order", addOrder.getEditText().getText().toString());
        map.put("extra", addExtra.getEditText().getText().toString());

        FirebaseDatabase.getInstance().getReference().child("users").push().setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getApplicationContext(), "Added your Order Successfully", Toast.LENGTH_LONG).show();
                Intent myIntent = new Intent(AddData.this, Dashboard.class);
                startActivity(myIntent);
                finish();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Sorry,your Order was not inserted", Toast.LENGTH_LONG).show();
            }
        });
    }
}