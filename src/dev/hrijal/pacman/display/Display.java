package dev.hrijal.pacman.display;
import javax.swing.*;
import java.awt.*;

public class Display
{
	
	private JFrame frame;
	private Canvas canvas; //We can draw graphics inside a canvas
	String title;
	int width,height;
	
	public Display(String title, int width, int height)
	{
		this.title = title;
		this.width = width;
		this.height = height;
		
		setupDisplay();
	}
	
	public void setupDisplay()
	{
		frame  = new JFrame(title);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(width,height);
		frame.setResizable(false); //Don't allow users to resize the window
		frame.setLocationRelativeTo(null); //Display the window in the center of the screen
		frame.setVisible(true);
		
		canvas = new Canvas();
		//Ensure that the canvas will always be of the size specified
		canvas.setPreferredSize(new Dimension(width,height)); //new Dimension(width,height)
		canvas.setMinimumSize(new Dimension(width,height));
		canvas.setMaximumSize(new Dimension(width,height));
		canvas.setFocusable(false); //Make it so that our JFrame is the only thing that can be focused

		frame.getContentPane().add(canvas);
		frame.pack(); //Resize our JFrame slightly so we can see the canvas fully
					  //Without this method we might not see all of the canvas
	}
	
	public Canvas getCanvas()
	{
		return canvas;
	}
	
	public JFrame getFrame()
	{
		return frame;
	}
	
}