package com.ngsarmy.pgu.core;

/* PguGraphic class:
 * base class for all
 * graphical types within
 * the engine. Do not 
 * use directly.
 */
public abstract class PguGraphic 
{
	// internal point for transformations
	protected PguPoint _point;
	
	// x position of graphic (relative if relative is enabled)
	public float x;
	
	// y position of graphic (relative if relative is enabled)
	public float y;
	
	// whether this graphic is visible
	public boolean visible;
	
	// how much this graphic is affected by the camera in the x axis
	public float scrollX;

	// how much this graphic is affected by the camera in the y axis
	public float scrollY;
	
	// whether this graphic is relative to it's parent entity
	public boolean relative;
	
	// whether this graphic should update
	public boolean active;
	
	public PguGraphic()
	{
		active = true;
		visible = true;
		x = y = 0;
		scrollX = scrollY = 1;
		relative = true;
		_point = new PguPoint();
	}
	
	// INTERNAL:
	// updates the graphic
	public void update()
	{
	}
	
	// INTERNAL:
	// removes the graphic from the scene
	public void destroy()
	{
	}
	
	// INTERNAL:
	// renders a graphic to the position point (PguPoint) taking into account the 
	// camera (PguPoint) offset
	public void render(PguPoint point, PguPoint camera)
	{
	}
	
	// USAGE:
	// stop updating the graphic
	public void pause()
	{
		active = false;
	}
	
	// USAGE:
	// resume updating the graphic
	public void resume()
	{
		active = true;
	}
}
