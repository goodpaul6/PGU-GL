package com.ngsarmy.pgu.graphics;

import org.lwjgl.opengl.GL11;

import com.ngsarmy.pgu.core.PguColor;
import com.ngsarmy.pgu.core.PguGraphic;
import com.ngsarmy.pgu.core.PguPoint;
import com.ngsarmy.pgu.core.PguRectangle;
import com.ngsarmy.pgu.core.PguTexture;
import com.ngsarmy.pgu.core.PguUtils;

/* PguImage class:
 * a performance optimized
 * non-animated image that
 * can be drawn onto the screen
 * with transformations.
 */
public class PguImage extends PguGraphic
{
	// rotation of the image, in degrees
	public float angle;
	
	// scale of the image (x and y)
	public float scale;
	
	// x scale of the image
	public float scaleX;
	
	// y scale of the image
	public float scaleY;
	
	// x origin of the image, determines
	// the transformation point
	// default is top-left corner
	public float originX;
	
	// y origin of the image, determines
	// the transformation point
	// default is top-left corner
	public float originY;

	// whether this image is flipped in the x axis or not
	public boolean flipX;
	
	// whether this image is flipped in the y axis or not
	public boolean flipY;
	
	// the pgu color of this image
	public PguColor color;
	
	// internal texture
	private PguTexture _texture;
	
	// internal rectangle
	protected PguRectangle _rectangle;
	
	public PguImage(String source, PguRectangle clip)
	{
		init();
		_texture = PguTexture.fromFile(source);
		_rectangle = clip;
	}
	
	public PguImage(String source)
	{
		init();
		_texture = PguTexture.fromFile(source);
		_rectangle = new PguRectangle(0, 0, _texture.getWidth(), _texture.getHeight());
	}
	
	private void init()
	{
		scrollX = 1;
		scrollY = 1;
		angle = 0;
		scale = scaleX = scaleY = 1;
		originX = originY = 0;
		flipX = flipY = false;
		color = new PguColor(255, 255, 255, 255);
	}
	
	@Override
	public void render(PguPoint point, PguPoint camera)
	{
		_point.x = (relative ? point.x : 0) + x - originX - camera.x * scrollX;
		_point.y = (relative ? point.y : 0) + y - originY - camera.y * scrollY;
		
		if(_texture != null)
		{
			GL11.glLoadIdentity();
		
			_texture.bind();
			
			GL11.glTranslatef(_point.x+originX, _point.y+originY, 0);
			GL11.glRotatef(angle, 0, 0, 1);
			GL11.glTranslatef(-originX, -originY, 0);
			
			GL11.glScalef(scaleX * scale, scaleY * scale, 0);
			
			GL11.glColor4f(color.fR(), color.fG(), color.fB(), color.fA());
			
			GL11.glBegin(GL11.GL_QUADS);
			GL11.glTexCoord2f(_rectangle.x / _texture.getWidth(), _rectangle.y / _texture.getHeight());
			GL11.glVertex2f(0, 0);
			GL11.glTexCoord2f((_rectangle.x + _rectangle.w) / _texture.getWidth(), _rectangle.y / _texture.getHeight());
			GL11.glVertex2f(_rectangle.w, 0);
			GL11.glTexCoord2f((_rectangle.x + _rectangle.w) / _texture.getWidth(), (_rectangle.y + _rectangle.h) / _texture.getHeight());
			GL11.glVertex2f(_rectangle.w, _rectangle.h);
			GL11.glTexCoord2f(_rectangle.x / _texture.getWidth(), (_rectangle.y + _rectangle.h) / _texture.getHeight());
			GL11.glVertex2f(0, _rectangle.h);
			GL11.glEnd();
			
			PguUtils.unbindTexture();
		}
	}
	
	// USAGE:
	// returns the width of the image (or clipped area)
	public int getWidth()
	{
		return (int)_rectangle.w;
	}
	
	// USAGE:
	// returns the height of the image (or clipped area)
	public int getHeight()
	{
		return (int)_rectangle.h;
	}
	
	// USAGE:
	// centers the image to it's origin
	public void centerOrigin()
	{
		originX = _rectangle.w / 2;
		originY = _rectangle.h / 2;
	}
}
