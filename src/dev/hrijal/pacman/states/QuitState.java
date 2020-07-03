package dev.hrijal.pacman.states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import dev.hrijal.pacman.Handler;

public class QuitState extends State
{
	
	private Rectangle yesButton;
	private Rectangle noButton;
	
	public QuitState(Handler handler)
	{
		super(handler);
		
		yesButton = new Rectangle(handler.getGame().getWidth() / 3 + 10, 300, 200, 50);
		noButton = new Rectangle(handler.getGame().getWidth() / 3 + 10, 400, 200, 50);
	}
	
	@Override
	public void tick() 
	{
		boolean escKeyPressed = handler.getGame().getKeyManager().esc;
		
		if(escKeyPressed)
		{
			State.setState(handler.getGame().getMainMenuState());
		}
		
		if(buttonPressed(yesButton))
		{
			System.exit(0);
		}
		
		if(buttonPressed(noButton))
		{
			State.setState(handler.getGame().getMainMenuState());
		}
	}

	@Override
	public void render(Graphics g) 
	{		
		Graphics2D g2d = (Graphics2D) g;
		
		Font fnt0 = new Font("SansSerif", Font.BOLD, 50);
		Font fnt1 = new Font("SansSerif", Font.BOLD, 30);
		
		g.setFont(fnt0);
		g.setColor(Color.ORANGE);
		
		g.drawString("PACMAN", handler.getGame().getWidth() / 3, 150);
		
		g.setFont(fnt1);
		g.setColor(Color.WHITE);
		
		g.drawString("Are you sure you want to quit?", handler.getGame().getWidth() / 6, 250);

		//Yes Button
		g.setColor(Color.WHITE);
		
		if(buttonHovered(yesButton))
		{
			g.setColor(Color.gray);
		}
		
		g.drawString("Yes", yesButton.x + 70, yesButton.y + 35);
		g2d.draw(yesButton);
		
		//No Button
		g.setColor(Color.WHITE);
		
		if(buttonHovered(noButton))
		{
			g.setColor(Color.gray);
		}

		g.drawString("No", noButton.x + 75, noButton.y + 35);
		g2d.draw(noButton);
	}

}
