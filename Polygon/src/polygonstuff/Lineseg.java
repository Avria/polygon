package polygonstuff;

import javafx.scene.canvas.GraphicsContext;

public class Lineseg {
	
	private Point p1, p2;
	
	public Lineseg()
	{
		p1 = new Point();
		p2 = new Point();
	}
	
	public Lineseg(int x1, int x2, int y1, int y2)
	{
		p1 = new Point(x1, y1);
		p2 = new Point(x2, y2);
	}
	
	public Lineseg(Point p1, Point p2)
	{
		this.p1 = p1;
		this.p2 = p2;
	}
	
	public void setP1(Point p1)
	{
		this.p1 = p1;
	}
	
	public void setP2(Point p2)
	{
		this.p2 = p2;
	}
	
	public Point getRealP1()
	{
		return p1;
	}
	
	public Point getRealP2()
	{
		return p2;
	}
	
	public void drawLineseg(GraphicsContext gc)
	{
		gc.strokeLine(p1.getX(), p1.getY(), p2.getX(), p2.getY());
		p1.drawPoint(gc);
		p2.drawPoint(gc);
	}
	
	public Point getP1()
	{
		int x, y;
		
		x = p1.getX();
		y = p1.getY();
		
		int currentx = x;
	    int currenty = y;
	    
	    final int width = 600;
	    final int height = 600;
	    
	    int userx = 0, usery = 0;
    	
	    if (currentx > (width / 2))
    	{
    		userx = currentx - (width/2);
    	}
    	else if (currentx < (width / 2))
    	{
    		userx = (width / 2 - currentx) * -1;
    	}
    	else if (currentx == (width / 2))
    	{
    		userx = 0;
    	}
    	if ( (currenty < (height / 2)) || (currenty > (height) / 2) )
    	{
    		usery =  (currenty - (height / 2)) * -1;
    	}
    	else if (currenty == (height / 2))
    	{
    		usery = 0;
    	}

		Point userp1 = new Point(userx, usery);
		
		return userp1;
	}
	
	public Point getP2()
	{
		int x, y;
		
		x = p2.getX();
		y = p2.getY();
		
		int currentx = x;
	    int currenty = y;
	    
	    final int width = 600;
	    final int height = 600;
	    
	    int userx = 0, usery = 0;
    	
	    if (currentx > (width / 2))
    	{
    		userx = currentx - (width/2);
    	}
    	else if (currentx < (width / 2))
    	{
    		userx = (width / 2 - currentx) * -1;
    	}
    	else if (currentx == (width / 2))
    	{
    		userx = 0;
    	}
    	if ( (currenty < (height / 2)) || (currenty > (height) / 2) )
    	{
    		usery =  (currenty - (height / 2)) * -1;
    	}
    	else if (currenty == (height / 2))
    	{
    		usery = 0;
    	}

		Point userp2 = new Point(userx, usery);
		
		return userp2;
	}
	
	public double calcSlope()
	{
	 
		double slope = (p2.getY() - p1.getY() / (double)(p2.getX() - p1.getX()));
		
		return slope;
	}
	
	public void setLabelP1(int num)
	{
		p1.setLabel(num);
	}
	
	public void setLabelP2(int num)
	{
		// only for the last point since ending to beginning lineseg isn't a real one
		p2.setLabel(num);
	}
	
	public String calcMidpoint()
	{
		int x, y;
		
		x = (p1.getX() + p2.getX()) / 2;
		y = (p1.getY() + p2.getY()) / 2;
		
		int currentx = x;
	    int currenty = y;
	    
	    final int width = 600;
	    final int height = 600;
	    
	    int userx = 0, usery = 0;
    	
	    if (currentx > (width / 2))
    	{
    		userx = currentx - (width/2);
    	}
    	else if (currentx < (width / 2))
    	{
    		userx = (width / 2 - currentx) * -1;
    	}
    	else if (currentx == (width / 2))
    	{
    		userx = 0;
    	}
    	if ( (currenty < (height / 2)) || (currenty > (height) / 2) )
    	{
    		usery =  (currenty - (height / 2)) * -1;
    	}
    	else if (currenty == (height / 2))
    	{
    		usery = 0;
    	}

		Point midpoint = new Point(userx, usery);
		
		return midpoint.toString();
	}
	
	public double calcDistance()
	{
		double distance;
		
		distance = ((p2.getY() - p1.getY()) * (p2.getY() - p1.getY()) + (p2.getX() - p1.getX()) * (p2.getX() - p1.getX()));
		distance = Math.sqrt(distance);
		
		return distance;
	}
	
	public String toString()
	{
		String output;
		
		output = p1.getX() + "~" + p1.getY() + "~" + p2.getX() + "~" + p2.getY() + "~";
		
		return output;
	}
	
	public String lastToString()
	{
		String output;
		
		output = p1.getX() + "~" + p1.getY() + "~" + p2.getX() + "~" + p2.getY() + "~!";
		
		return output;
	}
	
}
