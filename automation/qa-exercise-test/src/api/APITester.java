
package api;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import frontend.TestRunner;
import okhttp3.OkHttpClient;

public class APITester {
	
	public static final OkHttpClient client = new OkHttpClient();
	
	public static final String API_URL = "http://localhost/qa-exercise/fake-api-call.php";
	
	private static final WebDriver driver = new FirefoxDriver();
	
	
	public static void main(String args[]) throws IOException {
		
		System.out.println("Testing fake API - " + API_URL + "\n");
		
		System.out.println("Test 1 - Verify http response code.\nTest Run -\n");
		FakeApiTest.getMethodResponseStatusVerified(client, API_URL);
		System.out.println("Test 1 completed.\n");
		
		System.out.println("Test 2 - print all tasks.\nTest Run -\n");
		FakeApiTest.printTasks(client, API_URL);
		System.out.println("Test 2 completed\n");
		
		System.out.println("Test 3 - print tasks with None category.\nTest Run -\n");
		FakeApiTest.printTasksCategoryNone(client, API_URL);
		System.out.println("Test 3 completed\n");
		
		System.out.println("Test 4 - print tasks with status pendig.\nTest Run -\n");
		FakeApiTest.printTasksStatusPending(client, API_URL);
		System.out.println("Test 4 completed\n");
		
		System.out.println("Test 5 - count all tasks and verify count.\nTest Run -\n");
		FakeApiTest.countTasksVerify(client, API_URL, driver, TestRunner.BASE_URL+"index.php");
		System.out.println("Test 5 completed\n");
		
		
		
		
		
		
	}
	

}
