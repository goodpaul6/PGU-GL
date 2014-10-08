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
	// the game engine host
	public static PguGame game;
	// width of the viewport
	public static int width = 480;
	// height of the viewport
	public static int height = 240;
	// scale of the viewport
	public static float scale = 2;
	// background color
	public static PguColor bgColor = new PguColor();
	// volume (this affects the volume of both music and sound effects)
	public static float volume = 1.0f;
	// delta time
	public static float elapsed = 0;
	// default texture blending mode: change this as needed
	public static final int DEFAULT_TEXTURE_BLENDING_MODE = PguTexture.NEAREST;
	// whether or not the text should be smoothened out (bad for pixel-y games)
	public static final boolean ANTIALIAS_TEXT = false;
	// whether this program's framerate should sync with the screen's framerate
	public static final boolean ENABLE_VSYNC = false;
	// the sample amount for the collision detection (the higher this value, the slower the game, but the better the collision detection)
	public static int entityCollisionSampleAmount = 2;
	// current game scene (CAN BE null)
	public static PguScene scene;
	// current zoom value (if the value is less than 1, it will zoom out, greater than one, zoom in, otherwise, normal) 
	public static float zoom = 1;
	
	
	// USAGE:
	// point1 -> the PguPoint from which the distance will be calculated
	// point2 -> the other PguPoint
	// returns the distance SQUARED between 2 points
	// NOTE: this is faster than regular distance
	public static float distanceSquared(PguPoint point1, PguPoint point2)
	{
		return (point1.x - point2.x) * (point1.x - point2.x) + (point1.y - point2.y) * (point1.y - point2.y);
	}
	
	// USAGE:
	// point1 -> the PguPoint from which the distance will be calculated
	// point2 -> the other PguPoint
	// returns the distance between 2 points
	// NOTE: this is slower than the distanceSquared method
	public static float distance(PguPoint point1, PguPoint point2)
	{
		return (float)Math.sqrt(distanceSquared(point1, point2));
	}
}
