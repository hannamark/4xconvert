package gov.nih.nci.registry.test.performance;

import gov.nih.nci.pa.test.integration.support.BatchingTestSuite;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;

/**
 * 
 * Class to control the order that selenium tests are run in.
 * 
 * @author dkrylov
 */
@RunWith(BatchingTestSuite.class)
@SuiteClasses(value = { UpdateAndAmendPerformanceTest.class,
        TrialSearchPerformanceTest.class })
public class AllPerformanceTests {

}
