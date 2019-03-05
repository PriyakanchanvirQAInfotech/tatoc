package org.pages.tatoc;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class FrameDungeon {

	public WebDriver driver ;
    public String baseUrl = "http://10.0.1.86/tatoc/basic/frame/dungeon";
    String driverPath = "/home/qainfotech/Downloads/chromedriver_linux64/chromedriver";
 
    @BeforeTest
	public void beforeMethod() {
		System.setProperty("webdriver.chrome.driver", driverPath);
		driver= new ChromeDriver(); // created an instance of a chrome driver
		 driver.manage().window().maximize();// maximize the window size
		 driver.get(baseUrl);
	}
    
    @Test
	public void boxColourCheck() {
    	
    	driver.switchTo().frame("main");
    	WebElement Box1 = driver.findElement(By.xpath("//div[@id= 'answer' and text() ='Box 1']"));
    	driver.switchTo().frame("child");
    	WebElement Box2 = driver.findElement(By.xpath("//div[@id= 'answer' and text() ='Box 2']"));
    	driver.switchTo().parentFrame();
    	driver.findElement(By.xpath("//a[ 'answer' and text()= 'Proceed']")).click();
    	if(Box1.equals(Box2)) {
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
		
    	if(Box1!=(Box2)) {
    		driver.findElement(By.xpath("//a[ 'answer' and text()= 'Repaint Box 2']")).click();
    		Reporter.log("Colour of box1 and box2 are not same then we click on 'Repaint Box 2' to recolour it.", true);
		}
		else
		{
			driver.findElement(By.xpath("//a[ 'answer' and text()= 'Proceed']")).click();
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
    		driver.findElement(By.xpath("//a[ 'answer' and text()= 'Repaint Box 2']")).click();
    		driver.findElement(By.xpath("//a[ 'answer' and text()= 'Proceed']")).click();
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
