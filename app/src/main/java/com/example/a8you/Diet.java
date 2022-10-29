package com.example.a8you;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Diet extends AppCompatActivity {

    FrameLayout day1, day2, mealBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet);

        day1=(FrameLayout) findViewById(R.id.day1);
        day1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent day1 = new Intent(Diet.this,day1.class);
                startActivity(day1);
            }
        });

//        breakfastt =(FrameLayout) findViewById(R.id.breakfast);
//        breakfastt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent breakfast = new Intent(Diet.this, breakfast.class);
//            }
//        });

        day2 = (FrameLayout) findViewById(R.id.day2);
        day2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent day2 = new Intent(Diet.this, day2.class);
                startActivity(day2);
            }
        });

        mealBtn = (FrameLayout) findViewById(R.id.meal);
        mealBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent meals = new Intent(Diet.this, resepi1.class);
                startActivity(meals);
            }
        });



        // Initialize and assign variable
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);

        // Set Home selected
        //bottomNavigationView.setSelectedItemId(R.id.dashboard);

        // Perform item selected listener
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener(){

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId())
                {
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(),Home.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.dashboard:
                        startActivity(new Intent(getApplicationContext(),HistoryActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.about:
                        startActivity(new Intent(getApplicationContext(),Profile.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

//        Button testBtn = (Button)findViewById(R.id.testBtn);
//
//        testBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent a = new Intent(Diet.this, Home.class);
//                startActivity(a);
//            }
//        });
    }
}