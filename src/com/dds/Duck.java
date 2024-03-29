package com.dds;

import android.content.Context;
import android.os.Vibrator;
import org.cocos2d.actions.base.CCAction;
import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.nodes.*;
import org.cocos2d.sound.SoundEngine;
import org.cocos2d.types.CGPoint;

import android.graphics.Point;
import org.cocos2d.types.CGSize;

/**
 * @author Wouter
 * Date: 17-09-12
 * Time: 11:53
 */
public class Duck extends CCSprite
{
    protected boolean alive = true;
    protected CCAnimation fallAnimation;
    private Route route = new Route(5);
    private double distance = route.getTotalDistance();
    protected static float actualDuration = 10.0f;
    private boolean falling = false;
    private boolean check = true;
    private Vibrator v;



    public Duck(CCSpriteFrame frame, CCAnimation flyAnimation, CCAnimation fallAnimation) {
        super(frame);
        v = (Vibrator) CCDirector.sharedDirector().getActivity().getSystemService(Context.VIBRATOR_SERVICE);;

        scheduleUpdate();
        this.fallAnimation = fallAnimation;

        CGSize duckContentSize = this.getContentSize();
        CGSize winSize = CCDirector.sharedDirector().displaySize();

        this.setScaleX(MainActivity.scale);

        this.setScaleY(MainActivity.scale);

        Point start = route.getStops()[0];
        setPosition(start.x, winSize.height - start.y - (duckContentSize.height / 2.0f));
        
        CCAction flyAction = CCRepeatForever.action(CCAnimate.action(flyAnimation, true));

        // Determine speed of the target        
        float duration = (float) (route.getDistanceToNextPoint() / distance * actualDuration);
        float direction = (float) route.getDirectionToNextPoint();
        setFlipX(direction > 90 && direction < 270);
        Point p = route.next();

        // Create the actions
        CCMoveTo actionMove = CCMoveTo.action(duration, CGPoint.ccp(p.x, winSize.height - p.y - (duckContentSize.height / 2.0f)));
        CCCallFunc actionMoveDone = CCCallFunc.action(this, "spriteMoveFinished");
        CCSequence actions = CCSequence.actions(actionMove, actionMoveDone);

        runAction(actions);
        runAction(flyAction);
    }

    public Duck() {}

    protected void fallDown() 
    {
    	if (!this.falling)
    	{
    		this.schedule("checkHitWithDog");
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
    		if (!route.hasNext() && !this.falling)
        	{
        		Dog.health--;
        	}
            alive = false;
    	}
    }

    public void update(float time) {
        if(!alive)
        {
            removeSelf();
        }
    }

    public void checkHitWithDog(float dt)
    {
    	if(alive && getParent() != null) {
            CGPoint dogPosition = getParent().getChildByTag(1).getPosition();
            if(getPosition().y <= dogPosition.y+15) {
                if(dogPosition.x -50 < getPosition().x && getPosition().x < dogPosition.x + 50) {
                    alive = false;
                    LabelLayer.updateVariables(false);
                    SoundEngine.sharedEngine().playEffect(CCDirector.sharedDirector().getActivity(), R.raw.bof1);

                    if(GameLayer.isVibrationEnabled) {
                        long[] pattern = { 230, 100 };
                        v.vibrate(pattern, -1);
                    }
                }
                else if(getPosition().y < 0 && check) {
                    LabelLayer.updateVariables(true);
                    check = false;
                }
            }
        }
        else
        {
        	spriteMoveFinished();
        }
    }
}