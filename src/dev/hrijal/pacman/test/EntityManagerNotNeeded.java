package dev.hrijal.pacman.test;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import dev.hrijal.pacman.Handler;
import dev.hrijal.pacman.entities.Entity;
import dev.hrijal.pacman.entities.Observer;
import dev.hrijal.pacman.entities.Subject;
import dev.hrijal.pacman.entities.creatures.ghosts.Ghost;
import dev.hrijal.pacman.entities.creatures.player.Player;
import dev.hrijal.pacman.entities.statics.Capsule;

public class EntityManagerNotNeeded implements Subject
{

	private Handler handler;
	
	private List<Entity> entities;
	
	private Player player;
	
	private Ghost ghostOrange;
//	private Ghost ghostGreen;
//	private Ghost ghostPurple;
	
	private List<Observer> observers;

	public EntityManagerNotNeeded(Handler handler, Player player, Ghost ghostOrange)
	{
		this.handler = handler;
		
		this.entities = new ArrayList<>();
		
		this.player = player;
		
		this.ghostOrange = ghostOrange;
		//this.ghostGreen = ghostGreen;
		//this.ghostPurple = ghostPurple;
		
		this.observers = new ArrayList<>();
	}
	
	public void tick()
	{
		for(Entity e : entities)
		{
			e.tick();
		}
		player.tick();
		ghostOrange.tick();
		
		//Check for player collisions
		
		player.getStaticEntityCollisionIndex(0f, 0f);
	}
	
	public void render(Graphics g)
	{
		for(Entity e : entities)
		{
			e.render(g);
		}
		player.render(g);
		ghostOrange.render(g);
	}
	
	public void removeEntity(int index)
	{
		if(entities.get(index).getClass() == Capsule.class)
		{
			ghostOrange.setFrightened(true);
		}			
		entities.remove(index);
	}
	
	
	//OBSERVER INTERFACE 
	
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
//			o.update();
		}
	}
	
	
	//GETTERS AND SETTERS 
	
	public Player getPlayer() 
	{
		return player;
	}
	
	public void setPlayer(Player player) 
	{
		this.player = player;
	}

	public List<Entity> getEntities() 
	{
		return entities;
	}

	public void setEntities(ArrayList<Entity> entities) 
	{
		this.entities = entities;
	}

	public Handler getHandler() 
	{
		return handler;
	}

	public void setHandler(Handler handler) 
	{
		this.handler = handler;
	}

	public void addEntity(Entity e)
	{
		entities.add(e);
	}
	
}
