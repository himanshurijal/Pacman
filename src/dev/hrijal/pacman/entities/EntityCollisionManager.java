package dev.hrijal.pacman.entities;

import java.util.ArrayList;
import java.util.List;

import dev.hrijal.pacman.Handler;
import dev.hrijal.pacman.entities.creatures.ghosts.Ghost;
import dev.hrijal.pacman.entities.statics.StaticEntity;

public class EntityCollisionManager implements Subject  //TODO: Try to implement Facade Pattern and the principle of least knowledge
{
	
	private Handler handler;
	private List<Observer> observers; //Observer classes: ScoreManager, GhostManager, StaticEntityManager
	private StaticEntity staticCollisionObject;
	private List<Ghost> ghostCollisionObjects;
	
	public EntityCollisionManager(Handler handler)
	{
		this.handler = handler;
		observers = new ArrayList<>();
		staticCollisionObject = null;
		ghostCollisionObjects = new ArrayList<>();
	}
	
	public void registerObserver(Observer o)
	{
		observers.add(o);
	}
	
	public void removeObserver(Observer o)
	{
		int index = observers.indexOf(o);
		
		if(index >= 0)
		{
			observers.remove(o);
		}
	}
	
	public void notifyObservers()
	{
		for(Observer o: observers)
		{
			o.update(this);
		}
	}
	
	public void checkCollisionAndNotify()
	{
		int collisionIndex = handler.getWorld().getPlayer().getStaticEntityCollisionIndex(0f, 0f);
		
		if(collisionIndex != -1)
		{
			staticCollisionObject = handler.getWorld().getStaticEntityManager().getEntities().get(collisionIndex);
			notifyObservers();
			staticCollisionObject = null;
		}
		
		for(Ghost ghost: handler.getWorld().getGhostManager().getGhosts())
		{
			if(ghost.collisionWithPlayer() && !ghost.isDead())
			{
				ghostCollisionObjects.add(ghost);
			}
		}
		
		if(ghostCollisionObjects.size() > 0)
		{
			notifyObservers();
			ghostCollisionObjects.clear();
		}
	}

	public StaticEntity getStaticCollisionObject()
	{
		return staticCollisionObject;
	}
	
	public List<Ghost> getGhostCollisionObjects()
	{
		List<Ghost> ghostCollisionObjectsCopy = new ArrayList<>(ghostCollisionObjects);
		return ghostCollisionObjectsCopy;
	}
	
}