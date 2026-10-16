package edu.iastate.cs2280.hw1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

/**
 * @author [redacted]
 *This class is used to help construct the town
 *function in which the cells reside.
 */
public class Town {

	private int length, width; // Row and col (first and second indices)
	public TownCell[][] grid;

	/**
	 * Constructor to be used when user wants to generate grid randomly, with the
	 * given seed. This constructor does not populate each cell of the grid (but
	 * should assign a 2D array to it).
	 * 
	 * @param length
	 * @param width
	 */
	public Town(int length, int width) {
		this.length = length;
		this.width = width;
		grid = new TownCell[length][width];
	}

	/**
	 * Constructor to be used when user wants to populate grid based on a file.
	 * Please see that it simple throws FileNotFoundException exception instead of
	 * catching it. Ensure that you close any resources (like file or scanner) which
	 * is opened in this function.
	 * 
	 * @param inputFileName
	 * @throws FileNotFoundException
	 */
	public Town(String inputFileName) throws FileNotFoundException {
		Scanner scan = new Scanner(new File(inputFileName));
		length = scan.nextInt();
		width = scan.nextInt();
		grid = new TownCell[length][width];
		for (int r = 0; r < length; r++) {
			for (int c = 0; c < width; c++) {
				String line = scan.next();
				char character = line.charAt(0);
				if(character == 'C') {
					grid[r][c] = new Casual(this, r, c);
				}
				else if(character == 'S') {
					grid[r][c] = new Streamer(this, r, c);
				}
				else if(character == 'R') {
					grid[r][c] = new Reseller(this, r, c);
				}
				else if(character == 'E') {
					grid[r][c] = new Empty(this, r, c);
				}
				else if(character == 'O') {
					grid[r][c] = new Outage(this, r, c);
				}
				else {
					grid[r][c] = new Empty(this, r, c);
				}
				}
			}
		scan.close();
		}
		
	

	/**
	 * Returns width of the grid.
	 * 
	 * @return width
	 */
	public int getWidth() {

		return width;
	}

	/**
	 * Returns length of the grid.
	 * 
	 * @return length
	 */
	public int getLength() {

		return length;
	}

	/**
	 * Initialize the grid by randomly assigning cell with one of the following
	 * class object: Casual, Empty, Outage, Reseller OR Streamer
	 * 
	 * using the randomint assigns each cell with a random character
	 */
	public void randomInit(int seed) {
		Random rand = new Random(seed);
		for (int r = 0; r < length; r++) {
			for (int c = 0; c < width; c++) {
				int num = rand.nextInt(TownCell.NUM_CELL_TYPE);
				if(num == TownCell.CASUAL) {
					grid[r][c] = new Casual(this, r, c);
				}
				else if(num == TownCell.STREAMER)
					grid[r][c] = new Streamer(this, r, c);
					
					if(num == TownCell.RESELLER)
					grid[r][c] = new Reseller(this, r, c);
					
					if(num == TownCell.EMPTY)
					grid[r][c] = new Empty(this, r, c);
					
					if(num == TownCell.OUTAGE)
					grid[r][c] = new Outage(this, r, c);
				
				}
			}
		}
	

	/**
	 * Output the town grid. For each square, output the first letter of the cell
	 * type. Each letter should be separated either by a single space or a tab. And
	 * each row should be in a new line. There should not be any extra line between
	 * the rows.
	 */
	@Override
	public String toString() {
		String s = "";
		for (int r = 0; r < length; r++) {
			for (int c = 0; c < width; c++) {
				if (c > 0)
					s += " ";
				s += grid[r][c].who().name().charAt(0);
			}
			s += "\n";
		}
		return s;

	}
}
