package edu.iastate.cs2280.hw1;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * @author [redacted]
 * This class test the class Reseller.java
 * I created each test to run each rule that casual uses 
 * to determine what cell it is on the town grid.
 * 
 */

public class ResellerTest {
/**
 * Test the who() function returns the  state.
 * made a 1 by 1 and set the base cell as Casual and 
 * used assert equals to determine if it was equal.
 */
	@Test
	public void testWho() {
		Town t = new Town(1 ,1);
		t.grid[0][0] = new Reseller(t,0,0);
		assertEquals(State.RESELLER, t.grid[0][0].who());
	}
	/**
	 * Test the rule 3a "If there are 3 or fewer casual users in the neighborhood,
	 *  then Reseller finds it unprofitable to maintain the business and leaves, making the cell Empty."
		Created a 3x3 with outage cells to easily determine if a 
		reseller would turn a caasual to an outage 
	 */
	@Test
	public void test3a() {
		Town t = new Town(1,1);
				t.grid[0][0] = new Reseller(t,0,0);
				Town tnew = new Town(1,1);
				assertEquals(State.EMPTY, t.grid[0][0].next(tnew).who());
			
		
	}
	/**
	 * Test rule 1b "Also, if there are 3 or more empty cells in the neighborhood, then the Reseller
leaves, making the cell Empty. Resellers do not like unpopulated regions."
	Created a 3x3 with empty cells and a reseller to easily determine if a 
		reseller would turn into an empty 
	 */
	@Test
	public void test3b () {
		Town t = new Town(3,3);
		for(int r = 0; r< 3; r++) 
			for(int c = 0; c<3; c++) 
				t.grid[r][c] = new Casual(t,r,c);
				t.grid[0][0] = new Empty(t,0,0);
				t.grid[0][1] = new Empty(t,0,1);
				t.grid[1][0] = new Empty(t,1,0);
				t.grid[0][2] = new Empty(t,0,2);
				t.grid[1][1] = new Reseller(t,1,1);
				Town tnew = new Town(3,3);
				assertEquals(State.EMPTY, t.grid[1][1].next(tnew).who());
			
		
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
				t.grid[1][1] = new Reseller(t,1,1);
				Town tnew = new Town(3,3);
				assertEquals(State.STREAMER, t.grid[1][1].next(tnew).who());
				
	}
	/**
	 *  test rule 7 'If none of the rules apply, then the cell state
	 *   remains unchanged for the next iteration"
	 *   I created a town that skips all rules and checks if it stays Reseller
	 */
	@Test
	public void rule7() {
		Town t = new Town(3,3);
		for(int r = 0; r< 3; r++) 
			for(int c = 0; c<3; c++) 
				t.grid[r][c] = new Casual(t,r,c);
			t.grid[0][0] = new Streamer(t,0,0);
			t.grid[0][1] = new Streamer(t,0,1);
			t.grid[1][0] = new Streamer(t,1,0);
			t.grid[0][2] = new Streamer(t,0,2);
			t.grid[1][1] = new Reseller(t,1,1);
				Town tnew = new Town(3,3);
				assertEquals(State.RESELLER, t.grid[0][0].next(tnew).who());
	}
}
