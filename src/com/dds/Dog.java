package com.dds;

import android.view.Surface;
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

    public static String playerImage;

    public Dog(String path) 
    {
        super(path);
        
        this.winSize = CCDirector.sharedDirector().displaySize();

        this.setScaleX(MainActivity.scale);

        this.setScaleY(MainActivity.scale);

        int x = (int) this.winSize.width /2;
        int y = (int) (this.getContentSize().getHeight() * MainActivity.scale) /2;


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
            float x, y;
            switch (GameLayer.rotation) {
                case Surface.ROTATION_90:
                    x = -event.values[1];
                    y = event.values[0];
                    break;
                case Surface.ROTATION_180:
                    x = event.values[0];
                    y = -event.values[1];
                    break;
                case Surface.ROTATION_270:
                    x = event.values[1];
                    y = -event.values[0];
                    break;
                case Surface.ROTATION_0:
                default:
                    x = event.values[0];
                    y = event.values[1];
                    break;
            }

            ccAccelerometerChanged(x, y, event.values[2]);
        }
	}

	public void ccAccelerometerChanged(float f1, float f2, float f3)
    {
        CGPoint position = this.getPosition();
        CGSize size = CCDirector.sharedDirector().winSize();

        int speed;

        speed = (int) (GameLayer.dp2px((8/1.5)) / (1 / Math.abs(f1)));

        if(GameLayer.gamePlaying) {
            float widthOfDog = this.getContentSize().width * MainActivity.scale;

            if(f1 < -0.5 && !(position.x-speed > winSize.getWidth() - (widthOfDog / 2))) {
                this.setPosition(Math.min(size.width - (widthOfDog / 2), this.getPosition().x+speed), this.getPosition().y);
            }
            else if(f1 > 0.5 && !(position.x+speed < 0 + (widthOfDog / 2))) {
                this.setPosition(Math.max(widthOfDog / 2, this.getPosition().x-speed), this.getPosition().y);
            }
        }
    }
}
