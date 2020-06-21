package dev.hrijal.pacman.states;

import java.awt.Graphics;

import dev.hrijal.pacman.Handler;
import dev.hrijal.pacman.worlds.World;

public class GameState extends State
{
	private World world;

	public GameState(Handler handler)
	{
		super(handler);
		
		world = new World("res/worlds/world.txt", handler);
		handler.setWorld(world);
	}
	
	public void tick()
	{
		handler.getWorld().tick();
	}
	
	public void render(Graphics g)
	{
		handler.getWorld().render(g);
	}
}