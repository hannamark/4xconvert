package gov.nih.nci.po.service.external;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.po.data.bo.Address;
import gov.nih.nci.po.data.bo.Email;
import gov.nih.nci.po.data.bo.EntityStatus;
import gov.nih.nci.po.data.bo.HealthCareFacility;
import gov.nih.nci.po.data.bo.IdentifiedOrganization;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.data.bo.OrganizationCR;
import gov.nih.nci.po.data.bo.OrganizationalContact;
import gov.nih.nci.po.data.bo.OversightCommittee;
import gov.nih.nci.po.data.bo.PhoneNumber;
import gov.nih.nci.po.data.bo.ResearchOrganization;
import gov.nih.nci.po.data.bo.URL;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

public class CtepUtilsTest {

    @Test(expected = Exception.class)
    public void testNeedsCRNullCtep() {
        CtepUtils.isDifferent(null, new Organization());
    }

    @Test(expected = Exception.class)
    public void testNeedsCRNullLocal() {
        CtepUtils.isDifferent(new Organization(), null);
    }

    @Test
    public void testNeedsCRBothEmpty() {
        assertFalse(CtepUtils.isDifferent(new Organization(), new Organization()));
    }

    @Test
    public void testNeedsCRNameDifferences() {
        Organization o1 = new Organization();
        Organization o2 = new Organization();

        o1.setName("");
        assertTrue(CtepUtils.isDifferent(o1, o2));
        assertTrue(CtepUtils.isDifferent(o2, o1));

        o2.setName("");
        assertFalse(CtepUtils.isDifferent(o1, o2));
        assertFalse(CtepUtils.isDifferent(o2, o1));

        o1.setName(" ");
        assertTrue(CtepUtils.isDifferent(o1, o2));
        assertTrue(CtepUtils.isDifferent(o2, o1));

        o1.setName("foo");
        o2.setName("Foo");
        assertTrue(CtepUtils.isDifferent(o1, o2));
        assertTrue(CtepUtils.isDifferent(o2, o1));

        o1.setName("Acme, Inc.");
        o2.setName("Acme, Inc.");
        assertFalse(CtepUtils.isDifferent(o1, o2));
        assertFalse(CtepUtils.isDifferent(o2, o1));
    }

    @Test
    public void testNeedsCRAddressDifferences() {
        Organization o1 = new Organization();
        Organization o2 = new Organization();

        o1.setPostalAddress(new Address());
        assertFalse(CtepUtils.isDifferent(o1, o2)); // Blank & null address can be treated the same

        assertFalse(CtepUtils.isDifferent(o1, o1));

        o2.setPostalAddress(new Address());
        assertFalse(CtepUtils.isDifferent(o1, o2));

        // addresses can be different in a lot of ways, but we only test a couple here because
        // the implementation dispatches to Address.contentEquals, which is tested elsewhere
        o1.getPostalAddress().setPostalCode("12345");
        assertTrue(CtepUtils.isDifferent(o1, o2));
        assertTrue(CtepUtils.isDifferent(o2, o1));

        o2.getPostalAddress().setPostalCode("12345");
        o2.getPostalAddress().setCityOrMunicipality("City");
        assertTrue(CtepUtils.isDifferent(o1, o2));
        assertTrue(CtepUtils.isDifferent(o2, o1));

        o1.getPostalAddress().setCityOrMunicipality("City");
        assertFalse(CtepUtils.isDifferent(o1, o2));
    }

    @Test
    public void testNeedsCREmailDifferences() {
        Organization o1 = new Organization();
        Organization o2 = new Organization();

        Email e1 = new Email();
        o1.getEmail().add(e1);
        assertTrue(CtepUtils.isDifferent(o1, o2));
        assertTrue(CtepUtils.isDifferent(o2, o1));

        Email e2 = new Email();
        o2.getEmail().add(e2);
        assertFalse(CtepUtils.isDifferent(o1, o1));

        e1.setValue("test@example.com");
        assertTrue(CtepUtils.isDifferent(o1, o2));
        assertTrue(CtepUtils.isDifferent(o2, o1));

        e2.setValue("test@example.com");
        assertFalse(CtepUtils.isDifferent(o1, o1));

        Email e3 = new Email();
        e3.setValue("test2@example.com");
        o1.getEmail().add(e3);
        assertFalse(CtepUtils.isDifferent(o1, o1));
    }
    
    @Test
    public void testNeedsCRPhoneDifferences() {
        Organization o1 = new Organization();
        Organization o2 = new Organization();

        PhoneNumber e1 = new PhoneNumber();
        o1.getPhone().add(e1);
        assertTrue(CtepUtils.isDifferent(o1, o2));
        assertTrue(CtepUtils.isDifferent(o2, o1));

        PhoneNumber e2 = new PhoneNumber();
        o2.getPhone().add(e2);
        assertFalse(CtepUtils.isDifferent(o1, o1));

        e1.setValue("111-222-3333");
        assertTrue(CtepUtils.isDifferent(o1, o2));
        assertTrue(CtepUtils.isDifferent(o2, o1));

        e2.setValue("111-222-3333");
        assertFalse(CtepUtils.isDifferent(o1, o1));

        PhoneNumber e3 = new PhoneNumber();
        e3.setValue("444-555-6666");
        o1.getPhone().add(e3);
        assertFalse(CtepUtils.isDifferent(o1, o1));
    }

    @Test
    public void testNeedsCRMultipleDifferences() {
        Organization o1 = new Organization();
        o1.setName("name");
        o1.setPostalAddress(new Address());
        o1.getPostalAddress().setCityOrMunicipality("city");
        o1.getEmail().add(new Email());
        o1.getEmail().get(0).setValue("test@example.com");

        Organization o2 = new Organization();
        assertTrue(CtepUtils.isDifferent(o1, o2));
        assertTrue(CtepUtils.isDifferent(o2, o1));

        o2.setName("name");
        o2.setPostalAddress(new Address());
        o2.getPostalAddress().setCityOrMunicipality("city");
        o2.getEmail().add(new Email());
        o2.getEmail().get(0).setValue("test@example.com");
        assertFalse(CtepUtils.isDifferent(o1, o1));

        o2.setName("name2");
        assertTrue(CtepUtils.isDifferent(o1, o2));
        assertTrue(CtepUtils.isDifferent(o2, o1));
    }

    @Test
    public void testNeedsCRNonCheckedFields() {
        Organization o1 = new Organization();
        Organization o2 = new Organization();

        o1.setId(1L);
        o1.setComments("comments");
        o1.setDuplicateOf(new Organization());
        o1.setStatusCode(EntityStatus.NULLIFIED);
        o1.setStatusDate(new Date());
        o1.getFax().add(new PhoneNumber("5"));
        o1.getTty().add(new PhoneNumber("7"));
        o1.getUrl().add(new URL("http://www.example.com"));
        o1.getHealthCareFacilities().add(new HealthCareFacility());
        o1.getIdentifiedOrganizations().add(new IdentifiedOrganization());
        o1.getOrganizationalContacts().add(new OrganizationalContact());
        o1.getOversightCommittees().add(new OversightCommittee());
        o1.getResearchOrganizations().add(new ResearchOrganization());

        assertFalse(CtepUtils.isDifferent(o1, o2));
    }

    @Test(expected = Exception.class)
    public void testCopyFromNull() {
        CtepUtils.copy(null, new OrganizationCR());
    }

    @Test(expected = Exception.class)
    public void testCopyToNull() {
        CtepUtils.copy(new Organization(), null);
    }

    @Test
    public void testCopy() {
        Organization o1 = new Organization();
        // fields that should be copied
        o1.setName("name");
        o1.getEmail().add(new Email("test@exapmle.com"));
        o1.setPostalAddress(new Address());
        o1.getPostalAddress().setCityOrMunicipality("city");
        // fields thot should not be copied
        o1.setId(1L);
        o1.setComments("comments");
        o1.setDuplicateOf(new Organization());
        o1.setStatusCode(EntityStatus.NULLIFIED);
        o1.setStatusDate(new Date());
        o1.getFax().add(new PhoneNumber("5"));
        o1.getPhone().add(new PhoneNumber("6"));
        o1.getTty().add(new PhoneNumber("7"));
        o1.getUrl().add(new URL("http://www.example.com"));
        o1.getHealthCareFacilities().add(new HealthCareFacility());
        o1.getIdentifiedOrganizations().add(new IdentifiedOrganization());
        o1.getOrganizationalContacts().add(new OrganizationalContact());
        o1.getOversightCommittees().add(new OversightCommittee());
        o1.getResearchOrganizations().add(new ResearchOrganization());

        OrganizationCR o2 = new OrganizationCR();
        CtepUtils.copy(o1, o2);

        assertEquals(o1.getName(), o2.getName());
        assertEquals(o1.getEmail().get(0).getValue(), o2.getEmail().get(0).getValue());
        assertEquals(o1.getPostalAddress().getCityOrMunicipality(), o2.getPostalAddress().getCityOrMunicipality());
        assertNull(o2.getId());
        assertNull(o2.getStatusCode());
        assertTrue(o2.getFax().isEmpty());
        assertTrue(o2.getPhone().isEmpty());
        assertTrue(o2.getTty().isEmpty());
        assertTrue(o2.getUrl().isEmpty());
    }

    @Test
    public void areEmailListsEqual() {
        List<Email> list1 = new ArrayList<Email>();
        List<Email> list2 = new ArrayList<Email>();
        assertTrue(CtepUtils.areEmailListsEqual(list1, list2));

        Email email1 = new Email("1@example.com");
        Email email1copy = new Email("1@example.com");
        Email email2 = new Email("2@example.com");
        Email email2copy = new Email("2@example.com");

        list1.add(email1);
        assertFalse(CtepUtils.areEmailListsEqual(list1, list2));

        list2.add(email2);
        assertFalse(CtepUtils.areEmailListsEqual(list1, list2));

        list1.add(email2copy);
        list2.add(email1copy);
        assertTrue(CtepUtils.areEmailListsEqual(list1, list2));
    }
    
    @Test
    public void arePhoneNumberListsEqual() {
        List<PhoneNumber> list1 = new ArrayList<PhoneNumber>();
        List<PhoneNumber> list2 = new ArrayList<PhoneNumber>();
        assertTrue(CtepUtils.arePhoneNumberListsEqual(list1, list2));

        PhoneNumber phone1 = new PhoneNumber("111-222-3333");
        PhoneNumber phone1copy = new PhoneNumber("111-222-3333");
        PhoneNumber phone2 = new PhoneNumber("111-222-3333x12345");
        PhoneNumber phone2copy = new PhoneNumber("111-222-3333x12345");

        list1.add(phone1);
        assertFalse(CtepUtils.arePhoneNumberListsEqual(list1, list2));

        list2.add(phone2);
        assertFalse(CtepUtils.arePhoneNumberListsEqual(list1, list2));

        list1.add(phone2copy);
        list2.add(phone1copy);
        assertTrue(CtepUtils.arePhoneNumberListsEqual(list1, list2));
    }


}
