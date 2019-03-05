package org.pages.tatoc;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import junit.framework.Assert;

public class CookieHandling {
	public WebDriver driver;
    public String baseUrl = "http://10.0.1.86/tatoc/basic/cookie#"; // url of the tatoc page
    String driverPath = "/home/qainfotech/Downloads/chromedriver_linux64/chromedriver";
 
    @BeforeTest
	public void beforeMethod() {
		System.setProperty("webdriver.chrome.driver", driverPath);
		driver= new ChromeDriver(); // created an instance of a chrome driver
		 driver.manage().window().maximize();// maximize the window size
		 driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		 driver.get(baseUrl);
	}
   
    @Test
    public void cookieDirectProceed() {
    	driver.findElement(By.cssSelector(".page a[onclick='gonext();']")).click();
    	Assert.assertEquals( driver.getTitle(), "Error - T.A.T.O.C");
    	Reporter.log("When directly click on proceed and it will open an error page.", true);
     	driver.navigate().back();
    }
    
    @Test
    public void cookieGenerateTokenAndProceed() {
    	driver.findElement(By.cssSelector(".page a[onclick='generateToken();']")).click();
    	driver.findElement(By.cssSelector(".page a[onclick='gonext();']")).click();
    	Assert.assertEquals( driver.getTitle(), "Error - T.A.T.O.C");
    	Reporter.log("Generate token and click on proceed and it will open an error page.", true);
    	driver.navigate().back();
    }
    
    @Test
    public void cookieSetProperly() {
    	driver.findElement(By.cssSelector(".page a[onclick='generateToken();']")).click();
    	String cookie = driver.findElement(By.id("token")).getText();
    	String[] cookiesplit = cookie.split(": ");
    	
    	Cookie newcookie = new Cookie("Token", cookiesplit[1]);
    	
    	driver.manage().addCookie(newcookie);
    	driver.findElement(By.cssSelector(".page a[onclick='gonext();']")).click();
    	Assert.assertEquals( driver.getTitle(), "End - T.A.T.O.C");
    	Reporter.log("Generate token, copy token value and create cookie then click on proceed and it will proceed to next page.", true);
    	driver.navigate().back();
    }
    @AfterTest
	 public void afterMethod() {
	     driver.quit();
	 }
}
