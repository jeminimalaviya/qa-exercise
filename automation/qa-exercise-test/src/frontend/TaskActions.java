package frontend;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import common.Category;

public class TaskActions {

	private static ArrayList<Category> categories = new ArrayList<>();

	// setting categories as per categories.list file along with dummy None category
	static {
		categories.add(new Category("0", "", "None"));
		categories.add(new Category("1","#00FF00","College"));
		categories.add(new Category("2","#FF00FF","Leisure"));
		categories.add(new Category("3","#C0C0C0","Play"));
		categories.add(new Category("4","#BF5700","Personal"));
	}

	// create task with given name, category and due date
	// any parameter passed empty to be selected none value
	public static void createTask(int testId, WebDriver driver, String name, String category, String date) throws Exception {

		System.out.println("Creating task : "+ name);

		// check if category parameter is provided and set appropriately on browser
		if(category != null && category != "") { 
			System.out.println("Category : " + category);

			// create web element to select category
			Select categorySelector = new Select(driver.findElement(By.xpath("/html/body/div[4]/span/select[1]")));
			categorySelector.selectByVisibleText(category);
		}

		// check if date parameter is provided and set appropriately on browser
		if(date != null && date != "") {
			int day = Integer.parseInt(date.substring(0,2));
			int month = Integer.parseInt(date.substring(3,5));
			int year = Integer.parseInt(date.substring(7));
			System.out.println("Date :" + month + "/" + day + "/" + year);

			// create web elements to select day month and year
			Select daySelector = new Select(driver.findElement(By.xpath("/html/body/div[4]/span/select[2]")));
			daySelector.selectByValue(Integer.toString(day));
			Select monthSelector = new Select(driver.findElement(By.xpath("/html/body/div[4]/span/select[3]")));
			monthSelector.selectByValue(Integer.toString(month));
			Select yearSelector = new Select(driver.findElement(By.xpath("/html/body/div[4]/span/select[4]")));
			yearSelector.selectByValue( Integer.toString(2000+year));
		}

		// create web element for text field
		WebElement textField = driver.findElement(By.xpath("/html/body/div[4]/input[1]"));

		// check if text field is enabled
		if(textField.isEnabled()) {
			textField.sendKeys(name); // enter task name into text field
		}

		// create web element for add button
		WebElement run = driver.findElement(By.xpath("/html/body/div[4]/input[2]"));

		// click run button
		if(run.isEnabled()) {
			run.click();
		}
		
		System.out.println("Taking snap shot with new task.");
		takeSnapShot(driver, TestRunner.BASE_DIR+"test"+testId+"-taskcreated-"+name+".png");

	}


	// verify if task is created and visible on index page
	@Test
	public static boolean taskFound(int testId, WebDriver driver, String taskName) throws Exception {
		boolean found = Boolean.valueOf(false);
		System.out.println("Finding Task with name : " + taskName );
		WebElement taskSelected = getTaskElementByName(driver, taskName);
		found = (taskSelected != null);
		if(found) {
			System.out.println("Task Found!");
			System.out.println("Taking snap shot with task.");
			takeSnapShot(driver, TestRunner.BASE_DIR+"test"+testId+"-taskfound-"+taskName+".png");
		}
		else {
			System.out.println("Task Not Found!");
		}
		return found;
	}


	// verify the category and respective color of task
	@Test
	public static boolean taskCategoryVerified(int testId, WebDriver driver, String taskName, String category) throws Exception {
		boolean verified = Boolean.valueOf(false);
		System.out.println("Finding Task with name : " + taskName );
		// get task element based on name
		WebElement taskSelected = getTaskElementByName(driver, taskName);
		if(taskSelected != null) {
			System.out.println("Task Found!");
		}
		else {
			System.out.println("Task Not Found, Aboort!");
			return verified;
		}
		// get the category object based on name
		Category cat = null;
		for(Category c: categories) {
			if(c.getValue().equals(category)) {
				cat = c;
				break;
			}
		}

		// if category is other than "None" then match with color from category list
		if(cat.getValue() != "None") { 
			System.out.println("Validating text color based on category");
			String colorSelected = taskSelected.findElement(By.xpath(".//span")).getCssValue("color");
			String colorSelectedHex = getHexColorByRgb(colorSelected);
			assertEquals(colorSelectedHex.toLowerCase(), cat.getColor().toLowerCase());
			verified = colorSelectedHex.equalsIgnoreCase(cat.getColor());
			if(verified) {
				System.out.println("Text color as expected!");
				System.out.println("Taking snap shot.");
				takeSnapShot(driver, TestRunner.BASE_DIR+"test"+testId+"-taskCategoryColorVerified-"+taskName+".png");
			}
			else {
				System.out.println("Text color not as expected!");
				System.out.println("Taking snap shot.");
				takeSnapShot(driver, TestRunner.BASE_DIR+"test"+testId+"-taskCategoryColorNotAsExpected-"+taskName+".png");
			}
		} 
		// if category is "None" then assert that span element does not exist
		else { 	
			System.out.println("Validating text for None category");
			verified  = taskSelected.findElements(By.xpath(".//span")).isEmpty();
			asExpected(verified);
			System.out.println("Taking snap shot.");
			takeSnapShot(driver, TestRunner.BASE_DIR+"test"+testId+"-taskCategoryColorVerified-"+taskName+".png");
		}

		return verified;
	}


	// verify that completed task are shown as strike text
	// verify that incomplete/pending tasks are shown as plain text
	@Test
	public static boolean taskCompletedVerified(int testId, WebDriver driver, String taskName, boolean completed) throws Exception {
		boolean verified = Boolean.valueOf(false);
		System.out.println("Finding Task with name : " + taskName );
		// get task element based on name
		WebElement taskSelected = getTaskElementByName(driver, taskName);
		if(taskSelected != null) {
			System.out.println("Task Found!");
		}
		else {
			System.out.println("Task Not Found, Aboort!");
			return verified;
		}
		// if task is completed then verify that task name is striked
		if(completed) {
			System.out.println("Validating completed task shown in striked text");
			verified = !taskSelected.findElements(By.xpath(".//strike")).isEmpty();
			asExpected(verified);
			System.out.println("Taking snap shot.");
			takeSnapShot(driver, TestRunner.BASE_DIR+"test"+testId+"-taskCompletedVerified-"+taskName+".png");
		}
		// if task is not completed then verify that there is no striked text
		else {
			System.out.println("Validating pending task not shown in striked text");
			verified = taskSelected.findElements(By.xpath(".//strike")).isEmpty();
			asExpected(verified);
			System.out.println("Taking snap shot.");
			takeSnapShot(driver, TestRunner.BASE_DIR+"test"+testId+"-taskCategoryColorVerified-"+taskName+".png");
		}

		return verified;
	}

	// verify that the due date for task is shown correctly as (DD/MM/YY) for tasks with valid due date
	// verify that the due date is shown as (None) for tasks without a valid due date
	@Test
	public static boolean taskDueDateVerified(int testId, WebDriver driver, String taskName, int day, int month, int year) throws Exception {
		boolean verified = Boolean.valueOf(false);
		System.out.println("Finding Task with name : " + taskName );
		// get task element based on name
		WebElement taskSelected = getTaskElementByName(driver, taskName);
		if(taskSelected != null) {
			System.out.println("Task Found!");
		}
		else {
			System.out.println("Task Not Found, Aboort!");
			return verified;
		}

		
		// if date parameters are non zero then match it with task element due date
		if(day != 0 && month != 0 && year != 0) {
			System.out.println("Validating due date for task");
			String taskString = taskSelected.getText().substring(2+taskName.length());
			int start = taskString.indexOf('(');
			int end = taskString.lastIndexOf(')');
			String dueDateSelected = taskString.substring(start+1, end);
			int daySelected = Integer.parseInt(dueDateSelected.substring(0, 2));
			int monthSelected = Integer.parseInt(dueDateSelected.substring(3, 5));
			int yearSelected = 2000 + Integer.parseInt(dueDateSelected.substring(6, 8));
			
			assertEquals(day, daySelected);
			assertEquals(month, monthSelected);
			assertEquals(year, yearSelected);

			verified = (day==daySelected) && (month==monthSelected) && (year==yearSelected);
			asExpected(verified);
			System.out.println("Taking snap shot.");
			takeSnapShot(driver, TestRunner.BASE_DIR+"test"+testId+"-taskDueDateVerified-"+taskName+".png");
			
		}
		// if date parameters are set zero then verify that duedate of task element is None
		else {
			System.out.println("Validating that due date is (None)");
			String taskString = taskSelected.getText().substring(2+taskName.length());
			verified = taskString.endsWith("(None)");
			asExpected(verified);
			System.out.println("Taking snap shot.");
			takeSnapShot(driver, TestRunner.BASE_DIR+"test"+testId+"-taskDueDateVerified-"+taskName+".png");
		}

		return verified;
	}



	// get task web element from task name
	protected static WebElement getTaskElementByName(WebDriver driver, String taskName) {
		List<WebElement> tasks = driver.findElements(By.xpath("/html/body/div[2]/form/ul/li"));
		WebElement taskSelected = null;

		// get the task web element based on task name
		for(WebElement task: tasks) {
			String taskId = task.findElement(By.xpath(".//a[1]")).getText();
			String taskText = task.getText();
			//taskText = taskText.substring(2);
			if(taskText.startsWith(taskId+taskName+" ")) {
				taskSelected = task;
				break;
			}
		}

		return taskSelected;
	}

	protected static String getHexColorByRgb(String color) {
		String[] hexValue = color.replace("rgb(", "").replace(")", "").split(",");

		int hexValue1=Integer.parseInt(hexValue[0]);
		hexValue[1] = hexValue[1].trim();
		int hexValue2=Integer.parseInt(hexValue[1]);
		hexValue[2] = hexValue[2].trim();
		int hexValue3=Integer.parseInt(hexValue[2]);

		String colorHex = String.format("#%02x%02x%02x", hexValue1, hexValue2, hexValue3);

		return colorHex;
	}


	protected static void asExpected(boolean verified) {
		if(verified) {
			System.out.println("As expected");
		}
		else {
			System.out.println("Not as expected");
		}
	}

	// method taken from https://www.guru99.com/take-screenshot-selenium-webdriver.html
	protected static void takeSnapShot(WebDriver webdriver,String fileWithPath) throws Exception{

		//Convert web driver object to TakeScreenshot
		TakesScreenshot scrShot =((TakesScreenshot)webdriver);

		//Call getScreenshotAs method to create image file
		File SrcFile=scrShot.getScreenshotAs(OutputType.FILE);

		//Move image file to new destination
		File DestFile=new File(fileWithPath);

		//Copy file at destination
		FileUtils.copyFile(SrcFile, DestFile);

	}


}
