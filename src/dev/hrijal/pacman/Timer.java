package dev.hrijal.pacman;

public class Timer implements Runnable
{
	//TODO: Use threads to make it so that the caller doesn't need to increment timer themselves
	private boolean timerReady; //Flag to check if timer is ready to be incremented by the called
	
	private long duration;
	private long timer;
	private long lastTime;
	
	public Timer(long duration)
	{
		timerReady = false;
		
		this.duration = duration;
		timer = 0;
		lastTime = 0;
	}
	
	public synchronized void readyTimer()
	{		
		if(timerReady)
		{
			return;
		}
		
		timerReady = true;
	}

	public void incrementTimer()
	{
		if(timerReady)
		{
			if(lastTime == 0)
				lastTime = System.currentTimeMillis();
			
			timer += System.currentTimeMillis() - lastTime;
			lastTime = System.currentTimeMillis();
		}
		else
		{
			return;
		}
	}
	
	public synchronized void resetTimer()
	{
		if(!timerReady)
		{
			return;
		}
				
		timerReady = false;
		
		timer = 0;
		lastTime = 0;
	}

	public boolean isTimerExpired()
	{
		if(timerReady && timer >= duration)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	@Override
	public void run()
	{
		while(timerReady && timer < duration)
		{
			incrementTimer();
		}
	}
	
	
	//GETTERS AND SETTERS
	
	public boolean isTimerReady() 
	{
		return timerReady;
	}
	
	public long getDuration()
	{
		return duration;
	}
	
	public void setDuration(long duration)
	{
		this.duration = duration;
	}
	
	public long getTimer() 
	{
		return timer;
	}

	public void setTimer(long timer) 
	{
		this.timer = timer;
	}
	
	public long getLastTime()
	{
		return lastTime;
	}
	
	public void setLastTime(long lastTime)
	{
		this.lastTime = lastTime;
	}
	
}
