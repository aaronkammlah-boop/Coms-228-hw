package edu.iastate.cs2280.hw2;

/**
 *  
 * @author [redacted]
 *
 */

/**
 * 
 * This class executes four sorting algorithms: selection sort, insertion sort, mergesort, and
 * quicksort, over randomly generated integers as well integers from a file input. It compares the 
 * execution times of these algorithms on the same input. 
 *
 */

import java.io.FileNotFoundException;
import java.util.Scanner; 
import java.util.Random; 


public class CompareSorters 
{
	/**
	 * Repeatedly take integer sequences either randomly generated or read from files. 
	 * Use them as coordinates to construct points.  Scan these points with respect to their 
	 * median coordinate point four times, each time using a different sorting algorithm.  
	 * 
	 * @param args
	 **/
	public static void main(String[] args) throws FileNotFoundException
	{		
		PointScanner[] scanners = new PointScanner[4];
		
		Scanner input = new Scanner(System.in);
		Random ran = new Random();
		
		System.out.println("Performances of Four Sorting Algorithms in Point Scanning");
		System.out.println("keys: 1(random intergers) 2(file input) 3(exit)");
		int trial = 1;
		while(true) {
			System.out.print("Trial "+ trial +": ");
			int key = input.nextInt();
			if(key==3) {
				break;
			}
			if(key ==1) {
				//makes random points and scans with the 4 sorting algs
				System.out.print("Enter number of random Points: ");
				int points = input.nextInt();
				Point[] pts = generateRandomPoints(points, ran);
				scanners[0]= new PointScanner(pts, Algorithm.SelectionSort);
				scanners[1]= new PointScanner(pts, Algorithm.InsertionSort);
				scanners[2]= new PointScanner(pts, Algorithm.MergeSort);
				scanners[3]= new PointScanner(pts, Algorithm.QuickSort);
				
			}else if(key ==2) {
				// scans file and uses 4 sorting algs
				System.out.println("Points from a file");
				System.out.print("File name: " );
				String filename = input.next();
				try {
				scanners[0]= new PointScanner(filename, Algorithm.SelectionSort);
				scanners[1]= new PointScanner(filename, Algorithm.InsertionSort);
				scanners[2]= new PointScanner(filename, Algorithm.MergeSort);
				scanners[3]= new PointScanner(filename, Algorithm.QuickSort);
				
			}catch (FileNotFoundException e){
				System.out.println("File not found: "+ filename);
				continue; 
			}
			}
				else {
				System.out.println("Invalid number Please enter 1, 2, or3.");
				continue;
				}
			
			for(PointScanner s : scanners) {
				s.scan();
			}
			System.out.println();
			System.out.printf("%-15s %10s %15s%n", "algorithm", "size", "time(ns)");
			System.out.println("------------------------------------------");
			for(PointScanner s : scanners) {
			System.out.println(s.stats());
			}
			System.out.println("------------------------------------------");
			System.out.println();
			trial++;
		}
		input.close();
		
	}
	
	
	/**
	 * This method generates a given number of random points.
	 * The coordinates of these points are pseudo-random numbers within the range 
	 * [-50,50] � [-50,50]. Please refer to Section 3 on how such points can be generated.
	 * 
	 * Ought to be private. Made public for testing. 
	 * 
	 * @param numPts  	number of points
	 * @param rand      Random object to allow seeding of the random number generator
	 * @throws IllegalArgumentException if numPts < 1
	 */
	public static Point[] generateRandomPoints(int numPts, Random rand) throws IllegalArgumentException
	{ 
		
		if(numPts< 1) {
			throw new IllegalArgumentException("Number of points must be 1 or more.");
		}
			Point[] pts = new Point[numPts];
			//makes coords in range [-50,50]
			for(int i = 0; i <numPts; i++) {
				int x = rand.nextInt(101)-50;
				int y = rand.nextInt(101)-50;
				pts[i] = new Point(x,y);
			
			
		}
		return pts; 
		
	}
	
}
