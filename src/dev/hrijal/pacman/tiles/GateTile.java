package dev.hrijal.pacman.tiles;

import dev.hrijal.pacman.gfx.Assets;

public class GateTile extends Tile 
{
	public GateTile(int id)
	{
		super(Assets.gate, id);
	}
	
	@Override
	public boolean isSolid()
	{
		return true;
	}
}
