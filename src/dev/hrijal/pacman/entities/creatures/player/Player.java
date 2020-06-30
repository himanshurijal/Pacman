package dev.hrijal.pacman.entities.creatures.player;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import dev.hrijal.pacman.Handler;
import dev.hrijal.pacman.entities.Entity;
import dev.hrijal.pacman.entities.creatures.Creature;
import dev.hrijal.pacman.gfx.Animation;
import dev.hrijal.pacman.gfx.Assets;
import dev.hrijal.pacman.input.KeyManager;

public class Player extends Creature //TODO: Try to implement Facade Pattern and the principle of least knowledge
{	
	
	private boolean isDead;
	
	//ANIMATION
	private Animation animUp;
	private Animation animDown;
	private Animation animLeft;
	private Animation animRight;
	private Animation animDead;
	
	public Player(Handler handler, float x, float y, int points) 
	{
		super(handler, x, y, points);

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
			moveX(); 
			moveY();
		}
		else
		{
			animDead.tick();
		}
	}
	
	public void render(Graphics g)
	{		
		g.drawImage(getCurrentAnimationFrame(), (int) x, (int) y, Entity.ENTITY_WIDTH, Entity.ENTITY_HEIGHT, null);
//		
//		g.setColor(Color.white);
//		g.drawRect((int) (mazeCollisionBounds.x + x), (int) (mazeCollisionBounds.y + y), 
//				   									 (int) mazeCollisionBounds.height, (int) mazeCollisionBounds.width);
//		g.drawRect((int) x + entityCollisionBounds.x, (int) y + entityCollisionBounds.y,
//													 entityCollisionBounds.width, entityCollisionBounds.height);
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