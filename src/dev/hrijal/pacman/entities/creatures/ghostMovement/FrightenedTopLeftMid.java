package dev.hrijal.pacman.entities.creatures.ghostMovement;

import dev.hrijal.pacman.entities.creatures.ghosts.Ghost;
import dev.hrijal.pacman.tiles.Tile;

public class FrightenedTopLeftMid implements FrightenedMode
{
	
	private Ghost ghost;
	private float tempSpeed;
	public static final float DEST_X = Tile.TILEWIDTH * 6,
							  DEST_Y = Tile.TILEHEIGHT * 5;

	public FrightenedTopLeftMid(Ghost ghost)
	{
		this.ghost = ghost;
		tempSpeed = ghost.getSpeed() / 2;
	}
	
	public void runAway()
	{
		ghost.makeNextMove(FrightenedTopLeftMid.DEST_X, FrightenedTopLeftMid.DEST_Y, tempSpeed);
	}
	
}
