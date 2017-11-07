package com.example.wolfknight.alphabet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import de.hdodenhof.circleimageview.CircleImageView;

public class category extends AppCompatActivity implements View.OnClickListener{

    CircleImageView headerImage;
    ImageView toddler,baby,infant,child,kiddle;
    static String category = null;
    com.example.wolfknight.alphabet.dataAccessClass dataAccessClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        toddler = (ImageView)findViewById(R.id.toddler);
        baby = (ImageView)findViewById(R.id.Baby);
        infant = (ImageView)findViewById(R.id.Infant);
        child = (ImageView)findViewById(R.id.child);
        kiddle = (ImageView)findViewById(R.id.kiddle);
        headerImage = (CircleImageView)findViewById(R.id.headerImage);
        //headerImage.setImageBitmap(home_page.bitmap);
        //dataAccessClass = new dataAccessClass(this);
        //dataAccessClass.loadImageFromStorage(headerImage, startpage.directory.getAbsolutePath(),continue_Activity.userName );

        toddler.setOnClickListener(this);
        baby.setOnClickListener(this);
        infant.setOnClickListener(this);
        child.setOnClickListener(this);
        kiddle.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v==toddler){
            category = "toddler";

        }
        if(v==baby){
            category = "baby";
            //Intent intent = new Intent(this,lesson.class);
            //startActivity(intent);
        }
        if(v==infant){
            category = "infant";
        }
        if(v==kiddle){
            category = "kiddie";
        }
        if(v==child){
            category = "toddler";
        }

        startActivity(new Intent(this,lesson.class));
    }
}
