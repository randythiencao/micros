package testCases;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

import unitTests.SeleniumTestMain;

public class ARTest02Address {
	private WebDriver driver;

	@Test
	public void address() {
		driver = SeleniumTestMain.driverUtils.getDriver();
		SeleniumTestMain.driverUtils.waitPageLoad();
		
		assertTrue("Expceted ARRAD encountered " + SeleniumTestMain.driverUtils.getPageId(),
				"ARRAD".equals(SeleniumTestMain.driverUtils.getPageId()));
		
		driver.findElement(By.xpath("//*[@id=\"Foreign\"]")).click();
		SeleniumTestMain.driverUtils.inputValue("Somewhere", "//*[@id=\"addressLine1Foreign\"]");
		SeleniumTestMain.driverUtils.inputValue("Over The Rainbow", "//*[@id=\"addressLine2Foreign\"]");

		Select dropdown = new Select(driver.findElement(By.xpath("//*[@id=\"countryResidenceForeign\"]")));
		dropdown.selectByValue("VN");
		driver.findElement(By.xpath("//*[@id=\"actionButtonNext\"]")).click();
	}
}
