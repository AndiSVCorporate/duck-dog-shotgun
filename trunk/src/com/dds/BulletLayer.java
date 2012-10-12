package com.dds;

import android.content.Context;
import android.os.Vibrator;
import android.view.MotionEvent;
import org.cocos2d.events.CCTouchDispatcher;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.*;
import org.cocos2d.protocols.CCTouchDelegateProtocol;
import org.cocos2d.sound.SoundEngine;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGSize;

import java.util.List;

/**
 * @author Wouter
 * Date: 08-10-12
 * Time: 16:30
 */
public class BulletLayer extends CCLayer implements CCTouchDelegateProtocol {
    public boolean reload = false;
    private CCLabel reloadLabel;
    private Vibrator v;

    public BulletLayer() {
        scheduleUpdate();

        v = (Vibrator) CCDirector.sharedDirector().getActivity().getSystemService(Context.VIBRATOR_SERVICE);

        CCTouchDispatcher.sharedDispatcher().addDelegate(this, 0);

        buildBulletBar();
    }

    public void buildBulletBar() {
        removeAllChildren(true);
        reloadLabel = null;
        //make bar with how many bullets left
        CCSprite bulletSprite = CCSprite.sprite("bullet.png");

        int spaceBetweenBullets = (int) (15 * MainActivity.scale);

        int x = 0;
        int y = (int) (60 * MainActivity.scale);

        for(int i = 1; i <= GameLayer.bullets; i++) {
            if(i != 1) {
                bulletSprite = CCSprite.sprite("bullet.png");
            }
            bulletSprite.setPosition(x+(i*spaceBetweenBullets), y);

            bulletSprite.setScale(MainActivity.scale/8);
            bulletSprite.setTag(i);
            addChild(bulletSprite);
        }
        this.reload = false;
    }

    public synchronized boolean ccTouchesBegan(MotionEvent e)
    {
        if(GameLayer.gamePlaying) {
            GameLayer gameLayer = (GameLayer) getParent().getChildByTag(2);

            List children = gameLayer.getChildren();

            CGSize winSize = CCDirector.sharedDirector().displaySize();
            double touchX = e.getX();
            double touchY = winSize.height - e.getY();

            if(GameLayer.bullets > 0) {
                SoundEngine.sharedEngine().playEffect(CCDirector.sharedDirector().getActivity(), R.raw.shot3);
                if(GameLayer.isVibrationEnabled) {
                    v.vibrate(100);
                }
            }

            int count = 0;
            for(int i = 0; i < children.size(); i++) {
                Object child = children.get(i);
                if (child instanceof Duck) {
                    CGPoint pos = ((Duck) child).getPosition();

                    int shot = 5; // hagel in dp

                    if (GameLayer.bullets > 0) {
                        if (pos.x >= touchX - ((Duck) child).getContentSize().width / 2 - GameLayer.dp2px(shot) && pos.x <= touchX + ((Duck) child).getContentSize().width / 2 + GameLayer.dp2px(shot) && pos.y >= touchY - ((Duck) child).getContentSize().height / 2 - GameLayer.dp2px(shot) && pos.y <= touchY + ((Duck) child).getContentSize().height / 2 + GameLayer.dp2px(shot)) {
                            gameLayer.bleed(((Duck) child).getPosition());
                            ((Duck) child).fallDown();
                            SoundEngine.sharedEngine().playEffect(CCDirector.sharedDirector().getActivity(), R.raw.splat);
                            count++;
                        }
                    }
                } else if(child instanceof Dog) {
                    CGPoint pos = ((Dog) child).getPosition();
                    if (GameLayer.bullets > 0) {
                        if (pos.x >= touchX - ((Dog) child).getContentSize().width / 2 && pos.x <= touchX + ((Dog) child).getContentSize().width / 2 && pos.y >= touchY - ((Dog) child).getContentSize().height / 2 && pos.y <= touchY + ((Dog) child).getContentSize().height / 2) {
                            SoundEngine.sharedEngine().playEffect(CCDirector.sharedDirector().getActivity(), R.raw.splat);
                            GameLayer.gamePlaying = false;
                        }
                    }
                }
            }

            if (count > 1)
            {
                GameLayer.score += count;
            }

            if(GameLayer.bullets > 0) {
                GameLayer.bullets--;
            }
        } else {
            CCDirector.sharedDirector().popScene();
        }

        return false;
    }

    public void update(float time) {
        switch (GameLayer.bullets) {
            case 0: removeChildByTag(1, true);
            	if (reloadLabel == null)
            	{
	                CGSize winSize = CCDirector.sharedDirector().displaySize();
	                reloadLabel = CCLabel.makeLabel("Shake to reload!", "Arial", 72);
	                reloadLabel.setScale(MainActivity.scale);
	                reloadLabel.setTag(10);
	                reloadLabel.setPosition(winSize.width / 2, winSize.height / 2);
	                addChild(reloadLabel);
            	}
                break;
            case 1: removeChildByTag(2, true);
                break;
            case 2: removeChildByTag(3, true);
                break;
            case 3: removeChildByTag(4, true);
                break;
            case 4: removeChildByTag(5, true);
                break;
            case 5: removeChildByTag(6, true);
                break;
            case 6: removeChildByTag(7, true);
            default:
                break;
        }

        if(reload)
        {
            buildBulletBar();
        }
    }
}
