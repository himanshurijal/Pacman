package dev.hrijal.pacman.entities;

import java.awt.Graphics;
import java.util.ArrayList;

import dev.hrijal.pacman.Handler;
import dev.hrijal.pacman.entities.creatures.Ghost;
import dev.hrijal.pacman.entities.creatures.Player;
import dev.hrijal.pacman.entities.statics.Capsule;

public class EntityManager 
{

	private Handler handler;
	
	private ArrayList<Entity> entities;
	
	private Player player;
	
	private Ghost ghostOrange;
//	private Ghost ghostGreen;
//	private Ghost ghostPurple;

	public EntityManager(Handler handler, Player player, Ghost ghostOrange)
	{
		this.handler = handler;
		
		this.entities = new ArrayList<Entity>();
		
		this.player = player;
		
		this.ghostOrange = ghostOrange;
		//this.ghostGreen = ghostGreen;
		//this.ghostPurple = ghostPurple;
	}
	
	public void tick()
	{
		for(Entity e : entities)
		{
			e.tick();
		}
		player.tick();
		ghostOrange.tick();
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
	
	
	//GETTERS AND SETTERS 
	
	public Player getPlayer() 
	{
		return player;
	}
	
	public void setPlayer(Player player) 
	{
		this.player = player;
	}

	public ArrayList<Entity> getEntities() 
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
