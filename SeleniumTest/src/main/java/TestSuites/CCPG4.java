package TestSuites;

import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;

import testCases.ARTest01RegisterAppliction;
import testCases.ARTest02Address;
import testCases.ARTest03RegisterApplicationType;
import testCases.ARTest04RegisterIndividualIndividual;
import testCases.ARTest05FileClearanceResults;
import testCases.ARTest06FRegisterIndividualSummary;
import testCases.LoginTest;
import utils.StopOnFirstFailureSuite;

@RunWith(StopOnFirstFailureSuite.class)
@SuiteClasses({
	LoginTest.class,
	ARTest01RegisterAppliction.class,
	ARTest02Address.class,
	ARTest03RegisterApplicationType.class,
	ARTest04RegisterIndividualIndividual.class,
	ARTest05FileClearanceResults.class,
	ARTest06FRegisterIndividualSummary.class
})





public class CCPG4 {

}
