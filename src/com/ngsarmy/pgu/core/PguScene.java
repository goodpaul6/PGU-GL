package com.ngsarmy.pgu.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ngsarmy.pgu.utils.PguInput;

/* PguScene class:
 * this class houses entities
 * and acts as a state or in this
 * case a "scene" from which you
 * can transition to other scenes.
 * (Ex. MenuScene, PlayScene, ...)
 */
public class PguScene 
{
	public PguPoint camera;
	
	private HashMap<Integer, ArrayList<PguEntity>> _entities;
	private HashMap<Integer, ArrayList<PguEntity>> _add;
	private ArrayList<PguEntity> _remove;
	
	public PguScene()
	{
		camera = new PguPoint();
		_entities = new HashMap<Integer, ArrayList<PguEntity>>();
		_add = new HashMap<Integer, ArrayList<PguEntity>>();
		_remove = new ArrayList<PguEntity>();
	}
	
	// USAGE:
	// override this, called when the scene is switched to, and is set to the currently active scene
	public void begin()
	{
	}
	
	// USAGE:
	// override this, called when the scene is changed and the active scene is no longer this
	public void end()
	{
	}
	
	// WARNING: DO NOT CALL THIS DIRECTLY, IT IS CALLED INTERNALLY
	// If you do override this, remember to call super.update()
	// it updates all the entities in the scene
	// returns whether the update should fall through (if scenes below this one should update)
	// if you override this for something like a gui overlay, make sure
	// to return true in your overrided function so that the next state gets updated
	public boolean update()
	{
		for(ArrayList<PguEntity> ents : _entities.values())
		{
			for(int i = 0; i < ents.size(); i++)
			{
				PguEntity ent = ents.get(i);
				
				if(ent.active)
					ent.update();
				
				if(ent.graphic != null && ent.graphic.active) ent.graphic.update();
			}
		}
		
		return true;
	}
	
	// WARNING: DO NOT CALL THIS DIRECTLY, IT IS CALLED INTERNALLY
	// If you do override this, remember to call super.render()
	// it renders all the entities in the scene
	// returns whether the render should fall through (if scenes below this one should render)
	// if you override this for something like a gui overlay, make sure
	// to return true in your overrided function so that the next state gets rendered (if you wish for that to happen)
	public boolean render()
	{
		for(ArrayList<PguEntity> ents : _entities.values())
		{
			for(int i = 0; i < ents.size(); i++)
			{
				PguEntity ent = ents.get(i);

				if(ent.visible) ent.render();
			}
		}
		
		return true;
	}
	
	// USAGE:
	// returns the x mouse position relative to this scene (including camera offset, etc)
	public float getMouseX()
	{
		return PguInput.getMouseX() + camera.x;
	}

	// USAGE:
	// returns the y mouse position relative to this scene (including camera offset, etc)
	public float getMouseY()
	{
		return PguInput.getMouseY() + camera.y;
	}
	
	// USAGE:
	// adds an entity to the first layer (if you don't care about it's layer, this is the method you want)
	// e -> entity to add
	// returns the added entity
	public PguEntity add(PguEntity e)
	{
		add(e, 0);
		return e;
	}
	
	// USAGE:
	// adds an entity to a specific layer
	// e -> entity to add
	// layer -> the layer to which this entity will be added
	// returns the added entity
	public PguEntity add(PguEntity e, int layer)
	{
		if(!_entities.containsKey(layer))
			_entities.put(layer, new ArrayList<PguEntity>());
		if(!_add.containsKey(layer))
			_add.put(layer, new ArrayList<PguEntity>());
		_add.get(layer).add(e);
		return e;
	}
	
	// USAGE:
	// adds a graphic to the scene as an entity
	// g -> graphic to add
	// layer -> the layer to which you wish to add this graphic
	// returns the entity which contains the graphic you added
	public PguEntity addGraphic(PguGraphic g, int layer)
	{
		PguEntity ent = new PguEntity(0, 0, g);
		ent.active = false;
		return add(ent, layer);
	}
	
	// USAGE:
	// adds a graphic to the scene as an entity on the first layer (use this if you don`t care about the layer of the entity)
	// g -> graphic to add
	// returns the entity which contains the graphic you added
	public PguEntity addGraphic(PguGraphic g)
	{
		PguEntity ent = new PguEntity(0, 0, g);
		ent.active = false;
		return add(ent);
	}
	
	// USAGE:
	// removes an entity from the scene
	// returns the removed entity
	public PguEntity remove(PguEntity e)
	{
		_remove.add(e);
		return e;
	}
	
	// USAGE:
	// removes all of the entities in the scene
	public void removeAll()
	{
		for(ArrayList<PguEntity> ents : _entities.values())
		{
			for(int i = 0; i < ents.size(); i++)
				remove(ents.get(i));
		}
	}
	
	// USAGE:
	// puts all of the entities in the scene with a specific type
	// type -> type of the entities which you are looking for
	// into -> List into which the entities will be placed
	public void getType(String type, List<PguEntity> into)
	{
		for(ArrayList<PguEntity> ents : _entities.values())
		{
			for(int i = 0; i < ents.size(); i++)
			{
				PguEntity ent = ents.get(i);
				if(ent.type.equals(type)) into.add(ent);
			}
		}
	}
	
	// USAGE:
	// puts all of the entities in the scene with a specific layer
	// layer -> the layer from which you want the entities
	// into -> the List into which the entities will be placed
	public void getLayer(int layer, List<PguEntity> into)
	{
		if(!_entities.containsKey(layer)) return;
		ArrayList<PguEntity> ents = _entities.get(layer);
		for(int i = 0; i < ents.size(); i++)
			into.add(ents.get(i));
	}
	
	// USAGE:
	// gets the first object in the scene with a specific name
	// name -> the name of the object which you are looking for
	// returns the entity with the name (if found, else null)
	public PguEntity getInstance(String name)
	{
		for(ArrayList<PguEntity> ents : _entities.values())
		{
			for(int i = 0; i < ents.size(); i++)
			{
				PguEntity ent = ents.get(i);
				if(ent.name.equals(name)) return ent;
			}
		}
		return null;
	}
	
	// DANGER: INTERNAL METHOD, DO NOT CALL DIRECTLY
	public void applyChanges()
	{
		for(Map.Entry<Integer, ArrayList<PguEntity>> entSet : _add.entrySet())
		{
			for(int i = 0; i < entSet.getValue().size(); i++)
			{
				_entities.get(entSet.getKey()).add(entSet.getValue().get(i));
				entSet.getValue().get(i).scene = this;
				entSet.getValue().get(i).added();
			}
		}
		_add.clear();
		
		for(int i = 0; i < _remove.size(); i++)
		{
			_entities.remove(_remove.get(i));
			_remove.get(i).removed();
		}
		_remove.clear();
	}
}
