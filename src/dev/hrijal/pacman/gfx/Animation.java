package dev.hrijal.pacman.gfx;

import java.awt.image.BufferedImage;

public class Animation 
{
	private BufferedImage[] frames;
	private int index, speed;
	private long timer, lastTime;
	
	public Animation(BufferedImage[] frames, int speed)
	{
		this.speed = speed;
		this.frames = frames;
		this.index = 0; 
		this.timer = 0;
		lastTime = System.currentTimeMillis();
	}
	
	public void tick()
	{
		timer += System.currentTimeMillis() - lastTime; //Get time past between calling the 
														//current tick method and the last tick method
		lastTime = System.currentTimeMillis();
		
		if(timer > speed)
		{
			index++;
			timer = 0;
			
			if(index >= frames.length)
			{
				index = 0;
			}
		}
	}
	
	public BufferedImage getCurrentFrame()
	{
		return frames[index];
	}
}
