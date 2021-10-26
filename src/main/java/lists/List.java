package lists;

public interface List {
	
	public boolean isEmpty() throws Exception;
	public void insert(String obj, int index) throws Exception; // insert at index of list
	public String remove(int index) throws Exception; // remove node from list at given index
	
}