package com.ngsarmy.pgu.core;

/* PguRectangle class:
 * stores a rectangle's 
 * x, y, width and height.
 * Provides some methods
 * for checking overlap with
 * other rectangles and whether
 * it contains a point
 */
public class PguRectangle 
{
	public float x, y, w, h;
	
	public PguRectangle(float x, float y, float w, float h)
	{
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}
	
	public PguRectangle(PguPoint position, PguPoint size)
	{
		this(position.x, position.y, size.x, size.y);
	}
	
	public PguRectangle()
	{
		this(0, 0, 0, 0);
	}
	
	public boolean collides(PguRectangle other)
	{
		if(x < other.x + other.w && y < other.y + other.h
		&& x + w > other.x && y + h > other.y)
			return true;
		return false;
	}
	
	public boolean contains(float x, float y)
	{
		if(x > this.x && y > this.y && x < this.x + w && y < this.y + h)
			return true;
		return false;
	}
	
	public boolean contains(PguPoint p)
	{
		return contains(p.x, p.y);
	}
}
