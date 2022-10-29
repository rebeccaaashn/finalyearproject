package com.example.a8you;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class cal extends AppCompatActivity implements View.OnClickListener {
    EditText ageInput;
    EditText heightInput;
    EditText weightInput;

    Button cutButton;
    Button maintButton;
    Button bulkButton;
    Button bckcal;

    TextView displayValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cal);

        // Find elements from the layout and Define them
        ageInput = (EditText) findViewById(R.id.ageInput);
        heightInput = (EditText) findViewById(R.id.heightInput);
        weightInput = (EditText) findViewById(R.id.weightInput);

        cutButton = (Button) findViewById(R.id.cutButton);
        maintButton = (Button) findViewById(R.id.maintButton);
        bulkButton = (Button) findViewById(R.id.bulkButton);

        displayValue = (TextView) findViewById(R.id.displayValue);

        //Set button listeners
        cutButton.setOnClickListener(this);
        maintButton.setOnClickListener(this);
        bulkButton.setOnClickListener(this);


        bckcal =(Button) findViewById(R.id.bckCal);
        bckcal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backB = new Intent(cal.this, Profile.class);
                startActivity(backB);
            }
        });
    }

    public void onClick(View v) {
        // TODO Auto-generated method stub
        int ageNum = 0;
        int weightNum = 0;
        int heightNum = 0;
        int result = 0;
        int calculate = 0;
        int percentAdd = 0;

        // Test to see if fields are empty
        if (TextUtils.isEmpty(ageInput.getText().toString())
                || TextUtils.isEmpty(heightInput.getText().toString())
                || TextUtils.isEmpty(weightInput.getText().toString())) {
            return;
        }

        // This is to read the Edit Text fields from the layout and fill the Variables with Numbers
        ageNum =  Integer.parseInt(ageInput.getText().toString());
        weightNum = Integer.parseInt(weightInput.getText().toString());
        heightNum = Integer.parseInt(heightInput.getText().toString());

        //Defines which button has been clicked and will calculate accordingly
        switch (v.getId()) {
            case R.id.cutButton:
                calculate = (6*weightNum) + (12*heightNum) - (5*ageNum) + 5;
                percentAdd =  (calculate*1);
                result = calculate + percentAdd;
                displayValue.setText("Lose " + result + " calories");
                break;
            case R.id.maintButton:
                calculate = (10*weightNum) + (6*heightNum) - (5*ageNum) + 5;
                percentAdd =  (calculate*1);
                result = calculate + percentAdd;
                displayValue.setText("Must maintain " + result + " calories");

                break;
            case R.id.bulkButton:
                calculate = (15*weightNum) + (15*heightNum) - (5*ageNum) + 5;
                percentAdd = (calculate*1);
                result = calculate + percentAdd;
                displayValue.setText("Must gain " + result + " calories");

                break;
            default:
                break;
        }

        //Displays the output
//        displayValue.setText(String.valueOf(result));
    }
}