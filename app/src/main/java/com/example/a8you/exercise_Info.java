package com.example.a8you;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class exercise_Info extends AppCompatActivity {

    ImageView gifImageView;
    TextView infoDetails;
    String type;
    Button backE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_info);

        backE =(Button) findViewById(R.id.backInfo);
        backE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backE = new Intent(exercise_Info.this, Exercise.class);
                startActivity(backE);
            }
        });

        // Initialize and assign variable
//        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);
//
//        // Set Home selected
//        bottomNavigationView.setSelectedItemId(R.id.home);
//
//        // Perform item selected listener
//        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//
//                switch(item.getItemId())
//                {
//                    case R.id.dashboard:
//                        startActivity(new Intent(getApplicationContext(),HistoryActivity.class));
//                        overridePendingTransition(0,0);
//                        return true;
//                    case R.id.home:
//                        return true;
//                    case R.id.about:
//                        startActivity(new Intent(getApplicationContext(),Profile.class));
//                        overridePendingTransition(0,0);
//                        return true;
//                }
//                return false;
//            }
//        });

        ImageView gifImageView = findViewById(R.id.gifImageView);
        TextView infoDetails = findViewById(R.id.info);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            type = extras.getString("key");
            infoSwitch();
        }

    }

    public void setType(String newType){
        type = newType;

    }
    public void infoSwitch(){
        ImageView gifImageView = findViewById(R.id.gifImageView);
        TextView infoTitle = findViewById(R.id.infoTitle);
        TextView infoDetails = findViewById(R.id.info);
        switch (type) {
            case "pushup":
                gifImageView.setImageResource(R.drawable.pushup);
                infoTitle.setText("Pushups");
                infoDetails.setText("1. Contract your abs and tighten your core by pulling your belly button toward your spine. " +
                        "\n2. Inhale as you slowly bend your elbows and lower yourself to the floor, until your elbows are at a 90-degree angle." +
                        "\n3. Exhale while contracting your chest muscles and pushing back up through your hands, returning to the start position. ");
                break;
            case "jumpingjacks":
                gifImageView.setImageResource(R.drawable.jumpingjacks);
                infoTitle.setText("Jumping");
                infoDetails.setText("1. Begin by standing with your legs straight and your arms to your sides." +
                        "\n2. Jump up and spread your feet beyond hip-width apart while bringing your arms above your head, nearly touching." +
                        "\n3. Jump again, lowering your arms and bringing your legs together. Return to your starting position.");
                break;
            case "squat":
                gifImageView.setImageResource(R.drawable.squat);
                infoTitle.setText("Squats");
                infoDetails.setText("1. Stand with the feet slightly wider than hip-width apart, angling the toes slightly outward." +
                        "\n2. Keep the hands down by the sides, with the palms facing in and keep the shoulders back." +
                        "\n3. Engage the abdominal muscles to support the back." +
                        "\n4. Shift the hips back and bend the knees as though taking a seat, keeping a flat back." +
                        "\n5. Keep lowering down to the ground until the thighs are parallel with the floor." +
                        "\n6. Push through the feet to straighten back up into the starting position." +
                        "\n7. Inhale into the squat, then exhale when standing back up.");
                break;
            case "lunges":
                gifImageView.setImageResource(R.drawable.lunges);
                infoTitle.setText("Lunges");
                infoDetails.setText("1.Stand upright with the feet together." +
                        "\n2. Step one leg forward into a long stride, bending the knee and placing the foot flat on the floor." +
                        "\n3. Bend the knee of the supporting leg toward the floor." +
                        "\n4. Use the muscles of the forward leg to push back to standing." +
                        "\n5. Repeat with the opposite leg.");
                break;
            default:
                infoDetails.setText("");
        }
    }
}