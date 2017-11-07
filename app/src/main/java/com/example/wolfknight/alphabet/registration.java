package com.example.wolfknight.alphabet;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Locale;

import br.com.bloder.magic.view.MagicButton;
import de.hdodenhof.circleimageview.CircleImageView;

public class registration extends AppCompatActivity implements View.OnClickListener{

    private static final int PICK_IMAGE_REQUEST = 123;
    private static final int CAMERA_REQUEST = 456;
    private static final int GALLERY_KITKAT_INTENT_CALLED = 789 ;
    ImageView circleImageView,gallery,camera;
    Uri filepath;
    Boolean imageselected = false,allfields=false;
    Bitmap profileImageBitmap;
    EditText name,nickname,gaurdian_email;
    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;
    LinkedHashMap map;
    RadioGroup radioSexgroup;
    RadioButton radioSexButton;
    String email;
    TextView dob;
    MagicButton magicButton;
    Intent cam_intent;
    reference_class referenceClass;
    com.example.wolfknight.alphabet.dataAccessClass dataAccessClass;
    File directory;
    DatabaseReference mfdbRef,mRef,mNameRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        magicButton = (MagicButton) findViewById(R.id.magic_button);
        circleImageView = (CircleImageView)findViewById(R.id.circleImageView);
        camera = (ImageView)findViewById(R.id.camera);
        gallery = (ImageView)findViewById(R.id.gallery);

        name = (EditText)findViewById(R.id.name);
        dob = (TextView) findViewById(R.id.dateOfBirth);
        nickname = (EditText)findViewById(R.id.nickName);
        radioSexgroup = (RadioGroup)findViewById(R.id.radioGroup);
        gaurdian_email = (EditText)findViewById(R.id.etemail);

        magicButton.setMagicButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!imageselected)Toast.makeText(getApplicationContext(),"Select Image.",Toast.LENGTH_LONG).show();
                else{
                    generateData();
                    if(allfields) {
                        dataAccessClass.saveToInternalStorage(directory, profileImageBitmap,nickname.getText().toString());
                        dataAccessClass.storeToFirebaseDatabase(mNameRef, map);
                        SharedPreferences sharedPreferences = getSharedPreferences("MyData",MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("name",nickname.getText().toString());
                        editor.putString("email",email);
                        editor.commit();
                        startActivity(new Intent(getApplicationContext(), home_page.class));
                    }
                }
            }
        });
        myCalendar = Calendar.getInstance();

        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        gallery.setOnClickListener(this);
        camera.setOnClickListener(this);
        dob.setOnClickListener(this);
        radioSexgroup.setOnClickListener(this);

        //Get Reference to database
        getReference();

        //Store image offline
        dataAccessClass = new dataAccessClass(this);
    }

    public void generateData(){
        map = new LinkedHashMap();
        int itemSelected = radioSexgroup.getCheckedRadioButtonId();
        radioSexButton = (RadioButton)findViewById(itemSelected);

        String Name = name.getText().toString();
        email = gaurdian_email.getText().toString().replace(".","_");
        String dateob=null;
        dateob = dob.getText().toString();
        String gender=null;
        try {
            gender = radioSexButton.getTag().toString();
        }catch (Exception e){

        }
        String nickName = nickname.getText().toString();
        if(TextUtils.isEmpty(Name)) Toast.makeText(getApplicationContext(),"Name is not filled.",Toast.LENGTH_SHORT).show();
        else {
            map.put("Name", name.getText().toString());
            if(TextUtils.isEmpty(email)) Toast.makeText(getApplicationContext(),"Email is not filled.",Toast.LENGTH_SHORT).show();
            else {
                map.put("Gaurdian's Email",email);
                mRef = mfdbRef.child(email);
                mNameRef = mRef.child(Name);
                if(TextUtils.isEmpty(dateob))Toast.makeText(getApplicationContext(),"Date of Birth is not filled.",Toast.LENGTH_SHORT).show();
                else{
                    map.put("DOB",dateob);
                    if(TextUtils.isEmpty(gender))Toast.makeText(getApplicationContext(),"Boy/Girl.",Toast.LENGTH_SHORT).show();
                    else{
                        map.put("Gender",gender);
                        if(TextUtils.isEmpty(nickName))Toast.makeText(getApplicationContext(),"Nick Name is not filled.",Toast.LENGTH_SHORT).show();
                        else{
                            map.put("Nick Name",nickName);
                            allfields = true;
                        }
                    }
                }
            }
        }
    }

    public void getReference(){

        referenceClass = new reference_class(this);
        mfdbRef = referenceClass.getFirebaseReference("users");
        directory = referenceClass.getOfflineReference("profile");
    }

    @Override
    public void onClick(View v) {
        if(v == gallery){
            Intent i = new Intent();
            i.setType("image/*");
            i.setAction(i.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(i, "Select Picture"), PICK_IMAGE_REQUEST);

        }

        if(v == camera){
            cam_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (cam_intent.resolveActivity(getPackageManager()) != null)
                startActivityForResult(cam_intent, CAMERA_REQUEST);
        }

        if(v==dob){
            new DatePickerDialog(this,date, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {

                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));
                profileImageBitmap = bitmap;
                circleImageView.setImageBitmap(bitmap);
                imageselected = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            //filepath = data.getData();

        profileImageBitmap = (Bitmap) data.getExtras().get("data");
        circleImageView.setImageBitmap(profileImageBitmap);
        }

    }

    private void updateLabel() {

        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        dob.setText(sdf.format(myCalendar.getTime()));
    }

}

