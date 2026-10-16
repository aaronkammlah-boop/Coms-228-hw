package edu.iastate.cs2280.hw1;
/**
 * @author [redacted]
 *
 * Represents a Casual  cell
 * Casual customers use minimal bandwidth and generate $1 profit each month.
 *
 * rules: 
 *   Rule 6a:(empty + outage neighbors <= 1) becomes Reseller.
 *   Rule 1a:if any Reseller neighbor becomes Outage.
 *   Rule 1b:if any Streamer neighbor becomes Streamer.
 *   Rule 6b:if 5+ casual neighbors becomes Streamer.
 *   Rule 7:otherwise stays Casual.
 */
public class Casual extends TownCell {
/**
 * makes a casual cell at the given position
 * @param p town the cell belongs to
 * @param r row
 * @param c column
 */
	public Casual(Town p, int r, int c) {
		super(p, r, c);
	}

	@Override
	/**
	 * reterns the state as casual 
	 * @return State.CASUAL
	 */
	public State who() {
		return State.CASUAL;
	}

	@Override
	/**
	 * Using the given rules to determine the state of a given casual
	 * cell going on to the next month
	 * @param tNew next town object (next month)
	 * @return returns towncell for the next month
	 */
	public TownCell next(Town tNew) {
		census(nCensus);
		//Rule 6a(empty + outage neighbors <= 1) becomes Reseller.
		if (nCensus[EMPTY] + nCensus[OUTAGE] <= 1) {
			return new Reseller(tNew, row, col);
		} //Rule 1b if any Reseller neighbor becomes Outage.
		if (nCensus[RESELLER] > 0) {
			return new Outage(tNew, row, col);
		} //Rule 1b:if any Streamer neighbor becomes Streamer.
		if (nCensus[STREAMER] > 0) {
			return new Streamer(tNew, row, col);
		}//Rule 6b:if 5+ casual neighbors becomes Streamer.
		if (nCensus[CASUAL] >= 5) {
			return new Streamer(tNew, row, col);
		} // Rule 7:otherwise stays Casual.
		return new Casual(tNew, row, col);

	}
}
