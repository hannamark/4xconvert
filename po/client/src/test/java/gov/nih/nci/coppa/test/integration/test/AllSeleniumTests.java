package gov.nih.nci.coppa.test.integration.test;

import org.junit.runner.RunWith;
import org.junit.runners.AllTests;
import org.openqa.jtc.junit.TestSuiteBuilder;

@RunWith(AllTests.class)
public class AllSeleniumTests {
    public static junit.framework.Test suite(){
      return TestSuiteBuilder.suite(AbstractSeleneseTestCase.class);
    }
  }
