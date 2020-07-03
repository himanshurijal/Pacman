package dev.hrijal.pacman.states; 

import java.awt.Graphics;
import java.awt.Rectangle;

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
	
	public boolean buttonPressed(Rectangle button)
	{
		boolean check = false;
		
		int mouseX = handler.getGame().getMouseManager().getMouseX();
		int mouseY = handler.getGame().getMouseManager().getMouseY();
		boolean mousePressed = handler.getGame().getMouseManager().isLeftPressed();
		
		if(mousePressed)
		{
			if(mouseX >= button.x && mouseX <= button.x + button.width)
			{
				if(mouseY >= button.y && mouseY <= button.y + button.height)
				{
					check = true;
				}
			}
		}
		
		return check;
	}
	
	public boolean buttonHovered(Rectangle button)
	{
		boolean check = false;
		
		int mouseX = handler.getGame().getMouseManager().getMouseX();
		int mouseY = handler.getGame().getMouseManager().getMouseY();
		
		if(mouseX >= button.x && mouseX <= button.x + button.width)
		{
			if(mouseY >= button.y && mouseY <= button.y + button.height)
			{
				check = true;
			}
		}
		
		return check;
	}
	
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