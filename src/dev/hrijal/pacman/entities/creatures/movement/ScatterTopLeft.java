package dev.hrijal.pacman.entities.creatures.movement;

import dev.hrijal.pacman.entities.creatures.Ghost;
import dev.hrijal.pacman.tiles.Tile;

public class ScatterTopLeft implements ScatterBehavior
{
	
	private Ghost ghost;
	public static final float DEST_X = Tile.TILEWIDTH + 7,
							  DEST_Y = Tile.TILEHEIGHT * 2 + 7;

	public ScatterTopLeft(Ghost ghost)
	{
		this.ghost = ghost;
	}
	
	public void scatter()
	{
		ghost.makeNextMove(ScatterTopLeft.DEST_X, ScatterTopLeft.DEST_Y);
	}
	
}
