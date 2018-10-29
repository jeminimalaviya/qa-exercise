package api;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import common.Category;
import common.Task;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class FakeApiTest {

	private static List<Task> tasks = new ArrayList<>();
	
	private static ArrayList<Category> categories = new ArrayList<>();

	// setting categories as per categories.list file along with dummy None category
	static {
		categories.add(new Category("0", "", "None"));
		categories.add(new Category("1","#00FF00","College"));
		categories.add(new Category("2","#FF00FF","Leisure"));
		categories.add(new Category("3","#C0C0C0","Play"));
		categories.add(new Category("4","#BF5700","Personal"));
	}
	

	// verify that the response code of fake api get request is as expected 
	@Test
	public static boolean getMethodResponseStatusVerified(OkHttpClient client, String url) throws IOException {
		boolean verified = Boolean.valueOf(false);
		// creating a new request with given url
		Request request = new Request.Builder().url(url).build();

		// create a new call based on request
		Call call = client.newCall(request);
		System.out.println("Sending GET request to " + url);
		Response response = call.execute();

		System.out.println("Verifying http response code is equal to 200");
		assertEquals(response.code(), 200);
		System.out.println("As Expected.");

		return verified;
	}

	// count tasks and verify
	@Test
	public static void countTasksVerify(OkHttpClient client, String url, WebDriver driver, String frontEndUrl) throws IOException {
		boolean verified = Boolean.valueOf(false);
		System.out.println("Getting tasks from api");
		if(tasks.isEmpty()) {
			tasks = getTasks(client, url);
		}
		System.out.println("Launching application in FireFox");
		driver.get(frontEndUrl);
		System.out.println("Getting tasks from browser");
		List<WebElement> tasksBrowser = driver.findElements(By.xpath("/html/body/div[2]/form/ul/li"));
		
		assertEquals(tasks.size(), tasksBrowser.size());
		System.out.println("count of tasks from api matches with browser");
	}
	
	// parse json response and print all tasks
	@Test
	public static void printTasks(OkHttpClient client, String url) throws IOException {
		if(tasks.isEmpty()) {
			tasks = getTasks(client, url);
		}
		System.out.println("All Tasks - ");
		for(Task t: tasks) {
				System.out.println(t.getName());
		}
	}
	
	
	// parse json response and print all tasks with category none
	@Test
	public static void printTasksCategoryNone(OkHttpClient client, String url) throws IOException {
		if(tasks.isEmpty()) {
			tasks = getTasks(client, url);
		}
		System.out.println("Tasks with None category - ");
		for(Task t: tasks) {
			if(categories.get(Integer.parseInt(t.getCategory())).getValue().equals("None")) {
				System.out.println(t.getName());
			}
		}
	}
	
	// parse json response and print all tasks with status pending
	@Test
	public static void printTasksStatusPending(OkHttpClient client, String url) throws IOException {
		if(tasks.isEmpty()) {
			tasks = getTasks(client, url);
		}
		System.out.println("Tasks with status pending - ");
		for(Task t: tasks) {
			if(t.getCompleted().equals("p")) {
				System.out.println(t.getName());
			}
		}
	}
	
	// parse tasks from response
	protected static List<Task> getTasks(OkHttpClient client, String url) throws IOException{
		// creating a new request with given url
		Request request = new Request.Builder().url(url).build();

		// create a new call based on request
		Call call = client.newCall(request);
		System.out.println("Sending GET request to " + url);
		Response response = call.execute();
		
		assertEquals(response.code(), 200);
		ResponseBody body = response.body();
		String json = body.string();
		List<Task> tasks = new ArrayList<>();
		Gson gson = new Gson();
		Type taskListType = new TypeToken<ArrayList<Task>>() {}.getType();
		tasks = gson.fromJson(json, taskListType);
		return tasks;
	}


}
