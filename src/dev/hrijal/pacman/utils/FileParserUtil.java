package dev.hrijal.pacman.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public final class FileParserUtil 
{

	public static String loadFileAsString(String path)
	{
		StringBuilder builder = new StringBuilder();
		
		try 
		{
			BufferedReader reader = new BufferedReader(new FileReader(path));
			String line = null;
			while((line = reader.readLine()) != null)
			{
				builder.append(line + "\n");
			}
			reader.close();
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		return builder.toString();
	}
	
	public static int parseInt(String number)
	{
		try
		{
			int num = Integer.parseInt(number);
			return num;
		}
		catch (NumberFormatException ex)
		{
			ex.printStackTrace();
			return 0;
		}
	}
	
}
