package com.ngsarmy.pgu.core;

import org.lwjgl.opengl.GL11;

public class PguUtils 
{
	public static final double nanoSecond = 1000000000;
	
	public static float nanoToSeconds(long nano)
	{
		return (float)(nano / 1000000000.0);
	}
	
	public static void unbindTexture()
	{
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
	}
	
	public static float getMouseX()
	{
		
	}
}
