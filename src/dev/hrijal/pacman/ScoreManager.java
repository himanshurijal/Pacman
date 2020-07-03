package dev.hrijal.pacman;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dev.hrijal.pacman.entities.EntityCollisionManager;
import dev.hrijal.pacman.entities.Observer;
import dev.hrijal.pacman.entities.Subject;
import dev.hrijal.pacman.entities.creatures.ghosts.Ghost;
import dev.hrijal.pacman.entities.creatures.ghosts.ghoststates.DeadState;
import dev.hrijal.pacman.entities.creatures.ghosts.ghoststates.FrightenedState;
import dev.hrijal.pacman.tiles.Tile;

public class ScoreManager implements Observer
{
	
	private int currScore;
	private int highScore;
	private List<Integer> ghostPoints;
	private List<List<Integer>> ghostCollisionCoordinates;
	public static final long SCORE_DISPLAY_DURATION = 1000;
	private long timer;
	private long lastTime;
	
	public ScoreManager(EntityCollisionManager entityCollisionManager)
	{
		currScore = 0;
		highScore = 0;
		ghostPoints = new ArrayList<>(Arrays.asList(200,400,600,800));
		ghostCollisionCoordinates = new ArrayList<>();
		timer = 0;
		lastTime = 0;
		entityCollisionManager.registerObserver(this);
	}
	
	public void update(Subject subject)
	{			
		if(subject instanceof EntityCollisionManager)
		{
			EntityCollisionManager entityCollisionManager = (EntityCollisionManager) subject;
			
			if(entityCollisionManager.getStaticCollisionObject() != null)
			{
				currScore += entityCollisionManager.getStaticCollisionObject().getPoints();
			}
			
			for(Ghost ghost: entityCollisionManager.getGhostCollisionObjects())
			{
				if(ghost.getState() instanceof FrightenedState || ghost.getState() instanceof DeadState)
				{
					currScore += ghostPoints.get(0);
					ghostCollisionCoordinates.add(Arrays.asList(ghost.getEntityCollisionBounds(0f,0f).x, 
																ghost.getEntityCollisionBounds(0f,0f).y));
				}
			}
		}
	}
	
	public void tick()
	{
		if(currScore > highScore)
		{
			highScore = currScore;
		}
		
		if(ghostCollisionCoordinates.size() != 0)
		{
			incrementTimer();
		}
		
		if(timer >= SCORE_DISPLAY_DURATION)
		{
			ghostCollisionCoordinates.clear();
			resetTimer();
		}
	}
	
	public void render(Graphics g)
	{
		g.setColor(Color.white);
		g.setFont(new Font("SansSerif", Font.BOLD, 13));
		
		g.drawString("Current Score: " + currScore, Tile.TILEWIDTH / 2, Tile.TILEHEIGHT / 2 + 5);
		g.drawString("High Score: " + highScore, Tile.TILEWIDTH * 19 - 10, Tile.TILEHEIGHT / 2 + 5);
		
		for(List<Integer> coordinates: ghostCollisionCoordinates) //Move this code to tick method
		{
			g.setColor(Color.green);
			g.setFont(new Font("arial", Font.PLAIN, 15));
			
			g.drawString(ghostPoints.get(0).toString(), coordinates.get(0) - 5,  coordinates.get(1) + 10);
		}
	}
	
	public void incrementTimer()
	{
		if(lastTime == 0)
			lastTime = System.currentTimeMillis();
		
		timer += System.currentTimeMillis() - lastTime;
		lastTime = System.currentTimeMillis();
	}
	
	public void resetTimer()
	{
		timer = 0;
		lastTime = 0;
	}
	
}
