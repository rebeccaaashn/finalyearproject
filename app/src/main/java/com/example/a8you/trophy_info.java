package com.example.a8you;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Objects;

public class trophy_info extends AppCompatActivity {

    ImageView currentBadge;
    public String achievedString;
    TextView infoDetails, status, badgeName;
    EditText shareText;
    Button share, backBtn;
    Boolean achieved;
    String badgeType, infoString;
    String newMoveString = "Can only be obtained once start moving!";
    String newGoal5String = "Can only be obtained once reached 50 steps";
    String newGoal10String = "Can only be obtained once reached 100 steps";
    String newGoal20String = "Can only be obtained once reached 200 steps";
    private Uri getImageToShare(Bitmap image, Context context) {
        File imagefolder = new File(context.getCacheDir(), "images");
        Uri uri = null;
        try {
            imagefolder.mkdirs();
            File file = new File(imagefolder, "shared_images.png");

            FileOutputStream outputStream = new FileOutputStream(file);
            image.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            outputStream.flush();
            outputStream.close();
            uri = FileProvider.getUriForFile(Objects.requireNonNull(context.getApplicationContext()) ,  "com.example.badgetest" + ".provider",file);

//            uri = FileProvider.getUriForFile(this, "${applicationId}.provider", file);
        } catch (Exception e) {
            Toast.makeText(this, "" + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        return uri;
    }


    //    BitmapDrawable bitmapDrawable;
//    Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trophy_info);

        badgeName = findViewById(R.id.badgeName);
        currentBadge = findViewById(R.id.currentBadge);
        infoDetails = findViewById(R.id.badgeDetail);
        share = findViewById(R.id.shareBadge);
        status = findViewById(R.id.status);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            badgeType = extras.getString("badge");
            achieved = extras.getBoolean("achieved");
            badgeSwitch(achieved);
        }

        if (achieved == true){
            status.setText("Achieved");
        } else {
            status.setText("Not Achieved");
        }

//        badgeName.setText(badgeType + " Badge");

        BitmapDrawable bitmapDrawable = (BitmapDrawable) currentBadge.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();
//  Sharing Badge
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                share(badgeType, achieved, bitmap);
            }
        });
// Back to Badge
        backBtn = findViewById(R.id.backThrophy);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backToBadge = new Intent(trophy_info.this, Trophy.class);
                startActivity(backToBadge);
            }
        });
    }

    private void share(String badgeType, boolean achieved, Bitmap bitmap){
//        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
//        StrictMode.setVmPolicy(builder.build());

//        Uri uri = getImageToShare(bitmap);
        Intent intent = new Intent(Intent.ACTION_SEND);

        // setting type to image
//        intent.setType("image/*");
        intent.setType("text/*");

//        Uri uri = getImageToShare(bitmap, getApplicationContext());

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // putting uri of image to be shared
//        intent.putExtra(Intent.EXTRA_STREAM, uri);

        // adding texts to share
        if (achieved){
            achievedString= " achieved";
        } else {
            achievedString= " not achieved";
        }

        String shareMessageAchieved = "The badge " + badgeType + " has been shared &" + achievedString;
        intent.putExtra(Intent.EXTRA_TEXT, shareMessageAchieved);

        // Add subject Here
        intent.putExtra(Intent.EXTRA_SUBJECT, "8You application is sharing:");


        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);


        // calling startactivity() to share
        startActivity(Intent.createChooser(intent, "Share Via"));
    }

    // Change Image & Info Details
    public void badgeSwitch(boolean newAchieved){
        ImageView currentBadge = findViewById(R.id.currentBadge);
        TextView infoDetails = findViewById(R.id.badgeDetail);
        badgeName = findViewById(R.id.badgeName);

        switch (badgeType) {
            case "newMove":
                if (newAchieved ) {
                    currentBadge.setImageResource(R.drawable.newmove);
                } else {
                    currentBadge.setImageResource(R.drawable.noclnewmove);
                }
                badgeName.setText("New Move Badge");
                infoString = newMoveString;
                break;
            case "goal5":
                if (newAchieved ) {
                    currentBadge.setImageResource(R.drawable.movegoal5);
                } else {
                    currentBadge.setImageResource(R.drawable.noclmovegoal5);
                }
                badgeName.setText("5% Goal Badge Badge");
                infoString = newGoal5String;
                break;
            case "goal10":
                if (newAchieved ) {
                    currentBadge.setImageResource(R.drawable.movegoal10);
                } else {
                    currentBadge.setImageResource(R.drawable.noclmovegoal30);

                }
                badgeName.setText("10% Goal Badge");
                infoString = newGoal10String;
                break;
            case "goal20":
                if (newAchieved ) {
                    currentBadge.setImageResource(R.drawable.movegoal20);
                } else {
                    currentBadge.setImageResource(R.drawable.noclmovegoal20);
                }
                badgeName.setText("20% Goal Badge");
                infoString = newGoal20String;
            default:

        }
        infoDetails.setText(infoString);

    }


//        Intent shareIntent = new Intent(Intent.ACTION_SEND);
//
//        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//        icon.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
//        File f = new File(Environment.getExternalStorageDirectory() + File.separator + "temporary_file.jpg");
//        try {
//            f.createNewFile();
//            FileOutputStream fo = new FileOutputStream(f);
//            fo.write(bytes.toByteArray());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

//        shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file:///sdcard/temporary_file.jpg"));
//        shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("/storage/emulated/0/Pictures/logo.png"));
//        startActivity(Intent.createChooser(shareIntent, "Share Image"));

//        shareIntent.setType("Share!");
//        startActivity(shareIntent);
}