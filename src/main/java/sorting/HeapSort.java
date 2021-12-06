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
		recursiveHeapify(numbersSize / 2 - 1);

		// percolate down heap
		recursiveSortMaxHeap(numbersSize - 1);
	}

	/**
	 * Method percolates down max heap
	 * 
	 * Reference: https://www.geeksforgeeks.org/heap-sort/
	 * 
	 * @param nodeIndex
	 * @param arraySize
	 */
    private void heapify(int i, int n) {
        int largest = i; // Initialize largest as root
        int l = 2 * i + 1; // left = 2*i + 1
        int r = 2 * i + 2; // right = 2*i + 2
 
        // If left child is larger than root
    	this.comparisons++;
        if (l < n && this.data[l] > this.data[largest]) {
            largest = l;
        }
 
        // If right child is larger than largest so far
        this.comparisons++;
        if (r < n && this.data[r] > this.data[largest]) {
            largest = r;
        }
 
        // If largest is not root
        if (largest != i) {
        	this.swap(i, largest);
 
            // Recursively heapify the affected sub-tree
            heapify(largest, n);
        }
    }
	
	/**
	 * Method recursively calls itself to heapify the data
	 * 
	 * @param arraySize
	 */
	private void recursiveHeapify(int nodeIndex) {
		
		if (nodeIndex >= 0) {
			heapify(nodeIndex, this.data.length); // percolate down
			recursiveHeapify(nodeIndex - 1); // recursive call to do again
		}
	}
	
	/**
	 * Method recursively calls itself to percolate down heap array and continue 
	 * sorting the data
	 * 
	 * @param arraySize
	 */
	private void recursiveSortMaxHeap(int arraySize) {
		
		if (arraySize > 0) {
			this.swap(0, arraySize); // swap root with leaf
			heapify(0, arraySize); // percolate down
			recursiveSortMaxHeap(arraySize - 1); // recursive call to do again
			
		}
	}
}
