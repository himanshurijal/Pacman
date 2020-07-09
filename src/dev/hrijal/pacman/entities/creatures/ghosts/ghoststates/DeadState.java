package dev.hrijal.pacman.entities.creatures.ghosts.ghoststates;

import java.awt.image.BufferedImage;

import dev.hrijal.pacman.entities.creatures.ghosts.Ghost;
import dev.hrijal.pacman.entities.creatures.ghosts.movement.DeadRunHome;
import dev.hrijal.pacman.gfx.Assets;

public class DeadState extends GhostState
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
//		if(ghost.getX() == DeadRunHome.DEST_X && ghost.getY() == DeadRunHome.DEST_Y)
//		{			
//			ghost.getLastState().setTimer(ghost.getLastStateTimer());
//			ghost.getLastState().setLastTime(System.currentTimeMillis());
//			ghost.setState(ghost.getLastState());
//		}
		
		if(movementPauseTimer.isTimerReady())
		{
			movementPauseTimer.incrementTimer();
			
			if(movementPauseTimer.isTimerExpired())
			{
				movementPauseTimer.resetTimer();
			}
		}
		
		if(ghost.getX() == DeadRunHome.DEST_X && ghost.getY() == DeadRunHome.DEST_Y)
		{			
			ghost.getLastState().setTimer(ghost.getLastStateTimer());
			ghost.getLastState().setLastTime(System.currentTimeMillis());
			ghost.setState(ghost.getLastState());
		}
	}
	
	@Override
	public void makeNextMove()
	{
//		long pauseTimer = ghost.getHandler().getWorld().getScoreManager().getTimer();
//		
//		if(pauseTimer == 0)
//		{
//			ghost.runToHome();
//		}
		
		
		if(!movementPauseTimer.isTimerReady()) //If the timer to pause movement hasn't been started
		{
			ghost.runToHome();
		}
	}
	
	@Override
	public BufferedImage getCurrentFrame() 
	{
//		long pauseTimer = ghost.getHandler().getWorld().getScoreManager().getTimer();
//		
//		if(pauseTimer != 0)
//		{
//			return Assets.empty;
//		}
		
		if(movementPauseTimer.isTimerReady()) //If the timer to pause movement hasn't been started
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
	
}
