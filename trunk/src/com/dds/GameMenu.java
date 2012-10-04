package com.dds;

import org.cocos2d.layers.CCScene;
import org.cocos2d.menus.CCMenu;
import org.cocos2d.menus.CCMenuItemLabel;
import org.cocos2d.nodes.CCNode;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.ccColor3B;

/**
 * @author Wouter
 *         Date: 04-10-12
 *         Time: 12:45
 */
public class GameMenu extends CCMenu {
    public static CCScene scene() {
        CCScene returnScene = CCScene.node();

        CCMenu gameMenu = new GameMenu();

        returnScene.addChild(gameMenu);

        return returnScene;
    }

    public GameMenu() {
        CCNode ccStartNode = CCNode.node();
        ccStartNode.setPosition(CGPoint.ccp(300, 300));
        ccStartNode.setAnchorPoint(CGPoint.ccp(0, 0));

        CCMenuItemLabel startGame = CCMenuItemLabel.item("text", this, "");
        startGame.setPosition(CGPoint.ccp(0, 0));
        startGame.setAnchorPoint(CGPoint.ccp(0, 0));
        startGame.setColor(ccColor3B.ccc3(139, 69, 19));
        startGame.setScale(0.5f);

        CCMenu menu2 = CCMenu.menu(startGame);
        menu2.alignItemsVertically(-10f);
        menu2.setPosition(CGPoint.ccp(-20, 0));
        menu2.setAnchorPoint(CGPoint.ccp(0, 0));
        // addChild(menu2);

        ccStartNode.addChild(menu2);
    }
}
