package com.example.wolfknight.alphabet;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;

public class baby_color extends AppCompatActivity {

    TextView colorName;
    ImageView colorImage;
    ArrayList<String> nameList;
    DatabaseReference mRefChild;
    int totalLetters,index=0;
    RelativeLayout root;
    reference_class referenceClass;
    com.example.wolfknight.alphabet.dataAccessClass object;
    ArrayList<Bitmap> picsList;
    com.example.wolfknight.alphabet.dataAccessClass dataAccessClass;
    File babyColorsDirectory,mypath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baby_color);

        root = (RelativeLayout)findViewById(R.id.root);
        root.setOnTouchListener(new OnSwipeTouchListener(this){
            @Override
            public void onSwipeLeft() {
                if (index == totalLetters - 1) index = -1;
                colorImage.setImageBitmap(picsList.get(++index));
                colorName.setText(nameList.get(index));
            }
            @Override
            public void onSwipeRight() {
                if (index == 0) index = totalLetters;
                colorImage.setImageBitmap(picsList.get(--index));
                colorName.setText(nameList.get(index).toString());
            }

        });

       colorName = (TextView)findViewById(R.id.colorName);
        colorImage = (ImageView) findViewById(R.id.colorImage);

        nameList = new ArrayList<String>();
        picsList = new ArrayList<Bitmap>();

        dataAccessClass = new dataAccessClass(this);
        //Get Reference to database
        getReference();

        LoadList();
    }

    public void LoadList(){
        mRefChild.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot urlSnapshot : dataSnapshot.getChildren()){
                    String data = urlSnapshot.getValue().toString();
                    String key = urlSnapshot.getKey().toString();
                    Bitmap bitmap = null;

                    new DownloadImage().execute(data);

                    nameList.add(key);

                }
                if(picsList.isEmpty()){
                    Toast.makeText(baby_color.this,"List is empty",Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(baby_color.this, "updated", Toast.LENGTH_SHORT).show();
                    totalLetters = picsList.size();

                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),"Database error",Toast.LENGTH_LONG).show();
            }
        });
    }


    public void getReference(){

        referenceClass = new reference_class(this);
        mRefChild= referenceClass.getFirebaseReference("baby_colors");
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
            picsList.add(bitmap);
            colorImage.setImageBitmap(picsList.get(index));
            Toast.makeText(baby_color.this, "updated", Toast.LENGTH_SHORT).show();
            totalLetters = picsList.size();
        }
    }
}
