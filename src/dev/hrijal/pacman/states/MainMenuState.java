package dev.hrijal.pacman.states;

import java.awt.Color;
import java.awt.Graphics;

import dev.hrijal.pacman.Handler;

public class MainMenuState extends State
{
	public MainMenuState(Handler handler)
	{
		super(handler);
	}
	
	@Override
	public void tick() 
	{
		if(handler.getGame().getMouseManager().isLeftPressed())
		{
			//GameState gameState = new GameState(handler);
			//handler.getGame().setGameState(gameState);
			State.setState(handler.getGame().getGameState());
		}
	}

	@Override
	public void render(Graphics g) 
	{
		g.setColor(Color.BLUE);
		g.drawRect(250, 250, 30, 30);
		g.drawRect(280, 250, 30, 30);
		g.drawRect(310, 250, 30, 30);
	}
}