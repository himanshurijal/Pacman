package dev.hrijal.pacman.entities.creatures.ghosts.movement;

import dev.hrijal.pacman.entities.creatures.ghosts.Ghost;
import dev.hrijal.pacman.tiles.Tile;

public class DeadRunHome implements DeadBehavior
{
	private Ghost ghost;
	private float tempSpeed;
	public static final float DEST_X = Tile.TILEWIDTH * 11,
			   				  DEST_Y = Tile.TILEHEIGHT * 11;

	public DeadRunHome(Ghost ghost)
	{
		this.ghost = ghost;
		tempSpeed = ghost.getSpeed() * 2;
	}

	public void runToHome()
	{
		ghost.makeNextMove(DEST_X, DEST_Y, tempSpeed);
	}
}
