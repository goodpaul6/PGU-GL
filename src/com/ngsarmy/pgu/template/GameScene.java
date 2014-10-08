package com.ngsarmy.pgu.template;

import com.ngsarmy.pgu.core.PguEntity;
import com.ngsarmy.pgu.core.PguG;
import com.ngsarmy.pgu.core.PguScene;
import com.ngsarmy.pgu.graphics.PguText;

public class GameScene extends PguScene 
{	
	PguEntity e;
	PguText t;
	
	@Override
	public void begin()
	{
		t = PguG.createDefaultText();
		t.scale = 3;
		e = addGraphic(t);
		t.setText("hello");
		e.x = 100;
		e.y = 100;
		PguG.zoom = 2;
	}
	
	@Override
	public boolean update()
	{
		super.update();
		t.setText("hello?");
		return true;
	}
}
