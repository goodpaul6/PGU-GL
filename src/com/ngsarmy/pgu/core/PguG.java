package com.ngsarmy.pgu.core;

/* PguG class:
 * stores global values
 * and methods (but this is
 * not where you store yours)
 * These methods and values
 * are used by the engine
 * and can be used by you.
 */
public class PguG 
{
	// width of the viewport
	public static int width = PguGame.WIDTH;
	// height of the viewport
	public static int height = PguGame.HEIGHT;
	// background color
	public static PguColor bgColor = new PguColor();
	// volume
	public static float volume = 1.0f;
	// delta time
	public static float elapsed = 0;
	// default texture blending mode: change this as needed
	public static final int defaultTextureBlendingMode = PguTexture.NEAREST;
}
