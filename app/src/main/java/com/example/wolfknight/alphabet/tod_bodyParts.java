package com.example.wolfknight.alphabet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class tod_bodyParts extends AppCompatActivity {

    com.example.wolfknight.alphabet.dataAccessClass dataAccessClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tod_body_parts);

    }

    public void clickFunc(View v){

        String text = v.getTag().toString();
        Toast.makeText(this,text,Toast.LENGTH_LONG).show();
        reference.speakout(text);
    }
}
