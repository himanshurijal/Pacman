package dev.hrijal.pacman.entities.creatures.ghostMovement;

import dev.hrijal.pacman.entities.creatures.Ghost;
import dev.hrijal.pacman.tiles.Tile;

public class FrightenedRunMiddle implements FrightenedBehavior
{
	
	private Ghost ghost;
	private float tempSpeed;
	public static final float DEST_X = Tile.TILEWIDTH * 11,
							  DEST_Y = Tile.TILEHEIGHT * 9;

	public FrightenedRunMiddle(Ghost ghost)
	{
		this.ghost = ghost;
		tempSpeed = ghost.getSpeed() / 2;
	}
	
	public void runAway()
	{
		ghost.makeNextMove(FrightenedRunMiddle.DEST_X, FrightenedRunMiddle.DEST_Y, tempSpeed);
	}
	
}
