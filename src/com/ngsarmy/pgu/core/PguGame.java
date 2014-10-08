package com.ngsarmy.pgu.core;

import java.util.ArrayList;
import java.util.Stack;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.openal.SoundStore;

import com.ngsarmy.pgu.template.GameScene;
import com.ngsarmy.pgu.utils.PguInput;

/* PguGame class:
 * This is the class where
 * all the action happens.
 * This is where the 
 * game loop resides.
 * A lot of globals 
 * are also kept in
 * this class, such 
 * as the background color
 */
public class PguGame 
{
	// whether the game is FULLY paused (no processing at all)
	private boolean _paused;
	
	// the scene stack
	private Stack<PguScene> _scenes;
	
	// scenes to be added and removed, respectively
	private ArrayList<PguScene> _add;
	private ArrayList<PguScene> _remove;
	
	public PguGame()
	{
		try
		{
			Display.setDisplayMode(new DisplayMode((int)(PguG.width * PguG.scale), (int)(PguG.height * PguG.scale)));
			Display.setVSyncEnabled(PguG.ENABLE_VSYNC);
			Display.create();
		}
		catch(LWJGLException e)
		{
			e.printStackTrace();
			System.exit(1);
		}
		
		_scenes = new Stack<PguScene>();
		_add = new ArrayList<PguScene>();
		_remove = new ArrayList<PguScene>();
	}
	
	public void run()
	{
		_paused = false;
		
		PguG.game = this;
		
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, PguG.width, PguG.height, 0, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		
		long lastTime = System.nanoTime();
		long delta;
		
		long frames = 0;
		long timer = System.currentTimeMillis();

		while(!Display.isCloseRequested())
		{
			delta = System.nanoTime() - lastTime;
			lastTime = System.nanoTime();
			
			PguG.elapsed = PguUtils.nanoToSeconds(delta);
			
			if(!_paused)
			{
				PguInput.update();
				update();
				render();
			}
			
			SoundStore.get().poll(0);
			
			++frames;
			if((System.currentTimeMillis() - timer) / 1000.0 >= 1)
			{
				System.out.println("FPS: " + frames);
				frames = 0;
				timer = System.currentTimeMillis();
			}
			
			Display.update();
		}
		
		Display.destroy();
	}
	
	private void update()
	{
		applyChanges();
		
		for(int i = 0; i < _scenes.size(); i++)
		{
			PguScene scene = _scenes.get(i);
			scene.applyChanges();
			if(!scene.update()) break;
		}
	}
	
	private void render()
	{
		GL11.glClearColor(PguG.bgColor.fR(), PguG.bgColor.fG(), PguG.bgColor.fB(), PguG.bgColor.fA());
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
		
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		
		applyChanges();
		
		for(int i = 0; i < _scenes.size(); i++)
		{
			PguScene scene = _scenes.get(i);
			scene.applyChanges();
			if(!scene.render()) break;
		}
		
		GL11.glFlush();
	}
	
	private void applyChanges()
	{	
		for(int i = 0; i < _add.size(); i++)
		{
			PguScene s = _add.get(i);
			_scenes.add(s);
			PguG.scene = s;
			s.begin();
		}
		_add.clear();
		
		for(int i = 0; i < _remove.size(); i++)
		{
			PguScene s = _remove.get(i);
			_scenes.remove(_remove.get(i));
			PguG.scene = null;
			s.end();
		}
		_remove.clear();
		
		if(_scenes.size() > 0)
			PguG.scene = _scenes.lastElement();
		else
			PguG.scene = null;
	}
	
	// USAGE:
	// pushes a scene onto the scene stack (overlays a scene)
	// this does not remove the previous scene, if you want to do that, use transScene
	// scene -> the scene to push onto the stack
	public void pushScene(PguScene scene)
	{
		_add.add(scene);
	}
	
	// USAGE:
	// pops a scene off the scene stack if there is one (removes the current scene)
	// this does not push a new scene onto the stack, if you want to do that, use transScene
	public void popScene()
	{
		if(_scenes.size() > 0)
			_remove.add(_scenes.lastElement());
	}
	
	// USAGE:
	// pops the current scene off the stack and pushes a new one
	// scene -> the scene to push after popping the current
	public void transScene(PguScene scene)
	{
		popScene();
		pushScene(scene);
	}
	
	// MAIN METHOD:
	// here is where the game class is created and the initial state is loaded
	public static void main(String[] args)
	{
		PguGame game = new PguGame();
		game.pushScene(new GameScene());
		game.run();
	}
}
