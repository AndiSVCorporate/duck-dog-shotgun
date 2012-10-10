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
        scoreLabel = CCLabel.makeLabel("" + GameLayer.score, "Arial", FONT_SIZE);
        scoreLabel.setColor(ccColor3B.ccBLACK);
        scoreLabel.setScale(MainActivity.scale);
        scoreLabel.setPosition((winSize.width / 6)*5, (int) (CCDirector.sharedDirector().winSize().height - GameLayer.dp2px(40)));
        scoreLabel.setTag(23);

        addChild(scoreLabel);
        LabelLayer.instance = this;

        buildHealthBar();
    }

    public void buildHealthBar() {
        //make bar with how many bullets left
        CCSprite healthSprite;

        int spaceBetweenHearts = (int) (60 * MainActivity.scale);

        int x = (int) (10 * MainActivity.scale);
//        int y = (int) (GameLayer.dp2px(582));
        int y = (int) (CCDirector.sharedDirector().winSize().height - GameLayer.dp2px(40));

        for(int i = 1; i <= Dog.health; i++) {
        	healthSprite = CCSprite.sprite("heart.png");
            healthSprite.setPosition(x + (i * spaceBetweenHearts), y);

            healthSprite.setScale(MainActivity.scale / 8);
            healthSprite.setTag(i);
            addChild(healthSprite);
        }
    }

    private static LabelLayer getInstance() {
        return LabelLayer.instance;
    }

    public static void updateVariables(Boolean healthUpdate) {
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
        scoreLabel.setString("" + GameLayer.score);

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
