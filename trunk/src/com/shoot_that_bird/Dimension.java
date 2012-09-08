package com.shoot_that_bird;

public class Dimension 
{
	private int width;
	private int height;
	
	public Dimension(int w, int h)
	{
		setWidth(w);
		setHeight(h);
	}
	
	public void setWidth(int w)
	{
		width = w;
	}
	
	public void setHeight(int h)
	{
		height = h;
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public int getHeight()
	{
		return height;
	}
}
