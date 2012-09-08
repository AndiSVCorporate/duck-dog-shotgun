package com.shoot_that_bird;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.example.shoot_that_bird.R;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
	
	private static Bitmap decodeFile(String path)
    {
        BitmapFactory.Options options = new BitmapFactory.Options();
		Bitmap bm;
		try
		{
			options.inSampleSize = 2;
			bm = BitmapFactory.decodeFile(path, options);
		}
		catch (Exception e)
		{
			options.inSampleSize = 16;
			bm = BitmapFactory.decodeFile(path, options);
		}
		
		return bm;
    }
	
	private static Bitmap scaleBitmap(Bitmap bm, Dimension max)
	{
		double dHeight = bm.getHeight();
		double dWidth = bm.getWidth();
		
		double scale = 1;
		if (dHeight > max.getHeight())
		{
			scale = max.getHeight() / dHeight;
			dHeight *= scale;
			dWidth *= scale;
		}
		
		if (dWidth > max.getWidth())
		{
			scale = max.getWidth() / dWidth;
			dHeight *= scale;
			dWidth *= scale;
		}
		
		return Bitmap.createScaledBitmap(bm, (int) dWidth, (int) dHeight, false);
	}
}


/**

// How to draw an image which is included in the project, replace *image_name* with the name of the image
ImageView imageView = new ImageView(this);
Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.*image_name*);
imageView.setImageBitmap(bm);


// How to draw an image which is not included in the project, replace *path* with the path of the image (probably won't need this)
ImageView imageView = new ImageView(this);
Bitmap bm = MainActivity.decodeFile(*path*);
imageView.setImageBitmap(bm);


// How to scale an image with a maximum height of *height* and a maximum width of *width*
MainActivity.scaleBitmap(bm , new Dimension(*height*, *width*));

*/