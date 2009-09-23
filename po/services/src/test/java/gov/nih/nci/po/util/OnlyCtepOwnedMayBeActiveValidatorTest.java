package gov.nih.nci.po.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.po.data.bo.HealthCareFacility;
import gov.nih.nci.po.data.bo.RoleStatus;

import org.junit.Test;


public class OnlyCtepOwnedMayBeActiveValidatorTest {
    OnlyCtepOwnedMayBeActiveValidator validator = new OnlyCtepOwnedMayBeActiveValidator();

    
    @Test
    public void ctepOwnedAndActiveIsValid() {
        HealthCareFacility o = new HealthCareFacility() {
            @Override
            public boolean isCtepOwned() {
                return true;
            }
        };
        assertTrue(validator.isValid(o));
        o.setStatus(RoleStatus.PENDING);
        assertTrue(validator.isValid(o));
        o.setStatus(RoleStatus.ACTIVE);
        assertTrue(validator.isValid(o));
        o.setStatus(RoleStatus.SUSPENDED);
        assertTrue(validator.isValid(o));
        o.setStatus(RoleStatus.NULLIFIED);
        assertTrue(validator.isValid(o));
    }
    @Test
    public void notCtepOwnedAndActiveIsInValid() {
        HealthCareFacility o = new HealthCareFacility() {
            @Override
            public boolean isCtepOwned() {
                return false;
            }
        };
        assertTrue(validator.isValid(o));
        o.setStatus(RoleStatus.PENDING);
        assertTrue(validator.isValid(o));
        o.setStatus(RoleStatus.ACTIVE);
        assertFalse(validator.isValid(o));
        o.setStatus(RoleStatus.SUSPENDED);
        assertTrue(validator.isValid(o));
        o.setStatus(RoleStatus.NULLIFIED);
        assertTrue(validator.isValid(o));
    }
    
}
