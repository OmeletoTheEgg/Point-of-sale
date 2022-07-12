

import javax.swing.Icon;

public class Item {
	protected String name = "";
	protected String price = "";
	protected int quantity = 1;
	protected Icon icon = null;
	protected String itemID = "";
	
	Item() {};
	Item(String name, String price, int quantity, Icon icon, String itemID) {
		this.name = name;
		this.price = price;
		this.quantity = quantity;
		this.icon = icon;
		this.itemID = itemID;
	}
}
