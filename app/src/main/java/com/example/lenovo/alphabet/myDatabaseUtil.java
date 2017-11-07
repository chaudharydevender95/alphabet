package com.example.lenovo.alphabet;

import android.app.Activity;
import android.content.Intent;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by lenovo on 6/10/2017.
 */

public class myDatabaseUtil extends Activity{
    private static FirebaseDatabase mDatabase;

    public static FirebaseDatabase getDatabase() {
        if (mDatabase == null) {
            mDatabase = FirebaseDatabase.getInstance();
            mDatabase.setPersistenceEnabled(true);
            // ...
        }

        return mDatabase;

    }


}
