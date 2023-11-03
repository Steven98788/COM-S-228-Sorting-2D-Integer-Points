package edu.iastate.cs228.hw2;

import java.io.FileNotFoundException;
import java.lang.NumberFormatException;
import java.lang.IllegalArgumentException;
import java.util.InputMismatchException;

/**
 *  
 * @author Steven Bui
 *
 */

/**
 * 
 * This class implements the mergesort algorithm.
 *
 */

public class MergeSorter extends AbstractSorter {
	// Other private instance variables if needed

	/**
	 * Constructor takes an array of points. It invokes the superclass constructor,
	 * and also set the instance variables algorithm in the superclass.
	 * 
	 * @param pts input array of integers
	 */
	public MergeSorter(Point[] pts) {
		// TODO
		super(pts);

		// Set the instance variable algorithm of the superclass
		super.algorithm = "mergesort";

	}

	/**
	 * Perform mergesort on the array points[] of the parent class AbstractSorter.
	 * 
	 */
	@Override
	public void sort() {
		// TODO
		mergeSortRec(this.points);

	}

	/**
	 * This is a recursive method that carries out mergesort on an array pts[] of
	 * points. One way is to make copies of the two halves of pts[], recursively
	 * call mergeSort on them, and merge the two sorted subarrays into pts[].
	 * 
	 * @param pts point array
	 */

	private void mergeSortRec(Point[] points) {
	    int length = points.length;
	    int middle = length / 2;

	    if (length <= 1) {
	        return;
	    }

	    Point[] leftArray = new Point[middle];
	    Point[] rightArray = new Point[length - middle];

	    for (int i = 0; i < middle; i++) {
	        leftArray[i] = points[i];
	    }

	    int c = 0;
	    for (int i = middle; i < length; i++) {
	        rightArray[c] = points[i];
	        c++;
	    }

	    mergeSortRec(leftArray);
	    mergeSortRec(rightArray);

	    Point[] mergedArray = merge(leftArray, rightArray);

	    for (int i = 0; i < mergedArray.length; i++) {
	        points[i] = mergedArray[i];
	    }
	}

	// Other private methods if needed ...

	private Point[] merge(Point[] leftPart, Point[] rightPart) {
	    int leftLength = leftPart.length;
	    int rightLength = rightPart.length;

	    Point[] merged = new Point[leftLength + rightLength];

	    int i = 0;
	    int j = 0;
	    int k = 0;
	    while (i < leftLength && j < rightLength) {
	        if (pointComparator.compare(leftPart[i], rightPart[j]) <= 0) {
	            merged[k++] = leftPart[i++];
	        } else {
	            merged[k++] = rightPart[j++];
	        }
	    }

	    while (i < leftLength) {
	        merged[k++] = leftPart[i++];
	    }

	    while (j < rightLength) {
	        merged[k++] = rightPart[j++];
	    }

	    return merged;
	}


	// Other private methods if needed ...

}
