package com.utils;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.main.PG4Case;

public abstract class PageUtils {
	public String getCurrPageId() {
		return PG4Case.currPageId;
	}

	public void setCurrPageId(String currPageId) {
		PG4Case.currPageId = currPageId;
	}

	public void waitPageLoad() {
		ExpectedCondition<Boolean> pageLoadCondition = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
			}
		};
		String pageId = (String) ((JavascriptExecutor) PG4Case.driver).executeScript(
				"if(document.getElementById('PAGE_ID') != null){"
				+ 	"return document.getElementById('PAGE_ID').value;"
				+ "}else{"
				+ 	"return null;"
				+ "}");
		String pageTitle = "";
		if(pageId != null && !"".equals(pageId) && !pageId.equals(getCurrPageId())) {
			if(pageId != null && !"SELHP".equals(pageId)) {
				pageTitle = (String) ((JavascriptExecutor) PG4Case.driver).executeScript("return document.getElementsByClassName('Heading_1')[0].innerText;");
			}	
			System.out.println(pageId+"\t"+pageTitle);
			setCurrPageId(pageId);
		}else if (getCurrPageId().equals(pageId) && PG4Case.driver.findElement(By.id("errorSpanClient")).isDisplayed()) {
			System.out.println("\t\t"+PG4Case.driver.findElement(By.id("errorSpanClient")).getText());
		}
		WebDriverWait wait = new WebDriverWait(PG4Case.driver, 30);
		wait.until(pageLoadCondition);
		try {
			Thread.sleep(800);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void inputValue(String txt, String xpath) {
		if (txt.contains("'")) {
			PG4Case.driver.findElement(By.xpath(xpath)).sendKeys(txt);
		} else {
			WebElement wb = PG4Case.driver.findElement(By.xpath(xpath));
			JavascriptExecutor jse = (JavascriptExecutor) PG4Case.driver;
			jse.executeScript("arguments[0].value='" + txt + "';", wb);
		}
	}

	public String[] generateSSN() {
		String[] ssn = new String[3];
		int randomNum = ThreadLocalRandom.current().nextInt(100, 899);
		String temp = String.valueOf(randomNum);
		if (temp.charAt(0) == temp.charAt(1) && temp.charAt(1) == temp.charAt(2)) {
			return generateSSN();
		}
		ssn[0] = temp;

		randomNum = ThreadLocalRandom.current().nextInt(10, 99);
		temp = String.valueOf(randomNum);
		ssn[1] = temp;
		randomNum = ThreadLocalRandom.current().nextInt(1000, 9999);
		temp = String.valueOf(randomNum);
		ssn[2] = temp;
		return ssn;
	}

	public String generateString() {
		int leftLimit = 97; // letter 'a'
		int rightLimit = 122; // letter 'z'
		int targetStringLength = 8;
		Random random = new Random();
		int randomLimitedInt;
		StringBuilder buffer = new StringBuilder(targetStringLength);
		for (int i = 0; i < targetStringLength; i++) {
			randomLimitedInt = leftLimit + (int) (random.nextFloat() * (rightLimit - leftLimit + 1));
			buffer.append((char) randomLimitedInt);
		}
		return buffer.toString();
	}

	public static String rightPadding(String str) {
		return String.format("%1$-" + 21 + "s", str);
	}
}
