package gov.nih.nci.po.util;

import static org.junit.Assert.assertEquals;
import gov.nih.nci.po.data.bo.Address;
import gov.nih.nci.po.data.bo.ContactInfo;

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
        ContactInfo ci = new ContactInfo(a);
        Map<String, String[]> result = PoHibernateUtil.validate(ci);
        assertEquals(3, result.size());
        assertEquals("must be set", result.get("mailingAddress.postalCode")[0]);
        assertEquals("must be set", result.get("mailingAddress.country")[0]);
        assertEquals("must be set", result.get("mailingAddress.cityOrMunicipality")[0]);
    }

}