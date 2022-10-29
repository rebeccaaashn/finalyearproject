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


public class Trophy extends AppCompatActivity {
    public static badge newMoveBadge = new badge("newMove",false);

    static Trophy INSTANCE;
    private static int steps;
    public static badge goal5Badge = new badge("goal5",false);
    int newMoveSteps = 0;
    public static badge goal10Badge = new badge("goal10",false);
    int perc10Steps = 100;
    int perc20Steps = 200;

    private Button BackBtn, testButton;
    private TextView testSteps;
    public static badge goal20Badge = new badge("goal20",false);
    private static int totalSteps = 0;
    int perc5Steps = 50;
    private ImageView goal10IMG ,goal20IMG, newmoveIMG, goal5IMG;

    public static Trophy getInstance(){
        return INSTANCE;
    }

    public badge getGoal5Badge(){
        return this.goal5Badge;
    }
    public badge getNewMoveBadge(){
        return this.newMoveBadge;
    }

    public badge getGoal10Badge(){
        return this.goal10Badge;
    }

    public badge getGoal20Badge(){
        return this.goal20Badge;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trophy);
        INSTANCE=this;

        // Initialize and assign variable
        goal10IMG = (ImageView) findViewById(R.id.goal10);
        goal20IMG = (ImageView) findViewById(R.id.goal20);
        newmoveIMG = (ImageView) findViewById(R.id.newmove);
        goal5IMG = (ImageView) findViewById(R.id.goal5);

/*
        Without this it will crash due to retrieving null value
*/
        try {
            steps = fitsensor.getInstance().getSavedStepCount();
            totalSteps = totalSteps + steps;
        } catch (Exception e) {
//            Toast.makeText(Trophy.this, e + "" , Toast.LENGTH_SHORT).show();
            System.out.println("Error" + e);
        }


/*
        Testing by setting textView into retrieved stepsCount
*/

        /*testSteps = (TextView) findViewById(R.id.testSteps);
        String str = Integer.toString(steps);
        testSteps.setText(str);*/

        checkBadgeWithSteps(steps);
        checkBadge();

        // Initialize and assign variable
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);

        // Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.home);

        // Perform item selected listener
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId())
                {
                    case R.id.dashboard:
                        startActivity(new Intent(getApplicationContext(),HistoryActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.home:
                        return true;
                    case R.id.about:
                        startActivity(new Intent(getApplicationContext(),Profile.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        newmoveIMG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newMoveIntent = new Intent(Trophy.this, trophy_info.class);
                newMoveIntent.putExtra("badge", "newMove");
                newMoveIntent.putExtra("achieved", newMoveBadge.achieved);
                startActivity(newMoveIntent);
            }
        });

        goal5IMG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newgoal5Intent = new Intent(Trophy.this, trophy_info.class);
                newgoal5Intent.putExtra("badge", "goal5");
                newgoal5Intent.putExtra("achieved", goal5Badge.achieved);
                startActivity(newgoal5Intent);
            }
        });

        goal10IMG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newgoal10Intent = new Intent(Trophy.this, trophy_info.class);
                newgoal10Intent.putExtra("badge", "goal10");
                newgoal10Intent.putExtra("achieved", goal10Badge.achieved);
                startActivity(newgoal10Intent);
            }
        });

        goal20IMG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newgoal20Intent = new Intent(Trophy.this, trophy_info.class);
                newgoal20Intent.putExtra("badge", "goal20");
                newgoal20Intent.putExtra("achieved", goal20Badge.achieved);
                startActivity(newgoal20Intent);
            }
        });

/*        BackBtn = findViewById(R.id.BackBtnBadge);
        BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back = new Intent(Trophy.this, Home.class);
                startActivity(back);
            }
        });*/

/*
        testButton = findViewById(R.id.testButton);
        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                addDataForBadge(9);
            }
        });
*/

    }

    private void checkBadge() {
        if (newMoveBadge.achieved){
            newmoveIMG.setImageResource(R.drawable.newmove);
        }
        if (goal5Badge.achieved){
            goal5IMG.setImageResource(R.drawable.movegoal5);
        }
        if (goal10Badge.achieved){
            goal10IMG.setImageResource(R.drawable.movegoal10);
        }
        if (goal20Badge.achieved) {
            goal20IMG.setImageResource(R.drawable.movegoal20);
        }
    }

    public boolean checkBadgeWithSteps(int steps){
/*        user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();
        reference =
                FirebaseDatabase.getInstance().getReference("Badges")
                        .child(userID)
//                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .child("badge");*/

        if (steps > newMoveSteps && newMoveBadge.achieved == false) {
            newMoveBadge.achieved = true;
            newmoveIMG.setImageResource(R.drawable.newmove);
        }

        if (steps >= perc5Steps && goal5Badge.achieved == false) {
            goal5Badge.achieved = true;
            goal5IMG.setImageResource(R.drawable.movegoal5);
        }
        if (steps >= perc10Steps && goal10Badge.achieved == false) {
            goal10Badge.achieved = true;
            goal10IMG.setImageResource(R.drawable.movegoal10);
        }

        if (steps >= perc20Steps && goal20Badge.achieved == false) {
            goal20Badge.achieved = true;
            goal20IMG.setImageResource(R.drawable.movegoal20);
        }
        return false;
    }


//    String data="FirstActivity";


//    public static badge newMoveBadge, goal10Badge, goal20Badge;



/*
    private void addDataForBadge(int currentStepCount ) {
        reference =
                FirebaseDatabase.getInstance().getReference("Users")
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .child("badge");
//        if (currentStepCount > 0 && newMoveBool == false) {
            badge newMoveObj = new badge("newMove",true);
//            User user = new User(newMoveObj);
        reference.child("newMove").setValue(user);
           */
/* FirebaseDatabase.getInstance().getReference("Users")
                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .child("badge")
                    .child("newMove")
                    .setValue(true)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
//                            Toast.makeText(MainActivity.this, "A has been achieved ", Toast.LENGTH_SHORT).show();
                                //redirect to login
                            } else {
//                            Toast.makeText(MainActivity.this, "Failed to add badge data!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });*//*
//            newMoveObj.
//            addingBadgeObj(newMoveObj);
//        }
        if (currentStepCount >= 10 && goal10Bool == false){
            goal10Bool = true;
//            badge goal10Obj = new badge(true);
        }
    }
    public void addingBadgeObj (badge currentbadgetoadd){
        FirebaseDatabase.getInstance().getReference("Users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("Badge")
                .setValue(currentbadgetoadd).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
//                            Toast.makeText(MainActivity.this, "A has been achieved ", Toast.LENGTH_SHORT).show();
                            //redirect to login
                        } else {
//                            Toast.makeText(MainActivity.this, "Failed to add badge data!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
*/

    /*private void badgeCheck(String message){
        switch (message) {
            case "10" :
//                badge  badge = new badge("10steps",true);
                goal10.setImageResource(R.drawable.movegoal10);
                b10 = true;
                break;
            case "20" :
//                badge badge20 = new badge("20steps",true);
                goal20.setImageResource(R.drawable.movegoal20);
//                mDatabase.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
//                        .child("badge").setValue(badge20);
                break;
            case "1" :
//                badge newmove1 = new badge("newmove",true);
                newmove.setImageResource(R.drawable.newmove);
//                mDatabase.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
//                        .child("badge").setValue(newmove1);
                break;
            case "perweek" :
//                badge perweek = new badge("perweek",true);
                newmove.setImageResource(R.drawable.perfectweek);
                break;
            default:
        }
    }*/
}