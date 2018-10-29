// POJO class to represent task
package common;

public class Task {
	
	private String id;
	private String name;
	private String category;
	private String dueDate;
	
	// constructor
	public Task() {
		// default constructor
	}
	
	public Task(String id, String name, String category, String dueDate) {
		this.id = id;
		this.name = name;
		this.category = category;
		this.dueDate = dueDate;
	}

	// getters and setters
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getDueDate() {
		return dueDate;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}

}
