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
	public void checkTimer()
	{
		incrementTimer();
		
		if(timer >= duration)
		{
			resetTimer();
			ghost.setState(ghost.getChasingState());
		}
	}
	
	@Override
	public void makeNextMove()
	{
		ghost.scatter();
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
