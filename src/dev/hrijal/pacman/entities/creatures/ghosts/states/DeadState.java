package dev.hrijal.pacman.entities.creatures.ghosts.states;

import java.awt.image.BufferedImage;

import dev.hrijal.pacman.entities.creatures.ghosts.Ghost;
import dev.hrijal.pacman.entities.creatures.ghosts.movement.DeadRunHome;
import dev.hrijal.pacman.gfx.Assets;

public class DeadState extends State
{

	private BufferedImage[] movement;
	
	public DeadState(Ghost ghost, long duration, BufferedImage[] movementAssets)
	{
		super(ghost, duration);
		movement = movementAssets;
	}
	
	@Override
	public void checkTransitionToNextState()
	{		
		if(playerDead)
		{
			ghost.setStateAfterPause(ghost.getResetState());
			ghostsInResetStateCount++;
			ghost.setState(ghost.getPauseState());
		}
		
		if(ghost.getX() == DeadRunHome.DEST_X && ghost.getY() == DeadRunHome.DEST_Y)
		{			
			ghost.getStateAfterFrightened().setLastTime(System.currentTimeMillis());
			ghost.setState(ghost.getStateAfterFrightened());
		}
	}
	
	@Override
	public void makeNextMove()
	{
		ghost.runToHome(); //No checks for ghostsInResetStateCount in ResetState because they can only be in
						   //AtHomeState, ChasingState, or ScatteredState after leaving ResetState.
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
