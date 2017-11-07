package com.example.lenovo.alphabet;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;

/**
 * Created by lenovo on 5/1/2017.
 */

public class sampleCodes {




    private Bitmap getResizedBitmap(Bitmap bitmap,int reqHeight,int reqWidth){
        Matrix matrix = new Matrix();

        RectF src = new RectF(0,0,bitmap.getHeight(),bitmap.getWidth());
        RectF dest = new RectF(0,0,reqHeight,reqWidth);

        matrix.setRectToRect(src ,dest,Matrix.ScaleToFit.CENTER);

        return Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
    }
}
