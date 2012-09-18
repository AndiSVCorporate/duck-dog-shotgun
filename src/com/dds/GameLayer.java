package com.dds;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;
import org.cocos2d.actions.base.CCAction;
import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.instant.CCCallFuncN;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.*;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGSize;

import java.util.ArrayList;

/**
 * @author Wouter
 * Date: 14-09-12
 * Time: 17:04
 */
public class GameLayer extends CCLayer {
    protected CCSpriteSheet duckSpriteSheet;
    protected ArrayList<CCSpriteFrame> flySprites;
    protected CCAnimation flyAnimation;

    public static CCScene scene()
    {
        CCScene returnScene = CCScene.node();

        CCLayer innerLayer = new GameLayer();

        returnScene.addChild(innerLayer);

        return returnScene;
    }

    public GameLayer() {
        CCSpriteFrameCache.sharedSpriteFrameCache().addSpriteFrames("duck_sprite.plist");
        this.duckSpriteSheet = CCSpriteSheet.spriteSheet("duck_sprite.png");

        this.flySprites = new ArrayList<CCSpriteFrame>();

        for(int i = 1; i <= 4; i++)
        {
            this.flySprites.add(CCSpriteFrameCache.sharedSpriteFrameCache().spriteFrameByName("xhdpi_retro" + i + ".png"));
        }

        this.flyAnimation = CCAnimation.animation("fly", 2f, this.flySprites);

        this.setIsTouchEnabled(true);

        this.schedule("gameLogic", 1.0f);
    }

    public void gameLogic(float dt)
    {
        addTarget();
    }

    protected void addTarget()
    {
        Duck duck = new Duck(CCSprite.sprite(this.flySprites.get(0)));

        CCAction flyAction = CCRepeatForever.action();

        // Determine where to spawn the target along the Y axis
        CGSize winSize = CCDirector.sharedDirector().displaySize();

        int y = (int) winSize.height/2;

        // Create the target slightly off-screen along the right edge,
        // and along a random position along the Y axis as calculated above
        duck.duckSprite.setPosition(winSize.width + (duck.duckSprite.getContentSize().width / 2.0f), y);
        addChild(duck.duckSprite);

        // Determine speed of the target
        int actualDuration = 1;

        // Create the actions
        CCMoveTo actionMove = CCMoveTo.action(actualDuration, CGPoint.ccp(-duck.duckSprite.getContentSize().width / 2.0f, y));
        CCCallFuncN actionMoveDone = CCCallFuncN.action(this, "spriteMoveFinished");
        CCSequence actions = CCSequence.actions(actionMove, actionMoveDone);

        duck.duckSprite.runAction(actions);
    }

    public void spriteMoveFinished(Object sender)
    {
        CCSprite sprite = (CCSprite)sender;
        this.removeChild(sprite, true);
    }

    public boolean ccTouchesBegan(MotionEvent event) {
        return true;
    }

    public boolean ccTouchesMoved(MotionEvent event) {
        return true;
    }

    public void update(float gameTime) {

    }

    public boolean ccTouchesEnded(MotionEvent event) {
        return true;
    }

    public void finalize() throws Throwable {
        super.finalize();
        CCTextureCache.purgeSharedTextureCache();
    }
}
