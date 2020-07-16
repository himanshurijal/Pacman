package dev.hrijal.pacman.entities.creatures.ghosts;

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
import dev.hrijal.pacman.entities.creatures.ghosts.movement.ChaseBehavior;
import dev.hrijal.pacman.entities.creatures.ghosts.movement.DeadBehavior;
import dev.hrijal.pacman.entities.creatures.ghosts.movement.FrightenedBehavior;
import dev.hrijal.pacman.entities.creatures.ghosts.movement.ScatterBehavior;
import dev.hrijal.pacman.entities.creatures.ghosts.states.AtHomeState;
import dev.hrijal.pacman.entities.creatures.ghosts.states.ChasingState;
import dev.hrijal.pacman.entities.creatures.ghosts.states.DeadState;
import dev.hrijal.pacman.entities.creatures.ghosts.states.FrightenedState;
import dev.hrijal.pacman.entities.creatures.ghosts.states.State;
import dev.hrijal.pacman.entities.creatures.ghosts.states.PauseState;
import dev.hrijal.pacman.entities.creatures.ghosts.states.ResetState;
import dev.hrijal.pacman.entities.creatures.ghosts.states.ScatteredState;
import dev.hrijal.pacman.entities.creatures.player.score.ScoreManager;
import dev.hrijal.pacman.gfx.Assets;

public class Ghost extends Creature
{
	
	//STATES
	private State atHomeState;
	private State scatteredState;
	private State chasingState;
	private State frightenedState;
	private State deadState;
	private State pauseState;
	private State resetState;
	
	private State currState;

	private State stateAfterFrightened; //Needed for storing the next state to transition to once ghosts exit FrightenedState
	private State stateAfterPause;		 //or PauseState.
	
	public static final long  SCATTERED_DURATION = 7000,
	   		  				  CHASING_DURATION = 20000,
							  FRIGHTENED_DURATION = 8000,  
					   		  FLASHING_DURATION = 2000,
					   		  RESET_DURATION = 1300;
	
	//MOVEMENT
	private float[] lastAdjNode;
	
	private ScatterBehavior scatterBehavior;
	private ChaseBehavior chaseBehavior;
	private FrightenedBehavior frightenedBehavior;
	private DeadBehavior deadBehavior;
	
	public Ghost(Handler handler, float x, float y, BufferedImage[] movementAssets)
	{
		super(handler, x, y);
			
		//States
		atHomeState = new AtHomeState(this, 0, movementAssets);
		scatteredState = new ScatteredState(this, SCATTERED_DURATION, movementAssets);
		chasingState = new ChasingState(this, CHASING_DURATION, movementAssets);
		frightenedState = new FrightenedState(this, FRIGHTENED_DURATION + FLASHING_DURATION, Assets.ghostFlashing);
		deadState = new DeadState(this, 0, Assets.ghostEyes);
		pauseState = new PauseState(this, ScoreManager.SCORE_DISPLAY_DURATION);
		resetState = new ResetState(this, RESET_DURATION);
		
		currState = atHomeState;
		
		stateAfterFrightened = null;
		stateAfterPause = null;
		
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
		currState.checkTransitionToNextState();
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
//		chaseBehavior.render(g);
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
	
	@Override
	public boolean isCollisionWithTile(int x, int y)
	{
		return handler.getWorld().getTile(y, x).isSolid(this);
	}

	public float getHValue(float currGhostX, float currGhostY, float destX, float destY)
	{
		return (float) Math.sqrt(Math.pow(currGhostX - destX, 2) + Math.pow(currGhostY - destY, 2));
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
				return - 1;																   
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

	public State getAtHomeState() 
	{
		return atHomeState;
	}

	public State getScatteredState() 
	{
		return scatteredState;
	}

	public State getChasingState()
	{
		return chasingState;
	}

	public State getFrightenedState() 
	{
		return frightenedState;
	}

	public State getDeadState()
	{
		return deadState;
	}
	
	public State getPauseState()
	{
		return pauseState;
	}
	
	public State getResetState()
	{
		return resetState;
	}

	public State getState()
	{
		return currState;
	}
	 
	public void setState(State state)
	{
		currState = state;
	}
	
	public State getStateAfterFrightened()
	{
		return stateAfterFrightened;
	}
	
	public void setStateAfterFrightened(State state)
	{
		this.stateAfterFrightened = state;
	}
	
	public State getStateAfterPause()
	{
		return stateAfterPause;
	}
	
	public void setStateAfterPause(State state)
	{
		this.stateAfterPause = state;
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