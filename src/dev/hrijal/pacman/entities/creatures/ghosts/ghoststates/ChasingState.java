package dev.hrijal.pacman.entities.creatures.ghosts.ghoststates;

import java.awt.image.BufferedImage;

import dev.hrijal.pacman.entities.creatures.ghosts.Ghost;

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
	public void checkTimer()
	{
		if(switchedStateCount <= 4) //Switch to scattered state after chasing duration limit has been reached
		{
			incrementTimer();
			
			if(timer >= duration)
			{
				switchedStateCount++;
				resetTimer();
				ghost.setState(ghost.getScatteredState());
			}
		}
		else
		{
			//Once state has been switched to scattered state for four times remain in chasing state indefinitely
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
