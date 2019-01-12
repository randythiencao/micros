package junit.utils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
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
		return (String) ((JavascriptExecutor) driver).executeScript("return document.getElementById('PAGE_ID').value;");
	}
	
	public void waitPageLoad() {
		ExpectedCondition<Boolean> pageLoadCondition = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
			}
		};
		String pageId = (String) ((JavascriptExecutor) driver).executeScript("return document.getElementById('PAGE_ID').value;");
		String pageTitle = "";
		if(pageId != null && !"".equals(pageId) && !pageId.equals(getCurrPageId())) {
			if(pageId != null && !"SELHP".equals(pageId)) {
				pageTitle = (String) ((JavascriptExecutor) driver).executeScript("return document.getElementsByClassName('Heading_1')[0].innerText;");
			}	
			System.out.println(pageId+"\t"+pageTitle);
			setCurrPageId(pageId);
		}else if (pageId.equals(getCurrPageId()) && driver.findElement(By.id("errorSpanClient")).isDisplayed()) {
			System.out.println("\t\t"+driver.findElement(By.id("errorSpanClient")).getText());
		}
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(pageLoadCondition);
		try {
			Thread.sleep(800);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
