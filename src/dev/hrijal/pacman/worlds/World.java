package dev.hrijal.pacman.worlds;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import dev.hrijal.pacman.Handler;
import dev.hrijal.pacman.Timer;
import dev.hrijal.pacman.entities.EntityCollisionManager;
import dev.hrijal.pacman.entities.creatures.ghosts.Ghost;
import dev.hrijal.pacman.entities.creatures.ghosts.GhostManager;
import dev.hrijal.pacman.entities.creatures.player.Player;
import dev.hrijal.pacman.entities.creatures.player.PlayerManager;
import dev.hrijal.pacman.entities.creatures.player.score.ScoreManager;
import dev.hrijal.pacman.entities.statics.Capsule;
import dev.hrijal.pacman.entities.statics.Edible;
import dev.hrijal.pacman.entities.statics.StaticEntity;
import dev.hrijal.pacman.entities.statics.StaticEntityManager;
import dev.hrijal.pacman.tiles.Tile;
import dev.hrijal.pacman.utils.FileParserUtil;

public class World
{
	
	//ENVIRONMENT
	private int[][] tiles;
	private int width, height;
	
	//ENTITY COLLISION
	private EntityCollisionManager entityCollisionManager;
	
	//STATIC ENTITIES
	private StaticEntityManager staticEntityManager;
	
	//PLAYER
	private PlayerManager playerManager;
	private int spawnX, spawnY;

	//GHOSTS
	private GhostManager ghostManager;
	
	//SCOREBOARD
	private ScoreManager scoreManager;
	
	//TIMER
	public static final long GAME_START_DURATION = 3000;
	private Timer gameStartTimer;

	//WORLD STATES
	private boolean playerDead = false;
	private boolean gameWon = false;
	private boolean gameOver = false;
	
	public World(String path, Handler handler)
	{	
		//Environment
		loadWorld(path);
		
		//Entity Collision Manager
		entityCollisionManager = new EntityCollisionManager(handler);
		
		//Static entities		
		List <StaticEntity> staticEntities = new ArrayList<>();

		float currTileX = 0;
		float currTileY = 0;  

		for(int[] tileRow: tiles)
		{
			currTileX = 0;
			
			for(int tile: tileRow) //TODO: Try to implement FlyWeight pattern to make rendering more efficient
			{
				if(tile == 1) //Tile with id 1 is reserved for edibles
				{
					staticEntities.add(new Edible(handler, currTileX, currTileY));
				}
				else if( tile == 2) //Tile with id 2 is reserved for capsules
				{
					staticEntities.add(new Capsule(handler, currTileX, currTileY));
				}
				currTileX += Tile.TILEWIDTH;
			}
			
			currTileY += Tile.TILEHEIGHT;
		}
		
		staticEntityManager = new StaticEntityManager(handler, staticEntities, entityCollisionManager);
		
		//Player
		playerManager = new PlayerManager(handler, spawnX, spawnY, entityCollisionManager);
		
		//Ghosts
		ghostManager  = new GhostManager(handler, entityCollisionManager);
		
		//ScoreBoard
		scoreManager = new ScoreManager(entityCollisionManager);
		
		//Timer
		gameStartTimer = new Timer(GAME_START_DURATION);
		
		//Register as observer
//		entityCollisionManager.registerGhostCollisionObserver(this);
//		entityCollisionManager.registerStaticCollisionObserver(this);
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
//		System.out.println("World tick!");
//		
		if(getPlayers().size() == 0)
		{
			gameOver = true;
		}
		
		if(getStaticEntities().size() == 0)
		{
			gameWon = true;
		}
		
		if(getPlayer().isDead())
		{
			playerDead = true;
		}
		
		if(playerDead && !getPlayer().isDead())
		{
			gameStartTimer.resetTimer();
			playerDead = false;
		}	

		if(gameStartTimer.isTimerExpired())
		{
			if(!gameWon && !gameOver) 
			{
				staticEntityManager.tick();
				
				playerManager.tick();
				
				ghostManager.tick();
				
				entityCollisionManager.checkStaticCollisionAndNotify();
				entityCollisionManager.checkGhostCollisionAndNotify();
				
				scoreManager.tick();
			}
		}
		else
		{
			gameStartTimer.incrementTimer();
		}
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
		
		if(gameWon)
		{
			g.setColor(Color.GREEN);
			g.setFont(new Font("SansSerif", Font.BOLD, 27));
			g.drawString("You Won!", Tile.TILEWIDTH * 9 + 10, Tile.TILEHEIGHT * 14 - 5);
		}
		else if(gameOver)
		{
			g.setColor(Color.RED);
			g.setFont(new Font("SansSerif", Font.BOLD, 27));
			g.drawString("Game Over!", Tile.TILEWIDTH * 9 - 5, Tile.TILEHEIGHT * 14 - 5);
		}
		else
		{
			if(!gameStartTimer.isTimerExpired())
			{
				g.setColor(Color.YELLOW);
				g.setFont(new Font("SansSerif", Font.BOLD, 27));
				g.drawString("Ready!", Tile.TILEWIDTH * 10, Tile.TILEHEIGHT * 14 - 5);
			}
			
			staticEntityManager.render(g);
			
			playerManager.render(g);
			
			ghostManager.render(g);
		
			scoreManager.render(g);
		}
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
	

//	@Override
//	public void updateOnStaticCollision(Subject subject) 
//	{
//		if(getStaticEntities().size() == 0)
//		{
//			gameWon = true;
//		}
//	}
//
//	@Override
//	public void updateOnGhostCollision(Subject subject) 
//	{
//		if(GhostState.isPlayerDead())
//		{
//			playerDead = true;
//		}
//		
//		if(getPlayers().size() == 0)
//		{
//			gameOver  = true;
//		}
//	}
	
	
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
	
	public ScoreManager getScoreManager()
	{
		return scoreManager;
	}
	
	public Player getPlayer()
	{
		return playerManager.getPlayer();
	}
	
	public List<Player> getPlayers()
	{
		return playerManager.getPlayers();
	}
	
	public List<Ghost> getGhosts()
	{
		return ghostManager.getGhosts();
	}
	
	public List<StaticEntity> getStaticEntities()
	{
		return staticEntityManager.getEntities();
	}
	
}
