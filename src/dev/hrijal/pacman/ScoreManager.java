package dev.hrijal.pacman;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dev.hrijal.pacman.entities.EntityCollisionManager;
import dev.hrijal.pacman.entities.Observer;
import dev.hrijal.pacman.entities.Subject;
import dev.hrijal.pacman.entities.creatures.ghosts.Ghost;
import dev.hrijal.pacman.entities.creatures.ghosts.ghoststates.AtHomeState;
import dev.hrijal.pacman.entities.creatures.ghosts.ghoststates.ChasingState;
import dev.hrijal.pacman.entities.creatures.ghosts.ghoststates.ScatteredState;
import dev.hrijal.pacman.tiles.Tile;

public class ScoreManager implements Observer
{
	private int currScore;
	private int highScore;
	private List<Integer> ghostPoints;
	private List<List<Integer>> ghostCollisionCoordinates;
	private static final long SCORE_DISPLAY_DURATION = 5000;
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
				if(!(ghost.getState() instanceof AtHomeState) && (ghost.getState() instanceof ScatteredState) 
																&& !(ghost.getState() instanceof ChasingState))
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
	}
	
	public void render(Graphics g)
	{
		g.setColor(Color.white);
		g.drawString("Current Score: " + currScore, Tile.TILEWIDTH / 2, Tile.TILEHEIGHT / 2 + 5);
		g.drawString("High Score: " + highScore, Tile.TILEWIDTH * 19, Tile.TILEHEIGHT / 2 + 5);
		
		incrementTimer();
		
		for(List<Integer> coordinates: ghostCollisionCoordinates)
		{
			g.setColor(Color.green);
			g.drawString(ghostPoints.get(0).toString(), coordinates.get(0) - 5,  coordinates.get(1));
		}
		
		if(timer >= SCORE_DISPLAY_DURATION)
		{
			ghostCollisionCoordinates.clear();
			resetTimer();
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
