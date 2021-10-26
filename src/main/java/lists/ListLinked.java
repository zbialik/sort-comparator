package lists;

public class ListLinked implements List {

	public ListNode head;
	public int size;

	public ListLinked() {
		this.head = null;
		this.size = 0;
	}

	public void clear() throws Exception {
		while (!this.isEmpty()) {
			this.remove(0);
		}
	}

	/**
	 * Returns the size of the stack
	 * 
	 * @return size
	 */
	public int size() {
		return this.size;
	}

	/**
	 * Returns true if stack size is 0 and false is greater than 0 throws exception
	 * 
	 * @return empty (bool)
	 * @throws Exception when stack size is negative
	 */
	public boolean isEmpty() throws Exception {
		if (this.size == 0) {
			return true;
		} else if (this.size > 0) {
			return false;
		} else {
			throw new Exception("list size is negative"); // throw exception
		}
	}

	/**
	 * Creates node with provided data and appends node to top of stack
	 * 
	 * @param data
	 * @throws Exception 
	 */
	public void insert(String data, int index) throws Exception {
		
		if (index < 0 || index > this.size) { // validate
			String msg = "index out of bounds: " + index;
			throw new Exception(msg);
		} else {
			ListNode newNode = new ListNode(); // allocate space
			newNode.data = data; //initialize new node with given data
			this.size++; // update size
			if (index == 0) { // insert at head of list
				newNode.next = this.head ; // connect new node with old head
				this.head = newNode; // update list header
			} else { // generic insertion
				ListNode after = getListNode(index-1); //Get ref to prev node
				newNode.next = after.next; // point new node's next to next node
				after.next = newNode; // point prev node's next to new node
			}
		}

	}

	/**
	 * Removes the provided node from the list and returns its data
	 * 
	 * @return nodeData
	 * @throws Exception 
	 */
	public String remove(int index) throws Exception {
		ListNode temp;
		if (index < 0 || index > this.size) { // validate
			String msg = "index out of bounds: " + index;
			throw new Exception(msg);
		} else {
			if (index == 0) { // delete at head of list
				temp = this.head; // grab node for deletion
				this.head = temp.next; // update list header
			} else { // generic deletion
				ListNode after = getListNode(index - 1); // Ref to prev node
				temp = after.next; // Grab node for deletion
				after.next = temp.next; // Connect head & tail
			}
			this.size--; // Update size
			temp.next = null; // disconnect node from list
			return temp.data; // Return deleted value

		}
	}
	
	/**
	 * Returns a pointer to the provided node
	 * 
	 * @return node
	 * @throws Exception 
	 */
	private ListNode getListNode(int index) throws Exception {
		ListNode here;
		if (index < 0 || index > this.size) { // validate
			String msg = "index out of bounds: " + index;
			throw new Exception(msg);
		} else {
			here = this.head; //Set temp ptr to head of list
			for ( int i = 0; i < index; i++) {
				here = here.next;
			}
		}
		
		return here;
	}

	/**
	 * Converts stack to string for printing and troubleshooting support
	 * 
	 * @return stackString
	 */
	public String toString() {
		String stackString = "";

		for (int i = 0; i < this.size; i++) {
			try {
				stackString += (String) this.getListNode(i).data;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return stackString;
	}
}
