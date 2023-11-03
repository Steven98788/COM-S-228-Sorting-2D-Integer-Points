package edu.iastate.cs228.hw2;
/**
import java.io.File;

/**
 * 
 * @author 
 *
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;


/**
 * 
 * @author Steven Bui
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
		try {
			sortingAlgorithm = algo;
			
			points = new Point[pts.length];
			
			for(int i=0; i< pts.length; ++i) {
				points[i] = pts[i];
			}
			
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("the list is null or 0");
		}
			
		
		
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
		// TODO
		File fileInput = new File(inputFileName);
		
		Scanner scnr = new Scanner(fileInput);
		
		ArrayList<Integer> temporaryList = new ArrayList<Integer>();
		
		//adds integers to temp list
		while ((scnr).hasNextInt()) {
			temporaryList.add(scnr.nextInt());
	
		}
		if((temporaryList.size() % 2) <0 && (temporaryList.size() % 2) >0) {
			scnr.close();
			throw new InputMismatchException("The list of integers is odd and must be even");
			
		}
		
		this.points = new Point[temporaryList.size() / 2];
		
		int x = 0;
		for (int i = 0; i < temporaryList.size(); i+=2) 
		{
			Point p = new Point(temporaryList.get(i), temporaryList.get(i+1));
			this.points[x] = p;
			x++;
		}
		
		this.sortingAlgorithm = algo;
		
		scnr.close();
		
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
		// TODO  
		AbstractSorter aSorter; 
		
		// create an object to be referenced by aSorter according to sortingAlgorithm. for each of the two 
		// rounds of sorting, have aSorter do the following: 
		// 
		//     a) call setComparator() with an argument 0 or 1. 
		//
		//     b) call sort(). 		
		// 
		//     c) use a new Point object to store the coordinates of the medianCoordinatePoint
		//
		//     d) set the medianCoordinatePoint reference to the object with the correct coordinates.
		//
		//     e) sum up the times spent on the two sorting rounds and set the instance variable scanTime.
		if (sortingAlgorithm == Algorithm.SelectionSort) 
		{
			aSorter = new SelectionSorter(this.points);
		} 
		else if (sortingAlgorithm == Algorithm.InsertionSort) 
		{
			aSorter = new InsertionSorter(this.points);
		} 
		else if (sortingAlgorithm == Algorithm.MergeSort) 
		{
			aSorter = new MergeSorter(this.points);
		} 
		else 
		{
			aSorter = new QuickSorter(this.points);
		}
		
		int x = 0;
		int y = 0;
		
		
		long startTime = System.nanoTime();
					
		for (int i = 0; i < 2; i++) {
			aSorter.setComparator(i);
			
			if (i == 0 || i == 1) {
				aSorter.sort();
			}
			
			if (i == 0) {
				x = aSorter.getMedian().getX();
			}
			
			if (i == 1) {
				y = aSorter.getMedian().getY();
				
				medianCoordinatePoint = new Point(x, y);
			}
		}
		
		long endTime = System.nanoTime();
		this.scanTime = endTime - startTime;
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
		// TODO 
		String returns = String.format("%-17s %-10d %-10d", this.sortingAlgorithm, this.points.length, this.scanTime);
		return(returns);
	}
	
	
	/**
	 * Write MCP after a call to scan(),  in the format "MCP: (x, y)"   The x and y coordinates of the point are displayed on the same line with exactly one blank space 
	 * in between. 
	 */
	@Override
	public String toString()
	{
		// TODO
		String returns = "";
		for (int i = 0; i < points.length; i++) {
			returns = returns + points[i].toString() + "\n";
		}
		
		return(returns);
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
		// TODO 
		try {
			String outputFileName;
			
			if (sortingAlgorithm.equals(Algorithm.SelectionSort)) 
			{
				outputFileName = "select.txt";
			} 
			else if (sortingAlgorithm.equals(Algorithm.InsertionSort)) 
			{
				outputFileName = "insert.txt";
			} 
			else if (sortingAlgorithm.equals(Algorithm.MergeSort)) 
			{
				outputFileName = "merge.txt";
			} 
			else 
			{
				outputFileName = "quick.txt";
			}
			
			System.out.println(outputFileName);
			PrintWriter printer = new PrintWriter(outputFileName);
			
			printer.println(this.toString());
			printer.close();
		} 
		catch (FileNotFoundException e) 
		{
			throw new FileNotFoundException();
		}
	}	


	

		
}
