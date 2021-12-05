package sorting;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Sort {
	
	public static Logger LOGGER = LogManager.getLogger(Sort.class);

	int[] data;
	int comparisons;
	int exchanges;

	public Sort(int[] data) {
		this.data = data.clone(); // clone the data
		this.comparisons = 0;
		this.exchanges = 0;
	}
	
	/**
	 * Method swaps the values located in the provided indexes
	 * 
	 * @param i1
	 * @param i2
	 */
	public void swap(int i1, int i2) {
		int temp = this.data[i1];
		this.data[i1] = this.data[i2];
		this.data[i2] = temp;
	}

}
