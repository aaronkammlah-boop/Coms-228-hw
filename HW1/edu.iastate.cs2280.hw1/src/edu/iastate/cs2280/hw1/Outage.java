
package edu.iastate.cs2280.hw1;

/**
 * @author [redacted]
 * Represents an outage cell
 * outage cells have no profit
 * 
 * Rules: 6a and 6b don't apply here
 * only rule 4 applies
 * Rule 4: always becomes Empty
 */
public class Outage extends TownCell {
	/**makes a Outage cell at the given position
	 * @param p town the cell belongs to
	 * @param r row
	 * @param c column
	 */
	public Outage(Town p, int r, int c) {
		super(p,r,c);
	}
	/**
	 * returns the cell as outage
	 * @return State.OUTAGE
	 */
	@Override
	public State who() {
		return State.OUTAGE;
	}
	/**
	 * Using the given rules to determine the state of a given outage
	 * cell going on to the next month
	 * @param tNew next town object (next month)
	 * @return returns towncell for the next month
	 */
	@Override
	public TownCell next(Town tNew) {
		//Rule 4: always becomes Empty
		return new Empty(tNew,row,col);
	}
}
