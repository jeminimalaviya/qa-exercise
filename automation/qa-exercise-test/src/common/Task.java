// POJO class to represent task
package common;

import com.google.gson.annotations.SerializedName;

public class Task {
	
	private String id;
	@SerializedName("task name") private String name;
	private String category = "None";
	@SerializedName("due date") private String dueDate = "None";
	@SerializedName("status") private String completed = "p";
	
	// constructor
	public Task() {
		// default constructor
		this.category = "None";
		this.dueDate = "None";
		this.completed = "p";
	}
	
	public Task(String id, String name, String category, String dueDate, String completed) {
		this.id = id;
		this.name = name;
		if(category != null && !category.isEmpty()) {
			this.category = category;
		}
		else {
			this.category = "None";
		}
		
		if(dueDate != null && !dueDate.isEmpty()) {
			this.dueDate = dueDate.substring(0, 10);
		}
		else {
			this.dueDate = "None";
		}
		
		if(completed != null && !completed.isEmpty()) {
			this.completed = completed;
		}
		else {
			this.completed = "p";
		}
		
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
		if(category.isEmpty()) category = "0";
		return category;
	}

	public void setCategory(String category) {
		if(category != null && !category.isEmpty()) {
			this.category = category;
		}
		else {
			this.category = "None";
		}
	}

	public String getDueDate() {
		if(dueDate.isEmpty()) dueDate = "None";
		return dueDate;
	}

	public void setDueDate(String dueDate) {
		if(dueDate != null && !dueDate.isEmpty()) {
			this.dueDate = dueDate.substring(0, 10);
		}
		else {
			this.dueDate = "None";
		}
	}
	
	public String getCompleted() {
		if(completed.isEmpty()) completed="p";
		return completed;
	}

	public void setCompleted(String completed) {
		if(completed != null && !completed.isEmpty()) {
			this.completed = completed;
		}
		else {
			this.completed = "p";
		}
	}

	@Override
	public String toString() {
		String result = "";
		result = "TaskID : " + id +
					", Name : " + name +
					", Category : " + category +
					", Due Date : " + dueDate + 
					", Status : " + completed + "\n";
		return result;
	}

}
