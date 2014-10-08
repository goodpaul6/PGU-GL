package com.ngsarmy.pgu.graphics;

/* PguAnimation class:
 * an internal class used by
 * PGU to store animation
 * data.
 */
final class PguAnimation
{
	// animation name (like "run" and "jump")
	public String name;
	// frame indices (indices being the locations within the image represented as a natural number)
	public int[] frames;
	// animation speed
	public float frameRate;
	// whether the animation loops
	public boolean loop;
	
	// parent spritemap object
	private PguSpritemap _parent;
	
	public PguAnimation(String name, int[] frames, float frameRate, boolean loop, PguSpritemap parent)
	{
		this.name = name;
		this.frames = frames;
		this.frameRate = (frameRate <= 0 ? 1 : frameRate);
		this.loop = loop;
		_parent = parent;
	}
	
	public PguSpritemap getParent()
	{
		return _parent;
	}
}
