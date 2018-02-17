package polygonstuff;

import java.util.ArrayList;
import javafx.scene.canvas.GraphicsContext;

public class Polygon {
	
	private Lineseg[] arrlines = new Lineseg[15];
	
	private int sides; 
	private String color;
	private int colornum;

	public Polygon()
	{
		for (int i = 0; i < 15; i++)
		{
			arrlines[i] = new Lineseg();
		}
		
		sides = 0;
	}
	
	public Polygon(ArrayList<Point> arrbegin, ArrayList<Point> arrend, int colornum)
	{	
		String[] arrcolornames = {"Light Pink", "Cornflower Blue", "Light Green", "Khaki", "Thistle"};
		
		sides = arrbegin.size();
		
		for (int i = 0; i < sides; i++)
		{
			arrlines[i] = new Lineseg(arrbegin.get(i), arrend.get(i));
		}
		
		this.colornum = Integer.valueOf(colornum);
		this.color = arrcolornames[Integer.valueOf(colornum)];
	}
	
	public Polygon(int numSides)
	{
		for (int i = 0; i < numSides; i++)
		{
			arrlines[i] = new Lineseg();
		}
		
		sides = numSides;
	}
	
	public Polygon(ArrayList<Point> arrbegin, ArrayList<Point> arrend, String color, int colornum)
	{
		for (int i = 0; i < arrbegin.size(); i++)
		{
			arrlines[i] = new Lineseg(arrbegin.get(i), arrend.get(i));
		}
		
		sides = arrbegin.size();
		
		this.color = color;
		this.colornum = colornum;
	}
	
	public void addNewLineseg(Lineseg l)
	{
		arrlines[sides] = l;
		sides++;
	}
	
	public Lineseg getLineseg(int index)
	{
		return arrlines[index];
	}
	
	public void drawPolygon(GraphicsContext gc)
	{
		arrlines[sides] = new Lineseg();
		
		arrlines[sides].setP1(arrlines[sides - 1].getRealP2());
		arrlines[sides].setP2(arrlines[0].getRealP1());
		
		sides++;
				
		for (int i = 0; i < sides; i++)
		{
			arrlines[i].drawLineseg(gc);
		}
		
		sides--;
		
		arrlines[sides].setP1(new Point(0, 0));
		arrlines[sides].setP2(new Point(0, 0));
		
	}
	
	public String toString()
	{
		String output = color + " " + (sides + 1);
		
		return output;
	}
	
	public String[] getArrPoints()
	{
		String[] arrPoints = new String[15];
		
		for (int i = 0; i < sides; i++)
		{
			arrPoints[i] = arrlines[i].getP1().toString();
		}
		
		arrPoints[sides] = arrlines[sides - 1].getP2().toString();
		
		return arrPoints;
	}
	
	public String toOutput()
	{
		String output = "";
		
		for (int i = 0; i < sides; i++)
		{
			if (i != sides - 1)
				output += arrlines[i].toString();
			else
				output += arrlines[i].lastToString();
		}
		
		return output;
	}
	
	public int getSize()
	{
		return sides;
	}
	
	public void setSides(int sides)
	{
		this.sides = sides;
		
		for (int i = sides; i < 15; i++)
		{
			arrlines[i] = null;
		}
	}
	
	public void setColornum(int colornum)
	{
		this.colornum = colornum;
	}
	
	public int getColornum()
	{
		return colornum;
	}
	
	public String getColor()
	{
		return color;
	}
	
	public void setColor(String color)
	{
		this.color = color;
	}
	
	public double calcPerimeter()
	{
		double perimeter = 0;
		
		for (int i = 0; i < sides; i++)
		{
			perimeter += arrlines[i].calcDistance();
		}
		
		return perimeter;
	}
	
	public double calcArea()
	{
		double area1 = 0, area2 = 0, area;
		
		arrlines[sides] = new Lineseg(arrlines[sides - 1].getRealP2(), arrlines[0].getRealP1());
		sides++;
				
		for (int i = 0; i < sides; i++)
		{
			area1 += arrlines[i].getP1().getX() * arrlines[i].getP2().getY();	
		}
		
		for (int i = 0; i < sides; i++)
		{
			area2 += arrlines[i].getP1().getY() * arrlines[i].getP2().getX();
		}
		
		area = area1 - area2;
		area = Math.abs(area) * .5;
		
		arrlines[sides] = null;
		sides--;
		
		return area;
	}
	
	public void setPoints(int index, Point p1, Point p2)
	{
		arrlines[index].setP1(p1);
		arrlines[index].setP2(p2);
	}
	
	public Point getRealPointP1(int index)
	{
		return arrlines[index].getRealP1();
	}
	
	public Point getRealPointP2(int index)
	{
		return arrlines[index].getRealP2();
	}
	
	public Point getPointP1(int index)
	{	
		return arrlines[index].getP1();
	}
	
	public Point getPointP2(int index)
	{	
		return arrlines[index].getP2();
	}
	
	public double getDistance(int index)
	{
		return arrlines[index].calcDistance();
	}
	
	public double getSlope(int index)
	{
		return arrlines[index].calcSlope();
	}
	
	public String getMidpoint(int index)
	{
		return arrlines[index].calcMidpoint();
	}
}
