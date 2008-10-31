package gov.nih.nci.coppa.test;

import gov.nih.nci.coppa.test.integration.TopicIntegrationTest;
import gov.nih.nci.coppa.test.integration.test.AllSeleniumTests;
import gov.nih.nci.coppa.test.remoteapi.AllApiTests;
import gov.nih.nci.coppa.test.remoteapi.AllApiTests.VerifyAllApiTestsSuite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses(value = { VerifyAllApiTestsSuite.class, AllSeleniumTests.class, AllApiTests.class, DataGeneratorUtilTest.class, TopicIntegrationTest.class })
public class AllTests {

}