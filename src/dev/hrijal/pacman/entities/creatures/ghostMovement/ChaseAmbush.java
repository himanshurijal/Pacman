package dev.hrijal.pacman.entities.creatures.ghostMovement;

import java.awt.Color;
import java.awt.Graphics;

import dev.hrijal.pacman.Handler;
import dev.hrijal.pacman.entities.creatures.ghosts.Ghost;
import dev.hrijal.pacman.input.KeyManager;
import dev.hrijal.pacman.tiles.Tile;

public class ChaseAmbush implements ChaseMode
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

	public float getAmbushDestX(float destX)
	{
		KeyManager key = handler.getGame().getKeyManager();
		float ambushDestX = (int) ((destX / Tile.TILEWIDTH) * Tile.TILEWIDTH) + Tile.TILEWIDTH / 3;
		
		if(key.left)
		{
			ambushDestX -= ghost.getSpeed() * 30;
		}
		else if(key.right)
		{
			ambushDestX += ghost.getSpeed() * 30;
		}

		return ambushDestX;
	}
	
	public float getAmbushDestY(float destY)
	{
		KeyManager key = handler.getGame().getKeyManager();
		float ambushDestY = (int) ((destY / Tile.TILEHEIGHT) * Tile.TILEHEIGHT) + Tile.TILEHEIGHT / 3;
		
		if(key.up)
		{
			ambushDestY -= ghost.getSpeed() * 30;
		}
		else if(key.down)
		{
			ambushDestY += ghost.getSpeed() * 30;
		}

		return ambushDestY;
	}

}
