package dev.hrijal.pacman.gfx;
import java.awt.image.BufferedImage;

public class SpriteSheet //Use SpriteSheet to increase efficiency while loading images. 
						 //Rather than loading each image individually load the sprite sheet with all the images at once
						 //and take it from there
{
	private BufferedImage sheet;
	
	public SpriteSheet(BufferedImage sheet)
	{
		this.sheet = sheet;
	}
	
	public BufferedImage crop (int x, int y, int width, int height)
	{
		return sheet.getSubimage(x,y,width,height);
	}
}