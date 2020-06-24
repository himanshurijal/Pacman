package dev.hrijal.pacman.entities.creatures.ghosts;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import dev.hrijal.pacman.Handler;
import dev.hrijal.pacman.entities.EntityCollisionManager;
import dev.hrijal.pacman.entities.Observer;
import dev.hrijal.pacman.entities.Subject;
import dev.hrijal.pacman.entities.creatures.ghostMovement.ChaseAggressive;
import dev.hrijal.pacman.entities.creatures.ghostMovement.ChaseAmbush;
import dev.hrijal.pacman.entities.creatures.ghostMovement.DeadRunHome;
import dev.hrijal.pacman.entities.creatures.ghostMovement.FrightenedBottomLeftMid;
import dev.hrijal.pacman.entities.creatures.ghostMovement.FrightenedBottomRightMid;
import dev.hrijal.pacman.entities.creatures.ghostMovement.FrightenedTopLeftMid;
import dev.hrijal.pacman.entities.creatures.ghostMovement.FrightenedTopRightMid;
import dev.hrijal.pacman.entities.creatures.ghostMovement.ScatterBottomLeft;
import dev.hrijal.pacman.entities.creatures.ghostMovement.ScatterBottomRight;
import dev.hrijal.pacman.entities.creatures.ghostMovement.ScatterTopLeft;
import dev.hrijal.pacman.entities.creatures.ghostMovement.ScatterTopRight;
import dev.hrijal.pacman.entities.statics.Capsule;
import dev.hrijal.pacman.gfx.Assets;
import dev.hrijal.pacman.tiles.Tile;

public class GhostManager implements Observer 
{
	
	private Handler handler;
	private List<Ghost> ghosts;

	public GhostManager(Handler handler, EntityCollisionManager entityCollisionManager)
	{
		this.handler = handler;
		
		//Create ghosts
		ghosts = new ArrayList<>();
		
		Ghost ghostRed = new Ghost(handler, Tile.TILEWIDTH * 11, Tile.TILEHEIGHT * 9, Assets.ghostRed);
		ghostRed.setScatterBehavior(new ScatterBottomLeft(ghostRed));
		ghostRed.setChaseBehavior(new ChaseAggressive(handler,ghostRed));
		ghostRed.setFrightenedBehavior(new FrightenedTopLeftMid(ghostRed));
		ghostRed.setDeadBehavior(new DeadRunHome(ghostRed));
		
		Ghost ghostPurple = new Ghost(handler, Tile.TILEWIDTH * 12, Tile.TILEHEIGHT * 11, Assets.ghostPurple);
		ghostPurple.setScatterBehavior(new ScatterBottomRight(ghostPurple));
		ghostPurple.setChaseBehavior(new ChaseAggressive(handler,ghostPurple));
		ghostPurple.setFrightenedBehavior(new FrightenedTopRightMid(ghostPurple));
		ghostPurple.setDeadBehavior(new DeadRunHome(ghostPurple));
		
		Ghost ghostOrange = new Ghost(handler, Tile.TILEWIDTH * 10, Tile.TILEHEIGHT * 11, Assets.ghostOrange);
		ghostOrange.setScatterBehavior(new ScatterTopLeft(ghostOrange));
		ghostOrange.setChaseBehavior(new ChaseAmbush(handler,ghostOrange));
		ghostOrange.setFrightenedBehavior(new FrightenedBottomLeftMid(ghostOrange));
		ghostOrange.setDeadBehavior(new DeadRunHome(ghostOrange));
		
		Ghost ghostGreen = new Ghost(handler, Tile.TILEWIDTH * 11, Tile.TILEHEIGHT * 11, Assets.ghostGreen);
		ghostGreen.setScatterBehavior(new ScatterTopRight(ghostGreen));
		ghostGreen.setChaseBehavior(new ChaseAmbush(handler,ghostGreen));
		ghostGreen.setFrightenedBehavior(new FrightenedBottomRightMid(ghostGreen));
		ghostGreen.setDeadBehavior(new DeadRunHome(ghostGreen));
			
		//Set starting behavior for each ghost
		ghostRed.setAtHome(false);
		ghostRed.setAtHomeDuration(1);
		ghostRed.setScattered(true);
		
		ghostPurple.setAtHome(true);
		ghostPurple.setAtHomeDuration(1000);

		ghostOrange.setAtHome(true);
		ghostOrange.setAtHomeDuration(10000);
		
		ghostGreen.setAtHome(true);
		ghostGreen.setAtHomeDuration(15000);
		
		//Add ghosts to list and ghost manager as observer
		ghosts.add(ghostRed);
		ghosts.add(ghostOrange);
		ghosts.add(ghostGreen);
		ghosts.add(ghostPurple);
		
		entityCollisionManager.registerObserver(this);
	}
	
	public void tick()
	{
		for(Ghost ghost: ghosts)
		{
			ghost.tick();
		}
	}
	
	public void render(Graphics g)
	{
		for(Ghost ghost: ghosts)
		{
			ghost.render(g);
		}
	}
	
	public List<Ghost> getGhosts()
	{
		List<Ghost> ghostsCopy = new ArrayList<>(ghosts);
		return ghostsCopy;
	}
	
	public void update(Subject subject)
	{
		if(subject instanceof EntityCollisionManager)
		{
			EntityCollisionManager entityCollisionManager = (EntityCollisionManager) subject;
			
			if(entityCollisionManager.getStaticCollisionObject() instanceof Capsule)
			{
				for(Ghost ghost: ghosts)
				{
					if(ghost.isAtHome())
					{
						ghost.setSecondaryTimer(ghost.getTimer());
						ghost.resetTimer();
						ghost.setAtHome(false);
						ghost.setFrightened(true);
					}
					else
					{
						ghost.setAllStatesToFalse();
						ghost.resetTimer();
						ghost.setFrightened(true);
					}
				}
			}
			
			for(Ghost ghost: entityCollisionManager.getGhostCollisionObjects())
			{				
				if(ghost.isFrightened() || ghost.isFlashing())
				{
					Ghost currGhost = ghosts.get(ghosts.indexOf(ghost));
					
					currGhost.setFrightened(false);
					currGhost.setFlashing(false);
					currGhost.setDead(true);
					
					float currGhostNewX = Math.round(currGhost.getX() / Tile.TILEWIDTH) * Tile.TILEWIDTH;
					float currGhostNewY = Math.round(currGhost.getY() / Tile.TILEHEIGHT) * Tile.TILEHEIGHT;
					
					currGhost.setX(currGhostNewX);
					currGhost.setY(currGhostNewY);
				}
				else
				{
					ghosts.clear();
					handler.getWorld().getPlayer().setDead(true);
					break;
				}
			}
		}
	}
	
}
