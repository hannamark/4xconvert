package gov.nih.nci.po.data.bo;


import gov.nih.nci.po.data.CurationException;
import gov.nih.nci.po.data.common.CurationStatus;

import org.junit.Test;
import static org.junit.Assert.*;


public class CurationStatusTest {

    /**
     * Test canTransitionTo() method
     */
    @Test
    public void testCanTransitionTo() {
        CurationStatus cs = CurationStatus.NEW;
        assertTrue(cs.canTransitionTo(CurationStatus.REJECTED));
        assertFalse(cs.canTransitionTo(CurationStatus.DEPRECATED));
        cs = CurationStatus.CURATED;
        assertFalse(cs.canTransitionTo(CurationStatus.REJECTED));
        assertTrue(cs.canTransitionTo(CurationStatus.DEPRECATED));
    }

   /**
     * Test Curation status
     */
    @Test
    public void testCurationStatus() {
        CurationStatus cs = CurationStatus.CURATED;
        cs.isSubmittable();

    }

    /**
     * Test Curation Exceptions
     */
    @Test
    public void testCurationException() {
        try {
            curationExceptionThrown();
            fail();
        } catch (CurationException e) {
            // expected
        }

        try {
            curationExceptionThrownWithMessage();
            fail();
        } catch (CurationException e) {
            // expected
            
        }

        try {
            curationExceptionThrownInWrapperWithMessage();
            fail();
        } catch (CurationException e) {
            // expected
        }

        try {
            curationExceptionThrownInWrapper();
            fail();
        } catch (CurationException e) {
            assertTrue(e.getCause() instanceof NullPointerException);
        }
    }

    private void curationExceptionThrown() throws CurationException {
        if (true) {
            throw new CurationException();
        }
    }

    private void curationExceptionThrownWithMessage() throws CurationException {
        if (true) {
            throw new CurationException("Message");
        }
    }

    private void curationExceptionThrownInWrapperWithMessage() throws CurationException {

        try {
            throw new NullPointerException();
        } catch (Exception e) {
            throw new CurationException("message", e);
        }
    }

    private void curationExceptionThrownInWrapper() throws CurationException {

        try {
            throw new NullPointerException();
        } catch (Exception e) {
            throw new CurationException(e);
        }
    }
}
