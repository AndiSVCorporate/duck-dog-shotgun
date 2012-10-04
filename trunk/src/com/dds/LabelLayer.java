package com.dds;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.nodes.CCNode;
import org.cocos2d.nodes.CCTextureCache;
import org.cocos2d.opengl.CCTexture2D;
import org.cocos2d.types.CGSize;
import org.cocos2d.types.ccColor3B;

/**
 * @author Wouter
 *         Date: 03-10-12
 *         Time: 13:21
 */
public class LabelLayer extends CCLayer {
    protected static CCLabel scoreLabel;
    protected static CCLabel healthLabel;
    private static LabelLayer instance;

    public static final float FONT_SIZE = 45;

    public LabelLayer() {
        this.scheduleUpdate();

        CGSize winSize = CCDirector.sharedDirector().displaySize();

        //make labels
        scoreLabel = CCLabel.makeLabel("Score: " + GameLayer.score, "Arial", FONT_SIZE);
        scoreLabel.setColor(ccColor3B.ccBLACK);
        scoreLabel.setPosition(winSize.width/6, (int)(winSize.height*0.95));
        scoreLabel.setTag(23);

        healthLabel = CCLabel.makeLabel("Health: " + Dog.health, "Arial", FONT_SIZE);
        healthLabel.setColor(ccColor3B.ccBLACK);
        healthLabel.setPosition(winSize.width / 6, (int) (winSize.height * 0.90));
        healthLabel.setTag(24);

        addChild(scoreLabel);
        addChild(healthLabel);
        LabelLayer.instance = this;
    }

    private static LabelLayer getInstance() {
        return LabelLayer.instance;
    }

    public static void update(Boolean healthUpdate) {
        if(healthUpdate)
        {
            Dog.health--;
        }
        else
        {
            GameLayer.score++;
        }
    }

    public void update(float time) {
        scoreLabel.setString("Score: " + GameLayer.score);
        healthLabel.setString("Health: " + Dog.health);
    }
}
