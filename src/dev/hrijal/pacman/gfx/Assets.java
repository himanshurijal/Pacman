package dev.hrijal.pacman.gfx;

import java.awt.image.BufferedImage;

public class Assets //Game environment
{
	
	public static final int width = 50, height = 50;
	public static final int mazeWidth = 50, mazeHeight = 50;
	
	public static BufferedImage maze, gate, empty, edible, pacman;
	
	public static BufferedImage[] playerLeft, playerRight, playerDown, playerUp, playerDead,
								  ghostRed, ghostOrange, ghostGreen, ghostPurple, ghostFlashing,
								  ghostEyes, capsule;
	
	
	public static void init()
	{
		SpriteSheet creatureSheet = new SpriteSheet(ImageLoader.loadImage("/textures/creatureSheet.png"));
		SpriteSheet mazeSheet = new SpriteSheet(ImageLoader.loadImage("/textures/mazeSheet.png"));
		SpriteSheet gateSheet = new SpriteSheet(ImageLoader.loadImage("/textures/gateSheet.png"));
		
		maze = mazeSheet.crop(0, 0, mazeWidth, mazeHeight);
		gate = gateSheet.crop(0 + 20, 0, mazeWidth, mazeHeight);
		empty = creatureSheet.crop(width * 6, 0, mazeWidth, height);
		
		
		//EDIBLE ASSETS
		
		edible = creatureSheet.crop(width * 8, height * 5, width, height);
		
		capsule = new BufferedImage[2];
		capsule[0] = creatureSheet.crop(width * 8, height * 6, width, height);
		capsule[1] = empty;
		
	
		//GHOST ASSETS (Ghost assets for any ghost must be loaded in this order unless Spritesheet is changed)
		
		ghostRed = new BufferedImage[4];
		ghostOrange = new BufferedImage[4];
		ghostGreen = new BufferedImage[4];
		ghostPurple = new BufferedImage[4];
		
		ghostRed[0] = creatureSheet.crop(width * 0, height * 0, width, height); //right
		ghostRed[1] = creatureSheet.crop(width * 0, height * 2, width, height); //down
		ghostRed[2] = creatureSheet.crop(width * 0, height * 4, width, height); //left
		ghostRed[3] = creatureSheet.crop(width * 0, height * 6, width, height); //up
		
		ghostOrange[0] = creatureSheet.crop(width * 3, height * 0, width, height); //right
		ghostOrange[1] = creatureSheet.crop(width * 3, height * 2, width, height); //down
		ghostOrange[2] = creatureSheet.crop(width * 3, height * 4, width, height); //left
		ghostOrange[3] = creatureSheet.crop(width * 3, height * 6, width, height); //up
		
		ghostGreen[0] = creatureSheet.crop(width * 4, height * 0, width, height); //right
		ghostGreen[1] = creatureSheet.crop(width * 4, height * 2, width, height); //down
		ghostGreen[2] = creatureSheet.crop(width * 4, height * 4, width, height); //left
		ghostGreen[3] = creatureSheet.crop(width * 4, height * 6, width, height); //up
		
		ghostPurple[0] = creatureSheet.crop(width * 5, height * 0, width, height); //right
		ghostPurple[1] = creatureSheet.crop(width * 5, height * 2, width, height); //down
		ghostPurple[2] = creatureSheet.crop(width * 5, height * 4, width, height); //left
		ghostPurple[3] = creatureSheet.crop(width * 5, height * 6, width, height); //up
		
		//ADDITIONAL GHOST ASSETS
		
		ghostFlashing = new BufferedImage[2];
		ghostEyes = new BufferedImage[4];
		
		ghostFlashing[0] = creatureSheet.crop(width * 0, height * 11, width, height); //blue and white
		ghostFlashing[1] = creatureSheet.crop(width * 1, height * 11, width, height); //grey and red
		
		ghostEyes[0] = creatureSheet.crop(width * 6, height * 5, width, height); //right
		ghostEyes[1] = creatureSheet.crop(width * 6, height * 6, width, height); //down
		ghostEyes[2] = creatureSheet.crop(width * 6, height * 7, width, height); //left
		ghostEyes[3] = creatureSheet.crop(width * 6, height * 8, width, height); //up
		
		
		//PACMCAN ASSETS
		
		pacman = creatureSheet.crop(width * 17, 0, width, height);
		
		playerRight = new BufferedImage[3];
		playerDown = new BufferedImage[3];
		playerLeft = new BufferedImage[3];
		playerUp = new BufferedImage[3];
		playerDead = new BufferedImage[12];
		
		playerRight[0] = creatureSheet.crop(width * 17, height * 0, width, height);
		playerRight[1] = creatureSheet.crop(width * 17, height * 1, width, height);
		playerRight[2] = creatureSheet.crop(width * 17, height * 2, width, height);
		
		playerDown[0] = creatureSheet.crop(width * 17, height * 3, width, height);
		playerDown[1] = creatureSheet.crop(width * 17, height * 4, width, height);
		playerDown[2] = creatureSheet.crop(width * 17, height * 5, width, height);
		
		playerLeft[0] = creatureSheet.crop(width * 17, height * 6, width, height);
		playerLeft[1] = creatureSheet.crop(width * 17, height * 7, width, height);
		playerLeft[2] = creatureSheet.crop(width * 17, height * 8, width, height);

		playerUp[0] = creatureSheet.crop(width * 17, height * 9, width, height);
		playerUp[1] = creatureSheet.crop(width * 17, height * 10, width, height);
		playerUp[2] = creatureSheet.crop(width * 17, height * 11, width, height);
		
		playerDead[0] = creatureSheet.crop(width * 17, height * 0, width, height);
		playerDead[1] = creatureSheet.crop(width * 7, height * 0, width, height);
		playerDead[2] = creatureSheet.crop(width * 7, height * 1, width, height);
		playerDead[3] = creatureSheet.crop(width * 7, height * 2, width, height);
		playerDead[4] = creatureSheet.crop(width * 7, height * 3, width, height);
		playerDead[5] = creatureSheet.crop(width * 7, height * 4, width, height);
		playerDead[6] = creatureSheet.crop(width * 7, height * 5, width, height);
		playerDead[7] = creatureSheet.crop(width * 7, height * 6, width, height);
		playerDead[8] = creatureSheet.crop(width * 7, height * 7, width, height);
		playerDead[9] = creatureSheet.crop(width * 7, height * 8, width, height);
		playerDead[10] = creatureSheet.crop(width * 7, height * 9, width, height);
		playerDead[11] = creatureSheet.crop(width * 7, height * 10, width, height);
	}
	
}