package com.dds;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import android.app.Activity;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.Window;
import android.view.WindowManager;

import org.cocos2d.events.CCTouchDispatcher;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCTextureCache;
import org.cocos2d.opengl.CCGLSurfaceView;


public class MainActivity extends Activity
{
	public static final String ROOT = Environment.getExternalStorageDirectory() + "/.BirdyBoom/";
    protected static CCGLSurfaceView mGLSurfaceView;
    protected int mode = 0;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        if (!(new File(MainActivity.ROOT)).exists())
        {
        	File root = new File(MainActivity.ROOT);
        	root.mkdirs();
        }

        Dog.playerImage = read("animal.dds").equals("") ? "dog.png" : read("animal.dds");

        // set the window status, no tile, full screen and don't sleep
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // be able to change the mediavolume
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    public void onStart() 
    {
        super.onStart();

        MainActivity.mGLSurfaceView = new CCGLSurfaceView(this);

        setContentView(MainActivity.mGLSurfaceView);

        // attach the OpenGL view to a window
        CCDirector.sharedDirector().attachInView(MainActivity.mGLSurfaceView);

        // set false to disable FPS display, but don't delete fps_images.png!!
        CCDirector.sharedDirector().setDisplayFPS(false);

        // frames per second
        CCDirector.sharedDirector().setAnimationInterval(1.0f / 80);

        // Make the Scene active
        CCDirector.sharedDirector().runWithScene(GameMenu.scene());
        CCDirector.sharedDirector().pushScene(HelpLayer.scene());
    }

    public void onBackPressed() 
    {
    	if (mode == 0)
        {
    		if (read("highscore.dds").equals(""))
    		{
    			write("highscore.dds", Math.max(GameLayer.score, Integer.parseInt(read("highscore.dds").equals("") ? "0" : read("highscore.dds"))) + "");
    			write("overall.dds", (Integer.parseInt(read("overall.dds").equals("") ? "0" : read("overall.dds")) + GameLayer.score) + "");
    		}
    		onDestroy();
    		finish();
        }
    	else if (mode == 1)
    	{
    		write("highscore.dds", Math.max(GameLayer.score, Integer.parseInt(read("highscore.dds").equals("") ? "0" : read("highscore.dds"))) + "");
    		write("overall.dds", (Integer.parseInt(read("overall.dds").equals("") ? "0" : read("overall.dds")) + GameLayer.score) + "");
    		GameLayer.reset();
    		
    		CCTouchDispatcher.sharedDispatcher().removeAllDelegates();
    		CCDirector.sharedDirector().popScene();
    		mode = 0;
    	}
    }

    @Override
    public void onPause()
    {
        super.onPause();

        CCDirector.sharedDirector().pause();
    }

    @Override
    public void onResume() 
    {
        super.onResume();
        
        CCDirector.sharedDirector().resume();
    }

    @Override
    public void onDestroy() 
    {
        super.onDestroy();

        CCDirector.sharedDirector().end();
    }
    
    public String read(String file)
    {
    	String result = "";
    	try
    	{
	    	FileInputStream fIn = openFileInput(file);
	        InputStreamReader isr = new InputStreamReader(fIn);
	        char[] inputBuffer = new char[1];
	        
	        while (isr.read(inputBuffer) != -1)
	        {
	        	result += new String(inputBuffer);
	        }
		} 
	    catch (IOException e) {}
    	
    	return result;
    }
    
    public void write(String file, String content)
    {
    	 try 
    	 {
             FileOutputStream fOut = openFileOutput(file, MODE_WORLD_READABLE);
             OutputStreamWriter osw = new OutputStreamWriter(fOut); 

             osw.write(content);
             osw.flush();
             osw.close();
    	 }
    	 catch (IOException e) {}
    }
}
