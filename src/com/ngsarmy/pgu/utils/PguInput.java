package com.ngsarmy.pgu.utils;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import com.ngsarmy.pgu.core.PguG;

/* PguInput class:
 * this class contains
 * various static methods
 * for checking whether a
 * key is down, or a mouse
 * button was just pressed,
 * etc
 * 
 * NOTE: Use Keyboard.KEY...
 * as the argument to the 
 * key checking methods
 */
public class PguInput 
{
	private static boolean[] _prevKeys = new boolean[Keyboard.getKeyCount()];
	private static boolean[] _prevMouseButtons = new boolean[Mouse.getButtonCount()];
	
	private static boolean[] _keys = new boolean[Keyboard.getKeyCount()];
	private static boolean[] _mouseButtons = new boolean[Mouse.getButtonCount()];
	
	// WARNING: DO NOT CALL, CALLED INTERNALLY
	public static void update()
	{
		for(int i = 0; i < _prevKeys.length; i++)
		{
			_prevKeys[i] = _keys[i];
			_keys[i] = Keyboard.isKeyDown(i);
		}
		
		for(int i = 0; i < _prevMouseButtons.length; i++)
		{	
			_prevMouseButtons[i] = _mouseButtons[i];
			_mouseButtons[i] = Mouse.isButtonDown(i);
		}
	}
	
	// USAGE:
	// returns whether a key was just pressed given the keycode 
	// key -> A key code (ex. Keyboard.KEY_A)
	public static boolean justPressed(int key)
	{
		return (_keys[key] && !_prevKeys[key]);
	}
	
	// USAGE:
	// returns whether a key was just released given the keycode
	// key -> A key code (ex. Keyboard.KEY_A)
	public static boolean justReleased(int key)
	{
		return (!_keys[key] && _prevKeys[key]);
	}
	
	// USAGE:
	// returns whether a key is down given the keycode
	// key -> A key code (ex. Keyboard.KEY_A)
	public static boolean isKeyDown(int key)
	{
		return _keys[key];
	}
	
	// USAGE:
	// returns whether a key is up given the keycode
	// key -> A key code (ex. Keyboard.KEY_A)
	public static boolean isKeyUp(int key)
	{
		return !_keys[key];
	}
	
	// USAGE:
	// returns whether a mouse button is down given the button code
	// button -> A button code (ex. PguMouseButtons.LEFT)
	public static boolean isMouseButtonDown(int button)
	{
		return _mouseButtons[button];
	}
	
	// USAGE:
	// returns whether a mouse button is up given the button code
	// button -> A button code (ex. PguMouseButtons.RIGHT)
	public static boolean isMouseButtonUp(int button)
	{
		return !_mouseButtons[button];
	}
	
	// USAGE:
	// returns whether a mouse button was just pressed
	// button -> A button code (ex. PguMouseButtons.LEFT)
	public static boolean justPressedMouseButton(int button)
	{
		return (!_prevMouseButtons[button] && _mouseButtons[button]);
	}
	
	// USAGE:
	// returns whether a mouse button was just released
	// button -> A button code (ex. PguMouseButtons.LEFT)
	public static boolean justReleasedMouseButton(int button)
	{
		return (_prevMouseButtons[button] && !_mouseButtons[button]);
	}
	
	// USAGE:
	// returns the x position of the mouse relative to the games width and height (unscaled)
	public static float getMouseX()
	{
		return (Mouse.getX() / PguG.scale);
	}

	// USAGE:
	// returns the y position of the mouse relative to the games width and height (unscaled)
	public static float getMouseY()
	{
		return (Mouse.getY() / PguG.scale);
	}
	
	// USAGE:
	// returns the delta x amount of the mouse (the amount the mouse moved in the x axis since the last frame) (unscaled)
	public static float getMouseDx()
	{
		return (Mouse.getDX() / PguG.scale);
	}
	
	// USAGE:
	// returns the delta y amount of the mouse (the amount the mouse moved in the y axis since the last frame) (unscaled)
	public static float getMouseDy()
	{
		return (Mouse.getDY() / PguG.scale);
	}
	
	// USAGE:
	// returns the x position of the mouse relative to the games width and height (scaled)
	public static int getMouseXScaled()
	{
		return Mouse.getX();
	}
	
	// USAGE:
	// returns the y position of the mouse relative to the games width and height (scaled)
	public static int getMouseYScaled()
	{
		return Mouse.getY();
	} 
}
