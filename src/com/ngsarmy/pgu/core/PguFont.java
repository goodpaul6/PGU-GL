package com.ngsarmy.pgu.core;

/* PguFont class:
 * Stores bitmap fonts.
 * Used by the PguText class
 * to facilitate rendering of text.
 * This class stores character set
 * data as well as textures which
 * store the font.
 */
public class PguFont 
{
	private static PguHashMap<String, PguFont> _cache = new PguHashMap<String, PguFont>();
	
	public static PguFont fromFile(String path, int charWidth, int charHeight, String charset, PguRectangle bounds)
	{
		if(_cache.containsKey(path)) return _cache.get(path);
		
		PguFont font = new PguFont(path, charWidth, charHeight, charset, bounds);
		_cache.put(path, font);
		return font;
	}
	
	// charset data
	private PguHashMap<Character, Integer> _charset;
	
	// character size
	private int _charWidth;
	private int _charHeight;
	
	// bounds from which to extract the text
	private PguRectangle _rect;
	
	// texture
	private PguTexture _texture;

	public PguFont(String source, int charWidth, int charHeight, String charset, PguRectangle rect)
	{
		_texture = PguTexture.fromFile(source);
		_charWidth = charWidth;
		_charHeight = charHeight;
		_charset = new PguHashMap<Character, Integer>();
		_rect = rect;
		
		for(int i = 0; i < charset.length(); i++)
			_charset.put(charset.charAt(i), i);
	}
	
	public PguTexture getTexture()
	{
		return _texture;
	}
	
	public int getCharWidth()
	{
		return _charWidth;
	}
	
	public int getCharHeight()
	{
		return _charHeight;
	}
	
	public PguRectangle getBounds()
	{
		return _rect;
	}
	
	public int getCharFrame(char c)
	{
		if(_charset.containsKey(c))
			return _charset.get(c);
		return 0;
	}
}
