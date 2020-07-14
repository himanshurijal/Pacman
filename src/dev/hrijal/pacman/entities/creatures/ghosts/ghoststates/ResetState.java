package dev.hrijal.pacman.entities.creatures.ghosts.ghoststates;

import java.awt.image.BufferedImage;

import dev.hrijal.pacman.entities.creatures.ghosts.Ghost;
import dev.hrijal.pacman.gfx.Assets;

public class ResetState extends GhostState
{

	public ResetState(Ghost ghost, long duration)
	{
		super(ghost, duration);
	}

	@Override
	public void checkTransitionToNextState()
	{		
		if(currStateTimer.isTimerReady())
		{
			currStateTimer.incrementTimer();
			
			if(currStateTimer.isTimerExpired())
			{
				playerDead = false;
				
				currStateTimer.resetTimer();
				
				ghost.setX(ghost.getSpawnX());
				ghost.setY(ghost.getSpawnY());
				ghost.setState(ghost.getAtHomeState());
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
		//No Movement in this state
	}

	@Override
	public BufferedImage getCurrentFrame() 
	{
		return Assets.empty;
	}

	@Override
	public void playerCollisionWithCapsule()
	{
		//Do nothing
	}
	
	@Override
	public void ghostCollisionWithPlayer()
	{
		//Do nothing
	}
}
