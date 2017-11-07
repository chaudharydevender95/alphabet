package com.example.wolfknight.alphabet;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;

public class home_page extends AppCompatActivity implements View.OnClickListener{

    ImageView lessonButton;
    static Bitmap bitmap;
    CircleImageView circleImageView;
    com.example.wolfknight.alphabet.dataAccessClass dataAccessClass;
    File directory;
    reference_class referenceClass;
    String name,Email;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);


        SharedPreferences sharedPreferences = getSharedPreferences("MyData",MODE_PRIVATE);
        name = sharedPreferences.getString("name","").toString();
        Email = sharedPreferences.getString("password","").toString();

        lessonButton = (ImageView) findViewById(R.id.lessonButton);

        circleImageView  = (CircleImageView)findViewById(R.id.circleImageView);
        dataAccessClass = new dataAccessClass(this);
        dataAccessClass.loadImageFromStorage(circleImageView, startpage.directory.getAbsolutePath(), continue_Activity.userName);

        lessonButton.setOnClickListener(this);
    }


    public void getReference(){

        referenceClass = new reference_class(this);
        directory = referenceClass.getOfflineReference("profile");
    }

    public void getImage() {


    }

    @Override
    public void onClick(View v) {
        if(v==lessonButton){
            startActivity(new Intent(this,category.class));
        }
    }
}

