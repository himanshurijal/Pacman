package dev.hrijal.pacman.worlds.states;

import java.awt.Graphics;
import dev.hrijal.pacman.worlds.World;

public class ResetState extends State
{

	public ResetState(World world, long duration)
	{
		super(world, duration);
	}


	@Override
	public void tick() 
	{
		world.tickWorldComponents();
	}
	
	@Override
	public void checkTransitionToNextState()
	{
		currStateTimer.incrementTimer();
		
		if(currStateTimer.isTimerExpired())
		{
			currStateTimer.resetTimer();
			
			if(((PlayingState) world.getPlayingState()).getPlayerDeathCount() == 3)
			{
				world.setCurrentState(world.getPlayingState());
			}
			else
			{
				world.setCurrentState(world.getReadyState());
			}
		}
	}
	
	@Override
	public void render(Graphics g) 
	{
		world.renderWorldEnvironment(g);
		world.renderWorldObjects(g);
	}

}
