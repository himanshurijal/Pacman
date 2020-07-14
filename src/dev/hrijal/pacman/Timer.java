package dev.hrijal.pacman;

public class Timer implements Runnable
{
	//TODO: Use threads to make it so that the caller doesn't need to increment timer themselves
	
	private long duration;
	private long timer;
	private long lastTime;
	
	public Timer(long duration)
	{
		this.duration = duration;
		timer = 0;
		lastTime = 0;
	}

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

	public boolean isTimerExpired()
	{
		if(timer >= duration)
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
		while(timer < duration)
		{
			incrementTimer();
		}
	}
	
	
	//GETTERS AND SETTERS
	
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
