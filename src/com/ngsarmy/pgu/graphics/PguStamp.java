package com.ngsarmy.pgu.graphics;

import org.lwjgl.opengl.GL11;

import com.ngsarmy.pgu.core.PguGraphic;
import com.ngsarmy.pgu.core.PguPoint;
import com.ngsarmy.pgu.core.PguRectangle;
import com.ngsarmy.pgu.core.PguTexture;
import com.ngsarmy.pgu.core.PguUtils;

/* PguStamp class:
 * a graphic that provides simple 
 * non-transformed image rendering.
 * No flipping, rotating, or scaling.
 * No origin transformation.
 * Faster than the regular Image.
 * 
 * NOTE: It is highly recommended 
 * that you use Image if you EVER
 * plan on doing anything aside from
 * rendering an image (or region)
 * since the speedup is negligible
 * in most cases
 */
public class PguStamp extends PguGraphic
{	
	// texture object which stores the image
	private PguTexture _texture;
	// rectangle which represents the region of the image being shown
	private PguRectangle _rectangle;
	
	public PguStamp(String source, PguRectangle rectangle)
	{
		super();
		_texture = PguTexture.fromFile(source);
		_rectangle = rectangle;
	}
	
	public PguStamp(String source)
	{
		super();
		_texture = PguTexture.fromFile(source);
		_rectangle = new PguRectangle(0, 0, _texture.getWidth(), _texture.getHeight());
	}
	
	@Override
	public void render(PguPoint point, PguPoint camera)
	{
		_point.x = point.x + x - camera.x * scrollX;
		_point.y = point.y + y - camera.y * scrollY;
		
		_texture.bind();
		
		GL11.glLoadIdentity();
		
		GL11.glColor4f(1, 1, 1, 1);
		
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(_rectangle.x / _texture.getWidth(), _rectangle.y / _texture.getHeight());
		GL11.glVertex2f(0, 0);
		GL11.glTexCoord2f((_rectangle.x + _rectangle.w) / _texture.getWidth(), _rectangle.y / _texture.getHeight());
		GL11.glVertex2f(_rectangle.w, 0);
		GL11.glTexCoord2f((_rectangle.x + _rectangle.w) / _texture.getWidth(), (_rectangle.y + _rectangle.h) / _texture.getHeight());
		GL11.glVertex2f(_rectangle.w, _rectangle.h);
		GL11.glTexCoord2f(0, (_rectangle.y + _rectangle.h) / _texture.getHeight());
		GL11.glVertex2f(0, _rectangle.h);
		GL11.glEnd();
		
		PguUtils.unbindTexture();
	}
	
	// USAGE:
	// returns the width of the image (region?) being rendered by this stamp
	public int getWidth()
	{
		return _texture.getWidth();
	}
	
	// USAGE:
	// returns the height of the image (region?) being rendered by this stamp
	public int getHeight()
	{
		return _texture.getHeight();
	}
}
