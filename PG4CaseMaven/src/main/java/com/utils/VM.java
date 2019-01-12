package com.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.main.PG4Case;

public class VM extends PageUtils {
	public void startVM() {
		System.out.println("----------------Starting VM");
		enrollVendor();
		vendorMatch();
		vendorMailingAddressDetail();
		vendorPhysicalAddressDetail();
		vendorDemographicDetail();
		vendorDetail();
		System.out.println("----------------Finished VM");
	}
	
	public void enrollVendor() {
		waitPageLoad();
		PG4Case.driver.findElement(By.xpath("//*[@id=\"othersImage\"]/a")).click();
		PG4Case.driver.findElement(By.xpath("//*[@id=\"VMTOP\"]/a")).click();
		PG4Case.driver.findElement(By.xpath("//*[@id=\"VMENX\"]/a")).click();
		waitPageLoad();
		PG4Case.driver.findElement(By.xpath("//*[@id=\"individual1\"]")).click();
		PG4Case.driver.findElement(By.xpath("//*[@id=\"vendorName2\"]")).sendKeys(generateString());
		
		Select dropdown = new Select(PG4Case.driver.findElement(By.xpath("//*[@id=\"vendorTypeOrg\"]")));
		JavascriptExecutor jse = (JavascriptExecutor) PG4Case.driver;
		int options = ((Long) jse.executeScript("return document.getElementById('vendorTypeOrg').options.length;")).intValue();
		String vendorType= "";
		List<Integer> ccVendors = new ArrayList<Integer>();
		for(int i =1; i < options; i++) {
			vendorType = (String) jse.executeScript("return document.getElementById('vendorTypeOrg').options["+i+"].innerText;");
			if(vendorType.startsWith("CC ")) {
				ccVendors.add(i);
			}
		}
		int randomNum = ThreadLocalRandom.current().nextInt(0, ccVendors.size());
		dropdown.selectByIndex(ccVendors.get(randomNum));
		
		randomNum = ThreadLocalRandom.current().nextInt(10, 99);
		inputValue(String.valueOf(randomNum), "//*[@id=\"fein1fein\"]");
		randomNum = ThreadLocalRandom.current().nextInt(1000000, 9999999);
		inputValue(String.valueOf(randomNum), "//*[@id=\"fein2fein\"]");
		
		inputValue(PG4Case.MONTH, "//*[@id=\"monthfeinVerifiedOn\"]");
		inputValue(PG4Case.DAY, "//*[@id=\"datefeinVerifiedOn\"]");
		inputValue(PG4Case.YEAR, "//*[@id=\"yearfeinVerifiedOn\"]");
		PG4Case.driver.findElement(By.xpath("//*[@id=\"actionButton4\"]")).click();
	}
	
	public void vendorMatch() {
		waitPageLoad();
		do {
			PG4Case.driver.findElement(By.xpath("//*[@id=\"actionButtonNext\"]")).click();
		}while("VMVFC".equals(PG4Case.driver.findElement(By.xpath("//*[@id=\"PAGE_ID\"]")).getAttribute("value")));
	}
	
	public void vendorMailingAddressDetail() {
		waitPageLoad();
		inputValue("Somewhere Mailing", "//*[@id=\"addressLine1\"]");
		Select dropdown = new Select(PG4Case.driver.findElement(By.xpath("//*[@id=\"city\"]")));
		dropdown.selectByValue("064");
		int randomNum = ThreadLocalRandom.current().nextInt(10000, 99999);
		inputValue(String.valueOf(randomNum), "//*[@id=\"zipCode5\"]");
		PG4Case.driver.findElement(By.xpath("//*[@id=\"actionButtonNext\"]")).click();
		
		WebDriverWait wait = new WebDriverWait(PG4Case.driver, 10);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#radio1")));
		PG4Case.driver.findElement(By.cssSelector("#radio1")).click();
		PG4Case.driver.findElement(By.xpath("//*[@id=\"applyButton\"]")).click();
		PG4Case.driver.findElement(By.xpath("//*[@id=\"actionButtonNext\"]")).click();
	}
	
	public void vendorPhysicalAddressDetail() {
		waitPageLoad();
		inputValue("Somewhere Physical", "//*[@id=\"addressLine1\"]");
		Select dropdown = new Select(PG4Case.driver.findElement(By.xpath("//*[@id=\"city\"]")));
		dropdown.selectByValue("064");
		int randomNum = ThreadLocalRandom.current().nextInt(10000, 99999);
		inputValue(String.valueOf(randomNum), "//*[@id=\"zipCode5\"]");
		PG4Case.driver.findElement(By.xpath("//*[@id=\"actionButtonNext\"]")).click();
		
		WebDriverWait wait = new WebDriverWait(PG4Case.driver, 10);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#radio1")));
		PG4Case.driver.findElement(By.cssSelector("#radio1")).click();
		PG4Case.driver.findElement(By.xpath("//*[@id=\"applyButton\"]")).click();
		PG4Case.driver.findElement(By.xpath("//*[@id=\"actionButtonNext\"]")).click();
	}
	
	public void vendorDemographicDetail() {
		waitPageLoad();
		inputValue(PG4Case.MONTH, "//*[@id=\"montheffectiveBeginDate\"]");
		inputValue(PG4Case.DAY, "//*[@id=\"dateeffectiveBeginDate\"]");
		inputValue(PG4Case.YEAR, "//*[@id=\"yeareffectiveBeginDate\"]");
		PG4Case.driver.findElement(By.xpath("//*[@id=\"actionButtonNext\"]")).click();
	}
	
	public void vendorDetail() {
		waitPageLoad();
		inputValue("01", "//*[@id=\"monthcircumstancesStartChangeDate\"]");
		inputValue("01", "//*[@id=\"datecircumstancesStartChangeDate\"]");
		inputValue(PG4Case.YEAR, "//*[@id=\"yearcircumstancesStartChangeDate\"]");
		
		Select dropdown = new Select(PG4Case.driver.findElement(By.xpath("//*[@id=\"status\"]")));
		dropdown.selectByValue("A");
		PG4Case.driver.findElement(By.xpath("//*[@id=\"actionButtonSubmit\"]")).click();
		waitPageLoad();
		if(!PG4Case.driver.findElement(By.xpath("//*[@id=\"errorSpanClient\"]/span")).getText().equals("VM004: Vendor Created Successfully")) {
			
		}
		PG4Case.VENDOR = PG4Case.driver.findElement(By.xpath("//*[@id=\"g_MpsPageHeader\"]/div[2]")).getText();
		//System.out.println(rightPadding("Provider ID:")+" "+PG4Case.VENDOR);
	}
}
