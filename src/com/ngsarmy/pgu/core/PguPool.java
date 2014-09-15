package com.ngsarmy.pgu.core;

import java.util.Stack;

/* PguPool class:
 * an internal helper
 * class to help set
 * up object pools.
 * The idea is to
 * prevent the needless 
 * allocation of objects
 * by storing objects 
 * that are no longer 
 * in use and then revitalizing
 * them as opposed to 
 * allocating new objects.
 */
public class PguPool <T> extends Stack<T>
{
	/**
	 * JAVA BS
	 */
	private static final long serialVersionUID = 1L;
}
