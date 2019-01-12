package utils;

import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;
import org.junit.runner.notification.RunNotifier;

/**
 * @author Raphael
 *
 */
public class FailFastListener extends RunListener {

	private RunNotifier runNotifier;

	/**
	 * Allow this Listener to access runNotifier
	 */
	public FailFastListener(RunNotifier runNotifier) {
		super();
		this.runNotifier = runNotifier;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.junit.runner.notification.RunListener#testFailure(org.junit.runner.
	 * notification.Failure)
	 */
	@Override
	public void testFailure(Failure failure) throws Exception {
		System.out.println(failure.getTestHeader()+"\t"+failure.getMessage());
		this.runNotifier.pleaseStop();
		Runtime.getRuntime().exec("taskkill /F /IM chromedriver.exe");
	}
}