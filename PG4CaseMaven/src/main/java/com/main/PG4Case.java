package com.main;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;

import com.utils.AR;
import com.utils.DC;
import com.utils.EN;
import com.utils.VM;

public class PG4Case {
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
	public static long startTime = System.currentTimeMillis();
	public static boolean generateCert = true;

	public static void main(String[] args) throws InterruptedException, IOException {
		int count = 0;
		int cases = 0;
		int maxFails = 3;
		int maxCases = 1;
		boolean headless = false;

		String username = "impccadmin";
		String url = DEV9;
		int numOfChildren = 3;
		int numOfParents = 1;
		
		for (String arg : args) {
			if ("-headless".equalsIgnoreCase(arg)) {
				headless = true;
				System.out.println("Headless mode will turn off Generating Certificates in EN");
			}
			if ("-children".equals(arg)) {
				numOfChildren = Integer.parseInt(args[Arrays.asList(args).indexOf(arg) + 1]);
				if (numOfChildren > 19) {
					numOfChildren = 19;
					System.out.println("Only support maximum 19 children. Setting 19 as default.");
				}
			}
			if ("-parents".equals(arg)) {
				numOfParents = Integer.parseInt(args[Arrays.asList(args).indexOf(arg) + 1]);
				if (numOfParents > 2) {
					numOfParents = 2;
					System.out.println("Only support maximum 2 parents. Setting 2 as default.");
				}
			}
			if ("-url".equals(arg)) {
				url = args[Arrays.asList(args).indexOf(arg) + 1];
			}
			if ("-maxFails".equals(arg)) {
				maxFails = Integer.parseInt(args[Arrays.asList(args).indexOf(arg) + 1]);
			}
			if ("-generateCert".equals(arg)) {
				generateCert = Boolean.parseBoolean(args[Arrays.asList(args).indexOf(arg) + 1]);
			}
			if ("-cases".equals(arg)) {
				maxCases = Integer.parseInt(args[Arrays.asList(args).indexOf(arg) + 1]);
			}
		}
		
		ClassLoader classLoader = PG4Case.class.getClassLoader();
		URL resource = classLoader.getResource("drivers/chromedriver.exe");
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

		if (headless) {
			chromeOptions.addArguments("--headless", "--disable-gpu", "--ignore-certificate-errors");
			generateCert = false;
		} else {
			chromeOptions.addArguments("--disable-gpu", "--ignore-certificate-errors");
		}
		chromeOptions.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		chromeOptions.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);

		driver = new ChromeDriver(chromeOptions);
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		

		do {
			try {
				startDriver(headless, numOfParents, numOfChildren, url);
				cases += 1;
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Failed to complete. Failed: " + ++count + "(max " + maxFails + ")");
				if (count == maxFails)
					throw e;
				driver.close();
				driver.quit();

			} finally {
				System.out.println("----------------Summary.");
				System.out.println(String.format("%1$-" + 21 + "s", "Case Number:") + " " + CASENUM);
				for (String a : INDVS) {
					System.out.println(a);
				}
				System.out.println(String.format("%1$-" + 21 + "s", "Vendor:") + " " + VENDOR);

				// Clean up
				Runtime.getRuntime().exec("taskkill /F /IM chromedriver.exe");
				FileUtils.deleteDirectory(new File("temp"));
				long endTime = System.currentTimeMillis();
				
				AR.parentsNameList.clear();
				AR.childrenNameList.clear();
				AR.childrenIdList.clear();
				AR.parentsIdList.clear();
				INDVS.clear();
				
				System.out.println(
						String.format("%1$-" + 21 + "s", "Time(seconds):") + " " + (endTime - startTime) / 1000);
				if (count == maxFails)
					break;
			}
		} while (count <= maxCases);
	}

	/**
	 * Start WebDriver
	 * 
	 * @param headless
	 * @param numOfParents
	 * @param numOfChildren
	 * @param url
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void startDriver(boolean headless, int numOfParents, int numOfChildren, String url)
			throws IOException, InterruptedException {
		// create temp driver
		
		AR arDriver = new AR();
		DC dcDriver = new DC();
		EN enDriver = new EN();
		VM vmDriver = new VM();
		driver.get(url);

		System.out.println("Parents: " + numOfParents);
		System.out.println("Children: " + numOfChildren);
		// Login
		WebElement element = driver.findElement(By.xpath("//*[@id=\"main\"]/div[4]/div[3]/div[1]/input"));
		element.sendKeys("impccadmin");

		element = driver.findElement(By.xpath("//*[@id=\"main\"]/div[4]/div[3]/div[2]/input"));
		element.sendKeys("123");

		driver.findElement(By.xpath("//*[@id=\"loginButtons\"]/input")).click();

		// Time Travel
		/*
		 * YEAR = "2018"; DAY = "05"; MONTH = "12";
		 * driver.findElement(By.xpath("//*[@id=\"monthtimeTraveldate\"]")).sendKeys(
		 * MONTH);
		 * driver.findElement(By.xpath("//*[@id=\"datetimeTraveldate\"]")).sendKeys(DAY)
		 * ;
		 * driver.findElement(By.xpath("//*[@id=\"yeartimeTraveldate\"]")).sendKeys(YEAR
		 * ); driver.findElement(By.xpath(
		 * "//*[@id=\"MainHeader\"]/table[2]/tbody/tr/td/table/tbody/tr/td/table/tbody/tr[7]/td/table/tbody/tr[2]/td[2]/input[5]"
		 * )).click();
		 */

		arDriver.startAR(numOfParents, numOfChildren);
		dcDriver.startDC(numOfParents, numOfChildren);
		vmDriver.startVM();
		// VENDOR = "250000001";
		enDriver.startEN();
		System.out.println("----------------Completed.");
		driver.close();
		driver.quit();
	}
}
