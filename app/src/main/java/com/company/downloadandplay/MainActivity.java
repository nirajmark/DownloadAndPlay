package com.company.downloadandplay;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.company.downloadandplay.services.DownloadService;

public class MainActivity extends AppCompatActivity {

    Button play;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        play = (Button) findViewById(R.id.play);




        Intent intent = new Intent(this, DownloadService.class);
        intent.putExtra("url", "Put_Url_Here"); // Note: here put the url from where u want to download file
        startService(intent);

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(),Uri.parse(getApplicationContext().getFilesDir()+"/audio.aac"));
                mediaPlayer.start();
            }
        });
    }


}


//links : https://stackoverflow.com/questions/3028306/download-a-file-with-android-and-showing-the-progress-in-a-progressdialog
