package com.example.abnlt.myphotozigtask;

import android.app.ActionBar;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

import java.io.IOException;

public class PlayerActivity extends AppCompatActivity {
    private MediaPlayer mpintro;
    String mp3;
    String mp4;
    Button btnMp3;
    VideoView vidMp4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mp3 = extras.getString("mp3");
            mp4=extras.getString("mp4");
        }
        btnMp3=(Button)findViewById(R.id.button2);
        vidMp4=(VideoView)findViewById(R.id.videoView);
        vidMp4.setVideoPath(mp4);
        vidMp4.setMediaController(new MediaController(this));
        vidMp4.start();
        vidMp4.requestFocus();
        vidMp4.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });

        btnMp3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mpintro = new MediaPlayer();
                String media_path = mp3;
                mpintro.setAudioStreamType(AudioManager.STREAM_MUSIC);
                Uri uri = Uri.parse(media_path);
                try {
                    mpintro.setDataSource(getApplicationContext(), uri);
                    mpintro.prepare();
                    mpintro.start();
                    btnMp3.setEnabled(false);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mpintro.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        btnMp3.setEnabled(true);
                        mpintro.release();
                        mpintro = null;
                    }
                });
            }
        });




    }
}
