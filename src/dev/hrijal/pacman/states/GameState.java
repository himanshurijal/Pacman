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
		
		// Works with jar file (and Eclipse)
//		world = new World("dev/hrijal/pacman/res/worlds/world.txt", handler);
		
		// From ClassLoader, all paths are "absolute" already - there's no context
		// from which they could be relative. Therefore you don't need a leading slash.
		// Check FileParserUtils class.
		world = new World("worlds/world.txt", handler);

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