package org.pages.tatoc;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class DragAndDrop {

	String driverPath;
	public WebDriver driver;
	public String baseUrl;
	JavascriptExecutor js;
	

	@BeforeTest
	@Parameters ({"url","path"})
	public void beforeMethod(String url, String path) throws IOException {
		driverPath = path;
		System.setProperty("webdriver.gecko.driver", driverPath);
		DesiredCapabilities capabilities = DesiredCapabilities.firefox();
		capabilities.setCapability("marionette",true);
		driver= new FirefoxDriver(capabilities);
		driver.manage().window().maximize();// maximize the window size
		baseUrl = url;
		driver.get(baseUrl);
		js = (JavascriptExecutor) driver;   
	}
	
	
    @Test
	public void dragBoxOpensNextPage() {

    	WebElement From=driver.findElement(By.id("dragbox"));	
        WebElement To=driver.findElement(By.id("dropbox"));	
    	Actions act=new Actions(driver);					
        act.dragAndDrop(From, To).build().perform();
       
        WebDriverWait wait = new WebDriverWait(driver,20);
        WebElement click = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(("//a[@onclick= 'gonext();']"))));
        click.click();
        
        System.out.println(driver.getTitle());
        Assert.assertEquals( driver.getTitle(), "Windows - Basic Course - T.A.T.O.C");
        System.out.println(driver.getTitle());
        Reporter.log("Dragbox and drop it in the box and click on proceed and it will open the next page.", true);
        driver.navigate().back();
	}
	
    @Test
	public void dragBoxProceedsToErrorPage() {
    	driver.manage().timeouts().implicitlyWait(20,TimeUnit.SECONDS) ;
    	WebElement From=driver.findElement(By.id("dragbox"));	
        WebElement To=driver.findElement(By.id("dragbox"));	
    	Actions act=new Actions(driver);					
        act.dragAndDrop(From, To).build().perform();
        WebDriverWait wait = new WebDriverWait(driver,20);
        WebElement click = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(("//a[@onclick= 'gonext();']"))));
        click.click();
        Assert.assertEquals( driver.getTitle(), "Error - T.A.T.O.C");
        Reporter.log("Dragbox and drop it outside the box and click on proceed and it will open an error page.", true);
	}

    @AfterTest
	 public void afterMethod() {
	     driver.quit();
	 }
}
