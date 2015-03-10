package com.ngsarmy.pgu.core;

import java.io.IOException;

import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.util.ResourceLoader;

/* PguMusic class:
 * Where PguSound is used for
 * playing sounds, PguMusic 
 * does the same but for long
 * pieces of music (which could
 * span minutes in length). Please
 * use this when you need to play 
 * any long audio file.
 * 
 * (DO NOT CALL CONSTRUCTOR YOURSELF)
 * 
 * (supports .wav, .aif)
 */
public class PguMusic
{
	private static PguHashMap<String, PguMusic> _cache = new PguHashMap<String, PguMusic>();
	private Audio _aud;
	
	public static PguMusic fromFile(String path)
	{
		if(_cache.containsKey(path)) return _cache.get(path);
		
		int dotPos = path.lastIndexOf('.');
		PguMusic aud;
		try
		{
			Audio slickAud = AudioLoader.getStreamingAudio(path.substring(dotPos + 1).toUpperCase(), ResourceLoader.getResource("res/" + path));
			aud = new PguMusic(slickAud);
			_cache.put(path, aud);
			return aud;
		}
		catch(IOException e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	public PguMusic(Audio aud)
	{
		_aud = aud;
	}
	
	public void play(float pitch, float gain, boolean loop)
	{
		_aud.playAsMusic(pitch, gain * PguG.volume, loop);
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
