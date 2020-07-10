package dev.hrijal.pacman.entities.creatures.player;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import dev.hrijal.pacman.Handler;
import dev.hrijal.pacman.Timer;
import dev.hrijal.pacman.entities.EntityCollisionManager;
import dev.hrijal.pacman.entities.GhostCollisionObserver;
import dev.hrijal.pacman.entities.Subject;
import dev.hrijal.pacman.entities.creatures.ghosts.Ghost;
import dev.hrijal.pacman.entities.creatures.ghosts.ghoststates.FrightenedState;
import dev.hrijal.pacman.entities.creatures.player.score.ScoreManager;
import dev.hrijal.pacman.tiles.Tile;

public class PlayerManager implements GhostCollisionObserver
{

	//PLAYERS
	private List<Player> players;
	private Player currPlayer;
	private int spawnX;
	private int spawnY;
	
	//TIMER
	private static final long PLAYER_DEAD_DURATION = 1200;
	private Timer playerDeadTimer;
	private Timer movementPauseTimer;
	
	public PlayerManager(Handler handler, int spawnX, int spawnY, EntityCollisionManager entityCollisionManager)
	{		
		//Players
		players = new ArrayList<>();
		
		Player playerFirstLife = new Player(handler, spawnX, spawnY);
		Player playerSecondLife = new Player(handler, Tile.TILEWIDTH,
											 handler.getGame().getHeight() - Tile.TILEHEIGHT);
		Player playerThirdLife = new Player(handler, Tile.TILEWIDTH * 2, 
				 							handler.getGame().getHeight() - Tile.TILEHEIGHT);
		
		players.add(playerFirstLife);
		players.add(playerSecondLife);
		players.add(playerThirdLife);
		
		currPlayer  = players.get(0);
		
		this.spawnX = spawnX;
		this.spawnY = spawnY;

		//Timer
		playerDeadTimer = new Timer(PLAYER_DEAD_DURATION);
		movementPauseTimer = new Timer(ScoreManager.SCORE_DISPLAY_DURATION);
		
		//Register as observer
		entityCollisionManager.registerGhostCollisionObserver(this);
	}
	
	public void tick()
	{
		if(movementPauseTimer.isTimerReady())
		{
			movementPauseTimer.incrementTimer();
			
			if(movementPauseTimer.isTimerExpired())	
			{
				movementPauseTimer.resetTimer();
			}
		}
		else if(playerDeadTimer.isTimerReady())
		{
			if(players.size() > 0)
			{
				currPlayer.tick();
			}
			
			playerDeadTimer.incrementTimer();
			
			if(playerDeadTimer.isTimerExpired())
			{
				players.remove(currPlayer);
				
				if(players.size() > 0)
				{
					currPlayer = players.get(0);
					currPlayer.setX(spawnX);
					currPlayer.setY(spawnY);
				}
				
				playerDeadTimer.resetTimer();
			}
		}
		else
		{
			if(players.size() > 0)
			{
				currPlayer.tick();
			}
		}
	}
	
	public void render(Graphics g)
	{
		if(players.size() > 0 && !movementPauseTimer.isTimerReady())
		{
			currPlayer.render(g);
		}
		
		for(int i = 1; i < players.size(); i++)
		{
			players.get(i).render(g);
		}
	}

	@Override
	public void updateOnGhostCollision(Subject subject)
	{
		if(subject instanceof EntityCollisionManager)
		{
			EntityCollisionManager entityCollisionManager = (EntityCollisionManager) subject;

			for(Ghost ghost: entityCollisionManager.getGhostCollisionObjects())
			{		
				if((ghost.getState() instanceof FrightenedState))
				{
					movementPauseTimer.readyTimer();
				}
				else
				{
					currPlayer.setDead(true);
					playerDeadTimer.readyTimer();
				}
			}
		}	
	}
	
	
	//GETTERS AND SETTERS
	
	public Player getPlayer()
	{
		return currPlayer;
	}
}
