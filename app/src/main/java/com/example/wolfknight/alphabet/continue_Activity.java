package com.example.wolfknight.alphabet;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by lenovo on 5/2/2017.
 */

public class continue_Activity extends AppCompatActivity implements View.OnClickListener{

    TextView addChild,switchChild;
    reference_class referenceClass;
    static File directory;
    CircleImageView currentUserImage;
    com.example.wolfknight.alphabet.dataAccessClass dataAccessClass;
    static String userName;
    static File[] users;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.continue_as);

        currentUserImage =(CircleImageView)findViewById(R.id.currentUserImage);
        addChild= (TextView)findViewById(R.id.add_child);
        switchChild = (TextView)findViewById(R.id.switch_child);

        addChild.setOnClickListener(this);
        switchChild.setOnClickListener(this);
        currentUserImage.setOnClickListener(this);

        getReference();

        users = directory.listFiles();
        String[] segments = users[0].getAbsolutePath().split("/");
        userName = segments[segments.length-1];

        dataAccessClass = new dataAccessClass(this);

        dataAccessClass.loadImageFromStorage(currentUserImage,directory.getAbsolutePath(),userName );
    }


    @Override
    public void onClick(View v) {
        if(v==addChild){
            startActivity(new Intent(this,registration.class));
        }
        if(v==switchChild){
            startActivity(new Intent(this,select_Child.class));
        }
        if(v==currentUserImage){
            startActivity(new Intent(getApplicationContext(), home_page.class));
        }
    }

    public void getReference(){

        referenceClass = new reference_class(this);
        directory = referenceClass.getOfflineReference("profile");
    }
}
