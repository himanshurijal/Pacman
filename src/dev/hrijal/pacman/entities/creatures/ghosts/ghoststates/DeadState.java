package dev.hrijal.pacman.entities.creatures.ghosts.ghoststates;

import java.awt.image.BufferedImage;

import dev.hrijal.pacman.entities.creatures.ghosts.Ghost;
import dev.hrijal.pacman.entities.creatures.ghosts.movement.DeadRunHome;

public class DeadState extends GhostState
{

	private BufferedImage[] movement;
	
	public DeadState(Ghost ghost, long duration, BufferedImage[] movementAssets)
	{
		super(ghost, duration);
		movement = movementAssets;
	}
	
	@Override
	public void checkTimer()
	{
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
		ghost.runToHome();
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
	
	@Override
	public void ghostCollisionWithPlayer()
	{
		//Do nothing
	}
	
	@Override
	public void playerCollisionWithCapsule() //Overridden in AtHomeState
	{
		//Do nothing
	}
	
}