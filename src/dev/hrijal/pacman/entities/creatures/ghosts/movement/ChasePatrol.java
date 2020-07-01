package dev.hrijal.pacman.entities.creatures.ghosts.movement;

import java.awt.Color;
import java.awt.Graphics;

import dev.hrijal.pacman.Handler;
import dev.hrijal.pacman.entities.creatures.ghosts.Ghost;
import dev.hrijal.pacman.input.KeyManager;
import dev.hrijal.pacman.tiles.Tile;

public class ChasePatrol implements ChaseBehavior
{

	private Handler handler;
	private Ghost ghost;
	private float tempSpeed;
	
	public ChasePatrol(Handler handler, Ghost ghost)
	{
		this.handler = handler;
		this.ghost = ghost;
		tempSpeed = ghost.getSpeed();
	}
	
	public void chase()
	{
		float ghostX = ghost.getX();
		float ghostY = ghost.getY();
		
		float playerX = handler.getWorld().getPlayer().getX();
		float playerY = handler.getWorld().getPlayer().getY();
		
		float distanceFromPlayer = getDistanceFromPlayer(ghostX, ghostY, playerX, playerY);
		
		if(distanceFromPlayer >= Tile.TILEWIDTH * 8)
		{
			ghost.makeNextMove(playerX, playerY, tempSpeed);
		}
		else
		{
			ghost.scatter();
		}
	}
	
	public float getDistanceFromPlayer(float ghostX, float ghostY, float playerX, float playerY)
	{
		return (float) Math.sqrt(Math.pow(ghostX - playerX, 2) + Math.pow(ghostY - playerY, 2));
	}

	@Override
	public void render(Graphics g) 
	{
	}
	
}
