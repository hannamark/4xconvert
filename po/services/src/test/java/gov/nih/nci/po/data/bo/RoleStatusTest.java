package gov.nih.nci.po.data.bo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 *
 * @author gax
 */
public class RoleStatusTest {

    /**
     * This test makes sure that the enum is declared properly. Some compilers might not create the correct code to init
     * this enum.
     */
    @Test
    public void testGetAllowedTransitions() {
        check(RoleStatus.ACTIVE, RoleStatus.ACTIVE, RoleStatus.SUSPENDED, RoleStatus.NULLIFIED);
        check(RoleStatus.PENDING, RoleStatus.PENDING, RoleStatus.ACTIVE, RoleStatus.NULLIFIED);
        check(RoleStatus.SUSPENDED, RoleStatus.SUSPENDED, RoleStatus.ACTIVE, RoleStatus.NULLIFIED);
        check(RoleStatus.NULLIFIED, RoleStatus.NULLIFIED);
    }

    private void check(RoleStatus rs, RoleStatus... allowed) {
        for (RoleStatus x : allowed) {
            assertTrue("missing " + x, rs.getAllowedTransitions().contains(x));
        }
        assertEquals(allowed.length, rs.getAllowedTransitions().size());
        assertFalse(rs.getAllowedTransitions().contains(null));
    }

}
