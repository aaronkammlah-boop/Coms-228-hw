package edu.iastate.cs2280.hw1;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
/**
 * @author [redacted]
 * 
 * Test different ways to check if
 * the census function is working correctly
 * Creating a 3 by 3 grid to use on different test
 */
class TownCellTest {
private Town t;
/**
 * 3 by 3 for every test
 * C S R
 * E O C
 * R S E
 */
@BeforeEach
public void grid() {
	t = new Town(3,3);
	t.grid[0][0] = new Casual(t,0,0);
	t.grid[0][1] = new Streamer(t,0,1);
	t.grid[0][2] = new Reseller(t,0,2);
	t.grid[1][0] = new Empty(t,1,0);
	t.grid[1][1] = new Outage(t,1,1);
	t.grid[1][2] = new Casual(t,1,2);
	t.grid[2][0] = new Reseller(t,2,0);
	t.grid[2][1] = new Streamer(t,2,1);
	t.grid[2][2] = new Empty(t,2,2);
}
/**
 * test the center cell and the amount of each cell around it 
 */
	@Test
	public void testcenter() {
		int[] census = new int[TownCell.NUM_CELL_TYPE];
		t.grid[1][1].census(census);
		assertEquals(2,census[TownCell.CASUAL]);
		assertEquals(2,census[TownCell.STREAMER]);
		assertEquals(2,census[TownCell.RESELLER]);
		assertEquals(2,census[TownCell.EMPTY]);
		assertEquals(0,census[TownCell.OUTAGE]);
	}
/**
 * Same test as the center but taking the corner/ orgin
 */
	@Test
	public void testcorner() {
		int[] census = new int[TownCell.NUM_CELL_TYPE];
		t.grid[0][0].census(census);
		assertEquals(0,census[TownCell.CASUAL]);
		assertEquals(1,census[TownCell.STREAMER]);
		assertEquals(0,census[TownCell.RESELLER]);
		assertEquals(1,census[TownCell.EMPTY]);
		assertEquals(1,census[TownCell.OUTAGE]);
	}
	/**
	 * Tests the census resets the array before counting
	 */
	@Test
	public void testreset() {
		int[] census = new int[TownCell.NUM_CELL_TYPE];
		census[TownCell.CASUAL] = 78;
		census[TownCell.EMPTY]= 12;
		t.grid[1][1].census(census);
		assertTrue(census[TownCell.CASUAL] <99);
		assertTrue(census[TownCell.EMPTY] <42);
	}
}

