package com.example.wolfknight.alphabet;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

public class kiddleFillupPrac extends AppCompatActivity implements TextToSpeech.OnInitListener{

    DatabaseReference mRef;
    ArrayList<String> names;
    int listSize, wordIndex,cboxIndex,nameIndex,letterIndex;
    int[] textViews = {R.id.first,R.id.second,R.id.third};
    int totalQues=15,quescount=0,correctAns=0,attempt=1;
    String alphabet = "abcdefghijklmnopqrstuvwxyz";
    char missingLetter;
    TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kiddle_fillup_prac);

        tts= new TextToSpeech(this,this);

        names= new ArrayList<>();
        getReference();

        LoadList();
    }
    public void LoadList(){

        mRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot urlSnapshot : dataSnapshot.getChildren()) {
                    String data = urlSnapshot.getValue().toString();
                    String key = urlSnapshot.getKey().toString();
                    names.add(key);
                }
                listSize = names.size();
                if(listSize>0) generateQuestion();
                else Toast.makeText(kiddleFillupPrac.this, "List is empty", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Database error", Toast.LENGTH_LONG).show();
            }
        });

    }
    public void buttonClicked(View v){
        if(v.getTag().toString().charAt(0) == missingLetter){
            if(attempt==1) correctAns++;
            showCorrectAnsAlertDialog();
        }
        else showIncorrectAnsAlertDialog();

    }
    public void getReference(){

        reference_class referenceClass = new reference_class(this);
        mRef = referenceClass.getFirebaseReference("infant_three_letter_words");

    }


    public void generateQuestion() {
        if (quescount < totalQues) {
            attempt = 1;
            Random rand = new Random();
            ArrayList<String> list = new ArrayList();
            quescount++;

            wordIndex = rand.nextInt(listSize);
            String word = names.get(wordIndex);
            letterIndex = rand.nextInt(3);
            missingLetter = word.charAt(letterIndex);
            list.add("" + missingLetter);

            for (int i = 0; i < 3; i++) {
                if (i != letterIndex) {
                    TextView textView = (TextView) findViewById(textViews[i]);
                    textView.setText("" + word.charAt(i));
                }
            }
            TextView textView = (TextView) findViewById(textViews[letterIndex]);
            textView.setText("_");
        }else showResultDialog();
    }

    public void showCorrectAnsAlertDialog(){
        String alertMsg = "Correct Answer";
        speakout(alertMsg);
        new AlertDialog.Builder(this)
                .setTitle("Your response")
                .setMessage(alertMsg)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        generateQuestion();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
    public void showIncorrectAnsAlertDialog(){
        String alertMsg = "Try Again";
        speakout(alertMsg);
        new AlertDialog.Builder(this)
                .setTitle("Your response")
                .setMessage(alertMsg)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        attempt++;
                        //clickedCheckBox.toggle();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
    public void showResultDialog(){
        new AlertDialog.Builder(this)
                .setTitle("Test Over")
                .setMessage("Total Ques : "+totalQues+"\nCorrect : "+correctAns+"\nIncorrect : "+(totalQues-correctAns))
                .setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        correctAns = 0;
                        quescount =0;

                    }
                })
                .setNegativeButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(kiddleFillupPrac.this,lesson.class));
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
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

    public void speakout(String text) {
        tts.setPitch((float) (1.8));
        tts.setSpeechRate((float) 1.2);
        tts.speak(" "+text, TextToSpeech.QUEUE_FLUSH, null);
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
