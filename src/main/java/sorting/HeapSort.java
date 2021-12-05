package sorting;

public class HeapSort extends Sort {

	public HeapSort(int[] data) {
		super(data);
	}

	/**
	 * Method heapifies data and then sorts it by percolating down max heap
	 * @param numbers
	 * @param numbersSize
	 */
	public void heapSort() {
		int numbersSize = this.data.length;
		
		// heapify data
		for (int i = numbersSize / 2 - 1; i >= 0; i--) {
			maxHeapPercolateDown(i, numbersSize);
		}

		// percolate down heap
		for (int i = numbersSize - 1; i > 0; i--) {
			this.swap(0, i);
			maxHeapPercolateDown(0, i);
		}
	}

	/**
	 * Method percolates down max heap
	 * 
	 * Reference: zyBook ISBN: 979-8-203-90262-7 (Section 6.12: Heaps using arrays)
	 * 
	 * @param nodeIndex
	 * @param this.data
	 * @param arraySize
	 */
	private void maxHeapPercolateDown(int nodeIndex, int arraySize) {
		int childIndex = 2 * nodeIndex + 1;
		int value = this.data[nodeIndex];

		while (childIndex < arraySize) {
			// find the max among the node and all the node's children
			int maxValue = value;
			int maxIndex = -1;
			for (int i = 0; i < 2 && i + childIndex < arraySize; i++) {
				if (this.data[i + childIndex] > maxValue) {
					maxValue = this.data[i + childIndex];
					maxIndex = i + childIndex;
				}
			}

			if (maxValue == value) {
				return;
			} else {
				this.swap(nodeIndex, maxIndex);
				nodeIndex = maxIndex;
				childIndex = 2 * nodeIndex + 1;
			}
		}
	}
}
