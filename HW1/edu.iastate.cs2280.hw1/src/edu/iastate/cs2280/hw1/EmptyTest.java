package edu.iastate.cs2280.hw1;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * @author [redacted]
 * This class test the class Casual.java
 * I created each test to run each rule that empty uses 
 * to determine what cell it is or will become on the town grid.
 * 
 */

public class EmptyTest {
/**
 * Test the who() function returns the empty state.
 * made a 1 by 1 and set the base cell as empty and 
 * used assert equals to determine if it was equal.
 */
	@Test
	public void testWho() {
		Town t = new Town(1 ,1);
		t.grid[0][0] = new Empty(t,0,0);
		assertEquals(State.EMPTY, t.grid[0][0].who());
	}
	/**
	 * test rule 6a "Any cell that (1) is not a Reseller or Outage and (2) 
	 * and has (Number of Empty + Number of Outage neighbors less than or equal to 1) 
	 * converts to Reseller. 
	 * made the causal have no one else so the rule would be met and 
	 * a streamer would be created
	 */
	@Test
	public void test6a() {
		Town t = new Town(1,1);
		t.grid[0][0] = new Casual(t,0,0);
		Town tnew = new Town(1,1);
		assertEquals(State.RESELLER, t.grid[0][0].next(tnew).who());
	}
	
	/**
	 *  test rule 5 'If the cell was empty, then a Casual user takes it 
	 *  and it becomes a C."
	 *   I created a town that skips all rules and checks if it becomes a casual
	 */
	@Test
	public void rule5() {
		Town t = new Town(3,3);
		for(int r = 0; r< 3; r++) 
			for(int c = 0; c<3; c++) 
				t.grid[r][c] = new Empty(t,r,c);
				Town tnew = new Town(3,3);
				assertEquals(State.CASUAL, t.grid[0][0].next(tnew).who());
	}
}