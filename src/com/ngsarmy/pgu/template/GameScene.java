package com.ngsarmy.pgu.template;

import com.ngsarmy.pgu.core.PguEntity;
import com.ngsarmy.pgu.core.PguG;
import com.ngsarmy.pgu.core.PguScene;

public class GameScene extends PguScene 
{	
	PguEntity e;
	
	@Override
	public void begin()
	{
		e.x = 100;
		e.y = 100;
		PguG.zoom = 2;
	}
	
	@Override
	public boolean update()
	{
		super.update();
		return true;
	}
}
