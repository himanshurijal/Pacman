package dev.hrijal.pacman.worlds.states;

import java.awt.Graphics;

import dev.hrijal.pacman.Timer;
import dev.hrijal.pacman.worlds.World;

public abstract class State
{
	
	protected World world;
	
	protected Timer currStateTimer;
	
	public State(World world, long duration)
	{
		this.world = world;
		
		currStateTimer = new Timer(duration);
	}
	
	public abstract void checkTransitionToNextState();
	
	public abstract void tick();
	
	public abstract void render(Graphics g);
	
}
