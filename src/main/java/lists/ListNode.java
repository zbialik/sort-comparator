package lists;

public class ListNode {
	
	public Object data;
	public ListNode next;
	public ListNode prev;
	
	public String toString() {
		String output = "";
		
		output += data.toString();
		
		return output;
	}
	
}