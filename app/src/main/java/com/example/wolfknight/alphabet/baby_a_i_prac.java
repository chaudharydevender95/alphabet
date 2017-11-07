package com.example.wolfknight.alphabet;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;

public class baby_a_i_prac extends AppCompatActivity implements View.OnClickListener {

    reference_class referenceClass;
    DatabaseReference mRef,a_iRef;
    File directory;
    com.example.wolfknight.alphabet.dataAccessClass dataAccessClass;
    ArrayList<Bitmap> bitmapList;
    ArrayList nameList;
    Button nextButton,clearButton;
    int index =0;
    RelativeLayout root;
    private CanvasView customCanVas;
    ImageView alphabetImage;
    LinearLayout progressBar;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baby_a_i_prac);

        root = (RelativeLayout)findViewById(R.id.rootLayout);
        nextButton = (Button) findViewById(R.id.nextButton);
        clearButton = (Button)findViewById(R.id.clearButton);

        bitmapList = new ArrayList<>();
        nameList = new ArrayList();
        progressBar = (LinearLayout) findViewById(R.id.progressBar);

        alphabetImage = (ImageView)findViewById(R.id.alphabetImage);

        customCanVas = (CanvasView) findViewById(R.id.canvasView);




        mRef = reference.databaseReference.child("writing");
        a_iRef= mRef.child("a_i");
        a_iRef.keepSynced(true);

        LoadList();


        nextButton.setOnClickListener(this);
        clearButton.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        if(v==nextButton){
            if(index<(bitmapList.size()-1)){
                alphabetImage.setImageBitmap(bitmapList.get(++index));
                if(index==8) index = -1;
            }
            customCanVas.clearCanvas();
        }

        if(v==clearButton){
            Toast.makeText(baby_a_i_prac.this, "clicking", Toast.LENGTH_SHORT).show();
            customCanVas.clearCanvas();
        }
    }
    public void LoadList(){


        a_iRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot urlSnapshot : dataSnapshot.getChildren()) {
                    String data = urlSnapshot.getValue().toString();
                    new DownloadImage().execute(data);

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
            bitmapList.add(bitmap);
            progressBar.setVisibility(View.GONE);
            alphabetImage.setImageBitmap(bitmapList.get(index));

        }
    }


}
