package com.dds;

import org.cocos2d.actions.base.CCAction;
import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.instant.CCCallFuncN;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.events.CCTouchDispatcher;
import org.cocos2d.nodes.*;
import org.cocos2d.protocols.CCTouchDelegateProtocol;
import org.cocos2d.types.CGPoint;

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

    public Duck(CCSpriteFrame frame, CCAnimation flyAnimation) {
        super(frame);
        CCTouchDispatcher.sharedDispatcher().addDelegate(this, 0);
        CGSize duckContentSize = this.getContentSize();
        CGSize winSize = CCDirector.sharedDirector().displaySize();
        int y = (int) (winSize.height/1.3);

        setPosition(winSize.width + (duckContentSize.width / 2.0f), y);

        CCAction flyAction = CCRepeatForever.action(CCAnimate.action(flyAnimation, true));

        // Determine speed of the target
        int actualDuration = 2;

        // Create the actions
        CCMoveTo actionMove = CCMoveTo.action(actualDuration, CGPoint.ccp(-duckContentSize.width / 2.0f, y));
        CCCallFuncN actionMoveDone = CCCallFuncN.action(this, "spriteMoveFinished");
        CCSequence actions = CCSequence.actions(actionMove, actionMoveDone);


        runAction(actions);
        runAction(flyAction);
    }
    
    public boolean ccTouchesBegan(MotionEvent e)
    {
        CGSize winSize = CCDirector.sharedDirector().displaySize();
        double touchX = e.getX();
        double touchY = e.getY()+(winSize.height/2);
        
    	CGPoint pos = getPosition();
        if((double) pos.x >= (touchX-90) && (double) pos.x <= (touchX+20)) {
            if((double) pos.y >= (touchY-20) && (double) pos.y <= (touchY+50)) {
                fallDown();
            }
        }
        return true;
    }

    protected void fallDown() {
        stopAllActions();
        CGPoint position = this.getPosition();
        CGSize duckContentSize = this.getContentSize();

        CCMoveTo fallToCertainDeath = CCMoveTo.action(2, CGPoint.ccp(position.x, -duckContentSize.height / 2.0f));
        CCCallFuncN fellToCertainDeath = CCCallFuncN.action(this, "spriteMoveFinished");

        CCSequence actions = CCSequence.actions(fallToCertainDeath, fellToCertainDeath);

        runAction(actions);

        CheckHitWithDogThread checkThread = new CheckHitWithDogThread();
        Thread t = new Thread(checkThread);
        t.start();
    }
    
    public void spriteMoveFinished() {
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

    class CheckHitWithDogThread implements Runnable {

        @Override
        public void run() {
            while(alive) {
                CGPoint dogPosition = getParent().getChildByTag(1).getPosition();
                if(getPosition().y <= dogPosition.y+15) {
                    if(dogPosition.x -10 < getPosition().x || getPosition().x < dogPosition.x + 30) {
                        alive = false;
                    }
                }
            }
            spriteMoveFinished();
        }
    }
}
