package dev.hrijal.pacman.worlds.states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import dev.hrijal.pacman.entities.EntityCollisionManager;
import dev.hrijal.pacman.tiles.Tile;
import dev.hrijal.pacman.worlds.World;

public class PlayingState extends WorldState
{
	
	private boolean gameWon;
	private boolean gameOver;
	
	private int playerDeathCount;
	
	public PlayingState(World world, long duration, EntityCollisionManager entityCollisionManager)
	{
		super(world, duration);
		
		gameWon = false;
		gameOver = false;
		
		playerDeathCount = 0;
	}


	@Override
	public void tick() 
	{
		if(world.getStaticEntities().size() == 0)
		{
			gameWon = true;
		}
		
		if(playerDeathCount == 3)
		{
			gameOver = true;
		}
		
		if(!gameOver)
		{
			world.tickWorldComponents();
		}
	}
	
	@Override
	public void checkTransitionToNextState()
	{
		if(world.getPlayer().isDead() && playerDeathCount != 3)
		{
			playerDeathCount++;
			world.setCurrentState(world.getResetState());
		}
	}
	
	@Override
	public void render(Graphics g) 
	{
		if(gameWon)
		{
			world.renderWorldEnvironment(g);
			
			g.setColor(Color.GREEN);
			g.setFont(new Font("SansSerif", Font.BOLD, 27));
			g.drawString("You Won!", Tile.TILEWIDTH * 9 + 10, Tile.TILEHEIGHT * 14 - 5);
		}
		else if(gameOver)
		{
			world.renderWorldEnvironment(g);
			
			g.setColor(Color.RED);
			g.setFont(new Font("SansSerif", Font.BOLD, 27));
			g.drawString("Game Over!", Tile.TILEWIDTH * 9 - 5, Tile.TILEHEIGHT * 14 - 5);
		}
		else
		{
			world.renderWorldEnvironment(g);
			world.renderWorldObjects(g);
		}
	}
	
	
	//GETTERS AND SETTERS
	
	public int getPlayerDeathCount()
	{
		return playerDeathCount;
	}
	
}
