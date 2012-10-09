package com.dds;

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
public class GameMenu extends CCMenu {
	protected static CCLabel playLabel;
	
    public static CCScene scene() {
        CCScene returnScene = CCScene.node();

        CCMenu gameMenu = new GameMenu();

        returnScene.addChild(gameMenu);

        return returnScene;
    }

    public GameMenu() {
        super();
    	this.scheduleUpdate();
        CGSize winSize = CCDirector.sharedDirector().displaySize();

        CCMenuItemImage menuItem = CCMenuItemImage.item("button.png", "button_pressed.png", this, "startGame");
        playLabel = CCLabel.makeLabel("Play Game", "Arial", 40f);
        playLabel.setPosition(menuItem.getContentSize().width/2, menuItem.getContentSize().height/2);
        menuItem.addChild(playLabel);

        addChild(menuItem, 0);
    }

    public void startGame(Object id) {
    	CCDirector.sharedDirector().replaceScene(GameLayer.scene());
    	((MainActivity) CCDirector.sharedDirector().getActivity()).mode = 1;
    }
    
    public void update(float time)
    {
    	playLabel.setString("Play Game");
    }
}