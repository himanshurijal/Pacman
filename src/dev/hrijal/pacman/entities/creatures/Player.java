package dev.hrijal.pacman.entities.creatures;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import dev.hrijal.pacman.Handler;
import dev.hrijal.pacman.entities.Entity;
import dev.hrijal.pacman.gfx.Animation;
import dev.hrijal.pacman.gfx.Assets;
import dev.hrijal.pacman.input.KeyManager;
import dev.hrijal.pacman.tiles.Tile;

public class Player extends Creature
{	
	
	private boolean isDead;
	
	//Animation
	private Animation animUp;
	private Animation animDown;
	private Animation animLeft;
	private Animation animRight;
	private Animation animDead;
	
	private int score;
	
	public Player(Handler handler, float x, float y, int points) 
	{
		super(handler, x, y, points);
		
		score = 0;
		isDead = false;
		
		//Movement		
		mazeCollisionBounds.x = 0;
		mazeCollisionBounds.y = 0;
		mazeCollisionBounds.width = Entity.ENTITY_WIDTH - 1;
		mazeCollisionBounds.height = Entity.ENTITY_HEIGHT - 1;
		
		entityCollisionBounds.x = Entity.ENTITY_WIDTH / 2 - 5;;
		entityCollisionBounds.y = Entity.ENTITY_HEIGHT / 2 - 5;
		entityCollisionBounds.width = 10;
		entityCollisionBounds.height = 10;
		
		//Animation
		animUp = new Animation(Assets.playerUp, 100);
		animDown = new Animation(Assets.playerDown, 100);
		animLeft = new Animation(Assets.playerLeft, 100);
		animRight = new Animation(Assets.playerRight, 100);
		animDead = new Animation(Assets.playerDead, 100);
	}
	
	public void tick()
	{
		if(!isDead)
		{
			animUp.tick();
			animDown.tick();
			animLeft.tick();
			animRight.tick();
			
			getInput();
			
			int entityIndex = 0;
			
			moveX(); 
			entityIndex = getEntityCollisionIndex(xMove, 0f);
			if(entityIndex != -1)
			{
				score += handler.getWorld().getEntityManager().getEntities().get(entityIndex).getPoints();
				handler.getWorld().getEntityManager().removeEntity(entityIndex);
			}

			moveY();
			entityIndex = getEntityCollisionIndex(0f, yMove);
			if(entityIndex != -1)
			{
				score += handler.getWorld().getEntityManager().getEntities().get(entityIndex).getPoints();
				handler.getWorld().getEntityManager().removeEntity(entityIndex);
			}
		}
		else
		{
			animDead.tick();
		}
	}
	
	public void render(Graphics g)
	{
		g.setColor(Color.white);
		g.drawString("Current Score: " + score, Tile.TILEWIDTH / 2, Tile.TILEHEIGHT / 2 + 5);
		
		g.drawImage(getCurrentAnimationFrame(), (int) x, (int) y, Entity.ENTITY_WIDTH, Entity.ENTITY_HEIGHT, null);
		
		g.setColor(Color.white);
		g.drawRect((int) (mazeCollisionBounds.x + x), (int) (mazeCollisionBounds.y + y), 
				   									 (int) mazeCollisionBounds.height, (int) mazeCollisionBounds.width);
		g.drawRect((int) x + entityCollisionBounds.x, (int) y + entityCollisionBounds.y,
													 entityCollisionBounds.width, entityCollisionBounds.height);
	}	
	
	public void getInput()
	{
		xMove = 0;
		yMove = 0;
		
		KeyManager key = handler.getGame().getKeyManager();
		
		if(key.up)
		{
			yMove = -speed;
		}
		else if(key.down)
		{
			yMove = speed;
		}
		else if(key.left)
		{
			xMove = -speed;
		}
		else if(key.right) 
		{
			xMove = speed;
		}
	}
	
	public int getEntityCollisionIndex(float xOffset, float yOffset)
	{
		int index = -1, i = 0;
		
		for(Entity e: handler.getWorld().getEntityManager().getEntities())
		{
			if(e.equals(this))
			{
				continue;
			}
			if(e.getCollisionBounds(0f, 0f).intersects(getCollisionBounds(xOffset, yOffset)))
			{
				index = i;
			}
			i++;
		}
		
		return index;
	}

	public BufferedImage getCurrentAnimationFrame()
	{
		if(isDead)
		{
			return animDead.getCurrentFrame();
		}
		else if(xMove > 0)
		{
			return animRight.getCurrentFrame();
		}
		else if(xMove < 0 )
		{
			return animLeft.getCurrentFrame();
		}
		else if(yMove > 0)
		{
			return animDown.getCurrentFrame();
		}
		else if (yMove < 0)
		{
			return animUp.getCurrentFrame();
		}
		else
		{
			return Assets.pacman;
		}
	}
	
	
	//GETTERS AND SETTERS
	
	public boolean isDead()
	{
		return isDead;
	}

	public void setDead(boolean isDead) 
	{
		this.isDead = isDead;
	}
	
}