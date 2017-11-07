package com.example.wolfknight.alphabet;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

public class kiddleMulDiv extends AppCompatActivity implements TextToSpeech.OnInitListener{

    int[] textViews = {R.id.first,R.id.second,R.id.third};
    int totalQues=10,quescount=0,correctAns=0,attempt=1;
    int cor_op_ind;
    int option,firstnum,secondnum,answer;
    int buttons[] = {R.id.button1,R.id.button2,R.id.button3,R.id.button4};
    TextToSpeech tts;
    TextView first,sign,second;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kiddle_mul_div);

        first = (TextView)findViewById(R.id.firstnum);
        sign = (TextView)findViewById(R.id.secondnum);
        second = (TextView)findViewById(R.id.thirdnum);

        tts= new TextToSpeech(this,this);

        generateQuestion();

    }

    public void generateQuestion(){
        if(quescount<totalQues) {
            attempt=1;
            Random rand = new Random();
            int signSelect = rand.nextInt(2);
            firstnum  = rand.nextInt(9)+1;
            first.setText(""+firstnum);

            if(signSelect == 0){
                sign.setText("*");
                secondnum = rand.nextInt(10);
                answer = firstnum * secondnum;
            }else{
                sign.setText("/");
                secondnum = rand.nextInt(firstnum+1)+1;
                if(secondnum>firstnum) secondnum-=1;
                answer = firstnum/secondnum;
            }
            second.setText(""+secondnum);
            ArrayList list = new ArrayList();
            quescount++;

            cor_op_ind = rand.nextInt(4);
            list.add(answer);

            Button correctButton = (Button) findViewById(buttons[cor_op_ind]);
            correctButton.setText(""+answer);
            correctButton.setTag(answer);
            int range = min(answer*2,100);

            for (int i = 0; i < 4; i++) {
                if (i != cor_op_ind) {
                    do {
                        option = rand.nextInt(range+1);

                    } while (list.contains(option));
                    list.add(option);
                    Button button = (Button) findViewById(buttons[i]);
                    button.setText(""+option);
                    button.setTag(option);

                }

            }
        }else showResultDialog();

    }

    public void buttonClick(View v){
        if((int)v.getTag()==answer){
            if(attempt==1) correctAns++;
            showCorrectAnsAlertDialog();
        }
        else showIncorrectAnsAlertDialog();
    }

    public int min(int firstnum,int secondnum){

        int max = 6>firstnum?6:firstnum;
        return max<secondnum?max:secondnum;
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
                        generateQuestion();

                    }
                })
                .setNegativeButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(kiddleMulDiv.this,lesson.class));
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

}
