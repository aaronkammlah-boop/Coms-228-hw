package edu.iastate.cs2280.hw1;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
/**
 * @author [redacted]
 * 
 * Test the ispBusiness class and its functions
 */
class ISPBusinessTest {
/**
 * test that get profit works with a mixed town
 * I created a grid that used each of the cells to see
 * if the function works
 */
	@Test
	public void testgetprofit() {
		Town t = new Town(2,3);
		t.grid[0][0] = new Casual(t,0,0);
		t.grid[0][1] = new Streamer(t,0,1);
		t.grid[0][2] = new Reseller(t,0,2);
		t.grid[1][0] = new Empty(t,1,0);
		t.grid[1][1] = new Outage(t,1,1);
		t.grid[1][2] = new Casual(t,1,2);
		assertEquals(2, ISPBusiness.getProfit(t));
	}
/**
 * test to make sure update plain works
 * used the given 4x4 grid
 */
	@Test
	public void testupdateplain(){
		Town t = new Town(4,4);
		t.randomInit(10);
		Town tnew = ISPBusiness.updatePlain(t);
		assertEquals(t.getLength(), tnew.getLength());
		assertEquals(t.getWidth(), tnew.getWidth());
	}
	/**
	 * Test the given seed 4x4 with the seed of 10
	 */
	@Test
	public void testisp(){
		Town t = new Town(4,4);
		t.randomInit(10);
		int prof = 0;
		int maxprof = t.getLength() * t.getWidth() * 12;
		for(int month = 0; month < 12; month++) {
			prof += ISPBusiness.getProfit(t);
			t = ISPBusiness.updatePlain(t);
		}
		double uti = 100.0 * prof / maxprof;
		assertEquals(27.60, uti, .01);
	}
}
