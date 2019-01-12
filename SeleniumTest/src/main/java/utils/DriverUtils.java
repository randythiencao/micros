package utils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DriverUtils {
	private WebDriver driver;
	private String currPageId;

	public DriverUtils() throws IOException {
		ClassLoader classLoader = DriverUtils.class.getClassLoader();
		URL resource = classLoader.getResource("chromedriver.exe");
		File f = new File("temp");
		if (!f.exists()) {
			f.mkdirs();
		}
		File chromeDriver = new File("temp" + File.separator + "chromedriver.exe");
		if (!chromeDriver.exists()) {
			chromeDriver.createNewFile();
			org.apache.commons.io.FileUtils.copyURLToFile(resource, chromeDriver);
		}
		System.setProperty("webdriver.chrome.driver", chromeDriver.getAbsolutePath());

		ChromeOptions chromeOptions = new ChromeOptions();

		chromeOptions.addArguments("--headless");
		// driver = new ChromeDriver(chromeOptions);
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	}

	public WebDriver getDriver() {
		return this.driver;
	}

	public String getCurrPageId() {
		return this.currPageId;
	}

	public void setCurrPageId(String currPageId) {
		this.currPageId = currPageId;
	}

	public boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	public String getPageId() {
		return (String) ((JavascriptExecutor) driver).executeScript("if(document.getElementById('PAGE_ID') != null){ "
				+ "return document.getElementById('PAGE_ID').value;}" + "else { return '';}");
	}

	public void waitPageLoad() {
		ExpectedCondition<Boolean> pageLoadCondition = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
			}
		};
		WebDriverWait wait = new WebDriverWait(driver, 30);
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
			driver.findElement(By.xpath(xpath)).sendKeys(txt);
		} else {
			WebElement wb = driver.findElement(By.xpath(xpath));
			JavascriptExecutor jse = (JavascriptExecutor)driver;
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
}
