package testCases;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import unitTests.SeleniumTestMain;

public class ARTest05FileClearanceResults {
	private WebDriver driver;
	
	@Test
	public void clearanceResults() {
		driver = SeleniumTestMain.driverUtils.getDriver();
		SeleniumTestMain.driverUtils.waitPageLoad();
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		assertTrue("Expceted ARTMP encountered " + SeleniumTestMain.driverUtils.getPageId(),
				"ARTMP".equals(SeleniumTestMain.driverUtils.getPageId()));
		WebElement wb = driver.findElement(By.xpath("//*[@name=\"submit\"]"));
		String pageId = (String) jse.executeScript("return document.getElementById('PAGE_ID').value;", wb);
		if("ARTMP".equals(pageId)) {
			Select dropdown = new Select(driver.findElement(By.xpath("//*[@id=\"establishNewIndividual\"]")));
			dropdown.selectByValue("Y");
			do {
				driver.findElement(By.xpath("//*[@id=\"button2\"]")).click();
			}while("ARTMP".equals(driver.findElement(By.xpath("//*[@id=\"PAGE_ID\"]")).getAttribute("value")));
		}
	}
}
