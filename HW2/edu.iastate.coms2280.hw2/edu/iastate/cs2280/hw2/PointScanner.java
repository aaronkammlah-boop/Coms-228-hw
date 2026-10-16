package edu.iastate.cs2280.hw2;

/**
 * 
 * @author [redacted]
 *
 */

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.File;

/**
 * 
 * This class sorts all the points in an array of 2D points to determine a reference point whose x and y 
 * coordinates are respectively the medians of the x and y coordinates of the original points. 
 * 
 * It records the employed sorting algorithm as well as the sorting time for comparison. 
 *
 */
public class PointScanner  
{
	private Point[] points; 
	
	private Point medianCoordinatePoint;  // point whose x and y coordinates are respectively the medians of 
	                                      // the x coordinates and y coordinates of those points in the array points[].
	private Algorithm sortingAlgorithm;    
	
		
	protected long scanTime; 	       // execution time in nanoseconds. 
	
	/**
	 * This constructor accepts an array of points and one of the four sorting algorithms as input. Copy 
	 * the points into the array points[].
	 * 
	 * @param  pts  input array of points 
	 * @throws IllegalArgumentException if pts == null or pts.length == 0.
	 */
	public PointScanner(Point[] pts, Algorithm algo) throws IllegalArgumentException
	{
		// checks if the input was valid
		if(pts == null || pts.length == 0) {
			throw new IllegalArgumentException("Array is null or empty");
		}
		//copies points to array points
		points = new Point[pts.length];
		for(int i = 0; i<pts.length; i++) {
			points[i] = new Point(pts[i]);
		}
		sortingAlgorithm = algo;
	}

	
	/**
	 * This constructor reads points from a file. 
	 * 
	 * @param  inputFileName
	 * @throws FileNotFoundException 
	 * @throws InputMismatchException   if the input file contains an odd number of integers
	 */
	protected PointScanner(String inputFileName, Algorithm algo) throws FileNotFoundException, InputMismatchException
	{
		sortingAlgorithm = algo;
		Scanner scan = new Scanner(new File(inputFileName));
		ArrayList<Integer> vals = new ArrayList();
		while(scan.hasNextInt()) {
			vals.add(scan.nextInt());
		}
		scan.close();
		if(vals.size()%2 != 0) {
			throw new InputMismatchException("File has an odd number of ints");
		}
		
		points = new Point[vals.size()/2];
		for(int i = 0; i< points.length; i++) {
			points[i] = new Point(vals.get(2*i), vals.get(2* i+1));
		}
	}

	
	/**
	 * Carry out two rounds of sorting using the algorithm designated by sortingAlgorithm as follows:  
	 *    
	 *     a) Sort points[] by the x-coordinate to get the median x-coordinate. 
	 *     b) Sort points[] again by the y-coordinate to get the median y-coordinate.
	 *     c) Construct medianCoordinatePoint using the obtained median x- and y-coordinates.     
	 *  
	 * Based on the value of sortingAlgorithm, create an object of SelectionSorter, InsertionSorter, MergeSorter,
	 * or QuickSorter to carry out sorting.       
	 * @param algo
	 * @return
	 */
	public void scan()
	{
		
		AbstractSorter aSorter; 
		//selects the create sorting alg.
		if(sortingAlgorithm == Algorithm.SelectionSort) {
			aSorter = new SelectionSorter(points);
		}else if(sortingAlgorithm == Algorithm.InsertionSort) {
			aSorter = new InsertionSorter(points);
		}else if(sortingAlgorithm == Algorithm.MergeSort) {
			aSorter = new MergeSorter(points);
		} else if(sortingAlgorithm == Algorithm.QuickSort) {
			aSorter = new QuickSorter(points);
		}else {
			throw new IllegalStateException("Unknown algorithm.");
		}
		scanTime = 0;
		// first sorts x coordinates (gets median)
		aSorter.setComparator(0);
		long first = System.nanoTime();
		aSorter.sort();
		scanTime+= System.nanoTime() - first;
		int medianx = aSorter.getMedian().getX();
		
		// second sorts y coordianates (gets median)
		aSorter.setComparator(1);
		long second = System.nanoTime();
		aSorter.sort();
		scanTime+= System.nanoTime() - second;
		int mediany = aSorter.getMedian().getY();
		
		medianCoordinatePoint = new Point(medianx,mediany);
	
		
	}
	
	
	/**
	 * Outputs performance statistics in the format: 
	 * 
	 * <sorting algorithm> <size>  <time>
	 * 
	 * For instance, 
	 * 
	 * selection sort   1000	  9200867
	 * 
	 * Use the spacing in the sample run in Section 2 of the project description. 
	 */
	public String stats()
	{
		return String.format("%-15s %10d %15d", sortingAlgorithm.name(),points.length,scanTime);
		
	}
	
	
	/**
	 * Write MCP after a call to scan(),  in the format "MCP: (x, y)"   The x and y coordinates of the point are displayed on the same line with exactly one blank space 
	 * in between. 
	 */
	@Override
	public String toString()
	{
		return "MCP: " + medianCoordinatePoint.toString(); 
		
	}

	
	/**
	 *  
	 * This method, called after scanning, writes point data into a file by outputFileName. The format 
	 * of data in the file is the same as printed out from toString().  The file can help you verify 
	 * the full correctness of a sorting result and debug the underlying algorithm. 
	 * 
	 * @throws FileNotFoundException
	 */
	public void writeMCPToFile() throws FileNotFoundException
	{
		//names the output of the file
		String outputFileName = sortingAlgorithm.name().toLowerCase()+ "_result.txt";
		PrintWriter pw = new PrintWriter(new File(outputFileName));
		pw.println(toString());
		pw.close();
	}	

	

		
}
