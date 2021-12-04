package sorting;

public class Sort {

	int[] data;
	int comparisons;
	int exchanges;

	public Sort(int[] data) {
		this.data = data;
		this.comparisons = 0;
		this.exchanges = 0;
	}

	/**
	 * executes insertion sort on range of data between low and high index (inclusive)
	 * 
	 * @param lowIndex
	 * @param highIndex
	 */
	public void insertionSort(int lowIndex, int highIndex) {
		int i = lowIndex;
		int j = lowIndex;
		int temp;  // temp variable for swap

		for (i = lowIndex + 1; i <= highIndex; i++) {
			j = i;
			
			if (j > 0 && this.data[j] >= this.data[j - 1]) {
				this.comparisons++; // increment comparisons
			} else {
				do {
					this.comparisons++; // increment comparisons
					this.exchanges++; // increment exchanges

					// swap this.data[j] and this.data[j - 1]
					temp = this.data[j];
					this.data[j] = this.data[j - 1];
					this.data[j - 1] = temp;
					j--;
				} while (j > 0 && this.data[j] < this.data[j - 1]);
			}
		}
	}

}
