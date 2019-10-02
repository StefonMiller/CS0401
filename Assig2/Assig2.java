/*
 * Stefon Miller
 * Professor Ramirez CS401 9:30AM 
 * SMM248@pitt.edu
 * This program is a gambling website that keeps track of its users balance, 
 * games won, games played, names, and win rates.  It stores these in a file 
 * at the end of the game and reads it in if the user returns.
 */
//Import statments
import java.util.*;
import java.text.*;
import java.io.*;
public class Assig2 
{
	
	public static void main(String[] args) throws IOException
	{
		double balance;				//value that will be passsed into the Player class
		String firstName;			//first name of the user that will be passed into the Player class
		String lastName;			//last name of the user that will be passed into the Player class
		int gamesWon;				//Number of games a previous user won
		int gamesPlayed;			//Number of games a previous user played
		Random rand = new Random();	//Creating a random object of the Random class to be passed into the Die Class
		String playAgain;			//Sentinal variable determining if the game will go another round
		double amountB = 0;			//Amount of money the user bets
		Player p1;					//Object of the player class that will be used
		PrintWriter fileOut;		//Creating a printwriter object for future use
		Scanner fileIn;				//Creating a Scanner object that reads in text from a file if there is an existing user for later use
		
		NumberFormat f = NumberFormat.getCurrencyInstance(Locale.US);	//Creates a formatter for dollars
		Scanner keyboard = new Scanner(System.in);						//Creates a scanner object used for keyboard input
		
		System.out.println("What is your name?");						//Ask the user for their name and create a file object with that name and the '.txt' extention
		firstName = keyboard.nextLine();
		File inFile = new File(firstName + ".txt");
		
		if(inFile.exists())												//Determine if the file exists
		{
			/*
			 * If the file exists, welcome the user back and pass in data from their respective file into 
			 * the 4 variables used to construct an object of the player class for them
			 */
			fileIn = new Scanner(inFile);								
			System.out.println("Welcome back " + firstName + "!");
			lastName = fileIn.next();									
			fileIn.nextLine();
			firstName = fileIn.next();
			fileIn.nextLine();
			balance = fileIn.nextDouble();
			fileIn.nextLine();
			gamesPlayed = fileIn.nextInt();
			fileIn.nextLine();
			gamesWon = fileIn.nextInt();
			fileIn.nextLine();
			
			p1 = new Player(balance, firstName, lastName, gamesWon, gamesPlayed);
			System.out.println("Here are your stats:\n\n" + p1);					//Print out the stats of the player in case they forget
			
		}
		else
		{
			/*
			 * If the file doesn't exist, create a file with their first name and the '.txt' extention,
			 * and ask them for the values you will be passing into their player object
			 */
			fileOut = new PrintWriter(inFile);
			fileIn = new Scanner(inFile);
			System.out.println("Welcome to my casino, new user!");
			System.out.println("How much money do you have?");
			balance = keyboard.nextDouble();
			while(balance < 0)
			{
				System.out.println("Please enter a valid value above or equal to $0.00");
				balance = keyboard.nextDouble();
			}
			keyboard.nextLine();
			System.out.println("What is your last name?");
			lastName = keyboard.nextLine();
			p1 = new Player(balance, firstName, lastName, 0, 0);
			p1.saveData(fileOut);
			fileOut.close();
		}
		
		//Ask the user to play
		System.out.println("Would you like to play?(anything other than 'yes' will exit)");
		playAgain = keyboard.next();
		
		
		while(playAgain.toLowerCase().equals("yes") && p1.getBalance() != 0)		//If the user says yes and their balance isn't 0, allow them to play
		{
			keyboard.nextLine();
			System.out.println("How much do you want to bet?");						//Make sure the user enters a valid amount to bet
			amountB = keyboard.nextDouble();
			while(amountB <= 0 || amountB > p1.getBalance())
			{
				keyboard.nextLine();
				System.out.println("Enter a valid value less than " + f.format(p1.getBalance()) + " but greater than $0.00.");
				amountB = keyboard.nextDouble();
			}	
				p1.subMoney(amountB);												//Subtrace the money the user bets before the game starts
				
				System.out.println("Do you think the roll of 2 dice will be over(>7), under(<7), or seven(=7)?");
				String choice = keyboard.next();
				while(!choice.toLowerCase().equals("over") && !choice.toLowerCase().equals("seven") && !choice.toLowerCase().equals("under"))
				{
					System.out.println("Enter something valid");					//Make sure they enter a valid choice
					choice = keyboard.next();
					
				}
				keyboard.nextLine();
				/*
				 * Create 2 dice objects, 'roll' them, assign these values to 
				 * 2 ints and add them together, then compare this total value
				 * to 'over', 'under', or 'seven'.
				 * 
				 */
				Die die1 = new Die(rand);
				Die die2 = new Die(rand);
				
				int total; 
				int roll1 = die1.rollDie();
				int roll2 = die2.rollDie();
				
				total = roll1 + roll2;
				
				String outcome;
				
				if(total == 7)
				{
					outcome = "seven";
				}
				else if(total < 7)
				{
					outcome = "under";
				}
				else if(total > 7)
				{
					outcome = "over";
				}
				else
				{
					outcome = "oops";
				}
				/*
				 * Compare the user's choice to the outcome, and report the results accordingly
				 * by adding or subtracting the amount they won or lost, telling them they lost or won,
				 * printing their balance, incrementing the total and round games played, and tallying the
				 * game as a win or a loss.
				 */
				if(choice.toLowerCase().equals(outcome.toLowerCase()) && outcome.equals("seven"))
				{
					System.out.println("Congrats!  It was " + roll1 + " and " + roll2 + " which equals " + total + "(" + outcome + ") and you guessed " + choice);
					System.out.println("You win your original bet back + 4x that amount(" + f.format(5*amountB) + ")!");
					p1.addMoney(amountB *5);
					p1.won();
					p1.printBalance();
				}
				else if(choice.toLowerCase().equals(outcome.toLowerCase()))
				{
					System.out.println("Congrats!  It was " + roll1 + " and " + roll2 + " which equals " + total + "(" + outcome + ") and you guessed " + choice);
					System.out.println("You win your original bet back + that amount(" + f.format(2*amountB) + ")!");
					p1.addMoney(2*amountB);
					p1.won();
					p1.printBalance();
				}
				else
				{
					System.out.println("Oh no!  It was " + roll1 + " and " + roll2 + " which equals " + total + "(" + outcome + ")" + "and you guessed " + choice);
					System.out.println("You lost " + f.format(amountB) + "!");
					System.out.println("Better luck next time!");
					p1.lost();
					p1.printBalance();
				}
				//Promp the user for another game
				System.out.println("Would you like to play again?(Anything other than 'yes' will exit)");
				playAgain = keyboard.next();															
							
			}
		/*
		 * Splits exiting the game into 2 reasons, a balance of 0, oropting to quit,
		 * after these cases are known to the user, their stats are displayed and the 
		 * printwriters are closed
		 * 
		 */
		if(!playAgain.toLowerCase().equals("yes"))
		{
			System.out.println("You elected not to play again");
			System.out.println("Thank you for trying out my program!\n");
		}
		else if(p1.getBalance() == 0)
		{
			System.out.println("You have no money!");
			System.out.println("Thank you for trying out my program!\n");
		}
		System.out.println("Here are your stats:\n\n" + p1.endOfRoundToString());
		p1.endOfGame();																//Resets values of the 'round' variables
		PrintWriter replace = new PrintWriter(inFile);								//Creates a new PrintWriter object to overwrite the text file, not add onto it
		p1.saveData(replace);														//Write the data
		fileIn.close();
		replace.close();
	}
}
