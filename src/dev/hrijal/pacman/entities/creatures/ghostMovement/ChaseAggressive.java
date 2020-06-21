package dev.hrijal.pacman.entities.creatures.ghostMovement;

import java.awt.Color;
import java.awt.Graphics;

import dev.hrijal.pacman.Handler;
import dev.hrijal.pacman.entities.creatures.Ghost;

public class ChaseAggressive implements ChaseBehavior
{
	
	private Handler handler;
	private Ghost ghost;
	private float tempSpeed;

	public ChaseAggressive(Handler handler, Ghost ghost)
	{
		this.handler = handler;
		this.ghost = ghost;
		tempSpeed= ghost.getSpeed();
	}
	
	public void chase()
	{
		ghost.makeNextMove(handler.getWorld().getEntityManager().getPlayer().getX(), 
						   handler.getWorld().getEntityManager().getPlayer().getY(), 
						   tempSpeed);
	}
	
	public void render(Graphics g)
	{
		g.setColor(Color.red);
		g.drawRect((int) 10, (int) 10, 10, 10);
	}

}
