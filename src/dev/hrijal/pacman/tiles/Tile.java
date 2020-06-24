package dev.hrijal.pacman.tiles;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import dev.hrijal.pacman.entities.creatures.ghosts.Ghost;

public class Tile 
{
	
	public static Tile[] tiles = new Tile[256];	
	protected BufferedImage texture;
	protected final int id;
	
	public static final int TILEWIDTH = 30,
							TILEHEIGHT = 30;
	
	//Remember to change variables in World and worlds directory if any of the below pre-defined tile id change
	public static Tile emptyTile = new EmptyTile(0);
	public static Tile edibleTile = new ReservedEdibleTile(1);
	public static Tile capsuleTile = new ReservedCapsuleTile(2); 
	public static Tile mazeTile = new MazeTile(3);
	public static Tile gateTile = new GateTile(4); 
	
	public Tile(BufferedImage texture, int id)
	{
		this.texture  = texture;
		this.id = id;
		
		tiles[id] = this;
	}
	
	public void tick()
	{
		
	}
	
	public void render(Graphics g, int x, int y)
	{
		g.drawImage(texture, x, y, Tile.TILEWIDTH, Tile.TILEHEIGHT, null);
//		g.setColor(Color.white);
//		g.drawRect(x, y, Tile.TILEWIDTH, Tile.TILEHEIGHT);
	}
	
	
	public boolean isSolid()
	{
		return false;
	}
	
	public boolean isSolid(Ghost ghost)
	{
		return false;
	}
	
	//GETTERS AND SETTERS
	
	public int getId()
	{
		return id;
	}
	
}
