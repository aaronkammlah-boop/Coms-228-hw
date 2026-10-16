package edu.iastate.cs2280.hw2;

import java.io.FileNotFoundException;
import java.lang.NumberFormatException; 
import java.lang.IllegalArgumentException; 
import java.util.InputMismatchException;


/**
 *  
 * @author [redacted]
 *
 */

/**
 * 
 * This class implements insertion sort.   
 *
 */

public class InsertionSorter extends AbstractSorter 
{
	
	/**
	 * Constructor takes an array of points.  It invokes the superclass constructor, and also 
	 * set the instance variables algorithm in the superclass.
	 * 
	 * @param pts  
	 */
	public InsertionSorter(Point[] pts) 
	{
		super(pts);
		algorithm = "insertion Sort";
	}	

	
	/** 
	 * Perform insertion sort on the array points[] of the parent class AbstractSorter.  
	 */
	@Override 
	public void sort()
	{
		for(int i = 1; i <points.length; i++) {
			Point num = points[i];
			int j = i-1;
			//shifts larger nums to the right to insert next num
			while(j>= 0 && pointComparator.compare(points[j], num)>0) {
				points[j+1] = points[j];
				j--;
			}
			//inserts num into correct spot
			points[j+1] = num;
		}
	}		
}
