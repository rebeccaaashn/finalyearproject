package com.example.a8you;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ViewFlipper;

public class mainMeditate extends AppCompatActivity implements View.OnClickListener {

    ViewFlipper viewFlipper;
    Button next;
    Button previous;
    FrameLayout song;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_meditate);

        viewFlipper = (ViewFlipper)findViewById(R.id.viewFlipper);
        next = (Button) findViewById(R.id.next);
        previous = (Button) findViewById(R.id.previous);

        next.setOnClickListener(this);
        previous.setOnClickListener(this);
    }

    public void onClick(View v) {
        if (v == next) {
            viewFlipper.showNext();
        }
        else if (v == previous) {
            viewFlipper.showPrevious();
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
//    ImageView pushup = findViewById(R.id.push_up);
//        pushup.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//            Intent pushupView = new Intent(Exercise.this,exercise_Info.class);
//            pushupView.putExtra("key","pushup");
//            startActivity(pushupView);
//        }
//    });
//    son = (ImageButton) findViewById(R.id.diet);
//        dietBtn.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//            Intent d = new Intent(Home.this, Diet.class);
//            startActivity(d);
//        }
//    });


}