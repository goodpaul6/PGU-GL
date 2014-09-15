package com.ngsarmy.pgu.core;

/* PguPoint class:
 * provides lots of
 * utilities for storing
 * 2-dimensional coords.
 * The get() static method
 * of this class returns a
 * cached instance of a PguPoint
 * to reduce memory allocation.
 * If you use a PguPoint created
 * with PguPoint.get, make sure to call
 * PguPoint.dispose on it
 */
public class PguPoint 
{
	// internal pool
	private static PguPool<PguPoint> pool = new PguPool<PguPoint>();
	
	public static PguPoint get(float x, float y)
	{
		if(pool.size() > 0)
			return pool.pop().set(x, y);
		
		return new PguPoint(0, 0);
	}
	
	public static PguPoint get()
	{
		return get(0, 0);
	}
	
	public float x;
	public float y;
	
	public PguPoint()
	{
		x = 0;
		y = 0;
	}
	
	public PguPoint(float x, float y)
	{
		this.x = x;
		this.y = y;
	}
	
	public PguPoint set(float x, float y)
	{
		this.x = x;
		this.y = y;
		return this;
	}
	
	public PguPoint set(PguPoint p)
	{
		this.x = p.x;
		this.y = p.y;
		return this;
	}
	
	public float length2()
	{
		return (x * x + y * y);
	}

	public float length()
	{
		return (float)Math.sqrt(x * x + y * y);
	}
	
	public float distanceTo(PguPoint p)
	{
		float xDist = p.x - x;
		float yDist = p.y - y;
		
		return (float)Math.sqrt(xDist * xDist + yDist * yDist);
	}
	
	public float dot(PguPoint p)
	{
		return x * p.x + y * p.y;
	}
	
	public PguPoint add(float x, float y)
	{
		this.x += x;
		this.y += y;
		return this;
	}
	
	public PguPoint sub(float x, float y)
	{
		this.x -= x;
		this.y -= y;
		return this;
	}
	
	public PguPoint mul(float x, float y)
	{
		this.x *= x;
		this.y *= y;
		return this;
	}
	
	public PguPoint div(float x, float y)
	{
		this.x /= x;
		this.y /= y;
		return this;
	}
	
	public PguPoint add(PguPoint p)
	{
		this.x += p.x;
		this.y += p.y;
		return this;
	}

	public PguPoint sub(PguPoint p)
	{
		this.x -= p.x;
		this.y -= p.y;
		return this;
	}

	public PguPoint mul(PguPoint p)
	{
		this.x *= p.x;
		this.y *= p.y;
		return this;
	}
	
	public PguPoint div(PguPoint p)
	{
		this.x /= p.x;
		this.y /= p.y;
		return this;
	}
	
	public static void dispose(PguPoint p)
	{
		pool.push(p);
	}
}
