package dev.hrijal.pacman.entities.creatures.ghosts.states;

import java.awt.image.BufferedImage;

import dev.hrijal.pacman.entities.creatures.ghosts.Ghost;
import dev.hrijal.pacman.tiles.Tile;

public class ChasingState extends GhostState
{
	
	private BufferedImage[] movement;
	private int switchedStateCount;
	
	public ChasingState(Ghost ghost, long duration, BufferedImage[] movementAssets)
	{
		super(ghost, duration);
		movement = movementAssets;
		switchedStateCount = 0;
	}
	
	@Override
	public void checkTransitionToNextState()
	{
		if(ghostDead)
		{
			ghost.setStateAfterPause(ghost.getState());
			ghost.setState(ghost.getPauseState());
		}
		else if(playerDead)
		{
			ghost.setStateAfterPause(ghost.getResetState());
			ghost.setState(ghost.getPauseState());
		}
		else
		{
			if(switchedStateCount <= 4) //Switch to scattered state after chasing duration limit has been reached
			{
				currStateTimer.incrementTimer();
				
				if(currStateTimer.isTimerExpired())
				{
					switchedStateCount++;
					currStateTimer.resetTimer();
					ghost.setState(ghost.getScatteredState());
				}
			}
			else
			{
				//Once state has been switched to scattered state for four times remain in chasing state indefinitely
			}
		}
	}
	
	@Override
	public void makeNextMove()
	{
		ghost.chase();
	}
	
	@Override
	public BufferedImage getCurrentFrame() 
	{
		if(ghost.getxMove() > 0)
		{
			return movement[0];
		}
		else if(ghost.getyMove() > 0)
		{
			return movement[1];
		}
		else if(ghost.getxMove() < 0)
		{
			return movement[2];
		}
		else if (ghost.getyMove() < 0)
		{
			return movement[3];
		}
		else
		{
			return movement[0];
		}
	}
	
}
