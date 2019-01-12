package testCases;

import static org.junit.Assert.assertTrue;

import java.util.concurrent.ThreadLocalRandom;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

import unitTests.SeleniumTestMain;

public class ARTest04RegisterIndividualIndividual {
	private WebDriver driver;
	
	@Test
	public void registerIndvIndividualParent() {
		driver = SeleniumTestMain.driverUtils.getDriver();
		SeleniumTestMain.driverUtils.waitPageLoad();
		assertTrue("Expceted ARRII encountered " + SeleniumTestMain.driverUtils.getPageId(),
				"ARRII".equals(SeleniumTestMain.driverUtils.getPageId()));
		Select dropdown = new Select(driver.findElement(By.xpath("//*[@id=\"gender\"]")));
		int randomNum = ThreadLocalRandom.current().nextInt(1, 3);
		dropdown.selectByIndex(randomNum);

		SeleniumTestMain.driverUtils.inputValue(System.getProperty("MONTH"), "//*[@id=\"monthdateOfBirth1\"]");
		SeleniumTestMain.driverUtils.inputValue(System.getProperty("DAY"),"//*[@id=\"datedateOfBirth1\"]");
		SeleniumTestMain.driverUtils.inputValue(String.valueOf(Integer.parseInt(System.getProperty("YEAR")) - 21),"//*[@id=\"yeardateOfBirth1\"]");
		
		String ssn[] = SeleniumTestMain.driverUtils.generateSSN();
		SeleniumTestMain.driverUtils.inputValue(ssn[0],"//*[@id=\"ssn1ssn\"]");
		SeleniumTestMain.driverUtils.inputValue(ssn[1],"//*[@id=\"ssn2ssn\"]");
		SeleniumTestMain.driverUtils.inputValue(ssn[2],"//*[@id=\"ssn3ssn\"]");
		
		dropdown = new Select(driver.findElement(By.xpath("//*[@id=\"ccIndvImpairment\"]")));
		dropdown.selectByIndex(1);
		
		driver.findElement(By.xpath("//*[@name=\"submit\"]")).click();
		
	}
}
