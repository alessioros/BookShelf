package it.polimi.bookshelf.objects;

public class Shelf {

	public String shelf_id;
	public String shelf_name;
	public String user_id;

	public Shelf() {

	}

	public Shelf(String shelf_id, String shelf_name, String user_id) {
		this.shelf_id = shelf_id;
		this.shelf_name = shelf_name;
		this.user_id = user_id;
	}
}
