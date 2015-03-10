package com.ngsarmy.pgu.graphics;

import java.util.HashMap;

import com.ngsarmy.pgu.core.PguG;
import com.ngsarmy.pgu.core.PguRectangle;

/* PguSpritemap class:
 * a graphic which provides
 * animation capabilities. 
 * Animation frames, by the way, 
 * are represented by values 0-inf
 * where 0 is the top left, and 1
 * is the top left + frame width in the
 * x axis, and so on. 
 * 
 * NOTE: This does not support multi-size
 * animation frames for the sake of simplicity.
 */
public class PguSpritemap extends PguImage
{	
	// is the animation complete
	public boolean complete;
	
	// the factor by which the rates of all animations is multiplied
	// this allows you to speed up or slow down all animations in this
	// spritemap by a certain amount
	public float rate;
	
	// whether the animations play in reverse
	public boolean reverse;
	
	// internal anim data 
	private int _width;
	private int _height;
	private int _columns;
	private int _rows;
	private int _frameCount;
	private HashMap<String, PguAnimation> _anims;
	private PguAnimation _anim;
	private int _index;
	private int _frame;
	private float _timer;
	
	public PguSpritemap(String source, int frameWidth, int frameHeight, PguRectangle src)
	{
		super(source);
		complete = true;
		rate = 1;
		
		_anims = new HashMap<String, PguAnimation>();
		_timer = _frame = 0;
		
		reverse = false;
		
		_rectangle = new PguRectangle(0, 0, frameWidth, frameHeight);
		
		_width = super.getWidth();
		_height = super.getHeight();
		
		if(frameWidth == 0) _rectangle.w = _width;
		if(frameHeight == 0) _rectangle.h = _height;

		_columns = (int)Math.ceil(_texture.getWidth() / _width);
		_rows = (int)Math.ceil(_texture.getHeight() / _height);
		
		_frameCount = _columns * _rows;
	}
	
	public PguSpritemap(String source, int frameWidth, int frameHeight)
	{
		super(source);
		complete = true;
		rate = 1;
		
		_anims = new HashMap<String, PguAnimation>();
		_timer = _frame = 0;
		
		reverse = false;
		
		_rectangle = new PguRectangle(0, 0, frameWidth, frameHeight);
		
		_width = super.getWidth();
		_height = super.getHeight();
		
		if(frameWidth == 0) _rectangle.w = _width;
		if(frameHeight == 0) _rectangle.h = _height;
	
		_columns = (int)Math.ceil(_texture.getWidth() / _width);
		_rows = (int)Math.ceil(_texture.getHeight() / _height);
		
		_frameCount = _columns * _rows;
	}
	
	@Override
	public void update()
	{
		if(_anim != null && !complete)
		{
			_timer += _anim.frameRate * PguG.elapsed * rate;
			if(_timer >= 1)
			{
				while(_timer >= 1)
				{
					_timer--;
					_index += reverse ? -1 : 1;
					
					if((reverse && _index == -1) || (!reverse && _index == _anim.frames.length))
					{
						if(_anim.loop)
							_index = reverse ? _anim.frames.length - 1 : 0;
						else
						{
							_index = reverse ? 0 : _anim.frames.length - 1;
							complete = true;
							break;
						}
					}
				}
				if(_anim != null) _frame = (int)_anim.frames[_index];
				updateRect();
			}
		}
	}
	
	private void updateRect()
	{
		_rectangle.x = (_frame % _columns) * _width;
		_rectangle.y = (_frame / _columns) * _height;
		_rectangle.w = _width;
		_rectangle.h = _height;
	}
	
	// USAGE:
	// add an animation to the spritemap given 
	// name -> name of the animation
	// frames -> an array containing the animation frames
	// frameRate -> the rate at which this animation is played
	// loop -> whether to loop the animation
	// returns the added animation object
	// fails if you try to add 2 animations with the same name
	public PguAnimation add(String name, int[] frames, float frameRate, boolean loop)
	{
		if(_anims.containsKey(name))
		{
			System.err.println("Cannot have multiple animations with the same name '" + name + "' (Spritemap)");
			System.exit(1);
		}
		
		for(int i = 0; i < frames.length; ++i)
		{
			frames[i] %= _frameCount;
			if(frames[i] < 0) frames[i] += _frameCount;
		}
		
		PguAnimation anim = new PguAnimation(name, frames, frameRate, loop, this);
		_anims.put(name, anim);
		return anim;
	}
	
	// USAGE:
	// play a previously added animation (added using add()) given
	// name -> name of the animation to play
	// reset -> whether to reset to the first frame of the animation if it's already playing
	// reverse -> whether it should be played in reverse
	// returns the animation object representing the played animation
	// fails and returns null if the animation doesn't exist (also stops the current
	// animation in this case)
	public PguAnimation play(String name, boolean reset, boolean reverse)
	{
		if(!reset && _anim != null && _anim.name.equals(name))
			return _anim;
		
		if(!_anims.containsKey(name))
		{	
			stop(true);
			return null;
		}
		
		_anim = _anims.get(name);
		this.reverse = reverse;
		restart();
		
		return _anim;
	}
	
	// USAGE:
	// plays a set of frames as if you supplied an animation :)
	// frames -> the set of frames to play
	// frameRate -> the frame rate at which to play the frames
	// loop -> whether to loop these animations
	// reset -> whether to reset the current animation 
	// reverse -> whether to play the animation in reverse
	public PguAnimation playFrames(int[] frames, float frameRate, boolean loop, boolean reset, boolean reverse)
	{
		if(frames == null || frames.length == 0)
		{
			stop(reset);		
			return null;
		}

		if(reset == false && _anim != null && _anim.frames == frames)
			return _anim;

		return playAnimation(new PguAnimation(null, frames, frameRate, loop, this), reset, reverse);
	}
	
	// USAGE:
	// plays or restarts the supplied animation object
	// animation -> the animation object to play
	// reset -> when supplied animation is currently playing, should it be force-restarted
	// reverse -> if the animation should be played backwards
	// returns the animation object representing the played animation
	public PguAnimation playAnimation(PguAnimation anim, boolean reset, boolean reverse)
	{
		if(anim == null)
		{
			System.err.println("No animation supplied (Spritemap: playAnimation)");
			System.exit(1);
		}
		
		if(reset == false && _anim == anim)
			return anim;
		
		_anim = anim;
		this.reverse = reverse;
		restart();
		
		return _anim;
	}
	
	// USAGE:
	// resets the current animation to play from the beginning
	public void restart()
	{
		_timer = _index = reverse ? _anim.frames.length - 1 : 0;
		_frame = _anim.frames[_index];
		complete = false;
		updateRect();
	}
	
	// USAGE:
	// immediately stops the current animation
	// reset -> if true, resets the animation to the first frame
	public void stop(boolean reset)
	{
		_anim = null;
		
		if(reset)
			_frame = _index = reverse ? _anim.frames.length - 1 : 0;
		
		complete = true;
		updateRect();
	}
	
	// USAGE:
	// returns the current frame of the current animation being played
	public int getFrame()
	{
		return _frame;
	}
	
	// USAGE:
	// returns the width of each frame
	public int getWidth()
	{
		return _width;
	}
	
	// USAGE:
	// returns the height of each frame
	public int getHeight()
	{
		return _height;
	}
}
