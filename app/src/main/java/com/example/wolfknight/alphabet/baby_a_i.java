package com.example.wolfknight.alphabet;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.util.ArrayList;

public class baby_a_i extends AppCompatActivity {

    ImageView smallLetter,capsLetter,alphabetImage;
    ArrayList smallLetterList,capsLetterList,alphabetImageList,letterNameList,alphabetNameList;
    DatabaseReference mRef,smallRef,capsRef,alphabetRef;
    int totalLetters,index=0;
    RelativeLayout root;
    static boolean fetched=false;
    TextView alphabetName;
    reference_class referenceClass;
    dataAccessClass object;
    File smallLetterDirectory,capsLetterDirectory,alphabetImageDirectory;
    ArrayList<Bitmap> imageList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baby_a_i);

        //smallLetter = (ImageView) findViewById(R.id.smallLetter);
        //capsLetter = (ImageView) findViewById(R.id.capsLetter);
        alphabetImage = (ImageView) findViewById(R.id.alphabetImage);
        alphabetName = (TextView)findViewById(R.id.text);

        root = (RelativeLayout) findViewById(R.id.rootLayout);
        root.setOnTouchListener(new OnSwipeTouchListener(this){
            @Override
            public void onSwipeLeft() {
                if (index == totalLetters - 1) index = -1;

                //object.accessData(++index,smallLetterList,letterNameList,smallLetter,smallLetterDirectory);
                //alphabetName.setText(""+fetched);
                //if(fetched==true){fetched = false;
                  //  alphabetName.setText(""+fetched);
                    //object.accessData(++index,capsLetterList,letterNameList,capsLetter,capsLetterDirectory);
                    //alphabetName.setText(""+fetched);
                object.accessData(++index,alphabetImageList,alphabetNameList,alphabetName,alphabetImage,alphabetImageDirectory);
                //}

                //object.accessData(index,alphabetImageList,alphabetNameList,alphabetImage,alphabetImageDirectory);
                //object.speakout(alphabetNameList.get(index).toString());
                //alphabetName.setText(alphabetNameList.get(++index).toString());
            }
            @Override
            public void onSwipeRight() {
                if (index == 0) index = totalLetters;

                //object.accessData(--index,smallLetterList,letterNameList,smallLetter,smallLetterDirectory);
                //alphabetName.setText(""+fetched);
                //if(fetched == true){fetched = false;
                  //  alphabetName.setText(""+fetched);
                    //object.accessData(--index,capsLetterList,letterNameList,capsLetter,capsLetterDirectory);
                   // alphabetName.setText(""+fetched);
                //}
                //object.accessData(index,alphabetImageList,alphabetNameList,alphabetImage,alphabetImageDirectory);
                //object.speakout(alphabetNameList.get(index).toString());
                //alphabetName.setText(alphabetNameList.get(--index).toString());
                object.accessData(--index,alphabetImageList,alphabetNameList,alphabetName,alphabetImage,alphabetImageDirectory);
            }
        });



        smallLetterList = new ArrayList();
        capsLetterList = new ArrayList();
        alphabetImageList = new ArrayList();
        letterNameList = new ArrayList();
        alphabetNameList = new ArrayList();

        getReference();

        LoadList();
        //LoadList(capsRef,capsLetterList,letterNameList,capsLetterDirectory,capsLetter);
        //LoadList(alphabetRef,alphabetImageList,alphabetNameList,alphabetImageDirectory,alphabetImage);

    }

    public void LoadList(){
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot urlSnapshot : dataSnapshot.getChildren()){
                    String data = urlSnapshot.getValue().toString();
                    String key = urlSnapshot.getKey().toString();
                    alphabetImageList.add(data);
                    alphabetNameList.add(key);

                }
                if(alphabetImageList.isEmpty()){
                    Toast.makeText(baby_a_i.this, "Nothing fetched", Toast.LENGTH_SHORT).show();
                }else{
                    totalLetters = alphabetImageList.size();
                    object = new dataAccessClass(baby_a_i.this);
                    object.accessData(index,alphabetImageList,alphabetNameList,alphabetName,alphabetImage,alphabetImageDirectory);
                    /*for(int i=0;i<nameList.size();i++){
                        System.out.println("key"+nameList.get(i).toString()
                        );
                        Log.e("value",urllist.get(i).toString());
                    }*/
                    //alphabetName.setText(nameList.get(index).toString());

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
        mRef = referenceClass.getFirebaseReference("alpha");
        mRef.keepSynced(true);
        smallRef = mRef.child("small");
        smallRef.keepSynced(true);
        capsRef = mRef.child("caps");
        capsRef.keepSynced(true);
        alphabetRef = mRef.child("pics");
        alphabetRef.keepSynced(true);
        smallLetterDirectory = referenceClass.getOfflineReference("a_i small letters");
        capsLetterDirectory = referenceClass.getOfflineReference("a_i caps letters");
        alphabetImageDirectory = referenceClass.getOfflineReference("a_i images");
    }
}
