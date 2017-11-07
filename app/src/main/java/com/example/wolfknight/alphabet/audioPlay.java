package com.example.wolfknight.alphabet;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class audioPlay extends AppCompatActivity {

    RelativeLayout root;
    MediaPlayer mediaPlayer=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_play);


        root = (RelativeLayout)findViewById(R.id.rootLayout);
        root.setBackground(getResources().getDrawable(rhymsActivity.backGround[rhymsActivity.rhym_num]));

        mediaPlayer = MediaPlayer.create(audioPlay.this,rhymsActivity.audioList[rhymsActivity.rhym_num]);
        mediaPlayer.start();


        Button resume = (Button)findViewById(R.id.resume);
        Button pause = (Button)findViewById(R.id.pause);

        resume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.seekTo(0);
            }
        });
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer.isPlaying())
                    mediaPlayer.pause();
                else mediaPlayer.start();
            }
        });
    }

    @Override
    public void onBackPressed(){
        if(mediaPlayer != null && mediaPlayer.isPlaying())
            mediaPlayer.stop();
        finish();
    }

    public void resumeAudio(){
        //mediaPlayer.seekTo(0);
        Toast.makeText(this, "clicking", Toast.LENGTH_SHORT).show();
    }
    public void pauseAudio(){
        //mediaPlayer.pause();
        Toast.makeText(this, "clicking", Toast.LENGTH_SHORT).show();
    }
}
