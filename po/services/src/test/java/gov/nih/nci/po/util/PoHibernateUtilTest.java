package gov.nih.nci.po.util;

import static org.junit.Assert.assertEquals;
import gov.nih.nci.po.data.bo.Address;
import gov.nih.nci.po.data.bo.Organization;

import java.util.Map;

import org.junit.Test;

/**
 *
 * @author gax
 */
public class PoHibernateUtilTest {

    @Test
    public void testValidate() {

        Address a = new Address();
        a.setStreetAddressLine("dummy street");
        Organization org = new Organization();
        Map<String, String[]> result = PoHibernateUtil.validate(org);
        assertEquals(3, result.size());
        assertEquals("must be set", result.get("postalAddress")[0]);
        assertEquals("must be set", result.get("name")[0]);
        assertEquals("must be set", result.get("curationStatus")[0]);
    }
}