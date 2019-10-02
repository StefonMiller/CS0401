// CS 0401 Fall 2015
// Outline of the MyPoly class that you must implement for Assignment 5.
// I have provided some data and a couple of methods for you, plus some method headers
// for the methods called from Assig5.java.  You must implement all of those methods but 
// you will likely want to add some other "helper" methods as well, and also some new
// instance variables (esp. for Assig5B.java).

import java.util.*;
import java.util.List;

import java.awt.*;
import java.awt.geom.*;
import java.awt.geom.Ellipse2D.Double;
import java.awt.image.BufferedImage;

public class MyPoly extends Polygon
{
	 // This ArrayList is how we will "display" the points in the MyPoly.  The idea is
	 // that a circle will be created for every (x,y) point in the MyPoly.  To give you
	 // a good start on this, I have implemented the constructors below.
	 private ArrayList<Ellipse2D.Double> thePoints;
	 private Color myColor;
	 private boolean highlighted;
	 private boolean flag;
	 private int index;
	 // Constructors.  These should work with Assig5.java but you may want to modify
	 // them for Assig5B.java.
	MyPoly()
	{
		super();
		myColor = Color.BLACK;
		thePoints = new ArrayList<Ellipse2D.Double>();
	}

	// This constructor should be a lot of help to see the overall structure of the
	// MyPoly class and how both the inherited Polygon functionality as well as the
	// additional functionality are incorporated.  Note that the first thing done is
	// a call to super to set up the points in the "regular" Polygon.  Then the color
	// is set and the point circles are created to correspond with each point in the
	// Polygon.
	MyPoly(int [] xpts, int [] ypts, int npts, Color col)
	{
		super(xpts, ypts, npts);
		myColor = col;
		thePoints = new ArrayList<Ellipse2D.Double>();
		for (int i = 0; i < npts; i++)
		{
			int x = xpts[i];
			int y = ypts[i];
			addCircle(x, y);
		}
	}
	
	// The setFrameFromCenter() method in Ellipse2D.Double allows the circles to be
	// centered on the points in the MyPoly
	public void addCircle(int x, int y)
	{
		Ellipse2D.Double temp = new Ellipse2D.Double(x, y, 8, 8);
		temp.setFrameFromCenter(x, y, x+4, y+4);
		thePoints.add(temp);
	}
     
	public void translate(int x, int y)
	{
		//Translate the polygon, remove all points, then redraw them translated
		super.translate(x, y);
		thePoints.removeAll(thePoints);
		for(int i = 0; i < npoints; i++)
		{
			addCircle(xpoints[i] + x,ypoints[i] + y);
		}
		
	}
    
   
	// This method is so simple I just figured I would give it to you. 	   
	public void setHighlight(boolean b)
	{
		highlighted = b;	
	}
     
	public void addPoint(int x, int y)
	{
		//Add a circle around the point
		super.addPoint(x, y);
		addCircle(x,y);
		// You must override this method to add a new point to the end of the
		// MyPoly.  The Polygon version works fine for the "regular" part of the
		// MyPoly but you must add the functionality to add the circle for the
		// point.	
	}
    public void removeAll()
    {
    	//Remove all points from the xpoints, ypoints, and thePoints arrays
    	thePoints.removeAll(thePoints);
    	for(int i = 0; i < npoints; i ++)
    	{
    		xpoints[i] = 0;
    		ypoints[i] = 0;
    	}
    }
	public MyPoly insertPoint(int x, int y)
	{	
		//To find the closest, use the given getClosest method
		int closest = getClosest(x,y);
		//initilization to avoid error
		int second = 0;
		//Add a point to the end of the xpoints and ypoints arrays
		addPoint(x,y);
		//if there is only 1 point, do nothing
		if (npoints == 1)
		{
			
		}
		else
		{
			//Similar to the provided method, this code uses the points themselves and the distance formula to find which point is second closest
			int minInd = 0;
			double currDist;
			double minDist;
			if(closest != 0)
			{
				//Calculate the distance from index 0 to the mouse as long as the closest point isn't index 0
				currDist = Math.sqrt(Math.pow((xpoints[0]-xpoints[(npoints - 1)]),2) + Math.pow((ypoints[0]-ypoints[(npoints - 1)]), 2));
				minDist = currDist;
				
				for(int i = 1; i < npoints - 1; i++)
				{
					//Determine which distance is shortest
					currDist = Math.sqrt(Math.pow((xpoints[i]-xpoints[(npoints - 1)]),2) + Math.pow((ypoints[i]-ypoints[(npoints - 1)]), 2));
					
					if (currDist < minDist && i != closest)
					{
						//If a distance is shorter than the min, set the min equal to it and the minindex equal to its index
						
						minDist = currDist;
						minInd = i;
						
					}
					else
					{
					}
					
				}
			}
			else
			{
				//Calculate the distance from index 1 to the mouse if the closest point is in index 0
				currDist = Math.sqrt(Math.pow((xpoints[1]-xpoints[(npoints - 1)]),2) + Math.pow((ypoints[1]-ypoints[(npoints - 1)]), 2));
				minDist = currDist;
				
				minInd = 1;
				for(int i = 2; i < npoints - 1; i++)
				{
					//Calculate all points' distances from the mouse
					currDist = Math.sqrt(Math.pow((xpoints[i]-xpoints[(npoints - 1)]),2) + Math.pow((ypoints[i]-ypoints[(npoints - 1)]), 2));
					
					if (currDist < minDist && i != closest)
					{
						//If one point is closer than the minDistance, set minDist equal to it and MinIndex equal to its index
						
						minDist = currDist;
						minInd = i;
						
					}
					else
					{
						
					}
					
				}
			}
			
			
			//Set second equal to the index of the second closest point
			second = minInd;
		}
		//If closest is greater than second, move all points from and including closest to the right 1 and insert the new point in closest's old index
		if(closest > second)
		{
			if(npoints == 0)
			{
				
			}
			else
			{
				//If second is 0, then leave the point at the end, if not, move the points over and insert it
				if(second == 0)
				{
					
				}
				else
				{
					//If the xpoints and ypoints arrays resize themselves, there is an error, this fixes this
					if(npoints%4 == 0)
					{
						int[] a = new int[npoints*2];
						int[] b = new int[npoints*2];
						for(int i = 0; i < npoints; i++)
						{
							a[i] = xpoints[i];
							b[i] = ypoints[i];
						}
						xpoints = a;
						ypoints = b;
					}
					int x1 = xpoints[npoints - 1];
					int y1 = ypoints[npoints - 1];
					for(int i = npoints; i > closest; i--)
					{
						xpoints[i] = xpoints[i-1];
						ypoints[i] = ypoints[i-1];
						
					}
					xpoints[closest] = x1;
					ypoints[closest] = y1;
				}
				
			}
			
			
			
		}
		//If second is greater than closest, move all points from and including second to the right 1 and insert the new point in second's old index
		else
		{
			//If second is 0, then leave the point at the end, if not, move the points over and insert it
			if(npoints == 0)
			{
				
			}
			else
			{
				if(closest == 0 && (second + 1) == (npoints -1))
				{
					
				}
				else
				{

					//If the xpoints and ypoints arrays resize themselves, there is an error, this fixes this
					if(npoints%4 == 0)
					{
						int[] a = new int[npoints*2];
						int[] b = new int[npoints*2];
						for(int i = 0; i < npoints; i++)
						{
							a[i] = xpoints[i];
							b[i] = ypoints[i];
						}
						xpoints = a;
						ypoints = b;
					}
					int x1 = xpoints[npoints - 1];
					int y1 = ypoints[npoints - 1];
					
					for(int i = npoints; i > second; i--)
					{
						xpoints[i] = xpoints[i-1];
						ypoints[i] = ypoints[i-1];
					}
					xpoints[second] = x1;
					ypoints[second] = y1;
				}
				
			}
		}
		for(int i = 0; i < npoints; i++)
		{
		}
		//Return the new myPoly 
		return new MyPoly(xpoints,ypoints,npoints,myColor);
		// Implement this method to return a new MyPoly containing new point (x,y)
		// inserted between the two points in the MyPoly that it is "closest" to.  See
		// the getClosest() method below for help with this.
	}
	
	// This method will return the index of the first point of the line segment that is
	// closest to the argument (x, y) point.  It uses some methods in the Line2D.Double
	// class and will be very useful when adding a point to the MyPoly.  Read through it
	// and see if you can figure out exactly what it is doing.
	public int getClosest(int x, int y)
	{
		if (npoints == 1)
			return 0;
		else
		{
			Line2D currSeg = new Line2D.Double(xpoints[0], ypoints[0], xpoints[1], ypoints[1]);
			double currDist = currSeg.ptSegDist(x, y);
			double minDist = currDist;
			int minInd = 0;
			for (int ind = 1; ind < npoints; ind++)
			{
				currSeg = new Line2D.Double(xpoints[ind], ypoints[ind],
								xpoints[(ind+1)%npoints], ypoints[(ind+1)%npoints]);
				currDist = currSeg.ptSegDist(x, y);
				if (currDist < minDist)
				{
					minDist = currDist;
					minInd = ind;
				}
			}
			return minInd;
		}
	}
	public void setFlag(Point p)
	{
		//If the point is within an elipse in thePoints, the set the flag to true true and set the index to i
		for(int i = 0; i < thePoints.size(); i++)
		{
			if(thePoints.get(i).contains(p))
			{
				flag = true;
				index = i;
				break;
			}
			else
			{
				flag = false;
			}
		}
		
	}
	public MyPoly removePoint(int x, int y)
	{
		
		//used to determine if a point was removed
		int orig = thePoints.size();
		for(int i = 0; i < thePoints.size(); i++)
		{
			//If the user right clicks within one of the poitns, remove it from the arraylist
			if(x >= thePoints.get(i).getMinX() && x <=thePoints.get(i).getMaxX() && y >= thePoints.get(i).getMinY() && y <= thePoints.get(i).getMaxY())
			{
				if(thePoints.size() == 0 || thePoints.size() == 1)
				{
					setHighlight(true);
					thePoints.removeAll(thePoints);
					return null;
				}
				else
				{
					setHighlight(true);
					thePoints.remove(i);
					xpoints[i] = 0;
					ypoints[i] = 0;
					npoints--;
				}
				
			}
			else
			{
				setHighlight(true);
			}
		}
		//If no point was removed, do nothing
		if(thePoints.size() == orig)
		{
			setHighlight(true);
			return this;
		}
		//If there are no more points, return null
		else if (thePoints.size() == 0)
		{		
			setHighlight(true);
			return null;
		}
		else
		{
			//If a point was removed and nothing weird happened(no points, etc) return a new MyPoly with the new amount of points
			for(int i = 0; i < thePoints.size(); i++)
			{
				xpoints[i] = (int) thePoints.get(i).getCenterX();
				ypoints[i] = (int) thePoints.get(i).getCenterY();
				setHighlight(true);
			}
			setHighlight(true);
			return new MyPoly(xpoints,ypoints,npoints,myColor);
		}
		// Implement this method to return a new MyPoly that is the same as the
		// original but without the "point" containing (x, y).  Note that in this
		// case (x, y) is not an actual point in the MyPoly but rather a location
		// on the screen that was clicked on my the user.  If this point falls within
		// any of the point circles in the MyPoly then the point in the MyPoly that
		// the circle represents should be removed.  If the resulting MyPoly has no
		// points (i.e. the last point has been removed) then this method should return
		// null.  If (x,y) is not within any point circles in the MyPoly then the
		// original, unchanged MyPoly should be returned.
	}

	public boolean contains(int x, int y)
	{
		//If there is 1 point, simply check whether or not it is in the elipse(if you had to click on the specific pixel it would be very hard to move it)
		if(npoints == 1)
		{
			if(x >= thePoints.get(0).getMinX() && x <=thePoints.get(0).getMaxX() && y >= thePoints.get(0).getMinY() && y <= thePoints.get(0).getMaxY())
			{
				
				return true;
			}
			else
			{
				
				return false;
			}
		}
		//If there are 2 points, get the slope the points make and make sure the y value of your mouse fits between the two points
		else if(npoints == 2)
		{
			double x1 = thePoints.get(0).getCenterX();
			double x2 = thePoints.get(1).getCenterX();
			double y1 = thePoints.get(0).getCenterY();
			double y2 = thePoints.get(1).getCenterY();
			
			//Find test and actual slopes
			double slope = ((double)(y1-y2)/(x2-x1));
			
			double testSlope;
			
			testSlope = ((double)(y-y1)/(x-x1));

			//If they are too far apart, return false
			if(Math.abs((Math.abs(testSlope) - Math.abs(slope))) > .025)
			{	
				return false;
			}
			if((x >= x1 && x <= x2) || (x >= x2 && x <= x1))
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		//If there are more than 3 points, simply use the super's method
		else
		{
			return(super.contains(x, y));
		}
		// Override this method. The Polygon contains() method works fine as long as
		// the Polygon has 3 or more points.  You will override the method so that if
		// a MyPoly has only 2 points or 1 point it will still work.  Think about how
		// you can do this.
	}
	
	public void draw(Graphics2D g)
	{
		//Set the color, draw all the points as long as there are more than 1 and it is highled
		g.setColor(myColor);
		for(int i = 0; i < thePoints.size(); i++)
		{
			if(highlighted || npoints == 1)
			{
				g.draw(thePoints.get(i));
			}
			g.draw(this);
		}
		//Only fill if the polygon is not highlighted 
		if(!highlighted)
		{
			g.fill(this);
		}
		//If the mouse is in an elipse, fill it
		if(flag)
		{
			g.fill(thePoints.get(index));
		}
		// Implement this method to draw the MyPoly onto the Graphics2D argument g.
		// See MyRectangle2D.java for a simple example of doing this.  In the case of
		// this MyPoly class the method is more complex, since you must handle the
		// special cases of 1 point (draw only the point circle), 2 points (drow the
		// line) and the case where the MyPoly is selected.  You must also use the
		// color of the MyPoly in this method.
	}
	  
	public String fileData()
	{
		//Create a stringbuilder to print out the coords and RGB
		StringBuilder S = new StringBuilder();
		int b = myColor.getBlue();
		int r = myColor.getRed();
		int g = myColor.getGreen();
		for(int i = 0; i < npoints; i ++)
		{
			//Properly format the coords and append each index
			S.append(xpoints[i] + ",");
			S.append(ypoints[i] + ":");
		}
		//Properly format the RGB and append it
		S.append("|" + r + "," + g + "," + b);
		//return the stringbuilder to string
		return S.toString();
		// Implement this method to return a String representation of this MyPoly
		// so that it can be saved into a text file.  This should produce a single
		// line that is formatted in the following way:
		// x1:y1,x2:y2,x3:y3, ... , |r,g,b
		// Where the points and the r,g,b values are separated by a vertical bar.
		// For two examples, see A5snap.htm and A5Bsnap.htm.
		// Look at the Color class to see how to get the r,g,b values.
	}

	// These methods are also so simple that I have implemented them.
	public void setColor(Color newColor)
	{
		myColor = newColor;
	}	
	
	public Color getColor()
	{
		return myColor;
	}		
	  
}