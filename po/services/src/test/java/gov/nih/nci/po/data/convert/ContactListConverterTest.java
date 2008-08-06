
package gov.nih.nci.po.data.convert;

import gov.nih.nci.coppa.iso.DSet;
import gov.nih.nci.coppa.iso.Tel;
import gov.nih.nci.coppa.iso.TelEmail;
import gov.nih.nci.coppa.iso.TelPhone;
import gov.nih.nci.coppa.iso.TelUrl;
import gov.nih.nci.po.data.bo.Email;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.data.bo.PhoneNumber;
import gov.nih.nci.po.data.bo.URL;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author gax
 */
public class ContactListConverterTest {
    @Test
    public void testConvertToDSet_Organization() {
        System.out.println("convertToDSet");
        Organization org = new Organization();
        org.getEmail().add(new Email("fooemail"));
        org.getFax().add(new PhoneNumber("foofax"));
        org.getPhone().add(new PhoneNumber("foophone"));
        org.getUrl().add(new URL("file:foourl"));
        org.getTty().add(new PhoneNumber("footty"));
        
        DSet<? extends Tel> result = ContactListConverter.convertToDSet(org);
        assertEquals(5, result.getItem().size());
        for(Tel t : result.getItem()){
            String v = t.getValue().toString();
            if(v.equals(TelEmail.SCHEME_MAILTO + ":fooemail")) continue;
            if(v.equals(TelPhone.SCHEME_X_TEXT_FAX + ":foofax")) continue;
            if(v.equals(TelPhone.SCHEME_TEL + ":foophone")) continue;
            if(v.equals(TelUrl.SCHEME_FILE + ":foourl")) continue;
            if(v.equals(TelPhone.SCHEME_X_TEXT_TEL + ":footty")) continue;
            fail(v);
        }
        
    }

    
}