package dev.hrijal.pacman.tiles;

import dev.hrijal.pacman.entities.creatures.ghosts.Ghost;
import dev.hrijal.pacman.entities.creatures.ghosts.states.DeadState;
import dev.hrijal.pacman.entities.creatures.ghosts.states.ScatteredState;
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

	@Override
	public boolean isSolid(Ghost ghost)
	{
		if(ghost.getState() instanceof ScatteredState || ghost.getState() instanceof DeadState)
		{
			return false;
		}
		else
		{
			return true;
		}	
	}
	
}
