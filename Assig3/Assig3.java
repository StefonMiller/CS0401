/*
 * Stefon Miller CS401 Tuesday lab 11AM, class 9:30AM tuesdays / thursdays
 * SMM248@pitt.edu
 * This program reads a quiz in from a file, quizzes the user, and rewrites the
 * results back to the same file
 */
import java.util.*;
import java.text.*;
import java.io.*;

public class Assig3 
{
	public static void main(String[] args) throws IOException
	{
		int numChoices;		//Number of choices a question will have, varying
		String question;	//The question string
		String []choices;	//An array of choices varying in size depending on the numChoices variable
		int right;			//The right answer
		int played;			//Number of people who have attempted a question
		int correct;		//Number of people who got the question right
		ArrayList<Question> qArray = new ArrayList<Question>();		//ArrayList of questions 
		int answer;			//The index in choices that is the answer
		int numRightPlayer = 0;//The number of questions a player got right
		int minIndex = 0;	//Minimum index, used for sorting later
		int maxIndex = 0;	//Maximum index, used for sorting later
		double min = 1.5;	//arbitrary number that the percent will never go over
		double max = 0;		//arbitrary number the percent will never go under
		List<Double> percent = new ArrayList<Double>();	//Arraylist of percentages, used for sorting them later
		
		//Create a formatter for percentages to 1 decimal place
		NumberFormat f = NumberFormat.getPercentInstance();
		f.setMinimumFractionDigits(1);
		
		//Intro
		System.out.println("Welcome to the hardest quiz you will ever take. Good luck.\n");	
		
		//Create the keyboard object, the fileIn object, and the S object
		Scanner keyboard = new Scanner(System.in);
		File fileIn = new File("MyQuiz.txt");
		Scanner S = new Scanner(fileIn);
		
		//Keep creating questions while the file has data to read in
		while(S.hasNext())
		{
			//First line is the question, second is the number of choices
			question = S.nextLine();
			numChoices = S.nextInt();
			S.nextLine();
			//Create an array of size equal to the number of choices given by the text document
			choices = new String[numChoices];
			//Print out the question
			System.out.println(question);
			//Fill the choices array with choices
			for(int j = 0; j < choices.length; j++)
			{
				choices [j]= S.nextLine();
				System.out.println(""  + (j + 1) +") " + choices[j]);
			}
			//Read in the right answer
			right = S.nextInt();
			S.nextLine();
			//Read in the amount of people who have tried the question
			played = S.nextInt();
			//Read in how many people got the quesiton right
			S.nextLine();
			correct = S.nextInt();
			//Consume a new line only if the text file has more to read in(Don't consume a new line if the file is empty)
			if(S.hasNext())
			{
				S.nextLine();
			}
			else
			{
			}
			//Ask the user for their answer and if it is invalid ask again
			do
			{
				System.out.println("What is your answer?(Enter a number 1 - " + choices.length + ")");
				answer = keyboard.nextInt();
				keyboard.nextLine();
			}while(answer > choices.length || answer <= 0);
			
			//Add a new question object to the array after every question is answered
			qArray.add(new Question(question, numChoices, choices, right, played, correct, answer));
		}
		//close the file
		S.close();
		//If the array is empty, exit the program
		if(qArray.isEmpty())
		{
			System.out.println("The file is empty");
			System.exit(0);
		}
		//Check the user's answers
		for(int k = 0; k < qArray.size(); k++)
		{
			qArray.get(k).checkAnswer();
		
			
		}
		
		//Start a new line for formatting purposes
		System.out.println("\n");
		
		//Check if the player was right for one question index, if they are, increment numRightPlayer
		for(int l = 0; l < qArray.size(); l++)
		{
			
			if(qArray.get(l).getStats())
			{
				numRightPlayer++;
			}
			else
			{
				
			}
		}
		
		/*
		 * Display player stats.  I decided to do this outside of the class because
		 * the Question class represents one question and these statistics represent
		 * the whole quiz
		 */
		System.out.println("Player stats:\n" + "\tNumber of questions correct: " + numRightPlayer + "\n"
				+ "\tNumber of questions incorrect: " + (qArray.size() - numRightPlayer) + "\n"
				+ "\tPercentage correct: " + f.format(((double)numRightPlayer / qArray.size())));

		//Go to a new line for formatting
		System.out.println("\n");
		
		//Display global statistics by getting the global statistics for every question
		System.out.println("Global Statistics:\n");
		for(int l = 0; l < qArray.size(); l++)
		{
			//pass in the percent formatter and an int
			qArray.get(l).displayGlobalStats(f);
		}
		
		//Get the hardest question
		for(int l = 0; l < qArray.size(); l++)
		{
				//If 1.5 > the index, set the min to the index and save that index.  1.5 will always be greater than the percentage
				if(min >= qArray.get(l).getPercent())
				{
					min = qArray.get(l).getPercent();
					minIndex = l;
				}
			
		}
		
		/*
		 * Display the hardest question, not done in the class as the hardest question
		 * is related to the quiz, not the question
		 */
		System.out.println("\nThe hardest question is question " 
		+ minIndex + ":\n" + qArray.get(minIndex).printQuestion(f) + "\n");
		
		//Get the easiest question
		for(int l = 0; l < qArray.size(); l++)
		{
			//Fill the percent array
			percent.add(qArray.get(l).getPercent());
			//If 0 < the index, set the max to that index and save the index.  0 will always be <= the percent
			if(max <= qArray.get(l).getPercent())
			{
				max = qArray.get(l).getPercent();
				maxIndex = l;
			}
			
		}
		
		/*
		 * Display the easiest question, not done in the class as the easiest question
		 * is related to the quiz, not the question
		 */
		System.out.println("The easiest question is question " 
		+ maxIndex + ":\n" + qArray.get(maxIndex).printQuestion(f));
		
		//Sort the percent array in non-ascending order
		Collections.sort(percent);
		Collections.reverse(percent);
		
		//Print out the question array
		System.out.println("Sorting in non-ascending order:");
		for(int j = 0; j < percent.size(); j++)
		{
			System.out.println("\t" + f.format(percent.get(j)));
			
		}
		
		//Save the data to the file and close it after
		PrintWriter p = new PrintWriter(fileIn);
		for(int l = 0; l < qArray.size(); l++)
		{
			qArray.get(l).saveData(p);
		}
		p.close();
	
	
		
	}
	
	
	
}
