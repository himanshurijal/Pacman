package dev.hrijal.pacman.entities.creatures.ghosts;

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
import dev.hrijal.pacman.entities.creatures.Creature;
import dev.hrijal.pacman.entities.creatures.ghosts.ghoststates.AtHomeState;
import dev.hrijal.pacman.entities.creatures.ghosts.ghoststates.ChasingState;
import dev.hrijal.pacman.entities.creatures.ghosts.ghoststates.DeadState;
import dev.hrijal.pacman.entities.creatures.ghosts.ghoststates.FrightenedState;
import dev.hrijal.pacman.entities.creatures.ghosts.ghoststates.GhostState;
import dev.hrijal.pacman.entities.creatures.ghosts.ghoststates.ScatteredState;
import dev.hrijal.pacman.entities.creatures.ghosts.movement.ChaseBehavior;
import dev.hrijal.pacman.entities.creatures.ghosts.movement.DeadBehavior;
import dev.hrijal.pacman.entities.creatures.ghosts.movement.FrightenedBehavior;
import dev.hrijal.pacman.entities.creatures.ghosts.movement.ScatterBehavior;
import dev.hrijal.pacman.gfx.Assets;
import dev.hrijal.pacman.tiles.Tile;

public class Ghost extends Creature
{
	
	//STATES
	private GhostState atHomeState;
	private GhostState scatteredState;
	private GhostState chasingState;
	private GhostState frightenedState;
	private GhostState deadState;
	
	private GhostState currState;
	
	private GhostState lastState;	//Needed for storing the last active state in case ghosts go into frightened state
	private long lastStateTimer;	//Once ghosts exit frightened state their state will be restored to the last state
	private long lastStateLastTime; //they were in.
	
	public static final long  SCATTERED_DURATION = 7000, //Calculated in milliseconds
	   		  				  CHASING_DURATION = 20000,
							  FRIGHTENED_DURATION = 8000,  
					   		  FLASHING_DURATION = 2000,
					   		  DEAD_DURATION = 100;
	
	//MOVEMENT
	private float[] lastAdjNode;
	
	private ScatterBehavior scatterBehavior;
	private ChaseBehavior chaseBehavior;
	private FrightenedBehavior frightenedBehavior;
	private DeadBehavior deadBehavior;
	
	public Ghost(Handler handler, float x, float y, BufferedImage[] movementAssets)
	{
		super(handler, x, y, 200);
			
		//States
		atHomeState = new AtHomeState(this, 0, movementAssets);
		scatteredState = new ScatteredState(this, SCATTERED_DURATION, movementAssets);
		chasingState = new ChasingState(this, CHASING_DURATION, movementAssets);
		frightenedState = new FrightenedState(this, FRIGHTENED_DURATION + FLASHING_DURATION, Assets.ghostFlashing);
		deadState = new DeadState(this, DEAD_DURATION, Assets.ghostEyes);
		
		currState = atHomeState;
		
		lastState = null;
		lastStateTimer = 0;
		lastStateLastTime = 0;
		
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
	}

	@Override
	public void tick() 
	{				
		currState.checkTimer();
		currState.makeNextMove();
	}

	@Override
	public void render(Graphics g) 
	{
		g.drawImage(getCurrentFrame(), (int) x, (int) y, Entity.ENTITY_WIDTH, Entity.ENTITY_HEIGHT, null);
		
//		g.setColor(Color.white);
//		g.drawRect((int) (mazeCollisionBounds.x + x), (int) (mazeCollisionBounds.y + y), 
//				   									 (int) mazeCollisionBounds.height, (int) mazeCollisionBounds.width);
//		g.drawRect((int) x + entityCollisionBounds.x, (int) y + entityCollisionBounds.y,
//													 entityCollisionBounds.width, entityCollisionBounds.height);
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
	
	@Override
	public boolean isCollisionWithTile(int x, int y)
	{
		return handler.getWorld().getTile(y, x).isSolid(this);
	}

	public float getHValue(float currGhostX, float currGhostY, float destX, float destY)
	{
		return (float) Math.sqrt(Math.pow(currGhostX - destX, 2) + Math.pow(currGhostY - destY, 2));
	}

	public void killPlayer()
	{
		handler.getWorld().getPlayer().setDead(true);
	}
	
	public void scatter()
	{
		scatterBehavior.scatter();
	}
	
	public void runAway()
	{
		frightenedBehavior.runAway();
	}
	
	public void chase()
	{
		chaseBehavior.chase();
	}
	
	public void runToHome()
	{
		deadBehavior.runToHome();
	}
	
	public BufferedImage getCurrentFrame()
	{
		return currState.getCurrentFrame();
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
	
	//STATES
	
	public void setAtHomeDuration(long duration)
	{
		atHomeState.setDuration(duration);
	}

	public GhostState getAtHomeState() 
	{
		return atHomeState;
	}

	public GhostState getScatteredState() 
	{
		return scatteredState;
	}

	public GhostState getChasingState()
	{
		return chasingState;
	}

	public GhostState getFrightenedState() 
	{
		return frightenedState;
	}

	public GhostState getDeadState()
	{
		return deadState;
	}

	public GhostState getState()
	{
		return currState;
	}
	 
	public void setState(GhostState state)
	{
		currState = state;
	}
	
	public GhostState getLastState()
	{
		return lastState;
	}
	
	public void setLastState(GhostState lastState)
	{
		this.lastState = lastState;
	}
	
	public long getLastStateTimer() 
	{
		return lastStateTimer;
	}

	public void setLastStateTimer(long lastStateTimer)
	{
		this.lastStateTimer = lastStateTimer;
	}

	public long getLastStateLastTime() 
	{
		return lastStateLastTime;
	}

	public void setLastStateLastTime(long lastStateLastTime)
	{
		this.lastStateLastTime = lastStateLastTime;
	}
	
	//MOVEMENT
	
	public void setScatterBehavior(ScatterBehavior scatterBehavior) 
	{
		this.scatterBehavior = scatterBehavior;
	}

	public void setChaseBehavior(ChaseBehavior chaseBehavior)
	{
		this.chaseBehavior = chaseBehavior;
	}
	
	public void setFrightenedBehavior(FrightenedBehavior frightenedBehavior) 
	{
		this.frightenedBehavior = frightenedBehavior;
	}

	public void setDeadBehavior(DeadBehavior deadBehavior)
	{
		this.deadBehavior = deadBehavior;
	}	

}