package com.ngsarmy.pgu.utils;

import org.lwjgl.input.Mouse;

/* PguMouseButtons class:
 * a class which contains
 * the codes for the canonical
 * LEFT, MIDDLE, and RIGHT 
 * click buttons. The reason
 * as to why this is a class 
 * and not an enum is because
 * the code of the buttons
 * may vary for different
 * mice and thus must be
 * determined at runtime
 */
public class PguMouseButtons 
{
	public static int LEFT = Mouse.getButtonIndex("BUTTON0");
	public static int RIGHT = Mouse.getButtonIndex("BUTTON1");
	public static int MIDDLE = Mouse.getButtonIndex("BUTTON2");
}
