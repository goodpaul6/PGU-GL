package com.ngsarmy.pgu.core;

/* PguColor class:
 * this is a utility
 * class which stores
 * r, g, b, and a
 * values for rendering.
 * The values are from 0 
 * to 1, 0 being darkest
 * and 1 being lightest.
 */
public class PguColor 
{
	float r, g, b, a;
	
	public PguColor()
	{
		r = 0;
		g = 0;
		b = 0; 
		a = 0;
	}
	
	public PguColor(float r, float g, float b, float a)
	{
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}
	
	public PguColor(int r, int g, int b, int a)
	{
		this.r = (float)r / 255;
		this.g = (float)g / 255;
		this.b = (float)b / 255;
		this.a = (float)a / 255;
	}
}
