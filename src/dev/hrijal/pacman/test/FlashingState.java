package dev.hrijal.pacman.test;

import java.awt.image.BufferedImage;

import dev.hrijal.pacman.entities.creatures.ghosts.Ghost;
import dev.hrijal.pacman.entities.creatures.ghosts.ghoststates.GhostState;
import dev.hrijal.pacman.gfx.Animation;
import dev.hrijal.pacman.tiles.Tile;

public class FlashingState extends GhostState
{
	
	private Animation animFlashing;
	
	public FlashingState(Ghost ghost, long duration, BufferedImage[] flashingAssets)
	{
		super(ghost, duration);
		animFlashing = new Animation(flashingAssets, 100);
	}

	@Override
	public void checkTimer()
	{
		incrementTimer();
		
		if(timer >= duration)
		{
			resetTimer();
			
			float adjustedX = Math.round(ghost.getX() / Tile.TILEWIDTH) * Tile.TILEWIDTH;
			float adjustedY = Math.round(ghost.getY() / Tile.TILEHEIGHT) * Tile.TILEHEIGHT;
			
			ghost.setX(adjustedX);
			ghost.setY(adjustedY);
			
			ghost.setState(ghost.getChasingState());
		}
	}
	
	@Override
	public void makeNextMove()
	{
		ghost.runAway();
	}
	
	@Override
	public BufferedImage getCurrentFrame()
	{
		animFlashing.tick();
		return animFlashing.getCurrentFrame();
	}
	
	@Override
	public void ghostCollisionWithPlayer()
	{
		resetTimer();
		
		float adjustedX = Math.round(ghost.getX() / Tile.TILEWIDTH) * Tile.TILEWIDTH;
		float adjustedY = Math.round(ghost.getY() / Tile.TILEHEIGHT) * Tile.TILEHEIGHT;
		
		ghost.setX(adjustedX);
		ghost.setY(adjustedY);
		
		ghost.setState(ghost.getDeadState());
	}
	
}
