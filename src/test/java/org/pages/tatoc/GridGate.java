package org.pages.tatoc;

import java.io.File;
import java.net.HttpURLConnection;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class GridGate {

	
    public WebDriver driver ;
    public String baseUrl = "http://10.0.1.86/tatoc/basic/grid/gate";
    String driverPath = "/home/qainfotech/Downloads/chromedriver_linux64/chromedriver";
 
	@BeforeTest
	public void beforeMethod() {
		System.setProperty("webdriver.chrome.driver", driverPath);
		driver= new ChromeDriver(); // created an instance of a chrome driver
		 driver.manage().window().maximize();// maximize the window size
		 driver.get(baseUrl);
	}
	
	@Test
	public void opensNextPage() {
		driver.findElement(By.className("greenbox")).click();
		Assert.assertEquals("Frame Dungeon - Basic Course - T.A.T.O.C", driver.getTitle(), "It will open an error page.");
		System.out.println("When we click on the green box it proceeds to the next page.");
		driver.navigate().back();
	}
	
	@Test
	public void proceedToErrorPage() {
		driver.findElement(By.className("redbox")).click();
		Assert.assertEquals("Error - T.A.T.O.C", driver.getTitle(), "It will proceed to the next Page");
		System.out.println("When we click on the red box it proceeds to an error page.");
		driver.navigate().back();
	}
	
	
	@AfterTest
	 public void afterMethod() {
	     driver.quit();
	 }
}
