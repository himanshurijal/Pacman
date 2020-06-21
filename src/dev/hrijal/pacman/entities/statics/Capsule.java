package dev.hrijal.pacman.entities.statics;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import dev.hrijal.pacman.Handler;
import dev.hrijal.pacman.entities.Entity;
import dev.hrijal.pacman.gfx.Animation;
import dev.hrijal.pacman.gfx.Assets;

public class Capsule extends StaticEntity
{
	
	private Animation anim;
	
	public Capsule(Handler handler, float x, float y)
	{
		super(handler, x, y, 50);
		
		anim = new Animation(Assets.capsule, 200);
	
		entityCollisionBounds.x = Entity.ENTITY_WIDTH / 2 - 5;;
		entityCollisionBounds.y = Entity.ENTITY_HEIGHT / 2 - 5;
		entityCollisionBounds.width = 10;
		entityCollisionBounds.height = 10;
	}

	@Override
	public void tick() 
	{	
		anim.tick();
	}

	@Override
	public void render(Graphics g)
	{
		g.drawImage(getCurrentAnimationFrame(), (int) x, (int) y, Entity.ENTITY_WIDTH, Entity.ENTITY_HEIGHT, null);
	}
	
	public BufferedImage getCurrentAnimationFrame()
	{
		return anim.getCurrentFrame();
	}
	
}
