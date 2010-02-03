
package gov.nih.nci.po.data.convert;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.Tel;
import gov.nih.nci.iso21090.TelEmail;
import gov.nih.nci.iso21090.TelPhone;
import gov.nih.nci.iso21090.TelUrl;
import gov.nih.nci.po.data.bo.Email;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.data.bo.Person;
import gov.nih.nci.po.data.bo.PhoneNumber;
import gov.nih.nci.po.data.bo.URL;

import org.junit.Test;

/**
 *
 * @author gax
 */
public class ContactListConverterTest {
    @Test
    public void testConvertToDSet_Organization() {
        Organization org = new Organization();
        org.getEmail().add(new Email("fooemail"));
        org.getFax().add(new PhoneNumber("foofax"));
        org.getPhone().add(new PhoneNumber("foophone"));
        org.getUrl().add(new URL("file:foourl"));
        org.getTty().add(new PhoneNumber("footty"));

        DSet<Tel> result = ContactListConverter.convertToDSet(org);
        check(result);
    }
    
    @Test
    public void testConvertToDSet_Person() {
        Person per = new Person();
        per.getEmail().add(new Email("fooemail"));
        per.getFax().add(new PhoneNumber("foofax"));
        per.getPhone().add(new PhoneNumber("foophone"));
        per.getUrl().add(new URL("file:foourl"));
        per.getTty().add(new PhoneNumber("footty"));

        DSet<Tel> result = ContactListConverter.convertToDSet(per);
        check(result);

    }

    private void check(DSet<Tel> result) {
        assertEquals(5, result.getItem().size());
        for (Tel t : result.getItem()) {
            String v = t.getValue().toString();
            if (v.equals(TelEmail.SCHEME_MAILTO + ":fooemail")) {
                continue;
            }
            if (v.equals(TelPhone.SCHEME_X_TEXT_FAX + ":foofax")) {
                continue;
            }
            if (v.equals(TelPhone.SCHEME_TEL + ":foophone")) {
                continue;
            }
            if (v.equals(TelUrl.SCHEME_FILE + ":foourl")) {
                continue;
            }
            if (v.equals(TelPhone.SCHEME_X_TEXT_TEL + ":footty")) {
                continue;
            }
            fail(v);
        }
    }

}