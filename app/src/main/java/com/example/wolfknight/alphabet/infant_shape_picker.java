package com.example.wolfknight.alphabet;

import android.graphics.Rect;
import android.graphics.RectF;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class infant_shape_picker extends AppCompatActivity implements View.OnTouchListener{

    RelativeLayout rootLayout;
    TextView location;
    ImageView picker_Image;
    float prevX,prevY;
    int images[] = {R.id.imageView1,R.id.imageView2,R.id.imageView3,R.id.imageView4};
    Boolean notFound = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infant_shape_picker);

        rootLayout = (RelativeLayout)findViewById(R.id.rootLayout);
        picker_Image = (ImageView)findViewById(R.id.picker_Image);

        location = (TextView)findViewById(R.id.location);

        rootLayout.setOnTouchListener(this);

        initImages();
    }

    public void initImages(){
        for(int i=0;i<4;i++) {
            ImageView image = (ImageView) findViewById(images[i]);
            image.setOnTouchListener(this);
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event)
    {
        float pointX = event.getX()-135;
        float pointY = event.getY()-135;

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                location.setText(""+pointX +"  "+pointY);
                prevX = pointX;
                prevY = pointY;
                //path.moveTo(pointX, pointY);
                return true;
            case MotionEvent.ACTION_MOVE:
                location.setText(""+pointX +"  "+pointY);
                //RectF rect = new RectF(prevX-70 ,prevY-70,(prevX+70),(pointY+70));
                //if(!(rect.contains(pointX,pointY))) {
                    //path.lineTo(pointX, pointY);
                  //  prevX = pointX;
                   // prevY = pointY;
                    View findview = findViewAt(rootLayout, (int) pointX, (int) pointY);
                    if (findview != null) {//view.setText(""+findview.getTag());
                        int loc[] = new int[2];
                        String viewTag = findview.getTag().toString();
                        if (picker_Image.getTag().toString() == viewTag) {
                            picker_Image.setX(findview.getX());
                            picker_Image.setY(findview.getY());
                            notFound = false;
                        }
                        //view.setText(""+loc[0]+"  "+loc[1]);
                    }
                    else{
                        picker_Image.setX(pointX);
                        picker_Image.setY(pointY);
                    }
                if(notFound==true){
                                    }

                    break;

            default:
                return false;
        }
        v.postInvalidate();
        return true;
    }

    private View findViewAt(ViewGroup viewGroup, int x, int y) {

        for(int i = 0; i < viewGroup.getChildCount(); i++) {
            View child = viewGroup.getChildAt(i);
            int[] location = new int[2];
            child.getLocationOnScreen(location);
            Rect rect = new Rect(x - (child.getWidth()/2), y - (child.getHeight()/2), x + (child.getWidth()/10), y + (child.getWidth()/10));
            if (rect.contains((location[0]+20),(location[1])-135)) {
                //view.setText("tag = "+child.getTag()+"Id = "+child.getId());
                return child;

            }
        }
        return null;
    }

}
