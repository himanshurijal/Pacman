package dev.hrijal.pacman.entities.creatures.ghosts.ghoststates;

import java.awt.image.BufferedImage;

import dev.hrijal.pacman.Timer;
import dev.hrijal.pacman.entities.creatures.ghosts.Ghost;
import dev.hrijal.pacman.entities.creatures.player.score.ScoreManager;

public abstract class GhostState 
{
	
	protected Ghost ghost;
//	protected long duration; //Duration after which ghosts will transition to next state
//	protected long timer;
//	protected long lastTime;
	
	protected Timer currStateTimer;
	protected static Timer movementPauseTimer;
	
	public GhostState(Ghost ghost, long duration)
	{
		this.ghost = ghost;
//		this.duration = duration;
//		timer =  0;
//		lastTime = 0;
		
		currStateTimer = new Timer(duration);
		movementPauseTimer = new Timer(ScoreManager.SCORE_DISPLAY_DURATION);
	}
	
	public abstract void checkTransitionToNextState();
	
	public abstract void makeNextMove();
	
	public abstract BufferedImage getCurrentFrame();
	
	public void playerCollisionWithCapsule() //Overridden in FrightenedState and DeadState
	{
		ghost.setLastState(ghost.getState());
//		ghost.setLastStateTimer(timer);
		ghost.setLastStateTimer(currStateTimer.getTimer());

//		resetTimer();
		currStateTimer.resetTimer();
		
		ghost.setState(ghost.getFrightenedState());
	}

	public void ghostCollisionWithPlayer() //Overridden in FrightenedState
	{
		//Do nothing
	}
//	
//	public void incrementTimer()
//	{
//		if(lastTime == 0)
//			lastTime = System.currentTimeMillis();
//		
//		timer += System.currentTimeMillis() - lastTime;
//		lastTime = System.currentTimeMillis();
//	}
//	
//	public void resetTimer()
//	{
//		timer = 0;
//		lastTime = 0;
//	}
	
	
	//GETTERS AND SETTERS
	
	public void setDuration(long duration)
	{
//		this.duration = duration;
		currStateTimer.setDuration(duration);
	}

	public void setTimer(long timer)
	{
//		this.timer = timer;
		currStateTimer.setTimer(timer);
	}

	public void setLastTime(long lastTime)
	{
//		this.lastTime = lastTime;
		currStateTimer.setLastTime(lastTime);
	}
	
}
