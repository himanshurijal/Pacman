package dev.hrijal.pacman.entities.creatures;

import java.awt.Rectangle;

import dev.hrijal.pacman.Handler;
import dev.hrijal.pacman.entities.Entity;
import dev.hrijal.pacman.tiles.Tile;

public abstract class Creature extends Entity
{
	
	public static final float DEFAULT_SPEED = 3.0f;
	
	protected float speed;
	protected float xMove, yMove;
	
	protected Rectangle mazeCollisionBounds;
	
	public Creature(Handler handler, float x, float y, int points)
	{
		super(handler, x, y, points);
		
		this.speed  = DEFAULT_SPEED;
		this.xMove = 0;
		this.yMove = 0;
		
		this.mazeCollisionBounds  = new Rectangle(0, 0, Entity.ENTITY_WIDTH, Entity.ENTITY_HEIGHT);
	}

	public boolean isCollisionWithTile(int x, int y)
	{
		return handler.getWorld().getTile(y, x).isSolid();
	}
	
	public void moveX()
	{
		int tupY = (int) (y + mazeCollisionBounds.y) / Tile.TILEHEIGHT;
		int tlowY = (int) ((y + mazeCollisionBounds.y + mazeCollisionBounds.height) / Tile.TILEHEIGHT);
		int tX; 
		
		if(xMove >  0) //Move Right
		{
			tX = (int) (x + mazeCollisionBounds.x + mazeCollisionBounds.width + xMove) / Tile.TILEWIDTH;
			
			if(!isCollisionWithTile(tX,tupY) && !isCollisionWithTile(tX,tlowY) )
			{
				x += xMove;
			}
			else
			{
				x = tX * Tile.TILEWIDTH - mazeCollisionBounds.x - mazeCollisionBounds.width - 1;
			}
		}
		else if(xMove < 0)//Move Left
		{
			tX = (int) (x + mazeCollisionBounds.x + xMove) / Tile.TILEWIDTH;
			
			if(!isCollisionWithTile(tX,tupY) && !isCollisionWithTile(tX,tlowY) )
			{
				x += xMove;
			}
			else
			{
				x = tX * Tile.TILEWIDTH + Tile.TILEWIDTH - mazeCollisionBounds.x;
			}
		}

	}
	
	public void moveY()
	{
		//We need trightX and tleftX to ensure that our player cannot move in between solid block
		int trightX = (int) (x + mazeCollisionBounds.x + mazeCollisionBounds.width) / Tile.TILEWIDTH;
		int tleftX = (int) (x + mazeCollisionBounds.x) / Tile.TILEWIDTH;
		int tY;
		
		if(yMove > 0) //Move Down
		{
			tY = (int) (y + mazeCollisionBounds.y + mazeCollisionBounds.height + yMove) / Tile.TILEHEIGHT;
			
			if(!isCollisionWithTile(trightX,tY) && !isCollisionWithTile(tleftX,tY) )
			{
				y += yMove;
			}
			else
			{
				y = tY * Tile.TILEHEIGHT - mazeCollisionBounds.y - mazeCollisionBounds.height - 1;
			}
		}
		else if(yMove < 0)//Move Up
		{
			tY = (int) (y + mazeCollisionBounds.y + yMove) / Tile.TILEHEIGHT;
			
			if(!isCollisionWithTile(trightX,tY) && !isCollisionWithTile(tleftX,tY) )
			{
				y += yMove;
			}
			else
			{
				y = tY * Tile.TILEHEIGHT + Tile.TILEHEIGHT - mazeCollisionBounds.y;
			}
		}
	}
	
	
	//GETTERS AND SETTERS 
	
	public float getSpeed() 
	{
		return speed;
	}

	public void setSpeed(float speed)
	{
		this.speed = speed;
	}
	
	public float getxMove()
	{
		return xMove;
	}

	public void setxMove(float xMove) 
	{
		this.xMove = xMove;
	}

	public float getyMove()
	{
		return yMove;
	}

	public void setyMove(float yMove)
	{
		this.yMove = yMove;
	}
	
	public Rectangle getMazeCollisionBounds()
	{
		return mazeCollisionBounds;
	}
	
}
