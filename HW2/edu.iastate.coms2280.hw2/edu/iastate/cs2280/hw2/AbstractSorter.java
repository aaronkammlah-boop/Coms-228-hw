 
package edu.iastate.cs2280.hw2;

/**
 *  
 * @author [redacted]
 *
 */

import java.util.Comparator;
import java.io.FileNotFoundException;
import java.lang.IllegalArgumentException; 
import java.util.InputMismatchException;

/**
 * 
 * This abstract class is extended by SelectionSort, InsertionSort, MergeSort, and QuickSort.
 * It stores the input (later the sorted) sequence. 
 *
 */
public abstract class AbstractSorter
{
	
	protected Point[] points;    // array of points operated on by a sorting algorithm. 
	                             // stores ordered points after a call to sort(). 
	
	protected String algorithm = null; // "selection sort", "insertion sort", "mergesort", or
	                                   // "quicksort". Initialized by a subclass constructor.
		 
	protected Comparator<Point> pointComparator = null;  
	
	
	 

	
	
	/**
	 * This constructor accepts an array of points as input. Copy the points into the array points[]. 
	 * 
	 * @param  pts  input array of points 
	 * @throws IllegalArgumentException if pts == null or pts.length == 0.
	 */
	protected AbstractSorter(Point[] pts) throws IllegalArgumentException
	{
		// if the input is null or length of the array is zero it throws an exception
		if(pts==null || pts.length == 0) {
			throw new IllegalArgumentException("array is null or empty");
		}
		// copies points into a new array
		points = new Point[pts.length];
		for(int i = 0; i<pts.length;i++) {
			points[i] = new Point(pts[i]);
		}
	}

		
	
	
	

	/**
	 * Generates a comparator on the fly that compares by x-coordinate if order == 0, by y-coordinate
	 * if order == 1. Assign the 
     * comparator to the variable pointComparator. 
     *  
	 * 
	 * @param order  0   by x-coordinate 
	 * 				 1   by y-coordinate
	 * 			    
	 * 
	 * @throws IllegalArgumentException if order is less than 0 or greater than 1
	 *        
	 */
	
	public void setComparator(int order) throws IllegalArgumentException
	{
		// checks if the order is less than zero or greater than 1
		if(order < 0 || order >1) {
			throw new IllegalArgumentException("Order must be 0 (x) or 1 (y)");
		}
		Point.setXorY(order == 0);
		pointComparator = new Comparator<Point>() {
			@Override
			public int compare(Point p1, Point p2) {
				return p1.compareTo(p2);
			}
			
		};
	}
	/**
	 * compares two points using the points set by setcomparator
	 * @param p1 first point
	 * @param p2 second point
	 * @return returns negative if p1 comes before p2 and zero if equal
	 * otherwise it is positive if p1 comes after p2
	 */
	protected int compare( Point p1, Point p2) {
		return p1.compareTo(p2);
	}
	

	/**
	 * Use the created pointComparator to conduct sorting.  
	 * 
	 * Should be protected. Made public for testing. 
	 */
	public abstract void sort(); 
	
	
	/**
	 * Obtain the point in the array points[] that has median index 
	 * 
	 * @return	median point 
	 */
	public Point getMedian()
	{
		return points[points.length/2]; 
	}
	
	
	/**
	 * Copys the array points[] onto the array pts[]. 
	 * 
	 * @param pts
	 */
	public void getPoints(Point[] pts)
	{
		for(int i = 0; i < points.length; i++) {
			pts[i] =   new Point(points[i]);
		}
	}
	

	/**
	 * Swaps the two elements indexed at i and j respectively in the array points[]. 
	 * 
	 * @param i
	 * @param j
	 */
	protected void swap(int i, int j)
	{
		// used to swap i and  j vaules 
		Point temp = points[i];
		points[i] = points[j];
		points[j] = temp;
	}	
}
