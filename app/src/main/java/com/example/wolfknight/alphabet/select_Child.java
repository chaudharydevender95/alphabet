package com.example.wolfknight.alphabet;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class select_Child extends AppCompatActivity {

    LinearLayout linearLayout;
    RelativeLayout root;
    LinearLayout.LayoutParams params;
    reference_class referenceClass;
    File[] users;
    ArrayList<String> userList;
    ArrayList<Bitmap> userImageList;
    File directory;
    ImageView appIcon;
    ListView userslist;
    static com.example.wolfknight.alphabet.dataAccessClass dataAccessClass;
    int imageSize,screenWidth,screenHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select__child);

        root = (RelativeLayout)findViewById(R.id.root);
        appIcon = (ImageView)findViewById(R.id.appIcon);

        userList = new ArrayList<>();
        userImageList = new ArrayList<>();
        userslist = (ListView)findViewById(R.id.userList);

        dataAccessClass = new dataAccessClass(this);

        getSizeAttributes();

        getReference();

        getUsers();

        customList adapter = new customList(select_Child.this,userList,directory);
        userslist.setAdapter(adapter);

        userslist.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(select_Child.this, "You Clicked at " +userList.get(position), Toast.LENGTH_SHORT).show();

            }
        });

    }

    public void getSizeAttributes(){
        DisplayMetrics metrics = getApplicationContext().getResources().getDisplayMetrics();
        screenWidth = metrics.widthPixels;
        screenHeight = metrics.heightPixels;
        imageSize = screenWidth/8;
    }

    public void getReference(){

        referenceClass = new reference_class(this);
        directory = referenceClass.getOfflineReference("profile");
    }


    public void getUsers(){

        users = directory.listFiles();

        for(File userName : users){
            String[] segments = userName.getAbsolutePath().split("/");
            String user = segments[segments.length-1];
            userList.add(user);

        }
    }
}
