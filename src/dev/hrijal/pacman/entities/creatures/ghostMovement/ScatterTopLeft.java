package dev.hrijal.pacman.entities.creatures.ghostMovement;

import dev.hrijal.pacman.entities.creatures.Ghost;
import dev.hrijal.pacman.tiles.Tile;

public class ScatterTopLeft implements ScatterBehavior
{
	
	private Ghost ghost;
	private float tempSpeed;
	public static final float DEST_X = Tile.TILEWIDTH + 7,
							  DEST_Y = Tile.TILEHEIGHT * 2 + 7;

	public ScatterTopLeft(Ghost ghost)
	{
		this.ghost = ghost;
		tempSpeed = ghost.getSpeed();
	}
	
	public void scatter()
	{
		ghost.makeNextMove(ScatterTopLeft.DEST_X, ScatterTopLeft.DEST_Y, tempSpeed);
	}
	
}
