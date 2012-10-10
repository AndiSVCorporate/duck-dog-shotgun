package com.dds;

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
	protected CCMenuItem playGameMenuItem;
    protected CCMenuItem selectPlayerMenuItem;
    protected CCMenuItemImage helpMenuItem;

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
        CGSize winSize = CCDirector.sharedDirector().winSize();

        this.schedule("updateScore", 1f);

        playGameMenuItem = CCMenuItemImage.item("button.png", "button_pressed.png", this, "startGame");
        playGameMenuItem.setPosition(playGameMenuItem.getPosition().x, (float) (playGameMenuItem.getPosition().y+GameLayer.dp2px(20)));
        CCLabel playLabel = CCLabel.makeLabel("Play Game", "Arial", 40f);
        playLabel.setPosition(playGameMenuItem.getContentSize().width / 2, playGameMenuItem.getContentSize().height / 2);
        playLabel.setTag(1);

        GameLayer.scale = winSize.width/720;
        playGameMenuItem.setScale(GameLayer.scale);

        playGameMenuItem.addChild(playLabel);

        addChild(playGameMenuItem);
        
        selectPlayerMenuItem = CCMenuItemImage.item("button.png", "button_pressed.png", this, "selectPlayer");
        selectPlayerMenuItem.setScale(GameLayer.scale);

        CCLabel choosePlayerLabel = CCLabel.makeLabel("Select Animal", "Arial", 40f);
        choosePlayerLabel.setPosition(selectPlayerMenuItem.getContentSize().width / 2, (selectPlayerMenuItem.getContentSize().height / 2));
        choosePlayerLabel.setTag(1);

        selectPlayerMenuItem.addChild(choosePlayerLabel);
        selectPlayerMenuItem.setPosition(playGameMenuItem.getPosition().x, (float) (playGameMenuItem.getPosition().y - selectPlayerMenuItem.getContentSize().height - GameLayer.dp2px(10)));
        addChild(selectPlayerMenuItem);

        helpMenuItem = CCMenuItemImage.item("button.png", "button_pressed.png", this, "startHelp");
        helpMenuItem.setPosition(selectPlayerMenuItem.getPosition().x, (float) (selectPlayerMenuItem.getPosition().y - helpMenuItem.getContentSize().height - GameLayer.dp2px(10)));
        helpMenuItem.setScale(GameLayer.scale);
        addChild(helpMenuItem);

        CCLabel helpLabel = CCLabel.makeLabel("Help", "Arial", 40f);
        helpLabel.setPosition(helpMenuItem.getContentSize().width / 2, helpMenuItem.getContentSize().height / 2);
        helpLabel.setTag(1);

        helpMenuItem.addChild(helpLabel);

    }

    public void buildScoreBar() {
        getParent().removeChildByTag(102, false);
        getParent().removeChildByTag(101, true);
        CGSize winSize = CCDirector.sharedDirector().winSize();
        int highscore = Integer.parseInt(((MainActivity) CCDirector.sharedDirector().getActivity()).read("highscore.dds").equals("") ? "0" : ((MainActivity) CCDirector.sharedDirector().getActivity()).read("highscore.dds"));
        int overall = Integer.parseInt(((MainActivity) CCDirector.sharedDirector().getActivity()).read("overall.dds").equals("") ? "0" : ((MainActivity) CCDirector.sharedDirector().getActivity()).read("overall.dds"));

        CCLabel highscoreLabel = CCLabel.makeLabel("Highscore: "+highscore, "Arial", 27);
        highscoreLabel.setPosition(winSize.width/6, (winSize.height/20)*19);
        highscoreLabel.setScale(GameLayer.scale);
        highscoreLabel.setTag(101);

        CCLabel overallScoreLabel = CCLabel.makeLabel("Total birds: "+overall, "Arial", 27);
        overallScoreLabel.setPosition(winSize.width/6, (winSize.height/20)*18);
        overallScoreLabel.setScale(GameLayer.scale);
        overallScoreLabel.setTag(102);

        getParent().addChild(highscoreLabel);
        getParent().addChild(overallScoreLabel);
    }

    public void updateScore(float dt) {
        buildScoreBar();
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
}
