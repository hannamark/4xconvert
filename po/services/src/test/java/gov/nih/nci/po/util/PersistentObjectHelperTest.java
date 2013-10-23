package gov.nih.nci.po.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import gov.nih.nci.po.data.bo.HealthCareFacility;
import gov.nih.nci.po.data.bo.Organization;

import org.junit.Test;

import com.fiveamsolutions.nci.commons.data.persistent.PersistentObject;

/**
 * @author Hugh Reinhart
 * @since Oct 23, 2013
 */
public class PersistentObjectHelperTest {
    private class TestPersistendObject implements PersistentObject {
        private static final long serialVersionUID = 1L;

        @Override
        public Long getId() {
            return null;
        }
    }

    @Test
    public void hcfTest() {
        HealthCareFacility obj = new HealthCareFacility();
        obj.setPlayer(new Organization());
        HealthCareFacility hcfInitialized = (HealthCareFacility) PersistentObjectHelper.initialize(obj);
        assertNull(hcfInitialized.getName());

        obj.getPlayer().setName("xyzzy");
        hcfInitialized = (HealthCareFacility) PersistentObjectHelper.initialize(obj);
        assertEquals("xyzzy", obj.getName());
    }

    @Test
    public void otherTest() {
        TestPersistendObject obj = new TestPersistendObject();
        assertEquals(obj, PersistentObjectHelper.initialize(obj));
    }
}
