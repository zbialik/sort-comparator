package sorting;

public class QuickSortMedianPivot extends QuickSort {

	public QuickSortMedianPivot(int[] data) {
		super(data);
	}

	/**
	 * Method that returns the median-of-3 element as the pivot
	 * 
	 * Additionally swaps the the elements to make use of comparison
	 * 
	 * Source referenced from here: https://examples.javacodegeeks.com/quicksort-java-algorithm-code-example/
	 * 
	 * @param lowIndex
	 * @param highIndex
	 * @return pivot
	 * @throws Exception 
	 */
	public int pivot(int left, int right) throws Exception {
		
		int center = (left+right)/2;
        
        if(this.data[left] > this.data[center]) {
			this.comparisons++; // count comparisons for analysis
        	this.swap(left,center);
        }
         
        if(this.data[left] > this.data[right]) {
			this.comparisons++; // count comparisons for analysis
        	this.swap(left, right);
        }
         
        if(this.data[center] > this.data[right]) {
			this.comparisons++; // count comparisons for analysis
        	this.swap(center, right);
        }
         
        this.swap(center, right);
        
        return this.data[right];
	}
}
