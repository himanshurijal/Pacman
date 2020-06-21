package dev.hrijal.pacman.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PathFinding 
{
	
	int solid = 1;
	int startPoint = 2;
	int endPoint = 3;
	int currParentIndex = 0;
	
	int[] startCoordinates = {5,1};
	int[] endCoordinates = {4,7};
	
	List<List<List<Integer>>> adjNodeValues; //Store values in the order of node coordinates, G, H, F 
									 //for all the adjacent nodes of the current block being looked at
	List<List<Integer>> pathCoordinates;
	
	int [][] grid = new int[10][10];
	
	public static void main(String [] args)
	{
		PathFinding test = new PathFinding();
		test.setupGrid();
		test.go();
	}
	
	public void setupGrid()
	{
		adjNodeValues = new ArrayList<>();
		pathCoordinates = new ArrayList<>();
		pathCoordinates.add(Arrays.asList(startCoordinates[0],startCoordinates[1]));
		
		for(int i = 0; i < grid.length; i++)
		{
			for(int j = 0; j  < grid[0].length; j++)
			{
				if( ((i == 2 || i == 7) && j >= 2 && j <= 6) || (i >= 3 && i <= 7 && j == 6) )
				{
					grid[i][j] = solid;
				}
				else if(i == startCoordinates[0] && j == startCoordinates[1])
				{
					grid[i][j] = startPoint;
				}
				else if(i == endCoordinates[0] && j == endCoordinates[1])
				{
					grid[i][j] = endPoint;
				}
				
				if(grid[i][j] == startPoint)
					System.out.print("S ");
				else if(grid[i][j] == endPoint)
					System.out.print("E ");
				else if(grid[i][j] == solid)
					System.out.print("| ");
				else 
					System.out.print("_ ");
			}
			System.out.println("");
		}
	}
	
	public void go()
	{
		int currNodeIndex = 0;
		
		if(endCoordinates[0] >= grid.length || endCoordinates[1] >= grid[0].length ||
													grid[endCoordinates[0]][endCoordinates[1]] == 1)
		{
			System.out.println("Unreachable destination!");
		}
		else
		{
			while(pathCoordinates.indexOf(Arrays.asList(endCoordinates[0],endCoordinates[1])) == -1)
			{
				//System.out.println("here");
				int x = pathCoordinates.get(currParentIndex).get(0);
				int y = pathCoordinates.get(currParentIndex).get(1);
				
				//System.out.println(x + "," + y);
				int currNodeX = 0;
				int currNodeY = 0;
				
				boolean isDeadEnd = true;
				
				List<List<Integer>> tempNodeValues = new ArrayList<>();
				
				if(y < grid[0].length - 1)
				{
					currNodeX = x;
					currNodeY = y + 1;
					
					//System.out.print(currNodeX + "," + currNodeY);
					
					if(isNodeValid(currNodeX, currNodeY, currNodeIndex))
					{
						tempNodeValues.add(Arrays.asList(currNodeX, currNodeY, 
								  getGValue(currNodeX, currNodeY, currParentIndex), getHValue(currNodeX, currNodeY),
								  getGValue(currNodeX, currNodeY, currParentIndex)+ getHValue(currNodeX, currNodeY)));	
						isDeadEnd = false;
					}

				}

				if(x < grid.length - 1)
				{
					currNodeX = x + 1;
					currNodeY = y;
					
					//System.out.print(" " + currNodeX + "," + currNodeY);
					
					if(isNodeValid(currNodeX, currNodeY, currNodeIndex))
					{
						tempNodeValues.add(Arrays.asList(currNodeX, currNodeY, 
								  getGValue(currNodeX, currNodeY, currParentIndex), getHValue(currNodeX, currNodeY),
								  getGValue(currNodeX, currNodeY, currParentIndex)+ getHValue(currNodeX, currNodeY)));	
						isDeadEnd = false;
					}
				}
				
				if(y > 0)
				{
					currNodeX = x;
					currNodeY = y - 1;
					
					//System.out.print(" " + currNodeX + "," + currNodeY);
					
					if(isNodeValid(currNodeX, currNodeY, currNodeIndex))
					{
						tempNodeValues.add(Arrays.asList(currNodeX, currNodeY, 
								  getGValue(currNodeX, currNodeY, currParentIndex), getHValue(currNodeX, currNodeY),
								  getGValue(currNodeX, currNodeY, currParentIndex)+ getHValue(currNodeX, currNodeY)));	
						isDeadEnd = false;
					}
				}

				if(x > 0) //Change order of adjacent node priority based on the position of destination point
						  //For eg: If E is below grid midpoint priority is R,D,L,U but if E is above midpoint
						  //priority is R,U,L,D
				{
					currNodeX = x - 1;
					currNodeY = y;
					
					//System.out.print(" " + currNodeX + "," + currNodeY);
					
					if(isNodeValid(currNodeX, currNodeY, currNodeIndex))
					{
						tempNodeValues.add(Arrays.asList(currNodeX, currNodeY, 
								  getGValue(currNodeX, currNodeY, currParentIndex), getHValue(currNodeX, currNodeY),
								  getGValue(currNodeX, currNodeY, currParentIndex)+ getHValue(currNodeX, currNodeY)));	
						isDeadEnd = false;
					}
				}


				if(isDeadEnd) //We need to backtrack and use another adjacent node for our path
				{
					pathCoordinates.remove(currParentIndex);
					System.out.println("Removed Dead End!");
					adjNodeValues.get(currNodeIndex - 1).remove(0);
					pathCoordinates.add(Arrays.asList(adjNodeValues.get(currNodeIndex - 1).get(0).get(0),
							adjNodeValues.get(currNodeIndex - 1).get(0).get(1)));
					
				}
				else
				{
					Collections.sort(tempNodeValues, new ListCompare());
					
					List<List<Integer>> tempNodeValuesDeepCopy = new ArrayList<>(tempNodeValues);
					
					adjNodeValues.add(tempNodeValuesDeepCopy);
					
					pathCoordinates.add(Arrays.asList(adjNodeValues.get(currNodeIndex).get(0).get(0),
							adjNodeValues.get(currNodeIndex).get(0).get(1)));
					
					
					currNodeIndex++;
					currParentIndex++;
					
					//System.out.println(tempNodeValuesDeepCopy);
				}
				
				tempNodeValues.clear();
				

				//System.out.println(adjNodeValues);
				System.out.println(pathCoordinates);
				System.out.println("");
			}
		}
		
	}
	
	public int getGValue(int x ,int y, int currParentIndex)
	{
		if(currParentIndex == 0) return 1;
		else return adjNodeValues.get(currParentIndex - 1).get(0).get(2) + 1;
	}
	
	public int getHValue(int x, int y)
	{
		return Math.abs(endCoordinates[0] - x) + Math.abs(endCoordinates[1] - y);		
	}
	
	public boolean isNodeValid(int currNodeX, int currNodeY, int currNodeIndex)
	{		
		if(grid[currNodeX][currNodeY] == 1)
		{
			return false;
		}
		else if(pathCoordinates.indexOf(Arrays.asList(currNodeX, currNodeY)) != -1)
		{
			return false;
		}
		
		for(int i = 0; i < currNodeIndex; i++)
		{
			for(int j = 0; j < adjNodeValues.get(i).size(); j++)
			{
				if(adjNodeValues.get(i).get(j).get(0) == currNodeX 
						&& adjNodeValues.get(i).get(j).get(1) == currNodeY)
				{
					return false;
				}
			}

		}
		
		return true;
	}
	
	public class ListCompare implements Comparator<List<Integer>>
	{
		@Override
		public int compare(List<Integer> o1, List<Integer> o2) 
		{
			if(o1.get(4) < o2.get(4) || (o1.get(4) == o2.get(4) && o1.get(3) < o2.get(3))) //Sort by F value but 
																						   //if F value is equal sort by H value
				return -1;																   
			else if(o1.get(4) > o2.get(4))
				return 1;
			else
				return 0;
		}
		
	}
	
}
