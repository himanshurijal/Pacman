package dev.hrijal.pacman.entities.creatures.ghostMovement;

import dev.hrijal.pacman.entities.creatures.ghosts.Ghost;
import dev.hrijal.pacman.tiles.Tile;

public class FrightenedBottomRightMid implements FrightenedMode
{
	
	private Ghost ghost;
	private float tempSpeed;
	public static final float DEST_X = Tile.TILEWIDTH * 18,
			  				  DEST_Y = Tile.TILEHEIGHT * 16;

	public FrightenedBottomRightMid(Ghost ghost)
	{
		this.ghost = ghost;
		tempSpeed = ghost.getSpeed() / 2;
	}
	
	public void runAway()
	{
		ghost.makeNextMove(FrightenedBottomRightMid.DEST_X, FrightenedBottomRightMid.DEST_Y, tempSpeed);
	}
	
}
