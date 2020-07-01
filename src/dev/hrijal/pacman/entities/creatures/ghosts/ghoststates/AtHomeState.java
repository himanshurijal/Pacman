package dev.hrijal.pacman.entities.creatures.ghosts.ghoststates;

import java.awt.image.BufferedImage;

import dev.hrijal.pacman.entities.creatures.ghosts.Ghost;
import dev.hrijal.pacman.tiles.Tile;

public class AtHomeState extends GhostState
{
	
	private BufferedImage[] movement;
	
	public AtHomeState(Ghost ghost, long duration, BufferedImage[] movementAssets)
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
			ghost.setState(ghost.getScatteredState());
			resetTimer();
		}
	}

	@Override
	public void makeNextMove()
	{
		ghost.makeNextMove(Tile.TILEWIDTH * 11, Tile.TILEHEIGHT * 11, ghost.getSpeed());
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
