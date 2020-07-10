package dev.hrijal.pacman.entities.creatures.player;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import dev.hrijal.pacman.Handler;
import dev.hrijal.pacman.entities.Entity;
import dev.hrijal.pacman.entities.creatures.Creature;
import dev.hrijal.pacman.gfx.Animation;
import dev.hrijal.pacman.gfx.Assets;
import dev.hrijal.pacman.input.KeyManager;

public class Player extends Creature 
{	
	
	private boolean isDead;
	
	//MOVEMENT
	private float lastXMove; 
	private float lastYMove;
	
	//ANIMATION
	private Animation animUp;
	private Animation animDown;
	private Animation animLeft;
	private Animation animRight;
	private Animation animDead;
	
	public Player(Handler handler, float x, float y) 
	{
		super(handler, x, y);

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

		lastXMove = -speed;
		lastYMove = 0;
				
		//Animation
		animUp = new Animation(Assets.playerUp, 100);
		animDown = new Animation(Assets.playerDown, 100);
		animLeft = new Animation(Assets.playerLeft, 100);
		animRight = new Animation(Assets.playerRight, 100);
		animDead = new Animation(Assets.playerDead, 100);
	}
	
	public void tick()
	{
		if(isDead)
		{
			animDead.tick();
		}
		else
		{
			animUp.tick();
			animDown.tick();
			animLeft.tick();
			animRight.tick();
			
			getInput();
			moveX(lastXMove); 
			moveY(lastYMove);
		}
	}
	
	public void render(Graphics g)
	{		
		g.drawImage(getCurrentAnimationFrame(), (int) x, (int) y, Entity.ENTITY_WIDTH, Entity.ENTITY_HEIGHT, null);
	}	
	
	public void getInput()
	{
		xMove = 0;
		yMove = 0;
		
		KeyManager key = handler.getGame().getKeyManager();
		
		if(key.up)
		{
			yMove = -speed;
			
			if(isXMoveValid(xMove) && isYMoveValid(yMove))
			{
				lastXMove = xMove;
				lastYMove = yMove;
			}
		}
		else if(key.down)
		{
			yMove = speed;
			
			if(isXMoveValid(xMove) && isYMoveValid(yMove))
			{
				lastXMove = xMove;
				lastYMove = yMove;
			}
		}
		else if(key.left)
		{
			xMove = -speed;
			
			if(isXMoveValid(xMove) && isYMoveValid(yMove))
			{
				lastXMove = xMove;
				lastYMove = yMove;
			}
		}
		
		else if(key.right) 
		{
			xMove = speed;
			
			if(isXMoveValid(xMove) && isYMoveValid(yMove))
			{
				lastXMove = xMove;
				lastYMove = yMove;
			}
		}
	}
	
	public BufferedImage getCurrentAnimationFrame()
	{
		if(isDead)
		{
			return animDead.getCurrentFrame();
		}
		else if(lastXMove > 0)
		{
			return animRight.getCurrentFrame();
		}
		else if(lastXMove < 0 )
		{
			return animLeft.getCurrentFrame();
		}
		else if(lastYMove > 0)
		{
			return animDown.getCurrentFrame();
		}
		else if (lastYMove < 0)
		{
			return animUp.getCurrentFrame();
		}
		else
		{
//			return Assets.pacman;
			return Assets.playerLeft[0];
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