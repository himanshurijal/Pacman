package dev.hrijal.pacman.worlds.states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import dev.hrijal.pacman.tiles.Tile;
import dev.hrijal.pacman.worlds.World;

public class ReadyState extends State
{

	public ReadyState(World world, long duration)
	{
		super(world, duration);
	}
	

	@Override
	public void tick() 
	{
		//No movement of objects in this state. 
		//Players are allowed time to get ready to play when the world is in this state.
	}

	@Override
	public void checkTransitionToNextState()
	{
		currStateTimer.incrementTimer();
		
		if(currStateTimer.isTimerExpired())
		{
			currStateTimer.resetTimer();
			
			world.setCurrentState(world.getPlayingState());
		}
	}
	
	@Override
	public void render(Graphics g) 
	{
		world.renderWorldEnvironment(g);
		world.renderWorldObjects(g);
		
		g.setColor(Color.YELLOW);
		g.setFont(new Font("SansSerif", Font.BOLD, 27));
		g.drawString("Ready!", Tile.TILEWIDTH * 10, Tile.TILEHEIGHT * 14 - 5);
	}

}
