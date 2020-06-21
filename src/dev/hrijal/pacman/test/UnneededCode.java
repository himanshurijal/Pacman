package dev.hrijal.pacman.test;
//PLAYER 

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import dev.hrijal.pacman.tiles.Tile;

/*
public void checkNearestTileMid()
{
	int currTileX = Math.round(x / Tile.TILEWIDTH);
	int currTileY = Math.round(y / Tile.TILEHEIGHT);
	
	float currTileMidX = (float) (currTileX * Tile.TILEWIDTH + (currTileX * Tile.TILEWIDTH + Tile.TILEWIDTH)) / 2 ;
	float currTileMidY = (float) (currTileY * Tile.TILEHEIGHT + (currTileY * Tile.TILEHEIGHT + Tile.TILEHEIGHT)) / 2 ;
	
	int nextTileX = (int) (x + bounds.x + bounds.width + xMove) / Tile.TILEWIDTH;
	int nextTileY = (int) (y / Tile.TILEHEIGHT);
	
	float nextTileMidX = (float) (nextTileX * Tile.TILEWIDTH + (nextTileX * Tile.TILEWIDTH + Tile.TILEWIDTH)) / 2 ;
	float nextTileMidY = (float) (nextTileY * Tile.TILEHEIGHT + (nextTileY * Tile.TILEHEIGHT + Tile.TILEHEIGHT)) / 2 ;
	
	float playerMidX = (float) (this.x + (this.x +this.width)) / 2;
	float playerMidY = (float) (this.y + (this.y + this.height)) / 2 ;
	
	int nearestTileX, nearestTileY;
	float nearestTileMidX, nearestTileMidY;
	
	if(Math.abs(currTileMidX - playerMidX) <= Math.abs(nextTileMidX - playerMidX))
	{
		nearestTileX = currTileX;
		nearestTileMidX = currTileMidX;
	}
	else
	{
		nearestTileX = nextTileX;
		nearestTileMidX = nextTileMidX;
	}
	
	if(Math.abs(currTileMidY - playerMidY) <= Math.abs(nextTileMidY - playerMidY))
	{
		nearestTileY = currTileY;
		nearestTileMidY =  currTileMidY;
	}
	else
	{
		nearestTileY = nextTileY;
		nearestTileMidY = nextTileMidY;
	}
	
	if(handler.getKeyManager().right)
		System.out.println(nearestTileMidX + "," + nearestTileMidY + ") (" + playerMidX + "," + playerMidY );
	
	boolean isEdible =  handler.getWorld().getTile(nearestTileY, nearestTileX).isEdible();
	
	if(isEdible)
	{
		KeyManager key = handler.getKeyManager();
			
		if(key.right && playerMidX >= nearestTileMidX)
		{
			//System.out.println("Hit"+playerMidX + "," + tileMidX);
			handler.getWorld().setTile(nearestTileY, nearestTileX, 2);
		}
		else if(key.left && playerMidX <= nearestTileMidX)
		{
			//System.out.println("Hit"+playerMidX + "," + tileMidX);
			handler.getWorld().setTile(nearestTileY, nearestTileX, 2);
		}
		else if (key.down && playerMidY >= nearestTileMidY)
		{
			handler.getWorld().setTile(nearestTileY, nearestTileX, 2);
		}
		else if (key.up && playerMidY <= nearestTileMidY)
		{
			handler.getWorld().setTile(nearestTileY, nearestTileX, 2);
		}
	}
	
}
*/

/*
	public void eatEdible(int x, int y) //Check if current tile has an edible object in it
												//If there is an edible and player's midpoint X-Y (location is greater 
												//than edible X-Y remove the edible object and increase player score
	{
		int edibleIndex = checkIfExistsEdible(x,y);
				
		if(edibleIndex != -1)
		{
			float tileMidX = (float) (x * Tile.TILEWIDTH + (x * Tile.TILEWIDTH + Tile.TILEWIDTH)) / 2 ;
			float tileMidY = (float) (y * Tile.TILEHEIGHT + (y * Tile.TILEHEIGHT + Tile.TILEHEIGHT)) / 2 ;
			
			float playerMidX = (float) (this.x + (this.x +this.width)) / 2;
			float playerMidY = (float) (this.y + (this.y + this.height)) / 2 ;

			
			if(xMove > 0 && playerMidX >= tileMidX)
			{
				handler.getWorld().getEntityManager().getEntities().remove(edibleIndex);
			}
			else if(xMove < 0 && playerMidX <= tileMidX)
			{
				handler.getWorld().getEntityManager().getEntities().remove(edibleIndex);
			}
			else if (yMove > 0 && playerMidY >= tileMidY)
			{
				handler.getWorld().getEntityManager().getEntities().remove(edibleIndex);
			}
			else if (yMove < 0 && playerMidY <= tileMidY)
			{
				handler.getWorld().getEntityManager().getEntities().remove(edibleIndex);
			}
		}
	}
	
	public int checkIfExistsEdible(int x, int y)
	{
		float currTileX = (float) x * Tile.TILEWIDTH;
		float currTileY = (float) y * Tile.TILEHEIGHT;
		
		int index = 0;
		
		for(Entity e: handler.getWorld().getEntityManager().getEntities())
		{
			if(e.getX() == currTileX && e.getY() == currTileY)
			{
				return index;
			}
			index++;
		}
		
		return -1;
	}
*/


//ASSSETS

//ghostRed = creatureSheet.crop(0, 0, width, height);
//ghostPink = creatureSheet.crop(width, 0, width, height);
//ghostBlue = creatureSheet.crop(width * 2, 0, width, height);


//GHOST MOVEMENT

//class PathFinder
//{
//	
//	int [][] tiles; //This is our game world/grid
//	int worldWidth, worldHeight;
//	int startTileX, startTileY, destTileX, destTileY;
//	List<List<List<Integer>>> adjNodeValues; //Store values in the order of node coordinates, G, H, and  F 
//	 									     //for all the adjacent nodes of the current block being looked at
//	List<List<Integer>> pathCoordinates;
//	
//	PathFinder(float startX, float startY, float destX, float destY)
//	{		
//		tiles = handler.getWorld().getTilesArray();
//		worldWidth = handler.getWorld().getWidth();
//		worldHeight = handler.getWorld().getHeight();
//		
//		this.startTileX = (int) startX / Tile.TILEWIDTH;  
//		this.startTileY = (int) startY / Tile.TILEHEIGHT;      
//		this.destTileX = (int) destX / Tile.TILEWIDTH;  
//		this.destTileY = (int) destY / Tile.TILEHEIGHT; //At the end, don't forget to add/subtract X-Y values 
//													    //lost/gained in division.
//		System.out.println(startTileX+","+startTileY);
//		System.out.println(destTileX+","+destTileY);
//		adjNodeValues = new ArrayList<>();
//		pathCoordinates = new ArrayList<>();
//		pathCoordinates.add(Arrays.asList(startTileY, startTileX));
//	}
//	
//	public List<List<Integer>> returnPath()
//	{
//		int currNodeIndex = 0;
//		int currParentIndex = 0;
//		
//		if(destTileY >= worldHeight || destTileX >= worldWidth || tiles[destTileY][destTileX] == 3)
//		{
//			System.out.println("Unreachable destination!");
//		}
//		else
//		{
//			while(pathCoordinates.indexOf(Arrays.asList(destTileY, destTileX)) == -1)
//			{
//				int x = pathCoordinates.get(currParentIndex).get(0);
//				int y = pathCoordinates.get(currParentIndex).get(1);
//				
//				//System.out.println(x + "," + y);
//				int currNodeX = 0;
//				int currNodeY = 0;
//				
//				boolean isDeadEnd = true;
//				
//				List<List<Integer>> tempNodeValues = new ArrayList<>();
//				
//				if(y < worldWidth - 1) //R, D, L, U
//				{
//					currNodeX = x;
//					currNodeY = y + 1;
//					
//					//if(currNodeX == 15 && currNodeY == 1)System.out.print(currNodeX + "," + currNodeY);
//					
//					if(isNodeValid(currNodeX, currNodeY, currNodeIndex))
//					{
//						tempNodeValues.add(Arrays.asList(currNodeX, currNodeY, 
//								  getGValue(currNodeX, currNodeY, currParentIndex), getHValue(currNodeX, currNodeY),
//								  getGValue(currNodeX, currNodeY, currParentIndex)+ getHValue(currNodeX, currNodeY)));	
//						isDeadEnd = false;
//					}
//		
//				}
//		
//				if(x < worldHeight - 1)
//				{
//					currNodeX = x + 1;
//					currNodeY = y;
//					
//					//System.out.print(" " + currNodeX + "," + currNodeY);
//					
//					if(isNodeValid(currNodeX, currNodeY, currNodeIndex))
//					{
//						tempNodeValues.add(Arrays.asList(currNodeX, currNodeY, 
//								  getGValue(currNodeX, currNodeY, currParentIndex), getHValue(currNodeX, currNodeY),
//								  getGValue(currNodeX, currNodeY, currParentIndex)+ getHValue(currNodeX, currNodeY)));	
//						isDeadEnd = false;
//					}
//				}
//				
//				if(y > 0)
//				{
//					currNodeX = x;
//					currNodeY = y - 1;
//					
//					if(isNodeValid(currNodeX, currNodeY, currNodeIndex))
//					{
//						tempNodeValues.add(Arrays.asList(currNodeX, currNodeY, 
//								  getGValue(currNodeX, currNodeY, currParentIndex), getHValue(currNodeX, currNodeY),
//								  getGValue(currNodeX, currNodeY, currParentIndex)+ getHValue(currNodeX, currNodeY)));	
//						isDeadEnd = false;
//					}
//				}
//		
//				if(x > 0) //Change order of adjacent node priority based on the position of destination point
//						  //For eg: If E is below grid midpoint priority is R,D,L,U but if E is above midpoint
//						  //priority is R,U,L,D
//				{
//					currNodeX = x - 1;
//					currNodeY = y;
//				
//					if(isNodeValid(currNodeX, currNodeY, currNodeIndex))
//					{
//						tempNodeValues.add(Arrays.asList(currNodeX, currNodeY, 
//								  getGValue(currNodeX, currNodeY, currParentIndex), getHValue(currNodeX, currNodeY),
//								  getGValue(currNodeX, currNodeY, currParentIndex)+ getHValue(currNodeX, currNodeY)));	
//						isDeadEnd = false;
//					}
//				}
//		
//		
//				if(isDeadEnd) //We need to backtrack and use another adjacent node for our path
//				{
//					//pathCoordinates.remove(currParentIndex);
//					while(adjNodeValues.get(currNodeIndex - 1).size() == 1)
//					{
//						System.out.println("Removing Dead End!"+pathCoordinates.get(currParentIndex));
//						pathCoordinates.remove(currParentIndex);
//						adjNodeValues.remove(currNodeIndex - 1);
//						currNodeIndex--;
//						currParentIndex--;
//						System.out.println(pathCoordinates);
//					}
////					System.out.println("Removing Dead End!"+pathCoordinates.get(currParentIndex));
//					pathCoordinates.remove(currParentIndex);
//					adjNodeValues.get(currNodeIndex - 1).remove(0);
//					pathCoordinates.add(Arrays.asList(adjNodeValues.get(currNodeIndex - 1).get(0).get(0),
//							adjNodeValues.get(currNodeIndex - 1).get(0).get(1)));
//					//System.out.println(currNodeIndex+","+currParentIndex);
//					
//				}
//				else
//				{
//					Collections.sort(tempNodeValues, new ListCompare());
//					
//					List<List<Integer>> tempNodeValuesDeepCopy = new ArrayList<>(tempNodeValues);
//					
//					adjNodeValues.add(tempNodeValuesDeepCopy);
//					
////					if(currNodeX == 14 && currNodeY == 2)
////					{
////						System.out.print(currNodeX + "," + currNodeY);
////						System.out.println(adjNodeValues);
////					}
//					
//					pathCoordinates.add(Arrays.asList(adjNodeValues.get(currNodeIndex).get(0).get(0),
//							adjNodeValues.get(currNodeIndex).get(0).get(1)));
//					
//					
//					currNodeIndex++;
//					currParentIndex++;
//					
//					//System.out.println(tempNodeValuesDeepCopy);
//				}
//				
//				tempNodeValues.clear();
//				
//		
//				//System.out.println(adjNodeValues);
//				//System.out.println(pathCoordinates);
//				//System.out.println("");
//			}
//		}
//		return pathCoordinates;
//	}
//	
//	public int getGValue(int x ,int y, int currParentIndex)
//	{
//		if(currParentIndex == 0) return 1;
//		else return adjNodeValues.get(currParentIndex - 1).get(0).get(2) + 1;
//	}
//	
//	public int getHValue(int x, int y)
//	{
//		return Math.abs(destTileX - x) + Math.abs(destTileY - y);		
//	}
//	
//	public boolean isNodeValid(int currNodeX, int currNodeY, int currNodeIndex)
//	{		
//		if(tiles[currNodeX][currNodeY] == 3) //MazeTile ID  = 3
//		{
//			return false;
//		}
//		else if(pathCoordinates.indexOf(Arrays.asList(currNodeX, currNodeY)) != -1)
//		{
//			return false;
//		}
//		
//		for(int i = 0; i < currNodeIndex; i++)
//		{
//			for(int j = 0; j < adjNodeValues.get(i).size(); j++)
//			{
//				if(adjNodeValues.get(i).get(j).get(0) == currNodeX 
//						&& adjNodeValues.get(i).get(j).get(1) == currNodeY)
//				{
//					return false;
//				}
//			}
//
//		}
//		
//		return true;
//	}
//	
//	class ListCompare implements Comparator<List<Integer>>
//	{
//		@Override
//		public int compare(List<Integer> o1, List<Integer> o2) 
//		{
//			if(o1.get(4) < o2.get(4) || (o1.get(4) == o2.get(4) && o1.get(3) < o2.get(3))) //Sort by F value but 
//																						   //if F value is equal sort by H value
//				return -1;																   
//			else if(o1.get(4) > o2.get(4))
//				return 1;
//			else
//				return 0;
//		}	
//	}
//	
//}