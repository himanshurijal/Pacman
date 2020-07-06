package dev.hrijal.pacman.entities.statics;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import dev.hrijal.pacman.Handler;
import dev.hrijal.pacman.entities.Entity;
import dev.hrijal.pacman.entities.EntityCollisionManager;
import dev.hrijal.pacman.entities.GhostCollisionObserver;
import dev.hrijal.pacman.entities.StaticCollisionObserver;
import dev.hrijal.pacman.entities.Subject;

public class StaticEntityManager implements StaticCollisionObserver
{
	
	private List<StaticEntity> staticEntities;

	public StaticEntityManager(Handler handler, List<StaticEntity> staticEntities, EntityCollisionManager entityCollisionManager)
	{
		this.staticEntities = new ArrayList<>(staticEntities);
		entityCollisionManager.registerStaticCollisionObserver(this);
	}
	
	public void tick()
	{
		for(Entity e : staticEntities)
		{
			e.tick();
		}
	}
	
	public void render(Graphics g)
	{
		for(Entity e : staticEntities)
		{
			e.render(g);
		}
	}
	
	public void removeEntity(int index)
	{	
		staticEntities.remove(index);
	}
	
	@Override
	public void updateOnStaticCollision(Subject subject) 
	{
		if(subject instanceof EntityCollisionManager)
		{
			EntityCollisionManager entityManager = (EntityCollisionManager) subject;
			staticEntities.remove(entityManager.getStaticCollisionObject());
		}
	}
	
	
	//GETTERS AND SETTERS 

	public List<StaticEntity> getEntities() 
	{
		List<StaticEntity> entitiesCopy = new ArrayList<>(staticEntities);
		return entitiesCopy;
	}

	public void setEntities(ArrayList<StaticEntity> entities) 
	{
		this.staticEntities = new ArrayList<>(entities);
	}

}
