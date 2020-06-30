package dev.hrijal.pacman.entities.creatures.ghosts.movement;

import java.awt.Color;
import java.awt.Graphics;

import dev.hrijal.pacman.Handler;
import dev.hrijal.pacman.entities.creatures.ghosts.Ghost;
import dev.hrijal.pacman.input.KeyManager;
import dev.hrijal.pacman.tiles.Tile;

public class ChaseRelative implements ChaseBehavior
{
	
	private Handler handler;
	private Ghost ghost;
	private float tempSpeed;
	private float destX,destY;

	public ChaseRelative(Handler handler, Ghost ghost)
	{
		this.handler = handler;
		this.ghost = ghost;
		tempSpeed = ghost.getSpeed();
	}
	
	public void chase()
	{
		float ghostRedX = handler.getWorld().getGhostManager().getGhosts().get(0).getX();
		float ghostRedY = handler.getWorld().getGhostManager().getGhosts().get(0).getY();
		
		float ambushDestX = getAmbushDestX(handler.getWorld().getPlayer().getX());
		float ambushDestY = getAmbushDestY(handler.getWorld().getPlayer().getY());
		
		destX = calcRelativeDestX(ghostRedX, ambushDestX);
		destY = calcRelativeDestX(ghostRedY, ambushDestY);
		
		ghost.makeNextMove(destX, destY, tempSpeed);
	}
	
	public void render(Graphics g)
	{
		g.setColor(Color.green);
		g.drawRect((int) destX, (int) destY, 10, 10);
	}
	
	public float calcRelativeDestX(float ghostRedX, float ambushDestX)
	{
		float relativeDestX =  (ambushDestX - ghostRedX) * 2;	
		return relativeDestX + ghostRedX;
	}
	
	public float calcRelativeDestY(float ghostRedY, float ambushDestY)
	{
		float relativeDestY =  (ambushDestY - ghostRedY) * 2;
		return relativeDestY + ghostRedY;
	}

	public float getAmbushDestX(float playerX)
	{
		KeyManager key = handler.getGame().getKeyManager();

		float ambushDestX = playerX;
		
		if(key.left)
		{
			ambushDestX -= Tile.TILEWIDTH * 2;
		}
		else if(key.right)
		{
			ambushDestX += Tile.TILEWIDTH * 2;
		}
		else if(key.up)
		{
			ambushDestX -= Tile.TILEWIDTH * 2;
		}
		
		return ambushDestX;
	}
	
	public float getAmbushDestY(float playerY)
	{
		KeyManager key = handler.getGame().getKeyManager();

		float ambushDestY = playerY;
		
		if(key.up)
		{
			ambushDestY -= Tile.TILEHEIGHT * 2;
		}
		else if(key.down)
		{
			ambushDestY += Tile.TILEHEIGHT * 2;
		}

		return ambushDestY;
	}
	
}
