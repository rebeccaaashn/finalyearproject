package com.example.a8you;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Html;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.time.LocalTime;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


public class Home extends AppCompatActivity {

    private FirebaseUser user;
    private DatabaseReference reference;


    private String userID;

    ImageButton exersiceBtn, dietBtn, mediBtn, trophyBtn, gameBtn, scanBtn;
    TextView tv_steps;
    SensorManager sensorManager;

    ImageView imageView;
    Button captureB;

    boolean running = false;
    int dayCount = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

//        imageView = findViewById(R.id.imageview);
//        captureB = findViewById(R.id.captureButton);


        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        final TextView greetingTextView = (TextView) findViewById(R.id.greeting1);
        final TextView steptv = (TextView) findViewById(R.id.step1);
        final TextView timetv = (TextView) findViewById(R.id.time1);
        try {
            dayCount = fitsensor.getInstance().getDayCount();
        } catch (Exception e) {
//            Toast.makeText(Trophy.this, e + "" , Toast.LENGTH_SHORT).show();
            System.out.println("Error" + e);
        }
        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);

                if (userProfile != null){
                    String fullName = userProfile.fullname;

                    greetingTextView.setText("Welcome, " + fullName + "!");

                }

            }



            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Home.this, "Something wrong happened!", Toast.LENGTH_SHORT).show();
            }
        });

        reference.child(userID).child("Exercise").child("Day"+dayCount).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User2 test = snapshot.getValue(User2.class);

                if (test != null){
                    String steps = test.step;
                    String times = test.time;

                    steptv.setText(steps);
                    timetv.setText(times);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
//        captureB.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                startActivityForResult(intent, 100);
//            }
//        });
//        //request for camera runtime
//        if ((ContextCompat.checkSelfPermission(Home.this, Manifest.permission.CAMERA)
//                != PackageManager.PERMISSION_GRANTED)) {
//            ActivityCompat.requestPermissions(Home.this, new String[]{
//                    Manifest.permission.CAMERA
//            }, 100);
//        }

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


        exersiceBtn = (ImageButton) findViewById(R.id.exercise);
        exersiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent e = new Intent(Home.this, Exercise.class);
                startActivity(e);
            }
        });

        mediBtn = (ImageButton) findViewById(R.id.medi);
        mediBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent m = new Intent(Home.this,  Meditation.class);
                startActivity(m);
            }
        });

        dietBtn = (ImageButton) findViewById(R.id.diet);
        dietBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent d = new Intent(Home.this, Diet.class);
                startActivity(d);
            }
        });
        gameBtn = (ImageButton) findViewById(R.id.game);
        gameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent game = new Intent(Home.this, fitsensor.class);
                startActivity(game);
            }
        });

        scanBtn = (ImageButton) findViewById(R.id.scan);
        scanBtn.setOnClickListener(view ->
        {
            scanCode();
        });

        trophyBtn = (ImageButton) findViewById(R.id.trophy);
        trophyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent t = new Intent(Home.this, Trophy.class);
                startActivity(t);
            }
        });

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

    }

//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
//        super.onActivityResult(requestCode,resultCode,data);
//        if(requestCode ==100){
//            {
//                Bitmap bitmap = (Bitmap)data.getExtras().get("data");
//                imageView.setImageBitmap(bitmap);
////                BitmapFactory.Options bmOptions = new BitmapFactory.Options();
////                Bitmap bitmap = BitmapFactory.decodeFile(picturePath,bmOptions);
////                imageView.setImageBitmap(bitmap);
//            }
//        }
//    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        running = true;
//        Sensor countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
//        if (countSensor != null){
//            sensorManager.registerListener(this, countSensor, SensorManager.SENSOR_DELAY_UI);
//        }
//        else{
//            Toast.makeText(this, "Sensor not found!", Toast.LENGTH_SHORT).show();
//        }
//
//    }
//
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        running = false;
//    }
//
//    @Override
//    public void onSensorChanged(SensorEvent sensorEvent) {
//        if (running) {
//            tv_steps.setText(String.valueOf(sensorEvent.values[0]));
//        }
//        else if (running){
//            tv_steps.setText(String.valueOf(sensorEvent.values[1000]));
//            Toast.makeText(this, "congratulation! you have reach 1000 steps", Toast.LENGTH_SHORT).show();
//        }
//        else if (LocalTime.now() == LocalTime.MIDNIGHT){
//            tv_steps.setText(String.valueOf(sensorEvent.values[0] + ""));
//        }
//        else if (LocalTime.now() == LocalTime.NOON){
//            tv_steps.setText(String.valueOf(sensorEvent.values[0] + ""));
//        }
//
//    }


    public void onAccuracyChanged(Sensor sensor, int i) {

    }
    private void scanCode() {

        ScanOptions options= new ScanOptions();
        options.setPrompt(" Volume up to flash on");
        options.setBeepEnabled(true);
        options.setOrientationLocked(true);
        options.setCaptureActivity(CaptureAct.class);
        barLauncher.launch(options);
    }
    ActivityResultLauncher<ScanOptions> barLauncher = registerForActivityResult(new ScanContract(), result ->
            {
                if (result.getContents() !=null)
                {

                    // Linkify the message
                    final SpannableString s = new SpannableString(result.getContents()); // msg should have url to enable clicking
                    Linkify.addLinks(s, Linkify.ALL);

                    final AlertDialog d = new AlertDialog.Builder(Home.this)
                            .setTitle("click here to get your treat! ")
                            .setPositiveButton(android.R.string.ok, null)
                            .setIcon(R.drawable.gift)
                            .setMessage( s )
                            .create();

                    d.show();
                    // Make the textview clickable. Must be called after show()
                    ((TextView)d.findViewById(android.R.id.message)).setMovementMethod(LinkMovementMethod.getInstance());

                }
            }
    );

}