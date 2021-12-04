package stacks;

public interface Stack {
	
	public int size();
	public boolean isEmpty() throws Exception;
	public Object peak();
	public void push(Object obj);
	public Object pop() throws Exception;
	
}