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
public class GameMenu extends CCMenu {
	protected CCMenuItem playGameMenuItem;
    protected CCMenuItem selectPlayerMenuItem;
    protected CCMenuItemImage helpMenuItem;

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
        
        playGameMenuItem = CCMenuItemImage.item("button.png", "button_pressed.png", this, "startGame");
        playGameMenuItem.setPosition(playGameMenuItem.getPosition().x, (float) (playGameMenuItem.getPosition().y+GameLayer.dp2px(20)));
        CCLabel playLabel = CCLabel.makeLabel("Play Game", "Arial", 40f);
        playLabel.setPosition(playGameMenuItem.getContentSize().width / 2, playGameMenuItem.getContentSize().height / 2);
        playLabel.setTag(1);

        CGSize winSize = CCDirector.sharedDirector().winSize();

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
        addChild(helpMenuItem);

        CCLabel helpLabel = CCLabel.makeLabel("Help", "Arial", 40f);
        helpLabel.setPosition(helpMenuItem.getContentSize().width / 2, helpMenuItem.getContentSize().height / 2);
        helpLabel.setTag(1);

        helpMenuItem.addChild(helpLabel);

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
