package com.dds;

import android.util.DisplayMetrics;
import android.view.MotionEvent;
import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCFadeOut;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.*;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGSize;
import org.cocos2d.types.ccColor3B;

import java.util.ArrayList;
import java.util.Random;

/**
 * @author Wouter
 * Date: 14-09-12
 * Time: 17:04
 */
public class GameLayer extends CCLayer {

    protected CCSpriteSheet duckSpriteSheet;
    protected CCSpriteSheet fallDuckSpriteSheet;
    protected CCSpriteSheet bloodSpriteSheet;
    protected ArrayList<CCSpriteFrame> flySprites;
    protected CCAnimation fallAnimation;
    protected CCAnimation flyAnimation;
    protected ArrayList<CCSpriteFrame> fallSprites;
    protected static ArrayList<CCSpriteFrame> bloodSprites;


    protected static CCSprite blood;

    public static boolean gamePlaying = true;

    public static int score = 0;

    public static float scale = 1;


    public static CCScene scene() {
        CCScene returnScene = CCScene.node();

        CCLayer innerLayer = new GameLayer();

        CCLayer labelLayer = new LabelLayer();

        labelLayer.setTag(1);

        returnScene.addChild(innerLayer);
        returnScene.addChild(labelLayer);

        return returnScene;
    }

    public GameLayer() {
        scheduleUpdate();

        CCSpriteFrameCache.sharedSpriteFrameCache().addSpriteFrames("duck_sprite.plist");
        this.duckSpriteSheet = CCSpriteSheet.spriteSheet("duck_sprite.png");
        CCSpriteFrameCache.sharedSpriteFrameCache().addSpriteFrames("duck_falling.plist");
        this.fallDuckSpriteSheet = CCSpriteSheet.spriteSheet("duck_falling.png");
        CCSpriteFrameCache.sharedSpriteFrameCache().addSpriteFrames("blood_sprite.plist");
        this.bloodSpriteSheet = CCSpriteSheet.spriteSheet("blood_sprite.png");

        this.flySprites = new ArrayList<CCSpriteFrame>();
        this.fallSprites = new ArrayList<CCSpriteFrame>();
        GameLayer.bloodSprites = new ArrayList<CCSpriteFrame>();

        CGSize winSize = CCDirector.sharedDirector().displaySize();

        CCSprite background = CCSprite.sprite("background.png");

        GameLayer.scale = winSize.width/background.getTexture().getWidth();

        background.setScaleX(GameLayer.scale);

        background.setScaleY(GameLayer.scale);

        background.setPosition(CGPoint.make(winSize.width / 2, winSize.height / 2));

        Dog dog = new Dog("panda.png");

        for(int i = 1; i <= 4; i++) {
            this.flySprites.add(CCSpriteFrameCache.sharedSpriteFrameCache().getSpriteFrame("xhdpi_retro" + i + ".png"));
            this.fallSprites.add(CCSpriteFrameCache.sharedSpriteFrameCache().getSpriteFrame("falling_duck" + i + ".png"));
            if(i != 4)
            {
                GameLayer.bloodSprites.add(CCSpriteFrameCache.sharedSpriteFrameCache().getSpriteFrame("blood" + i + ".png"));
            }
        }

        this.flyAnimation = CCAnimation.animation("fly", 0.09f, this.flySprites);
        this.fallAnimation = CCAnimation.animation("fall", 0.09f, this.fallSprites);

        this.setIsTouchEnabled(true);
        this.setIsAccelerometerEnabled(true);

        this.schedule("gameLogic", 2.0f);

        dog.setTag(1);

        this.addChild(background);
        this.addChild(dog);

        CheckLivesThread checkThread = new CheckLivesThread();
        Thread t = new Thread(checkThread);
        t.start();
    }
    
	public static double px2dp(double px)
	{
		DisplayMetrics metrics = CCDirector.theApp.getResources().getDisplayMetrics();
		return px / (metrics.densityDpi / 160);
	}
	
	public static double dp2px(double dp)
	{
		DisplayMetrics metrics = CCDirector.theApp.getResources().getDisplayMetrics();
		return dp * (metrics.densityDpi / 160);
	}

    public void gameLogic(float dt)
    {
        addTarget();
    }

    protected void addTarget()
    {
        if(GameLayer.gamePlaying) {
            Duck duck = new Duck(this.flySprites.get(0), this.flyAnimation, this.fallAnimation);
            addChild(duck);
        }
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

    public void update(float time) {
        if(!GameLayer.gamePlaying) {
            gameOver();
        }
    }

    public void gameOver() {
        CGSize winSize = CCDirector.sharedDirector().displaySize();

        this.stopAllActions();

        for(CCNode node : this.getChildren())
        {
            node.stopAllActions();
        }

        Label gameOverLabel = Label.makeLabel("Game Over", "Arial", 72);
        gameOverLabel.setColor(ccColor3B.ccRED);
        gameOverLabel.setPosition(winSize.width / 2, winSize.height / 2);
        this.addChild(gameOverLabel);
    }

    protected void bleed(CGPoint position) {
        Random r = new Random();
        CCSprite blood = CCSprite.sprite(GameLayer.bloodSprites.get(r.nextInt(3)));

        CCFadeOut bleedOut = CCFadeOut.action(1f);
        CCCallFunc deleteBlood = CCCallFunc.action(blood, "deleteBlood");
        CCSequence action = CCSequence.actions(bleedOut, deleteBlood);

        blood.setPosition(position);

        blood.runAction(action);

        addChild(blood);
    }

    protected void deleteBlood(Object sender) {
        ((CCNode) sender).removeSelf();
    }

    class CheckLivesThread implements Runnable
    {
        public void run()
        {
            while(Dog.health > 0) {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            GameLayer.gamePlaying = false;
        }
    }
}
