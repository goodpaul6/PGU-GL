package com.ngsarmy.pgu.core;

import java.io.IOException;

import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.util.ResourceLoader;

/* PguSound class:
 * this class stores 
 * sound. It also
 * provides caching utilities
 * so use fromFile to load it
 * 
 * (supports .wav and .aif
 * formats at the moment)
 * 
 * WARNING: Do not call
 * constructor yourself
 * 
 * NOTE: This can techinally
 * play music too, but do
 * NOT use it for music
 */
public class PguSound 
{	
	private static PguHashMap<String, PguSound> cache = new PguHashMap<String, PguSound>();
	private Audio aud;
	
	public static PguSound fromFile(String path)
	{
		int dotPos = path.lastIndexOf('.');
		PguSound aud;
		try
		{
			Audio slickAud = AudioLoader.getAudio(path.substring(dotPos + 1).toUpperCase(), ResourceLoader.getResourceAsStream("res/" + path));
			aud = new PguSound(slickAud);
			cache.put(path, aud);
			return aud;
		}
		catch(IOException e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	public PguSound(Audio aud)
	{
		this.aud = aud;
	}
	
	public void play(float pitch, float gain, boolean loop)
	{
		aud.playAsSoundEffect(pitch, gain * PguGame.volume, loop);
	}
	
	public void play()
	{
		play(1, 1, false);
	}
	
	public void play(boolean loop)
	{
		play(1, 1, loop);
	}
}