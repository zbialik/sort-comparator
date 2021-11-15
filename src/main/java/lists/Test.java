package lists;

public class Test {

	public Test() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws Exception {

		
		ListLinked[] listArray = new ListLinked[7];
		
		
		for (int i=0; i < listArray.length; i++) {
			ListLinked list = new ListLinked();
			list.clear();
			list.append("first");
			list.append("second");
			list.append("third");
			listArray[i] = list;
			
		}
		
		ListLinked[] refListArray = new ListLinked[7];
		for (int i=0; i < listArray.length; i++) {
			refListArray[i] = listArray[i].clone();
		}

		refListArray[0].clear();

		System.out.println("refListArray:");
		for (int i = 0; i < refListArray.length; i++) {
			System.out.print("[ " + refListArray[i].toString() + " ]");
		}
		
		System.out.println("\nlistArray:");
		for (int i = 0; i < listArray.length; i++) {
			System.out.print("[ " + listArray[i].toString() + " ]");
		}
		

	}

}
