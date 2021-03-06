package sorting;

public class QuickSort extends Sort {

	public QuickSort(int[] data) {
		super(data);
	}

	/**
	 * Method that returns the element at lowIndex as the pivot
	 * @param lowIndex
	 * @param highIndex
	 * @return pivot
	 * @throws Exception 
	 */
	public int pivot(int lowIndex, int highIndex) throws Exception {
		return this.data[lowIndex];
	}
	
	/**
	 * Method that executes the quicksort recursive algorithm
	 * @param lowIndex
	 * @param highIndex
	 * @throws Exception 
	 */
	public void quickSort(int lowIndex, int highIndex) throws Exception {
		// Base case: If the partition size is 1 or zero elements, then the partition is already sorted
		if (lowIndex >= highIndex) {
			return;
		}

		// Partition the data within the array. Value lowEndIndex returned from partitioning 
		// is the index of the low partition's last element.
		int lowEndIndex = this.partition(lowIndex, highIndex);

		// Recursively sort low partition (lowIndex to lowEndIndex)
		quickSort(lowIndex, lowEndIndex);
		
		// Recursively sort high partition (lowEndIndex + 1 to highIndex)
		quickSort(lowEndIndex + 1, highIndex);
	}
	
	/**
	 * Method that partitions the data based on provided low and high indexes
	 * @param lowIndex
	 * @param highIndex
	 * @return nextPartitionIndex
	 * @throws Exception 
	 */
	public int partition(int lowIndex, int highIndex) throws Exception {

		int pivot = this.pivot(lowIndex, highIndex); // grab pivot
		
		boolean done = false;
		while (!done) {
			// increment lowIndex while numbers[lowIndex] < pivot
			while (this.data[lowIndex] < pivot) {
				lowIndex++;
				this.comparisons++; // count comparisons for analysis
			}

			// decrement highIndex while pivot < numbers[highIndex]
			while (pivot < this.data[highIndex]) {
				highIndex--;
				this.comparisons++; // count comparisons for analysis
			}

			// if zero or one elements remain, then all numbers are partitioned.
			if (lowIndex >= highIndex) {
				done = true;
			} else {
				
				// swap this.data[lowIndex] and this.data[highIndex]
				this.swap(lowIndex, highIndex);

				// update lowIndex and highIndex
				lowIndex++;
				highIndex--;
			}
		}

		return highIndex;
	}

}
