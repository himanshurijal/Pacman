package dev.hrijal.pacman.entities.creatures.ghosts.states;

import java.awt.image.BufferedImage;

import dev.hrijal.pacman.entities.creatures.ghosts.Ghost;
import dev.hrijal.pacman.gfx.Assets;

public class PauseState extends GhostState
{
	
	public PauseState(Ghost ghost, long duration)
	{
		super(ghost, duration);
	}

	@Override
	public void checkTransitionToNextState()
	{	
		currStateTimer.incrementTimer();
		
		if(currStateTimer.isTimerExpired())
		{			
			ghostDead = false;
								
			currStateTimer.resetTimer();
		
			ghost.getStateAfterPause().setLastTime(System.currentTimeMillis());
			ghost.setState(ghost.getStateAfterPause());
		}
	}

	@Override
	public void makeNextMove() 
	{
		//No movement in this state
	}

	@Override
	public BufferedImage getCurrentFrame()
	{
		GhostState stateAfterPause = ghost.getStateAfterPause();
		
		if(stateAfterPause instanceof DeadState)
		{
			return Assets.empty;
		}
		else
		{
			return stateAfterPause.getCurrentFrame();
		}
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
