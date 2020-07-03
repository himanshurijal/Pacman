package dev.hrijal.pacman.entities.creatures.ghosts.ghoststates;

import java.awt.image.BufferedImage;

import dev.hrijal.pacman.entities.creatures.ghosts.Ghost;

public abstract class GhostState 
{
	
	protected Ghost ghost;
	protected long duration;
	protected long timer;
	protected long lastTime;
	
	public GhostState(Ghost ghost, long duration)
	{
		this.ghost = ghost;
		this.duration = duration;
		timer =  0;
		lastTime = 0;
	}
	
	public abstract void checkTimer();
	
	public abstract void makeNextMove();
	
	public abstract BufferedImage getCurrentFrame();
	
	public void incrementTimer()
	{
		if(lastTime == 0)
			lastTime = System.currentTimeMillis();
		
		timer += System.currentTimeMillis() - lastTime;
		lastTime = System.currentTimeMillis();
	}
	
	public void resetTimer()
	{
		timer = 0;
		lastTime = 0;
	}
	
	public void playerCollisionWithCapsule() //Overridden in FrightenedState
	{
		ghost.setLastState(ghost.getState());
		ghost.setLastStateTimer(timer);

		resetTimer();
		
		ghost.setState(ghost.getFrightenedState());
	}

	public void ghostCollisionWithPlayer() //Overridden in FrightenedState and DeadState
	{
		ghost.killPlayer();
	}
	
	
	//GETTERS AND SETTERS
	
	public void setDuration(long duration)
	{
		this.duration = duration;
	}

	public void setTimer(long timer)
	{
		this.timer = timer;
	}

	public void setLastTime(long lastTime)
	{
		this.lastTime = lastTime;
	}
	
}