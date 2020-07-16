package dev.hrijal.pacman.states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import dev.hrijal.pacman.Handler;

public class HelpState extends State
{
	
	private Rectangle moveUp;
	private Rectangle moveDown;
	private Rectangle moveLeft;
	private Rectangle moveRight;
	private Rectangle backButton;
	
	public HelpState(Handler handler)
	{
		super(handler);
		
		moveUp = new Rectangle(handler.getGame().getWidth() / 2 - 30, 250, 40, 40);
		moveDown = new Rectangle(handler.getGame().getWidth() / 2 - 30, 300, 40, 40);
		moveLeft = new Rectangle(handler.getGame().getWidth() / 2 - 80, 300, 40, 40);
		moveRight = new Rectangle(handler.getGame().getWidth() / 2 + 20, 300, 40, 40);
		backButton = new Rectangle(handler.getGame().getWidth() / 3 + 32, 400, 150, 50);	
	}

	@Override
	public void tick() 
	{
		boolean escKeyPressed = handler.getGame().getKeyManager().esc;
		
		if(escKeyPressed)
		{
			State.setState(handler.getGame().getMainMenuState());
		}
		
		if(buttonPressed(backButton))
		{
			State.setState(handler.getGame().getMainMenuState());
		}
	}

	@Override
	public void render(Graphics g)
	{
		Graphics2D g2d = (Graphics2D) g;
		
		Font fnt0 = new Font("SansSerif", Font.BOLD, 50);
		Font fnt1 = new Font("SansSerif", Font.BOLD, 25);
		
		g.setFont(fnt0);
		g.setColor(Color.PINK);
		
		g.drawString("PACMAN", handler.getGame().getWidth() / 3, 150);
		
		g.setFont(fnt1);
		g.setColor(Color.WHITE);
		
		g.drawString("W", moveUp.x + 8, moveUp.y + 30);
		g.drawString("↑", moveUp.x + 12, moveUp.y - 12);
		g2d.draw(moveUp);

		g.drawString("S", moveDown.x + 12, moveDown.y + 30);
		g.drawString("↓", moveDown.x + 12, moveDown.y + moveDown.height + 30);
		g2d.draw(moveDown);

		g.drawString("A", moveLeft.x + 10, moveLeft.y + 30);
		g.drawString("←", moveLeft.x - moveLeft.width, moveLeft.y + 30);
		g2d.draw(moveLeft);
		
		g.drawString("D", moveRight.x + 10, moveRight.y + 30);
		g.drawString("→", moveRight.x + moveRight.width + 12, moveRight.y + 30);
		g2d.draw(moveRight);
		
		//Back Button
		g.setColor(Color.WHITE);
		
		if(buttonHovered(backButton))
		{
			g.setColor(Color.gray);
		}
		
		g.drawString("< Back", backButton.x + 25, backButton.y + 35);
		g2d.draw(backButton);
	}
	
}