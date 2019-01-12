package unitTests;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.openqa.selenium.WebElement;

import TestSuites.CCPG4;
import utils.DriverUtils;

public class SeleniumTestMain {
	public static DriverUtils driverUtils;
	WebElement element;
	public static final String DEV = "https://ussltcsnw2654.solutions.glbsnet.com:9443/wp/SELoginAccess.jsp";
	public static final String SANDBOX = "https://167.219.19.29/wp/SELoginAccess.jsp?fromIndex=true";
	public static final String UADEV = "https://ussltcsnw2654.solutions.glbsnet.com:9443/wp/SELoginAccess.jsp?fromIndex=true";
	public static final String IIT = "https://dss6amapp16.ct.gov:9446/wp/SELoginAccess.jsp?fromIndex=true";
	public static final String DEV9 = "http://ussltcsnw2654.solutions.glbsnet.com:9085/wp/SELoginAccess.jsp";

	public static void main(String[] args) {
		try {
			System.setProperty("children", "3");
			System.setProperty("parents", "1");

			driverUtils = new DriverUtils();
			driverUtils.getDriver().get(DEV);
			JUnitCore junit = new JUnitCore();
			//junit.addListener(new TextListener(System.out));
			Result result = junit.run(CCPG4.class);
			resultReport(result);

			driverUtils.getDriver().close();
			Runtime.getRuntime().exec("taskkill /F /IM chromedriver.exe");
		} catch (Exception e) {

		}
	}

	public static void resultReport(Result result) {
		Date date = new Date(result.getRunTime());
		DateFormat formatter = new SimpleDateFormat("HH:mm:ss.SSS");
		formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
		String dateFormatted = formatter.format(date);
		
		System.out.println("Success: "+result.wasSuccessful()+
				"\nRuns: "+result.getRunCount() + 
				"\nFails: "+result.getFailureCount() +
				"\nCompleted in: "+dateFormatted);
	}
}
