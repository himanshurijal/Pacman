package dev.hrijal.pacman.entities.creatures.ghosts.ghoststates;

import java.awt.image.BufferedImage;

import dev.hrijal.pacman.Timer;
import dev.hrijal.pacman.entities.creatures.ghosts.Ghost;
import dev.hrijal.pacman.entities.creatures.player.score.ScoreManager;

public abstract class GhostState 
{
	
	protected Ghost ghost;

	//TIMERS
	protected Timer currStateTimer; //This timer will determine how long ghosts will stay in their current state
	protected static Timer movementPauseTimer; //This timer will determine how long ghosts will pause their movement.
											   //It will only start if a ghosts is "eaten" in FrightenedState.
	
	public GhostState(Ghost ghost, long duration)
	{
		this.ghost = ghost;

		//Timers
		currStateTimer = new Timer(duration);
		movementPauseTimer = new Timer(ScoreManager.SCORE_DISPLAY_DURATION);
	}
	
	public abstract void checkTransitionToNextState();
	
	public abstract void makeNextMove();
	
	public abstract BufferedImage getCurrentFrame();
	
	public void playerCollisionWithCapsule() //Overridden in FrightenedState and DeadState
	{
		ghost.setLastState(ghost.getState());
		ghost.setLastStateTimer(currStateTimer.getTimer());

		currStateTimer.resetTimer();
		
		ghost.setState(ghost.getFrightenedState());
	}

	public void ghostCollisionWithPlayer() //Overridden in FrightenedState
	{
		//Do nothing
	}
	
	
	//GETTERS AND SETTERS
	
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
