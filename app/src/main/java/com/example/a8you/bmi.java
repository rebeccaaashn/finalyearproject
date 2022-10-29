package com.example.a8you;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.example.a8you.BMICalcUtil.BMI_CATEGORY_HEALTHY;
import static com.example.a8you.BMICalcUtil.BMI_CATEGORY_OBESE;
import static com.example.a8you.BMICalcUtil.BMI_CATEGORY_OVERWEIGHT;
import static com.example.a8you.BMICalcUtil.BMI_CATEGORY_UNDERWEIGHT;

public class bmi extends AppCompatActivity {

    private EditText weightKgEditText, heightCmEditText;
    private EditText weightLbsEditText, heightFtEditText, heightInEditText;
    private Button calculateButton, bckBMI;
    private TextView bmiTextView, categoryTextView;
    private ToggleButton toggleUnitsButton;
    private CardView bmiResultCardView;
    private RadioButton maleBn, femaleBtn;
    User.genderEnum gender;
    private RadioGroup radiogroup;

    private boolean inMetricUnits;
    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        bckBMI =(Button) findViewById(R.id.backBmi);
        bckBMI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backB = new Intent(bmi.this, Profile.class);
                startActivity(backB);
            }
        });
        maleBn = findViewById(R.id.maleBtn);
        femaleBtn=findViewById(R.id.femaleBtn);
//        member= new Member()

        weightKgEditText = findViewById(R.id.activity_main_weightkgs);
        heightCmEditText = findViewById(R.id.activity_main_heightcm);

        weightLbsEditText = findViewById(R.id.activity_main_weightlbs);
        heightFtEditText = findViewById(R.id.activity_main_heightfeet);
        heightInEditText = findViewById(R.id.activity_main_heightinches);

        calculateButton = findViewById(R.id.activity_main_calculate);
        toggleUnitsButton = findViewById(R.id.activity_main_toggleunits);

        bmiTextView = findViewById(R.id.activity_main_bmi);
        categoryTextView = findViewById(R.id.activity_main_category);
        bmiResultCardView = findViewById(R.id.activity_main_resultcard);

        inMetricUnits = true;
        updateInputsVisibility();
        bmiResultCardView.setVisibility(View.GONE);

//        radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup radioGroup, int i) {
//                RadioButton radioButton = findViewById(i);
//                Toast.makeText(bmi.this, "selected gender" + radioButton.getText(), Toast.LENGTH_SHORT).show();
//            }
//        });



        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (inMetricUnits) {
                    if (weightKgEditText.length() == 0 || heightCmEditText.length() == 0) {
                        Toast.makeText(bmi.this, "Populate Weight and Height to Calculate BMI", Toast.LENGTH_SHORT).show();
                    } else {
                        double heightInCms = Double.parseDouble(heightCmEditText.getText().toString());
                        double weightInKgs = Double.parseDouble(weightKgEditText.getText().toString());
                        double bmi = BMICalcUtil.getInstance().calculateBMIMetric(heightInCms, weightInKgs);
                        displayBMI(bmi);
                    }
                } else {
                    if (weightLbsEditText.length() == 0 || heightFtEditText.length() == 0 || heightInEditText.length() == 0) {
                        Toast.makeText(bmi.this, "Populate Weight and Height to Calculate BMI", Toast.LENGTH_SHORT).show();
                    } else {
                        double heightFeet = Double.parseDouble(heightFtEditText.getText().toString());
                        double heightInches = Double.parseDouble(heightInEditText.getText().toString());
                        double weightLbs = Double.parseDouble(weightLbsEditText.getText().toString());
                        double bmi = BMICalcUtil.getInstance().calculateBMIImperial(heightFeet, heightInches, weightLbs);
                        displayBMI(bmi);
                    }
                }
//                mDatabase.child(FirebaseAuth.getInstance().getCurrentUser().getUid());
            }

        });

        toggleUnitsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inMetricUnits = !inMetricUnits;
                updateInputsVisibility();
            }
        });
    }

    private void updateInputsVisibility() {
        if (inMetricUnits) {
            heightCmEditText.setVisibility(View.VISIBLE);
            weightKgEditText.setVisibility(View.VISIBLE);
            heightFtEditText.setVisibility(View.GONE);
            heightInEditText.setVisibility(View.GONE);
            weightLbsEditText.setVisibility(View.GONE);
        } else {
            heightCmEditText.setVisibility(View.GONE);
            weightKgEditText.setVisibility(View.GONE);
            heightFtEditText.setVisibility(View.VISIBLE);
            heightInEditText.setVisibility(View.VISIBLE);
            weightLbsEditText.setVisibility(View.VISIBLE);
        }
    }

    private void displayBMI(double bmi) {
        bmiResultCardView.setVisibility(View.VISIBLE);

        bmiTextView.setText(String.format("%.2f", bmi));

        String bmiCategory = BMICalcUtil.getInstance().classifyBMI(bmi);
        categoryTextView.setText(bmiCategory);

        switch (bmiCategory) {
            case BMI_CATEGORY_UNDERWEIGHT:
                bmiResultCardView.setCardBackgroundColor(Color.YELLOW);
                break;
            case BMI_CATEGORY_HEALTHY:
                bmiResultCardView.setCardBackgroundColor(Color.GREEN);
                break;
            case BMI_CATEGORY_OBESE:
                bmiResultCardView.setCardBackgroundColor(Color.RED);
                break;
        }
    }
}