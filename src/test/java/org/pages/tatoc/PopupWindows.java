package org.pages.tatoc;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class PopupWindows {

	
	String driverPath;
	public WebDriver driver;
	public String baseUrl;
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
		 baseUrl = property.getProperty("pop_url");
		driver.get(baseUrl);
		js = (JavascriptExecutor) driver;
	}

	@Test
	public void launchPopupWindow() {
		String parentHandle = driver.getWindowHandle();
		js.executeScript("document.querySelector(\"a[onclick='launchwindow();']\")" + ".click()");
		for (String winHandle : driver.getWindowHandles()) {
			driver.switchTo().window(winHandle);
		}
		Reporter.log("Click on Launch Popup Window to open the popup window.", true);
		driver.switchTo().window(parentHandle);
	}

	@Test
	public void directlyProceed() {
		js.executeScript("document.querySelector(\"a[onclick='gonext();']\")" + ".click()");
		Assert.assertEquals(driver.getTitle(), "Error - T.A.T.O.C");
		Reporter.log("Click on proceed directly and it will open an error page.", true);
		driver.navigate().back();
	}

	@Test
	public void launchPopupWithoutValue() {
		String parentHandle = driver.getWindowHandle();
		js.executeScript("document.querySelector(\"a[onclick='launchwindow();']\")" + ".click()");
		for (String winHandle : driver.getWindowHandles()) {
			driver.switchTo().window(winHandle);
		}
		driver.findElement(By.id("submit")).click();
		driver.switchTo().window(parentHandle);
		js.executeScript("document.querySelector(\"a[onclick='gonext();']\")" + ".click()");
		Assert.assertEquals(driver.getTitle(), "Error - T.A.T.O.C");
		Reporter.log(
				"Click on Launch Popup Window to open the popup window and click on submit without putting any value it will open an error page.",
				true);
		js.executeScript("window.history.back();");
	}

	@Test
	public void popUpWindowWithValue() {
		String parentHandle = driver.getWindowHandle();
		js.executeScript("document.querySelector(\"a[onclick='launchwindow();']\")" + ".click()");
		for (String winHandle : driver.getWindowHandles()) {
			driver.switchTo().window(winHandle);
		}
		js.executeScript("document.getElementById('name').value= 'Priya';");
		js.executeScript("document.getElementById('submit').click();");
		driver.switchTo().window(parentHandle);
		js.executeScript("document.querySelector(\"a[onclick='gonext();']\")" + ".click()");
		Assert.assertEquals(driver.getTitle(), "Cookie Handling - Basic Course - T.A.T.O.C");
		Reporter.log(
				"Click on Launch Popup Window to open the popup window, then put the name in the text field and click on submit button, it will return back to the main page and then click on proceed it will open next page.",
				true);
	}

	@AfterTest
	public void afterMethod() {
		driver.quit();
	}

}
