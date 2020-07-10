package dev.hrijal.pacman.entities.creatures.ghosts.ghoststates;

import java.awt.image.BufferedImage;

import dev.hrijal.pacman.entities.creatures.ghosts.Ghost;

public class ScatteredState extends GhostState
{

	private BufferedImage[] movement;
	
	public ScatteredState(Ghost ghost, long duration, BufferedImage[] movementAssets)
	{
		super(ghost, duration);
		movement = movementAssets;
	}
	
	@Override
	public void checkTransitionToNextState()
	{
		if(currStateTimer.isTimerReady())
		{
			currStateTimer.incrementTimer();
			
			if(currStateTimer.isTimerExpired())
			{
				currStateTimer.resetTimer();
				ghost.setState(ghost.getChasingState());
			}
		}
		else
		{
			currStateTimer.readyTimer();
		}
	}
	
	@Override
	public void makeNextMove()
	{
		if(!movementPauseTimer.isTimerReady()) //If the timer to pause movement hasn't been started
		{
			ghost.scatter();
		}
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
