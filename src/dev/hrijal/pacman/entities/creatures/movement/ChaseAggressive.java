package dev.hrijal.pacman.entities.creatures.movement;

import java.awt.Color;
import java.awt.Graphics;

import dev.hrijal.pacman.entities.creatures.Ghost;

public class ChaseAggressive implements ChaseBehavior
{

	private Ghost ghost;

	public ChaseAggressive(Ghost ghost)
	{
		this.ghost = ghost;
	}
	
	public void chase(float destX, float destY)
	{
		ghost.makeNextMove(destX, destY);
	}
	
	public void render(Graphics g)
	{
		g.setColor(Color.red);
		g.drawRect((int) 10, (int) 10, 10, 10);
	}

}
