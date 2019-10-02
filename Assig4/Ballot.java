import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.*;

public class Ballot extends JPanel 
{
	private int id;//id of the file
	private String[] candidates;//array of candidate names
	private JButton[] buttons;//array of buttons dependent on the number of candidates
	private boolean[] clicked;//An array of boolean expression for each button saying whether it is clicked or not
	String[] choices;//The user's choices
	private int candIndex;//Index of the button in the buttonarray that was clicked
	private boolean hasVoted;//if the user has voted or not
	
	/**
	 * Default constructor
	 */
	public Ballot()
	{
		
	}
	/**
	 * Constructor for an actual ballot
	 * @param i ID of the question
	 * @param q category or question
	 * @param c candidates
	 */
	public Ballot(int i, String q, String[] c)
	{
		//Set the id of the question to the one passed in
		id = i;
		//set the question to the one passed in
		String question = q;
		//Create a new gridlayout and make it so the buttons are 10 pixels apart vertically, then set the layout
		GridLayout layout = new GridLayout(6,1);
		layout.setVgap(10);
		setLayout(layout);
		choices = new String[c.length];
		//Create semi-deep copy so user can't change these variables through the main program
		candidates = new String[c.length];
		for(int j = 0; j < c.length; j++)
		{
			candidates[j] = c[j];
		}
		//Set the number of questions to the length of the number of candidates
		int numChoices = c.length;
		//Set the clicked array equal to the number of candidates
		clicked = new boolean[numChoices];
		//intitalize the button array's size to the number of choices
		buttons = new JButton[numChoices];
		//Create a new label with the category
		JLabel label = new JLabel(question);
		//Add the label, set its font, and put it in the center
		add(label);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("Serif", Font.ITALIC, 24));
		//Create a new listener
		ActionListener listen = new ButtonListener();
		/*
		 * Create an arbitrary # of buttons and set the text on them to be the an index 
		 * of the candidate array, then disable them, add a listener, set the font, and add them
		 */
		for(int j = 0; j < buttons.length; j++)
		{
			buttons[j] = new JButton(candidates[j]);
			add(buttons[j]);
			buttons[j].addActionListener(listen);
			buttons[j].setEnabled(false);
			buttons[j].setFont(new Font("Serif", Font.BOLD,18));
		}
		
		
	}
	/**
	 * Enable the buttons
	 */
	public void enable()
	{
		for(int j = 0; j < buttons.length; j++)
		{
			buttons[j].setEnabled(true);
		}
	}
	/**
	 * Disable the buttons
	 */
	public void disable()
	{
		for(int j = 0; j < buttons.length; j++)
		{
			buttons[j].setEnabled(false);
		}
	}
	/**
	 * Return the id of the ballot
	 * @return the id of the ballot
	 */
	public int returnId()
	{
		return id;
	}
	/**
	 * check to see if the user has checked a button for this ballot
	 * @return a boolean expression to check if the user has checked a button for this ballot
	 */
	public boolean checkAnswered()
	{
		//Cycle through the clicked array and if any of the elements are true, return true
		for(int i = 0; i < clicked.length; i++)
		{
			if(clicked[i] == true)
			{
				return true;
			}
		}
		//If 0 elements are true, then this ballot was not answered
		return false;
	}
	/**
	 * Reset the color of the buttons and make them all "not clicked"
	 */
	public void resetButtons()
	{
		for(int i = 0; i < buttons.length; i ++)
		{
			buttons[i].setForeground(null);
			clicked[i] = false;
		}
	}
	/**
	 * Write the ballot's results back to a the ballot's respective file(It's ID.txt)
	 * @param i an array of the number of votes a candidate has
	 * @param s the name of the candidate
	 * @throws IOException
	 */
	public void saveData(int[] i, String[] s) throws IOException
	{
		//Create a file object referencing the id.txt file and one referencing a temporary file
		File oldFile = new File(id + ".txt");
		File tempFile = new File("temp.txt");
		//Create a printwriter object referencing the temporary file
		PrintWriter p = new PrintWriter(tempFile);
		int[] j= new int[i.length];
		//Initialize new arrays and pass the values of the old ones into these ones
		for(int k = 0; k < i.length; k++)
		{
			j[k] = i[k];
		}

		String[] z= new String[s.length];
		
		for(int r = 0; r < s.length; r++)
		{
			z[r] = s[r];
		}	
		//Iterate through the ID array
		for(int h = 0; h < j.length; h++)
		{
			//Add 1 to the index of the number of votes array in which a button was pressed
			if(candIndex == h && clicked[h] == true)
			{
				j[h] = (j[h] + 1);
			}
		}
		//Write the String "NAME:ID" to a file for every index of these two arrays
		for(int l = 0; l < i.length; l++)
		{
			p.println(z[l] + ":" + j[l]);
		}
		//Close the printwriter, delete the old file, rename the temp file to the old one, and remove the temp file
		p.close();
		oldFile.delete();
		tempFile.renameTo(oldFile);
		File remove = new File("temp.txt");
		remove.delete();
		
	
		 
	}
	private class ButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			//Iterate throught the button array
			for(int i = 0; i < buttons.length; i ++)
			{
				//If the user click a button that was not clicked, go here
				if(e.getSource().equals(buttons[i]) && clicked[i] == false)
				{
					//Color the button red, set it to "clicked" and make the choice the text on the button
					buttons[i].setForeground(Color.RED);
					clicked[i] = true;	
					choices[i] = buttons[i].getText();
					//Save the index of the user's choice
					candIndex = i;
				}
				//If the button was already clicked, chang the color back and make it "not clicked"
				else
				{
					buttons[i].setForeground(null);
					clicked[i] = false;
				}
				
				
			}
		}
	}
	
}
