package com.utils;

import java.util.concurrent.ThreadLocalRandom;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.main.PG4Case;

public class EN extends PageUtils{
	private String parent;
	public void startEN() {
		System.out.println("----------------Starting EN");
		parent = (String) PG4Case.driver.getWindowHandles().toArray()[0];
		enrollmentSearch();
		startEnrollments();
		System.out.println("----------------Finished EN");
	}
	
	public void enrollmentSearch() {
		waitPageLoad();
		PG4Case.driver.findElement(By.xpath("//*[@id=\"othersImage\"]/a")).click();
		PG4Case.driver.findElement(By.xpath("//*[@id=\"ENTOP\"]/a")).click();
		PG4Case.driver.findElement(By.xpath("//*[@id=\"ENTOL\"]/a")).click();
		PG4Case.driver.findElement(By.xpath("//*[@id=\"ENESX\"]/p/a")).click();
		waitPageLoad();
		inputValue(PG4Case.CASENUM, "//*[@id=\"caseNumber\"]");
		PG4Case.driver.findElement(By.xpath("//*[@id=\"button2\"]")).click();
	}
	
	public void startEnrollments() {
		waitPageLoad();
		JavascriptExecutor jse;
		WebElement wb = PG4Case.driver.findElement(By.xpath("//*[@id=\"rowsBox\"]"));
		jse = (JavascriptExecutor) PG4Case.driver;
		int records = ((Long) jse.executeScript("return $('#grid0').jqGrid('getGridParam').records;", wb)).intValue();
		String childId = "";
		String onClick = "";
		for(int i = 1; i <= records; i ++) {
			waitPageLoad();
			jse.executeScript("$('#grid0').setGridParam({rowNum:"+records+"});");
			jse.executeScript("$('#grid0').trigger('reloadGrid');");
			waitPageLoad();
			if(i > 9 && i < 20) {
				PG4Case.driver.findElement(By.cssSelector("#\\"+(31)+" "+(i-10)+" > td:nth-child(2) > input:nth-child(1)")).click();
			}else if(i <= 9) {
				PG4Case.driver.findElement(By.cssSelector("#\\"+(30+i)+"  > td:nth-child(2) > input:nth-child(1)")).click();
			}
			jse.executeScript("submitForm();");
			
			enrollmentStatusInfo();
			generalEnrollmentInfo();
			vendorRateInfo();
			ppaSchedule();
			enrollmentApprovedHoursOfCare();
			familyFee();
		}
		if(PG4Case.generateCert) {
			generateCertificate();
		}
	}
	
	public void enrollmentStatusInfo() {
		waitPageLoad();
		Select dropdown = new Select(PG4Case.driver.findElement(By.xpath("//*[@id=\"status\"]")));
		dropdown.selectByValue("AUT");
		inputValue(PG4Case.VENDOR, "//*[@id=\"vendorId\"]");
		dropdown = new Select(PG4Case.driver.findElement(By.xpath("//*[@id=\"provRelationChild\"]")));
		int randomNum = ThreadLocalRandom.current().nextInt(1, 5);
		dropdown.selectByIndex(randomNum);
		PG4Case.driver.findElement(By.xpath("//*[@id=\"actionButtonNext\"]")).click();
	}
	
	public void generalEnrollmentInfo() {
		waitPageLoad();
		PG4Case.driver.findElement(By.xpath("//*[@id=\"actionButtonNext\"]")).click();
	}
	
	public void vendorRateInfo() {
		waitPageLoad();
		int randomNum = ThreadLocalRandom.current().nextInt(50, 2000);
		inputValue(String.valueOf(randomNum), "//*[@id=\"pmEnrlmntRatesWeeklyVendorRate\"]");
		PG4Case.driver.findElement(By.xpath("//*[@id=\"actionButtonNext\"]")).click();
	}
	
	public void ppaSchedule() {
		waitPageLoad();
		PG4Case.driver.findElement(By.xpath("//*[@id=\"actionButtonNext\"]")).click();
	}
	
	public void enrollmentApprovedHoursOfCare() {
		waitPageLoad();
		PG4Case.driver.findElement(By.xpath("//*[@id=\"variableSchedule\"]")).click();
		inputValue("40", "//*[@id=\"totalHoursWeek\"]");
		inputValue("5", "//*[@id=\"totalDays\"]");
		PG4Case.driver.findElement(By.xpath("//*[@id=\"actionButtonNext\"]")).click();
	}
	
	public void familyFee() {
		waitPageLoad();
		do {
			PG4Case.driver.findElement(By.xpath("//*[@id=\"actionButtonNext\"]")).click();
		}while("ENCCS".equals(PG4Case.driver.findElement(By.xpath("//*[@id=\"PAGE_ID\"]")).getAttribute("value")));
		
	}
	
	public void generateCertificate() {
		waitPageLoad();
		JavascriptExecutor jse;
		WebElement wb = PG4Case.driver.findElement(By.xpath("//*[@id=\"rowsBox\"]"));
		jse = (JavascriptExecutor) PG4Case.driver;
		int records = ((Long) jse.executeScript("return $('#grid0').jqGrid('getGridParam').records;", wb)).intValue();
		String childId = "";
		String onClick = "";
		for(int i = 1; i <= records; i ++) {
			waitPageLoad();
			jse.executeScript("$('#grid0').setGridParam({rowNum:"+records+"});");
			jse.executeScript("$('#grid0').trigger('reloadGrid');");
			waitPageLoad();

			PG4Case.driver.findElement(By.xpath("//*[@id=\""+i+"\"]/td[13]/img")).click();
			
			String popup = (String) PG4Case.driver.getWindowHandles().toArray()[1];
			
			//test2.removeAll(test1);
			
			PG4Case.driver.switchTo().window(popup);
			waitPageLoad();
			Select dropdown = new Select(PG4Case.driver.findElement(By.xpath("//*[@id=\"certificateCriteria\"]")));
			dropdown.selectByValue("NE");
			PG4Case.driver.findElement(By.xpath("//*[@id=\"submit\"]")).click();	
			PG4Case.driver.switchTo().window(parent);
			waitPageLoad();
		}
		
	}
}
