package dev.hrijal.pacman.entities.creatures.ghostMovement;

import dev.hrijal.pacman.entities.creatures.ghosts.Ghost;
import dev.hrijal.pacman.tiles.Tile;

public class FrightenedBottomLeftMid implements FrightenedMode
{
	
	private Ghost ghost;
	private float tempSpeed;
	public static final float DEST_X = Tile.TILEWIDTH * 5,
			  				  DEST_Y = Tile.TILEHEIGHT * 16;

	public FrightenedBottomLeftMid(Ghost ghost)
	{
		this.ghost = ghost;
		tempSpeed = ghost.getSpeed() / 2;
	}
	
	public void runAway()
	{
		ghost.makeNextMove(FrightenedBottomLeftMid.DEST_X, FrightenedBottomLeftMid.DEST_Y, tempSpeed);
	}
	
}
