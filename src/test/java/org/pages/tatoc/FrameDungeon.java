package org.pages.tatoc;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
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
    	String Box1 = driver.findElement(By.xpath("//div[contains(text(),'Box 1')]")).getAttribute("class");
    	driver.switchTo().frame("child");
    	String Box2 = driver.findElement(By.xpath("//div[contains(text(),'Box 2')]")).getAttribute("class");
    	driver.switchTo().parentFrame();
    	driver.findElement(By.xpath("//a[contains(text(),'Proceed')]")).click();
    	if(Box1.equals(Box2)) {
			Assert.assertEquals(driver.getTitle(), "Drag - Basic Course - T.A.T.O.C");
			System.out.println("When we click on proceed if the colour of the box1 and box2 are same then it proceeds to the next page.");
		}
		else
		{
			Assert.assertEquals(driver.getTitle(), "Error - T.A.T.O.C");
			System.out.println("When we click on proceed if the colour of box1 and box2 are not same then it proceeds to an error page.");
		}
    	driver.navigate().back();
	}
    
    @Test
    public void clickRepaintBox2() {
    	driver.switchTo().frame("main");
    	String Box1 = driver.findElement(By.xpath("//div[contains(text(),'Box 1')]")).getAttribute("class");
    	driver.switchTo().frame("child");
    	String Box2 = driver.findElement(By.xpath("//div[contains(text(),'Box 2')]")).getAttribute("class");
    	driver.switchTo().parentFrame();
		
    	if(Box1!=(Box2)) {
    		driver.findElement(By.xpath("//a[contains(text(),'Repaint Box 2')]")).click();
    		System.out.println("Colour of box1 and box2 are not same then we click on 'Repaint Box 2' to recolour it.");
		}
		else
		{
			driver.findElement(By.xpath("//a[contains(text(),'Proceed')]")).click();
			Assert.assertEquals(driver.getTitle(), "Drag - Basic Course - T.A.T.O.C");
    		System.out.println("Colour of box1 and box2 are same then we click on 'Proceed' to open the next page.");
		}
    	driver.navigate().back();
    }
    
    @Test
    public void proceedAfterRepaint() {
    	driver.switchTo().frame("main");
    	String Box1 = driver.findElement(By.xpath("//div[contains(text(),'Box 1')]")).getAttribute("class");
    	driver.switchTo().frame("child");
    	String Box2 = driver.findElement(By.xpath("//div[contains(text(),'Box 2')]")).getAttribute("class");
    	driver.switchTo().parentFrame();
		
    	if(Box1!=(Box2)) {
    		driver.findElement(By.xpath("//a[contains(text(),'Repaint Box 2')]")).click();
    		driver.findElement(By.xpath("//a[contains(text(),'Proceed')]")).click();
    		if(Box1.equals(Box2)) {
    			Assert.assertEquals(driver.getTitle(), "Drag - Basic Course - T.A.T.O.C");
        		System.out.println("After repainting the second box if both the boxes have same colour then proceed to the next page.");
    		}
    		else
    		{
    			Assert.assertEquals(driver.getTitle(), "Error - T.A.T.O.C");
        		System.out.println("Colour of box1 and box2 are not same after repainting second box then after clicking on 'Proceed' it proceeds to an error page.");
    		}
		}
		else
		{
			Assert.assertEquals(driver.getTitle(), "Drag - Basic Course - T.A.T.O.C");
    		System.out.println("Colour of box1 and box2 are same after repainting the second box then after clicking on 'Proceed' it proceeds to the next page.");
		}
    	
    }
    
    @AfterTest
	 public void afterMethod() {
	     driver.quit();
	 }
}
