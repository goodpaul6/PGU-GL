package com.ngsarmy.pgu.core;

import java.io.IOException;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

/* PguTexture class:
 * this class is the
 * "image" class which
 * stores image data
 * for rendering onto the
 * screen. It also provides
 * loading capabilities
 * as well as caching.
 * Use the fromFile method
 * to load a texture from
 * a file (supports .png,
 * .jpg, .jpeg, .gif, and 
 * .tga)
 * 
 * WARNING:
 * do not construct the object 
 * yourself.
 */
public class PguTexture 
{	
	// nearest neighbor filter: pixel-y
	public static final int NEAREST = GL11.GL_NEAREST;
	// normal linear filter: slower but cleaner, a little blurry
	public static final int BILINEAR = GL11.GL_LINEAR;
	
	private static PguHashMap<String, PguTexture> _cache = new PguHashMap<String, PguTexture>();
	
	public static PguTexture fromFile(String path)
	{
		if(_cache.containsKey(path)) return _cache.get(path);
		
		int dotPos = path.lastIndexOf('.');
		PguTexture texture;
		try
		{
			Texture slickTex = TextureLoader.getTexture(path.substring(dotPos + 1).toUpperCase(), ResourceLoader.getResourceAsStream("res/" + path));
			slickTex.setTextureFilter(PguG.DEFAULT_TEXTURE_BLENDING_MODE);
			texture = new PguTexture(slickTex.getTextureID(), slickTex.getImageWidth(), slickTex.getImageHeight());
			_cache.put(path, texture);
			return texture;
		}
		catch(IOException e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	private int id;
	private int width;
	private int height;
	
	public PguTexture(int id, int width, int height)
	{
		this.id = id;
		this.width = width;
		this.height = height;
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public int getHeight()
	{
		return height;
	}
	
	public void bind()
	{
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
	}
}
