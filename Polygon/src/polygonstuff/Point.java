package polygonstuff;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;

public class Point {
	
	private int x, y;
	private Label lbl = new Label();
	
	private String name;
	
	public Point()
	{
		x = 0;
		y = 0;
	}
	
	public Point(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	public int calcQuadrant()
	{
		int quadrant = 0;
		
		if (x > 0 && y > 0)
			quadrant = 1;
		else if (x < 0 && y > 0)
			quadrant = 2;
		else if (x < 0 && y < 0)
			quadrant = 3;
		else if (x > 0 && y < 0)
			quadrant = 4;
		else if (x == 0 || y == 0)
			quadrant = 0;
		
		return quadrant;
	}
	
	public void setLabel(int num)
	{
		lbl.setLayoutX(x - 20);
		lbl.setLayoutY(y - 20);
		lbl.setText(Integer.toString(num + 1));
	}
	
	public void drawPoint(GraphicsContext gc)
	{
		gc.fillOval(x - 5, y - 5, 10, 10);
	}
	
	public String toString()
	{
		String output;
		
		output = "(" + x + ", " + y + ")";
		
		return output;
	}
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
	
	public void setX(int x)
	{
		this.x = x;
	}
	
	public void setY(int y)
	{
		this.y = y;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getName()
	{
		return name;
	}

}

