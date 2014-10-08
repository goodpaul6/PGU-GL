package com.ngsarmy.pgu.core;

import java.util.ArrayList;

import com.ngsarmy.pgu.graphics.PguImage;
import com.ngsarmy.pgu.graphics.PguStamp;

/* PguEntity class:
 * this class forms the
 * basis for all objects in
 * the game. It provides all the
 * things necessary for collision 
 * detection, resolution, for movement,
 * for graphics, and for general processing.
 * Derive your game object classes
 * from this (ex. Player, Enemy, ...)
 */
public class PguEntity 
{
	private static ArrayList<PguEntity> _cacheEnts = new ArrayList<PguEntity>();
	
	public PguScene scene;
	public float x;
	public float y;
	public boolean collidable;
	public boolean visible;
	public boolean active;
	public PguGraphic graphic;
	public String type;
	public String name;
	public PguRectangle hitbox;
	
	private PguPoint _camera;
	private PguPoint _point;
	
	public PguEntity()
	{
		scene = null;
		x = 0;
		y = 0;
		collidable = true;
		visible = true;
		active = true;
		graphic = null;
		type = "default";
		name = "default";
		hitbox = new PguRectangle(0, 0, 0, 0);
		
		_camera = new PguPoint();
		_point = new PguPoint();
	}
	
	public PguEntity(float x, float y, PguGraphic graphic)
	{
		scene = null;
		this.x = x;
		this.y = y;
		collidable = true;
		visible = true;
		active = true;
		this.graphic = graphic;
		type = "default";
		name = "default";
		hitbox = new PguRectangle(0, 0, 0, 0);
		
		_camera = new PguPoint();
		_point = new PguPoint();
	}
	
	// USAGE:
	// override this, this is called when an entity is added to a scene
	public void added()
	{
	}
	
	// USAGE:
	// override this, this is called when an entity is removed from a scene
	public void removed()
	{
	}
	
	// USAGE:
	// override this, it is called when the entity is updated
	public void update()
	{
	}
	
	// WARNING: DO NOT CALL THIS DIRECTLY, CALLED INTERNALLY
	// If you do override this, remember to call super.render(camera_offset) on your entity
	// camera -> the camera offset
	public void render()
	{
		if(graphic != null && graphic.visible)
		{
			if(graphic.relative)
			{
				_point.x = x;
				_point.y = y;
			}
			else _point.x = _point.y = 0;
			_camera.x = (scene == null) ? 0 : scene.camera.x;
			_camera.y = (scene == null) ? 0 : scene.camera.y;
			graphic.render(_point, _camera);
		}
	}
	
	// USAGE:
	// helper method sets the hitbox of the entity to the coordinates specified
	// you can access the hitbox at any time regardless using the hitbox variable
	// x -> x offset of the hitbox (relative to the entities position)
	// y -> y offset of the hitbox (relative to the entities position)
	// w -> width of the hitbox
	// h -> height of the hitbox
	public void setHitbox(float x, float y, float w, float h)
	{
		hitbox.x = x;
		hitbox.y = y;
		hitbox.w = w;
		hitbox.h = h;
	}
	
	// USAGE:
	// helper method that sets the hitbox of the entity to it's graphic's boundaries (if possible)
	// this may fail, as such, you shouldn't use this when you know your graphic doesn't have 
	// a width or a height
	// this will also fail if the graphic of the entity is null
	public void setHitboxToGraphic()
	{
		if(graphic == null) return;
		
		if(graphic instanceof PguImage)
		{
			PguImage img = (PguImage)graphic;
			hitbox.x = img.originX;
			hitbox.y = img.originY;
			double rads = Math.toRadians(img.angle);
			double sin = Math.sin(rads);
			double cos = Math.cos(rads);
			float w = img.getWidth();
			float h = img.getHeight();
			hitbox.w = (float)(Math.abs(w * sin) + Math.abs(h * cos));
			hitbox.h = (float)(Math.abs(w * cos) + Math.abs(h * sin));
		}
		else if(graphic instanceof PguStamp)
		{
			PguStamp stamp = (PguStamp)graphic;
			hitbox.x = 0;
			hitbox.y = 0;
			hitbox.w = stamp.getWidth();
			hitbox.h = stamp.getHeight();
		}
	}
	
	// USAGE:
	// checks for collision against a specific entity type
	// type -> the entity type to check for
	// x -> the virtual x position to place this entity (the x position where it will check collision)
	// y -> the virtual y position to place this entity (the y position where it will check collision)
	// returns the first entity with which you collide (and is of the type given)
	public PguEntity collide(String type, float x, float y)
	{
		if(scene == null) return null;
		_cacheEnts.clear();
		scene.getType(type, _cacheEnts);
		
		for(int i = 0; i < _cacheEnts.size(); i++)
		{
			PguEntity ent = _cacheEnts.get(i);
			
			if(ent.collidable && ent != this &&
					x - hitbox.x + hitbox.w > ent.x - ent.hitbox.x &&
					y - hitbox.y + hitbox.y > ent.y - ent.hitbox.y &&
					x - hitbox.x < ent.x - ent.hitbox.x + ent.hitbox.w &&
					y - hitbox.y < ent.y - ent.hitbox.y + ent.hitbox.h)
			{
				return ent;
			}
		}
		
		return null;
	}
	
	// USAGE:
	// checks for collision against multiple entity types
	// types -> the Array of types against which to check collision
	// x -> the virtual x position to place this entity (the x position where it will check collision)
	// y -> the virtual y position to place this entity (the y position where it will check collision)
	// returns the first entity with which you collide (and is of one of the types given)
	public PguEntity collideTypes(String[] types, float x, float y)
	{
		if(scene == null) return null;
		
		for(int i = 0; i < types.length; i++)
		{
			PguEntity e = collide(types[i], x, y);
			if(e != null) return e;
		}
		
		return null;
	}
	
	// USAGE:
	// checks if this entity collides with a specific entity
	// ent -> the entity to check against
	// x -> the virtual x position to place this entity (the x position where it will check collision)
	// y -> the virtual y position to place this entity (the y position where it will check collision)
	// returns the entity if you are colliding with it, otherwise null
	public PguEntity collideWith(PguEntity ent, float x, float y)
	{
		if(collidable && ent.collidable &&
				x - hitbox.x + hitbox.w > ent.x - ent.hitbox.x &&
				y - hitbox.y + hitbox.y > ent.y - ent.hitbox.y &&
				x - hitbox.x < ent.x - ent.hitbox.x + ent.hitbox.w &&
				y - hitbox.y < ent.y - ent.hitbox.y + ent.hitbox.h)
			return ent;
		return null;
	}
	
	// USAGE:
	// checks if this entity collides with a specific rectangle
	// rect -> rectangle to check collision against
	// x -> the virtual x position to place this entity (the x position where it will check collision)
	// y -> the virtual y position to place this entity (the y position where it will check collision)
	// returns true if there is an overlap and false if there isn't
	public boolean collideRect(PguRectangle rect, float x, float y)
	{
		if(x - hitbox.x + hitbox.w > rect.x && 
			y - hitbox.y + hitbox.h > rect.y &&
			x - hitbox.x <= rect.x + rect.w &&
			y - hitbox.y <= rect.y + rect.h)
			return true;
		return false;
	}
	
	// USAGE:
	// moves the entity, checking for collision as it does, and stops moving if a collision occurs
	// types -> the types of entities against which to check collision
	// x -> amount to move by in the x axis
	// y -> amount to move by in the y axis
	// sweep -> whether to skip the low-res collision check and do the high res check anyways
	public void moveBy(String[] types, float x, float y, boolean sweep)
	{	
		if(x != 0)
		{
			PguEntity ent;
			
			float xAmt = (x / PguG.entityCollisionSampleAmount);
			
			if(collidable && (sweep || collideTypes(types, this.x + x, this.y) != null))
			{
				for(int i = 0; i < PguG.entityCollisionSampleAmount; i++)
				{
					ent = collideTypes(types, this.x + xAmt, this.y);
					
					if(ent != null)
					{
						if(moveCollideX(ent)) break;
						else this.x += xAmt;
					}
					else this.x += xAmt;
				}
			}
			else
				this.x += x;
		}
		
		if(y != 0)
		{
			PguEntity ent;
			
			float yAmt = (y / PguG.entityCollisionSampleAmount);
			
			if(collidable && (sweep || collideTypes(types, this.x, this.y + y) != null))
			{
				for(int i = 0; i < PguG.entityCollisionSampleAmount; i++)
				{
					ent = collideTypes(types, this.x, this.y + yAmt);
					
					if(ent != null)
					{
						if(moveCollideY(ent)) break;
						else this.y += yAmt;
					}
					else this.y += yAmt;
				}
			}
			else
				this.y += y;
		}
	}
	
	// USAGE:
	// moves the entity, checking for collision as it does, and stops moving if a collision occurs
	// type -> the type of entities against which to check collision
	// x -> amount to move by in the x axis
	// y -> amount to move by in the y axis
	// sweep -> whether to skip the low-res collision check and do the high res check anyways
	public void modeBy(String type, float x, float y, boolean sweep)
	{
		if(x != 0)
		{
			PguEntity ent;
			
			float xAmt = (x / PguG.entityCollisionSampleAmount);
			
			if(collidable && (sweep || collide(type, this.x + x, this.y) != null))
			{
				for(int i = 0; i < PguG.entityCollisionSampleAmount; i++)
				{
					ent = collide(type, this.x + xAmt, this.y);
					
					if(ent != null)
					{
						if(moveCollideX(ent)) break;
						else this.x += xAmt;
					}
					else this.x += xAmt;
				}
			}
			else
				this.x += x;
		}
		
		if(y != 0)
		{
			PguEntity ent;
			
			float yAmt = (y / PguG.entityCollisionSampleAmount);
			
			if(collidable && (sweep || collide(type, this.x, this.y + y) != null))
			{
				for(int i = 0; i < PguG.entityCollisionSampleAmount; i++)
				{
					ent = collide(type, this.x, this.y + yAmt);
					
					if(ent != null)
					{
						if(moveCollideY(ent)) break;
						else this.y += yAmt;
					}
					else this.y += yAmt;
				}
			}
			else
				this.y += y;
		}
	}
	
	// USAGE:
	// override this, this will be called by moveBy when you overlap an object on the x axis
	// ent -> the entity which it collided with
	// returns whether a collision occured (if you return false, this entity will pass through the object with which it collided)
	public boolean moveCollideX(PguEntity ent)
	{
		return true;
	}
	
	// USAGE:
	// override this, this will be called by moveBy when you overlap an object on the y axis
	// ent -> the entity which it collided with
	// returns whether a collision occured (if you return false, this entity will pass through the object with which it collided)
	public boolean moveCollideY(PguEntity ent)
	{
		return true;
	}
}
