package com.dds;

import org.cocos2d.actions.base.CCAction;
import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.events.CCTouchDispatcher;
import org.cocos2d.nodes.*;
import org.cocos2d.protocols.CCTouchDelegateProtocol;
import org.cocos2d.types.CGPoint;

import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import org.cocos2d.types.CGSize;

/**
 * @author Wouter
 * Date: 17-09-12
 * Time: 11:53
 */
public class Duck extends CCSprite implements CCTouchDelegateProtocol 
{
    protected boolean alive = true;
    protected CCAnimation fallAnimation;
    private Route route = new Route(5);
    private double distance = route.getTotalDistance();
    private final float actualDuration = 7.0f;
    private boolean falling = false;

    public Duck(CCSpriteFrame frame, CCAnimation flyAnimation, CCAnimation fallAnimation) {
        super(frame);
        this.fallAnimation = fallAnimation;

        CCTouchDispatcher.sharedDispatcher().addDelegate(this, 0);
        CGSize duckContentSize = this.getContentSize();
        CGSize winSize = CCDirector.sharedDirector().displaySize();
        int y = (int) (winSize.height/1.3);

        this.setScaleX(GameLayer.scale);

        this.setScaleY(GameLayer.scale);

        Point start = route.getStops()[0];
//        setPosition(winSize.width + (duckContentSize.width / 2.0f), y);
        setPosition(start.x, winSize.height - start.y - (duckContentSize.height / 2.0f));
        
        CCAction flyAction = CCRepeatForever.action(CCAnimate.action(flyAnimation, true));

        // Determine speed of the target        
        float duration = (float) (route.getDistanceToNextPoint() / distance * actualDuration);
        float direction = (float) route.getDirectionToNextPoint();
//        setRotation(direction);
        setFlipX(direction > 90 && direction < 270);
        Point p = route.next();

        // Create the actions
//        CCMoveTo actionMove = CCMoveTo.action(duration, CGPoint.ccp(-duckContentSize.width / 2.0f, y));
        CCMoveTo actionMove = CCMoveTo.action(duration, CGPoint.ccp(p.x, winSize.height - p.y - (duckContentSize.height / 2.0f)));
        CCCallFunc actionMoveDone = CCCallFunc.action(this, "spriteMoveFinished");
        CCSequence actions = CCSequence.actions(actionMove, actionMoveDone);

        runAction(actions);
        runAction(flyAction);
    }
    
    public boolean ccTouchesBegan(MotionEvent e)
    {
    	Context c = CCDirector.theApp;
		DisplayMetrics metrics = c.getResources().getDisplayMetrics();
        CGSize winSize = CCDirector.sharedDirector().displaySize();
        double touchX = e.getX();
//        double touchY = e.getY()+(winSize.height/2);
        double touchY = winSize.height - e.getY();
        
    	CGPoint pos = this.getPosition();
        if((double) pos.x >= (touchX-GameLayer.dp2px(8)) && (double) pos.x <= (touchX+GameLayer.dp2px(40))) {
            if((double) pos.y >= (touchY-GameLayer.dp2px(50)) && (double) pos.y <= (touchY+GameLayer.dp2px(60))) {
                fallDown();
            }
        }
        return true;
    }

    protected void fallDown() 
    {
    	if (!this.falling)
    	{
	        stopAllActions();
	        this.falling = true;
	
	        CCAction fallAction = CCRepeatForever.action(CCAnimate.action(this.fallAnimation, true));
	        runAction(fallAction);
	
	        CGPoint position = this.getPosition();
	        CGSize duckContentSize = this.getContentSize();
	
	        CCMoveTo fallToCertainDeath = CCMoveTo.action(2, CGPoint.ccp(position.x, -duckContentSize.height / 2.0f));
	        CCCallFunc fellToCertainDeath = CCCallFunc.action(this, "spriteMoveFinished");
	
	        CCSequence actions = CCSequence.actions(fallToCertainDeath, fellToCertainDeath);
	
	        runAction(actions);
	
	        CheckHitWithDogThread checkThread = new CheckHitWithDogThread();
	        Thread t = new Thread(checkThread);
	        t.start();
    	}
    }
    
    public void spriteMoveFinished() 
    {
    	if (route.hasNext() && !this.falling)
    	{
    		CGSize winSize = CCDirector.sharedDirector().displaySize();
            CGSize duckContentSize = this.getContentSize();
    		 // Determine speed of the target        
            float duration = (float) (this.route.getDistanceToNextPoint() / this.distance * this.actualDuration);
            float direction = (float) this.route.getDirectionToNextPoint();
            setFlipX(direction > 90 && direction < 270);
            Point p = this.route.next();

            // Create the actions
            CCMoveTo actionMove = CCMoveTo.action(duration, CGPoint.ccp(p.x, winSize.height - p.y - (duckContentSize.height / 2.0f)));
            CCCallFunc actionMoveDone = CCCallFunc.action(this, "spriteMoveFinished");
            CCSequence actions = CCSequence.actions(actionMove, actionMoveDone);

            runAction(actions);
    	}
    	else
    	{
            removeSelf();
    	}
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

    class CheckHitWithDogThread implements Runnable 
    {
    	public void run() 
        {
            boolean check = true;

            while(alive && getParent() != null) {
                CGPoint dogPosition = getParent().getChildByTag(1).getPosition();
                if(getPosition().y <= dogPosition.y+15) {
                    if(dogPosition.x -50 < getPosition().x && getPosition().x < dogPosition.x + 50) {
                        alive = false;
                        LabelLayer.update(false);
                    }
                    else if(getPosition().y < 0 && check) {
                        LabelLayer.update(true);
                        check = false;
                    }
                }

                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            spriteMoveFinished();
        }
    }
}