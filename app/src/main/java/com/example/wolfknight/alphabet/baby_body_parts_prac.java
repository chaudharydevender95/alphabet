package com.example.wolfknight.alphabet;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

public class baby_body_parts_prac extends AppCompatActivity implements View.OnClickListener{

    ImageView body_img;
    CheckBox cbox0,cbox1,cbox2,cbox3;
    CheckBox clickedCheckBox;
    static DatabaseReference mRef,email,name,relatives;
    ArrayList urllist, nameList;
    FloatingActionButton addMember;
    int listSize, imageIndex,cboxIndex,nameIndex;
    int[] cboxes = {R.id.box0,R.id.box1,R.id.box2,R.id.box3};
    int totalQues=15,quescount=0,correctAns=0,attempt=1;
    static File directory;
    com.example.wolfknight.alphabet.dataAccessClass dataAccessClass;
    reference_class referenceClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baby_body_parts_prac);

        body_img = (ImageView)findViewById(R.id.bodyPartsImage);
        urllist = new ArrayList();
        nameList = new ArrayList();
        dataAccessClass = new dataAccessClass(baby_body_parts_prac.this);

        cbox0 = (CheckBox)findViewById(R.id.box0);
        cbox1 = (CheckBox)findViewById(R.id.box1);
        cbox2 = (CheckBox)findViewById(R.id.box2);
        cbox3 = (CheckBox)findViewById(R.id.box3);

        cbox0.setOnClickListener(this);
        cbox1.setOnClickListener(this);
        cbox2.setOnClickListener(this);
        cbox3.setOnClickListener(this);

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
                listSize = urllist.size();
                if(listSize>0) generateQuestion();


            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Database error", Toast.LENGTH_LONG).show();
            }
        });

    }
    public void getReference(){

        referenceClass = new reference_class(this);
        mRef = referenceClass.getFirebaseReference("bodyParts");
        directory = referenceClass.getOfflineReference("bodyParts");
    }

    public void generateQuestion(){
        if(quescount<totalQues) {
            attempt=1;
            Random rand = new Random();
            ArrayList list = new ArrayList();
            quescount++;

            imageIndex = rand.nextInt(listSize);
            cboxIndex = rand.nextInt(4);
            list.add(imageIndex);
            dataAccessClass.accessData(imageIndex, urllist, nameList, body_img, directory);

            CheckBox correctbox = (CheckBox) findViewById(cboxes[cboxIndex]);
            correctbox.setText(nameList.get(imageIndex).toString());

            for (int i = 0; i < 4; i++) {
                if (i != cboxIndex) {
                    do {
                        nameIndex = rand.nextInt(listSize);
                    } while (nameIndex == imageIndex || list.contains(nameIndex));
                    CheckBox box = (CheckBox) findViewById(cboxes[i]);
                    box.setText(nameList.get(nameIndex).toString());
                    list.add(nameIndex);
                }

            }
        }else showResultDialog();

    }

    @Override
    public void onClick(View v) {

        if(v.getId() == cboxes[cboxIndex]){
            if(attempt==1) correctAns++;
            showCorrectAnsAlertDialog();
        }
        else showIncorrectAnsAlertDialog();
        clickedCheckBox = (CheckBox)findViewById(v.getId());
    }

    public void showCorrectAnsAlertDialog(){
        String alertMsg = "Correct Answer";
        dataAccessClass.speakout(alertMsg);
        new AlertDialog.Builder(this)
                .setTitle("Your response")
                .setMessage(alertMsg)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        clickedCheckBox.toggle();
                        generateQuestion();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
    public void showIncorrectAnsAlertDialog(){
        String alertMsg = "Try Again";
        dataAccessClass.speakout(alertMsg);
        new AlertDialog.Builder(this)
                .setTitle("Your response")
                .setMessage(alertMsg)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        attempt++;
                        clickedCheckBox.toggle();
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
                        startActivity(new Intent(baby_body_parts_prac.this,lesson.class));
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
