/**
 * 
 */
package gov.nih.nci.pa.enums;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
/**
 * @author hreinhart
 *
 */
public class StudyStatusCodeTest {
    /**
     * Test canTransitionTo() method
     */
    @Test
    public void testCanTransitionTo() {
        StudyStatusCode cs;
        
        assertFalse(StudyStatusCode.ACTIVE.canTransitionTo(null));
        
        cs = StudyStatusCode.ACTIVE;
        assertFalse(cs.canTransitionTo(StudyStatusCode.ACTIVE));
        assertTrue(cs.canTransitionTo(StudyStatusCode.ADMINISTRATIVELY_COMPLETE));
        assertFalse(cs.canTransitionTo(StudyStatusCode.APPROVED));
        assertTrue(cs.canTransitionTo(StudyStatusCode.CLOSED_TO_ACCRUAL));
        assertFalse(cs.canTransitionTo(StudyStatusCode.CLOSED_TO_ACCRUAL_AND_INTERVENTION));
        assertFalse(cs.canTransitionTo(StudyStatusCode.COMPLETE));
        assertTrue(cs.canTransitionTo(StudyStatusCode.TEMPORARILY_CLOSED_TO_ACCRUAL));
        assertTrue(cs.canTransitionTo(StudyStatusCode.TEMPORARILY_CLOSED_TO_ACCRUAL_AND_INTERVENTION));
        assertFalse(cs.canTransitionTo(StudyStatusCode.WITHDRAWN));
        
        cs = StudyStatusCode.ADMINISTRATIVELY_COMPLETE;
        assertFalse(cs.canTransitionTo(StudyStatusCode.ACTIVE));
        assertFalse(cs.canTransitionTo(StudyStatusCode.ADMINISTRATIVELY_COMPLETE));
        assertFalse(cs.canTransitionTo(StudyStatusCode.APPROVED));
        assertFalse(cs.canTransitionTo(StudyStatusCode.CLOSED_TO_ACCRUAL));
        assertFalse(cs.canTransitionTo(StudyStatusCode.CLOSED_TO_ACCRUAL_AND_INTERVENTION));
        assertFalse(cs.canTransitionTo(StudyStatusCode.COMPLETE));
        assertFalse(cs.canTransitionTo(StudyStatusCode.TEMPORARILY_CLOSED_TO_ACCRUAL));
        assertFalse(cs.canTransitionTo(StudyStatusCode.TEMPORARILY_CLOSED_TO_ACCRUAL_AND_INTERVENTION));
        assertFalse(cs.canTransitionTo(StudyStatusCode.WITHDRAWN));
        
        cs = StudyStatusCode.APPROVED;
        assertTrue(cs.canTransitionTo(StudyStatusCode.ACTIVE));
        assertFalse(cs.canTransitionTo(StudyStatusCode.ADMINISTRATIVELY_COMPLETE));
        assertFalse(cs.canTransitionTo(StudyStatusCode.APPROVED));
        assertFalse(cs.canTransitionTo(StudyStatusCode.CLOSED_TO_ACCRUAL));
        assertFalse(cs.canTransitionTo(StudyStatusCode.CLOSED_TO_ACCRUAL_AND_INTERVENTION));
        assertFalse(cs.canTransitionTo(StudyStatusCode.COMPLETE));
        assertFalse(cs.canTransitionTo(StudyStatusCode.TEMPORARILY_CLOSED_TO_ACCRUAL));
        assertFalse(cs.canTransitionTo(StudyStatusCode.TEMPORARILY_CLOSED_TO_ACCRUAL_AND_INTERVENTION));
        assertTrue(cs.canTransitionTo(StudyStatusCode.WITHDRAWN));
        
        cs = StudyStatusCode.CLOSED_TO_ACCRUAL;
        assertFalse(cs.canTransitionTo(StudyStatusCode.ACTIVE));
        assertTrue(cs.canTransitionTo(StudyStatusCode.ADMINISTRATIVELY_COMPLETE));
        assertFalse(cs.canTransitionTo(StudyStatusCode.APPROVED));
        assertFalse(cs.canTransitionTo(StudyStatusCode.CLOSED_TO_ACCRUAL));
        assertTrue(cs.canTransitionTo(StudyStatusCode.CLOSED_TO_ACCRUAL_AND_INTERVENTION));
        assertFalse(cs.canTransitionTo(StudyStatusCode.COMPLETE));
        assertFalse(cs.canTransitionTo(StudyStatusCode.TEMPORARILY_CLOSED_TO_ACCRUAL));
        assertFalse(cs.canTransitionTo(StudyStatusCode.TEMPORARILY_CLOSED_TO_ACCRUAL_AND_INTERVENTION));
        assertFalse(cs.canTransitionTo(StudyStatusCode.WITHDRAWN));
        
        cs = StudyStatusCode.CLOSED_TO_ACCRUAL_AND_INTERVENTION;
        assertFalse(cs.canTransitionTo(StudyStatusCode.ACTIVE));
        assertTrue(cs.canTransitionTo(StudyStatusCode.ADMINISTRATIVELY_COMPLETE));
        assertFalse(cs.canTransitionTo(StudyStatusCode.APPROVED));
        assertFalse(cs.canTransitionTo(StudyStatusCode.CLOSED_TO_ACCRUAL));
        assertFalse(cs.canTransitionTo(StudyStatusCode.CLOSED_TO_ACCRUAL_AND_INTERVENTION));
        assertTrue(cs.canTransitionTo(StudyStatusCode.COMPLETE));
        assertFalse(cs.canTransitionTo(StudyStatusCode.TEMPORARILY_CLOSED_TO_ACCRUAL));
        assertFalse(cs.canTransitionTo(StudyStatusCode.TEMPORARILY_CLOSED_TO_ACCRUAL_AND_INTERVENTION));
        assertFalse(cs.canTransitionTo(StudyStatusCode.WITHDRAWN));
        
        cs = StudyStatusCode.COMPLETE;
        assertFalse(cs.canTransitionTo(StudyStatusCode.ACTIVE));
        assertFalse(cs.canTransitionTo(StudyStatusCode.ADMINISTRATIVELY_COMPLETE));
        assertFalse(cs.canTransitionTo(StudyStatusCode.APPROVED));
        assertFalse(cs.canTransitionTo(StudyStatusCode.CLOSED_TO_ACCRUAL));
        assertFalse(cs.canTransitionTo(StudyStatusCode.CLOSED_TO_ACCRUAL_AND_INTERVENTION));
        assertFalse(cs.canTransitionTo(StudyStatusCode.COMPLETE));
        assertFalse(cs.canTransitionTo(StudyStatusCode.TEMPORARILY_CLOSED_TO_ACCRUAL));
        assertFalse(cs.canTransitionTo(StudyStatusCode.TEMPORARILY_CLOSED_TO_ACCRUAL_AND_INTERVENTION));
        assertFalse(cs.canTransitionTo(StudyStatusCode.WITHDRAWN));
        
        cs = StudyStatusCode.TEMPORARILY_CLOSED_TO_ACCRUAL;
        assertTrue(cs.canTransitionTo(StudyStatusCode.ACTIVE));
        assertTrue(cs.canTransitionTo(StudyStatusCode.ADMINISTRATIVELY_COMPLETE));
        assertFalse(cs.canTransitionTo(StudyStatusCode.APPROVED));
        assertFalse(cs.canTransitionTo(StudyStatusCode.CLOSED_TO_ACCRUAL));
        assertFalse(cs.canTransitionTo(StudyStatusCode.CLOSED_TO_ACCRUAL_AND_INTERVENTION));
        assertFalse(cs.canTransitionTo(StudyStatusCode.COMPLETE));
        assertFalse(cs.canTransitionTo(StudyStatusCode.TEMPORARILY_CLOSED_TO_ACCRUAL));
        assertTrue(cs.canTransitionTo(StudyStatusCode.TEMPORARILY_CLOSED_TO_ACCRUAL_AND_INTERVENTION));
        assertFalse(cs.canTransitionTo(StudyStatusCode.WITHDRAWN));
        
        cs = StudyStatusCode.CLOSED_TO_ACCRUAL_AND_INTERVENTION;
        assertFalse(cs.canTransitionTo(StudyStatusCode.ACTIVE));
        assertTrue(cs.canTransitionTo(StudyStatusCode.ADMINISTRATIVELY_COMPLETE));
        assertFalse(cs.canTransitionTo(StudyStatusCode.APPROVED));
        assertFalse(cs.canTransitionTo(StudyStatusCode.CLOSED_TO_ACCRUAL));
        assertFalse(cs.canTransitionTo(StudyStatusCode.CLOSED_TO_ACCRUAL_AND_INTERVENTION));
        assertTrue(cs.canTransitionTo(StudyStatusCode.COMPLETE));
        assertFalse(cs.canTransitionTo(StudyStatusCode.TEMPORARILY_CLOSED_TO_ACCRUAL));
        assertFalse(cs.canTransitionTo(StudyStatusCode.TEMPORARILY_CLOSED_TO_ACCRUAL_AND_INTERVENTION));
        assertFalse(cs.canTransitionTo(StudyStatusCode.WITHDRAWN));
        
        cs = StudyStatusCode.WITHDRAWN;
        assertFalse(cs.canTransitionTo(StudyStatusCode.ACTIVE));
        assertFalse(cs.canTransitionTo(StudyStatusCode.ADMINISTRATIVELY_COMPLETE));
        assertFalse(cs.canTransitionTo(StudyStatusCode.APPROVED));
        assertFalse(cs.canTransitionTo(StudyStatusCode.CLOSED_TO_ACCRUAL));
        assertFalse(cs.canTransitionTo(StudyStatusCode.CLOSED_TO_ACCRUAL_AND_INTERVENTION));
        assertFalse(cs.canTransitionTo(StudyStatusCode.COMPLETE));
        assertFalse(cs.canTransitionTo(StudyStatusCode.TEMPORARILY_CLOSED_TO_ACCRUAL));
        assertFalse(cs.canTransitionTo(StudyStatusCode.TEMPORARILY_CLOSED_TO_ACCRUAL_AND_INTERVENTION));
        assertFalse(cs.canTransitionTo(StudyStatusCode.WITHDRAWN));
    }

}
