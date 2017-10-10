package com.goryn.testtaskeuropemap;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private HorizontalScrollView svHorizontal;
    private ScrollView svVertical;
    private Map<String, String> countriesMap;
    boolean firstLaunch = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        countriesMap = Constants.COUNTRIES_MAP;


        ImageView imageView = (ImageView) findViewById(R.id.iv_map);

        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        return true;

                    case MotionEvent.ACTION_UP:
                        ImageView image = (ImageView) view;

                        Matrix inverse = new Matrix();
                        image.getImageMatrix().invert(inverse);


                        float[] touchPoint = new float[]{event.getX(), event.getY()};
                        inverse.mapPoints(touchPoint);


                        Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
                        int pixel = bitmap.getPixel((int) touchPoint[0], (int) touchPoint[1]);

                        //then do what you want with the pixel data, e.g
                        int redValue = Color.red(pixel);
                        int blueValue = Color.blue(pixel);
                        int greenValue = Color.green(pixel);

                        Log.i("COLOR", "" + redValue + " " + greenValue + " " + blueValue);
                        Log.i("COLOR", fromRGBToHex(redValue, greenValue, blueValue));
                        checkColor(fromRGBToHex(redValue, greenValue, blueValue));
                }
                return false;
            }
        });

    }

    private void checkColor(String hex) {
        if (countriesMap.containsKey(hex)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(countriesMap.get(hex));
            builder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            Dialog dialog = builder.create();
            dialog.show();
        }
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if (firstLaunch){
            centerScrollView();
            firstLaunch = false;
        }

        super.onWindowFocusChanged(hasFocus);
    }

    private String fromRGBToHex(int r, int g, int b) {
        return String.format("#%02x%02x%02x", r, g, b);
    }


    private void centerScrollView() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        svHorizontal = (HorizontalScrollView) findViewById(R.id.sv_horizontal);
        svVertical = (ScrollView) findViewById(R.id.sv_vertical);

        svVertical.scrollTo(0, size.y / 2);
        svHorizontal.scrollTo(size.x / 2, 0);
    }

}
