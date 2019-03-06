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
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class FrameDungeon {

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
		 baseUrl = property.getProperty("frame_url");
		 driver.get(baseUrl);
		 js = (JavascriptExecutor) driver;
	}
    
    @Test
	public void boxColourCheck() {
    	
    	driver.switchTo().frame("main");
    	WebElement Box1 = driver.findElement(By.xpath("//div[@id= 'answer' and text() ='Box 1']"));
    	driver.switchTo().frame("child");
    	WebElement Box2 = driver.findElement(By.xpath("//div[@id= 'answer' and text() ='Box 2']"));
    	driver.switchTo().parentFrame();
    	js.executeScript("document.querySelector(\"a[onclick='gonext();']\")" + ".click()");
    	if(Box1==(Box2)) {
			Assert.assertEquals(driver.getTitle(), "Drag - Basic Course - T.A.T.O.C");
			Reporter.log("When we click on proceed if the colour of the box1 and box2 are same then it proceeds to the next page.", true);
		}
		else
		{
			Assert.assertEquals(driver.getTitle(), "Error - T.A.T.O.C");
			Reporter.log("When we click on proceed if the colour of box1 and box2 are not same then it proceeds to an error page.", true);
		}
    	driver.navigate().back();
	}
    
    @Test
    public void clickRepaintBox2() {
    	driver.switchTo().frame("main");
    	WebElement Box1 = driver.findElement(By.xpath("//div[@id= 'answer' and text() ='Box 1']"));
    	driver.switchTo().frame("child");
    	WebElement Box2 = driver.findElement(By.xpath("//div[@id= 'answer' and text() ='Box 2']"));
    	driver.switchTo().parentFrame();
		
    	if(!(Box1.equals(Box2))) {
    		js.executeScript("document.querySelector(\"a[onclick='reloadChildFrame();']\")" + ".click()");
        	Reporter.log("Colour of box1 and box2 are not same then we click on 'Repaint Box 2' to recolour it.", true);
		}
		else
		{
			js.executeScript("document.querySelector(\"a[onclick='gonext();']\")" + ".click()");
	    	Assert.assertEquals(driver.getTitle(), "Drag - Basic Course - T.A.T.O.C");
			Reporter.log("Colour of box1 and box2 are same then we click on 'Proceed' to open the next page.", true);
		}
    	driver.navigate().back();
    }
    
    @Test
    public void proceedAfterRepaint() {
    	driver.switchTo().frame("main");
    	WebElement Box1 = driver.findElement(By.xpath("//div[@id= 'answer' and text() ='Box 1']"));
    	driver.switchTo().frame("child");
    	WebElement Box2 = driver.findElement(By.xpath("//div[@id= 'answer' and text() ='Box 2']"));
    	driver.switchTo().parentFrame();
		
    	if(Box1!=(Box2)) {
    		js.executeScript("document.querySelector(\"a[onclick='reloadChildFrame();']\")" + ".click()");
        	js.executeScript("document.querySelector(\"a[onclick='gonext();']\")" + ".click()");
        	if(Box1.equals(Box2)) {
    			Assert.assertEquals(driver.getTitle(), "Drag - Basic Course - T.A.T.O.C");
    			Reporter.log("After repainting the second box if both the boxes have same colour then proceed to the next page.", true);
    		}
    		else
    		{
    			Assert.assertEquals(driver.getTitle(), "Error - T.A.T.O.C");
    			Reporter.log("Colour of box1 and box2 are not same after repainting second box then after clicking on 'Proceed' it proceeds to an error page.", true);
    		}
		}
		else
		{
			Assert.assertEquals(driver.getTitle(), "Drag - Basic Course - T.A.T.O.C");
			Reporter.log("Colour of box1 and box2 are same after repainting the second box then after clicking on 'Proceed' it proceeds to the next page.", true);
		}
    	
    }
    
    @AfterTest
	 public void afterMethod() {
	     driver.quit();
	 }
}
