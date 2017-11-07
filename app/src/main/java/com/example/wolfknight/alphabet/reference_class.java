package com.example.wolfknight.alphabet;

import android.content.Context;
import android.content.ContextWrapper;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.util.LinkedHashMap;

/**
 * Created by lenovo on 3/30/2017.
 */

public class reference_class {

    static Context ctx ;

    public reference_class(Context c){
        ctx = c;
    }

    public DatabaseReference getFirebaseReference(String folder){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseDatabase.setPersistenceEnabled(true);
        DatabaseReference mRef = firebaseDatabase.getReferenceFromUrl("https://alphabet-8d5ca.firebaseio.com/");
        DatabaseReference mRefChild = mRef.child(folder);

        return mRefChild;
    }

    public File getOfflineReference(String folder){
        //Offline database Reefrence
        ContextWrapper cw = new ContextWrapper(ctx);
        // path to /data/data/yourapp/app_data/imageDir
        File userdirectory = cw.getDir("users", Context.MODE_PRIVATE);
        File usersdirectory = new File(userdirectory,"username");
        File directory = new File(usersdirectory,folder);
        directory.mkdirs();
        return  directory;
    }

    public LinkedHashMap getReferenceData(DatabaseReference mRef){

        final LinkedHashMap map = new LinkedHashMap();
        System.out.print("Insied ");
        mRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot urlSnapshot : dataSnapshot.getChildren()) {
                    String data = urlSnapshot.getValue().toString();
                    String key = urlSnapshot.getKey().toString();
                    map.put(key, data);
                    //urllist.add(data);
                    //relationship.add(key);

                }
                if(!map.isEmpty()){
                    Toast.makeText(ctx,"Nothing has been fetched",Toast.LENGTH_LONG).show();
                }
                //text.setText("List is empty");

            }




            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ctx, "Database error", Toast.LENGTH_LONG).show();
            }

        });
        return map;
    }
}