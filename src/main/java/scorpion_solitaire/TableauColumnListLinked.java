package scorpion_solitaire;

import lists.ListLinked;
import lists.ListNode;

public class TableauColumnListLinked extends ListLinked {

	public TableauColumnListLinked() {
		super();
	}
	
	/**
	 * Returns a clone of the list
	 * 
	 * @return index
	 * @throws Exception
	 */
	public TableauColumnListLinked clone() {

		TableauColumnListLinked copy = new TableauColumnListLinked();

		ListNode curr = this.head;
		while (curr != null) {
			try {
				copy.append(((Card) curr.data).clone());
			} catch (Exception e) {
				e.printStackTrace();
			}
			curr = curr.next;
		}

		return copy;
	}

}
