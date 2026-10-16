package edu.iastate.cs2280.hw1;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
/**
 * @author [redacted]
 
* This class test the class Outage.java
* I created each test to run each rule that outage uses 
* to determine what cell it is on the town grid.
*/
class OutageTest {
	/**
	 * Test the who() function returns the outage state.
	 * made a 1 by 1 and set the base cell as empty and 
	 * used assert equals to determine if it was equal.
	 */
		@Test
		public void testWho() {
			Town t = new Town(1 ,1);
			t.grid[0][0] = new Outage(t,0,0);
			assertEquals(State.OUTAGE, t.grid[0][0].who());
		}
/**
 * test rule 4 "An Outage cell becomes an Empty cell, 
 * meaning internet access is restored
on the billing cycle after an outage."
Created a test field to see if a outage would turn empty
 */
		@Test
		public void testrule4() {
			Town t = new Town(1,1);
			for(int r = 0; r< 1; r++) 
				for(int c = 0; c<1; c++) 
					t.grid[r][c] = new Outage(t,r,c);
					Town tnew = new Town(1,1);
					assertEquals(State.EMPTY, t.grid[0][0].next(tnew).who());
		}
}
