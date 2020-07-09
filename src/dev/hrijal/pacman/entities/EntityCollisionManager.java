package dev.hrijal.pacman.entities;

import java.util.ArrayList;
import java.util.List;

import dev.hrijal.pacman.Handler;
import dev.hrijal.pacman.entities.creatures.ghosts.Ghost;
import dev.hrijal.pacman.entities.creatures.ghosts.ghoststates.DeadState;
import dev.hrijal.pacman.entities.creatures.player.Player;
import dev.hrijal.pacman.entities.statics.Capsule;
import dev.hrijal.pacman.entities.statics.StaticEntity;

public class EntityCollisionManager implements Subject 
{
	
	private Handler handler;
	
	private List<GhostCollisionObserver> ghostCollisionObservers; 	//Observer classes: ScoreManager, GhostManager
	private List<Ghost> ghostCollisionObjects;
	
	private List<StaticCollisionObserver> staticCollisionObservers; //Observer classes: ScoreManager, GhostManager, StaticEntityManager
	private StaticEntity staticCollisionObject;
	private int capsuleCount;
	
	public EntityCollisionManager(Handler handler)
	{
		this.handler = handler;
		
		ghostCollisionObservers = new ArrayList<>();
		ghostCollisionObjects = new ArrayList<>();
		
		staticCollisionObservers = new ArrayList<>();
		staticCollisionObject = null;
		capsuleCount = 0;
	}
	
	@Override
	public void registerGhostCollisionObserver(GhostCollisionObserver o)
	{
		ghostCollisionObservers.add(o);
	}
	
	@Override
	public void registerStaticCollisionObserver(StaticCollisionObserver o)
	{
		staticCollisionObservers.add(o);
	}

	@Override
	public void removeGhostCollisionObserver(GhostCollisionObserver o)
	{
		int index = ghostCollisionObservers.indexOf(o);
		
		if(index >= 0)
		{
			ghostCollisionObservers.remove(o);
		}
	}
	
	@Override
	public void removeStaticCollisionObserver(StaticCollisionObserver o)
	{
		int index = staticCollisionObservers.indexOf(o);
		
		if(index >= 0)
		{
			staticCollisionObservers.remove(o);
		}	
	}
	
	public void notifyGhostCollisionObservers()
	{
		for(GhostCollisionObserver o: ghostCollisionObservers)
		{
			o.updateOnGhostCollision(this);
		}
	}
	
	@Override
	public void notifyStaticCollisionObservers()
	{
		for(StaticCollisionObserver o: staticCollisionObservers)
		{
			o.updateOnStaticCollision(this);
		}
	}
	
	public void checkStaticCollisionAndNotify()
	{
		Player player = handler.getWorld().getPlayer();
		
		int collisionIndex = getStaticEntityCollisionIndex(player, 0f, 0f);
		
		if(collisionIndex != -1)
		{
			staticCollisionObject = handler.getWorld().getStaticEntities().get(collisionIndex);
			if(staticCollisionObject instanceof Capsule)
			{
				capsuleCount++;
			}
			
			notifyStaticCollisionObservers();
			staticCollisionObject = null;
		}
	}
	
	public void checkGhostCollisionAndNotify()
	{	
		for(Ghost ghost: handler.getWorld().getGhosts())
		{
			if(collisionPlayerAndGhost(ghost) && !(ghost.getState() instanceof DeadState))
			{
				ghostCollisionObjects.add(ghost);
			}
		}
		
		if(ghostCollisionObjects.size() > 0)
		{
			notifyGhostCollisionObservers();
			ghostCollisionObjects.clear();
		}
	}
	
	public int getStaticEntityCollisionIndex(Player player, float xOffset, float yOffset)
	{
		int index = -1, i = 0;
		
		for(Entity e: handler.getWorld().getStaticEntities())
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
	
	public int getCapsuleCount()
	{
		return capsuleCount;
	}
	
	public List<Ghost> getGhostCollisionObjects()
	{
		List<Ghost> ghostCollisionObjectsCopy = new ArrayList<>(ghostCollisionObjects);
		return ghostCollisionObjectsCopy;
	}
	
}