package org.pages.tatoc;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class DragAndDrop {

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
		 baseUrl = property.getProperty("drag_url");
		 driver.get(baseUrl);
		 js = (JavascriptExecutor) driver;   
	}
    
    @Test
	public void dragBoxOpensNextPage() {

    	WebElement From=driver.findElement(By.id("dragbox"));	
        WebElement To=driver.findElement(By.id("dropbox"));	
    	Actions act=new Actions(driver);					
        act.dragAndDrop(From, To).build().perform();
        
        js.executeScript("document.querySelector(\"a[onclick='gonext();']\")" + ".click()");
       // driver.findElement(By.cssSelector(".page a[onclick='gonext();']")).click();
        Assert.assertEquals( driver.getTitle(), "Windows - Basic Course - T.A.T.O.C");
        Reporter.log("Dragbox and drop it in the box and click on proceed and it will open the next page.", true);
        driver.navigate().back();
	}
	
    @Test
	public void dragBoxProceedsToErrorPage() {
    	
    	WebElement From=driver.findElement(By.id("dragbox"));	
        WebElement To=driver.findElement(By.id("dragbox"));	
    	Actions act=new Actions(driver);					
        act.dragAndDrop(From, To).build().perform();
        js.executeScript("document.querySelector(\"a[onclick='gonext();']\")" + ".click()");
        Assert.assertEquals( driver.getTitle(), "Error - T.A.T.O.C");
        Reporter.log("Dragbox and drop it outside the box and click on proceed and it will open an error page.", true);
	}

    @AfterTest
	 public void afterMethod() {
	     driver.quit();
	 }
}
