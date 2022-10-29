package com.example.a8you;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class Exercise extends AppCompatActivity {

    ImageView pushup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

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
//        Button meditationBtn = (Button) findViewById(R.id.meditationBtn);
//        Button stepsBtn = (Button) findViewById(R.id.StepsPageBtn);

//        exercise_Info info = new exercise_Info();
//        testBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent a = new Intent(Exercise.this, Home.class);
//                startActivity(a);
//            }
//        });

        ImageView pushup = findViewById(R.id.push_up);
        pushup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pushupView = new Intent(Exercise.this,exercise_Info.class);
                pushupView.putExtra("key","pushup");
                startActivity(pushupView);
            }
        });

        ImageView jumpingjacks = findViewById(R.id.jumpingjacks);
        jumpingjacks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent jumpingjacksView = new Intent(Exercise.this,exercise_Info.class);
                jumpingjacksView.putExtra("key","jumpingjacks");
                startActivity(jumpingjacksView);
            }
        });

        ImageView squat = findViewById(R.id.squat);
        squat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent squatview = new Intent(Exercise.this,exercise_Info.class);
                squatview.putExtra("key", "squat");
                startActivity(squatview);
            }
        });

        ImageView lunges = findViewById(R.id.lunges);
        lunges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent lungesView = new Intent(Exercise.this,exercise_Info.class);
                lungesView.putExtra("key","lunges");
                startActivity(lungesView);
            }
        });
    }
}