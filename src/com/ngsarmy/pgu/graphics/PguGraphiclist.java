package com.ngsarmy.pgu.graphics;

import java.util.ArrayList;

import com.ngsarmy.pgu.core.PguGraphic;
import com.ngsarmy.pgu.core.PguPoint;

/* PguGraphiclist class:
 * a graphic which can contain
 * many _graphics within it. Very
 * useful for many different things
 * (i.e object with health bar on
 * top, object made up of many 
 * images, etc)
 */
public class PguGraphiclist extends PguGraphic
{
	private ArrayList<PguGraphic> _graphics;
	private PguPoint _camera;
	
	public PguGraphiclist()
	{
		super();
		_graphics = new ArrayList<PguGraphic>();
		_camera = new PguPoint();
	}
	
	public PguGraphiclist(PguGraphic[] graphicArray)
	{
		super();
		_graphics = new ArrayList<PguGraphic>();
		for(int i = 0; i < graphicArray.length; i++)
			_graphics.add(graphicArray[i]);
		_camera = new PguPoint();
	}
	
	@Override
	public void update()
	{
		for(int i = 0; i < _graphics.size(); i++)
		{
			PguGraphic g = _graphics.get(i);
			if(g.active) g.update();
		}
	}
	
	@Override
	public void render(PguPoint point, PguPoint camera)
	{
		point.x += x;
		point.y += y;
		camera.x *= scrollX;
		camera.y *= scrollY;
		
		for(int i = 0; i < _graphics.size(); i++)
		{
			PguGraphic g = _graphics.get(i);
			
			if(g.visible)
			{
				if(g.relative)
				{
					_point.x = point.x;
					_point.y = point.y;
				}
				else _point.x = _point.y = 0;
				_camera.x = camera.x;
				_camera.y = camera.y;
				g.render(_point, _camera);
			}
		}
	}
	
	@Override
	public void destroy()
	{
		for(int i = 0; i < _graphics.size(); i++)
			_graphics.get(i).destroy();
	}
	
	public PguGraphic add(PguGraphic g)
	{
		if(!active) active = g.active;
		_graphics.add(g);
		return g;
	}
	
	public PguGraphic remove(PguGraphic g)
	{
		if(_graphics.indexOf(g) < 0) return g;
		_graphics.remove(g);
		updateCheck();
		return g;
	}
	
	public void removeAt(int index)
	{
		if(_graphics.size() == 0) return;
		index %= _graphics.size();
		remove(_graphics.get(index));
	}
	
	public void removeAll()
	{
		_graphics.clear();
		active = false;
	}
	
	private void updateCheck()
	{
		active = false;
		for(int i = 0; i < _graphics.size(); i++)
		{
			PguGraphic g = _graphics.get(i);
			
			if(g.active)
			{
				active = true;
				return;
			}
		}
	}
}
