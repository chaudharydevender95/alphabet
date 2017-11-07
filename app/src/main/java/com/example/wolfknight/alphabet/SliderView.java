package com.example.wolfknight.alphabet;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by lenovo on 5/6/2017.
 */

public class SliderView extends View {

    int mSliderColor,mSliderBackgroundColor;

    public SliderView(Context context) {
        super(context);
    }

    public SliderView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SliderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public SliderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void init(Context context,AttributeSet attrs,int defStyleRes){
        TypedArray array = context.obtainStyledAttributes(attrs,R.styleable.SliderView,defStyleRes,0);

        try{
            mSliderColor = array.getColor(R.styleable.SliderView_sliderColor,0);
            mSliderBackgroundColor = array.getColor(R.styleable.SliderView_sliderBackground,0);
        }catch (Exception e){

        }finally {
            array.recycle();
        }
        setFocusable(true);
        setFocusableInTouchMode(true);
        setupPaint();
    }

    private final int paintColor = Color.BLACK;
    // defines paint and canvas
    private Paint drawPaint;
    // stores next circle
    private Path path = new Path();



    private void setupPaint() {
        // Setup paint with color and stroke styles
        drawPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        drawPaint.setColor(paintColor);
        drawPaint.setAntiAlias(true);
        drawPaint.setStrokeWidth(15);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawPath(path, drawPaint);

        canvas.drawCircle(50, 50, 20, drawPaint);
        drawPaint.setColor(Color.GREEN);
        canvas.drawCircle(50, 150, 20, drawPaint);
        drawPaint.setColor(Color.BLUE);
        canvas.drawCircle(50, 250, 20, drawPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float pointX = event.getX();
        float pointY = event.getY();
        // Checks for the event that occurs
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                path.moveTo(pointX, pointY);
                return true;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(pointX, pointY);
                break;
            default:
                return false;
        }
        // Force a view to draw again
        postInvalidate();
        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        setMeasuredDimension(getMeasuredWidth(),getMeasuredHeight());
    }
}
