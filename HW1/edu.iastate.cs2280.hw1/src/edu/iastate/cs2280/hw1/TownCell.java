package edu.iastate.cs2280.hw1;


/**
 * 
 * @author [redacted]
 *	An abstract class used to represent one cell on the grid
 *  is used to help tell which cell is used with the who function
 *
 */
public abstract class TownCell {

	protected Town plain;
	protected int row;
	protected int col;
	
	
	// constants to be used as indices.
	protected static final int RESELLER = 0;
	protected static final int EMPTY = 1;
	protected static final int CASUAL = 2;
	protected static final int OUTAGE = 3;
	protected static final int STREAMER = 4;
	
	public static final int NUM_CELL_TYPE = 5;
	
	//Use this static array to take census.
	public static final int[] nCensus = new int[NUM_CELL_TYPE];

	public TownCell(Town p, int r, int c) {
		plain = p;
		row = r;
		col = c;
	}
	
	/**
	 * Checks all neigborhood cell types in the neighborhood.
	 * Refer to homework pdf for neighbor definitions (all adjacent
	 * neighbors excluding the center cell).
	 * Use who() method to get who is present in the neighborhood
	 *  
	 * @param counts of all customers
	 */
	public void census(int nCensus[]) {
		// zero the counts of all customers
		nCensus[RESELLER] = 0; 
		nCensus[EMPTY] = 0; 
		nCensus[CASUAL] = 0; 
		nCensus[OUTAGE] = 0; 
		nCensus[STREAMER] = 0; 
		int rowMin = Math.max( 0,row -1 ); // gets the rowmin
		int colMin = Math.max( 0,col -1 ); // col min
		int rowMax = Math.min(plain.getLength() -1, row +1); // row max
		int colMax = Math.min(plain.getWidth() -1, col +1); // col max
		for(int r = rowMin; r<= rowMax; r++) {
			for(int c = colMin; c<= colMax;c++) {
				if( r == row && c == col) continue;
				State s = plain.grid[r][c].who();
				if(s == State.RESELLER) { 
				nCensus[RESELLER]++; // adds to the reseller count
				}
				if(s == State.EMPTY) {
				nCensus[EMPTY]++; // adds to the empty count 
				}
				if(s == State.CASUAL) {
				nCensus[CASUAL]++; // adds to the casual count
				}
				if(s == State.OUTAGE) {
					nCensus[OUTAGE]++; // adds to outage count 
					}
				if(s == State.STREAMER) {
					nCensus[STREAMER]++; // adds to the streamer count
					}
			}
		}
	}

	/**
	 * Gets the identity of the cell.
	 * 
	 * @return State
	 */
	public abstract State who();

	/**
	 * Determines the cell type in the next cycle.
	 * 
	 * @param tNew: town of the next cycle
	 * @return TownCell
	 */
	public abstract TownCell next(Town tNew);
}
