
package edu.iastate.cs2280.hw1;

/**
 * @author [redacted]
 *  Represents a Empty  cell
 * empty has no profit
 *
 * rules: 
 *   Rule 6a:(empty + outage neighbors <= 1) becomes Reseller.
 *   Rule 5:  otherwise becomes Casual.
 */
 
public class Empty extends TownCell {

	/**
	 * makes a empty cell at the given position
 * @param p town the cell belongs to
 * @param r row
 * @param c column
	 */
	public Empty(Town p, int r, int c) {
		super(p,r,c);
	}
	/**
	 * returns the cell as empty
	 * @return State.EMPTY
	 */
	public State who() {
		return State.EMPTY;
	}
	/**
	 * Using the given rules to determine the state of a given Empty
	 * cell going on to the next month
	 * @param tNew next town object (next month)
	 * @return returns towncell for the next month
	 */
	public TownCell next(Town tNew) {
		census(nCensus);
		//Rule 6a:(empty + outage neighbors <= 1) becomes Reseller.
		if(nCensus[EMPTY] + nCensus[OUTAGE] <=1) {
			return new Reseller(tNew, row, col);
		}//Rule 5:  otherwise becomes Casual.
		return new Casual(tNew,row,col);
		
}
}
