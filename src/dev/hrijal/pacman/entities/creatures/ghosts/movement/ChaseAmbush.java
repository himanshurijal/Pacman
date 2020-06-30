package dev.hrijal.pacman.entities.creatures.ghosts.movement;

import java.awt.Color;
import java.awt.Graphics;

import dev.hrijal.pacman.Handler;
import dev.hrijal.pacman.entities.creatures.ghosts.Ghost;
import dev.hrijal.pacman.input.KeyManager;
import dev.hrijal.pacman.tiles.Tile;

public class ChaseAmbush implements ChaseBehavior
{

	private Handler handler;
	private Ghost ghost;
	private float tempSpeed;
	private float destX,destY;
	
	public ChaseAmbush(Handler handler, Ghost ghost)
	{
		this.handler = handler;
		this.ghost = ghost;
		tempSpeed = ghost.getSpeed();
		destX = 0;
		destY = 0;
	}
	
	public void chase()
	{
		destX = getAmbushDestX(handler.getWorld().getPlayer().getX());
		destY = getAmbushDestY(handler.getWorld().getPlayer().getY());
		
		ghost.makeNextMove(destX, destY, tempSpeed);
	}
	
	public void render(Graphics g)
	{
		g.setColor(Color.red);
		g.drawRect((int) destX, (int) destY, 10, 10);
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
