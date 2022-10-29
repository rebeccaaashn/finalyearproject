package com.example.a8you;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

public class resepi1 extends AppCompatActivity {

    Button backR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resepi1);

        backR =(Button) findViewById(R.id.backR);
        backR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent bckR = new Intent(resepi1.this, Diet.class);
                startActivity(bckR);
            }
        });

        Button buttonPlayVideo2 = (Button) findViewById(R.id.button1);
        getWindow().setFormat(PixelFormat.UNKNOWN);

        //display video file
        VideoView mVideoView2 = (VideoView) findViewById(R.id.videoView1);

        String uriPath2 = "android.resource://com.example.a8you/" + R.raw.meal;
        Uri uri2 = Uri.parse(uriPath2);
        mVideoView2.setVideoURI(uri2);
        mVideoView2.requestFocus();
        mVideoView2.start();

        buttonPlayVideo2.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                VideoView mVideoView2 = (VideoView) findViewById(R.id.videoView1);
                // VideoView mVideoView = new VideoView(this);
                String uriPath = "android.resource://com.example.toyo.playvideo/" + R.raw.food1;
                Uri uri2 = Uri.parse(uriPath);
                mVideoView2.setVideoURI(uri2);
                mVideoView2.requestFocus();
                mVideoView2.start();
            }
        });
    }}