package edu.iastate.cs2280.hw1;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * @author [redacted]
 * This class test the class Casual.java
 * I created each test to run each rule that casual uses 
 * to determine what cell it is on the town grid.
 * 
 */

public class CasualTest {
/**
 * Test the who() function returns the casual state.
 * made a 1 by 1 and set the base cell as Casual and 
 * used assert equals to determine if it was equal.
 */
	@Test
	public void testWho() {
		Town t = new Town(1 ,1);
		t.grid[0][0] = new Casual(t,0,0);
		assertEquals(State.CASUAL, t.grid[0][0].who());
	}
	/**
	 * Test the rule 1a "If there is any reseller in
	 *  its neighborhood, then the reseller causes outage in the
		casual user cell. Thus, the state of the 
		cell changes from C (Casual) to O (Outage)."
		Created a 3x3 with outage cells to easily determine if a 
		reseller would turn a casual to an outage 
	 */
	@Test
	public void test1a() {
		Town t = new Town(3,3);
		for(int r = 0; r< 3; r++) 
			for(int c = 0; c<3; c++) 
				t.grid[r][c] = new Outage(t,r,c);
				t.grid[1][1] = new Casual(t,1,1);
				t.grid[0][0] = new Reseller(t,0,0);
				Town tnew = new Town(3,3);
				assertEquals(State.OUTAGE, t.grid[1][1].next(tnew).who());
			
		
	}
	/**
	 * Test rule 1b "Otherwise, if there is any neighbor who is 
	 * a streamer, then the casual user also
	becomes a streamer, in the hopes of making it big on the internet"
	Created a 3x3 with outage cells to easily determine if a 
		reseller would turn a casual to a streamer 
	 */
	@Test
	public void test1b () {
		Town t = new Town(3,3);
		for(int r = 0; r< 3; r++) 
			for(int c = 0; c<3; c++) 
				t.grid[r][c] = new Outage(t,r,c);
				t.grid[1][1] = new Casual(t,1,1);
				t.grid[0][0] = new Streamer(t,0,0);
				Town tnew = new Town(3,3);
				assertEquals(State.STREAMER, t.grid[1][1].next(tnew).who());
			
		
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
	 * test rule 6b "If none of the above rules apply, any cell with 
	 * 5 or more casual neighbors becomes a Streamer."
	 * created a town that skips the previous task and
	 * checks if a streamer is created if this rule is met
	 */
	@Test
	public void test6b(){
		Town t = new Town(3,3);
		for(int r = 0; r< 3; r++) 
			for(int c = 0; c<3; c++) 
				t.grid[r][c] = new Casual(t,r,c);
				t.grid[0][0] = new Empty(t,0,0);
				t.grid[0][1] = new Empty(t,0,1);
				Town tnew = new Town(3,3);
				assertEquals(State.STREAMER, t.grid[1][1].next(tnew).who());
				
	}
	/**
	 *  test rule 7 'If none of the rules apply, then the cell state
	 *   remains unchanged for the next iteration"
	 *   I created a town that skips all rules and checks if it stays casual
	 */
	@Test
	public void rule7() {
		Town t = new Town(3,3);
		for(int r = 0; r< 3; r++) 
			for(int c = 0; c<3; c++) 
				t.grid[r][c] = new Empty(t,r,c);
				t.grid[1][1] = new Casual(t,0,0);
				Town tnew = new Town(3,3);
				assertEquals(State.CASUAL, t.grid[0][0].next(tnew).who());
	}
}
