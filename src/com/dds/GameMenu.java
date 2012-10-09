package com.dds;

import org.cocos2d.layers.CCScene;
import org.cocos2d.menus.*;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGSize;

/**
 * @author Wouter
 * Date: 04-10-12
 * Time: 12:45
 */
public class GameMenu extends CCMenu {
	protected CCMenuItemImage playGameMenuItem;
    protected CCMenuItemImage selectPlayerMenuItem;
	
    public static CCScene scene() {
        CCScene returnScene = CCScene.node();

        CCMenu gameMenu = new GameMenu();

        returnScene.addChild(gameMenu);

        return returnScene;
    }

    public GameMenu() {
        super();

        this.schedule("updateLabel", 0.1f);

        playGameMenuItem = CCMenuItemImage.item("button.png", "button_pressed.png", this, "startGame");
        CCLabel playLabel = CCLabel.makeLabel("Play Game", "Arial", 40f);
        playLabel.setPosition(playGameMenuItem.getContentSize().width/2, playGameMenuItem.getContentSize().height/2);
        playLabel.setTag(1);

        CGSize winSize = CCDirector.sharedDirector().winSize();

        GameLayer.scale = winSize.width/720;

        playGameMenuItem.addChild(playLabel);

        addChild(playGameMenuItem, 0);

        selectPlayerMenuItem = CCMenuItemImage.item("button.png", "button_pressed.png", this, "selectPlayer");

        CCLabel choosePlayerLabel = CCLabel.makeLabel("Select Player", "Arial", 40f);
        choosePlayerLabel.setPosition(selectPlayerMenuItem.getContentSize().width/2, selectPlayerMenuItem.getContentSize().height/2);
        choosePlayerLabel.setTag(1);

        selectPlayerMenuItem.addChild(choosePlayerLabel);
        selectPlayerMenuItem.setPosition(playGameMenuItem.getPosition().x, (float) (playGameMenuItem.getPosition().y - selectPlayerMenuItem.getContentSize().height - GameLayer.dp2px(10)));
        addChild(selectPlayerMenuItem);

    }

    public void updateLabel(float dt) {
        playGameMenuItem.removeChildByTag(1, true);

        CCLabel playLabel = CCLabel.makeLabel("Play Game", "Arial", 40f);
        playLabel.setPosition(playGameMenuItem.getContentSize().width/2, playGameMenuItem.getContentSize().height/2);
        playLabel.setTag(1);

        playGameMenuItem.addChild(playLabel);

        selectPlayerMenuItem.removeChildByTag(1, true);
        CCLabel choosePlayerLabel = CCLabel.makeLabel("Select Player", "Arial", 40f);
        choosePlayerLabel.setPosition(selectPlayerMenuItem.getContentSize().width/2, selectPlayerMenuItem.getContentSize().height/2);
        choosePlayerLabel.setTag(1);

        selectPlayerMenuItem.addChild(choosePlayerLabel);
    }

    public void startGame(Object id) {
    	CCDirector.sharedDirector().replaceScene(GameLayer.scene());
    	((MainActivity) CCDirector.sharedDirector().getActivity()).mode = 1;
    }

    public void selectPlayer(Object id) {
        CCDirector.sharedDirector().replaceScene(PlayerSelectLayer.scene());
        ((MainActivity) CCDirector.sharedDirector().getActivity()).mode = 1;
    }
}
