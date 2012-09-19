package com.dds;

import android.util.Log;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGSize;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * @author Wouter
 * Date: 17-09-12
 * Time: 12:01
 */
public class Dog extends CCSprite implements SensorEventListener 
{

    public Dog(String path) 
    {
        super(path);
        
        CGSize winSize = CCDirector.sharedDirector().displaySize();

        int x = (int) winSize.width /2;
        int y = (int) this.getContentSize().getHeight() /2;

        setPosition(x, y);
        
        SensorManager sensorManager = (SensorManager)CCDirector.sharedDirector().getActivity().getSystemService("sensor");
        Sensor accelerometer = sensorManager.getDefaultSensor(1);
        int accelerometerUpdateRate = 1;
        sensorManager.registerListener(this, accelerometer, accelerometerUpdateRate);
    }

	public void onAccuracyChanged(Sensor s, int i) 
	{
        Log.e("Error", "WORKS!");
		
	}

	public void onSensorChanged(SensorEvent event) 
	{
		if(event.sensor.getType() == 1)
        {
            ccAccelerometerChanged(event.values[0], event.values[1], event.values[2]);
        }
	}
	
	public void ccAccelerometerChanged(float f1, float f2, float f3)
    {
		Log.e("Dog", f1 + " - " + f2 + " - " + f3);
    }
}
