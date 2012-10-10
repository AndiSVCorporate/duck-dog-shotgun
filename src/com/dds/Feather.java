package com.dds;

import java.util.Random;

import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCBezierTo;
import org.cocos2d.actions.interval.CCRotateTo;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CCBezierConfig;
import org.cocos2d.types.CGPoint;

import android.util.Log;

public class Feather extends CCSprite
{	
	public Feather(String path)
	{
		super(path);
	}
	
	public static Feather sprite(String path)
	{
		return new Feather(path);
	}
	
    public void bezierMove()
    {
    	CGPoint pos = getPosition();

    	if (pos.y > 0)
    	{
	    	Random r = new Random();
	    	int next = 20 * (r.nextInt(10) - 4);
	    	
	        CCBezierConfig bc = new CCBezierConfig();
	        bc.controlPoint_1 = CGPoint.make(pos.x, (float) (pos.y - GameLayer.dp2px(25)));
	        bc.controlPoint_2 = CGPoint.make((float) (pos.x + GameLayer.dp2px(next / 5)), (float) (pos.y - GameLayer.dp2px(25)));
	        bc.endPosition = CGPoint.make((float) (pos.x + GameLayer.dp2px(next)), (float) (pos.y - GameLayer.dp2px(50 + r.nextInt(20) - 10)));
	        CCBezierTo bezier = CCBezierTo.action(1.0f, bc);
	        
	        CCRotateTo rotate = CCRotateTo.action(1.0f, r.nextInt(360));
	        
	        CCCallFunc move = CCCallFunc.action(this, "bezierMove");
	        
	        CCSequence seq = CCSequence.actions(bezier, move);
	        
	        runAction(seq);
	        runAction(rotate);
    	}
    	else
    	{
    		removeSelf();
    	}
    }
    
    public void bezierMove(Object sender)
    {
    	bezierMove();
    }
}
