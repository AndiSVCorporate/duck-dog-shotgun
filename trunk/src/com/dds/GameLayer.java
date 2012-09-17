package com.dds;

import android.view.MotionEvent;
import org.cocos2d.actions.instant.CCCallFuncN;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.*;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGSize;

/**
 * @author Wouter
 * Date: 14-09-12
 * Time: 17:04
 */
public class GameLayer extends CCLayer {
    protected CCSpriteSheet duckSpriteSheet;

    public static CCScene scene()
    {
        CCScene returnScene = CCScene.node();

        CCLayer innerLayer = new GameLayer();

        returnScene.addChild(innerLayer);

        return returnScene;
    }

    public GameLayer() {
        CGSize winSize =  CCDirector.sharedDirector().displaySize();

        CCSpriteFrameCache.sharedSpriteFrameCache().addSpriteFrames("duck_sprite.plist");
        this.duckSpriteSheet = CCSpriteSheet.spriteSheet("duck_sprite.png");

        this.setIsTouchEnabled(true);

        this.schedule("gameLogic", 1.0f);
    }

    public void gameLogic(float dt)
    {
        addTarget();
    }

    protected void addTarget()
    {
        CCSprite duck = new Duck(this.duckSpriteSheet);

        // Determine where to spawn the target along the Y axis
        CGSize winSize = CCDirector.sharedDirector().displaySize();

        int y = (int) winSize.height/2;

        // Create the target slightly off-screen along the right edge,
        // and along a random position along the Y axis as calculated above
        duck.setPosition(winSize.width + (duck.getContentSize().width / 2.0f), y);
        addChild(duck);

        // Determine speed of the target
        int actualDuration = 2;

        // Create the actions
        CCMoveTo actionMove = CCMoveTo.action(actualDuration, CGPoint.ccp(-duck.getContentSize().width / 2.0f, y));
        CCCallFuncN actionMoveDone = CCCallFuncN.action(this, "spriteMoveFinished");
        CCSequence actions = CCSequence.actions(actionMove, actionMoveDone);

        duck.runAction(actions);
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
