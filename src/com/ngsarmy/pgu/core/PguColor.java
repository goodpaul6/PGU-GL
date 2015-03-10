package com.ngsarmy.pgu.core;

/* PguColor class:
 * this is a utility
 * class which stores
 * r, g, b, and a
 * values for rendering.
 * The values are from 0 
 * to 255, 0 being darkest
 * and 255 being lightest.
 */
public class PguColor 
{
	int r, g, b, a;
	
	public PguColor()
	{
		r = 0;
		g = 0;
		b = 0; 
		a = 0;
	}
	
	public PguColor(int r, int g, int b, int a)
	{
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}
	
	
	// USAGE:
	// set the color values
	public void set(int r, int g, int b, int a)
	{
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}
	
	// USAGE:
	// convert from PguColor to AARRGGBB hex integer
	public int toHexARGB()
	{
		return ((a << 24) & 0xFF) | ((r << 16) & 0xFF) | ((g << 8) & 0xFF) | (b & 0xFF);
	}
	
	// USAGE:
	// get float r value (0 - 1)
	public float fR()
	{
		return (r / 255.0f);
	}
	
	// USAGE:
	// get float g value (0 - 1)
	public float fG()
	{
		return (g / 255.0f);
	}
	
	// USAGE:
	// get float g value (0 - 1)
	public float fB()
	{
		return (b / 255.0f);
	}
	
	// USAGE:
	// get float a value (0 - 1)
	public float fA()
	{
		return (a / 255.0f);
	}
}
