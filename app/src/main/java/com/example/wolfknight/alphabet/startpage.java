package com.example.wolfknight.alphabet;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class startpage extends AppCompatActivity {

    TextView mAddChild;
    reference_class referenceClass;
    static File directory;
    static File[] users;
    ArrayList userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startpage);

        mAddChild = (TextView)findViewById(R.id.add_a_child);

        mAddChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),registration.class));
            }
        });

        getReference();
        users = directory.listFiles();
        getUsers();

    }

    public void getReference(){

        referenceClass = new reference_class(this);
        directory = referenceClass.getOfflineReference("profile");
    }
    public void getUsers(){
        if(!(users.length==0)){
            startActivity(new Intent(this,continue_Activity.class));
        }
    }
}
