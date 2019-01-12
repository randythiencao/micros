package testCases;

import static org.junit.Assert.assertTrue;

import java.util.concurrent.ThreadLocalRandom;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import unitTests.SeleniumTestMain;

public class ARTest06FRegisterIndividualSummary {
	private WebDriver driver;
	private static final String[] count = {
			"one",
		    "two",
		    "three",
		    "four",
		    "five",
		    "six",
		    "seven",
		    "eight",
		    "nine",
		    "ten",
		    "eleven",
		    "twelve",
		    "thirteen",
		    "fourteen",
		    "fifteen",
		    "sixteen",
		    "seventeen",
		    "eighteen",
		    "nineteen"};
	
	@Test
	public void registerIndvSummary() {
		int children = Integer.parseInt(System.getProperty("children"));
		driver = SeleniumTestMain.driverUtils.getDriver();
		SeleniumTestMain.driverUtils.waitPageLoad();
		assertTrue("Expceted ARRIH encountered " + SeleniumTestMain.driverUtils.getPageId(),
				"ARRIH".equals(SeleniumTestMain.driverUtils.getPageId()));
		for(int i = 0; i < children; i++) {
			SeleniumTestMain.driverUtils.waitPageLoad();
			driver.findElement(By.xpath("//*[@id=\"button3\"]")).click();
			registerIndvIndividualChild(i);
			clearanceResults();
		}
		driver.findElement(By.xpath("//*[@id=\"button4\"]")).click();
	}
	
	public void registerIndvIndividualChild(int num) {
		SeleniumTestMain.driverUtils.waitPageLoad();
		
		int randomNum = ThreadLocalRandom.current().nextInt(1, 3);
		Select dropdown = new Select(driver.findElement(By.xpath("//*[@id=\"gender\"]")));
		dropdown.selectByIndex(randomNum);

		SeleniumTestMain.driverUtils.inputValue(count[num],"//*[@id=\"firstName\"]");
		SeleniumTestMain.driverUtils.inputValue("Child","//*[@id=\"lastName\"]");
		
		
		randomNum = ThreadLocalRandom.current().nextInt(1, Integer.parseInt(System.getProperty("MONTH"))+1);
		SeleniumTestMain.driverUtils.inputValue(String.valueOf(randomNum),"//*[@id=\"monthdateOfBirth1\"]");
		SeleniumTestMain.driverUtils.inputValue("1","//*[@id=\"datedateOfBirth1\"]");
		randomNum = ThreadLocalRandom.current().nextInt(1, 10);
		SeleniumTestMain.driverUtils.inputValue(String.valueOf(Integer.parseInt(System.getProperty("YEAR")) - randomNum),"//*[@id=\"yeardateOfBirth1\"]");
		
		String ssn[] = SeleniumTestMain.driverUtils.generateSSN();
		SeleniumTestMain.driverUtils.inputValue(ssn[0],"//*[@id=\"ssn1ssn\"]");
		SeleniumTestMain.driverUtils.inputValue(ssn[1],"//*[@id=\"ssn2ssn\"]");
		SeleniumTestMain.driverUtils.inputValue(ssn[2],"//*[@id=\"ssn3ssn\"]");
		
		dropdown = new Select(driver.findElement(By.xpath("//*[@id=\"ccIndvImpairment\"]")));
		dropdown.selectByIndex(1);
		
		driver.findElement(By.xpath("//*[@id=\"actionButtonNext\"]")).click();
	}
	
	public void clearanceResults() {
		SeleniumTestMain.driverUtils.waitPageLoad();
		WebElement wb = driver.findElement(By.xpath("//*[@name=\"submit\"]"));
		JavascriptExecutor jse = (JavascriptExecutor)driver;
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
