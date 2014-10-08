package com.ngsarmy.pgu.template.entities;

import org.lwjgl.input.Keyboard;

import com.ngsarmy.pgu.core.PguEntity;
import com.ngsarmy.pgu.core.PguG;
import com.ngsarmy.pgu.graphics.PguImage;
import com.ngsarmy.pgu.utils.PguInput;

public class Player extends PguEntity
{	
	private static final String[] COLLIDES_WITH = {"solid"};
	
	public Player(float x, float y)
	{
		super(x, y, new PguImage("PGULogo.png"));
	}
	
	@Override
	public void update()
	{
		float xMove = 0;
		float yMove = 0;
		
		if(PguInput.isKeyDown(Keyboard.KEY_A)) xMove -= 100;
		if(PguInput.isKeyDown(Keyboard.KEY_D)) xMove += 100;
		
		if(PguInput.isKeyDown(Keyboard.KEY_W)) yMove -= 100;
		if(PguInput.isKeyDown(Keyboard.KEY_S)) yMove += 100;
		
		xMove *= PguG.elapsed;
		yMove *= PguG.elapsed;
		
		moveBy(COLLIDES_WITH, xMove, yMove, false);
	}
}
