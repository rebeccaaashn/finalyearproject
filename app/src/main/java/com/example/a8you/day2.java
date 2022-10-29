package com.example.a8you;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class day2 extends AppCompatActivity {

    Button backD2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.day2);

        backD2 =(Button) findViewById(R.id.backD2);
        backD2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backD2 = new Intent(day2.this, Diet.class);
                startActivity(backD2);
            }
        });
    }
}