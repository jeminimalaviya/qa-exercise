package frontend;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class EditTaskActions extends TaskActions {

	// update task with new task name, category and due date
	// do not update parameter if passed empty
	public static void updateTask(int testId, WebDriver driver, String taskName, String newTaskName, String category, String dueDate) {
		System.out.println("Finding Task with name : " + taskName );
		// get task element based on name
		WebElement taskSelected = getTaskElementByName(driver, taskName);
		if(taskSelected != null) {
			System.out.println("Task Found!");
		}
		else {
			System.out.println("Task Not Found, Aboort!");
			return;
		}
		// click on task id link
		WebElement taskId = driver.findElement(By.xpath(".//a[@title='edit']"));
		taskId.click();
		
		// verify that edit page is launched
		String currentUrl = driver.getCurrentUrl();
		if(currentUrl.contains("edit.php")) {
			System.out.println("Edit page is launched!");
		}
		else {
			System.out.println("Edit page not launched, Abort!");
			return;
		}
		
		// updating task name field if new name is different from task name
		if(!taskName.equals(newTaskName)) {
			System.out.println("Entering new task name into text field.");
			//get text field web element
			WebElement textField = driver.findElement(By.xpath(".//input[@title='data']"));
			if(textField.isEnabled()) {
				textField.sendKeys(newTaskName); // enter new task name into text field
			}
		}
		
		// 
		if(category != null && category != "") { 
			System.out.println("Category : " + category);
			
			// create web element to select category
			Select categorySelector = new Select(driver.findElement(By.xpath("/html/body/div[4]/span/select[1]")));
			categorySelector.selectByVisibleText(category);
			}
	}
	
	// complete selected task
	public static void completeTask(int testId, WebDriver driver, String taskName) throws Exception {
		System.out.println("Finding Task with name : " + taskName );
		// get task element based on name
		WebElement taskSelected = getTaskElementByName(driver, taskName);
		if(taskSelected != null) {
			System.out.println("Task Found!");
		}
		else {
			System.out.println("Task Not Found, Aboort!");
			return;
		}
		System.out.println("Selecting check box of task.");
		WebElement checkBox = taskSelected.findElement(By.xpath(".//input[@type='checkbox']"));
		if(!checkBox.isSelected()) {
			checkBox.click();
		}
		if(taskSelected.findElements(By.xpath(".//strike")).isEmpty()) {
			System.out.println("Clicking Complete Button to mark it as completed.");
		}
		else {
			System.out.println("Clicking Complete Button to revert completed task to pending.");
		}
				
		WebElement completeButton = driver.findElement(By.xpath(".//input[@type='submit' and @value='Complete']"));
		completeButton.click();
		
		System.out.println("Taking snap shot.");
		TaskActions.takeSnapShot(driver, TestRunner.BASE_DIR+"test"+testId+"-completeTask-"+taskName+".png");
		
	}
	
	// remove selected task
	public static void removeTask(int testId, WebDriver driver, String taskName) throws Exception {
		System.out.println("Finding Task with name : " + taskName );
		// get task element based on name
		WebElement taskSelected = getTaskElementByName(driver, taskName);
		if(taskSelected != null) {
			System.out.println("Task Found!");
		}
		else {
			System.out.println("Task Not Found, Aboort!");
			return;
		}
		System.out.println("Selecting check box of task.");
		WebElement checkBox = taskSelected.findElement(By.xpath(".//input[@type='checkbox']"));
		if(!checkBox.isSelected()) {
			checkBox.click();
		}
		System.out.println("Clicking Remove Button.");		
		WebElement completeButton = driver.findElement(By.xpath(".//input[@type='submit' and @value='Remove']"));
		completeButton.click();
		
		System.out.println("Taking snap shot.");
		TaskActions.takeSnapShot(driver, TestRunner.BASE_DIR+"test"+testId+"-removeTask-"+taskName+".png");
	}
}
