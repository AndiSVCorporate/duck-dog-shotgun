package com.shoot_that_bird;

import com.example.shoot_that_bird.R;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}


/**

// How to draw an image which is included in the project, replace *image_name* with the name of the image
ImageView imageView = new ImageView(this);
Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.*image_name*);
imageView.setImageBitmap(bm);

*/