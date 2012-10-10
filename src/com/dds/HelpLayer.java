package com.dds;

import android.view.MotionEvent;
import org.cocos2d.events.CCTouchDispatcher;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.protocols.CCTouchDelegateProtocol;
import org.cocos2d.types.CGSize;

/**
 * @author Wouter
 * Date: 10-10-12
 * Time: 16:36
 */
public class HelpLayer extends CCLayer implements CCTouchDelegateProtocol {
    public static CCScene scene() {
        CCScene returnScene = CCScene.node();

        CCLayer backgroundLayer = new MenuBackgroundLayer();

        returnScene.addChild(backgroundLayer);

        CCLayer helpLayer = new HelpLayer();

        returnScene.addChild(helpLayer);

        return returnScene;
    }

    public HelpLayer() {
        CGSize winSize = CCDirector.sharedDirector().winSize();

        String texty = "The purpose of Birdy Boom is to shoot and catch as much birds as you can. " +
                "To shoot the birds, simply tap on the birds with your finger. To catch the falling birds tilt your device to move " +
                "the catching animal. You'll lose health if you miss a bird with the animal or wait long enough for the bird to fly away. " +
                "Tap the screen to go to the menu.";

        CCTouchDispatcher.sharedDispatcher().addDelegate(this, 0);

        CGSize dimensions = CGSize.make(600, 650);

        CCLabel helpLabel = CCLabel.makeLabel(texty, dimensions, CCLabel.TextAlignment.LEFT,"Arial", 40);
        helpLabel.setPosition((float)(winSize.width/1.95), (float)(winSize.height/2.1));

        helpLabel.setScale(MainActivity.scale);

        addChild(helpLabel);
    }

    public synchronized boolean ccTouchesBegan(MotionEvent e) {
        CCTouchDispatcher.sharedDispatcher().removeAllDelegates();
        CCDirector.sharedDirector().popScene();
        ((MainActivity) CCDirector.sharedDirector().getActivity()).mode = 0;
        return false;
    }
}
