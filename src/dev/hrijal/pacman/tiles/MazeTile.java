package dev.hrijal.pacman.tiles;

import dev.hrijal.pacman.gfx.Assets;

public class MazeTile extends Tile 
{
	
	public MazeTile(int id)
	{
		super(Assets.maze, id);
	}
	
	@Override
	public boolean isSolid()
	{
		return true;
	}
	
}
