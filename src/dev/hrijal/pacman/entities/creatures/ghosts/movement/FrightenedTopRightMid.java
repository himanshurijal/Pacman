package dev.hrijal.pacman.entities.creatures.ghosts.movement;

import dev.hrijal.pacman.entities.creatures.ghosts.Ghost;
import dev.hrijal.pacman.tiles.Tile;

public class FrightenedTopRightMid implements FrightenedBehavior
{
	
	private Ghost ghost;
	private float tempSpeed;
	public static final float DEST_X = Tile.TILEWIDTH * 18,
							  DEST_Y = Tile.TILEHEIGHT * 5;

	public FrightenedTopRightMid(Ghost ghost)
	{
		this.ghost = ghost;
		tempSpeed = ghost.getSpeed() / 2;
	}
	
	public void runAway()
	{
		ghost.makeNextMove(FrightenedTopRightMid.DEST_X, FrightenedTopRightMid.DEST_Y, tempSpeed);
	}
	
}
