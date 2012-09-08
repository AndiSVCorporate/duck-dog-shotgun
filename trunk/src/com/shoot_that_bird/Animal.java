package com.shoot_that_bird;

/*import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;*/
import android.graphics.Point;

abstract class Animal 
{
	private int speed;
	private Point position;
	private Dimension size;
	
	public Animal(int sp, Point p, Dimension si)
	{
		setSpeed(sp);
		setPosition(p);
		setSize(si);
	}
	
	public void setSpeed(int speedIn)
	{
		speed = speedIn;
	}
	
	public void setPosition(Point p)
	{
		position = p;
	}
	
	public void setSize(Dimension s)
	{
		size = s;
	}
	
	public int getSpeed()
	{
		return speed;
	}
	
	public Point getPosition()
	{
		return position;
	}
	
	public Dimension getSize()
	{
		return size;
	}
	
	abstract void move(Point p);
	
	/*private Bitmap decodeFile(File f, Dimension maxSize)
    {
        Bitmap b = null;
        try 
        {
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;

            FileInputStream fis = new FileInputStream(f);
            BitmapFactory.decodeStream(fis, null, o);
            fis.close();

            int scale = 1;
            if (o.outHeight > maxSize.getHeight() || o.outWidth > maxSize.getWidth()) 
            {
                scale = (int) Math.max(Math.pow(2, (int) Math.round(Math.log(maxSize.getHeight() / o.outHeight) / Math.log(0.5))), Math.pow(2, (int) Math.round(Math.log(maxSize.getWidth() / o.outWidth) / Math.log(0.5))));
            }

            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            fis = new FileInputStream(f);
            b = BitmapFactory.decodeStream(fis, null, o2);
            fis.close();
        } 
        catch (FileNotFoundException e) {} 
        catch (IOException e) {}
        
        return b;
    }*/
}
