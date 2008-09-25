package gov.nih.nci.po.data.bo;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author gax
 */
public class RoleStatusTest {

    /**
     * This test makes sure that the emun is declared properly.
     * some compilers might not create the correct code to init this enum.
     */
    @Test
    public void testGetAllowedTransitions() {
        check(RoleStatus.ACTIVE, RoleStatus.SUSPENDED, RoleStatus.NULLIFIED);
        check(RoleStatus.PENDING, RoleStatus.ACTIVE, RoleStatus.NULLIFIED);
        check(RoleStatus.SUSPENDED, RoleStatus.ACTIVE, RoleStatus.NULLIFIED);
        check(RoleStatus.NULLIFIED);
    }
    
    private void check(RoleStatus rs, RoleStatus ... allowed) {
        for (RoleStatus x:  allowed) {
            assertTrue("missing "+x, rs.getAllowedTransitions().contains(x));
        }
        assertEquals(allowed.length, rs.getAllowedTransitions().size());
        assertFalse(rs.getAllowedTransitions().contains(null));
    }
    
    

}