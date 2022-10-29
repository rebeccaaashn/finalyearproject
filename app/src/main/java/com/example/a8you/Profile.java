package com.example.a8you;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a8you.Config.payment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class Profile extends AppCompatActivity {

    // views for button
    private Button btnSelect, btnUpload;
    //    // view for image view
    private ImageView imageView;
    //
    // Uri indicates, where the image will be picked from
    private Uri filePath;
    //
//    // request code
    private final int PICK_IMAGE_REQUEST = 22;
//

    private StorageReference storageReference;
    FirebaseStorage storage;



    public Button editButton, subs, bmiBtn,  logout,calKal;
    public TextView username, weight,age, height, gender, bmi,nameview;
    private FirebaseUser user;
    private DatabaseReference reference;

    private String userID;
//    User.genderEnum genderData;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        btnSelect = findViewById(R.id.BChoose);
        btnUpload = findViewById(R.id.BUpload);
        imageView = findViewById(R.id.imgView);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

//        final TextView greetingTextView = (TextView) findViewById(R.id.greeting);
        final TextView fullNameTextView = (TextView) findViewById(R.id.fullName1);
        final TextView emailTextView = (TextView) findViewById(R.id.emailAddress1);
        final TextView ageTextView = (TextView) findViewById(R.id.age1);
        final TextView  weightTextV= (TextView) findViewById(R.id.weight1);
        final TextView heightTextV = (TextView) findViewById(R.id.height1);

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);

                if (userProfile != null){
                    String fullName = userProfile.fullname;
                    String email = userProfile.email;
                    String age = userProfile.age;
                   String weight =userProfile.weight;
                    String height = userProfile.height;

                    fullNameTextView.setText(fullName);
                    emailTextView.setText(email);
                    ageTextView.setText(age);
                    weightTextV.setText(weight);
                    heightTextV.setText(height);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Profile.this, "Something wrong happened!", Toast.LENGTH_SHORT).show();
            }
        });

        StorageReference profileRef;
        profileRef = storageReference.child(user.getUid()).child("images/" + /*UUID.randomUUID().toString()*/user.getUid());
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(imageView);
            }
        });

//        initViews();

//        setDetails();
        // Initialize and assign variable
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);

        // Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.about);

        // Perform item selected listener
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.dashboard:
                        startActivity(new Intent(getApplicationContext(), HistoryActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.about:
                        return true;
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), Home.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }

        });
        subs = (Button) findViewById(R.id.subs);
        subs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent subs = new Intent(Profile.this, payment.class);
                startActivity(subs);
            }
        });

        editButton = (Button) findViewById(R.id.goToEdit);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent edit = new Intent(Profile.this, profileEdit.class);
                startActivity(edit);
            }
        });

        bmiBtn = (Button) findViewById(R.id.calcBMI);
        bmiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent bmi = new Intent(Profile.this, bmi.class);
                startActivity(bmi);
            }
        });

        calKal = (Button) findViewById(R.id.calcKal);
        calKal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent kal = new Intent(Profile.this, cal.class);
                startActivity(kal);
            }
        });

        logout =(Button) findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(Profile.this, MainActivity.class));
            }
        });


// on pressing btnSelect SelectImage() is called
        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectImage();
            }
        });
        //        // on pressing btnUpload uploadImage() is called
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            } });
    }
    private void SelectImage()
    {
        // Defining Implicit Intent to mobile gallery
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(
                        intent,
                        "Select Image from here..."),
                PICK_IMAGE_REQUEST);}

    // Override onActivityResult method
    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    Intent data)
    {
        super.onActivityResult(requestCode,
                resultCode,
                data);

        // checking request code and result code
        // if request code is PICK_IMAGE_REQUEST and
        // resultCode is RESULT_OK
        // then set image in the image view
        if (requestCode == PICK_IMAGE_REQUEST
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null) {

            // Get the Uri of data
            filePath = data.getData();
            try {

                // Setting image on image view using Bitmap
                Bitmap bitmap = MediaStore
                        .Images
                        .Media
                        .getBitmap(
                                getContentResolver(),
                                filePath);
                imageView.setImageBitmap(bitmap);
            }

            catch (IOException e) {
                // Log the exception
                e.printStackTrace();
            }}}

    private void uploadImage()
    {
        if (filePath != null) {

            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

            // Code for showing progressDialog while uploading
            ProgressDialog progressDialog
                    = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            // Defining the child of storageReference
            StorageReference ref = storageReference
                    .child(user.getUid())
                    .child(
                            "images/"
                                    +  user.getUid());
//+ getFileExtension(filePath)
            // adding listeners on upload
            // or failure of image
            ref.putFile(filePath)
                    .addOnSuccessListener(
                            new OnSuccessListener<UploadTask.TaskSnapshot>() {

                                @Override
                                public void onSuccess(
                                        UploadTask.TaskSnapshot taskSnapshot) {

                                    // Image uploaded successfully
                                    // Dismiss dialog
                                    progressDialog.dismiss();
                                    Toast
                                            .makeText(Profile.this,
                                                    "Image Uploaded!!",
                                                    Toast.LENGTH_SHORT)
                                            .show();
////                                    storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
////                                        @Override
////                                        public void onSuccess(Uri uri) {
////                                            Uri downloadUrl = uri;
////                                            Toast.makeText(profileEdit.this, "Upload success! URL - " + downloadUrl.toString() , Toast.LENGTH_SHORT).show();
////                                        }
//                                    });
//                                     Upload upload = new Upload(taskSnapshot.getDownoadUrl().toString());
//                                    String uploadId = storageReference.push().getKey();
//                                    storageReference.child(uploadId).setValue(upload);
                                }
                            })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            // Error, Image not uploaded
                            progressDialog.dismiss();
                            Toast
                                    .makeText(Profile.this,
                                            "Failed " + e.getMessage(),
                                            Toast.LENGTH_SHORT)
                                    .show();
                        }
                    })
                    .addOnProgressListener(
                            new OnProgressListener<UploadTask.TaskSnapshot>() {

                                // Progress Listener for loading
                                // percentage on the dialog box
                                @Override
                                public void onProgress(
                                        UploadTask.TaskSnapshot taskSnapshot) {
                                    double progress
                                            = (100.0
                                            * taskSnapshot.getBytesTransferred()
                                            / taskSnapshot.getTotalByteCount());
                                    progressDialog.setMessage(
                                            "Uploaded "
                                                    + (int) progress + "%");
                                }
                            });
        }}}


//    private void setDetails(){
//
////        username = (TextView) findViewById(R.id.username);
//        weight = (TextView) findViewById(R.id.weight);
//        height = (TextView) findViewById(R.id.height);
//        age = (TextView) findViewById(R.id.age);
//        gender = (TextView) findViewById(R.id.gender);
//        bmi = (TextView) findViewById(R.id.bmi);

//        username.setText(user.username);
//        weight.setText(user.weight);
//        height.setText(user.height);
//        age.setText(user.age);
//        gender.setText(String.valueOf(genderData));
//        bmi.setText((int) user.setBmi());



