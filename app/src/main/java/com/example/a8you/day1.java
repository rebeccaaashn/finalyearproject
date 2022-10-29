package com.example.a8you;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

public class day1 extends AppCompatActivity {

    Button backD1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.day1);

        backD1 =(Button) findViewById(R.id.backD1);
        backD1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backD1 = new Intent(day1.this, Diet.class);
                startActivity(backD1);
            }
        });

    }


}