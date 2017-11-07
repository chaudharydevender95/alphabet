package com.example.wolfknight.alphabet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class rhymsActivity extends AppCompatActivity {

    static int audioList[] = {R.raw.ba_bamp3,R.raw.mary_had_a_liitle_lampmp3,R.raw.old_mcdonalmp3,R.raw.rain_rain_go_awaymp3,R.raw.wheels_on_the_busmp3,R.raw.ants_go_marchingmp3};
    static int rhym_num;
    static int backGround[] = {R.drawable.ba_ba,R.drawable.mary_rhym,R.drawable.old_mcdonald,R.drawable.rain_rain,R.drawable.wheels_on_bus,R.drawable.ants_go};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rhyms);

    }

    public void playAudio(View v){
        rhym_num = Integer.parseInt(v.getTag().toString());
        startActivity(new Intent(rhymsActivity.this,audioPlay.class));
    }
}
