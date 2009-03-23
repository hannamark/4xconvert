package gov.nih.nci.po.util;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import gov.nih.nci.po.data.bo.Email;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.data.bo.Person;
import gov.nih.nci.po.data.bo.PhoneNumber;
import gov.nih.nci.po.data.bo.URL;
import gov.nih.nci.po.service.AbstractBeanTest;
import gov.nih.nci.po.service.OrganizationCRServiceBeanTest;
import gov.nih.nci.po.service.PersonCRServiceBeanTest;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.person.PersonDTO;

import java.lang.reflect.Field;
import java.util.Collection;

import net.sf.xsnapshot.SnapshotHelper;

import org.junit.Test;

/**
 *
 * @author gax
 */
public class PoXsnapshotHelperTest extends AbstractBeanTest {

    @Test
    public void roundTripOrganization() {
        Organization org = new Organization();
        OrganizationCRServiceBeanTest.fill(org, getDefaultCountry());
        org.getPostalAddress().setDeliveryAddressLine("deliveryAddressLine");
        org.getEmail().add(new Email("222@example.com"));
        org.getFax().add(new PhoneNumber("123-123-1234"));
        org.getPhone().add(new PhoneNumber("123-123-1234"));
        org.getTty().add(new PhoneNumber("123-123-1234"));
        org.getUrl().add(new URL("http://example.com/"));

        OrganizationDTO dto = (OrganizationDTO) PoXsnapshotHelper.createSnapshot(org);
        Organization clone = (Organization) PoXsnapshotHelper.createModel(dto);

        EqualsByValue.assertEquals(org, clone);
    }

    @Test
    public void roundTripPerson() {
        Person per = new Person();
        PersonCRServiceBeanTest.fill(per, getDefaultCountry());
        per.getPostalAddress().setDeliveryAddressLine("deliveryAddressLine");
        per.getEmail().add(new Email("222@example.com"));
        per.getFax().add(new PhoneNumber("123-123-1235"));
        per.getPhone().add(new PhoneNumber("123-123-1234"));
        per.getTty().add(new PhoneNumber("123-123-1234"));
        per.getUrl().add(new URL("http://example.com/"));

        PersonDTO dto = (PersonDTO) PoXsnapshotHelper.createSnapshot(per);
        Person clone = (Person) PoXsnapshotHelper.createModel(dto);

        EqualsByValue.assertEquals(per, clone);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testHelperDirectly() throws Exception {
        Field f = PoXsnapshotHelper.class.getDeclaredField("REGISTRY");
        f.setAccessible(true);
        Object o = f.get(null);
        assertNotNull(o);
        PoXsnapshotHelper instance = (PoXsnapshotHelper) f.get(null);
        Collection<SnapshotHelper> values = instance.m_snapshotMap.values();
        for (SnapshotHelper helper : values) {
            assertNull(helper.createSnapshot(null, null));
            try {
                helper.createSnapshot(1L, null);
                fail();
            } catch (IllegalArgumentException iae) {
                // expected
            }
            assertNull(helper.createModel(null, null));
            try {
                helper.createModel(1L, null);
                fail();
            } catch (IllegalArgumentException iae) {
                // expected
            }
        }
    }

}
