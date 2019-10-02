/*
 * Stefon Miller
 * Professor Ramirez CS401 9:30AM 
 * SMM248@pitt.edu
 * This program is a simple shopping simulator for a place called
 * "Pies, Pies, and Pis.  It detects whether the user is there, if they have
 * a pie card, how many items they want, and outputs the corresponding recepit
 */

import java.util.*;
import java.text.*;
import javax.swing.JOptionPane;


public class Project1 
{
	
	public static void main(String[] args)
	{
		double cheesePrice = 10.00;
		double pepPrice = 12.00;
		double slicePrice = 2.00;
		double piePrice = 10.00;
		double charmPrice = 50.00;
		double cardPepPrice = cheesePrice;
		double cardSlicePrice = slicePrice - .25;
		double cardPiePrice = piePrice - 2.00;
		double cardCharmPrice = charmPrice - (charmPrice * .1);
		double subtotal = 0.00;
		double cardSubtotal = subtotal - (subtotal * .1);
		String customer;
		String hasCard;
		int valid = 2;
		int menuChoice;
		int numPep = 0;
		int numCheese = 0;
		int slices = 0;
		int wholePies = 0;
		int charms = 0;
		double tax = .07;
		int stay = 2;
		String order = "";
		double cheeseTotal = 0;
		double pepTotal = 0;
		double pieTotal = 0;
		double sliceTotal = 0;
		double charmTotal = 0;
		double discountPercent = .1;
		double total;
		double taxTotal;
		double paymentAmount;
		double change;
		
		
		Scanner keyboard = new Scanner(System.in);
		NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.US);
		
		/*
		 * Infinite while loop to keep repeat if the user enters an invalid
		 * term.  If he/she enters 'no', it will exit via the break command
		 */
		while(true)
		{
			//Resetting the values so the program can run again
			subtotal = 0.00;
			numPep = 0;
			numCheese = 0;
			slices = 0;
			wholePies = 0;
			charms = 0;
			order = "";
			cheeseTotal = 0;
			pepTotal = 0;
			pieTotal = 0;
			sliceTotal = 0;
			charmTotal = 0;
			total = 0;
			taxTotal = 0;
			paymentAmount = 0;
			change = 0;
			stay = 2;
			valid = 2;
			
			System.out.println("Is there another customer?(Yes or no)");
			customer = keyboard.next();
			//Enter if the customer types any case combination of 'yes'
			if(customer.toLowerCase().equals("yes"))
			{
				keyboard.nextLine();
				System.out.println("Welcom to pies, pies, "
						+ "and Πs!\nDo you have a pie card?(Yes or no)");
				hasCard = keyboard.next();
				
				while(valid > 1)
				{
					//Separates the program into 2 cases: has / doesn't have a card
					if(hasCard.toLowerCase().equals("yes"))
					{
						keyboard.nextLine();
						System.out.println("Welcome valued Pie Card holder!  Here are your Pie Card advantages:\n"
								+ "\tPizza) Pepperoni for the price of a plain(" + formatter.format(cheesePrice) + ")\n"
								+ "\tPies) $0.25 off a slice(" + formatter.format(cardSlicePrice) + ") or $2.00 off a whole Pie(" + formatter.format(cardPiePrice) + ")\n"
								+ "\tCharms) 10% off each charm(" + formatter.format(cardCharmPrice) + ")\n"
								+ "\tOverall) 10% off any orders of $100 and above\n");
						while(stay > 0)
						{
							//Prints a menu with 5 choices
							printMenu();
							menuChoice = keyboard.nextInt();
							//Make sure the user doesn't enter an invalid #
							while(menuChoice >= 6 || menuChoice <=0)
							{
								System.out.println("Please enter a valid menu number");
								menuChoice = keyboard.nextInt();
							}
							/*I know how much Professor Ramirez loves switch 
							 * statements, so I felt compelled to include one.
							 * This one reads input from the menu and does the 
							 * appropriate computation.  All ask for a number 
							 * of a certain item, it keeps asking the user to 
							 * enter a value if it is less than 0, does nothing
							 * if the value is equal to 0, and keeps the value 
							 * if the value is greater than 0
							 */
							switch(menuChoice)
							{
								case 1:
								{
									
									System.out.println("How many pepperoni pizzas would you like?");
									numPep = keyboard.nextInt();
									while(numPep < 0)
									{
										System.out.println("Please enter a valid amount");
										numPep = keyboard.nextInt();
									}
											
									System.out.println("How many cheese pizzas would you like?");
									numCheese = keyboard.nextInt();
									while(numCheese < 0)
									{
										System.out.println("Please enter a valid amount");
										numCheese = keyboard.nextInt();
									}										
									
									break;
								}
								case 2:
								{
									System.out.println("How many slices of pie would you like?(1 whole pie - 6 slices)");
									slices = keyboard.nextInt();
									while(slices < 0)
									{
										System.out.println("Please enter a valid amount");
										slices = keyboard.nextInt();
									}
										wholePies = slices / 6;
										slices = slices % 6;																														
									}
									break;
									
								
								case 3:
								{
									System.out.println("How many charms would you like?");
									charms = keyboard.nextInt();
									while(charms < 0)
									{
										System.out.println("Please enter a valid amount");
										charms = keyboard.nextInt();
									}									
									break;
								}
								case 4:
								{
									/*
									 * Computes the price and changes the order string at checkout in case
									 * the user changes their order
									 */
									if(numPep > 0)
									{

										pepTotal += numPep * cardPepPrice;
										order = order + "" + numPep + " pepperoni pizzas at " + formatter.format(cardPepPrice) + " each:\t" + formatter.format(pepTotal) + "\n";
									}
									if(numCheese > 0)
									{

										cheeseTotal += numCheese * cheesePrice;
										order = order +  numCheese + " cheese pizzas at " + formatter.format(cheesePrice) + " each:\t\t" + formatter.format(cheeseTotal) + "\n";
									}
									if(wholePies > 0)
									{

										pieTotal += wholePies * cardPiePrice;
										order = order + wholePies + " whole pies at " + formatter.format(cardPiePrice) + " each:\t\t" + formatter.format(pieTotal) + "\n";
									}
									if(slices > 0)
									{
										sliceTotal += slices + cardSlicePrice;
										order = order + slices + " slices of pie at " + formatter.format(cardSlicePrice) + " each:\t\t" + formatter.format(sliceTotal) + "\n";
									}
									if(charms > 0)
									{
										charmTotal += charms * cardCharmPrice;
										order = order + charms + " charms at " + formatter.format(cardCharmPrice) + " each:\t\t" + formatter.format(charmTotal) + "\n";
									}
									
										subtotal = pepTotal + cheeseTotal + pieTotal + sliceTotal + charmTotal;
									
									if(subtotal >= 100)
									{
										subtotal = subtotal - (subtotal * discountPercent);
									}
									
									//calculate tax and total
									taxTotal = subtotal * tax;
									total = subtotal + taxTotal;
									
									//Format the entire string created
									order = order + "\t\t\t\t\t------" + "\nSubtotal:\t\t\t\t" + formatter.format(subtotal) + "\nTax:\t\t\t\t\t"
											+ formatter.format(taxTotal) + "\n\t\t\t\t\t------\n" + "Total:\t\t\t\t\t" + formatter.format(total);
									
									//Finally printing the receipt
									System.out.println("Here is your receipt:\n\n"
											+ order + "\n");
									
									//Getting the money
									System.out.println("Please enter amount of money being given:");
									paymentAmount = keyboard.nextDouble();
									
									//Determining whether they have enough $$
									while(paymentAmount < total)
									{
										System.out.println("You still owe money, try again");
										paymentAmount = keyboard.nextDouble();
									}
									
									//Calculating and outputting change
									change = paymentAmount - total;
									System.out.println("Thank you, here is your change: " + formatter.format(change));
									
									//Exiting loops
									stay = 0;
									valid = 0;
									keyboard.nextLine();
									break;
									
									
								}
								/*
								 * If the user goes into labor or has a hear attack,
								 * this function allows them to exit quickly so nobody
								 * can acces their precious pies pies and pis account
								 * info.
								 */
								case 5:
								{
									System.out.println("Sorry you didn't want to buy anything.  See you next time!");
									stay = 0;
									System.exit(0);
								}
								
						}
						
						
						}
					}
					//Functionally the same as the above code, just with price changes
					else if(hasCard.toLowerCase().equals("no"))
					{
						keyboard.nextLine();
						System.out.println("Welcome valued customer, here are our prices:\n"
								+ "\tPizza) Cheese - " + formatter.format(cheesePrice) + "\t\tPepperoni - " + formatter.format(pepPrice) + "\n"
								+ "\tPies) Slice - " + formatter.format(slicePrice) + "\t\tWhole pie(6 slices) - " + formatter.format(piePrice) + "\n"
								+ "\tCharms) " + formatter.format(charmPrice) + "$\n");
						while(stay > 0)
						{
							printMenu();
							menuChoice = keyboard.nextInt();
							while(menuChoice >= 6 || menuChoice <=0)
							{
								System.out.println("Please enter a valid menu number");
								menuChoice = keyboard.nextInt();
							}
							switch(menuChoice)
							{
								case 1:
								{
									
									System.out.println("How many pepperoni pizzas would you like?");
									numPep = keyboard.nextInt();
									while(numPep < 0)
									{
										System.out.println("Please enter a valid amount");
										numPep = keyboard.nextInt();
									}
											
									System.out.println("How many cheese pizzas would you like?");
									numCheese = keyboard.nextInt();
									while(numCheese < 0)
									{
										System.out.println("Please enter a valid amount");
										numCheese = keyboard.nextInt();
									}										
									
									break;
								}
								case 2:
								{
									System.out.println("How many slices of pie would you like?(1 whole pie - 6 slices)");
									slices = keyboard.nextInt();
									while(slices < 0)
									{
										System.out.println("Please enter a valid amount");
										slices = keyboard.nextInt();
									}
										wholePies = slices / 6;
										slices = slices % 6;																														
									}
									break;
									
								
								case 3:
								{
									System.out.println("How many charms would you like?");
									charms = keyboard.nextInt();
									while(charms < 0)
									{
										System.out.println("Please enter a valid amount");
										charms = keyboard.nextInt();
									}									
									break;
								}
								case 4:
								{
									if(numPep > 0)
									{

										pepTotal += numPep * pepPrice;
										order = order + "" + numPep + " pepperoni pizzas at " + formatter.format(pepPrice) + " each:\t" + formatter.format(pepTotal) + "\n";
									}
									if(numCheese > 0)
									{

										cheeseTotal += numCheese * cheesePrice;
										order = order +  numCheese + " cheese pizzas at " + formatter.format(cheesePrice) + " each:\t\t" + formatter.format(cheeseTotal) + "\n";
									}
									if(wholePies > 0)
									{

										pieTotal += wholePies * piePrice;
										order = order + wholePies + " whole pies at " + formatter.format(piePrice) + " each:\t\t" + formatter.format(pieTotal) + "\n";
									}
									if(slices > 0)
									{
										sliceTotal += slices + slicePrice;
										order = order + slices + " slices of pie at " + formatter.format(slicePrice) + " each:\t\t" + formatter.format(sliceTotal) + "\n";
									}
									if(charms > 0)
									{
										charmTotal += charms * charmPrice;
										order = order + charms + " charms at " + formatter.format(charmPrice) + " each:\t\t" + formatter.format(charmTotal) + "\n";
									}
									
										subtotal = pepTotal + cheeseTotal + pieTotal + sliceTotal + charmTotal;
									taxTotal = subtotal * tax;
									total = subtotal + taxTotal;
									order = order + "\t\t\t\t\t------" + "\nSubtotal:\t\t\t\t" + formatter.format(subtotal) + "\nTax:\t\t\t\t\t"
											+ formatter.format(taxTotal) + "\n\t\t\t\t\t------\n" + "Total:\t\t\t\t\t" + formatter.format(total);
									System.out.println("Here is your receipt:\n\n"
											+ order);
									System.out.println("Please enter amount of money being given:");
									paymentAmount = keyboard.nextDouble();
									while(paymentAmount < total)
									{
										System.out.println("You still owe money, try again");
										paymentAmount = keyboard.nextDouble();
									}
									change = paymentAmount - total;
									System.out.println("Thank you, here is your change: " + formatter.format(change));
									stay = 0;
									valid = 0;
									keyboard.nextLine();
									break;
									
									
								}
								case 5:
								{
									System.out.println("Sorry you didn't want to buy anything.  See you next time!");
									System.out.println("Re-run if another customer is here");
									stay = 0;
									System.exit(0);
								}
								
							}
						}
					}
					
					//Tells the user if they entered something wrong	
					else
					{
						System.out.println("What you entered was invalid, try again");
						keyboard.nextLine();
						System.out.println("Welcom to pies, pies, "
								+ "and Πs!\n  Do you have a pie card?(Yes or no)");
						hasCard = keyboard.next();
						
					}
				}
			}
			//Leaves if the customer enters no
			else if(customer.toLowerCase().equals("no"))
			{
				System.out.println("Re-run if another customer is here");
				break;
			}
			
			//Tells the user he/she entered something wrong if they type in something else
			else
			{
				System.out.println("What you entered was invalid, try again");
			}
		}
	}
	/**
	 * Displays the menu
	 */
	public static void printMenu()
	{
		System.out.println("Main Menu:\n"
				+ "\t1)Update Pizza Order\n"
				+ "\t2)Update Pie Order\n"
				+ "\t3)Update Charm Order\n"
				+ "\t4)Checkout\n"
				+ "\t5)Cancel order and exit");
	}
}
