package lists;

public class ListLinked implements List {

	public ListNode head;
	public ListNode tail;
	public int size;

	public ListLinked() {
		this.head = null;
		this.tail = null;
		this.size = 0;
	}

	public void clear() throws Exception {
		while (!this.isEmpty()) {
			this.remove(0);
		}
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
	 * Creates node with provided data and appends to top of the list
	 * 
	 * @param data
	 * @throws Exception 
	 */
	public void append(Object data) throws Exception {
		insert(data, this.size);
	}

	/**
	 * Creates node with provided data and inserts to list where defined
	 * 
	 * @param data
	 * @throws Exception 
	 */
	public void insert(Object data, int index) throws Exception {
		
		if (index < 0 || index > this.size) { // validate
			String msg = "index out of bounds: " + index;
			throw new Exception(msg);
		} else {
			ListNode newNode = new ListNode(); // allocate space
			newNode.data = data; // initialize new node with given data
			
			if (index == 0) { // insert as head
				
				newNode.next = this.head ; // connect new node to old head
				newNode.prev= null; // set new node's prev to null
				
				if (this.isEmpty()) { // insert as tail if empty
					this.tail = newNode;
				} else { // update curr head.prev to point to new node
					this.head.prev = newNode;
				}
				
				this.head = newNode; // repoint head
				
			} else if (index == this.size) { // insert at tail of list
				newNode.next = null ; // set new node's next
				newNode.prev = this.tail ; // set new node's prev to curr tail
				this.tail.next = newNode; // connect old tail to new node
				
				this.tail = newNode; // repoint tail
				
			} else { // generic insertion
				ListNode prev = getListNode(index-1); //Get ref to prev node
				ListNode after = getListNode(index); //Get ref to after node
				newNode.next = after; // point new node's next to after nodee
				newNode.prev = prev; // point new node's prev to prev node
				prev.next = newNode; // point prev node's next to new node
				after.prev = newNode; // point after node's prev to new node
			}
			
			this.size++; // update size
		}
	}

	/**
	 * Removes the provided node from the list and returns its data
	 * 
	 * @return nodeData
	 * @throws Exception 
	 */
	public Object remove(int index) throws Exception {
		ListNode temp;
		
		if (index < 0 || index > this.size || this.size == 0) { // validate
			String msg = "index out of bounds: " + index;
			throw new Exception(msg);
		} else {
			if (index == 0) { // delete at head of list
				temp = this.head; // grab node for deletion
				this.head = temp.next; // update list header
				
				if (this.size == 1) { // also repoint tail to null
					this.tail = null;
				} else { // update head prev pointer
					this.head.prev = null; 
				}
				
			} else {
				ListNode prev = getListNode(index - 1); // Ref to prev node
				temp = prev.next; // Grab node for deletion
				prev.next = temp.next; // Connect head & tail
				
				if (index == this.size-1) { // deleting tail node
					
					this.tail = prev; // update tail
					
				} else { // generic deletion
					ListNode after = temp.next; // get after node
					after.prev = prev; // update after node to point to prev
				}
			}
			this.size--; // Update size
			temp.next = null; // disconnect node from list
			temp.prev = null; // disconnect node from list
			return temp.data; // Return deleted value

		}
	}
	
	/**
	 * Returns a pointer to the provided node
	 * 
	 * @return node
	 * @throws Exception 
	 */
	public ListNode getListNode(int index) throws Exception {
		ListNode here;
		if (index < 0 || index >= this.size || this.size == 0) { // validate
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
	 * Returns the index for the provided node
	 * 
	 * @return index
	 * @throws Exception 
	 */
	public int getNodeIndex(ListNode node) throws Exception {
		
		int index = 0;
		
		ListNode curr = this.head;
		
		while (curr != null && !curr.equals(node)) {
			curr = curr.next;
			index++;
		}
		
		if (curr == null) {
			String msg = "provided node is NOT in this list";
			throw new Exception(msg);
		} else {
			return index;
		}
	}
	
	/**
	 * Returns a clone of the list
	 * 
	 * @return index
	 * @throws Exception
	 */
	public ListLinked clone() {

		ListLinked copy = new ListLinked();

		ListNode curr = this.head;
		while (curr != null) {
			try {
				copy.append(curr.data);
			} catch (Exception e) {
				e.printStackTrace();
			}
			curr = curr.next;
		}

		return copy;
	}
	
	public String toString() {
		String output = "";
		
		ListNode curr = this.head;
		while (curr != null) {
			
			output += " " + curr.data.toString();
			curr = curr.next;
		}
		
		return output;
	}
	
	public boolean equals(ListLinked list) {
		boolean isEqual = true;
		
		if (list.size == this.size) {
			ListNode curr1 = this.head;
			ListNode curr2 = list.head;
			
			while (curr1 != null && curr2 != null) {
				if (!curr1.data.equals(curr2.data)) {
					isEqual = false;
				}
				
				curr1 = curr1.next;
				curr2 = curr2.next;
			}
			
		} else {
			isEqual = false;
		}
		
		
		return isEqual;
	}
}
