package dev.hrijal.pacman.entities.creatures;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import dev.hrijal.pacman.Handler;
import dev.hrijal.pacman.entities.Entity;
import dev.hrijal.pacman.entities.creatures.ghostMovement.ChaseAmbush;
import dev.hrijal.pacman.entities.creatures.ghostMovement.ChaseBehavior;
import dev.hrijal.pacman.entities.creatures.ghostMovement.DeadBehavior;
import dev.hrijal.pacman.entities.creatures.ghostMovement.DeadRunHome;
import dev.hrijal.pacman.entities.creatures.ghostMovement.FrightenedBehavior;
import dev.hrijal.pacman.entities.creatures.ghostMovement.FrightenedRunMiddle;
import dev.hrijal.pacman.entities.creatures.ghostMovement.ScatterBehavior;
import dev.hrijal.pacman.entities.creatures.ghostMovement.ScatterTopRight;
import dev.hrijal.pacman.gfx.Animation;
import dev.hrijal.pacman.gfx.Assets;
import dev.hrijal.pacman.tiles.Tile;

public class Ghost extends Creature
{
	//Movement
	float[] lastAdjNode;
	
	private boolean isFrightened;
	private boolean isFlashing;
	private boolean isDead;
	
	private long timer, lastTime;
	private static final long DISABLED_DURATION = 7000,  //Calculated in milliseconds
					   		  FLASHING_DURATION = 1000;
	
	private ChaseBehavior chaseBehavior;
	private FrightenedBehavior frightenedBehavior;
	private ScatterBehavior scatterBehavior;
	private DeadBehavior deadBehavior;
	
	//Animation
	private BufferedImage[] animMovement;
	private Animation animFlashing;
	
	public Ghost(Handler handler, float x, float y, BufferedImage[] movementAssets)
	{
		super(handler, x, y, 200);
		
		//Movement
		lastAdjNode = new float[2];
		lastAdjNode[0] = -1.0f;
		lastAdjNode[1] = -1.0f;
		
		mazeCollisionBounds.x = 0;
		mazeCollisionBounds.y = 0;
		mazeCollisionBounds.width = Entity.ENTITY_WIDTH -1;
		mazeCollisionBounds.height = Entity.ENTITY_HEIGHT -1;
		
		entityCollisionBounds.x = Entity.ENTITY_WIDTH / 2 - 7;
		entityCollisionBounds.y = Entity.ENTITY_HEIGHT / 2 - 7;
		entityCollisionBounds.width = 14;
		entityCollisionBounds.height = 14;
		
		this.timer = 0;
		this.lastTime = 0;
		
		chaseBehavior  = new ChaseAmbush(handler,this);
		frightenedBehavior = new FrightenedRunMiddle(this);
		scatterBehavior = new ScatterTopRight(this);
		deadBehavior = new DeadRunHome(this);
		
		this.isFrightened = false;
		this.isFlashing = false;
		this.isDead = false;
		
		//Animation
		this.animMovement = movementAssets;
		this.animFlashing = new Animation(Assets.ghostFlashing, 100);
	}

	@Override
	public void tick() 
	{
		if(isFrightened)
		{
			if(collisionWithPlayer())
			{
				setDead(true);
				setFrightened(false);
				
				x = Math.round(x / Tile.TILEWIDTH) * Tile.TILEWIDTH;
				y = Math.round(y / Tile.TILEHEIGHT) * Tile.TILEHEIGHT;
				
				System.out.println(x + "," + y);
				
				timer = 0;
			}
			else
			{
				if(lastTime == 0)
					lastTime = System.currentTimeMillis();
				
				timer += System.currentTimeMillis() - lastTime;
				lastTime = System.currentTimeMillis();
	
				frightenedBehavior.runAway();
				
				if(timer >= DISABLED_DURATION)
				{
					setFrightened(false);
					setFlashing(true);
					timer = 0;
				}
			}
		}
		
		if(isFlashing)
		{
			if(collisionWithPlayer())
			{
				setDead(true);
				setFlashing(false);
				
				x = Math.round(x / Tile.TILEWIDTH) * Tile.TILEWIDTH;
				y = Math.round(y / Tile.TILEHEIGHT) * Tile.TILEHEIGHT;
				
				timer = 0;
			}
			else
			{
				animFlashing.tick();
				
				timer += System.currentTimeMillis() - lastTime;
				lastTime = System.currentTimeMillis();
	
				frightenedBehavior.runAway();
				
				if(timer >= FLASHING_DURATION)
				{
					setFlashing(false);
					timer = 0;
					lastTime = 0;
				}
			}
		}
		
		if(isDead)
		{
			deadBehavior.runToHome();
			
			if(x == Tile.TILEWIDTH * 11 && y == Tile.TILEHEIGHT * 9)
			{
				setDead(false);
				timer = 0;
				lastTime = 0;
			}
		}
		
		if(!isFrightened && !isFlashing && !isDead)
		{
			if(collisionWithPlayer())
			{
				handler.getWorld().getEntityManager().getPlayer().setDead(true);
			}
			else
			{
				timer += System.currentTimeMillis() - lastTime;
				lastTime = System.currentTimeMillis();
				
				chaseBehavior.chase();
				
//				scatterBehavior.scatter();
				
				if(timer >= FLASHING_DURATION)
				{
					setFlashing(false);
					timer = 0;
					lastTime = 0;
				}
			}
		}	
	}

	@Override
	public void render(Graphics g) 
	{
		if(isFrightened)
		{
			g.drawImage(Assets.ghostFlashing[0], (int) x, (int) y, Entity.ENTITY_WIDTH, Entity.ENTITY_HEIGHT, null);
		}
		else if(isFlashing)
		{
			g.drawImage(getCurrentAnimationFrame(), (int) x, (int) y, Entity.ENTITY_WIDTH, Entity.ENTITY_HEIGHT, null);
		}
		else
		{
			g.drawImage(getCurrentImageFrame(), (int) x, (int) y, Entity.ENTITY_WIDTH, Entity.ENTITY_HEIGHT, null);
		}
		
		g.setColor(Color.white);
		g.drawRect((int) (mazeCollisionBounds.x + x), (int) (mazeCollisionBounds.y + y), 
				   									 (int) mazeCollisionBounds.height, (int) mazeCollisionBounds.width);
		g.drawRect((int) x + entityCollisionBounds.x, (int) y + entityCollisionBounds.y,
													 entityCollisionBounds.width, entityCollisionBounds.height);
		chaseBehavior.render(g);
	}
	
	public void makeNextMove(float destX, float destY, float tempSpeed) //Simpler implementation of A* algorithm
	{
		List<List<Float>> adjNodeValues = new ArrayList<>();
		
		float currGhostX = 0;
		float currGhostY = 0;
		
		currGhostX = x;			
		currGhostY = y - tempSpeed; //Up
		
		if(isXMoveValid(currGhostX - x) && isYMoveValid(currGhostY - y))
		{
			adjNodeValues.add(Arrays.asList(currGhostX, currGhostY, 1.0f, getHValue(currGhostX, currGhostY, destX, destY),
																	1.0f + getHValue(currGhostX, currGhostY, destX, destY)));
		}
		
		currGhostX = x - tempSpeed; //Left
		currGhostY = y;
		
		if(isXMoveValid(currGhostX - x) && isYMoveValid(currGhostY - y))
		{
			adjNodeValues.add(Arrays.asList(currGhostX, currGhostY, 1.0f, getHValue(currGhostX, currGhostY, destX, destY),
																	1.0f + getHValue(currGhostX, currGhostY, destX, destY)));
		}
		
		
		currGhostX = x;
		currGhostY = y + tempSpeed; //Down
		
		if(isXMoveValid(currGhostX - x) && isYMoveValid(currGhostY - y))
		{
			adjNodeValues.add(Arrays.asList(currGhostX, currGhostY, 1.0f, getHValue(currGhostX, currGhostY, destX, destY),
																	1.0f + getHValue(currGhostX, currGhostY, destX, destY)));
		}
		
		currGhostX = x + tempSpeed; //Right
		currGhostY = y;
		
		if(isXMoveValid(currGhostX - x) && isYMoveValid(currGhostY - y))
		{
			adjNodeValues.add(Arrays.asList(currGhostX, currGhostY, 1.0f, getHValue(currGhostX, currGhostY, destX, destY),
					                        						1.0f + getHValue(currGhostX, currGhostY, destX, destY)));
		}

		if(adjNodeValues.size() > 1)
		{
			int index = -1;
			int i = 0;
			for(List<Float> node: adjNodeValues)
			{
				if(node.get(0) == lastAdjNode[0] && node.get(1) == lastAdjNode[1])
				{
					index = i;
				}
				i++;
			}
			if(index != -1) adjNodeValues.remove(index);
		}
		
		Collections.sort(adjNodeValues, new ListCompare());
		
		if(adjNodeValues.size() > 0)
		{

			lastAdjNode[0] = x;
			lastAdjNode[1] = y;
			
			xMove = (adjNodeValues.get(0).get(0) - x);
			yMove =  (adjNodeValues.get(0).get(1) - y);
			
			x += xMove;
			y += yMove;
		}
	}
	
	public boolean isXMoveValid(float xMove)
	{
		boolean check = false;
		int tupY = (int) (y + mazeCollisionBounds.y) / Tile.TILEHEIGHT;
		int tlowY = (int) ((y + mazeCollisionBounds.y + mazeCollisionBounds.height) / Tile.TILEHEIGHT);
		int tX; 
		
		if(xMove > 0) //Move Right
		{
			tX = (int) (x + mazeCollisionBounds.x + mazeCollisionBounds.width + xMove) / Tile.TILEWIDTH;
			
			if(!isCollisionWithTile(tX,tupY) && !isCollisionWithTile(tX,tlowY) )
			{
				check = true;
			}
		}
		else if(xMove < 0)//Move Left
		{
			tX = (int) (x + mazeCollisionBounds.x + xMove) / Tile.TILEWIDTH;
			
			if(!isCollisionWithTile(tX,tupY) && !isCollisionWithTile(tX,tlowY) )
			{
				check = true;
			}
		}
		else
		{
			check = true;
		}
		
		return check;
	}
	
	public boolean isYMoveValid(float yMove)
	{
		boolean check = false;
		int trightX = (int) (x + mazeCollisionBounds.x + mazeCollisionBounds.width) / Tile.TILEWIDTH;
		int tleftX = (int) (x + mazeCollisionBounds.x) / Tile.TILEWIDTH;
		int tY;
		
		if(yMove > 0) //Move Down
		{
			tY = (int) (y + mazeCollisionBounds.y + mazeCollisionBounds.height + yMove) / Tile.TILEHEIGHT;
			
			if(!isCollisionWithTile(trightX,tY) && !isCollisionWithTile(tleftX,tY) )
			{
				check = true;
			}
		}
		else if(yMove < 0)//Move Up
		{
			tY = (int) (y + mazeCollisionBounds.y + yMove) / Tile.TILEHEIGHT;
			
			if(!isCollisionWithTile(trightX,tY) && !isCollisionWithTile(tleftX,tY) )
			{
				check = true;
			}
		}
		else
		{
			check = true;
		}
		
		return check;
	}
	
	public float getHValue(float currGhostX, float currGhostY, float destX, float destY)
	{
		return (float) Math.sqrt(Math.pow(currGhostX - destX, 2) + Math.pow(currGhostY - destY, 2));
	}
	
	public boolean collisionWithPlayer()
	{
		boolean check = false;
		
		if(handler.getWorld().getEntityManager().getPlayer().getCollisionBounds(0f, 0f).intersects(getCollisionBounds(0f,0f)))
		{
			check = true;
		}
		
		return check;
	}
	
	public BufferedImage getCurrentImageFrame()
	{
		if(xMove > 0 && !isDead)
		{
			return animMovement[0];
		}
		else if(yMove > 0 && !isDead)
		{
			return animMovement[1];
		}
		else if(xMove < 0 && !isDead)
		{
			return animMovement[2];
		}
		else if (yMove < 0 && !isDead)
		{
			return animMovement[3];
		}
		if(xMove > 0 && isDead)
		{
			return Assets.ghostEyes[0];
		}
		else if(yMove > 0 && isDead)
		{
			return Assets.ghostEyes[1];
		}
		else if(xMove < 0 && isDead )
		{
			return Assets.ghostEyes[2];
		}
		else if (yMove < 0 && isDead)
		{
			return Assets.ghostEyes[3];
		}
		else
		{
			return animMovement[0];
		}
	}
	
	public BufferedImage getCurrentAnimationFrame()
	{
		return animFlashing.getCurrentFrame();
	}
	
	
	//INNER CLASSES
	
	class ListCompare implements Comparator<List<Float>>
	{
		@Override
		public int compare(List<Float> o1, List<Float> o2) 
		{
			if(o1.get(4) < o2.get(4) || (o1.get(4) == o2.get(4) && o1.get(3) < o2.get(3))) //Sort by F value but 
																				   		   //if F value is equal sort by H value
				return -1;																   
			else if(o1.get(4) > o2.get(4))
				return 1;
			else
				return 0;
		}	
	}
	
	
	//GETTERS AND SETTERS
	
	public boolean isDisabled() 
	{
		return isFrightened;
	}

	public void setFrightened(boolean isFrightened) 
	{
		if((this.isFrightened && timer < DISABLED_DURATION) || isFlashing)
		{
			isFlashing = false;
			timer = 0;
			lastTime = 0;
		}
		this.isFrightened = isFrightened;
	}
	
	public boolean isFlashing() 
	{
		return isFlashing;
	}

	public void setFlashing(boolean isFlashing)
	{
		this.isFlashing = isFlashing;
	}

	public boolean isDead() 
	{
		return isDead;
	}

	public void setDead(boolean isDead) 
	{
		this.isDead = isDead;
	}
	
}