package com.shoot_that_bird;

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
}
