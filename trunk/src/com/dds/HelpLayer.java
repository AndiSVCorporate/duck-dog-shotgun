package com.dds;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGSize;

/**
 * @author Wouter
 * Date: 10-10-12
 * Time: 16:36
 */
public class HelpLayer extends CCLayer {
    public static CCScene scene() {
        CCScene returnScene = CCScene.node();

        CCLayer backgroundLayer = new MenuBackgroundLayer();

        returnScene.addChild(backgroundLayer);

        CCLayer helpLayer = new HelpLayer();

        returnScene.addChild(helpLayer);

        return returnScene;
    }

    public HelpLayer() {
        CCLabel helpLabel = CCLabel.makeLabel("The purpose of Birdy Boom is to shoot " +
                "and catch as much birds as you can. To shoot the birds, simply tap on the birds with your finger. " +
                "To catch the falling birds tilt your device to move the catching animal. ","Arial", 18);

        helpLabel.setPosition((int) GameLayer.dp2px(10), (int) GameLayer.dp2px(600));

        addChild(helpLabel);
    }
}
