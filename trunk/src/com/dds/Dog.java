package com.dds;

import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGSize;

/**
 * @author Wouter
 * Date: 17-09-12
 * Time: 12:01
 */
public class Dog extends CCSprite {

    public Dog(String path) {
        super(path);
        CGSize winSize = CCDirector.sharedDirector().displaySize();

        int x = (int) winSize.width /2;
        int y = (int) this.getContentSize().getHeight() /2;

        setPosition(x, y);
    }
}
