// CS 0401 Fall 2015
// Initial main program for Assignment 5.  Note that for the assignment you must
// 1) Implement the MyPoly class as specified so that this program will run as given
// 2) Add the extra functionality to this class as stated in the assignment sheet.

// See additional comments throughout this document.

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

public class Assig5b
{
    private final int NONE = 0, DRAW = 1, MODIFY = 2;  // State variables for the
    					// drawPanel.  See more details below in class ShapePanel
    private ShapePanel drawPanel;
    private JPanel buttonPanel;
    private JButton drawPoly, modifyPoints;  // Buttons to show in JFrame
    private JLabel msg;	
    private JFrame theFrame;
    private ArrayList<MyPoly> shapeList; 	// ArrayList of MyPoly objects
	private MyPoly newShape;
	private boolean flag = false;//Flag determining if the scene has changed
	private JMenuBar theBar;	// for menu options
	private JMenu fileMenu, editMenu;	// two menus will be used
	private JMenuItem endProgram, saveScene, saveAs, clear, open;  	// 4 menu items will be used in this
	private JMenuItem delItem, setColor, p2Back;		// program
	private int selindex, startInd;		// selindex is index of current selected MyPoly
										// startInd is index where search within list of
										// shapes will start
	private String currFile;	// filename in which to save the scene
    
    public Assig5b()
    {				// Initialize the GUI
		drawPanel = new ShapePanel(800, 500);
		shapeList = new ArrayList<MyPoly>();
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(1, 3));
		//Adding the new options to the menus
		drawPoly = new JButton("Draw");
		modifyPoints = new JButton("Modify");
		modifyPoints.setEnabled(false);

		ButtonHandler bhandler = new ButtonHandler();
		drawPoly.addActionListener(bhandler);
		modifyPoints.addActionListener(bhandler);

		buttonPanel.add(drawPoly);
		buttonPanel.add(modifyPoints);
		drawPanel.setMode(NONE);

		msg = new JLabel("");
		msg.setForeground(Color.BLUE);
		msg.setFont(new Font("TimesRoman", Font.BOLD, 14));
		buttonPanel.add(msg);
		theFrame = new JFrame("CS 0401 Assignment 5");
		drawPanel.setBackground(Color.white);
		theFrame.add(drawPanel, BorderLayout.NORTH);
		theFrame.add(buttonPanel, BorderLayout.SOUTH);

		// Note that way the menus are set up here.  JMenuItem objects generated
		// ActionEvents when clicked.  They are more or less just JButtons that happen
		// to be located within a menu rather than showing directly in the display.
		MenuHandler mhandler = new MenuHandler();
		theBar = new JMenuBar();
		theFrame.setJMenuBar(theBar);
		fileMenu = new JMenu("File");
		theBar.add(fileMenu);
		saveScene = new JMenuItem("Save");
		endProgram = new JMenuItem("Exit");
		saveAs = new JMenuItem("Save As");
		clear = new JMenuItem("New");
		open = new JMenuItem("Open");
		fileMenu.add(open);
		fileMenu.add(saveAs);
		fileMenu.add(clear);
		fileMenu.add(saveScene);
		fileMenu.add(endProgram);
		saveScene.addActionListener(mhandler);
		endProgram.addActionListener(mhandler);
		open.addActionListener(mhandler);
		clear.addActionListener(mhandler);
		saveAs.addActionListener(mhandler);


		editMenu = new JMenu("Edit");
		theBar.add(editMenu);
		delItem = new JMenuItem("Delete");
		setColor = new JMenuItem("Set Color");

		p2Back = new JMenuItem("Push to Back");
		p2Back.addActionListener(mhandler);
		delItem.addActionListener(mhandler);
		setColor.addActionListener(mhandler);
		p2Back.setEnabled(false);
		delItem.setEnabled(false);
		setColor.setEnabled(false);
		editMenu.add(delItem);
		editMenu.add(setColor);
		editMenu.add(p2Back);
		currFile = null;

		theFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		theFrame.pack();
		theFrame.setVisible(true);
	}

	// Handler for the buttons on the JFrame.  Note that both of these buttons "toggle"
	// when clicked.  Note also the implications of clicking each one -- see what is set
	// and then unset / reset upon a second click.
	private class ButtonHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if (e.getSource() == drawPoly)
			{
				if (drawPanel.currMode() != DRAW)
				{
					drawPanel.setMode(DRAW);	// Set the drawPanel so that it will
							// handle the actual drawing of the MyPoly.  This is because
							// the MouseListener to respond to the clicks is within the
							// ShapePanel class
					unSelect();
					msg.setText("Click points to draw new Polygon");
					drawPoly.setText("Finish Draw");
					modifyPoints.setEnabled(false);
					delItem.setEnabled(false);
					setColor.setEnabled(false);
					drawPanel.repaint();
				}
				else
				{
					drawPanel.setMode(NONE);	// Take drawPanel out of DRAW mode
					if (newShape != null)
					{
						newShape.setHighlight(false);  // IMPLEMENT: setHighlight()
						newShape = null;
					}
					drawPoly.setText("Draw");
					msg.setText("");
					drawPanel.repaint();
				}

			}
			else if (e.getSource() == modifyPoints)
			{
				if (drawPanel.currMode() != MODIFY)
				{
					drawPanel.setMode(MODIFY);	// Set the drawPanel so that it will
							// allow the user to edit the points within the selected
							// MyPoly object. 
					msg.setText("Click left to add point, right to remove");
					modifyPoints.setText("Quit Modify");
					drawPoly.setEnabled(false);
				}
				else
				{
					drawPanel.setMode(NONE);	// Set mode back to NONE
					msg.setText("");
					modifyPoints.setText("Modify");
					drawPoly.setEnabled(true);
				}
			}

		}
	}
	
	// Handler for the JMenuItems.  These options are all fairly straightforward.
	
	private class MenuHandler implements ActionListener
	{
		
		public void actionPerformed(ActionEvent e)
		{

			outer:
			if (e.getSource() == delItem)
			{
				flag = true;
				deleteSelected();
				msg.setText("Polygon has been deleted");
				drawPanel.setMode(NONE);
				modifyPoints.setEnabled(false);
				drawPoly.setEnabled(true);
				drawPanel.repaint();
			}
			else if (e.getSource() == setColor)
			{
                flag = true;
				Color newColor = JColorChooser.showDialog(theFrame,
                     "Choose Color for Polygon",
                     shapeList.get(selindex).getColor());  // IMPLEMENT: getColor()
                shapeList.get(selindex).setColor(newColor);  // IMPLEMENT: setColor()
                drawPanel.repaint();
            }
			else if(e.getSource() == p2Back)
			{
				//Move the selected polygon to the end of the list by removing it and re-adding it
				flag = true;
				MyPoly n;
				n = shapeList.get(selindex);
				shapeList.remove(selindex);
				shapeList.add(0,n);
				shapeList.get(selindex).setHighlight(false);
				msg.setText("");
				drawPanel.repaint();
				
			}
			else if (e.getSource() == saveScene)
			{
				//Save, prompt if the file isn't there
				if (currFile == null)
				{
					currFile = JOptionPane.showInputDialog(theFrame,"Enter file name");
				}

				flag = false;
				saveImages();
			}
			else if(e.getSource() == clear)
			{
				//Ask to save if the scene has changed and reset the flag after changing
				if(flag == true)
				{
					int reply = JOptionPane.showConfirmDialog(null, "Would you like to save?", "Save?", JOptionPane.YES_NO_OPTION);
					 //If they hit yes and both buttons are clicked, enter the statement
					 if (reply == JOptionPane.YES_OPTION)
						 {
								if (currFile == null)
								{
									currFile = JOptionPane.showInputDialog(theFrame,"Enter file name");
									if(currFile == null || (currFile != null && ("".equals(currFile))))   
									{
										break outer;
									}
								}
								flag = false;
								saveImages();
						 }
				}
				flag = false;
				//Remove every shape and set the current file to null
				shapeList.removeAll(shapeList);
				drawPanel.repaint();
				currFile = null;
			}
			
			else if(e.getSource() == open)
			{
				//If the scene has changed, ask them to save
				if(flag == true)
				{
					int reply = JOptionPane.showConfirmDialog(null, "Would you like to save?", "Save?", JOptionPane.YES_NO_OPTION);
					 //If they hit yes and both buttons are clicked, enter the statement
					 if (reply == JOptionPane.YES_OPTION)
						 {
								if (currFile == null)
								{
									currFile = JOptionPane.showInputDialog(theFrame,"Enter file name");
									if(currFile == null || (currFile != null && ("".equals(currFile))))   
									{
										break outer;
									}
								}

								saveImages();
								shapeList.removeAll(shapeList);
								drawPanel.repaint();
						 }
				}
				flag = false;
				//Ask for what file they want to open
				String fileN = JOptionPane.showInputDialog(theFrame,"What file would you like to open?");
				currFile = fileN;
				//If the file doesn't exist or they don't enter in a name, break
				if(fileN == null || (fileN != null && ("".equals(fileN))))   
				{
					break outer;	
				}
				File fileIn = null;
				try
				{
					fileIn = new File(fileN);
				}
				catch(NullPointerException e1)
				{
					
				}
				//Remove all current shapes
				shapeList.removeAll(shapeList);
				drawPanel.repaint();
				Scanner I = null;
				try 
				{
					I = new Scanner(fileIn);
				} catch (FileNotFoundException e1) 
				{
					JOptionPane.showMessageDialog(null, "Your file does not exist, please try again");
				}
				//If the file exists, continue
				if(fileIn.exists())
				{
					int j = 0;
					String l;
					int numPoly;
					l= I.nextLine();
					//Get the # of polygons
					numPoly = Integer.parseInt(l);
					while(j < numPoly)
					{
						if(numPoly > 0)
						{
							
								int [] x = null;
								int[] y = null;
								int r = 0;
								int g = 0;
								int b = 0;
								int npoints;
								Color c;
								//First split the RGB and the coords, then split the coords, then split the x's and y's
								String line = I.nextLine();
								String [] tokens = line.split("[|]");
								String[]tokens1 = tokens[0].split(":|\\,");
								String[] tokens2 = tokens[1].split(",");
								//assign 3 integers to their respective RGB values
								r = Integer.parseInt(tokens2[0]);
								g = Integer.parseInt(tokens2[1]);
								b = Integer.parseInt(tokens2[2]);
								//Create new x and y coords based on the size of the total coords
								x = new int[(tokens1.length/2)];
								y = new int[(tokens1.length/2)];
								npoints = (tokens1.length/2);
								for(int i = 0; i < tokens1.length; i++)
								{
									//If the index is even, it is an x value, if not it is a y
									if(i%2 == 0)
									{
										x[i/2] = Integer.parseInt(tokens1[i]);
									}
									if(i%2 == 1)
									{
										y[i/2] = Integer.parseInt(tokens1[i]);;
									}
								}
								//Set the color and create a new mypoly with the coords, color, and number of points  
								c = new Color(r, g, b);
								MyPoly p = new MyPoly(x,y,npoints,c);
								//Add the shapes to the shapelist
								 shapeList.add(p);
								 drawPanel.repaint();
								j++;
							
						}
						else
						{
							
						}
					}
				}
				else
				{
					
				}
			}
			else if(e.getSource() == saveAs)
			{
				//Simply ask for the filename and save, because saveAs saves to a new file no matter what
				currFile = JOptionPane.showInputDialog(theFrame,"Enter file name");
				saveImages();
				flag = false;
			}
			else if (e.getSource() == endProgram)
			{
				//Ask to save before exiting
				if(flag == true)
				{
					int reply = JOptionPane.showConfirmDialog(null, "Would you like to save?", "Save?", JOptionPane.YES_NO_OPTION);
					 //If they hit yes and both buttons are clicked, enter the statement
					 if (reply == JOptionPane.YES_OPTION)
						 {
								if (currFile == null)
								{
									currFile = JOptionPane.showInputDialog(theFrame,"Enter file name");
									if(currFile == null || (currFile != null && ("".equals(currFile))))   
									{
										break outer;
									}
								}
								saveImages();
						 }
				}

				flag = false;
				System.exit(0);
			}
		}
	}

	// Method to save the contents of the shapeList into a text file.  This method depends
	// upon the fileData() method in the MyPoly class.  See specifications of the fileData()
	// method in the Assignment 5 sheet and in A5snap.htm.
	public void saveImages()
	{
		try
		{
			PrintWriter P = new PrintWriter(new File(currFile));
			P.println(shapeList.size());
			for (int i = 0; i < shapeList.size(); i++)
			{
				P.println(shapeList.get(i).fileData());	// IMPLEMENT: fileData()
			}
			P.close();
		}
		catch (Exception e)
		{ 
			JOptionPane.showMessageDialog(theFrame, "I/O Problem - File not Saved");
		}
	}
	
	private void addshape(MyPoly newshape)
	{
		shapeList.add(newshape);
		drawPanel.repaint();
	}
	
	// Method to select the MyPoly object located in location (x, y).  If more than one
	// MyPoly encloses that point, this method will rotate through them in succession.
	private int getSelected(int x, int y)
	{
		unSelect();
		if (shapeList.size() == 0) 
			return -1;
		int currInd = startInd;
		do
		{
			try
			{
				if (shapeList.get(currInd).contains(x, y))  // OVERRIDE: contains().  The
					// contains() method in Polygon will work for any Polygon containing
					// 3 or more points.  However, it does not return true if the Polygon
					// contains only 1 or 2 points.  Your overridden version must handle
					// this issue.  This is non-trivial -- think about how you could do
					// this.  As a hint see the Point2D class.
				{
					startInd = (currInd+1) % shapeList.size();
					shapeList.get(currInd).setHighlight(true);  // highlight selected MyPoly.
					// When drawn, this will shown the individual points in the MyPoly and
					// its outline rather than the filled in shape.
					return currInd;
				}
				currInd = (currInd+1)%shapeList.size();
			}
			catch(IndexOutOfBoundsException e)
			{
				
			}
			
		} while (currInd != startInd);
		return -1;
	}

	public void deleteSelected()
	{

        flag = true;
		if (selindex >= 0)
		{
			shapeList.remove(selindex);
			selindex = -1;
			if (startInd >= shapeList.size())
				startInd = 0;
		}
	}

	public void unSelect()
	{
		if (selindex >= 0)
		{
			if((shapeList.size()== 0))
			{
				
			}
			else
			{
				shapeList.get(selindex).setHighlight(false);
				selindex = -1;
			}
			
		}
	}

	public static void main(String [] args)
	{
		new Assig5b();
	}

	// Class to do the "drawing" in this program.  See more comments below.
	private class ShapePanel extends JPanel
	{
		private int prefwid, prefht;
		private int x1, y1, x2, y2;    // used by mouse event handlers when drawing and
		                               // moving the shapes

		private int mode;	// Since reaction to mouse is different if we are creating
							// or moving or modifying a shape, we must keep track.
							
		public ShapePanel (int pwid, int pht)
		{		
			selindex = -1;
			startInd = 0;
			prefwid = pwid;   // values used by getPreferredSize method below (which
			prefht = pht;     // is called implicitly)
			setOpaque(true);

			MyMouser mListen = new MyMouser();  // Create listener for MouseEvents and
			addMouseListener(mListen);			// MouseMotionEvents
			addMouseMotionListener(mListen);      
		}  // end of constructor

		public void setMode(int newMode)	// Set mode
		{
			mode = newMode;
		}

		public int currMode()		// Return current mode
		{
			return mode;
		}

		public Dimension getPreferredSize()
		{
			return new Dimension(prefwid, prefht);
		}

		public void paintComponent (Graphics g)	// Method to paint contents of panel
		{
			super.paintComponent(g);  // super call needed here
			Graphics2D g2d = (Graphics2D) g;
			for (int i = 0; i < shapeList.size(); i++)
			{
				shapeList.get(i).draw(g2d);  // IMPLEMENT: draw().  This method will utilize
						// the predefined Graphics2D methods draw() (for the outline only,
						// when the object is first being drawn or it is selected by the user) 
						// and fill() (for the filled in shape) for the "basic" Polygon
						// but will require additional code to draw the enhancements added
						// in MyPoly (ex: the circles indicating the points in the polygon
						// and the color).  Also special cases for MyPoly objects with only
						// 1 or 2 points must be handled as well. For some help with this see
						// handout MyRectangle2D
			}
		}

		// This class will handle the MouseEvents (both click and motion) for the panel.
		// It extends MouseAdapter which trivially implements both MouseListener and
		// MouseMotionListener.
		private class MyMouser extends MouseAdapter
		{
 			public void mousePressed(MouseEvent e)
			{
				x1 = e.getX();  // store where mouse is when clicked
				y1 = e.getY();

				if (mode == NONE)
				{
					selindex = getSelected(x1, y1);  // find shape mouse is
					if (selindex >= 0)               // pointing to
					{
						modifyPoints.setEnabled(true);
						delItem.setEnabled(true);
						setColor.setEnabled(true);
						p2Back.setEnabled(true);
						msg.setText("Selected outline shown. Drag to move.");
					}
					else
					{
						modifyPoints.setEnabled(false);
						delItem.setEnabled(false);
						setColor.setEnabled(false);
						p2Back.setEnabled(false);
						msg.setText("");
					}
				}
				repaint();
			}
 			public void mouseMoved(MouseEvent e)
			{
 				if (mode == MODIFY)	// Allow user to add or remove points from
					// the current MyPoly
 				{
 					//If they move the mouse in modify mode, call the setFlag method for the point their mouse is at
 					shapeList.get(selindex).setFlag(e.getPoint());
 					repaint();
 					
 				}
 				else
 				{
 					repaint();
 				}
			}        
			public void mouseClicked(MouseEvent e)
			{
				if (mode == DRAW)	// Draw the points in the new MyPoly
				{
					if (newShape == null)	// For first point, new MyPoly must
					{		// be created.
						flag = true;
						newShape = new MyPoly();
						newShape.setHighlight(true);
						addshape(newShape);
                    }
					newShape.addPoint(x1, y1);	// OVERRIDE: addPoint()
				}
				else if (mode == MODIFY)	// Allow user to add or remove points from
											// the current MyPoly
				{
					MyPoly currPoly;
					if(selindex > shapeList.size())
					{
						currPoly = null;
					}
					else
					{
						currPoly = shapeList.get(selindex);
					}
					
					if (e.getButton() == 1)
					{

		                flag = true;
						currPoly = currPoly.insertPoint(x1, y1);
						currPoly.setHighlight(true);
							// IMPLEMENT: insertPoint()
							// Note that this method is not a mutator, but rather returns
							// a NEW MyPoly object.  The new MyPoly will contain all of the 
							// points in the selected MyPoly, but with (x1, y1) inserted 
							// between the points closest to point (x1, y1).  For help with
							// this see MyPoly.java and in particular the method
							// getClosest().
					}
					else if (e.getButton() == 3)
					{

		                flag = true;
						currPoly = currPoly.removePoint(x1, y1);
						if(currPoly == null)
						{
							
						}
						else
						{
							currPoly.setHighlight(true);
						}
							// IMPLEMENT: removePoint()
							// Note that this method is not a mutator, but rather returns
							// a NEW MyPoly object.  If point (x1, y1) falls within one of
							// the "point" circles of the MyPoly then the new MyPoly will not
							// contain that point.  If (x1, y1) does not fall within any point
							// circle then the original MyPoly will be returned. If, after the 
							// removal of the point, no points are remaining in the MyPoly
							// the removePoint() method will return null.  See more details in 
							// MyPoly.java
					}
					if (currPoly != null)
						shapeList.set(selindex, currPoly);
					else	// No MyPoly left after deletion so remove it from list
					{

						deleteSelected();
						mode = NONE;
						drawPoly.setEnabled(true);
						delItem.setEnabled(false);
						modifyPoints.setEnabled(false);
						setColor.setEnabled(false);
						modifyPoints.setText("Modify");
						msg.setText("");
					}
				}
				repaint();
			}
			
			public void mouseDragged(MouseEvent e)
			{
				x2 = e.getX();
				y2 = e.getY();
				if (mode == NONE && selindex >= 0)
				{
					MyPoly currPoly = shapeList.get(selindex);
					int deltaX = (x2 - x1);
					int deltaY = (y2 - y1);

	                flag = true;
					currPoly.translate(deltaX, deltaY);	// OVERRIDE: translate()
					// The predefined translate() method will move a certain amount rather
					// than moving to a specific location.  Thus we figure out how much to
					// move based on the difference between the spot where the mouse used
					// to be and where it is now.  However, since you are adding extra
					// instance variables to your MyPoly class, you must also handle
					// these in the translate() method, which is why you must override it.
					x1 = x2;
					y1 = y2;
                }
				repaint();
			}  
		} // end of MyMouser
	} // end of ShapePanel
}