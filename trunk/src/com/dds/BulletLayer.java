package com.dds;

import android.util.Log;
import android.view.MotionEvent;
import org.cocos2d.events.CCTouchDispatcher;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.*;
import org.cocos2d.protocols.CCTouchDelegateProtocol;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGSize;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Wouter
 * Date: 08-10-12
 * Time: 16:30
 */
public class BulletLayer extends CCLayer implements CCTouchDelegateProtocol {
    public BulletLayer() {
        scheduleUpdate();

        CCTouchDispatcher.sharedDispatcher().addDelegate(this, 0);

        buildBulletBar();
    }

    public void buildBulletBar() {
        removeAllChildren(true);
        //make bar with how many bullets left
        CCSprite bulletSprite = CCSprite.sprite("bullet.png");

        int spaceBetweenBullets = (int) (15 * GameLayer.scale);

        int x = 0;
        int y = (int) (60 * GameLayer.scale);

        for(int i = 1; i <= GameLayer.bullets; i++) {
            if(i != 1) {
                bulletSprite = CCSprite.sprite("bullet.png");
            }
            bulletSprite.setPosition(x+(i*spaceBetweenBullets), y);

            bulletSprite.setScale(GameLayer.scale/8);
            bulletSprite.setTag(i);
            addChild(bulletSprite);
        }
    }

    public synchronized boolean ccTouchesBegan(MotionEvent e)
    {
        GameLayer gameLayer = (GameLayer) getParent().getChildByTag(2);

        List children = gameLayer.getChildren();

        CGSize winSize = CCDirector.sharedDirector().displaySize();
        double touchX = e.getX();
        double touchY = winSize.height - e.getY();

        for(int i = 0; i < children.size(); i++) {
            Object child = children.get(i);
            if (child instanceof Duck) {
                CGPoint pos = ((Duck) child).getPosition();

                if (GameLayer.bullets > 0) {
                    if ((double) pos.x >= (touchX - GameLayer.dp2px(20)) && (double) pos.x <= (touchX + GameLayer.dp2px(40))) {
                        if ((double) pos.y >= (touchY - GameLayer.dp2px(70)) && (double) pos.y <= (touchY + GameLayer.dp2px(90))) {
                            gameLayer.bleed(((Duck) child).getPosition());
                            ((Duck) child).fallDown();
                        }
                    }
                }
            }
        }

        if(GameLayer.bullets > 0) {
            GameLayer.bullets--;
        }

        return false;
    }

    public void update(float time) {
        switch (GameLayer.bullets) {
            case 0: removeAllChildren(true);
                    CGSize winSize = CCDirector.sharedDirector().displaySize();
                    CCLabel reloadLabel = CCLabel.makeLabel("Shake to reload!", "Arial", 72);
                    reloadLabel.setTag(10);
                reloadLabel.setPosition(winSize.width / 2, winSize.height / 2);
                    addChild(reloadLabel);
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
    }
}
