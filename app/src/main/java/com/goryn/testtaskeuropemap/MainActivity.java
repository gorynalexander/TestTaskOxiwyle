package com.goryn.testtaskeuropemap;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView imageView = (ImageView) findViewById(R.id.iv_map);

        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {

                ImageView image = (ImageView) view;

                Matrix inverse = new Matrix();
                image.getImageMatrix().invert(inverse);

// map touch point from ImageView to image
                float[] touchPoint = new float[] {event.getX(), event.getY()};
                inverse.mapPoints(touchPoint);


                Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
                int pixel = bitmap.getPixel((int) touchPoint[0],(int) touchPoint[1]);

                //then do what you want with the pixel data, e.g
                int redValue = Color.red(pixel);
                int blueValue = Color.blue(pixel);
                int greenValue = Color.green(pixel);

                Log.i("COLRO", "" + redValue + " " + greenValue + " " + blueValue);
                Log.i("COLRO", fromRGBToHex(redValue, greenValue, blueValue));


                return false;
            }
        });

    }

    private String fromRGBToHex(int r, int g, int b){
        return String.format( "#%02x%02x%02x", r, g, b );
    }

}
