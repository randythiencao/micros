package testCases;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import unitTests.SeleniumTestMain;

public class ARTest01RegisterAppliction {
	private WebDriver driver;

	@Test
	public void registerApplication() {
		WebDriver driver = SeleniumTestMain.driverUtils.getDriver();
		SeleniumTestMain.driverUtils.waitPageLoad();

		driver.findElement(By.xpath("//*[@id=\"ARTOP\"]/a")).click();
		driver.findElement(By.xpath("//*[@id=\"AREGA\"]/a")).click();
		SeleniumTestMain.driverUtils.waitPageLoad();
		assertTrue("Expceted ARRAP encountered " + SeleniumTestMain.driverUtils.getPageId(),
				"ARRAP".equals(SeleniumTestMain.driverUtils.getPageId()));

		WebElement element = driver.findElement(By.xpath("//*[@id=\"RegesteredDateAndTime\"]"));
		String[] date = element.getAttribute("value").split(" ")[0].split("-");
		
		System.setProperty("DAY", date[2]);
		System.setProperty("MONTH", date[1]);
		System.setProperty("YEAR", date[0]);
		
		SeleniumTestMain.driverUtils.inputValue(date[1], "//*[@id=\"monthdateReceived\"]");
		SeleniumTestMain.driverUtils.inputValue(date[2], "//*[@id=\"datedateReceived\"]");
		SeleniumTestMain.driverUtils.inputValue(date[0], "//*[@id=\"yeardateReceived\"]");

		Select dropdown = new Select(driver.findElement(By.xpath("//*[@id=\"applicationSigned\"]")));
		dropdown.selectByIndex(2);
		dropdown = new Select(driver.findElement(By.xpath("//*[@id=\"appModeCd\"]")));
		dropdown.selectByIndex(4);

		SeleniumTestMain.driverUtils.inputValue(SeleniumTestMain.driverUtils.generateString(),
				"//*[@id=\"firstName\"]");
		SeleniumTestMain.driverUtils.inputValue(SeleniumTestMain.driverUtils.generateString(), "//*[@id=\"lastName\"]");

		driver.findElement(By.xpath("//*[@id=\"button2\"]")).click();
	}

	
}
