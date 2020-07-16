package dev.hrijal.pacman.entities.creatures.ghosts.states;

import java.awt.image.BufferedImage;

import dev.hrijal.pacman.Timer;
import dev.hrijal.pacman.entities.creatures.ghosts.Ghost;

public abstract class State 
{
	
	protected Ghost ghost;
	
	//COLLISION FLAGS
	protected static boolean ghostDead;
	protected static boolean playerDead;
	
	//TIMERS
	protected Timer currStateTimer; //This timer will determine how long ghosts will stay in their current state.
	
	protected static int ghostsInResetStateCount = 0; //We need this count to ensure ghosts only makeNextMove
													  //once all the ghosts exit ResetState.
	protected static int playerDeathCount = 0; //We need this count to ensure ghosts don't render on screen
											 //once the game is over. (We do stop rendering ghosts in the world 
											 //from World.ResetState but the timers aren't working accurately.)
	
	public State(Ghost ghost, long duration)
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
	
	public void playerCollisionWithCapsule() //Overridden in FrightenedState, DeadState, PauseState, ResetState.
	{
		ghost.setStateAfterFrightened(ghost.getState());
		
		ghost.setState(ghost.getFrightenedState());
	}

	public void ghostCollisionWithPlayer() //Overridden in FrightenedState, DeadState, PauseState, ResetState.
	{
		playerDead = true;
		playerDeathCount++;
		
		ghost.setStateAfterPause(ghost.getResetState());
		ghostsInResetStateCount++;

		ghost.setState(ghost.getPauseState());
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
