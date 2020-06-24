package dev.hrijal.pacman.entities.creatures.ghostMovement;

import dev.hrijal.pacman.entities.creatures.ghosts.Ghost;
import dev.hrijal.pacman.tiles.Tile;

public class ScatterBottomRight implements ScatterMode
{
	private Ghost ghost;
	private float tempSpeed;
	public static final float DEST_X = Tile.TILEWIDTH * 22 - Tile.TILEWIDTH,
							  DEST_Y = Tile.TILEHEIGHT * 16;

	public ScatterBottomRight(Ghost ghost)
	{
		this.ghost = ghost;
		tempSpeed = ghost.getSpeed();
	}
	
	public void scatter()
	{
		ghost.makeNextMove(ScatterBottomRight.DEST_X, ScatterBottomRight.DEST_Y, tempSpeed);
	}
}
