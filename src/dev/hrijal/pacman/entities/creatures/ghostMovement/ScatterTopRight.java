package dev.hrijal.pacman.entities.creatures.ghostMovement;

import dev.hrijal.pacman.entities.creatures.ghosts.Ghost;
import dev.hrijal.pacman.tiles.Tile;

public class ScatterTopRight implements ScatterMode
{
	
	private Ghost ghost;
	private float tempSpeed;
	public static final float DEST_X = Tile.TILEWIDTH * 22 - Tile.TILEWIDTH,
							  DEST_Y = Tile.TILEHEIGHT * 2 + 7;

	public ScatterTopRight(Ghost ghost)
	{
		this.ghost = ghost;
		tempSpeed= ghost.getSpeed();
	}
	
	public void scatter()
	{
		ghost.makeNextMove(ScatterTopRight.DEST_X, ScatterTopRight.DEST_Y, tempSpeed);
	}

}
