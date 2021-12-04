package stacks;

public class StackLinked implements Stack {
	
	private StackNode top;
	private int size;
	
	
	public StackLinked() {
		this.top = null;
		this.size = 0;
	}
	
	public void clear() throws Exception {
		while (!this.isEmpty()) {
			this.pop();
		}
	}

	/**
	* Returns the size of the stack
	* 
	* @return	size
	*/
	public int size() {
		return this.size;
	}

	/**
	* Returns true if stack size is 0 and false is greater than 0
	* throws exception
	* 
	* @return	empty (bool)
	* @throws 	Exception when stack size is negative
	*/
	public boolean isEmpty() throws Exception {
		if (this.size == 0) {
			return true;
		} else if (this.size > 0) {
			return false;
		} else {
			System.err.println("stack size is negative");
            throw new Exception("stack size is negative"); // throw exception
		}
	}
	
	/**
	* Returns the data stored in the stack's top node
	* 
	* @return	topData
	*/
	public Object peak() {
		Object topData = this.top.data;
		return topData;
	}
	
	/**
	* Creates node with provided data and appends node to top of stack
	* 
	* @param	data
	*/
	public void push(Object data) {
		StackNode temp = new StackNode();
		temp.data = data;
		temp.next = this.top;
		this.size++; // increment size by 1
		this.top = temp;
		
	}
	
	/**
	* Removes the top node from the stack and returns its data
	* 
	* @return	topData
	*/
	public Object pop() throws Exception {
		if (this.isEmpty()) {
			System.out.println("stack is empty - returning null as popped value");
			return null;
			
		} else {
			Object topData;
			StackNode temp = this.top; // create reference to current top node
			
			topData = this.top.data; // extract top node data
			this.top = this.top.next; // repoint stack top to next node
			
			temp.next = null; // repoint old top node to null for garbage collection
			temp.data = null; // also wipe out its data
			
			this.size--; // decrement size by 1
			return topData; // return data from old top node
			
		}
	}
	
	/**
	 * Reverses the order of the stack
	 * 
	 */
	public void reverse() throws Exception {
		
		StackLinked reverseStack = new StackLinked();
		
		// reverse stack
		while (!this.isEmpty()) {
			reverseStack.push(this.pop());
		}
		
		this.top = reverseStack.top;
		this.size = reverseStack.size();
		
	}
	
	/**
	 * Converts stack to string for printing and troubleshooting support
	 * 
	 * @return	stackString
	 */
	public String toString() {
		String stackString = "[ ";
		String element;
		
		StackNode tempNode = this.top;
		StackLinked tempStack = new StackLinked();
		
		// reverse stack
		while (tempNode != null) {
			tempStack.push(tempNode.data);
			tempNode = tempNode.next;
		}
		
		tempNode = tempStack.top;
		while (tempNode != null) {
			element = String.valueOf(tempNode.data);
			
			stackString = stackString + element + " ";
			
			tempNode = tempNode.next; // repoint to next
		}
		
		stackString = stackString + "]"; // close bracket
		
		return stackString;
	}

}