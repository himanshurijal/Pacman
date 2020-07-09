package dev.hrijal.pacman.entities;

import java.awt.Graphics;
import java.awt.Rectangle;

import dev.hrijal.pacman.Handler;
import dev.hrijal.pacman.tiles.Tile;

public abstract class Entity 
{

	protected Handler handler;
	
	protected float x, y;
	public static final int ENTITY_HEIGHT = Tile.TILEHEIGHT,
						    ENTITY_WIDTH = Tile.TILEWIDTH;
	
	protected Rectangle entityCollisionBounds;
		
	public Entity(Handler handler, float x, float y)
	{
		this.handler = handler;
		
		this.x = x;
		this.y = y;
		
		this.entityCollisionBounds = new Rectangle(0, 0, Entity.ENTITY_WIDTH, Entity.ENTITY_HEIGHT);
	}
	
	public abstract void tick();
	
	public abstract void render(Graphics g);
	
	
	//GETTERS AND SETTERS
	
	public Handler getHandler()
	{
		return handler;
	}
	
	public float getX() 
	{
		return x;
	}

	public void setX(float x) 
	{
		this.x = x;
	}

	public float getY() 
	{
		return y;
	}

	public void setY(float y) 
	{
		this.y = y;
	}
	
	public Rectangle getEntityCollisionBounds(float xOffset, float yOffset)
	{
		return new Rectangle((int) (x + entityCollisionBounds.x + xOffset), (int) (y + entityCollisionBounds.y + yOffset),
												   (int) entityCollisionBounds.width, (int) entityCollisionBounds.height);			
	}
	
}
