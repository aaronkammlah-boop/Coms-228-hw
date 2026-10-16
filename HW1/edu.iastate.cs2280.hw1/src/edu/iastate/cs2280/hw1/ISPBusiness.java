package edu.iastate.cs2280.hw1;
import java.io.FileNotFoundException;
import java.util.Scanner;
/**
 * @author [redacted]
 *
 * The ISPBusiness class performs simulation over a grid 
 * plain with cells occupied by different TownCell types.
 * gets the prof and uses it to find the total utilization
 *
 */
public class ISPBusiness {
	
	/**
	 * Returns a new Town object with updated grid value for next billing cycle.
	 * @param tOld: old/current Town object.
	 * @return: New town object.
	 */
	public static Town updatePlain(Town tOld) {
		Town tNew = new Town(tOld.getLength(), tOld.getWidth());
		for(int r = 0; r < tOld.getLength(); r++) {
			for( int c = 0; c < tOld.getWidth(); c++) {
				tNew.grid[r][c] = tOld.grid[r][c].next(tNew);
			}
		}
		return tNew;
	}
	
	/**
	 * Returns the profit for the current state in the town grid.
	 * @param town
	 * @return num 
	 */
	public static int getProfit(Town town) {
		 int num = 0;
		 for(int r = 0; r < town.getLength(); r++) {
			 for(int c = 0; c < town.getWidth(); c++) {
				 if(town.grid[r][c].who() == State.CASUAL) {
					 num++;
				 }
			 }
		 }
		return num;
	}
	

	/**
	 *  Main method. Interact with the user and ask if user wants to specify elements of grid
	 *  via an input file (option: 1) or wants to generate it randomly (option: 2).
	 *  
	 *  Depending on the user choice, create the Town object using respective constructor and
	 *  if user choice is to populate it randomly, then populate the grid here.
	 *  
	 *  Finally: For 12 billing cycle calculate the profit and update town object (for each cycle).
	 *  Print the final profit in terms of %. You should print the profit percentage
	 *  with two digits after the decimal point:  Example if profit is 35.5600004, your output
	 *  should be:
	 *
	 *	35.56%
	 *  
	 * Note that this method does not throw any exception, so you need to handle all the exceptions
	 * in it.
	 * 
	 * @param args
	 * 
	 */
	public static void main(String []args) {
		Scanner scan = new Scanner(System.in);
		int choice = 0;
		Town town = null;
		while(choice!= 1 && choice!= 2) {
			System.out.println("How to populate grid (type 1 or 2): 1: from a file. 2: randomly with seed");
			choice= scan.nextInt();
			if (choice!= 1 && choice!= 2) {
				System.out.println("Invalid selection: please enter 1 or 2.");
				
			}
		}
		if(choice == 1) {
			System.out.println("Please enter a file path:");
			String file = scan.next();
			try {
				town = new Town(file);
			}catch(FileNotFoundException e) {
				System.out.println("File not found: "+ file);
				scan.close();
				return;
			}
		}else {
			System.out.println("Provide rows, cols and seed integer separated by spaces: ");
			int row = scan.nextInt();
			int col = scan.nextInt();
			int seed = scan.nextInt();
			town = new Town(row, col);
			town.randomInit(seed);
		}
		scan.close();
		int totalProf = 0;
		int maxPot = town.getLength() * town.getWidth() * 12;
		for(int month = 0 ; month< 12; month++ ) {
			totalProf += getProfit(town);
			town = updatePlain(town);
		}
		double utilization = 100.0 * totalProf / maxPot;
		System.out.printf("%.2f" + "%%%n", utilization);
	}
}
