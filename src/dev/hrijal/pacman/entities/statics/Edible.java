package dev.hrijal.pacman.entities.statics;

import java.awt.Color;
import java.awt.Graphics;

import dev.hrijal.pacman.Handler;
import dev.hrijal.pacman.entities.Entity;
import dev.hrijal.pacman.gfx.Assets;

public class Edible extends StaticEntity
{
	
	public Edible(Handler handler, float x, float y)
	{
		super(handler, x, y, 10);
		
		entityCollisionBounds.x = Entity.ENTITY_WIDTH / 2 - 5;;
		entityCollisionBounds.y = Entity.ENTITY_HEIGHT / 2 - 5;
		entityCollisionBounds.width = 10;
		entityCollisionBounds.height = 10;
	}

	@Override
	public void tick() 
	{
		
	}

	@Override
	public void render(Graphics g) 
	{
		g.drawImage(Assets.edible, (int) x, (int) y, Entity.ENTITY_WIDTH, Entity.ENTITY_HEIGHT, null);
		g.setColor(Color.white);
		g.drawRect((int) x + entityCollisionBounds.x, (int) y + entityCollisionBounds.y,
							 entityCollisionBounds.width, entityCollisionBounds.height);
	}
	
}
