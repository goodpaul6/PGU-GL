package com.ngsarmy.pgu.graphics;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;

import com.ngsarmy.pgu.core.PguColor;
import com.ngsarmy.pgu.core.PguFont;
import com.ngsarmy.pgu.core.PguGraphic;
import com.ngsarmy.pgu.core.PguPoint;
import com.ngsarmy.pgu.core.PguRectangle;
import com.ngsarmy.pgu.core.PguUtils;

/* PguText class:
 * This graphic allows you to 
 * render _text (true type
 * fonts, whose files end 
 * in .ttf) and color it,
 * change it in realtime, etc
 */
public class PguText extends PguGraphic 
{
	// static internal color object
	private static Color _col = new Color(0, 0, 0);
	
	// internal font
	private PguFont _font;
	
	// current _text
	private String _text;
	
	// color tint
	public PguColor color;
	
	// x origin of the image, determines
	// the transformation point
	// default is top-left corner
	public float originX;
	
	// y origin of the image, determines
	// the transformation point
	// default is top-left corner
	public float originY;
	
	// scale factor by which the _text is scaled
	public float scale;
	
	// angle of rotation
	public float angle;
	
	// _text indices
	private int[] _indices;
	
	public PguText(String source, int cw, int ch, String charset, PguRectangle bounds)
	{
		super();
		_font = PguFont.fromFile(source, cw, ch, charset, bounds);
		_indices = new int[0];
		_text = "";
		
		color = new PguColor(255, 255, 255, 255);
		originX = 0;
		originY = 0;
		angle = 0;
		scale = 1;
	}
	
	public void setText(String text)
	{
		if(!text.equals(_text))
		{
			if(_indices.length < text.length())
				_indices = new int[text.length()];
		
			for(int i = 0; i < _indices.length; i++)
				_indices[i] = _font.getCharFrame(text.charAt(i));
			
			_text = text;
		}
	}
	
	public String getText()
	{
		return _text;
	}
	
	@Override
	public void render(PguPoint point, PguPoint camera)
	{
		_point.x = (relative ? point.x : 0) + x - originX - camera.x * scrollX;
		_point.y = (relative ? point.y : 0) + y - originY - camera.y * scrollY;
		
		_col.r = color.fR();
		_col.g = color.fG();
		_col.b = color.fB();
		_col.g = color.fG();
		
		if(_text.length() > 0)
		{
			_font.getTexture().bind();
			GL11.glLoadIdentity();
			
//			GL11.glTranslatef(_point.x+originX, _point.y+originY, 0);
//			GL11.glRotatef(angle, 0, 0, 1);
//			GL11.glTranslatef(-originX, -originY, 0);
//			
			GL11.glScalef(scale, scale, 0);
			
			float tw = _font.getBounds().w;
			float th = _font.getBounds().h;
			
			for(int i = 0; i < _indices.length; i++)
			{
				float ux = (_indices[i] % (int)(tw / _font.getCharWidth())) * _font.getCharWidth() + _font.getBounds().x;
				float uy = (_indices[i] / (int)(tw / _font.getCharWidth())) * _font.getCharHeight() + _font.getBounds().y;
				
				float u1 = ux / tw;
				float u2 = (ux + _font.getCharWidth()) / tw;
				float v1 = uy / th;
				float v2 = (uy + _font.getCharHeight()) / th;
				
				GL11.glColor4f(color.fR(), color.fG(), color.fB(), color.fA());
				
				GL11.glBegin(GL11.GL_QUADS);
				GL11.glTexCoord2f(u1, v1);
				GL11.glVertex2f(0, 0);
				GL11.glTexCoord2f(u2, v1);
				GL11.glVertex2f(_font.getCharWidth(), 0);
				GL11.glTexCoord2f(u2, v2);
				GL11.glVertex2f(_font.getCharWidth(), _font.getCharHeight());
				GL11.glTexCoord2f(u1, v2);
				GL11.glVertex2f(0, _font.getCharHeight());
				GL11.glEnd();
			
				/*GL11.glBegin(GL11.GL_QUADS);
				GL11.glTexCoord2f(0, 0);
				GL11.glVertex2f(0, 0);
				GL11.glTexCoord2f(1, 0);
				GL11.glVertex2f(128 * scale, 0);
				GL11.glTexCoord2f(1, 1);
				GL11.glVertex2f(128 * scale, 128 * scale);
				GL11.glTexCoord2f(0, 1);
				GL11.glVertex2f(0, 128 * scale);
				GL11.glEnd();*/
				
				GL11.glTranslatef(_font.getCharWidth(), 0, 0);
			}
			
			PguUtils.unbindTexture();
		}
	}
	
	// USAGE:
	// centers the _text based on its *CURRENT* width and height
	public void centerOrigin()
	{
		originX = getWidth() / 2;
		originY = getHeight() / 2;
	}
	
	// USAGE:
	// gets the width of the _text
	public int getWidth()
	{
		return 0;
	}
	
	// USAGE:
	// gets the height of the _text
	public int getHeight()
	{
		return 0;
	}
}
