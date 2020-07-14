package dev.hrijal.pacman.entities.creatures.ghosts.ghoststates;

import java.awt.image.BufferedImage;

import dev.hrijal.pacman.Timer;
import dev.hrijal.pacman.entities.creatures.ghosts.Ghost;

public abstract class GhostState 
{
	
	protected Ghost ghost;

	//COLLISION FLAGS
	protected static boolean ghostDead;
	protected static boolean playerDead;
	
	//TIMERS
	protected Timer currStateTimer; //This timer will determine how long ghosts will stay in their current state
	
	public GhostState(Ghost ghost, long duration)
	{
		this.ghost = ghost;

		//Collision Flags
		ghostDead = false;
		playerDead = false;
		
		//Timers
		currStateTimer = new Timer(duration);
	}
	
	public abstract void checkTransitionToNextState();
	
	public abstract void makeNextMove();
	
	public abstract BufferedImage getCurrentFrame();
	
	public void playerCollisionWithCapsule() //Overridden in FrightenedState, DeadState, PauseState, ResetState
	{
		ghost.setStateAfterFrightened(ghost.getState());
		
		ghost.setState(ghost.getFrightenedState());
	}

	public void ghostCollisionWithPlayer() //Overridden in FrightenedState, DeadState, PauseState, ResetState
	{
		playerDead = true;
		
		ghost.setStateAfterPause(ghost.getResetState());

		ghost.setState(ghost.getPauseState());
	}
	
	
	//GETTERS AND SETTERS

	public static boolean isPlayerDead() {
		return playerDead;
	}

	public void setDuration(long duration)
	{
		currStateTimer.setDuration(duration);
	}

	public void setTimer(long timer)
	{
		currStateTimer.setTimer(timer);
	}

	public void setLastTime(long lastTime)
	{
		currStateTimer.setLastTime(lastTime);
	}
	
}
