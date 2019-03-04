package org.pages.tatoc;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class DragAndDrop {

	public WebDriver driver ;
    public String baseUrl = "http://10.0.1.86/tatoc/basic/drag#";
    String driverPath = "/home/qainfotech/Downloads/chromedriver_linux64/chromedriver";
 
    @BeforeTest
	public void beforeMethod() {
		System.setProperty("webdriver.chrome.driver", driverPath);
		driver= new ChromeDriver(); // created an instance of a chrome driver
		 driver.manage().window().maximize();// maximize the window size
		 driver.get(baseUrl);
	}
    
    @Test
	public void dragBoxOpensNextPage() {

    	WebElement From=driver.findElement(By.id("dragbox"));	
        WebElement To=driver.findElement(By.id("dropbox"));	
    	Actions act=new Actions(driver);					
        act.dragAndDrop(From, To).build().perform();
        driver.findElement(By.xpath("//a[contains(text(),'Proceed')]")).click();
        Assert.assertEquals( driver.getTitle(), "Windows - Basic Course - T.A.T.O.C");
        System.out.println("Dragbox and drop it in the box and click on proceed and it will open the next page.");
        driver.navigate().back();
	}
	
    @Test
	public void dragBoxProceedsToErrorPage() {
    	
    	WebElement From=driver.findElement(By.id("dragbox"));	
        WebElement To=driver.findElement(By.id("dragbox"));	
    	Actions act=new Actions(driver);					
        act.dragAndDrop(From, To).build().perform();
        driver.findElement(By.xpath("//a[contains(text(),'Proceed')]")).click();
        Assert.assertEquals( driver.getTitle(), "Error - T.A.T.O.C");
        System.out.println("Dragbox and drop it outside the box and click on proceed and it will open an error page.");
	}

    @AfterTest
	 public void afterMethod() {
	     driver.quit();
	 }
}
