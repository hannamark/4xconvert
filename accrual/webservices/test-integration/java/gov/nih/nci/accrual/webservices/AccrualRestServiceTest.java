/**
 * 
 */
package gov.nih.nci.accrual.webservices;

import gov.nih.nci.pa.test.integration.util.TestProperties;
import gov.nih.nci.pa.webservices.test.integration.AbstractRestServiceTest;

import org.junit.Test;

/**
 * @author dkrylov
 * 
 */
public class AccrualRestServiceTest extends AbstractRestServiceTest {

    /**
     * @throws java.lang.Exception
     */
    @Override
    public void setUp() throws Exception {
        super.setUp("/sites/1");
        baseURL = "http://" + TestProperties.getServerHostname() + ":"
                + TestProperties.getServerPort() + "/accrual-services";
        deactivateAllTrials();
    }

    /**
     * Test method for
     * {@link gov.nih.nci.accrual.webservices.AccrualService#submitStudySubjects(long, gov.nih.nci.accrual.webservices.types.StudySubjects)}
     * .
     */
    @Test
    public final void testSubmitStudySubjects() {       
    }

}
