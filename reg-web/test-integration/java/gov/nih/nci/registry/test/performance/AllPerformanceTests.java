package gov.nih.nci.registry.test.performance;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * 
 * Class to control the order that selenium tests are run in.
 * 
 * @author dkrylov
 */
@RunWith(Suite.class)
@SuiteClasses(value = { TrialSearchPerformanceTest.class,
        UpdateAndAmendPerformanceTest.class })
public class AllPerformanceTests {

}
