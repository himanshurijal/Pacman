package dev.hrijal.pacman.entities.creatures.ghostMovement;

import dev.hrijal.pacman.entities.creatures.ghosts.Ghost;
import dev.hrijal.pacman.tiles.Tile;

public class ScatterBottomLeft implements ScatterMode
{
	private Ghost ghost;
	private float tempSpeed;
	public static final float DEST_X = Tile.TILEWIDTH + 7,
							  DEST_Y = Tile.TILEHEIGHT * 16;

	public ScatterBottomLeft(Ghost ghost)
	{
		this.ghost = ghost;
		tempSpeed = ghost.getSpeed();
	}
	
	public void scatter()
	{
		ghost.makeNextMove(ScatterBottomLeft.DEST_X, ScatterBottomLeft.DEST_Y, tempSpeed);
	}
}
