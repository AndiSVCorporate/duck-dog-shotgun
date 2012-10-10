package com.dds;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGSize;

/**
 * @author Wouter
 *         Date: 10-10-12
 *         Time: 16:49
 */
public class MenuBackgroundLayer extends CCLayer {
    protected CGSize winSize = CCDirector.sharedDirector().winSize();

    public  MenuBackgroundLayer() {
        CCSprite background = CCSprite.sprite("cabin.png");

        background.setScale(GameLayer.scale);

        background.setPosition(CGPoint.make(winSize.width / 2, winSize.height / 2));
        background.setTag(122);

        addChild(background);
    }
}
