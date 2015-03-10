package com.ngsarmy.pgu.graphics;

import org.lwjgl.opengl.GL11;

import com.ngsarmy.pgu.core.PguG;
import com.ngsarmy.pgu.core.PguGraphic;
import com.ngsarmy.pgu.core.PguPoint;
import com.ngsarmy.pgu.core.PguTexture;
import com.ngsarmy.pgu.core.PguUtils;

/* PguTilemap class:
 * This graphic provides utilities
 * for rendering a tilemap. It stores
 * the tilemap data, but collision is 
 * often done with the PguGrid class.
 * This class is purely for drawing the 
 * tilemap, and it can be attached to 
 * any entity.
 */
public class PguTilemap extends PguGraphic
{	
	private int[] _data;
	private int _width;
	private int _height;

	private PguTexture _tileset;
	private int _tileWidth;
	private int _tileHeight;
	
	public PguTilemap()
	{
		_width = 0;
		_height = 0;
	}
	
	// USAGE:
	// set the image containing the tiles for the map (as well as the width and height of each tile)
	public void setTileset(PguTexture tileset, int tileWidth, int tileHeight)
	{
		_tileset = tileset;
		_tileWidth = tileWidth;
		_tileHeight = tileHeight;
	}
	
	// USAGE:
	// set the map data of the tilemap (as well as its width and height)
	// 0 is for empty, 1 onwards represents the tile which will be drawn
	public void setData(int[] data, int width, int height)
	{
		_data = data;
		_width = width;
		_height = height;
	}
	
	// USAGE:
	// get the tile at a certain column and row (x, y)
	// gives -1 if the tile is outside of map bounds
	public int getTile(int x, int y)
	{
		if(x < 0 || x >= _width) return -1;
		if(y < 0 || y >= _height) return -1;
		
		return _data[x + y * _width];
	}
	
	// USAGE:
	// set the tile at a certain column and row (x, y)
	// this value should be within the range of how many
	// tiles the map's tileset has.
	public void setTile(int x, int y, int tile)
	{
		if(x < 0 || x >= _width) return;
		if(y < 0 || y >= _height) return;
	
		_data[x + y * _width] = tile;
	}
	
	// USAGE:
	// returns the width of each tile
	public int getTileWidth()
	{
		return _tileWidth;
	}
	
	// USAGE:
	// returns the height of each tile
	public int getTileHeight()
	{
		return _tileHeight;
	}
	
	
	@Override
	public void render(PguPoint point, PguPoint camera)
	{
		_point.x = point.x + x - camera.x * scrollX;
		_point.y = point.y + y - camera.y * scrollY;
		
		if(_tileset != null)
		{
			GL11.glLoadIdentity();
			
			_tileset.bind();
			
			GL11.glTranslatef(_point.x, _point.y, 0);
			GL11.glScalef(PguG.zoom, PguG.zoom, 1);
			
			for(int y = 0; y < _height; ++y)
			{
				for(int x = 0; x < _width; ++x)
				{
					int tile = _data[x + y * _width];
					if(tile >= 0)
					{
						int tu = tile % (_tileset.getWidth() / _tileWidth);
						int tv = tile / (_tileset.getWidth() / _tileWidth);
						
						float u = (float)(tu * _tileWidth) / _tileset.getWidth();
						float v = (float)(tv * _tileHeight) / _tileset.getHeight();
						
						float u2 = (float)(tu * _tileWidth + _tileWidth) / _tileset.getWidth();
						float v2 = (float)(tv * _tileHeight + _tileHeight) / _tileset.getHeight();
						
						PguUtils.renderQuad(x * _tileWidth, y * _tileHeight, _tileWidth, _tileHeight, u, u2, v, v2);
					}
				}
			}
			
			PguUtils.unbindTexture();
		}
	}
}
