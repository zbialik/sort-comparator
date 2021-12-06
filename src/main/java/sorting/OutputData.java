package sorting;

public class OutputData {
	
	public String sortName;
	public String dataOrder;
	public int dataSize;
	public int comparisons;
	public int exchanges;
	
	public OutputData(String name, String order, Sort sort) {
		this.sortName = name;
		this.dataOrder = order;
		this.dataSize = sort.data.length;
		this.comparisons = sort.comparisons;
		this.exchanges = sort.exchanges;	
	}
	
	public OutputData(String name, String order, int size, int c, int e) {
		this.sortName = name;
		this.dataOrder = order;
		this.dataSize = size;
		this.comparisons = c;
		this.exchanges = e;	
	}
	
	public String toString() {
		return String.format("%30s %20s %20s %20s %20s \r\n", this.sortName, this.dataOrder, this.dataSize, this.comparisons, this.exchanges);
	}

}
