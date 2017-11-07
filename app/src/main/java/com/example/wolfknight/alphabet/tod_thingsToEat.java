package com.example.wolfknight.alphabet;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

public class tod_thingsToEat extends AppCompatActivity implements View.OnClickListener{

    ImageView thingImage;
    TextView thingName;
    File directory;
    reference_class referenceClass;
    com.example.wolfknight.alphabet.dataAccessClass dataAccessClass;
    ImageButton right,wrong;
    int imageIndex,textIndex,maxIndex;
    static DatabaseReference mRef;
    ArrayList urllist, nameList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tod_things_to_eat);

        thingName = (TextView) findViewById(R.id.thingName);
        thingImage = (ImageView) findViewById(R.id.thingImage);


        urllist = new ArrayList();
        nameList = new ArrayList();
        right = (ImageButton) findViewById(R.id.right);
        wrong = (ImageButton) findViewById(R.id.wrong);

        dataAccessClass = new dataAccessClass(this);


        wrong.setOnClickListener(this);
        right.setOnClickListener(this);

        //Get Reference to database
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
                    urllist.add(data);
                    nameList.add(key);

                }
                maxIndex = nameList.size();
                if(maxIndex>0){
                    generateQuestion();
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Database error", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void getReference(){

        referenceClass = new reference_class(this);
        mRef = referenceClass.getFirebaseReference("tod_Game_thingsToEat");
        directory = referenceClass.getOfflineReference("tod_Game_thingsToEat");
    }

    public void generateQuestion(){
        int tempIndex;
        Random rand = new Random();
        imageIndex = rand.nextInt(maxIndex);
        textIndex = rand.nextInt(maxIndex);
        for(int j=0;j<maxIndex;j++){
            tempIndex=rand.nextInt(maxIndex);
            if(imageIndex == tempIndex) textIndex = tempIndex;
        }

        dataAccessClass.accessData(imageIndex,urllist,nameList,thingName,thingImage,directory);
        dataAccessClass.accessText(textIndex,nameList,thingName);
    }

    @Override
    public void onClick(View v) {
        String alertmsg,voiceMsg;
        if (v == right) {

            if(imageIndex == textIndex){
                alertmsg = "Correct Answer";
                voiceMsg = "Yes! you are correct";

            }
            else {
                alertmsg = "Wrong Answer";
                voiceMsg = "No! this is not an eatable thing";
            }
            dataAccessClass.speakout(voiceMsg);
            showAlertDialog(alertmsg);

        }
        if(v == wrong){
            if(imageIndex==textIndex){
                alertmsg = "Wrong Answer";
                voiceMsg = "No! this is eatable";
            }else {
                alertmsg = "Correct Answer";
                voiceMsg = "Yes! you are correct";
            }
            dataAccessClass.speakout(voiceMsg);
            showAlertDialog(alertmsg);
        }

    }

    public void showAlertDialog(String alertMsg){
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

}
