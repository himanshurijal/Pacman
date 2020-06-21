package dev.hrijal.pacman.entities.creatures.movement;

import java.awt.Graphics;

public interface ChaseBehavior 
{
	public void chase(float destX, float destY);
	
	public void render(Graphics g); //For testing, remove later
}
