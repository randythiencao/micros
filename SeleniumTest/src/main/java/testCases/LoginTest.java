package testCases;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import unitTests.SeleniumTestMain;

public class LoginTest {
 
	@Test
	public void login() {
		SeleniumTestMain.driverUtils.getDriver().get(SeleniumTestMain.DEV);
		SeleniumTestMain.driverUtils.waitPageLoad();
		
		WebDriver driver = SeleniumTestMain.driverUtils.getDriver();
		
		assertTrue("a",SeleniumTestMain.driverUtils.isElementPresent(By.xpath("//*[@id=\"main\"]/div[4]/div[3]/div[1]/input")));
		assertTrue("b",SeleniumTestMain.driverUtils.isElementPresent(By.xpath("//*[@id=\"main\"]/div[4]/div[3]/div[2]/input")));
		assertTrue("C",SeleniumTestMain.driverUtils.isElementPresent(By.xpath("//*[@id=\"loginButtons\"]/input")));
		
		WebElement element = driver.findElement(By.xpath("//*[@id=\"main\"]/div[4]/div[3]/div[1]/input"));
		element.sendKeys("impccadmin");

		
		element = driver.findElement(By.xpath("//*[@id=\"main\"]/div[4]/div[3]/div[2]/input"));
		element.sendKeys("123");
		
		
		SeleniumTestMain.driverUtils.getDriver().findElement(By.xpath("//*[@id=\"loginButtons\"]/input")).click();
		SeleniumTestMain.driverUtils.waitPageLoad();
		assertTrue("Login","SELHP".equals(SeleniumTestMain.driverUtils.getPageId()));
	}
}
