package com.example.wolfknight.alphabet;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;

public class family extends AppCompatActivity implements View.OnClickListener{

    ConstraintLayout rootLayout;
    ImageView relativepic;
    TextView relationName;
    ArrayList<Bitmap> picList;
    ArrayList<String> nameList;
    static DatabaseReference mRef,email,name,relatives;
    FloatingActionButton addMember;
    int totalLetters, index = 0;

    reference_class referenceClass;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family);

        relationName = (TextView) findViewById(R.id.relationName);
        relativepic = (ImageView) findViewById(R.id.relativeImage);
        rootLayout = (ConstraintLayout)findViewById(R.id.rootConsLayout);
        addMember = (FloatingActionButton)findViewById(R.id.addfamilyfab);


        rootLayout.setOnTouchListener(new OnSwipeTouchListener(this) {
            @Override
            public void onSwipeLeft() {
                if (index == totalLetters - 1) index = -1;
                index++;
                relativepic.setImageBitmap(picList.get(index));
                reference.speakout(nameList.get(index));
                relationName.setText(nameList.get(index));
            }
            @Override
            public void onSwipeRight() {
                if (index == 0) index = totalLetters;
                index--;
                relativepic.setImageBitmap(picList.get(index));
                reference.speakout(nameList.get(index));
                relationName.setText(nameList.get(index));
            }
        });

        picList = new ArrayList<>();
        nameList = new ArrayList<>();

        addMember.setOnClickListener(this);

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
                    nameList.add(key);

                    new family.DownloadImage().execute(data);

                }
                if (nameList.isEmpty()) {
                    Intent i = new Intent(getApplicationContext(), AddFamilyMember.class);
                    startActivity(i);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Database error", Toast.LENGTH_LONG).show();
            }
        });

    }

    private class DownloadImage extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... URL) {

            String imageURL = URL[0];

            Bitmap bitmap = null;
            try {
                // Download Image from URL
                InputStream input = new java.net.URL(imageURL).openStream();
                // Decode Bitmap
                bitmap = BitmapFactory.decodeStream(input);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            picList.add(bitmap);
            totalLetters = picList.size();
            if(totalLetters==1) {
                relativepic.setImageBitmap(picList.get(0));
                reference.speakout(nameList.get(0));
                relationName.setText(nameList.get(0));
            }
        }
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

    @Override
    public void onClick(View v) {
        if (v == addMember) {
            startActivity(new Intent(getApplicationContext(), AddFamilyMember.class));
        }

    }


}
