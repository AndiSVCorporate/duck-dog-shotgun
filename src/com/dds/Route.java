package com.dds;

import java.util.Random;

import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.util.Log;

public class Route 
{
	private static int vert = 10;
	
	private int hor;
	private double stepSize; // Value based on screen size (screen width / x -> x = amount of horizontal steps)
	private int step = 0; // Current position
	
	private Point[] stops;
	
	public Route(Context c, int s)
	{
		DisplayMetrics metrics = c.getResources().getDisplayMetrics();
		
		int height = metrics.heightPixels;
		int width = metrics.widthPixels;
		
		stepSize = px2dp(metrics.densityDpi, width) / vert;
		hor = (int) (px2dp(metrics.densityDpi, height) / 2 / stepSize);
		
		// Start position
		Random r = new Random();
		int x = Math.min(vert - 1, Math.max(0, r.nextInt(vert + hor * 2) - hor));
		int y = 0;
		if (x == 0 || x == vert - 1)
		{
			y = r.nextInt(hor);
		}
		
		stops = new Point[s + 2];
		stops[0] = new Point(x, y);
		
		// *s* random positions
		for (int i = 0; i < s; i++)
		{
			while (x == stops[i].x && y == stops[i].y)
			{
				x = r.nextInt(vert);
				y = r.nextInt(hor);
				stops[i + 1] = new Point(x, y);
			}
		}
		
		// End position
		while (x == stops[s].x && y == stops[s].y)
		{
			x = Math.min(vert - 1, Math.max(0, r.nextInt(vert + hor * 2) - hor));
			y = 0;
			if (x == 0 || x == vert - 1)
			{
				y = r.nextInt(hor);
			}
			
			stops[s + 1] = new Point(x, y);
		}
		Log.e("Route", toString());
	}
	
	public Point[] getStops()
	{
		return stops;
	}
	
	public double px2dp(double dpi, double px)
	{
		return px / (dpi / 160);
	}
	
	public Point next()
	{
		if (step < getStops().length)
		{
			step++;
			return getStops()[step];
		}
		else
		{
			return null;
		}
	}
	
	public double getTotalDistance()
	{
		double dis = 0;
		
		Point[] s = getStops();
		for (int i = 0; i < s.length - 1; i++)
		{
			Point p1 = s[i];
			Point p2 = s[i + 1];
			
			dis += Math.sqrt(Math.abs(Math.pow(p1.x - p2.x, 2)) + Math.abs(Math.pow(p1.y - p2.y, 2)));
		}
		
		return dis;
	}
	
	public double getDistanceToNextPoint()
	{
		Point[] s = getStops();
		if (step < s.length)
		{
			return Math.sqrt(Math.abs(Math.pow(s[step].x - s[step + 1].x, 2)) + Math.abs(Math.pow(s[step].y - s[step + 1].y, 2))); 
		}
		else 
		{
			return 0;
		}
	}
	
	public String toString()
	{
		String s = "";
		
		Point[] st = getStops();
		for (int i = 0; i < st.length; i++)
		{
			s += "Point(" + st[i].x + ", " + st[i].y + ")\n";
		}
		
		return s;
	}
}
