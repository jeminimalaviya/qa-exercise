// POJO class to represent category
package common;

public class Category {
	
	private String id;
	private String color;
	private String value;
	
	// constructor
	public Category() {
		// default constructor
	}
	
	public Category(String id, String color, String value) {
		this.id = id;
		this.color = color;
		this.value = value;
	}


	// getter and setters
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		String result = "";
		result = "CategoryID : " + id +
						"Name : " + value +
						"Color : " + color + "\n";
		return result;
	}
	

}
