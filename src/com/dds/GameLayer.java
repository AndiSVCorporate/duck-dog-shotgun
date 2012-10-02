package com.dds;

import android.content.pm.LabeledIntent;
import android.util.Log;
import android.view.MotionEvent;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.*;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGSize;
import org.cocos2d.types.ccColor3B;

import java.util.ArrayList;

/**
 * @author Wouter
 * Date: 14-09-12
 * Time: 17:04
 */
public class GameLayer extends CCLayer {

    protected CCSpriteSheet duckSpriteSheet;
    protected CCSpriteSheet fallDuckSpriteSheet;
    protected ArrayList<CCSpriteFrame> flySprites;
    protected CCAnimation fallAnimation;
    protected CCAnimation flyAnimation;
    protected ArrayList<CCSpriteFrame> fallSprites;
    protected static CCLabel scoreLabel;
    protected static CCLabel livesLabel;

    public static int score = 0;

    public static float scale = 1;
    public static final float FONT_SIZE = 45;

    public static CCScene scene() {
        CCScene returnScene = CCScene.node();

        CCLayer innerLayer = new GameLayer();

        returnScene.addChild(innerLayer);

        return returnScene;
    }

    public GameLayer() {
        CCSpriteFrameCache.sharedSpriteFrameCache().addSpriteFrames("duck_sprite.plist");
        this.duckSpriteSheet = CCSpriteSheet.spriteSheet("duck_sprite.png");
        CCSpriteFrameCache.sharedSpriteFrameCache().addSpriteFrames("duck_falling.plist");
        this.fallDuckSpriteSheet = CCSpriteSheet.spriteSheet("duck_falling.png");

        this.flySprites = new ArrayList<CCSpriteFrame>();
        this.fallSprites = new ArrayList<CCSpriteFrame>();

        CGSize winSize = CCDirector.sharedDirector().displaySize();

        CCSprite background = CCSprite.sprite("background.png");

        GameLayer.scale = winSize.width/background.getTexture().getWidth();

        background.setScaleX(GameLayer.scale);

        background.setScaleY(GameLayer.scale);

        background.setPosition(CGPoint.make(winSize.width / 2, winSize.height / 2));

        Dog dog = new Dog("dog.png");


        for(int i = 1; i <= 4; i++)
        {
            this.flySprites.add(CCSpriteFrameCache.sharedSpriteFrameCache().getSpriteFrame("xhdpi_retro" + i + ".png"));
            this.fallSprites.add(CCSpriteFrameCache.sharedSpriteFrameCache().getSpriteFrame("falling_duck" + i + ".png"));
        }

        this.flyAnimation = CCAnimation.animation("fly", 0.09f, this.flySprites);
        this.fallAnimation = CCAnimation.animation("fall", 0.09f, this.fallSprites);

        this.setIsTouchEnabled(true);
        this.setIsAccelerometerEnabled(true);

        this.schedule("gameLogic", 2.0f);

        dog.setTag(1);

        this.addChild(background);
        this.addChild(dog);

        //make labels

        scoreLabel = CCLabel.makeLabel("Score: " + GameLayer.score, "Arial", FONT_SIZE);
        scoreLabel.setColor(ccColor3B.ccBLACK);
        scoreLabel.setPosition(winSize.width/6, (int)(winSize.height*0.95));

        livesLabel = CCLabel.makeLabel("Health: " + Dog.health, "Arial", FONT_SIZE);
        livesLabel.setColor(ccColor3B.ccBLACK);
        livesLabel.setPosition(winSize.width/6, (int)(winSize.height*0.90));

        addChild(scoreLabel);
        addChild(livesLabel);

//        CheckLivesThread checkThread = new CheckLivesThread();
//        Thread t = new Thread(checkThread);
//        t.start();
    }
    
	public static double px2dp(double dpi, double px)
	{
		return px / (dpi / 160);
	}
	
	public static double dp2px(double dpi, double dp)
	{
		return dp * (dpi / 160);
	}

    public void gameLogic(float dt)
    {
        addTarget();
    }

    protected void addTarget()
    {
        Duck duck = new Duck(this.flySprites.get(0), this.flyAnimation, this.fallAnimation);
        addChild(duck);
    }

    public boolean ccTouchesBegan(MotionEvent event) {
        return true;
    }

    public boolean ccTouchesMoved(MotionEvent event) {
        return true;
    }

    public boolean ccTouchesEnded(MotionEvent event) {
        return true;
    }

    public void finalize() throws Throwable {
        super.finalize();
        CCTextureCache.purgeSharedTextureCache();
    }

    public static void updateScore() {
        GameLayer.score++;
        GameLayer.scoreLabel.setString("Score: " + GameLayer.score);
    }

    public void gameOver() {
        CGSize winSize = CCDirector.sharedDirector().displaySize();

        this.stopAllActions();

        for(CCNode node : this.getChildren())
        {
            node.stopAllActions();
        }
        CCLabel gameOverLabel = CCLabel.makeLabel("Game Over", "Arial", 72);
        gameOverLabel.setColor(ccColor3B.ccRED);
        gameOverLabel.setPosition(winSize.width/2, winSize.height/2);
        this.addChild(gameOverLabel);
    }

    class CheckLivesThread implements Runnable
    {
        public void run()
        {
            while(Dog.health > 0) {

            }
            gameOver();
        }
    }
}
