package com.example.wolfknight.alphabet;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;

public class AddFamilyMember extends AppCompatActivity implements View.OnClickListener{

    private static final int PICK_IMAGE_REQUEST = 234;
    ImageView relativepic;
    Button savebutton;
    EditText relativeEditText;
    boolean imageselected = false;
    private StorageReference storageReference;
    Uri filepath;
    TextView text;
    String downloadingurl=null;
    FloatingActionButton addMember;
    dataAccessClass access_object;
    reference_class referenceClass;
    File directory,userDirectory;
    Bitmap bitmap;
    FirebaseDatabase database;
    DatabaseReference mRef,email,name,relatives;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_family_member);

        relativepic = (ImageView) findViewById(R.id.relativeImage);
        savebutton = (Button) findViewById(R.id.savebutton);
        relativeEditText = (EditText) findViewById(R.id.relativeEditText);

        access_object = new dataAccessClass(this);


        savebutton.setOnClickListener(this);
        relativepic.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        if (v == relativepic) {
            Intent i = new Intent();
            i.setType("image/*");
            i.setAction(i.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(i, "Select an image"), PICK_IMAGE_REQUEST);
        }
        if (v == savebutton) {
            storedata();
        }
    }

    //Function for storing image and text
    private void storedata() {
        //final String downloadingurl;
       final String text = relativeEditText.getText().toString();
       if (imageselected != true) {
            Toast.makeText(getApplicationContext(), "Image not Selected", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this,"first",Toast.LENGTH_SHORT).show();
                if (TextUtils.isEmpty(text)) {
                    Toast.makeText(getApplicationContext(), "Enter Relation", Toast.LENGTH_LONG).show();
                } else {

                    storageReference = FirebaseStorage.getInstance().getReference();
                    StorageReference riversRef = storageReference.child("images/" + text);
                    /*ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos);
                    byte[] data = baos.toByteArray();*/
                    riversRef.putFile(filepath)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                @SuppressWarnings("VisibleForTests") Uri downloadUrl = taskSnapshot.getDownloadUrl();
                                downloadingurl = downloadUrl.toString();

                                Toast.makeText(getApplicationContext(), "Successful", Toast.LENGTH_LONG).show();
                                if (downloadingurl == null) {
                                    Toast.makeText(getApplicationContext(), "String is not generated", Toast.LENGTH_LONG).show();
                                } else {
                                    //access_object.saveToInternalStorage(family.directory, bitmap, text);

                                    family.relatives.child(text).setValue(downloadingurl);
                                    startActivity(new Intent(AddFamilyMember.this, family.class));

                                }
                            }
                        });
                //Store downloadurl to realtime database
                if (downloadingurl == null) {
                    Toast.makeText(getApplicationContext(), "String is not generated", Toast.LENGTH_LONG).show();
                } else {
                    //access_object.saveToInternalStorage(family.directory, bitmap, text);

                    family.relatives.child(text).setValue(downloadingurl);
                    startActivity(new Intent(this, family.class));

                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filepath = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filepath);
                relativepic.setImageBitmap(bitmap);
                imageselected = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
