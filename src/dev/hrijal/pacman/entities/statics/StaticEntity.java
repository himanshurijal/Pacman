package dev.hrijal.pacman.entities.statics;

import dev.hrijal.pacman.Handler;
import dev.hrijal.pacman.entities.Entity;

public abstract class StaticEntity extends Entity
{	
	
	public StaticEntity(Handler handler, float x, float y, int points)
	{
		super(handler, x, y, points);
	}
	
}
