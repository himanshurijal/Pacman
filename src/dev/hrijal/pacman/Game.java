package dev.hrijal.pacman;

import dev.hrijal.pacman.display.Display;
import dev.hrijal.pacman.gfx.Assets;
import dev.hrijal.pacman.input.KeyManager;
import dev.hrijal.pacman.input.MouseManager;
import dev.hrijal.pacman.states.GameState;
import dev.hrijal.pacman.states.MainMenuState;
import dev.hrijal.pacman.states.State;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class Game implements Runnable
{
	
	private Thread thread;
	private boolean isRunning;
	
	//DISPLAY
	private Display display;
	private String title;
	private int  width, height;

	private BufferStrategy bs;
	private Graphics g;
	
	//STATES 
	private State gameState;
	private State mainMenuState;
	
	//INPUT
	private KeyManager keyManager;
	private MouseManager mouseManager;
	
	//HANDLER
	private Handler handler;
	
	Game(String title, int width,int height)
	{
		//No significant additions
		this.isRunning = false;
		
		this.title = title;
		this.width = width;
		this.height = height;
	}

	private void init()
	{
		keyManager = new KeyManager();
		mouseManager = new MouseManager();
		
		display = new Display(title,width,height); 
		display.getFrame().addKeyListener(keyManager);
		display.getFrame().addMouseListener(mouseManager); 		 //Whichever one (JFrame or Canvas) is currently active
		display.getFrame().addMouseMotionListener(mouseManager); //will provide mouse events 
		display.getCanvas().addMouseListener(mouseManager);
		display.getCanvas().addMouseMotionListener(mouseManager);
		
		Assets.init();
		 
		handler = new Handler(this);
		
		gameState = new GameState(handler);
		State.setState(gameState); 
		
//		mainMenuState = new MainMenuState(handler);
//		State.setState(mainMenuState);
	}
	
	private void tick()
	{
		keyManager.tick();
		
		if(State.getState() != null)
		{
			State.getState().tick();
		}	
	}
	
	private void render() //Draw object in the screen
	{
		bs = display.getCanvas().getBufferStrategy();
		
		if(bs == null)
		{
			display.getCanvas().createBufferStrategy(3); //You'll never need more than three buffers
		}
		else
		{
			g = bs.getDrawGraphics(); //It's like a paint brush which allows us to draw graphics in the buffer/screen
			
			g.clearRect(0, 0, width, height);
			
			g.setColor(Color.black);
			g.fillRect(0, 0, width, height);
			
			if(State.getState() != null)
			{
				State.getState().render(g);
			}
			
			bs.show();
			g.dispose();
		}
	}
	
	public void run()
	{
		init();
		
		int fps = 40; //How many times every second we want the frame or tick and render method to run
		double timePerTick = 1000000000 / fps; //Time period after which each(1) frame will be run
											   //such that 60 frames will run in one second.
											   //NanoSeconds is used here for precision
		double delta  = 0;
		long now;
		long lastTime = System.nanoTime(); 
		int fpsCounter = 0;
		long timer = 0; //This is to check if total time past is equal to time period
						//after which 1 frame or tick and render method will run
		
		while(isRunning) //Update game variables and render to screen while running is true
		{
			now  = System.nanoTime();
			delta += (now - lastTime) / timePerTick; //How much time do we have until we can call another frame
													 //(tick and render methods)
			timer += now - lastTime;
			lastTime  = now;

			if(delta >= 1) //Delta will be one when amount of time past
						   //is equal to the time period after which 1 frame should run
			{
				tick();
				render();
				delta--;
				fpsCounter++;
			}
			
			if(timer >= 1000000000)
			{
//				System.out.println("Frames per second:"+fpsCounter);
				fpsCounter = 0;
				timer = 0;
			}
		}
		stop();
	}

	public synchronized void startGame()
	{
		if(isRunning)
		{
			return;
		}
		isRunning = true;
		
		thread = new Thread(this);
		thread.start();
	}
	
	public synchronized void stop() 
	{
		if(!isRunning)
		{
			return;
		}
		isRunning = false;
		
		try 
		{
			thread.join();
		} catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
	}
	
	
	//GETTERS AND SETTERS 

	public int getWidth() 
	{
		return width;
	}
	
	public int getHeight() 
	{
		return height;
	}

	public State getGameState() 
	{
		return gameState;
	}
	
	public void setGameState(State gameState) 
	{
		this.gameState = gameState;
	}

	public State getMainMenuState()
	{
		return mainMenuState;
	}
	
	public KeyManager getKeyManager()
	{
		return keyManager;
	}

	public MouseManager getMouseManager()
	{
		return mouseManager;
	}
	
}