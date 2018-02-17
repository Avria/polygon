package polygonstuff;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class Polygonfile {
	
	private static ArrayList<Polygon> arrPol = new ArrayList<Polygon>();
	
	public void open()
	{
		File file = new File("polygon.txt");
		File colorfile = new File("colors.txt");
		
		ArrayList<Integer> arrColors = new ArrayList<Integer>();
		
		Scanner input = null;
		Scanner colorinput = null;
		
		ArrayList<Point> arrbegin = new ArrayList<Point>();
		ArrayList<Point> arrend = new ArrayList<Point>();
				
		try 
		{
			input = new Scanner(file);
			colorinput = new Scanner(colorfile);
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
		
		input.useDelimiter("~");
		colorinput.useDelimiter("~");
		
		while (colorinput.hasNext())
		{
			arrColors.add(Integer.valueOf(colorinput.next()));
		}
		
		String str;
		int index = 0;
		
		while (input.hasNext())
		{
			str = input.next();
			
			if ((Objects.equals(str, new String("!\r\n")) == true) || (Objects.equals(str, new String("!")) == true))
			{
				arrPol.add(new Polygon(arrbegin, arrend, arrColors.get(index)));
				
				index++;
				arrbegin.clear();
				arrend.clear();
			}
			else 
			{
				arrbegin.add(new Point(Integer.valueOf(str), input.nextInt()));
				arrend.add(new Point(input.nextInt(), input.nextInt()));
			}
		}
		
		input.close();	
		colorinput.close();
	}
	
	public void addNewLineseg(int index, Lineseg l)
	{
		arrPol.get(index).addNewLineseg(l);
	}
	
	public void newPolygon()
	{
		arrPol.add(new Polygon());
	}
	
	public int getSize()
	{
		int size = arrPol.size();
		
		return size;
	}
	
	public void delete(int index)
	{
		arrPol.remove(index);																							
	}
	
	public void addNewItem(ArrayList<Point> arrbegin, ArrayList<Point> arrend, String color, int colornum)																
	{	
		Polygon newpol = new Polygon(arrbegin, arrend, color, colornum);
		arrPol.add(newpol);																					
	}
	
	public String[] listAllItems()																					
	{
		String[] itemList = new String[arrPol.size()];																
		
		for (int i = 0; i < arrPol.size(); i++)							
		{
			itemList[i] = arrPol.get(i).toString();
		}
		
		return itemList;																						
	}
	
	public Polygon getPolygon(int index)
	{
		return arrPol.get(index);
	}
	
	public double getPerimeter(int index)
	{
		return arrPol.get(index).calcPerimeter();
	}
	
	public int getNumPoints(int index)
	{
		return arrPol.get(index).getSize();
	}
	
	public int getColornum(int index)
	{
		int colornum;
		
		colornum = arrPol.get(index).getColornum();
		
		return colornum;
	}
	
	public double getArea(int index)
	{
		return arrPol.get(index).calcArea();
	}
	
	public void save()															
	{
		PrintWriter output = null;
		
		try 
		{
			output = new PrintWriter("polygon.txt");
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
		
		for (int i = 0; i < arrPol.size(); i++)
		{
			if (i != 0)
				output.print("~");
			
			output.print(arrPol.get(i).toOutput());
			
			if (i != arrPol.size() - 1)
				output.println();
		}
		
		output.close();
		
		try 
		{
			output = new PrintWriter("colors.txt");
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
		
		for (int i = 0; i < arrPol.size(); i++)
		{	
			output.print(arrPol.get(i).getColornum() + "~");
		}
		
		output.close();
	}

}
