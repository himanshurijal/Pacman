package dev.hrijal.pacman.entities.creatures.ghosts;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import dev.hrijal.pacman.Handler;
import dev.hrijal.pacman.entities.EntityCollisionManager;
import dev.hrijal.pacman.entities.GhostCollisionObserver;
import dev.hrijal.pacman.entities.StaticCollisionObserver;
import dev.hrijal.pacman.entities.Subject;
import dev.hrijal.pacman.entities.creatures.ghosts.movement.ChaseAggressive;
import dev.hrijal.pacman.entities.creatures.ghosts.movement.ChaseAmbush;
import dev.hrijal.pacman.entities.creatures.ghosts.movement.ChasePatrol;
import dev.hrijal.pacman.entities.creatures.ghosts.movement.ChaseRelative;
import dev.hrijal.pacman.entities.creatures.ghosts.movement.DeadRunHome;
import dev.hrijal.pacman.entities.creatures.ghosts.movement.FrightenedBottomLeftMid;
import dev.hrijal.pacman.entities.creatures.ghosts.movement.FrightenedBottomRightMid;
import dev.hrijal.pacman.entities.creatures.ghosts.movement.FrightenedTopLeftMid;
import dev.hrijal.pacman.entities.creatures.ghosts.movement.FrightenedTopRightMid;
import dev.hrijal.pacman.entities.creatures.ghosts.movement.ScatterBottomLeft;
import dev.hrijal.pacman.entities.creatures.ghosts.movement.ScatterBottomRight;
import dev.hrijal.pacman.entities.creatures.ghosts.movement.ScatterTopLeft;
import dev.hrijal.pacman.entities.creatures.ghosts.movement.ScatterTopRight;
import dev.hrijal.pacman.entities.statics.Capsule;
import dev.hrijal.pacman.gfx.Assets;
import dev.hrijal.pacman.tiles.Tile;

public class GhostManager implements GhostCollisionObserver, StaticCollisionObserver
{
	
	private List<Ghost> ghosts;

	public GhostManager(Handler handler, EntityCollisionManager entityCollisionManager)
	{
		//Create ghosts
		ghosts = new ArrayList<>();
		
		Ghost ghostRed = new Ghost(handler, Tile.TILEWIDTH * 11, Tile.TILEHEIGHT * 9, Assets.ghostRed);
		ghostRed.setScatterBehavior(new ScatterBottomLeft(ghostRed));
		ghostRed.setChaseBehavior(new ChaseAggressive(handler,ghostRed));
		ghostRed.setFrightenedBehavior(new FrightenedTopLeftMid(ghostRed));
		ghostRed.setDeadBehavior(new DeadRunHome(ghostRed));
		
		Ghost ghostPurple = new Ghost(handler, Tile.TILEWIDTH * 12, Tile.TILEHEIGHT * 11, Assets.ghostPurple);
		ghostPurple.setScatterBehavior(new ScatterBottomRight(ghostPurple));
		ghostPurple.setChaseBehavior(new ChaseAmbush(handler,ghostPurple));
		ghostPurple.setFrightenedBehavior(new FrightenedTopRightMid(ghostPurple));
		ghostPurple.setDeadBehavior(new DeadRunHome(ghostPurple));
		
		Ghost ghostGreen = new Ghost(handler, Tile.TILEWIDTH * 11, Tile.TILEHEIGHT * 11, Assets.ghostGreen);
		ghostGreen.setScatterBehavior(new ScatterTopRight(ghostGreen));
		ghostGreen.setChaseBehavior(new ChaseRelative(handler,ghostGreen));
		ghostGreen.setFrightenedBehavior(new FrightenedBottomRightMid(ghostGreen));
		ghostGreen.setDeadBehavior(new DeadRunHome(ghostGreen));
		
		Ghost ghostOrange = new Ghost(handler, Tile.TILEWIDTH * 10, Tile.TILEHEIGHT * 11, Assets.ghostOrange);
		ghostOrange.setScatterBehavior(new ScatterTopLeft(ghostOrange));
		ghostOrange.setChaseBehavior(new ChasePatrol(handler,ghostOrange));
		ghostOrange.setFrightenedBehavior(new FrightenedBottomLeftMid(ghostOrange));
		ghostOrange.setDeadBehavior(new DeadRunHome(ghostOrange));
			
		//Set starting state for each ghost
		ghostRed.setState(ghostRed.getScatteredState());
		ghostRed.setAtHomeDuration(1);

		ghostPurple.setState(ghostPurple.getAtHomeState());
		ghostPurple.setAtHomeDuration(1000);
		
		ghostGreen.setState(ghostGreen.getAtHomeState());
		ghostGreen.setAtHomeDuration(15000);
		
		ghostOrange.setState(ghostOrange.getAtHomeState());
		ghostOrange.setAtHomeDuration(10000);
		
		//Add ghosts to list and ghost manager as observer
		ghosts.add(ghostRed);
		ghosts.add(ghostPurple);
		ghosts.add(ghostGreen);
		ghosts.add(ghostOrange);
		
		//Register as observer
		entityCollisionManager.registerStaticCollisionObserver(this);
		entityCollisionManager.registerGhostCollisionObserver(this);
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
	
	@Override
	public void updateOnStaticCollision(Subject subject) 
	{
		if(subject instanceof EntityCollisionManager)
		{
			EntityCollisionManager entityCollisionManager = (EntityCollisionManager) subject;
			
			if(entityCollisionManager.getStaticCollisionObject() instanceof Capsule)
			{
				for(Ghost ghost: ghosts)
				{	
					ghost.getState().playerCollisionWithCapsule();
				}
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
				Ghost currGhost = ghosts.get(ghosts.indexOf(ghost));
				currGhost.getState().ghostCollisionWithPlayer();
			}
		}
	}
	
	
	//GETTERS AND SETTERS
	
	public List<Ghost> getGhosts()
	{
		List<Ghost> ghostsCopy = new ArrayList<>(ghosts);
		return ghostsCopy;
	}
	
}
