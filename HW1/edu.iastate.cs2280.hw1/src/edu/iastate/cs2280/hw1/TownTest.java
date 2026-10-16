package edu.iastate.cs2280.hw1;

import static org.junit.jupiter.api.Assertions.*;
import java.io.FileNotFoundException;
import org.junit.jupiter.api.Test;
/**
 * test the functions in this class like randomint to string getwidth
 * length etc.
 */
class TownTest {
/**
 * Test the getwidth, length and size function
 * 
 */
	@Test
	public void testdim() {
		Town t = new Town (1,2);
		assertEquals(1,t.getLength());
		assertEquals(2,t.getWidth());
	}
		/**
		 * Test that the same seed produces the same grid
		 * (use the seed given to us)
		 */
	@Test
	public void testrandomsame() {
		Town t1= new Town(4,4);
		t1.randomInit(10);
		Town t2 = new Town(4,4);
		t2.randomInit(10);
		for(int r = 0; r < 4; r++) {
		for(int c =0; c<4; c++) {
			assertEquals(t1.grid[r][c].who(), t2.grid[r][c].who());
		}
		}
	}
	/**
	 * test the file ISP4x4.txt is read correctly
	 */
	@Test
	public void testfile() throws FileNotFoundException {
		Town t = new Town("ISP4x4.txt");
		assertEquals(4,t.getLength());
		assertEquals(4,t.getWidth());
		assertEquals(State.OUTAGE,t.grid[0][0].who());
		assertEquals(State.RESELLER,t.grid[0][1].who());
		assertEquals(State.CASUAL,t.grid[1][2].who());
	}
	/**
	 * Test that toString to give the correct number of rows
	 * 
	 */
	@Test
	public void testtostring(){
		Town t = new Town(3,3);
		t.randomInit(1);
		String[] lines = t.toString().trim().split("\n");
		assertEquals(3, lines.length);
	}
}
