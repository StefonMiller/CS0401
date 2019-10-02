/*
 * Stefon Miller
 * CS401 Lab Tuesdays 11AM
 * SMM248@pitt.edu
 * This program creates a JPanel used for voting
 */

//Import statements
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.*;
import java.util.*;
import javax.swing.*;

public class Assig4 
{
	
	JButton login, finish;//Buttons for logging in and casting ballots
	JFrame theWindow;//The window in which all of the ballot JPanels will be added
	boolean isDone;//If the user casted his/her ballot
	ArrayList<Ballot> ballotArr;//Array of Ballots used to create a new JPanel for each ballot
	int id;//User's ID
	File fileIn;//File passed in throught the command line
	int [] idArr1;//Array of ID's of the users
	String[] nameArr;//Array of the Voter Names
	boolean[] votedArr;//An array of boolean expressions regarding if the person has voted or not
	boolean isGood;//If the user's ID is valid
	public Assig4(String[] args)throws IOException
	{
		theWindow = new JFrame("Ballot Box");//Create the window
		FlowLayout layout = new FlowLayout();//Give it a flowlayout
		layout.setHgap(10);//Set the gap between elements to be 10
		theWindow.setLayout(layout);//Attach the layout to the window
		int numBallots;//Number of ballots, the first line in the file
		String question;//The "question" or category
		int ballotID;//ID of the ballot
		login = new JButton("Login to vote");//Creates button for logging in
		finish = new JButton("Click to cast votes");//Creates button for casting ballots
		ActionListener listen = new OtherListener();//Create an actionlistener for the buttons
		//Connnect both buttons to the listener, set the fonts, and disable them
		finish.addActionListener(listen);
		login.addActionListener(listen);
		login.setFont(new Font("Serif", Font.BOLD,18));
		finish.setFont(new Font("Serif", Font.BOLD,18));
		finish.setEnabled(false);
		//Create a new file object named after the command line argument
		if(args.length == 0)
		{
			JOptionPane.showMessageDialog(null, "You did not enter a file name, please run the program and try again");
			System.exit(0);
		}
		fileIn = new File(args[0].toString());
		//Make sure the file exists and if not quit the program
		if(!fileIn.exists())
		{
			JOptionPane.showMessageDialog(null, "The filename you entered is not valid, please run the program again");
			System.exit(0);
		}
		else
		{
		}
		//Create a scanner for this file to read in things
		Scanner S = new Scanner(fileIn);
		//Read in the first line(number of ballots) and pass it into the numBallots variable, and consume a new line
		numBallots = S.nextInt();
		S.nextLine();
		int i = 0;//Loop sentinel 
		//Create the ballotArray and set it's length equal to the number of ballots
		ballotArr = new ArrayList<Ballot>(numBallots);
		//While Loop for reading in info to the ballots
		while(i < numBallots)
		{
			//Read in the whole ballot line and split it into a string array
			String line = S.nextLine();
			String[] wholeLine = line.split("[:]+");
			
			//Parse the first index of the String array to an int and set it equal to the ID of the ballot
			ballotID = Integer.parseInt(wholeLine[0]);
			//Create a new file of the BallotID in case it wasn't already created
			File voteFile = new File(ballotID + ".txt");
			
			//Set the question variable to the second index in the String array
			question = wholeLine[1];

			//Set the candidates variable equal to the whole line's last elements, no matter how long it is
			String[] candidates = new String[wholeLine.length - 2];
		
			//Split these last elements and pass them into the candidates variable
			candidates = wholeLine[2].split("[,]+");
			
			//If the file exits, fine, if not, create a new file with all votes equal to 0
			if(voteFile.exists())												
			{				
			}
			else
			{
				//Creates the file and writes in the info needed(Name:votes)
				voteFile.createNewFile();
				PrintWriter p = new PrintWriter(voteFile);
				for(int k = 0; k < candidates.length; k++)
				{
					p.println(candidates[k] + ":" + 0);
				}
				p.close();
			}
			
			//Create a new ballot object with the parameters ID, category, and candidates
			ballotArr.add(new Ballot(ballotID, question,candidates));
			//Iterate sentinel
			i++;
		
		}
		
		//Close scanner to avoid unclosed streams
		S.close();
		
		//After the ballot panels are created, add them 1 by 1 to the window
		for(int j = 0; j < ballotArr.size(); j++)
		{
			theWindow.add(ballotArr.get(j));
		}
		
		//Make the window do nothing when you hit the 'x', add the login and finish buttons, pack it, and set it to be visible.
		theWindow.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		theWindow.add(login);
		theWindow.add(finish);
		theWindow.pack();
		theWindow.setVisible(true);
	}
	
	class OtherListener implements ActionListener
	{
	 	
		public void actionPerformed(ActionEvent e)
		{
			//Check what button the user pressed
			if(e.getSource().equals(finish))
			{
				//ask for confirmation 
				 String message = "Are you sure you want to vote for these candidates?";
				 String title = "Submit Votes?";
				 int reply = JOptionPane.showConfirmDialog(null, message, title, JOptionPane.YES_NO_CANCEL_OPTION);
				 //If they hit yes and both buttons are clicked, enter the statement
				 if (reply == JOptionPane.YES_OPTION)
				 {
					 
					//For loop to read in the Ballot's ID text file and store it into arrays
					 for(int i = 0; i < ballotArr.size(); i ++)
					 {
						 	//An arraylist that will be used to hold the lines
						 	ArrayList<String> idArr = new ArrayList<String>();
						 	//Create a file object of the ballot's ID file
						 	File votesFile = new File(ballotArr.get(i).returnId() + ".txt");
						 	//Create scanner
						 	Scanner S = null;
						 	/*
						 	 * Try / catch because the actionpreformed method is from an interface and 
						 	 * adding a ThrowsIOException to the method would cause it to not match
						 	 * the method in the interface
						 	 */
						 	
							try 
							{
								//Create new scanner related to the file
								S = new Scanner(votesFile);
								
							} 
							catch (FileNotFoundException e2) 
							{
							}
							
							//While the file has lines, read them into the string arraylist
							while(S.hasNext())
							{
								String line = S.nextLine();
								//Split method to split the lines based on colons and add them to the list
								idArr.addAll(Arrays.asList(line.split("\\s*:\\s*")));
								
							}
							S.close();
							
							//Create an int array for the number of votes and a string array for the candidates
							int[] votesArr = new int[(idArr.size() / 2)];
							String[] nameArr1 = new String[(idArr.size() / 2)];
							//Set the sizes equal to the size of the arraylist /2 because the array list has elements for 2 things
							for(int j = 0; j < idArr.size(); j++)
							{
								//Check which index the arraylist is at in the for loop and put it in the respective array
								if(j % 2 == 0)
								{
									//If the index is even, it is an int b/c the file is formatted such that it goes name:votes
									nameArr1[j/2] = (idArr.get(j));
								}
								if(j % 2 == 1)
								{
									//If the index is odd, it is a string b/c the file is formatted such that it goes name:votes
									votesArr[j/2] = Integer.parseInt(idArr.get(j));
								}
							}
							
							//Call the savedata method for the respective ballot once the user has voted
							try 
							{
								ballotArr.get(i).saveData(votesArr,nameArr1);
								
							}
							catch (IOException e1) 
							{
								
							}
							
							//Re-enable the login button and disable all other buttons
							login.setEnabled(true);
							finish.setEnabled(false);
							for(int k = 0; k < ballotArr.size(); k++)
							{
								ballotArr.get(k).disable();
							}
							//Reset the color of the buttons
							ballotArr.get(i).resetButtons();
					 }
					 	
					 //Once the user has voted, change the last line of their text file to 'true'
					try 
					{
						saveVoterData(nameArr,votedArr,idArr1);
					} catch (IOException e1) 
					{
					}

				 }
				 
			}
			//Label for the break command
			outer:
			//If the user wants ot log in
			if(e.getSource().equals(login))
			{
				//Reset the value of valid, a sentinal to make sure the user enters a good id value
				boolean valid = false;
				//Prompt for voting id and if they hit cancel or ok w/ no value, return to the panel
				String d = JOptionPane.showInputDialog("What is your voting ID?");
				if(d == null || (d != null && ("".equals(d))))   
				{
						break outer;
				}
				//If the id is valid, keep going
				else
				{
					id = Integer.parseInt(d);
					valid = true;
				}
				//Open the voters.txt file and read the lines into another string arraylist
				File voterFile = new File("Voters.txt");
				//Create scanner and arraylist
				Scanner V = null;
				ArrayList<String> voterArr = new ArrayList<String>();
				//Create the scanner and link it to the voters.txt file
				try 
				{
					V = new Scanner(voterFile);
				} catch (FileNotFoundException e1){} 
				
				//Keep reading in lines until there are no more
				while(V.hasNext())
				{
					//Split each line based on a colon and passt he values into the arraylist
					String line = V.nextLine();
					voterArr.addAll(Arrays.asList(line.split("\\s*:\\s*")));
					
					
				}
				V.close();
				
				//Divide the size of the arraylist by 3(b/c there are 3 items per line) and initialize the id's, names, and boolean expressions to this length
				int voterArrSize = (voterArr.size() / 3);
				idArr1 = new int[voterArrSize];
				nameArr = new String[voterArrSize];
				votedArr = new boolean[voterArrSize];

				//Fill each array with the values from the arraylist
				for(int j = 0; j < voterArr.size(); j++)
				{
					//Because the file goes ID:NAME:VOTED, you can use modulus to determine which element the arraylist is on
					if(j % 3 == 0)
					{
						//if the arraylist is in index(0,3,6,9,etc.) then it is an id and therefore an int
						idArr1[j/3] = Integer.parseInt(voterArr.get(j));
					}
					if(j % 3 == 1)
					{
						//if the arraylist is in index(1,4,7,10,etc.) then it is a name and therefore a String
						nameArr[j/3] = (voterArr.get(j));
					}
					if(j % 3 == 2)
					{
						//if the arraylist is in index(2,5,8,11,etc.) then it is a boolean expression and therefore a boolean
						votedArr[j/3] = Boolean.parseBoolean(voterArr.get(j));
					}
				}
				//set isgood equal to false to make sure a user has voted
				isGood = false;
				for(int j = 0; j < idArr1.length; j++)
				{
					//If the id of the user is equal to one of the id's in the ID list, enter the if statement
					if(idArr1[j] == id)
					{
						//If they have not voted, enter this
						if(votedArr[j] == false)
						{
							//Welcome, enable all buttons except login, and set isGood equal to true
							JOptionPane.showMessageDialog(null, "Welcome " + nameArr[j] + ".");
							for(int k = 0; k < ballotArr.size(); k++)
							{
								ballotArr.get(k).enable();
							}
							votedArr[j] = true;
							login.setEnabled(false);
							finish.setEnabled(true);
							isGood = true;
							break;
						}
						//If they have already voted, go back to the panel
						if(votedArr[j] == true)
						{
							isGood = true;
							JOptionPane.showMessageDialog(null, "You have already voted, " + nameArr[j]);
							break;
						}
					}
				
				}
				//If they have not voted and their id doesn't match any of the id's in the array, tell them 
				if(isGood == false)
				{
					JOptionPane.showMessageDialog(null, "Your ID is not valid, sorry");
				}		
				
			}
				
		}
	}
	public static void main(String[] args) throws IOException
	{
		//Create new Voting window
		new Assig4(args);
	
	}

	
	 /**
	  * Save the user's data to a temp. file, delete the old file, and rename the temp file to the old file
	  * @param na an array of names
	  * @param ba an array of boolean expressions as to whether or not the user has voted
	  * @param ia an array of id's for the users
	  * @throws IOException if the file is not found, break
	  */
	public void saveVoterData(String [] na, boolean[] ba, int[] ia) throws IOException
	{
		//Create a file object referencing the voters.txt file and one referencing a temporary file
		File oldFile = new File("Voters.txt");
		File tempFile = new File("temptemp.txt");
		//Create a printwriter object referencing the temporary file
		PrintWriter pii = new PrintWriter(tempFile);
		//Print the arrays to match the old file(ID:NAME:TRUE/FALSE)
		for(int i = 0; i < na.length; i++)
		{
			pii.println(ia[i] + ":" + na[i] + ":" + ba[i]);
		}
		//Close the printwriter
		pii.close();
		//If the file exists, then delete it and rename the temp. file to it
		if(oldFile.exists())
		{
			oldFile.delete();
			tempFile.renameTo(oldFile);
		}
		//if not, tell the user the file doesn't exist
		else
		{
			JOptionPane.showMessageDialog(null, "The file was not found");
		}
		
	}
	 
	 

}
