package gov.nih.nci.po.util;

import gov.nih.nci.po.data.bo.Address;
import gov.nih.nci.po.data.bo.ContactInfo;
import java.util.Map;
import org.junit.Test;
import static org.junit.Assert.*;

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
//        for (Map.Entry<String, String[]> e : result.entrySet()) {
//            System.out.print("k=" + e.getKey());
//            for (String m : e.getValue()) {
//                System.out.print(" v=" + m);
//            }
//            System.out.println();
//        }
        assertEquals(3, result.size());
        assertEquals("must be set", result.get("mailingAddress.postalCode")[0]);
        assertEquals("must be set", result.get("mailingAddress.country")[0]);
        assertEquals("must be set", result.get("mailingAddress.cityOrMunicipality")[0]);
    }

}