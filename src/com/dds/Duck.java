package com.dds;

import org.cocos2d.events.CCTouchDispatcher;
import org.cocos2d.nodes.*;
import org.cocos2d.protocols.CCTouchDelegateProtocol;
import org.cocos2d.types.CGPoint;

import android.view.MotionEvent;
import org.cocos2d.types.CGSize;

/**
 * @author Wouter
 *         Date: 17-09-12
 *         Time: 11:53
 */
public class Duck extends CCSprite implements CCTouchDelegateProtocol 
{
    public CCSprite duckSprite;

    public Duck(CCSpriteFrame frame) 
    {
        super(frame);
        CCTouchDispatcher.sharedDispatcher().addDelegate(this, 0);
    }
    
    public boolean ccTouchesBegan(MotionEvent e) 
    {
        CGSize winSize = CCDirector.sharedDirector().displaySize();
        double touchX = e.getX();
        double touchY = e.getY()+(winSize.height/2);
        
    	CGPoint pos = getPosition();
        if((double) pos.x >= (touchX-40) && (double) pos.x <= (touchX+60)) {
            if((double) pos.y >= (touchY-40) && (double) pos.y <= (touchY+15)) {
                spriteMoveFinished();
            }
        }
        return true;
    }
    
    public void spriteMoveFinished()
    {
        removeSelf();
    }

	public boolean ccTouchesCancelled(MotionEvent e) 
	{
		return false;
	}

	public boolean ccTouchesEnded(MotionEvent e) 
	{
		return false;
	}

	public boolean ccTouchesMoved(MotionEvent e) 
	{
		return false;
	}
}
