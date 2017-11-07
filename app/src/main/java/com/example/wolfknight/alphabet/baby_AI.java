package com.example.wolfknight.alphabet;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Locale;

public class baby_AI extends AppCompatActivity{

    ImageView smallLetter,capsLetter,alphabetImage;
    ArrayList<Bitmap> picList;
    ArrayList<String> nameList;
    DatabaseReference mRef;
    int totalLetters,index=0,downloadcount=1,indexCount=0;
    RelativeLayout root;
    reference_class referenceClass;
    LinearLayout progressBar;
    File directory;
    dataAccessClass dataAccessClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baby__ai);

        root = (RelativeLayout) findViewById(R.id.root);
        progressBar = (LinearLayout) findViewById(R.id.progressBar);

        root.setOnTouchListener(new OnSwipeTouchListener(this){
            @Override
            public void onSwipeLeft() {
                if (index == totalLetters-3) index = -3;
                index+=3;
                smallLetter.setImageBitmap(picList.get(index));
                capsLetter.setImageBitmap(picList.get(index+1));
                alphabetImage.setImageBitmap(picList.get(index+2));
                reference.speakout(nameList.get(index+2));
            }
            @Override
            public void onSwipeRight() {
                if (index == 0) index = totalLetters;
                index-=3;
                smallLetter.setImageBitmap(picList.get(index));
                capsLetter.setImageBitmap(picList.get(index+1));
                alphabetImage.setImageBitmap(picList.get(index+2));
                reference.speakout(nameList.get(index+2));
            }
        });

        smallLetter = (ImageView) findViewById(R.id.smallLetter);
        capsLetter = (ImageView) findViewById(R.id.capsLetter);
        alphabetImage = (ImageView) findViewById(R.id.alphabetImage);

        picList = new ArrayList<>();
        nameList = new ArrayList<>();

        referenceClass = new reference_class(this);
        dataAccessClass = new dataAccessClass(this);
        //Get Reference to database
        directory = referenceClass.getOfflineReference("a_i");
        mRef = reference.databaseReference.child("a_i");
        mRef.keepSynced(true);

        LoadList();

    }

    public void LoadList(){

        mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String data = dataSnapshot.getValue(String.class);
                String key = dataSnapshot.getKey();
                nameList.add(key);

                new baby_AI.DownloadImage().execute(data);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

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
            dataAccessClass.saveToInternalStorage(directory,picList.get(indexCount),nameList.get(indexCount));
            indexCount++;
            if((downloadcount%3)==0){
                totalLetters = picList.size();
            }
            downloadcount++;
            if(picList.size()==3)
                loadImage();

        }
    }

    void loadImage(){
        smallLetter.setImageBitmap(picList.get(0));
        capsLetter.setImageBitmap(picList.get(1));
        alphabetImage.setImageBitmap(picList.get(2));
        reference.speakout(nameList.get(2).toString());
        progressBar.setVisibility(View.GONE);
    }
}
