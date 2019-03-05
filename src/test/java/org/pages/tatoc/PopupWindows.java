package org.pages.tatoc;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class PopupWindows {

	public WebDriver driver;
    public String baseUrl = "http://10.0.1.86/tatoc/basic/windows";
    String driverPath = "/home/qainfotech/Downloads/chromedriver_linux64/chromedriver";
 
    @BeforeTest
	public void beforeMethod() {
		System.setProperty("webdriver.chrome.driver", driverPath);
		driver= new ChromeDriver(); // created an instance of a chrome driver
		 driver.manage().window().maximize();// maximize the window size
		 driver.get(baseUrl);
	}

    @Test
    public void launchPopupWindow() {
    	String parentHandle = driver.getWindowHandle();
    	driver.findElement(By.xpath("//a[contains(text(),'Launch Popup Window')]")).click();
    	for (String winHandle : driver.getWindowHandles()) {
            driver.switchTo().window(winHandle); 
        }
    	Reporter.log("Click on Launch Popup Window to open the popup window.", true);
    	driver.switchTo().window(parentHandle);
    }
    
    @Test 
    public void directlyProceed() {
    	driver.findElement(By.cssSelector(".page a[onclick='gonext();']")).click();
    	Assert.assertEquals(driver.getTitle(),"Error - T.A.T.O.C");
    	Reporter.log("Click on proceed directly and it will open an error page.", true);
    	driver.navigate().back();
    }
    
    @Test
    public void launchPopupWithoutValue() {
    	String parentHandle = driver.getWindowHandle();
    	driver.findElement(By.cssSelector(".page a[onclick='launchwindow();']")).click();
    	for (String winHandle : driver.getWindowHandles()) {
            driver.switchTo().window(winHandle); 
        }
    	driver.findElement(By.id("submit")).click();
    	driver.switchTo().window(parentHandle);
    	driver.findElement(By.cssSelector(".page a[onclick='gonext();']")).click();
    	Assert.assertEquals(driver.getTitle(), "Error - T.A.T.O.C");
    	Reporter.log("Click on Launch Popup Window to open the popup window and click on submit without putting any value it will open an error page.", true);
    	driver.navigate().back();
    }
    
    @Test
    public void popUpWindowWithValue() {
    	String parentHandle = driver.getWindowHandle();
    	driver.findElement(By.cssSelector(".page a[onclick='launchwindow();']")).click();
    	for (String winHandle : driver.getWindowHandles()) {
            driver.switchTo().window(winHandle); 
        }
    	driver.findElement(By.id("name")).sendKeys("Priya");
    	driver.findElement(By.id("submit")).click();
    	driver.switchTo().window(parentHandle);
    	driver.findElement(By.cssSelector(".page a[onclick='gonext();']")).click();
    	Assert.assertEquals(driver.getTitle(), "Cookie Handling - Basic Course - T.A.T.O.C");
    	Reporter.log("Click on Launch Popup Window to open the popup window, then put the name in the text field and click on submit button, it will return back to the main page and then click on proceed it will open next page.", true);
    }
    @AfterTest
	 public void afterMethod() {
	     driver.quit();
	 }
    
}
