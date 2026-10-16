
package edu.iastate.cs2280.hw1;
/**
 * @author [redacted]
 *
 * Represents a reseller cell
 * Resellers do not generate any profit
 * rules: 
 *   Rule 3a:if casual neighbors <= 3 becomes Empty
 *   Rule 3b: if empty neighbors >= 3 becomes Empty
 *   Rule 6b:if 5+ casual neighbors becomes Streamer.
 *   Rule 7:otherwise stays Reseller
 */
public class Reseller extends TownCell{

	/**
 * makes a reseller cell at the given position
 * @param p town the cell belongs to
 * @param r row
 * @param c column
	 */
	public Reseller(Town p, int r, int c) { 
		super(p,r,c);
	}
	@Override
	/**
	 * returns the cell as reseller
	 * @return State.RESELLER

	 */
	public State who() {
		return State.RESELLER;
	}
	/**
	 * /**
	 * Using the given rules to determine the state of a given reseller
	 * cell going on to the next month
	 * @param tNew next town object (next month)
	 * @return returns towncell for the next month
	 */
	 
	@Override
	public TownCell next(Town tNew) {
		census(nCensus);
		//Rule 3a:if casual neighbors <= 3 becomes Empty
		if(nCensus[CASUAL] <=3) {
			return new Empty(tNew, row,col);
		}//Rule 3b: if empty neighbors >= 3 becomes Empty
		if(nCensus[EMPTY]>=3) {
			return new Empty(tNew, row, col);
		}//Rule 6b:if 5+ casual neighbors becomes Streamer.
		if(nCensus[CASUAL]>= 5) {
			return new Streamer(tNew, row,  col);
	}//Rule 7:otherwise stays Reseller
		return new Reseller(tNew,row,col);
		
}
}
