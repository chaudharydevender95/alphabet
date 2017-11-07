package com.example.wolfknight.alphabet;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Matrix.ScaleToFit;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.ImageWriter;
import android.os.AsyncTask;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by lenovo on 3/29/2017.
 */

public class dataAccessClass extends AppCompatActivity implements TextToSpeech.OnInitListener{

    ArrayList relationship,urllist1,urllist2;
    TextView relationName;
    File directory;
    Context ctx;
    ImageView relativepic,secondImage;
    ProgressDialog mProgressDialog;
    TextToSpeech tts;
    int index;
    String relation=" ";
    Boolean first=false,second=false;

    public dataAccessClass(Context c)
    {
        ctx = c;
        tts = new TextToSpeech(ctx,this);
    }

    public void accessData(int index,ArrayList urlList,ArrayList name,View textView,View imageView,File internal_reference){

        relationship = name;
        urllist1 = urlList;
        relativepic = (ImageView) imageView;
        relationName = (TextView) textView;
        directory = internal_reference;

        relation = relationship.get(index).toString();
        relationName.setText(relation);
        speakout(relation);
        File mypath=null;
        try {
            mypath = new File(directory, relation);
        }
        catch (Exception e){}
        if(mypath.exists()) {}//loadImageFromStorage(relativepic,directory.getAbsolutePath(),relation);
        else {
            new DownloadImage().execute(urllist1.get(index).toString());

        }


    }

    public void accessData(int index,ArrayList urlList,ArrayList name,View imageView,File internal_reference){
        relationship = name;
        urllist1 = urlList;
        directory = internal_reference;
        relativepic = (ImageView)imageView;

        relation = relationship.get(index).toString();

        File mypath=null;
        try {
            mypath = new File(directory, relation);
        }
        catch (Exception e){}
        if(mypath.exists()){}// loadImageFromStorage(relativepic,directory.getAbsolutePath(),relation);
        else {
            new DownloadImage().execute(urllist1.get(index).toString());
        }
    }
    public void accessData(int index,ArrayList firstImageList,ArrayList secondImageList,ArrayList name,View imageView,View secondImageView,File internal_reference){
        relationship = name;
        urllist1 = firstImageList;
        urllist2 = secondImageList;
        directory = internal_reference;
        relativepic = (ImageView)imageView;
        secondImage = (ImageView)secondImageView;

        relation = relationship.get(index).toString();

        Picasso.with(ctx)
                .load(urllist1.get(index).toString())
                .into(relativepic);
        Picasso.with(ctx).load(urllist2.get(index).toString()).into(secondImage);



        /*File mypath=null;
        try {
            mypath = new File(directory, relation);
        }
        catch (Exception e){}
        if(mypath.exists()) loadImageFromStorage(relativepic,directory.getAbsolutePath(),relation);
        else {
            first = false;
            new DownloadImage().execute(urllist1.get(index).toString());
        if(first = true){
            new DownloadImage().execute(urllist2.get(index).toString());
        }
        //}*/
    }

    /*public Bitmap loadImage(File internal_reference,ArrayList urllist,ArrayList nameList){
        File directory = internal_reference;
        ArrayList list = urllist;
        ArrayList name = nameList;
        Bitmap bitmap;

        File mypath=null;
        try {
            mypath = new File(directory, relation);
        }
        catch (Exception e){}
        if(mypath.exists()) loadImageFromStorage(relativepic,directory.getAbsolutePath(),relation);
        else {
            new DownloadImage().execute(urllist.get(index).toString());
        }
        return bitmap;

    }*/

    public void accessData(int index,ArrayList urllist,View ImageView,File internal_reference){

    }

    public void accessText(int index,ArrayList names,View textView){
        TextView activityText = (TextView) textView;
        String text = names.get(index).toString();
        activityText.setText(text);
        speakout(text);
    }

    public void speak(String string){
        speakout(string);
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int language = tts.setLanguage(Locale.ENGLISH);

            if (language == TextToSpeech.LANG_MISSING_DATA || language == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("tts", "This language is not supported");
            } else {
                speakout(relation);
            }
        } else {
            Toast.makeText(getApplicationContext(), "TextToSpeech not initiated", Toast.LENGTH_LONG).show();
        }
    }

    public void speakout(String text) {
        tts.setPitch((float) (-5));
        tts.setSpeechRate((float) 1.0);
        tts.speak(" "+text, TextToSpeech.QUEUE_FLUSH, null);

        //destroy();
    }
    public void destroy(){
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
    }

    @Override
    public void onDestroy() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }

    // DownloadImage AsyncTask
    private class DownloadImage extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected void onPreExecute() {
            //relativepic.setImageResource(R.drawable.);
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(ctx);
            // Set progressdialog title
            mProgressDialog.setTitle("Download Image");
            // Set progressdialog message
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            //mProgressDialog.show();
        }

        @Override
        protected Bitmap doInBackground(String... URL) {

            String imageURL = URL[0];

            Bitmap bitmap = null;
            try {
                // Download Image from URL
                InputStream input = new java.net.URL(imageURL).openStream();
                // Decode Bitmap
                bitmap = BitmapFactory.decodeStream(input);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            getResizedBitmap(result);
            // Set the bitmap into ImageView
            if(first==false)
            relativepic.setImageBitmap(result);
            // Close progressdialog
            mProgressDialog.dismiss();
            //Store to internal Storege
            //saveToInternalStorage(directory,result,relation);

        }
    }

    public String saveToInternalStorage(File directory,Bitmap bitmap,String name){

        // Create imageDir
        File mypath=new File(directory,name);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            Log.e("Storing",""+mypath.getAbsolutePath());
            Toast.makeText(ctx,""+mypath.getAbsolutePath(),Toast.LENGTH_LONG).show();
            //Toast.makeText(, "", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("error","storege");
            Toast.makeText(ctx,"Error in Internal Storage",Toast.LENGTH_LONG).show();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
    }


    public static void saveToInternalStorage(File mypath,Bitmap bitmap){

        // Create imageDir
        //File mypath=new File(directory,name);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            Log.e("store",""+mypath.getAbsolutePath());
            //Toast.makeText(ctx,""+mypath.getAbsolutePath(),Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("store","Error in Internal Storage");
            //Toast.makeText(ctx,"Error in Internal Storage",Toast.LENGTH_LONG).show();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public Bitmap loadImageFromStorage(View image,String path,String imageName)
    {
        Bitmap bitmap=null;
        ImageView imageView = (ImageView)image;
        try {
            File f=new File(path,imageName);
            //if(f.exists()) Toast.makeText(ctx,"exist",Toast.LENGTH_SHORT).show();
            bitmap = BitmapFactory.decodeStream(new FileInputStream(f));
            //getResizedBitmap(bitmap);
            if(bitmap!=null)
                imageView.setImageBitmap(bitmap);

            //Toast.makeText(ctx,"Loading",Toast.LENGTH_SHORT).show();
        }
        catch (FileNotFoundException e)
        {
           e.printStackTrace();
            //Toast.makeText(ctx,"Error Occured",Toast.LENGTH_LONG).show();
        }

        return bitmap;
    }

    public Bitmap loadImageFromStorage(File f)
    {
        Bitmap bitmap=null;
        try {
            //File f=new File(path,imageName);
            bitmap = BitmapFactory.decodeStream(new FileInputStream(f));
            getResizedBitmap(bitmap);

            Toast.makeText(ctx,"Loading",Toast.LENGTH_SHORT).show();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
            Toast.makeText(ctx,"Error Occured",Toast.LENGTH_LONG).show();
        }
        return bitmap;
    }

    private Bitmap getResizedBitmap(Bitmap bitmap){

        DisplayMetrics metrics = ctx.getResources().getDisplayMetrics();
        int reqWidth = metrics.widthPixels;
        int reqHeight = metrics.heightPixels;
        Matrix matrix = new Matrix();

        RectF src = new RectF(0,0,bitmap.getHeight(),bitmap.getWidth());
        RectF dest = new RectF(0,0,reqHeight,reqWidth);

        matrix.setRectToRect(src ,dest, Matrix.ScaleToFit.CENTER);

        return Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
    }

    public void storeToFirebaseDatabase(DatabaseReference mref, LinkedHashMap map){
        mref.setValue(map);
    }
}
