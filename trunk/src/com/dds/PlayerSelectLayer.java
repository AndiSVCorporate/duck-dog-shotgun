package com.dds;

import android.view.MotionEvent;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.nodes.CCNode;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.protocols.CCTouchDelegateProtocol;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGSize;

import java.util.List;

/**
 * @author Wouter
 * Date: 09-10-12
 * Time: 17:18
 */
public class PlayerSelectLayer extends CCLayer implements CCTouchDelegateProtocol {
	private static int pointsRequired = 150;

    public static CCScene scene() {
        CCScene scene = CCScene.node();

        CCLayer playerSelectLayer = new PlayerSelectLayer();
        CCLayer background = new MenuBackgroundLayer();

        scene.addChild(background);

        scene.addChild(playerSelectLayer);

        return scene;
    }

    public PlayerSelectLayer() {
        this.setIsTouchEnabled(true);

        CCSprite animal = null;

        CGSize winSize = CCDirector.sharedDirector().winSize();

        CCLabel label = CCLabel.makeLabel("Every extra 150 catched birds is worth a new animal.", "Arial", 25);
        label.setScale(MainActivity.scale);
        label.setPosition((float)(winSize.width/2), (float)((winSize.height/22)*21));
        addChild(label);

        int x = (int) (winSize.width / 2);

        for(int i = 0; i < 4; i++) {
            switch (i) {
                case 0:
                    animal = CCSprite.sprite("raccoon_head.png");
                    break;
                case 1:
                    animal = CCSprite.sprite("panda_head.png");
                    break;
                case 2:
                    animal = CCSprite.sprite("tiger_head.png");
                    break;
                case 3:
                    animal = CCSprite.sprite("dog_head.png");
                    break;
                default:
                    break;
            }
            
            int overall = Integer.parseInt(((MainActivity) CCDirector.sharedDirector().getActivity()).read("overall.dds"));

            if (overall < 3 * pointsRequired - i * pointsRequired)
            {
            	animal.setOpacity(125);
            }

            animal.setScale(MainActivity.scale);
            
            animal.setPosition(x, (float) (((winSize.height) / 4) * i + animal.getContentSize().height / 2));
            addChild(animal);
        }
    }

    public synchronized boolean ccTouchesBegan(MotionEvent e) {
        CGSize winSize = CCDirector.sharedDirector().winSize();

        List<CCNode> children = getChildren();
        float touchX = e.getX();
        double touchY = winSize.getHeight() - e.getY();

        for(int i = 0; i < children.size(); i++) {
            CCSprite child = (CCSprite) children.get(i);
            CGPoint pos = child.getPosition();

            if(child.getOpacity() == 255 && pos.x <= touchX + (child.getContentSize().width / 2) && pos.x >= touchX - (child.getContentSize().width / 2) && pos.y <= touchY + (child.getContentSize().height / 2) && pos.y >= touchY - (child.getContentSize().height / 2)) {
            	switch (i - 1) {
                    case 0:
                        Dog.playerImage = "raccoon.png";
                        break;
                    case 1:
                        Dog.playerImage = "panda.png";
                        break;
                    case 2:
                        Dog.playerImage = "tiger.png";
                        break;
                    case 3:
                        Dog.playerImage = "dog.png";
                        break;
                    default:
                        break;
                }
                ((MainActivity) CCDirector.sharedDirector().getActivity()).write("animal.dds", Dog.playerImage);
                CCDirector.sharedDirector().popScene();
            }
        }

        return false;
    }
}
