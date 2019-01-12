package junit.cases;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import junit.utils.DriverUtils;

public class PG4Test {

	public static final String DEV = "https://ussltcsnw2654.solutions.glbsnet.com:9443/wp/SELoginAccess.jsp";
	public static final String SANDBOX = "https://167.219.19.29/wp/SELoginAccess.jsp?fromIndex=true";
	public static final String UADEV = "https://ussltcsnw2654.solutions.glbsnet.com:9443/wp/SELoginAccess.jsp?fromIndex=true";
	public static final String IIT = "https://dss6amapp16.ct.gov:9446/wp/SELoginAccess.jsp?fromIndex=true";
	public static final String DEV9 = "http://ussltcsnw2654.solutions.glbsnet.com:9085/wp/SELoginAccess.jsp";

	public static WebDriver driver;
	public static String YEAR;
	public static String MONTH;
	public static String DAY;
	public static String CASENUM;
	public static String VENDOR;
	public static String currPageId;
	public static List<String> INDVS = new ArrayList<>();
	WebElement element;

	private static DriverUtils driverUtils;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		 driver = new ChromeDriver();
         driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		// driverUtils.getDriver().close();
	}

	@Test
	public void login() {
		driver.get("http://www.store.demoqa.com");
		driver.findElement(By.xpath(".//*[@id='account']/a")).click();
		driver.findElement(By.id("log")).sendKeys("testuser_3");
		driver.findElement(By.id("pwd")).sendKeys("Test@123");
		driver.findElement(By.id("login")).click();
		try {
			element = driver.findElement(By.xpath(".//*[@id='account_logout']/a"));
		} catch (Exception e) {
		}
		Assert.assertNotNull(element);
		System.out.println("Ending test " + new Object() {
		}.getClass().getEnclosingMethod().getName());

	}

}
