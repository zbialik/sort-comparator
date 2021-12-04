package sorting;

public class QuickSortMedianPivot extends QuickSort {

	public QuickSortMedianPivot(int[] data) {
		super(data);
	}

	/**
	 * Method that returns the element at middle point of data as the pivot
	 * @param lowIndex
	 * @param highIndex
	 * @return pivot
	 */
	public int pivot(int lowIndex, int highIndex) {
		int midpoint = lowIndex + (highIndex - lowIndex) / 2; // get middle point
		return this.data[midpoint];
	}
}
