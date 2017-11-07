package com.example.wolfknight.alphabet;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

public class tod_family_prac extends AppCompatActivity implements View.OnClickListener{

    ImageView relativepic;
    TextView relationName;
    static DatabaseReference mRef,email,name,relatives;
    ArrayList urllist, relationship;
    ArrayList<Bitmap> picList;
    ArrayList<String> nameList;
    ImageButton right,wrong;
    int imageIndex,textIndex,maxIndex;
    LinearLayout linearLayout;
    static File directory;
    reference_class referenceClass;
    com.example.wolfknight.alphabet.dataAccessClass dataAccessClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tod_family_prac);

        relationName = (TextView) findViewById(R.id.relatioName);
        relativepic = (ImageView) findViewById(R.id.relativism);


        urllist = new ArrayList();
        relationship = new ArrayList();
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
        relatives.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot urlSnapshot : dataSnapshot.getChildren()) {
                    String data = urlSnapshot.getValue().toString();
                    String key = urlSnapshot.getKey().toString();
                    urllist.add(data);
                    relationship.add(key);

                }
                maxIndex = relationship.size();
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

        SharedPreferences myPref = getSharedPreferences("MyData",MODE_PRIVATE);
        String emailId = myPref.getString("email","");
        String userName = myPref.getString("name","");

        mRef = reference.databaseReference.child("users");
        email = mRef.child(emailId);
        name = email.child(userName);
        relatives = name.child("relatives");
    }


    public void generateQuestion(){
        int tempIndex;
        Random rand = new Random();
        imageIndex = rand.nextInt(maxIndex);
        textIndex = rand.nextInt(maxIndex);


        dataAccessClass.accessData(imageIndex,urllist,relationship,relationName,relativepic,directory);
        dataAccessClass.accessText(textIndex,relationship,relationName);
    }

    @Override
    public void onClick(View v) {
        if (v == right) {
            String alertmsg;
            if(imageIndex == textIndex){
                alertmsg = "Correct Answer";
            }
            else alertmsg = "Wrong Answer";
            dataAccessClass.speakout(alertmsg);
            showAlertDialog(alertmsg);

        }
        if(v == wrong){
            String alertmsg;
            if(imageIndex==textIndex){
                alertmsg = "Wrong Answer";
            }else alertmsg = "Correct Answer";
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
