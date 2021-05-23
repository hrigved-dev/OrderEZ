package com.example.orderez;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class AddData extends AppCompatActivity {

    ImageView back;
    TextView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data);

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
    }
}