package com.dds;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.menus.*;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.types.CGSize;

import android.util.Log;

/**
 * @author Wouter
 * Date: 04-10-12
 * Time: 12:45
 */
public class GameMenu extends CCMenu {
	protected CCMenuItemImage playGameMenuItem;
    protected CCMenuItemImage selectPlayerMenuItem;
    protected CCMenuItemImage helpMenuItem;
    
    private CCLabel playLabel;
    private CCLabel choosePlayerLabel;
    private CCLabel helpLabel;
	
    public static CCScene scene() {
        CCScene returnScene = CCScene.node();

        CCMenu gameMenu = new GameMenu();

        CCLayer backgroundLayer = new MenuBackgroundLayer();

        backgroundLayer.addChild(gameMenu);

        returnScene.addChild(backgroundLayer);

        return returnScene;
    }

    public GameMenu() {
        super();

        this.schedule("updateLabel", 0.1f);

        playGameMenuItem = CCMenuItemImage.item("button.png", "button_pressed.png", this, "startGame");
        playLabel = CCLabel.makeLabel("Play Game", "Arial", 40f);
        playLabel.setPosition(playGameMenuItem.getContentSize().width/2, playGameMenuItem.getContentSize().height/2);
        playLabel.setTag(1);

        CGSize winSize = CCDirector.sharedDirector().winSize();

        GameLayer.scale = winSize.width/720;
        playGameMenuItem.setScale(GameLayer.scale);

        playGameMenuItem.addChild(playLabel);

        addChild(playGameMenuItem);

        selectPlayerMenuItem = CCMenuItemImage.item("button.png", "button_pressed.png", this, "selectPlayer");
        selectPlayerMenuItem.setScale(GameLayer.scale);

        choosePlayerLabel = CCLabel.makeLabel("Select Player", "Arial", 40f);
        choosePlayerLabel.setPosition(selectPlayerMenuItem.getContentSize().width/2, (float)((selectPlayerMenuItem.getContentSize().height/2)+GameLayer.dp2px(20)));
        choosePlayerLabel.setTag(1);

        selectPlayerMenuItem.addChild(choosePlayerLabel);
        selectPlayerMenuItem.setPosition(playGameMenuItem.getPosition().x, (float) (playGameMenuItem.getPosition().y - selectPlayerMenuItem.getContentSize().height - GameLayer.dp2px(10)));
        addChild(selectPlayerMenuItem);

        helpMenuItem = CCMenuItemImage.item("button.png", "button_pressed.png", this, "startHelp");
        helpMenuItem.setPosition(selectPlayerMenuItem.getPosition().x, (float) (selectPlayerMenuItem.getPosition().y - helpMenuItem.getContentSize().height - GameLayer.dp2px(10)));
        addChild(helpMenuItem);

        helpLabel = CCLabel.makeLabel("Help", "Arial", 40f);
        helpLabel.setPosition(helpMenuItem.getContentSize().width/2, helpMenuItem.getContentSize().height/2);
        helpLabel.setTag(1);

        helpMenuItem.addChild(helpLabel);

    }

    public void updateLabel(float t) {
        playGameMenuItem.removeChildByTag(1, true);

        playLabel = CCLabel.makeLabel("Play Game", "Arial", 40f);
        playLabel.setPosition(playGameMenuItem.getContentSize().width/2, playGameMenuItem.getContentSize().height/2);
        playLabel.setTag(1);

        playGameMenuItem.addChild(playLabel);

        selectPlayerMenuItem.removeChildByTag(1, true);
        choosePlayerLabel = CCLabel.makeLabel("Select Player", "Arial", 40f);
        choosePlayerLabel.setPosition(selectPlayerMenuItem.getContentSize().width / 2, selectPlayerMenuItem.getContentSize().height / 2);
        choosePlayerLabel.setTag(1);

        selectPlayerMenuItem.addChild(choosePlayerLabel);

        helpMenuItem.removeChildByTag(1, true);

        helpLabel = CCLabel.makeLabel("Play Game", "Arial", 40f);
        helpLabel.setPosition(helpMenuItem.getContentSize().width/2, helpMenuItem.getContentSize().height/2);
        helpLabel.setTag(1);

        helpMenuItem.addChild(helpLabel);

    }

    public void startGame(Object id) {
        CCDirector.sharedDirector().end();
        CCDirector.sharedDirector().runWithScene(GameLayer.scene());
    	((MainActivity) CCDirector.sharedDirector().getActivity()).mode = 1;
    }

    public void selectPlayer(Object id) {
        CCDirector.sharedDirector().end();
        CCDirector.sharedDirector().runWithScene(PlayerSelectLayer.scene());
        ((MainActivity) CCDirector.sharedDirector().getActivity()).mode = 2;
    }

    public void startHelp(Object id) {
        CCDirector.sharedDirector().end();
        CCDirector.sharedDirector().runWithScene(HelpLayer.scene());
        ((MainActivity) CCDirector.sharedDirector().getActivity()).mode = 2;
    }
}
