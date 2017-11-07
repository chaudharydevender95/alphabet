package com.example.wolfknight.alphabet;

import android.os.CountDownTimer;
import android.speech.tts.TextToSpeech;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;
import java.util.Random;

public class child_add_sub extends AppCompatActivity implements TextToSpeech.OnInitListener{

    com.example.wolfknight.alphabet.dataAccessClass dataAccessClass;
    Long sleep = 1000L;
    ConstraintLayout layout;
    int index=0,count=1,speak=1;
    TextToSpeech tts;


    int[] firstNumIcon = {R.id.first1,R.id.first2,R.id.first3,R.id.first4,R.id.first5,R.id.first6,
            R.id.first7,R.id.first8,R.id.first9};
    int[] secondNumIcon = {R.id.second1,R.id.second2,R.id.second3,R.id.second4,R.id.second5,R.id.second6,
            R.id.second7,R.id.second8,R.id.second9};
    int firstnum,secondnum,answer;
    TextView firstNumText,secondNumText,answerText;
    String[] counting = {"one","two","three","four","five","six","seven","eight","nine","ten","eleven","twelve",
                "thirteen","fourteen","fifteen","sixteen","seventeen","eightteen","nineteen","twenty"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_add_sub);

        firstNumText = (TextView)findViewById(R.id.firstNum);
        secondNumText = (TextView)findViewById(R.id.secondNum);
        answerText = (TextView)findViewById(R.id.answer);
        layout = (ConstraintLayout)findViewById(R.id.cordLayout);

        dataAccessClass = new dataAccessClass(this);
        tts = new TextToSpeech(this,this);

        generateQuestion();

    }


    public void generateQuestion(){

        final int p,q;
        Random rand = new Random();
        firstnum = rand.nextInt(10);
        //firstnum = 0;
        if(firstnum ==0 ) p=1;
        else  p=firstnum;
        secondnum = rand.nextInt(10);
        //secondnum = 0;
        if(secondnum == 0) q = 1;
        else q=secondnum;
        firstNumText.setText(""+firstnum);
        secondNumText.setText(""+secondnum);
        answer = firstnum+secondnum;
        answerText.setText(""+answer);
        speakout("init");

        CountDownTimer timer = new CountDownTimer((p+q+3)*1500-1000,1500) {
            @Override
            public void onTick(long millisUntilFinished) {
                if(index>0) {
                    if(firstnum == 0 && count == 1) speakout("zero");
                    else if(firstnum == 0 && count == 2){
                        speakout("plus");
                        index = 0;
                    }
                    else if (count <= firstnum) {
                        FloatingActionButton button = (FloatingActionButton) findViewById(firstNumIcon[index - 1]);
                        button.setVisibility(View.VISIBLE);
                        speakout("" + (""+speak));
                        speak++;

                    } else if (count == (firstnum+1)) {
                        speakout("plus");
                        index = 0;
                    } else {
                        if(secondnum == 0) dataAccessClass.speakout("zero");
                        else {
                        FloatingActionButton button = (FloatingActionButton) findViewById(secondNumIcon[index - 1]);
                        button.setVisibility(View.VISIBLE);
                        speakout("" + (speak-firstnum));
                            speak++;
                        }
                    }
                    count++;
                }


                index++;
            }

            @Override
            public void onFinish() {
                speakout("is equals to "+(firstnum+secondnum));

            }
        }.start();
        
    }
    private void speak(int count){
        Toast.makeText(child_add_sub.this,"inside",Toast.LENGTH_SHORT).show();
        //for(int i=0;i<count;i++){
            dataAccessClass.speakout("yes");
        dataAccessClass.speakout("yes");
        //}
    }

    public void startCounting(int num, final int array[]){

        CountDownTimer timer = new CountDownTimer((num+2)*1500,1500) {
            @Override
            public void onTick(long millisUntilFinished) {
                if(count>0) {
                    FloatingActionButton button = (FloatingActionButton) findViewById(array[count - 1]);
                    button.setVisibility(View.VISIBLE);
                }

                speakout(""+(count));
                count++;
            }

            @Override
            public void onFinish() {
                dataAccessClass.speakout("finished");

            }
        }.start();
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
        tts.setPitch((float) (-5));
        tts.setSpeechRate((float) 1.0);
        tts.speak(" "+text, TextToSpeech.QUEUE_FLUSH, null);

    }
}
