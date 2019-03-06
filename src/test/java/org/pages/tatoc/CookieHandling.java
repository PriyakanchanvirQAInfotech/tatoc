package org.pages.tatoc;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
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
		 baseUrl = property.getProperty("cookie_url");
		 driver.get(baseUrl);
		 js=  (JavascriptExecutor) driver;
	}
   
    @Test
    public void cookieDirectProceed() {
    	js.executeScript("document.querySelector(\"a[onclick='gonext();']\")" + ".click()");
    	Assert.assertEquals( driver.getTitle(), "Error - T.A.T.O.C");
    	Reporter.log("When directly click on proceed and it will open an error page.", true);
     	js.executeScript("window.history.back();");
    }
    
    @Test
    public void cookieGenerateTokenAndProceed() {
    	js.executeScript("document.querySelector(\"a[onclick='generateToken();']\")" + ".click()");
    	js.executeScript("document.querySelector(\"a[onclick='gonext();']\")" + ".click()");
    	Assert.assertEquals( driver.getTitle(), "Error - T.A.T.O.C");
    	Reporter.log("Generate token and click on proceed and it will open an error page.", true);
    	js.executeScript("window.history.back();");
    }
    
    @Test
    public void cookieSetProperly() {
    	js.executeScript("document.querySelector(\"a[onclick='generateToken();']\")" + ".click()");
    	String cookie = driver.findElement(By.id("token")).getText();
    	String[] cookiesplit = cookie.split(": ");
    	
    	Cookie newcookie = new Cookie("Token", cookiesplit[1]);
    	
    	driver.manage().addCookie(newcookie);
    	js.executeScript("document.querySelector(\"a[onclick='gonext();']\")" + ".click()");
    	Assert.assertEquals( driver.getTitle(), "End - T.A.T.O.C");
    	Reporter.log("Generate token, copy token value and create cookie then click on proceed and it will proceed to next page.", true);
    	js.executeScript("window.history.back();");
    }
    @AfterTest
	 public void afterMethod() {
	     driver.quit();
	 }
}
