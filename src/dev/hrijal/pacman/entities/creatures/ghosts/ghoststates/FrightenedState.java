package dev.hrijal.pacman.entities.creatures.ghosts.ghoststates;

import java.awt.image.BufferedImage;

import dev.hrijal.pacman.entities.creatures.ghosts.Ghost;
import dev.hrijal.pacman.gfx.Animation;
import dev.hrijal.pacman.tiles.Tile;

public class FrightenedState extends GhostState
{
	
	private BufferedImage movement;
	private Animation animFlashing;
	
	public FrightenedState(Ghost ghost, long duration, BufferedImage[] movementAssets)
	{
		super(ghost, duration);
		movement = movementAssets[0];
		animFlashing = new Animation(movementAssets, 100);
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
				
				adjustGhostXY(); 
				
				ghost.getLastState().setTimer(ghost.getLastStateTimer());
				ghost.getLastState().setLastTime(System.currentTimeMillis());
				ghost.setState(ghost.getLastState());
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
			ghost.runAway();
		}
	}
	
	@Override
	public BufferedImage getCurrentFrame()
	{
		if(currStateTimer.getTimer() < currStateTimer.getDuration() - Ghost.FLASHING_DURATION) 
		{
			return movement;
		}
		else //Run the flashing animation 2 seconds before the ghost is about to exit frightened mode
		{  
			animFlashing.tick();
			return animFlashing.getCurrentFrame();
		}
	}
	
	@Override
	public void playerCollisionWithCapsule()
	{
		currStateTimer.resetTimer();
		ghost.setState(ghost.getFrightenedState());
	}
	
	@Override
	public void ghostCollisionWithPlayer()
	{
		currStateTimer.resetTimer();
		
		movementPauseTimer.readyTimer();
		
		adjustGhostXY();
		
		ghost.setState(ghost.getDeadState());
	}
	
	public void adjustGhostXY() //We need to adjust ghosts' XY coordinates to the nearest tile coordinates
								//to make sure collision detection works properly (since speed is halved
								//when ghosts are frightened)
	{
		float adjustedX = Math.round(ghost.getX() / Tile.TILEWIDTH) * Tile.TILEWIDTH;
		float adjustedY = Math.round(ghost.getY() / Tile.TILEHEIGHT) * Tile.TILEHEIGHT;
		
		ghost.setX(adjustedX);
		ghost.setY(adjustedY);
	}
	
}
