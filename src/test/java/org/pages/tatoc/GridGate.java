package org.pages.tatoc;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class GridGate {

	
    public WebDriver driver ;
   public String baseUrl/* = "http://10.0.1.86/tatoc/basic/grid/gate"*/;
    String driverPath = "/home/qainfotech/chromedriver_linux64/chromedriver";
    JavascriptExecutor js;
    Properties property;
    
	@BeforeTest
	public void beforeMethod() throws IOException {
		property = new Properties();
		File f = new File("property//tatoc.properties");
		FileReader reader = new FileReader(f);
		property.load(reader);
		System.out.println("inside property file");
		driverPath = property.getProperty( "driverpath");
    	 driver= new ChromeDriver(); // created an instance of a chrome driver
		 driver.manage().window().maximize();// maximize the window size
		 baseUrl = property.getProperty("url");
		 driver.get(baseUrl);
		 js = (JavascriptExecutor) driver;  
	}
	
	@Test
	public void opensNextPage() {
		js.executeScript("document.querySelector(\"[class='greenbox']\")" + ".click()");
		Assert.assertEquals("Frame Dungeon - Basic Course - T.A.T.O.C", driver.getTitle(), "It will open an error page.");
		System.out.println("When we click on the green box it proceeds to the next page.");
		driver.navigate().back();
	}
	
	@Test
	public void proceedToErrorPage() {
		js.executeScript("document.querySelector(\"[class='redbox']\")" + ".click()");
		Assert.assertEquals("Error - T.A.T.O.C", driver.getTitle(), "It will proceed to the next Page");
		System.out.println("When we click on the red box it proceeds to an error page.");
		driver.navigate().back();
	}
	

	@AfterTest
	 public void afterMethod() {
	     driver.quit();
	 }
}
