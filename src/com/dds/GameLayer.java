package com.dds;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCBezierTo;
import org.cocos2d.actions.interval.CCFadeOut;
import org.cocos2d.actions.interval.CCScaleTo;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.*;
import org.cocos2d.sound.SoundEngine;
import org.cocos2d.types.CCBezierConfig;
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
public class GameLayer extends CCLayer implements SensorEventListener {
    protected BulletLayer bulletLayer;
    protected CCSpriteSheet duckSpriteSheet;
    protected CCSpriteSheet fallDuckSpriteSheet;
    protected CCSpriteSheet bloodSpriteSheet;
    protected ArrayList<CCSpriteFrame> flySprites;
    protected CCAnimation fallAnimation;
    protected CCAnimation flyAnimation;
    protected ArrayList<CCSpriteFrame> fallSprites;
    protected static ArrayList<CCSpriteFrame> bloodSprites;

    protected float lastFValue = 0;

    public static boolean gamePlaying;
    public static boolean isVibrationEnabled;
    
    private static int count = 0;
    private static float spawnTime = 3.0f;

    public static int score = 0;

    public static int bullets = 7;
    private int reloadShakes = 0;


    public static CCScene scene() {
    	reset();
        CCScene returnScene = CCScene.node();

        BulletLayer bulletLayer = new BulletLayer();
        CCLayer innerLayer = new GameLayer(bulletLayer);

        CCLayer labelLayer = new LabelLayer();

        innerLayer.setTag(2);
        labelLayer.setTag(1);


        returnScene.addChild(innerLayer);
        returnScene.addChild(labelLayer);
        returnScene.addChild(bulletLayer);

        return returnScene;
    }
    
    public static void reset()
    {
    	score = 0;
    	bullets = 7;
    	Dog.health = 5;
    	gamePlaying = true;
    	count = 0;
        spawnTime = 2.0f;
        Duck.actualDuration = 10.0f;
    }

    public GameLayer(BulletLayer bulletLayer) {
        loadSounds();
        this.bulletLayer = bulletLayer;
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

        background.setScaleY(MainActivity.scale);
        background.setScaleX(MainActivity.scale);

        background.setPosition(CGPoint.make(winSize.width / 2, winSize.height / 2));

        Dog dog = new Dog(Dog.playerImage);

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

        this.schedule("gameLogic", spawnTime);
        this.schedule("difficulty", 0.2f);

        dog.setTag(1);

        this.addChild(background);
        this.addChild(dog);
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

    protected void addTarget() {
        if(GameLayer.gamePlaying) {
            Duck duck = new Duck(this.flySprites.get(0), this.flyAnimation, this.fallAnimation);
            addChild(duck);
        }
    }

    public void finalize() throws Throwable {
        super.finalize();
        CCTextureCache.purgeSharedTextureCache();
    }

    public void update(float time) {
        if(!GameLayer.gamePlaying) {
            gameOver();
        }
        if(Dog.health <= 0) {
            GameLayer.gamePlaying = false;
        }
    }

    public void gameOver() {
        CGSize winSize = CCDirector.sharedDirector().displaySize();

        this.stopAllActions();

        for(CCNode node : this.getChildren())
        {
            node.stopAllActions();
        }

        if(bulletLayer.getChildByTag(10) != null) {
            bulletLayer.removeChildByTag(10, true);
        }

        CCLabel gameOverLabel = CCLabel.makeLabel("Game Over", "Sans Serif", 72);
        gameOverLabel.setColor(ccColor3B.ccRED);
        gameOverLabel.setPosition(winSize.width / 2, winSize.height / 2);
        this.addChild(gameOverLabel);
    }

    protected void bleed(CGPoint position) {
        Random r = new Random();
        CCSprite blood = CCSprite.sprite(GameLayer.bloodSprites.get(r.nextInt(3)));
        blood.setScale(0.5f * MainActivity.scale);
        
        for (int i = 0; i < 5; i++)
        {
	        Feather b = Feather.sprite("feather.png");
	        b.setScale(MainActivity.scale);
	        b.setPosition(position);
	        addChild(b);
	        b.bezierMove();
        }
        
        CCScaleTo scale = CCScaleTo.action((0.5f * MainActivity.scale), 1.0f * MainActivity.scale);
        scale.setDuration(0.25f);
        
        CCFadeOut bleedOut = CCFadeOut.action(1f);
        CCCallFunc deleteBlood = CCCallFunc.action(blood, "deleteBlood");
        CCSequence action = CCSequence.actions(scale, bleedOut, deleteBlood);

        blood.setPosition(position);

        blood.runAction(action);

        addChild(blood);
    }

    protected void deleteBlood(Object sender) {
        ((CCNode) sender).removeSelf();
    }

    public void onAccuracyChanged(Sensor s, int i) {}

    public void onSensorChanged(SensorEvent event){
        if(event.sensor.getType() == 1)
        {
            doReload(event.values[0], event.values[1], event.values[2]);
        }
    }

    protected void doReload(float f1, float f2, float f3) {
        if(GameLayer.gamePlaying) {
            if(f2 < 0 || f2 > 18) {
                if(Math.abs(lastFValue - f2) > 10) {
                    reloadShakes++;
                    if(reloadShakes == 2) {
                        bullets = 7;
                        reloadShakes=0;
                        bulletLayer.reload = true;
                        bulletLayer.removeChildByTag(10, true);
                        SoundEngine.sharedEngine().playEffect(CCDirector.sharedDirector().getActivity(), R.raw.reload);
                    }
                }
            }
            lastFValue = f2;
        }
    }
    
    public void difficulty(float dr) 
    {
        if(gamePlaying && Duck.actualDuration > 4.0f && count % 100 == 0) 
        {
            Duck.actualDuration -= 0.2f;
        }
        
        if (gamePlaying && spawnTime > 0.7f && count % 50 == 0)
        {
        	spawnTime -= 0.1f;
        	this.unschedule("gameLogic");
        	this.schedule("gameLogic", spawnTime);
        }
        
    	count++;
    }

    private void loadSounds() {
        SoundEngine.sharedEngine().preloadEffect(CCDirector.sharedDirector().getActivity(), R.raw.bof1);
        SoundEngine.sharedEngine().preloadEffect(CCDirector.sharedDirector().getActivity(), R.raw.reload);
        SoundEngine.sharedEngine().preloadEffect(CCDirector.sharedDirector().getActivity(), R.raw.shot3);
        SoundEngine.sharedEngine().preloadEffect(CCDirector.sharedDirector().getActivity(), R.raw.splat);
    }
}
