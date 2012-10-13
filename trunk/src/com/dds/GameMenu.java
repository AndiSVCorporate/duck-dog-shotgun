package com.dds;

import android.content.Context;
import android.os.Vibrator;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.menus.*;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.types.CGSize;

/**
 * @author Wouter
 * Date: 04-10-12
 * Time: 12:45
 */

/*
 * Highscore:
 * int highscore = Integer.parseInt(((MainActivity) CCDirector.sharedDirector().getActivity()).read("highscore.dds").equals("") ? "0" : ((MainActivity) CCDirector.sharedDirector().getActivity()).read("highscore.dds"));
 * 
 * 
 * Overall:
 * int overall = Integer.parseInt(((MainActivity) CCDirector.sharedDirector().getActivity()).read("overall.dds").equals("") ? "0" : ((MainActivity) CCDirector.sharedDirector().getActivity()).read("overall.dds"));
 */
public class GameMenu extends CCMenu {
    private boolean lastValueOfVibration;

    private CCMenuItemImage vibrationMenuToggle;

    private Vibrator v;

    public static CCScene scene() {
    	
        CCScene returnScene = CCScene.node();

        CCLayer backgroundLayer = new MenuBackgroundLayer();

        GameMenu gameMenu = new GameMenu();

        backgroundLayer.addChild(gameMenu);

        gameMenu.buildScoreBar();

        returnScene.addChild(backgroundLayer);

        return returnScene;
    }

    public GameMenu() {
        super();
        v = (Vibrator) CCDirector.sharedDirector().getActivity().getSystemService(Context.VIBRATOR_SERVICE);

        CGSize winSize = CCDirector.sharedDirector().winSize();

        GameLayer.isVibrationEnabled = lastValueOfVibration = ((MainActivity) CCDirector.sharedDirector().getActivity()).read("settings.dds").equals("1");

        this.scheduleUpdate();

        CCMenuItemImage playGameMenuItem = CCMenuItemImage.item("button.png", "button_pressed.png", this, "startGame");
        playGameMenuItem.setPosition(playGameMenuItem.getPosition().x, (float) (playGameMenuItem.getPosition().y+GameLayer.dp2px(60)));
        CCLabel playLabel = CCLabel.makeLabel("Play Game", "Arial", 40f);
        playLabel.setPosition(playGameMenuItem.getContentSize().width / 2, playGameMenuItem.getContentSize().height / 2);
        playLabel.setTag(1);

        MainActivity.scale = winSize.width/720;
        playGameMenuItem.setScale(MainActivity.scale);

        playGameMenuItem.addChild(playLabel);

        addChild(playGameMenuItem);
        
        CCMenuItemImage selectPlayerMenuItem = CCMenuItemImage.item("button.png", "button_pressed.png", this, "selectPlayer");
        selectPlayerMenuItem.setScale(MainActivity.scale);

        CCLabel choosePlayerLabel = CCLabel.makeLabel("Select Animal", "Arial", 40f);
        choosePlayerLabel.setPosition(selectPlayerMenuItem.getContentSize().width / 2, (selectPlayerMenuItem.getContentSize().height / 2));
        choosePlayerLabel.setTag(1);

        selectPlayerMenuItem.addChild(choosePlayerLabel);
        selectPlayerMenuItem.setPosition(playGameMenuItem.getPosition().x, (float) (playGameMenuItem.getPosition().y - selectPlayerMenuItem.getContentSize().height - GameLayer.dp2px(10)));
        addChild(selectPlayerMenuItem);

        CCMenuItemImage helpMenuItem = CCMenuItemImage.item("button.png", "button_pressed.png", this, "startHelp");
        helpMenuItem.setPosition(selectPlayerMenuItem.getPosition().x, (float) (selectPlayerMenuItem.getPosition().y - helpMenuItem.getContentSize().height - GameLayer.dp2px(10)));
        helpMenuItem.setScale(MainActivity.scale);
        addChild(helpMenuItem);

        CCLabel helpLabel = CCLabel.makeLabel("Help", "Arial", 40f);
        helpLabel.setPosition(helpMenuItem.getContentSize().width / 2, helpMenuItem.getContentSize().height / 2);

        helpMenuItem.addChild(helpLabel);

        vibrationMenuToggle = CCMenuItemImage.item("button.png", "button_pressed.png", this, "toggleVibration");
        vibrationMenuToggle.setPosition(helpMenuItem.getPosition().x, (float) (helpMenuItem.getPosition().y - vibrationMenuToggle.getContentSize().height - GameLayer.dp2px(10)));
        vibrationMenuToggle.setScale(MainActivity.scale);
        addChild(vibrationMenuToggle);

        CCLabel vibrationLabel = (GameLayer.isVibrationEnabled ? CCLabel.makeLabel("Vibration is on", "Arial", 40f) : CCLabel.makeLabel("Vibration is off", "Arial", 40f));
        vibrationLabel.setPosition(vibrationMenuToggle.getContentSize().width / 2, vibrationMenuToggle.getContentSize().height / 2);
        vibrationLabel.setTag(1);
        vibrationMenuToggle.addChild(vibrationLabel);
    }

    public void buildScoreBar() {
        getParent().removeChildByTag(102, false);
        getParent().removeChildByTag(101, true);
        CGSize winSize = CCDirector.sharedDirector().winSize();
        int highscore = Integer.parseInt(((MainActivity) CCDirector.sharedDirector().getActivity()).read("highscore.dds").equals("") ? "0" : ((MainActivity) CCDirector.sharedDirector().getActivity()).read("highscore.dds"));
        int overall = Integer.parseInt(((MainActivity) CCDirector.sharedDirector().getActivity()).read("overall.dds").equals("") ? "0" : ((MainActivity) CCDirector.sharedDirector().getActivity()).read("overall.dds"));

        CCLabel highscoreLabel = CCLabel.makeLabel("Highscore: "+highscore, "Arial", 27);
        highscoreLabel.setPosition(winSize.width/6, (winSize.height/20)*19);
        highscoreLabel.setScale(MainActivity.scale);
        highscoreLabel.setTag(101);

        CCLabel overallScoreLabel = CCLabel.makeLabel("Total birds: "+overall, "Arial", 27);
        overallScoreLabel.setPosition(winSize.width/6, (winSize.height/20)*18);
        overallScoreLabel.setScale(MainActivity.scale);
        overallScoreLabel.setTag(102);

        getParent().addChild(highscoreLabel);
        getParent().addChild(overallScoreLabel);
    }

    public void update(float dt) {
        buildScoreBar();

        if(lastValueOfVibration != GameLayer.isVibrationEnabled) {
            vibrationMenuToggle.removeChildByTag(1, true);

            CCLabel vibrationLabel = (GameLayer.isVibrationEnabled ? CCLabel.makeLabel("Vibration is on", "Arial", 40f) : CCLabel.makeLabel("Vibration is off", "Arial", 40f));
            vibrationLabel.setPosition(vibrationMenuToggle.getContentSize().width / 2, vibrationMenuToggle.getContentSize().height / 2);
            vibrationLabel.setTag(1);
            vibrationMenuToggle.addChild(vibrationLabel);


            lastValueOfVibration = GameLayer.isVibrationEnabled;
        }

    }

    public void startGame(Object id) {
    	CCDirector.sharedDirector().pushScene(GameLayer.scene());
    	((MainActivity) CCDirector.sharedDirector().getActivity()).mode = 1;
    }

    public void selectPlayer(Object id) {
        CCDirector.sharedDirector().pushScene(PlayerSelectLayer.scene());
        ((MainActivity) CCDirector.sharedDirector().getActivity()).mode = 1;
    }

    public void startHelp(Object id) {
        CCDirector.sharedDirector().pushScene(HelpLayer.scene());
        ((MainActivity) CCDirector.sharedDirector().getActivity()).mode = 1;
    }

    public void toggleVibration(Object id) {
        if(GameLayer.isVibrationEnabled) {
            ((MainActivity)CCDirector.sharedDirector().getActivity()).write("settings.dds", "0");
        } else {
            ((MainActivity)CCDirector.sharedDirector().getActivity()).write("settings.dds", "1");
            v.vibrate(200);
        }

        GameLayer.isVibrationEnabled = !GameLayer.isVibrationEnabled;
    }
}
