package com.dds;

import android.util.Log;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;
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
    protected CGSize winSize;
    public static int health = 5;

    public Dog(String path) 
    {
        super(path);
        
        this.winSize = CCDirector.sharedDirector().displaySize();

        this.setScaleX(GameLayer.scale);

        this.setScaleY(GameLayer.scale);

        int x = (int) this.winSize.width /2;
        int y = (int) (this.getContentSize().getHeight() * GameLayer.scale) /2;


        setPosition(x, y);
        
        SensorManager sensorManager = (SensorManager)CCDirector.sharedDirector().getActivity().getSystemService("sensor");
        Sensor accelerometer = sensorManager.getDefaultSensor(1);
        int accelerometerUpdateRate = 1;
        sensorManager.registerListener(this, accelerometer, accelerometerUpdateRate);
    }

	public void onAccuracyChanged(Sensor s, int i) {}

	public void onSensorChanged(SensorEvent event) 
	{
		if(event.sensor.getType() == 1)
        {
            ccAccelerometerChanged(event.values[0], event.values[1], event.values[2]);
        }
	}
	
	public void ccAccelerometerChanged(float f1, float f2, float f3)
    {
        CGPoint position = this.getPosition();

        int speed;

        speed = (int) (8 / (1 / Math.abs(f1)));
//        speed = (int) (120 * (1 / Math.abs(f1)));

        if(f1 < -0.8 && !(position.x > winSize.getWidth() - this.getContentSize().getWidth()/2))
        {
            this.setPosition(this.getPosition().x+speed, this.getPosition().y);
//            this.stopAllActions();
//            CCMoveTo actionMove = CCMoveTo.action(0.1f, CGPoint.ccp(this.getPosition().x+speed, this.getPosition().y));
//            runAction(actionMove);

        }
        else if(f1 > 0.8 && !(position.x < 0 + getContentSize().width/2))
        {
            this.setPosition(this.getPosition().x-speed, this.getPosition().y);
//            this.stopAllActions();
//            CCMoveTo actionMove = CCMoveTo.action(0.1f, CGPoint.ccp(this.getPosition().x-speed, this.getPosition().y));
//            runAction(actionMove);
        }
    }
}