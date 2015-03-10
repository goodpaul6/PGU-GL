package com.ngsarmy.pgu.template;

import com.ngsarmy.pgu.core.PguEntity;
import com.ngsarmy.pgu.core.PguMusic;
import com.ngsarmy.pgu.core.PguScene;
import com.ngsarmy.pgu.core.PguTexture;
import com.ngsarmy.pgu.graphics.PguTilemap;

public class MainScene extends PguScene
{	
	PguMusic song;
	
	@Override
	public void begin()
	{
		song = PguMusic.fromFile("alttp_overworld.ogg");
		song.play();
		
		add(new Player(100, 90), 1);
		
		int[] data = new int[]{
			1,1,1,1,1,1,1,1,1,1,
			1,-1,-1,-1,-1,-1,-1,-1,-1,1,
			1,-1,-1,-1,-1,-1,-1,-1,-1,1,
			1,-1,-1,-1,-1,-1,-1,-1,-1,1,
			1,-1,1,1,1,-1,-1,-1,-1,1,
			1,-1,-1,-1,-1,-1,-1,-1,-1,1,
			1,-1,-1,-1,-1,-1,-1,-1,-1,1,
			1,-1,-1,-1,-1,-1,-1,-1,-1,1,
			1,-1,-1,-1,-1,-1,-1,-1,-1,1,
			1,1,1,1,1,1,1,1,1,1,
		};
		
		addCollision(data, 10, 10);
		
		PguTilemap map = new PguTilemap();
		map.setTileset(PguTexture.fromFile("tileset.png"), 32, 32);
		map.setData(data, 10, 10);
		
		addGraphic(map);
	}
	
	private void addCollision(int[] data, int width, int height)
	{
		for(int x = 0; x < width; ++x)
		{
			for(int y = 0; y < height; ++y)
			{
				if(data[x + y * width] >= 0)
				{
					PguEntity rect = new PguEntity(x * 32, y * 32, null);
					rect.type = "solid";
					rect.setHitbox(0, 0, 32, 32);
					add(rect, 1);
				}
			}
		}
	}
}
