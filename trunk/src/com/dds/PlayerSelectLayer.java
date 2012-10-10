package com.dds;

import android.view.MotionEvent;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCNode;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.protocols.CCTouchDelegateProtocol;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGSize;

import java.util.List;

/**
 * @author Wouter
 *         Date: 09-10-12
 *         Time: 17:18
 */
public class PlayerSelectLayer extends CCLayer implements CCTouchDelegateProtocol {

    public static CCScene scene() {
        CCScene scene = CCScene.node();

        CCLayer playerSelectLayer = new PlayerSelectLayer();

        scene.addChild(playerSelectLayer);

        return scene;
    }

    public PlayerSelectLayer() {
        this.setIsTouchEnabled(true);

        CCSprite animal = null;

        CGSize winSize = CCDirector.sharedDirector().winSize();

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

            animal.setScale(GameLayer.scale);
            
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
            CCNode child = children.get(i);
            CGPoint pos = child.getPosition();

            if(((double) pos.x <= touchX && (double) pos.x >= (touchX - GameLayer.dp2px(child.getContentSize().width)))
                    && (pos.y <= touchY && pos.y >= touchY - GameLayer.dp2px(child.getContentSize().height))) {
                switch (i) {
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
                CCDirector.sharedDirector().replaceScene(GameMenu.scene());
            }
        }

        return false;
    }
}
