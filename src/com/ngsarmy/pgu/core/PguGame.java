package com.ngsarmy.pgu.core;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.openal.AL;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.openal.SoundStore;

import com.ngsarmy.pgu.graphics.PguImage;

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
	public static final int WIDTH = 480; 
	public static final int HEIGHT = 240;
	public static final int SCALE = 2;
	
	private PguImage graphic;
	private PguPoint camera;
	
	public PguGame()
	{
	}
	
	public void run()
	{
		try
		{
			Display.setDisplayMode(new DisplayMode(WIDTH * SCALE, HEIGHT * SCALE));
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
		
		graphic = new PguImage("PGULogo.png", new PguRectangle(0, 0, 32, 42));
		graphic.originX = graphic.getWidth() / 2;
		
		camera = new PguPoint(0, 0);
		
		while(!Display.isCloseRequested())
		{
			delta = System.nanoTime() - lastTime;
			lastTime = System.nanoTime();
			
			PguG.elapsed = PguUtils.nanoToSeconds(delta);
			
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
		rotation += 100 * PguG.elapsed;
		graphic.angle = rotation;
		
		if(Keyboard.isKeyDown(Keyboard.KEY_A))
			camera.x -= 200 * PguG.elapsed;
		
		if(Keyboard.isKeyDown(Keyboard.KEY_D))
			camera.x += 200 * PguG.elapsed;
		
		if(Keyboard.isKeyDown(Keyboard.KEY_W))
			camera.y -= 200 * PguG.elapsed;
		
		if(Keyboard.isKeyDown(Keyboard.KEY_S))
			camera.y += 200 * PguG.elapsed;
	}
	
	public void render()
	{
		GL11.glClearColor(PguG.bgColor.fR(), PguG.bgColor.fG(), PguG.bgColor.fB(), PguG.bgColor.fA());
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
		
		PguPoint p = PguPoint.get(100, 100);
		graphic.render(p, camera);
		PguPoint.dispose(p);
	}
	
	// MAIN METHOD:
	// here is where the game class is created and the initial state is loaded
	public static void main(String[] args)
	{
		PguGame game = new PguGame();
		game.run();
	}
}
