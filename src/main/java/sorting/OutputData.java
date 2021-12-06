package sorting;

public class OutputData {
	
	public String sortName;
	public int dataSize;
	public int comparisons;
	public int exchanges;
	
	public OutputData(String name, Sort sort) {
		this.sortName = name;
		this.dataSize = sort.data.length;
		this.comparisons = sort.comparisons;
		this.exchanges = sort.exchanges;	
	}
	
	public OutputData(String sort, int size, int c, int e) {
		this.sortName = sort;
		this.dataSize = size;
		this.comparisons = c;
		this.exchanges = e;	
	}
	
	public String toString() {
		return String.format("%30s %20s %20s %20s \r\n", this.sortName, this.dataSize, this.comparisons, this.exchanges);
	}

}
