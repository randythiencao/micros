package testCases;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import unitTests.SeleniumTestMain;

public class ARTest03RegisterApplicationType {
	private WebDriver driver;

	@Test
	public void type() {
		driver = SeleniumTestMain.driverUtils.getDriver();
		SeleniumTestMain.driverUtils.waitPageLoad();
		assertTrue("Expceted ARRAT encountered " + SeleniumTestMain.driverUtils.getPageId(),
				"ARRAT".equals(SeleniumTestMain.driverUtils.getPageId()));
		driver.findElement(By.xpath("//*[@name=\"submit\"]")).click();
	}
}
