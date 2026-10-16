package edu.iastate.cs2280.hw1;
/**
 * @author [redacted]
 *
 * Represents a Streamer cell
 * Streamers do not generate any profit
 * rules: 
 * Rule 6a:(empty + outage neighbors <= 1) becomes Reseller.
 *   Rule 2a: any reseller on the grid becomes an outage
 *   Rule 2b: if any outage cell is an outage become empty
 *   Rule 6b:if 5+ casual neighbors becomes Streamer.
 *   Rule 7:otherwise stays Streamer
 */
public class Streamer extends TownCell {
	/**
	 * makes a Streamer cell at the given position
	 * @param p town the cell belongs to
	 * @param r row
	 * @param c column
		 */
	public Streamer(Town p, int r, int c) {
		super(p,r,c);
	}
	/**
	 * returns the cell as Streamer
	 * @return State.STREAMER

	 */
	@Override
	public State who() {
		return State.STREAMER;
	}
	/**
	 * /**
	 * Using the given rules to determine the state of a given Streamer
	 * cell going on to the next month
	 * @param tNew next town object (next month)
	 * @return returns towncell for the next month
	 */
	@Override
	public TownCell next(Town tNew) {
		census(nCensus);
		//Rule 6a:(empty + outage neighbors <= 1) becomes Reseller.
		if(nCensus[EMPTY] + nCensus[OUTAGE] <=1) {
			return new Reseller(tNew, row, col);
		}//Rule 2a: any reseller on the grid becomes an outage
		if(nCensus[RESELLER]> 0) {
			return new Outage(tNew, row, col);
		} // Rule 2b: if any outage cell is an outage become empty
		if(nCensus[OUTAGE]> 0) {
			return new Empty(tNew, row, col);
	}//Rule 6b:if 5+ casual neighbors becomes Streamer.
		if(nCensus[CASUAL]>= 5) {
			return new Streamer(tNew, row, col);
		} // Rule 7:otherwise stays Streamer
		return new Streamer(tNew,row,col);
	}
}
