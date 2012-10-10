package com.dds;

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
    
    private CCLabel playLabel;
    private CCLabel choosePlayerLabel;
	
    public static CCScene scene() {
        CCScene returnScene = CCScene.node();

        CCMenu gameMenu = new GameMenu();

        returnScene.addChild(gameMenu);

        return returnScene;
    }

    public GameMenu() {
        super();

        this.schedule("updateLabel", 0.1f);
//        this.scheduleUpdate(1);

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
        choosePlayerLabel.setPosition(selectPlayerMenuItem.getContentSize().width/2, selectPlayerMenuItem.getContentSize().height/2);
        choosePlayerLabel.setTag(1);

        selectPlayerMenuItem.addChild(choosePlayerLabel);
        selectPlayerMenuItem.setPosition(playGameMenuItem.getPosition().x, (float) (playGameMenuItem.getPosition().y - selectPlayerMenuItem.getContentSize().height - GameLayer.dp2px(10)));
        addChild(selectPlayerMenuItem);

    }

    public void updateLabel(float t) {
        playGameMenuItem.removeChildByTag(1, true);

        playLabel = CCLabel.makeLabel("Play Game", "Arial", 40f);
        playLabel.setPosition(playGameMenuItem.getContentSize().width/2, playGameMenuItem.getContentSize().height/2);
        playLabel.setTag(1);

        playGameMenuItem.addChild(playLabel);

        selectPlayerMenuItem.removeChildByTag(1, true);
        choosePlayerLabel = CCLabel.makeLabel("Select Player", "Arial", 40f);
        choosePlayerLabel.setPosition(selectPlayerMenuItem.getContentSize().width/2, selectPlayerMenuItem.getContentSize().height/2);
        choosePlayerLabel.setTag(1);

        selectPlayerMenuItem.addChild(choosePlayerLabel);
    }
    
//    public void update(float t)
//    {
//    	playLabel.setString("Play Game");
//    	choosePlayerLabel.setString("Select Player");
//    	
//    	playGameMenuItem.setPosition(playGameMenuItem.getPosition());
//    	selectPlayerMenuItem.setPosition(selectPlayerMenuItem.getPosition());
//    }

    public void startGame(Object id) {
    	CCDirector.sharedDirector().replaceScene(GameLayer.scene());
    	((MainActivity) CCDirector.sharedDirector().getActivity()).mode = 1;
    }

    public void selectPlayer(Object id) {
        CCDirector.sharedDirector().replaceScene(PlayerSelectLayer.scene());
        ((MainActivity) CCDirector.sharedDirector().getActivity()).mode = 1;
    }
}
