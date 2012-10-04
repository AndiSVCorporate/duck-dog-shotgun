package com.dds;

import java.util.Random;

import org.cocos2d.nodes.CCDirector;

import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;

public class Route 
{
	private static int vert = 10;
	
	private int hor;
	private double stepSize; // Value based on screen size (screen width / x -> x = amount of horizontal steps)
	private int step = 0; // Current position
	private Point[] stops;
	
	public Route(int s)
	{
		Context c = CCDirector.theApp;
		DisplayMetrics metrics = c.getResources().getDisplayMetrics();
		
		//CGSize winSize = CCDirector.sharedDirector().displaySize();
		
		int height = metrics.heightPixels;
		int width = metrics.widthPixels;
		
		stepSize = GameLayer.px2dp(metrics.densityDpi, width) / vert;
		hor = (int) (GameLayer.px2dp(metrics.densityDpi, height) / 2 / stepSize);
		
		// Start position
		Random r = new Random();
		int x = (int) (Math.min(vert - 1, Math.max(0, r.nextInt(vert + hor * 2) - hor)) * GameLayer.dp2px(metrics.densityDpi, stepSize));
		int y = 0;
		if (x == 0 || x == (int) ((vert - 1) * GameLayer.dp2px(metrics.densityDpi, stepSize)))
		{
			y = (int) (r.nextInt(hor) * GameLayer.dp2px(metrics.densityDpi, stepSize));
		}
		
		stops = new Point[s + 2];
		stops[0] = new Point(x, y);
		
		// *s* random positions
		for (int i = 0; i < s; i++)
		{
			while (x == stops[i].x && y == stops[i].y)
			{
				x = (int) (r.nextInt(vert) * GameLayer.dp2px(metrics.densityDpi, stepSize));
				y = (int) (r.nextInt(hor) * GameLayer.dp2px(metrics.densityDpi, stepSize));
				stops[i + 1] = new Point(x, y);
			}
		}
		
		// End position
		while (x == stops[s].x && y == stops[s].y)
		{
			x = (int) (Math.min(vert - 1, Math.max(0, r.nextInt(vert + hor * 2) - hor)) * GameLayer.dp2px(metrics.densityDpi, stepSize));
			y = -50;
			if (x == 0)
			{
				x = -50;
				y = (int) (r.nextInt(hor) * GameLayer.dp2px(metrics.densityDpi, stepSize));
			}
			else if (x == (int) ((vert - 1) * GameLayer.dp2px(metrics.densityDpi, stepSize)))
			{
				x += 50;
				y = (int) (r.nextInt(hor) * GameLayer.dp2px(metrics.densityDpi, stepSize));
			}
			
			stops[s + 1] = new Point(x, y);
		}
	}
	
	public Point[] getStops()
	{
		return stops;
	}
	
	public boolean hasNext()
	{
		return (step < getStops().length - 1);
	}
	
	public Point next()
	{
		if (hasNext())
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
		if (hasNext())
		{
			return Math.sqrt(Math.abs(Math.pow(s[step].x - s[step + 1].x, 2)) + Math.abs(Math.pow(s[step].y - s[step + 1].y, 2))); 
		}
		else 
		{
			return 0;
		}
	}
	
	public double getDirectionToNextPoint()
	{
		Point[] s = getStops();
		if (hasNext())
		{
			double dir;
			try
			{
				dir = Math.toDegrees(Math.atan((s[step].x - s[step + 1].x) / (s[step].y - s[step + 1].y))); 
			}
			catch (ArithmeticException e)
			{
				dir = 0;
			}
			
			if (s[step].x - s[step + 1].x < 0)
			{
				dir += 180;
			}
			
			return dir;
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
