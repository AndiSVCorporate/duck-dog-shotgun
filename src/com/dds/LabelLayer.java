package com.dds;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGSize;
import org.cocos2d.types.ccColor3B;

/**
 * @author Wouter
 *         Date: 03-10-12
 *         Time: 13:21
 */
public class LabelLayer extends CCLayer {
    protected static CCLabel scoreLabel;
    private static LabelLayer instance;

    public static final float FONT_SIZE = 45;

    public LabelLayer() {
        this.scheduleUpdate();

        CGSize winSize = CCDirector.sharedDirector().displaySize();

        //make labels
        scoreLabel = CCLabel.makeLabel("Score: " + GameLayer.score, "Arial", FONT_SIZE);
        scoreLabel.setColor(ccColor3B.ccBLACK);
        scoreLabel.setScale(GameLayer.scale);
        scoreLabel.setPosition(winSize.width / 6, (int) (winSize.height * 0.95));
        scoreLabel.setTag(23);

        addChild(scoreLabel);
        LabelLayer.instance = this;

        buildHealthBar();
    }

    public void buildHealthBar() {
        removeAllChildren(true);

        //make bar with how many bullets left
        CCSprite healthSprite = CCSprite.sprite("heart.png");
        healthSprite.setScale(GameLayer.scale);

        int spaceBetweenHearts = (int) (60 * GameLayer.scale);

        int x = (int) (10 * GameLayer.scale);
        int y = (int) (1200 * GameLayer.scale);

        for(int i = 1; i <= Dog.health; i++) {
            if(i != 1) {
                healthSprite = CCSprite.sprite("heart.png");
            }
            healthSprite.setPosition(x + (i * spaceBetweenHearts), y);

            healthSprite.setScale(GameLayer.scale / 8);
            healthSprite.setTag(i);
            addChild(healthSprite);
        }
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

        switch (Dog.health) {
            case 0: removeChildByTag(1, true);
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
