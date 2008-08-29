package gov.nih.nci.po.data.bo;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import gov.nih.nci.po.data.CurationException;

import org.junit.Test;


public class EntityStatusTest {

    /**
     * Test canTransitionTo() method
     */
    @Test
    public void testCanTransitionTo() {
        EntityStatus cs = EntityStatus.NEW;
        assertTrue(cs.canTransitionTo(EntityStatus.REJECTED));
        assertFalse(cs.canTransitionTo(EntityStatus.DEPRECATED));
        cs = EntityStatus.CURATED;
        assertFalse(cs.canTransitionTo(EntityStatus.REJECTED));
        assertTrue(cs.canTransitionTo(EntityStatus.DEPRECATED));
    }

    // todo stm  Add test to actually test valid transistion statuses

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
