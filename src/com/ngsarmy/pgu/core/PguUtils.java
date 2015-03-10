package com.ngsarmy.pgu.core;

import org.lwjgl.opengl.GL11;

/* PguUtils class:
 * these are utilities specific
 * to the engine, you need not
 * use these. The utilities which
 * you will need are in the PguG
 * class.
 */
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
	
	public static void renderQuad(float x, float y, float w, float h, float u1, float u2, float v1, float v2)
	{
		GL11.glBegin(GL11.GL_TRIANGLES);
		
		GL11.glTexCoord2f(u1, v1);
		GL11.glVertex2f(x, y);
		GL11.glTexCoord2f(u2, v1);
		GL11.glVertex2f(x + w, y);
		GL11.glTexCoord2f(u2, v2);
		GL11.glVertex2f(x + w, y + h);
		GL11.glTexCoord2f(u1, v1);
		GL11.glVertex2f(x, y);
		GL11.glTexCoord2f(u2, v2);
		GL11.glVertex2f(x + w, y + h);
		GL11.glTexCoord2f(u1, v2);
		GL11.glVertex2f(x, y + h);
		
		GL11.glEnd();
	}
	
	public static boolean boxVsBox(float x1, float y1, float w1, float h1, float x2, float y2, float w2, float h2)
	{
		if(x1 + w1 < x2 || x2 + w2 < x1) return false;
		if(y1 + h1 < y2 || y2 + h2 < y1) return false;
		
		return false;
	}
}
