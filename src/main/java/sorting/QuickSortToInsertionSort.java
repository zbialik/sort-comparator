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
	
	/**
	 * Method executes insertion sort on range of data between low and high index (inclusive)
	 * 
	 * @param lowIndex
	 * @param highIndex
	 */
	public void insertionSort(int lowIndex, int highIndex) {
		int i = lowIndex;
		int j = lowIndex;

		for (i = lowIndex + 1; i <= highIndex; i++) {
			j = i;
			
			if (j > 0 && this.data[j] >= this.data[j - 1]) {
				this.comparisons++; // increment comparisons
			} else {
				do {
					this.comparisons++; // increment comparisons
					this.exchanges++; // increment exchanges

					// swap this.data[j] and this.data[j - 1]
					this.swap(j, j-1);
					
					j--;
				} while (j > 0 && this.data[j] < this.data[j - 1]);
			}
		}
	}

}
