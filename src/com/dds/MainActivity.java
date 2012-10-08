package com.dds;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.opengl.CCGLSurfaceView;

public class MainActivity extends Activity
{

    protected static CCGLSurfaceView mGLSurfaceView;
    protected int mode = 0;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        setTheme(R.style.DarkTheme);
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.main);

        super.onCreate(savedInstanceState);

        // set the window status, no tile, full screen and don't sleep
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

    }

    public void startGame(View view) {
//        Intent intent = new Intent(this, GameActivity.class);
//        startActivity(intent);
    	CCDirector.sharedDirector().replaceScene(GameLayer.scene());
    	mode = 1;
    }

    public void onStart() {
        super.onStart();

        MainActivity.mGLSurfaceView = new CCGLSurfaceView(this);

        setContentView(MainActivity.mGLSurfaceView);

        // attach the OpenGL view to a window
        CCDirector.sharedDirector().attachInView(MainActivity.mGLSurfaceView);

        // show FPS
        // set false to disable FPS display, but don't delete fps_images.png!!
        CCDirector.sharedDirector().setDisplayFPS(false);

        // frames per second
        CCDirector.sharedDirector().setAnimationInterval(1.0f / 80);

        // Make the Scene active
        CCDirector.sharedDirector().runWithScene(GameMenu.scene());
    }

    public void onBackPressed() {
    	if (mode == 0)
        {
    		onDestroy();
    		finish();
        }
    	else if (mode == 1)
    	{
    		CCDirector.sharedDirector().replaceScene(GameMenu.scene());
    		mode = 0;
    	}
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
