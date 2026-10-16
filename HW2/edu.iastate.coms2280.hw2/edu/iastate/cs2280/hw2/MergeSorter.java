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
 * This class implements the mergesort algorithm.   
 *
 */

public class MergeSorter extends AbstractSorter
{
	
	
	/** 
	 * Constructor takes an array of points.  It invokes the superclass constructor, and also 
	 * set the instance variables algorithm in the superclass.
	 *  
	 * @param pts   input array of integers
	 */
	public MergeSorter(Point[] pts) 
	{
		super(pts);
		algorithm = "mergesort";
	}


	/**
	 * Perform mergesort on the array points[] of the parent class AbstractSorter. 
	 * 
	 */
	@Override 
	public void sort()
	{
		mergeSortRec(points);
		
		
	}

	
	/**
	 * This is a recursive method that carries out mergesort on an array pts[] of points. One 
	 * way is to make copies of the two halves of pts[], recursively call mergeSort on them, 
	 * and merge the two sorted subarrays into pts[].   
	 * 
	 * @param pts	point array 
	 */
	private void mergeSortRec(Point[] pts)
	{
		//gets length ,mid point and left and right arrays
		int len = pts.length;
		if(len <=1)return;
		int mid = len/2;
		Point[] left = new Point[mid];
		Point[] right = new Point[len -mid];
		//copy first half to left array
		for(int i = 0; i< mid; i++ ) {
			left[i]= pts[i];
		}
		// copy right half to right array
		for(int i = mid; i < len; i++) {
			right[i-mid]=pts[i];
		}
		//uses recursion to sort everything
		mergeSortRec(left);
		mergeSortRec(right);
		int i = 0; 
		int j = 0;
		int k = 0;
		// gets the smallest num from either half
		while(i < left.length && j< right.length) {
			if(compare(left[i],right[j])<=0) {
				pts[k++]= left[i++];
			}else {
				pts[k++]= right[j++];
			}
		} // copies leftover numbers
		while(i < left.length) {
			pts[k++] = left[i++];
		}
		while(j < right.length) {
			pts[k++] = right[j++];
		}
		
	}


}
