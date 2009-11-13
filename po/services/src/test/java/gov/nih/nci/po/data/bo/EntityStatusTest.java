package gov.nih.nci.po.data.bo;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import gov.nih.nci.po.data.CurationException;

import org.junit.Test;

public class EntityStatusTest {

    @Test
    public void testCanTransitionFromPENDINGTo() {
        EntityStatus cs = EntityStatus.PENDING;
        assertTrue(cs.canTransitionTo(EntityStatus.NULLIFIED));
        assertTrue(cs.canTransitionTo(EntityStatus.ACTIVE));
        assertTrue(cs.canTransitionTo(EntityStatus.PENDING));
        assertFalse(cs.canTransitionTo(EntityStatus.INACTIVE));
    }

    @Test
    public void testCanTransitionFromACTIVETo() {
        EntityStatus cs = EntityStatus.ACTIVE;
        assertTrue(cs.canTransitionTo(EntityStatus.ACTIVE));
        assertTrue(cs.canTransitionTo(EntityStatus.INACTIVE));
        assertTrue(cs.canTransitionTo(EntityStatus.NULLIFIED));
        assertFalse(cs.canTransitionTo(EntityStatus.PENDING));
    }

    @Test
    public void testCanTransitionFromNULLIFIEDTo() {
        EntityStatus cs = EntityStatus.NULLIFIED;
        assertTrue(cs.canTransitionTo(EntityStatus.NULLIFIED));
        assertFalse(cs.canTransitionTo(EntityStatus.ACTIVE));
        assertFalse(cs.canTransitionTo(EntityStatus.INACTIVE));
        assertFalse(cs.canTransitionTo(EntityStatus.PENDING));
    }

    @Test
    public void testCanTransitionFromINACTIVETo() {
        EntityStatus cs = EntityStatus.INACTIVE;
        assertTrue(cs.canTransitionTo(EntityStatus.ACTIVE));
        assertTrue(cs.canTransitionTo(EntityStatus.INACTIVE));
        assertTrue(cs.canTransitionTo(EntityStatus.NULLIFIED));
        assertFalse(cs.canTransitionTo(EntityStatus.PENDING));
    }

    // todo stm Add test to actually test valid transistion statuses

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
