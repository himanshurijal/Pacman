package dev.hrijal.pacman.entities.creatures.ghosts.movement;

import java.awt.Color;
import java.awt.Graphics;

import dev.hrijal.pacman.Handler;
import dev.hrijal.pacman.entities.creatures.ghosts.Ghost;

public class ChaseAggressive implements ChaseBehavior
{
	
	private Handler handler;
	private Ghost ghost;
	private float tempSpeed;
	private float destX,destY;

	public ChaseAggressive(Handler handler, Ghost ghost)
	{
		this.handler = handler;
		this.ghost = ghost;
		tempSpeed = ghost.getSpeed();
	}
	
	public void chase()
	{
		destX = handler.getWorld().getPlayer().getX();
		destY = handler.getWorld().getPlayer().getY();
		
		ghost.makeNextMove(destX, destY, tempSpeed);
	}
	
	public void render(Graphics g)
	{
		g.setColor(Color.gray);
		g.drawRect((int) destX, (int) destY, 10, 10);
	}

}
