package dev.hrijal.pacman.states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import dev.hrijal.pacman.Handler;

public class MainMenuState extends State
{
	
	private Rectangle playButton;
	private Rectangle helpButton;
	private Rectangle quitButton;
	
	public MainMenuState(Handler handler)
	{
		super(handler);
		
		playButton = new Rectangle(handler.getGame().getWidth() / 3 + 10, 250, 200, 50);
		helpButton = new Rectangle(handler.getGame().getWidth() / 3 + 10, 350, 200, 50);
		quitButton = new Rectangle(handler.getGame().getWidth() / 3 + 10, 450, 200, 50);
	}
	
	@Override
	public void tick() 
	{
		if(buttonPressed(playButton))
		{
			State.setState(handler.getGame().getGameState());
		}
		
		if(buttonPressed(helpButton))
		{
			State.setState(handler.getGame().getHelpState());
		}
		
		if(buttonPressed(quitButton))
		{
			State.setState(handler.getGame().getQuitState());
		}
	}

	@Override
	public void render(Graphics g) 
	{		
		Graphics2D g2d = (Graphics2D) g;
		
		Font fnt0 = new Font("SansSerif", Font.BOLD, 50);
		Font fnt1 = new Font("SansSerif", Font.BOLD, 30);
		
		g.setFont(fnt0);
		g.setColor(Color.RED);
		
		g.drawString("PACMAN", handler.getGame().getWidth() / 3, 150);
		
		g.setFont(fnt1);
		g.setColor(Color.WHITE);

		//Play Button
		g.setColor(Color.WHITE);
		
		if(buttonHovered(playButton))
		{
			g.setColor(Color.gray);
		}
		
		g.drawString("Play", playButton.x + 70, playButton.y + 35);
		g2d.draw(playButton);
		
		//Settings Button
		g.setColor(Color.WHITE);
		
		if(buttonHovered(helpButton))
		{
			g.setColor(Color.gray);
		}

		g.drawString("Settings", helpButton.x + 40, helpButton.y + 35);
		g2d.draw(helpButton);
		
		//Quit Button
		g.setColor(Color.WHITE);
		
		if(buttonHovered(quitButton))
		{
			g.setColor(Color.gray);
		}
		
		g.drawString("Quit", quitButton.x + 70, quitButton.y + 35);
		g2d.draw(quitButton);
	}
	
}