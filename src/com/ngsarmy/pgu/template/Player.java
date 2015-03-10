package com.ngsarmy.pgu.template;

import org.lwjgl.input.Keyboard;

import com.ngsarmy.pgu.core.PguEntity;
import com.ngsarmy.pgu.core.PguG;
import com.ngsarmy.pgu.graphics.PguImage;
import com.ngsarmy.pgu.graphics.PguSpritemap;
import com.ngsarmy.pgu.utils.PguInput;

public class Player extends PguEntity
{	
	private static final String[] COLLIDES_WITH = {"solid"};
	
	private PguSpritemap _sprite;
	
	public Player(float x, float y)
	{
		super(x, y, new PguSpritemap("player.png", 32, 32));
		_sprite = (PguSpritemap)graphic;
		
		_sprite.add("down", new int[]{0,1}, 8, true);
		_sprite.add("left", new int[]{2,3}, 8, true);
		_sprite.add("up", new int[]{4,5}, 8, true);
		_sprite.add("right", new int[]{6,7}, 8, true);
		
		_sprite.scale = 2;
		_sprite.x = _sprite.y = -16;
		
		_sprite.play("down", false, false);
		
		setHitbox(1, 16, 31, 16);
	}
	
	@Override
	public void update()
	{
		scene.camera.x += ((x - PguG.getWidth() / 2) - scene.camera.x) * PguG.elapsed;
		scene.camera.y += ((y - PguG.getHeight() / 2) - scene.camera.y) * PguG.elapsed;
		
		float xMove = 0;
		float yMove = 0;
		
		_sprite.pause();
		
		if(PguInput.isKeyDown(Keyboard.KEY_LEFT))
		{
			_sprite.resume();
			_sprite.play("left", false, false);
			xMove -= 200;
		}
		else if(PguInput.isKeyDown(Keyboard.KEY_RIGHT))
		{ 
			_sprite.resume();
			_sprite.play("right", false, false);
			xMove += 200;
		}
		else if(PguInput.isKeyDown(Keyboard.KEY_UP))
		{
			_sprite.resume();
			_sprite.play("up", false, false);
			yMove -= 200;
		}
		else if(PguInput.isKeyDown(Keyboard.KEY_DOWN))
		{
			_sprite.resume();
			_sprite.play("down", false, false);
			yMove += 200;
		}
		
		xMove *= PguG.elapsed;
		yMove *= PguG.elapsed;
		
		moveBy(COLLIDES_WITH, xMove, yMove, false);
	}
}
