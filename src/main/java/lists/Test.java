package lists;

public class Test {

	public Test() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws Exception {

		
		ListLinked list = new ListLinked();

		list.append("first");
		list.append("second");
		list.append("third");
		
		ListLinked refList = list;
		ListNode thirdObject = list.getListNode(2);
		
		System.out.println("SIZE: " + list.size);

		for (int i = 0; i < list.size; i++) {
			System.out.print(list.getListNode(i).data + " ");
		}
		System.out.println();
		System.out.println("getNodeIndex(thirdObject): " + refList.getNodeIndex(thirdObject));

	}

}
