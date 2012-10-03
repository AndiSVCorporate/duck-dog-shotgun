package com.dds;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.opengl.CCGLSurfaceView;

/**
 * @author Wouter
 *         Date: 14-09-12
 *         Time: 17:02
 */
public class GameActivity extends Activity {

    protected static CCGLSurfaceView mGLSurfaceView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // set the window status, no tile, full screen and don't sleep
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);


    }

    @Override
    public void onStart() {
        super.onStart();

        GameActivity.mGLSurfaceView = new CCGLSurfaceView(this);

        setContentView(GameActivity.mGLSurfaceView);

        // attach the OpenGL view to a window
        CCDirector.sharedDirector().attachInView(GameActivity.mGLSurfaceView);

        // show FPS
        // set false to disable FPS display, but don't delete fps_images.png!!
        CCDirector.sharedDirector().setDisplayFPS(false);

        // frames per second
        CCDirector.sharedDirector().setAnimationInterval(1.0f / 80);

        CCScene scene = GameLayer.scene();

        // Make the Scene active
        CCDirector.sharedDirector().runWithScene(scene);
    }

    @Override
    public void onPause() {
        super.onPause();

        CCDirector.sharedDirector().pause();
    }

    @Override
    public void onResume() {
        super.onResume();

        CCDirector.sharedDirector().resume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        CCDirector.sharedDirector().end();
    }
}