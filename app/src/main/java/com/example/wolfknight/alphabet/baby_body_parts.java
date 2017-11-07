package com.example.wolfknight.alphabet;

import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import java.util.ArrayList;

public class baby_body_parts extends AppCompatActivity {

    ConstraintLayout rootLayout;
    ImageView bodyPartsImage;
    TextView bodyPartsName;
    static DatabaseReference mRef,email,name,relatives;
    ArrayList urllist, nameList;
    FloatingActionButton addMember;
    int listSize, index = 0;
    LinearLayout linearLayout;
    static File directory;
    dataAccessClass object;
    reference_class referenceClass;
    ProgressBar pbr;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baby_body_parts);

        bodyPartsName = (TextView) findViewById(R.id.bodyPartsName);
        bodyPartsImage = (ImageView) findViewById(R.id.bodyPartsImage);
        linearLayout = (LinearLayout)findViewById(R.id.linarlayout);
        rootLayout = (ConstraintLayout)findViewById(R.id.rootLayout);
        
        urllist = new ArrayList();
        nameList = new ArrayList();

        rootLayout.setOnTouchListener(new OnSwipeTouchListener(this) {
            @Override
            public void onSwipeLeft() {
                if (index == listSize - 1) index = -1;
                object.accessData(++index,urllist,nameList,bodyPartsName,bodyPartsImage,directory);
            }

            @Override
            public void onSwipeRight() {
                if (index == 0) index = listSize;
                object.accessData(--index,urllist,nameList,bodyPartsName,bodyPartsImage,directory);
            }
        });
        

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
                    listSize = urllist.size();
                    object = new dataAccessClass(baby_body_parts.this);
                    object.accessData(index,urllist,nameList,bodyPartsName,bodyPartsImage,directory);
                
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

}
