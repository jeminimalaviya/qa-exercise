// launch dot dash app in firefox

package frontend;

import java.util.List;
import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class TestRunner {

	// base url of application
	public static String BASE_URL = "http://localhost/qa-exercise/";
	

	// base directory of project evidence folder
	public static final String BASE_DIR = "C:/xampp/htdocs/qa-exercise/automation/qa-exercise-test/evidence-dump/";

	public static WebDriver driver;


	public static void main(String args[]) throws Exception {

		// create web driver for firefox
		WebDriver driver = new FirefoxDriver();
		

		System.out.println("Testing " + BASE_URL + "application\n");
		// open index page of application
		System.out.println("Launching " + BASE_URL + "index.php page\n");
		driver.get(BASE_URL+"index.php");

		// create task without category and verify
		System.out.println("Test 1 : Create task with Name=DemoTask, Category=None, DueDate=None\nTest Run -");
		TaskActions.createTask(1, driver, "DemoTask", "", "");
		System.out.println("Test 1 completed.\n");

		// create task with category=College and verify
		System.out.println("Test 2 : Create task with Name=DemoTask2, Category=College, DueDate=None\nTest Run -");
		TaskActions.createTask(2, driver, "DemoTask2", "College", "");
		System.out.println("Test 2 completed.\n");

		// create task with due date and verify
		System.out.println("Test 3 : Create task with Name=DemoTask3, Category=Personal, DueDate=15/12/2018\nTest Run -");
		TaskActions.createTask(3, driver, "DemoTask3", "Personal", "15/12/2018");
		System.out.println("Test 3 completed.\n");

		// check if the task - "Finish reading research papers" is in list
		System.out.println("Test 4 : Find task with Name=\"Finish reading research papers\"\nTest Run -");
		TaskActions.taskFound(4, driver, "Finish reading research papers");
		System.out.println("Test 4 completed.\n");

		// check if the color of task - "My new test" is as per category
		System.out.println("Test 5 : Verify task with Name=\"DemoTask2\" is shown in green color as per its category.\nTest Run -");
		TaskActions.taskCategoryVerified(5, driver, "DemoTask2", "College");
		System.out.println("Test 5 completed.\n");

		// check if the span element is not generated for task with None category
		System.out.println("Test 6 : Verify task with Name=\"DemoTask\" is shown in default black color as per its category.\nTest Run -");
		TaskActions.taskCategoryVerified(6, driver, "DemoTask", "None");
		System.out.println("Test 6 completed.\n");

		// check if the pending task is not shown as strike text
		System.out.println("Test 7 : Verify pending task with Name=\"DemoTask\" is shown as default text (not striked).\nTest Run -");
		TaskActions.taskCompletedVerified(7, driver, "DemoTask", false);
		System.out.println("Test 7 completed.\n");

		// check if due date for task is displayed correctly
		System.out.println("Test 8 : Verify task with Name=\"DemoTask3\" is shown with duedate=(15/12/18).\nTest Run -");
		TaskActions.taskDueDateVerified(8, driver, "DemoTask3", 15, 12, 2018);
		System.out.println("Test 8 completed.\n");


		// complete a task and check if the completed task is shown as strike text
		System.out.println("Test 9 : Complete task with Name=\"DemoTask\" and Verify that it is shown as striked text.\nTest Run -");
		EditTaskActions.completeTask(9, driver, "DemoTask");
		TaskActions.taskCompletedVerified(9, driver, "DemoTask", true);
		System.out.println("Test 9 completed.\n");
		
		// revert a completed task to pending and check that task is shown appropriately
		System.out.println("Test 10 : Revert Completed task with Name=\"DemoTask\" to pending and Verify that it is shown as expected.\nTest Run -");
		EditTaskActions.completeTask(10, driver, "DemoTask");
		TaskActions.taskCategoryVerified(10, driver, "DemoTask", "None");
		TaskActions.taskCompletedVerified(10, driver, "DemoTask", false);
		TaskActions.taskDueDateVerified(10, driver, "DemoTask", 0, 0, 0);
		System.out.println("Test 9 completed.\n");
		
		// remove a task and check that it is not shown on index page
		System.out.println("Test 11 : Remove a task with Name=\"DemoTask\" and verify that it is not found on index page.\nTest Run -");
		EditTaskActions.removeTask(11, driver, "DemoTask");
		TaskActions.taskFound(11, driver, "DemoTask");
		System.out.println("Test 11 completed.\n");
		
		
		
		




	}

}
