package dev.hrijal.pacman.entities.creatures.ghosts.states;

import java.awt.image.BufferedImage;

import dev.hrijal.pacman.entities.creatures.ghosts.Ghost;
import dev.hrijal.pacman.gfx.Assets;

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
		if(ghostDead)
		{
			ghost.setStateAfterPause(ghost.getState());
			ghost.setState(ghost.getPauseState());
		}
		else if(playerDead)
		{
			ghost.setStateAfterPause(ghost.getResetState());
			ghostsInResetStateCount++;
			ghost.setState(ghost.getPauseState());
		}
		else
		{
			currStateTimer.incrementTimer();
			
			if(currStateTimer.isTimerExpired())
			{
				currStateTimer.resetTimer();
				ghost.setState(ghost.getChasingState());
			}
		}
	}
	
	@Override
	public void makeNextMove()
	{
		if(ghostsInResetStateCount == 0) //Only move if all ghosts have exited ResetState. Ghosts might be in
										 //this state after ResetState as their AtHomeState timer might have run
										 //out before their state was set to ResetState
		{
			ghost.scatter();
		}
	}
	
	@Override
	public BufferedImage getCurrentFrame() 
	{
		if(playerDeathCount == 3)
		{
			return Assets.empty;
		}
		else if(ghost.getxMove() > 0)
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
