package com.dds;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCSpriteFrame;
import org.cocos2d.nodes.CCSpriteFrameCache;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGSize;

import java.util.ArrayList;

/**
 * @author Wouter
 * Date: 08-10-12
 * Time: 16:30
 */
public class BulletLayer extends CCLayer {
    public BulletLayer() {
        scheduleUpdate();

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

    public void update(float time) {
        switch (GameLayer.bullets) {
            case 0: removeAllChildren(true);
                break;
            case 1: removeChildByTag(1, true);
                break;
            case 2: removeChildByTag(2, true);
                break;
            case 3: removeChildByTag(3, true);
                break;
            case 4: removeChildByTag(4, true);
                break;
            case 5: removeChildByTag(5, true);
                break;
            case 6: removeChildByTag(6, true);
            default:
                break;
        }
    }
}
