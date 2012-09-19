package com.dds;

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

	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	public void onSensorChanged(SensorEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
