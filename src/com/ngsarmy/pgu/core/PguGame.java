package com.ngsarmy.pgu.core;

import org.lwjgl.LWJGLException;
import org.lwjgl.openal.AL;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.openal.SoundStore;

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
	public static final int WIDTH = 480 * 2; 
	public static final int HEIGHT = 240 * 2;
	
	// Game globals
	public static PguColor bgColor = new PguColor();
	public static float volume = 1.0f;
	public static float elapsed = 0;
	// end of game globals
	
	private float rotation = 0;
	
	public PguGame()
	{
	}
	
	public void run()
	{
		try
		{
			Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
			Display.create();
		}
		catch(LWJGLException e)
		{
			e.printStackTrace();
			System.exit(1);
		}
		
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, WIDTH, HEIGHT, 0, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		
		long lastTime = System.nanoTime();
		long delta;
		
		long frames = 0;
		long timer = System.currentTimeMillis();
		
		while(!Display.isCloseRequested())
		{
			delta = System.nanoTime() - lastTime;
			lastTime = System.nanoTime();
			
			elapsed = PguUtils.nanoToSeconds(delta);
			
			update();
			render();

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
		AL.destroy();
	}
	
	public void update()
	{
		rotation += 50 * elapsed;
	}
	
	public void render()
	{
		GL11.glClearColor(bgColor.r, bgColor.g, bgColor.b, bgColor.a);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
		
		GL11.glLoadIdentity();
		
		PguTexture.fromFile("PGULogo.png").bind();
		
		GL11.glScalef(2, 2, 1);
		GL11.glRotatef(rotation, 0, 0, 1);
		
		GL11.glBegin(GL11.GL_TRIANGLES);
		GL11.glTexCoord2f(0, 0);
		GL11.glVertex2f(0, 0);
		GL11.glTexCoord2f(1, 0);
		GL11.glVertex2f(100, 0);
		GL11.glTexCoord2f(1, 1);
		GL11.glVertex2f(100, 100);
		GL11.glTexCoord2f(0, 0);
		GL11.glVertex2f(0, 0);
		GL11.glTexCoord2f(1, 1);
		GL11.glVertex2f(100, 100);
		GL11.glTexCoord2f(0, 1);
		GL11.glVertex2f(0, 100);
		GL11.glEnd();
	}
	
	// MAIN METHOD:
	// here is where the game class is created and the initial state is loaded
	public static void main(String[] args)
	{
		PguGame game = new PguGame();
		game.run();
	}
}
