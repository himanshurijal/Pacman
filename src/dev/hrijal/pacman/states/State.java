package dev.hrijal.pacman.states; 

import java.awt.Graphics;

import dev.hrijal.pacman.Handler;

public abstract class State
{
	private static State currentState = null;
	
	protected Handler handler;
	
	public State(Handler handler)
	{
		this.handler = handler;
	}
	
	public abstract void tick();
	
	public abstract void render(Graphics g);
	
	
	//GETTERS AND SETTERS
	
	public static void setState(State state)
	{
		currentState = state;
	}
	
	public static State getState()
	{
		return currentState;
	}
	
}