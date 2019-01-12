package com.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.main.PG4Case;

public class AR extends PageUtils{
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
	public static List<String> parentsNameList = new ArrayList<>();
	public static List<String> childrenNameList = new ArrayList<>();
	public static List<String> childrenIdList = new ArrayList<>();
	public static List<String> parentsIdList = new ArrayList<>();
	private JavascriptExecutor jse = (JavascriptExecutor)PG4Case.driver;
	public void startAR(int numOfParents,int children) {
		System.out.println("----------------Starting AR");
		registerApplication();
		address();
		type();
		registerIndvIndividualParent();
		clearanceResults();
		registerIndvSummary(numOfParents,children);
		registerProgProgram(numOfParents,children);
		registerProgSummary();
		registerAppSummary(numOfParents,children);
		System.out.println("----------------Finished AR");
	}
	
	public void registerApplication() {
		waitPageLoad();
		
		PG4Case.driver.findElement(By.xpath("//*[@id=\"ARTOP\"]/a")).click();
		PG4Case.driver.findElement(By.xpath("//*[@id=\"AREGA\"]/a")).click();
		waitPageLoad();
		WebElement element = PG4Case.driver.findElement(By.xpath("//*[@id=\"RegesteredDateAndTime\"]"));
		String[] date = element.getAttribute("value").split(" ")[0].split("-");
		PG4Case.DAY = date[2];
		PG4Case.MONTH = date[1];
		PG4Case.YEAR = date[0];
		
		inputValue(date[1],"//*[@id=\"monthdateReceived\"]");
		inputValue(date[2],"//*[@id=\"datedateReceived\"]");
		inputValue(date[0],"//*[@id=\"yeardateReceived\"]");
		
		Select dropdown = new Select(PG4Case.driver.findElement(By.xpath("//*[@id=\"applicationSigned\"]")));
		dropdown.selectByIndex(2);
		dropdown = new Select(PG4Case.driver.findElement(By.xpath("//*[@id=\"appModeCd\"]")));
		dropdown.selectByIndex(4);

		inputValue(generateString(),"//*[@id=\"firstName\"]");
		inputValue(generateString(),"//*[@id=\"lastName\"]");
		
		
		PG4Case.driver.findElement(By.xpath("//*[@id=\"button2\"]")).click();
		
	}
	
	public void address() {
		
		waitPageLoad();
		
		boolean error = PG4Case.driver.findElement(By.xpath("//*[@id=\"errorSpanClient\"]")).isDisplayed();
		
		PG4Case.driver.findElement(By.xpath("//*[@id=\"Foreign\"]")).click();
		
		inputValue("Somewhere", "//*[@id=\"addressLine1Foreign\"]");
		inputValue("Over The Rainbow", "//*[@id=\"addressLine2Foreign\"]");
		
		Select dropdown = new Select(PG4Case.driver.findElement(By.xpath("//*[@id=\"countryResidenceForeign\"]")));
		dropdown.selectByValue("VN");
		
		PG4Case.driver.findElement(By.xpath("//*[@id=\"actionButtonNext\"]")).click();

		if(error) {
			
		}else {
			
		}
	}
	
	public void type() {
		waitPageLoad();
		PG4Case.driver.findElement(By.xpath("//*[@name=\"submit\"]")).click();
		
	}
	
	public void registerIndvIndividualParent() {
		waitPageLoad();
		Select dropdown = new Select(PG4Case.driver.findElement(By.xpath("//*[@id=\"gender\"]")));
		int randomNum = ThreadLocalRandom.current().nextInt(1, 3);
		dropdown.selectByIndex(randomNum);

		inputValue(PG4Case.MONTH, "//*[@id=\"monthdateOfBirth1\"]");
		inputValue(PG4Case.DAY,"//*[@id=\"datedateOfBirth1\"]");
		inputValue(String.valueOf(Integer.parseInt(PG4Case.YEAR) - 21),"//*[@id=\"yeardateOfBirth1\"]");
		
		String ssn[] = generateSSN();
		inputValue(ssn[0],"//*[@id=\"ssn1ssn\"]");
		inputValue(ssn[1],"//*[@id=\"ssn2ssn\"]");
		inputValue(ssn[2],"//*[@id=\"ssn3ssn\"]");
		
		inputValue(generateString(),"//*[@id=\"firstName\"]");
		inputValue(generateString(),"//*[@id=\"lastName\"]");
		
		dropdown = new Select(PG4Case.driver.findElement(By.xpath("//*[@id=\"ccIndvImpairment\"]")));
		dropdown.selectByIndex(1);
		
		PG4Case.driver.findElement(By.xpath("//*[@name=\"submit\"]")).click();
		
	}
	
	public void clearanceResults() {
		waitPageLoad();
		WebElement wb = PG4Case.driver.findElement(By.xpath("//*[@name=\"submit\"]"));
		String pageId = (String) jse.executeScript("return document.getElementById('PAGE_ID').value;", wb);
		if("ARTMP".equals(pageId)) {
			Select dropdown = new Select(PG4Case.driver.findElement(By.xpath("//*[@id=\"establishNewIndividual\"]")));
			dropdown.selectByValue("Y");
			do {
				PG4Case.driver.findElement(By.xpath("//*[@id=\"button2\"]")).click();
			}while("ARTMP".equals(PG4Case.driver.findElement(By.xpath("//*[@id=\"PAGE_ID\"]")).getAttribute("value")));
		}
	}
	
	public void registerIndvIndividualChild(int num) {
		waitPageLoad();
		
		int randomNum = ThreadLocalRandom.current().nextInt(1, 3);
		Select dropdown = new Select(PG4Case.driver.findElement(By.xpath("//*[@id=\"gender\"]")));
		dropdown.selectByIndex(randomNum);

		inputValue(count[num],"//*[@id=\"firstName\"]");
		inputValue("Child","//*[@id=\"lastName\"]");
		
		
		randomNum = ThreadLocalRandom.current().nextInt(1, Integer.parseInt(PG4Case.MONTH)+1);
		inputValue(String.valueOf(randomNum),"//*[@id=\"monthdateOfBirth1\"]");
		inputValue("1","//*[@id=\"datedateOfBirth1\"]");
		randomNum = ThreadLocalRandom.current().nextInt(1, 10);
		inputValue(String.valueOf(Integer.parseInt(PG4Case.YEAR) - randomNum),"//*[@id=\"yeardateOfBirth1\"]");
		
		String ssn[] = generateSSN();
		inputValue(ssn[0],"//*[@id=\"ssn1ssn\"]");
		inputValue(ssn[1],"//*[@id=\"ssn2ssn\"]");
		inputValue(ssn[2],"//*[@id=\"ssn3ssn\"]");
		
		dropdown = new Select(PG4Case.driver.findElement(By.xpath("//*[@id=\"ccIndvImpairment\"]")));
		dropdown.selectByIndex(1);
		
		PG4Case.driver.findElement(By.xpath("//*[@id=\"actionButtonNext\"]")).click();
		
	}
	
	public void registerIndvSummary(int parents,int children) {
		waitPageLoad();
		for(int i =0; i < parents-1; i++) {
			waitPageLoad();
			PG4Case.driver.findElement(By.xpath("//*[@id=\"button3\"]")).click();
			registerIndvIndividualParent();
			clearanceResults();
		}
		for(int i = 0; i < children; i++) {
			waitPageLoad();
			PG4Case.driver.findElement(By.xpath("//*[@id=\"button3\"]")).click();
			registerIndvIndividualChild(i);
			clearanceResults();
		}
		PG4Case.driver.findElement(By.xpath("//*[@id=\"button4\"]")).click();
	}
	
	public void registerProgProgram(int parents,int children) {
		waitPageLoad();
		WebElement wb = PG4Case.driver.findElement(By.xpath("//*[@id=\"aidRequestSw\"]"));
		jse = (JavascriptExecutor)PG4Case.driver;
		for(int i = children+1; i > parents; i--) {
			wb = PG4Case.driver.findElement(By.xpath("//*[@id=\""+i+"\"]/td[2]"));
			jse.executeScript("document.form1.aidRequestSw['"+(i-1)+"'].checked = true;", wb);
		}
		
		PG4Case.driver.findElement(By.xpath("//*[@id=\"button4\"]")).click();
	}
	
	public void registerProgSummary() {
		waitPageLoad();
		PG4Case.driver.findElement(By.xpath("//*[@id=\"button3\"]")).click();
	}
	
	public void registerAppSummary(int parents, int children) {
		waitPageLoad();
		childrenNameList.clear();
		childrenIdList.clear();
		jse = (JavascriptExecutor)PG4Case.driver;
		WebElement wb = PG4Case.driver.findElement(By.xpath("//*[@id=\"grid0\"]"));
		String name;
		wb = PG4Case.driver.findElement(By.xpath("//*[@id=\"rowsBox\"]"));
		Select dropdown = new Select(wb);
		jse.executeScript("$('#grid0').setGridParam({rowNum:"+(parents+children)+"});", wb);
		jse.executeScript("$('#grid0').trigger('reloadGrid');", wb);
		for(int i = 1; i <= parents; i++) {
			wb = PG4Case.driver.findElement(By.xpath("//*[@id=\""+i+"\"]/td[4]/a"));
			name = (String) jse.executeScript("return $('#grid0').jqGrid('getCell',"+i+",'name').replace(/ /g,'').replace(/&nbsp;/g,' ').replace(/  /g,' ');", wb);
			parentsNameList.add(name);
			parentsIdList.add(wb.getText());
			//System.out.println(rightPadding(name)+" "+wb.getText());
			PG4Case.INDVS.add(rightPadding(name)+" "+wb.getText());
		}
		
		for(int i = parents+1; i < parents+children+1; i++) {
			wb = PG4Case.driver.findElement(By.xpath("//*[@id=\""+i+"\"]/td[4]/a"));
			name = (String) jse.executeScript("return $('#grid0').jqGrid('getCell',"+i+",'name').replace(/ /g,'').replace(/&nbsp;/g,' ').replace(/  /g,' ');", wb);
			childrenNameList.add(name);
			childrenIdList.add(wb.getText());
			//System.out.println(rightPadding(name)+" "+wb.getText());
			PG4Case.INDVS.add(rightPadding(name)+" "+wb.getText());
		}
		
		dropdown = new Select(PG4Case.driver.findElement(By.xpath("//*[@id=\"continueDataCollection\"]")));
		dropdown.selectByValue("Y");
		
		PG4Case.driver.findElement(By.xpath("//*[@id=\"button3\"]")).click();
	}
}
