package dev.hrijal.pacman.entities.creatures.movement;

import dev.hrijal.pacman.entities.creatures.Ghost;
import dev.hrijal.pacman.tiles.Tile;

public class FrightenedRunSlow implements FrightenedBehavior
{
	
	private Ghost ghost;
	public static final float DEST_X = Tile.TILEWIDTH * 11,
							   DEST_Y = Tile.TILEHEIGHT * 9;

	public FrightenedRunSlow(Ghost ghost)
	{
		this.ghost = ghost;
	}
	
	public void runAway()
	{
		ghost.makeNextMove(FrightenedRunSlow.DEST_X, FrightenedRunSlow.DEST_Y);
	}
	
}
