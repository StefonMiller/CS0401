/*
 * Stefon Miller CS401 Tuesday lab 11AM, class 9:30AM tuesdays / thursdays
 * SMM248@pitt.edu
 * This class deals with the data and operations regarding single questions
 */
import java.io.PrintWriter;
import java.text.*;

public class Question 
{
	private int numChoices;				//Number of choices in a question
	private String question;			//The actual question
	private String [] choices;			//An array of strings of the choices
	private int correctAns;				//The correct answer
	private int numTried;				//Number of total tries
	private int numCorrect;				//Number of correct guesses
	private boolean correctPlayer;		//Determine if the player is correct
	private int answer;					//The users answer
	
	/**
	 * Constructor used to create a question
	 * @param q question string
	 * @param n number of choices
	 * @param ch choice array
	 * @param c the actual answer
	 * @param t number of tries globally
	 * @param r number of correct answers globally
	 * @param a user's answer
	 */
	public Question(String q, int n, String[] ch, int c, int t, int r, int a)
	{
		question = q;
		numChoices = n;
		choices = ch;
		correctAns = c;
		numTried = t;
		numCorrect = r;
		answer = a;
	}
	/**
	 * Determine whether the user was correct and tell them if they were wrong 
	 * or not while setting correctPlayer to either true or false to return to
	 * the method caller.  It also increments numCorrect if the user was correct
	 */
	public void checkAnswer()
	{
		//Add 1 try to the global number of tries
		numTried++;
		if(answer == correctAns)
		{
			System.out.println("Question: \t" + question + " \nAnswer: \t" + choices[answer-1] + " \nCorrect: \t" + choices[correctAns-1] + "  \nResult: \tCorrect!\n");
			numCorrect++;
			correctPlayer = true;
		}
		else
		{
			System.out.println("Question: \t" + question + " \nAnswer: \t" + choices[answer-1] + " \nCorrect: \t" + choices[correctAns-1] + "  \nResult: \tIncorrect!\n");
			correctPlayer = false;
		}
		
	}
	/**
	 * Calculate the global percentage of right answers for this question
	 * @param nC number correct
	 * @param nQ number of questions
	 * @return
	 */
	/**
	 * returns whether or not the player was right
	 * @return whether or not the player was right
	 */
	public boolean getStats()
	{
		return correctPlayer;
	}
	/**
	 * Write the data back to the file
	 * @param p printwriter associated with the file
	 */
	public void saveData(PrintWriter p)
	{
		p.println(question);
		p.println(numChoices);
		for(int i = 0; i < choices.length; i++)
		{
			p.println(choices[i]);
		}
		p.println(correctAns);
		p.println(numTried);
		p.println(numCorrect);
		
	}
	/**
	 * Print out the global stats for a question
	 * @param f number formatter for percents
	 */
	public void displayGlobalStats( NumberFormat f)
	{

		System.out.println("Question: \t\t\t" + question + " \nNumber of tries: \t\t" + numTried + " \nNumber of correct answers: \t" + numCorrect + "  \nPercentage Right:\t\t" + f.format(((double)numCorrect / numTried)) + "\n");
		 
	}
	/**
	 * return the global percent for a question
	 * @return the percent
	 */
	public double getPercent()
	{
		return ((double)numCorrect / numTried);
	}
	/**
	 * Print out a question and it's respective stats
	 * @param f formatter for percentage
	 * @return string of the question and its stats
	 */
	public String printQuestion(NumberFormat f)
	{
		String t;
		t = "Question: \t\t\t" + question + " \nNumber of tries: \t\t" + numTried + " \nNumber of correct answers: \t" + numCorrect + "  \nPercentage Right:\t\t" + f.format(((double)numCorrect / numTried)) + "\n";
		return t;
	}

}
