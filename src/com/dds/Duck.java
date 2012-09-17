package com.dds;

import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCSpriteSheet;

/**
 * @author Wouter
 *         Date: 17-09-12
 *         Time: 11:53
 */
public class Duck extends CCSprite {
    public Duck(CCSpriteSheet spriteSheet) {
        this.addChild(spriteSheet);
    }
}
