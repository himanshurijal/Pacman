package dev.hrijal.pacman.worlds;

import java.awt.Graphics;

import dev.hrijal.pacman.Handler;
import dev.hrijal.pacman.entities.EntityManager;
import dev.hrijal.pacman.entities.creatures.Ghost;
import dev.hrijal.pacman.entities.creatures.Player;
import dev.hrijal.pacman.entities.statics.Capsule;
import dev.hrijal.pacman.entities.statics.Edible;
import dev.hrijal.pacman.gfx.Assets;
import dev.hrijal.pacman.tiles.Tile;
import dev.hrijal.pacman.utils.FileParserUtil;

public class World 
{

	private int[][] tiles;
	private int width, height, spawnX, spawnY;
	private EntityManager entityManager;
	
	public World(String path, Handler handler)
	{
		this.entityManager = new EntityManager(handler, new Player(handler, 0, 0, 0),
							 new Ghost(handler, Tile.TILEWIDTH * 11, Tile.TILEHEIGHT * 9, Assets.ghostOrange));
		
		loadWorld(path); //We set the actual player spawn coordinates here
		
		float currTileX = 0;
		float currTileY = 0;
		
		for(int[] tileRow: tiles)
		{
			currTileX = 0;
			
			for(int tile: tileRow)
			{
				if(tile == 1) //Tile with id 1 is reserved for edibles
				{
					entityManager.addEntity(new Edible(handler, currTileX, currTileY));
				}
				else if( tile == 2) //Tile with id 2 is reserved for capsules
				{
					entityManager.addEntity(new Capsule(handler, currTileX, currTileY));
				}
				currTileX += Tile.TILEWIDTH;
			}
			currTileY += Tile.TILEHEIGHT;
		}
	}

	public void loadWorld(String path)
	{
		String file = FileParserUtil.loadFileAsString(path);
		
		String[] tokens = file.split("\\s+");
		
		width = FileParserUtil.parseInt(tokens[0]);
		height = FileParserUtil.parseInt(tokens[1]);
		spawnX = FileParserUtil.parseInt(tokens[2]);
		spawnY = FileParserUtil.parseInt(tokens[3]);
		
		tiles = new int[height][width];
		
		entityManager.getPlayer().setX(spawnX);
		entityManager.getPlayer().setY(spawnY);
	
		for(int i = 0; i < height; i++)
		{
			for(int j = 0; j < width; j++)
			{
				tiles[i][j] = FileParserUtil.parseInt(tokens[4 + (j + i * width)]);
			}
		}
	}
	
	public void tick()
	{
		entityManager.tick();
	}
	
	public void render(Graphics g)
	{
		for (int x = 0; x < height; x++)
		{
			for (int y = 0; y < width; y++)
			{
				getTile(x,y).render(g, y * Tile.TILEWIDTH, x * Tile.TILEHEIGHT);
			}
		}
		entityManager.render(g);
	}
	
	public Tile getTile(int x, int y)
	{
		if( x < 0 || y < 0 || x >= width || y >= height)
		{
			return Tile.emptyTile;
		}
		
		Tile t = Tile.tiles[tiles[x][y]];
		
		if(t == null)
		{
			return Tile.emptyTile;
		}
		
		return t;
	}
	
	public void setTile(int x, int y, int id)
	{	
		if( x < 0 || y < 0 || x >= width || y >= height)
		{
			return;
		}
		
		tiles[x][y] = id;
	}
	
	
	//GETTERS AND SETTERS

	public int[][] getTilesArray()
	{
		return tiles;
	}
	
	public int getWidth()
	{
		return width;
	}

	public int getHeight() 
	{
		return height;
	}
	
	public EntityManager getEntityManager() 
	{
		return entityManager;
	}
}
