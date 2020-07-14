package dev.hrijal.pacman.entities.creatures.player.score;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dev.hrijal.pacman.Timer;
import dev.hrijal.pacman.entities.EntityCollisionManager;
import dev.hrijal.pacman.entities.GhostCollisionObserver;
import dev.hrijal.pacman.entities.StaticCollisionObserver;
import dev.hrijal.pacman.entities.Subject;
import dev.hrijal.pacman.entities.creatures.ghosts.Ghost;
import dev.hrijal.pacman.entities.creatures.ghosts.ghoststates.DeadState;
import dev.hrijal.pacman.entities.creatures.ghosts.ghoststates.FrightenedState;
import dev.hrijal.pacman.entities.creatures.ghosts.ghoststates.PauseState;
import dev.hrijal.pacman.tiles.Tile;

public class ScoreManager implements GhostCollisionObserver, StaticCollisionObserver
{
	
	//PLAYER SCORE
	private int currScore;
	private int highScore;
	
	//GHOST POINTS
	private List<Integer> ghostPoints;
	private int capsuleCount;
	private int ghostPointsIndex;
	private List<List<Integer>> ghostCollisionCoordinates;

	//TIMER
	public static final long SCORE_DISPLAY_DURATION = 500;
	private Timer scoreDisplayTimer;
	
	public ScoreManager(EntityCollisionManager entityCollisionManager)
	{
		//Player Score
		currScore = 0;
		highScore = 0;
		
		//Ghost Points
		ghostPoints = new ArrayList<>(Arrays.asList(200,400,800,1600));
		capsuleCount = 0;
		ghostPointsIndex = 0;
		ghostCollisionCoordinates = new ArrayList<>();

		//Timer
		scoreDisplayTimer = new Timer(SCORE_DISPLAY_DURATION);
		
		//Register as observer
		entityCollisionManager.registerGhostCollisionObserver(this);
		entityCollisionManager.registerStaticCollisionObserver(this);
	}
	
	public void tick()
	{
		if(currScore > highScore)
		{
			highScore = currScore;
		}
		
		if(ghostCollisionCoordinates.size() != 0)
		{
			scoreDisplayTimer.readyTimer();
		}
		
		if(scoreDisplayTimer.isTimerReady())
		{
			scoreDisplayTimer.incrementTimer();
		}
		
		if(scoreDisplayTimer.isTimerExpired())
		{
			ghostCollisionCoordinates.clear();
			scoreDisplayTimer.resetTimer();
		}
	}
	
	public void render(Graphics g)
	{
		g.setColor(Color.white);
		g.setFont(new Font("SansSerif", Font.BOLD, 13));
		
		g.drawString("Current Score: " + currScore, Tile.TILEWIDTH / 2, Tile.TILEHEIGHT / 2 + 5);
		g.drawString("High Score: " + highScore, Tile.TILEWIDTH * 19 - 10, Tile.TILEHEIGHT / 2 + 5);
		
		for(List<Integer> coordinates: ghostCollisionCoordinates)
		{
			g.setColor(Color.green);
			g.setFont(new Font("arial", Font.PLAIN, 15));
			g.drawString(ghostPoints.get(ghostPointsIndex - 1).toString(), coordinates.get(0) - 5,  coordinates.get(1) + 10);
		}
	}

	@Override
	public void updateOnStaticCollision(Subject subject) 
	{
		if(subject instanceof EntityCollisionManager)
		{
			EntityCollisionManager entityCollisionManager = (EntityCollisionManager) subject;
			
			if(entityCollisionManager.getStaticCollisionObject() != null)
			{
				currScore += entityCollisionManager.getStaticCollisionObject().getPoints();
			}
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
				if(ghost.getStateAfterPause() instanceof DeadState)
				{
					if(capsuleCount != entityCollisionManager.getCapsuleCount())
					{
						ghostPointsIndex = 0;
						capsuleCount = entityCollisionManager.getCapsuleCount();
					}
					
					currScore += ghostPoints.get(ghostPointsIndex);
					ghostCollisionCoordinates.add(Arrays.asList(ghost.getEntityCollisionBounds(0f,0f).x, 
																ghost.getEntityCollisionBounds(0f,0f).y));
					ghostPointsIndex++;
				}
			}
		}
	}
	
	
	//GETTERS AND SETTERS
	
	public long getTimer()
	{
		return scoreDisplayTimer.getTimer();
	}

}
