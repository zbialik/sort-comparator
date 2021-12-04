package sorting;

public class QuickSortToInsertionSort extends QuickSort {

	public int partitionThreshold;
	
	public QuickSortToInsertionSort(int[] data, int threshold) {
		super(data);
		this.partitionThreshold = threshold;
	}
	
	/**
	 * executes quick sort until partition is size 100 or less and 
	 * finishes the rest with insertion sort
	 * @param lowIndex
	 * @param highIndex
	 */
	public void quickSort(int lowIndex, int highIndex) {
		// Base case: If the partition size is 1 or zero elements, then the partition is already sorted
		if (lowIndex >= highIndex) {
			return;
		}
		
		int partitionSize = highIndex - lowIndex;

		
		if (partitionSize < this.partitionThreshold) {
			
			LOGGER.trace("low partition has size {}, which is less than {} - finishing with insertion sort.", 
					partitionSize, this.partitionThreshold);
			this.insertionSort(lowIndex, highIndex); // execute insertion sort
			
		} else {
			// Partition the data within the array. Value lowEndIndex returned from partitioning 
			// is the index of the low partition's last element.
			int lowEndIndex = this.partition(lowIndex, highIndex);
			
			// Recursively sort low partition (lowIndex to lowEndIndex)
			quickSort(lowIndex, lowEndIndex);
			
			// Recursively sort high partition (lowEndIndex + 1 to highIndex)
			quickSort(lowEndIndex + 1, highIndex);
		}
	}

}
