package dev.hrijal.pacman.entities.creatures.ghostMovement;

import java.awt.Graphics;

public interface ChaseBehavior 
{
	public void chase();
	
	public void render(Graphics g); //For testing, remove later
}
