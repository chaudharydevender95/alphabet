package com.example.wolfknight.alphabet;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.support.constraint.ConstraintLayout;
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

public class mustWordPrac extends AppCompatActivity implements View.OnClickListener{

    ImageView mustWord,mustWordImage;
    ArrayList<String> nameList;
    DatabaseReference mRefChild,picRef,textRef;
    int textIndex,imageIndex=0,maxIndex;
    ConstraintLayout root;
    reference_class referenceClass;
    com.example.wolfknight.alphabet.dataAccessClass dataAccessClass;
    ArrayList<Bitmap> pics,name;
    ImageButton right,wrong;
    File directory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_must_word_prac);

        mustWord = (ImageView) findViewById(R.id.mustWord);
        mustWordImage = (ImageView) findViewById(R.id.mustWordImage);

        //picList = new ArrayList();
        //textList = new ArrayList();
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
        picRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot urlSnapshot : dataSnapshot.getChildren()){
                    String data = urlSnapshot.getValue().toString();
                    String key = urlSnapshot.getKey().toString();

                    nameList.add(key);
                    Toast.makeText(mustWordPrac.this, "fetching", Toast.LENGTH_SHORT).show();
                }
                maxIndex = nameList.size();
                if(maxIndex>0){
                    generateQuestion();
                }



            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),"Database error",Toast.LENGTH_LONG).show();
            }
        });
    }

    public void getReference(){
        reference_class reference_class = new reference_class(this);
        mRefChild= reference.databaseReference.child("must_words");
        picRef = mRefChild.child("pics");
        directory = reference_class.getOfflineReference("must_words");

    }

    public void generateQuestion(){

        Random rand = new Random();
        do {
            imageIndex = rand.nextInt(maxIndex);
        }while (imageIndex%2==1);
        do {
            textIndex = rand.nextInt(maxIndex);
        }while (textIndex%2==0);



        dataAccessClass.loadImageFromStorage(mustWordImage,directory.getAbsolutePath(),nameList.get(imageIndex).toString());
        dataAccessClass.loadImageFromStorage(mustWord,directory.getAbsolutePath(),nameList.get(textIndex).toString());
    }

    @Override
    public void onClick(View v) {
        if (v == right) {
            String alertmsg;
            if(imageIndex == textIndex-1){
                alertmsg = "Correct Answer";
            }
            else alertmsg = "Wrong Answer";
            dataAccessClass.speakout(alertmsg);
            showAlertDialog(alertmsg);

        }
        if(v == wrong){
            String alertmsg;
            if(imageIndex!=textIndex-1){
                alertmsg = "Correct Answer";
            }else alertmsg = "Wrong Answer";
            dataAccessClass.speakout(alertmsg);
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
