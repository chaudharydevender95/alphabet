package com.example.wolfknight.alphabet;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.speech.tts.TextToSpeech;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Locale;

public class mustWords extends AppCompatActivity{

    ImageView mustWord,mustWordImage;
    ArrayList<String> nameList;
    DatabaseReference mRefChild,picRef;
    int totalLetters,index=0,indexCount=0;
    ConstraintLayout root;
    ArrayList<Bitmap> picList;
    boolean even = false;
    reference_class referenceClass;
    File directory;
    dataAccessClass dataAccessClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_must_words);

        root = (ConstraintLayout)findViewById(R.id.rootLayout);
        root.setOnTouchListener(new OnSwipeTouchListener(this){
            @Override
            public void onSwipeLeft() {
                if (index == totalLetters-2) index = -2;
                index+=2;
                mustWordImage.setImageBitmap(picList.get(index));
                mustWord.setImageBitmap(picList.get(index+1));
                reference.speakout(nameList.get(index).toString());

            }
            @Override
            public void onSwipeRight() {
                if (index == 0) index = totalLetters;
                index-=2;
                mustWordImage.setImageBitmap(picList.get(index));
                mustWord.setImageBitmap(picList.get(index+1));
                reference.speakout(nameList.get(index).toString());
            }
        });


        referenceClass = new reference_class(this);
        dataAccessClass = new dataAccessClass(this);

        mustWord = (ImageView)findViewById(R.id.must_word);
        mustWordImage = (ImageView) findViewById(R.id.must_word_image);

        picList = new ArrayList();
        nameList = new ArrayList();


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

                    new mustWords.DownloadImage().execute(data);

                    nameList.add(key);

                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),"Database error",Toast.LENGTH_LONG).show();
            }
        });
    }
    public void getReference(){

        mRefChild= reference.databaseReference.child("must_words");
        picRef = mRefChild.child("pics");
        directory = referenceClass.getOfflineReference("must_words");
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
            //if(!(new File(directory,nameList.get(indexCount))).exists())
                dataAccessClass.saveToInternalStorage(directory,picList.get(indexCount),nameList.get(indexCount));
            indexCount++;
            if(even){
                totalLetters = picList.size();
                even = false;
            }else even = true;
            if(picList.size()==2)
                loadImage();

        }
    }

    void loadImage(){
        mustWordImage.setImageBitmap(picList.get(0));
        mustWord.setImageBitmap(picList.get(1));
        reference.speakout(nameList.get(1).toString());
    }

}


