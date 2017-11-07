package com.example.wolfknight.alphabet;

import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Locale;

public class reference extends AppCompatActivity implements TextToSpeech.OnInitListener{

    static TextToSpeech tts;
    static FirebaseDatabase firebaseDatabase;
    static DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reference);

        tts = new TextToSpeech(this,this);

        firebaseDatabase = FirebaseDatabase.getInstance();
        if(databaseReference==null)
            firebaseDatabase.setPersistenceEnabled(true);
        databaseReference = firebaseDatabase.getReferenceFromUrl("https://alphabet-8d5ca.firebaseio.com/");

        speakout(" ");
        speakout(" ");

        startActivity(new Intent(this,category.class));
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int language = tts.setLanguage(Locale.ENGLISH);

            if (language == TextToSpeech.LANG_MISSING_DATA || language == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("tts", "This language is not supported");
            } else {
                speakout("");
            }
        } else {
            Toast.makeText(getApplicationContext(), "TextToSpeech not initiated", Toast.LENGTH_LONG).show();
        }
    }

    public static void speakout(String text) {
        tts.setPitch((float) (-5));
        tts.setSpeechRate((float) 1.0);
        tts.speak(" " + text, TextToSpeech.QUEUE_FLUSH, null);
    }

    @Override
    protected void onDestroy() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();

        }
        super.onDestroy();
    }
}
