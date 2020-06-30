package dev.hrijal.pacman.entities;

import java.util.ArrayList;
import java.util.List;

import dev.hrijal.pacman.Handler;
import dev.hrijal.pacman.entities.creatures.ghosts.Ghost;
import dev.hrijal.pacman.entities.creatures.player.Player;
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
		Player player = handler.getWorld().getPlayer();
		
		int collisionIndex = getStaticEntityCollisionIndex(player, 0f, 0f);
		
		if(collisionIndex != -1)
		{
			staticCollisionObject = handler.getWorld().getStaticEntityManager().getEntities().get(collisionIndex);
			notifyObservers();
			staticCollisionObject = null;
		}
		
		for(Ghost ghost: handler.getWorld().getGhostManager().getGhosts())
		{
			if(collisionPlayerAndGhost(ghost))
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

	public int getStaticEntityCollisionIndex(Player player, float xOffset, float yOffset)
	{
		int index = -1, i = 0;
		
		for(Entity e: handler.getWorld().getStaticEntityManager().getEntities())
		{
			if(e.getEntityCollisionBounds(0f, 0f).intersects(player.getEntityCollisionBounds(xOffset, yOffset)))
			{
				index = i;
			}
			i++;
		}
		
		return index;
	}
	
	public boolean collisionPlayerAndGhost(Ghost ghost)
	{
		boolean check = false;
		
		if(handler.getWorld().getPlayer().getEntityCollisionBounds(0f, 0f).intersects(ghost.getEntityCollisionBounds(0f,0f)))
		{
			check = true;
		}
		
		return check;
	}
	
	
	//GETTERS AND SETTERS
	
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