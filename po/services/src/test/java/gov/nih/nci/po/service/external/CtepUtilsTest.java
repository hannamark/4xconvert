package gov.nih.nci.po.service.external;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.Tel;
import gov.nih.nci.iso21090.TelPhone;
import gov.nih.nci.po.data.bo.AbstractEnhancedOrganizationRole;
import gov.nih.nci.po.data.bo.Address;
import gov.nih.nci.po.data.bo.Country;
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
import gov.nih.nci.po.util.PoServiceUtil;
import gov.nih.nci.services.correlation.AbstractBaseEnhancedOrganizationRoleDTO;
import gov.nih.nci.services.correlation.ResearchOrganizationDTO;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import com.fiveamsolutions.nci.commons.data.persistent.PersistentObject;

public class CtepUtilsTest {

    @Test(expected = Exception.class)
    public void testNeedsCRNullCtep() {
        CtepUtils.isOrganizationDifferent(null, new Organization());
    }

    @Test(expected = Exception.class)
    public void testNeedsCRNullLocal() {
        CtepUtils.isOrganizationDifferent(new Organization(), null);
    }

    @Test
    public void testNeedsCRBothEmpty() {
        assertFalse(CtepUtils.isOrganizationDifferent(new Organization(), new Organization()));
    }

    @Test
    public void testNeedsCRNameDifferences() {
        Organization o1 = new Organization();
        Organization o2 = new Organization();

        o1.setName("");
        assertTrue(CtepUtils.isOrganizationDifferent(o1, o2));
        assertTrue(CtepUtils.isOrganizationDifferent(o2, o1));

        o2.setName("");
        assertFalse(CtepUtils.isOrganizationDifferent(o1, o2));
        assertFalse(CtepUtils.isOrganizationDifferent(o2, o1));

        o1.setName(" ");
        assertTrue(CtepUtils.isOrganizationDifferent(o1, o2));
        assertTrue(CtepUtils.isOrganizationDifferent(o2, o1));

        o1.setName("foo");
        o2.setName("Foo");
        assertTrue(CtepUtils.isOrganizationDifferent(o1, o2));
        assertTrue(CtepUtils.isOrganizationDifferent(o2, o1));

        o1.setName("Acme, Inc.");
        o2.setName("Acme, Inc.");
        assertFalse(CtepUtils.isOrganizationDifferent(o1, o2));
        assertFalse(CtepUtils.isOrganizationDifferent(o2, o1));
    }

    @Test
    public void testNeedsCRAddressDifferences() {
        Organization o1 = new Organization();
        Organization o2 = new Organization();

        o1.setPostalAddress(new Address());
        assertFalse(CtepUtils.isOrganizationDifferent(o1, o2)); // Blank & null address can be treated the same

        assertFalse(CtepUtils.isOrganizationDifferent(o1, o1));

        o2.setPostalAddress(new Address());
        assertFalse(CtepUtils.isOrganizationDifferent(o1, o2));

        // addresses can be different in a lot of ways, but we only test a couple here because
        // the implementation dispatches to Address.contentEquals, which is tested elsewhere
        o1.getPostalAddress().setPostalCode("12345");
        assertTrue(CtepUtils.isOrganizationDifferent(o1, o2));
        assertTrue(CtepUtils.isOrganizationDifferent(o2, o1));

        o2.getPostalAddress().setPostalCode("12345");
        o2.getPostalAddress().setCityOrMunicipality("City");
        assertTrue(CtepUtils.isOrganizationDifferent(o1, o2));
        assertTrue(CtepUtils.isOrganizationDifferent(o2, o1));

        o1.getPostalAddress().setCityOrMunicipality("City");
        assertFalse(CtepUtils.isOrganizationDifferent(o1, o2));
    }

    @Test
    public void testNeedsCREmailDifferences() {
        Organization o1 = new Organization();
        Organization o2 = new Organization();

        Email e1 = new Email();
        o1.getEmail().add(e1);
        assertTrue(CtepUtils.isOrganizationDifferent(o1, o2));
        assertTrue(CtepUtils.isOrganizationDifferent(o2, o1));

        Email e2 = new Email();
        o2.getEmail().add(e2);
        assertFalse(CtepUtils.isOrganizationDifferent(o1, o1));

        e1.setValue("test@example.com");
        assertTrue(CtepUtils.isOrganizationDifferent(o1, o2));
        assertTrue(CtepUtils.isOrganizationDifferent(o2, o1));

        e2.setValue("test@example.com");
        assertFalse(CtepUtils.isOrganizationDifferent(o1, o1));

        Email e3 = new Email();
        e3.setValue("test2@example.com");
        o1.getEmail().add(e3);
        assertFalse(CtepUtils.isOrganizationDifferent(o1, o1));
    }
    
//    @Test
    public void testNeedsCRPhoneDifferences() {
        // Note -- This testcases is not relevant as we are ignoring Phone number while synching Orgs with ECM.
        Organization o1 = new Organization();
        Organization o2 = new Organization();

        PhoneNumber e1 = new PhoneNumber();
        o1.getPhone().add(e1);
        assertTrue(CtepUtils.isOrganizationDifferent(o1, o2));
        assertTrue(CtepUtils.isOrganizationDifferent(o2, o1));

        PhoneNumber e2 = new PhoneNumber();
        o2.getPhone().add(e2);
        assertFalse(CtepUtils.isOrganizationDifferent(o1, o1));

        e1.setValue("111-222-3333");
        assertTrue(CtepUtils.isOrganizationDifferent(o1, o2));
        assertTrue(CtepUtils.isOrganizationDifferent(o2, o1));

        e2.setValue("111-222-3333");
        assertFalse(CtepUtils.isOrganizationDifferent(o1, o1));

        PhoneNumber e3 = new PhoneNumber();
        e3.setValue("444-555-6666");
        o1.getPhone().add(e3);
        assertFalse(CtepUtils.isOrganizationDifferent(o1, o1));
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
        assertTrue(CtepUtils.isOrganizationDifferent(o1, o2));
        assertTrue(CtepUtils.isOrganizationDifferent(o2, o1));

        o2.setName("name");
        o2.setPostalAddress(new Address());
        o2.getPostalAddress().setCityOrMunicipality("city");
        o2.getEmail().add(new Email());
        o2.getEmail().get(0).setValue("test@example.com");
        assertFalse(CtepUtils.isOrganizationDifferent(o1, o1));

        o2.setName("name2");
        assertTrue(CtepUtils.isOrganizationDifferent(o1, o2));
        assertTrue(CtepUtils.isOrganizationDifferent(o2, o1));
    }

    @Test
    public void testNeedsCRNonCheckedFields() {
        Organization o1 = new Organization();
        Organization o2 = new Organization();

        o1.setId(1L);        
        o1.setDuplicateOf(new Organization());
        o1.setStatusDate(new Date());
        o1.getFax().add(new PhoneNumber("5"));
        o1.getTty().add(new PhoneNumber("7"));
        o1.getUrl().add(new URL("http://www.example.com"));
        o1.getHealthCareFacilities().add(new HealthCareFacility());
        o1.getIdentifiedOrganizations().add(new IdentifiedOrganization());
        o1.getOrganizationalContacts().add(new OrganizationalContact());
        o1.getOversightCommittees().add(new OversightCommittee());
        o1.getResearchOrganizations().add(new ResearchOrganization());

        assertFalse(CtepUtils.isOrganizationDifferent(o1, o2));
    }

    @Test(expected = Exception.class)
    public void testCopyFromNull() {
        CtepUtils.copyOrganization(null, new OrganizationCR());
    }

    @Test(expected = Exception.class)
    public void testCopyToNull() {
        CtepUtils.copyOrganization(new Organization(), null);
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
        CtepUtils.copyOrganization(o1, o2);

        assertFalse(PoServiceUtil.aliasIsNotPresent(o2.getAlias(), o1.getName()));
        assertEquals(o1.getEmail().get(0).getValue(), o2.getEmail().get(0).getValue());
        assertEquals(o1.getPostalAddress().getCityOrMunicipality(), o2.getPostalAddress().getCityOrMunicipality());
        assertNull(o2.getId());
        assertEquals(o1.getStatusCode(), o2.getStatusCode());
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
    
    @Test
    public void converPhoneNumberFormats() throws URISyntaxException {
        AbstractBaseEnhancedOrganizationRoleDTO dto = new ResearchOrganizationDTO();
        dto.setTelecomAddress(new DSet<Tel>());
        dto.getTelecomAddress().setItem(new HashSet<Tel>());
        final Set<Tel> phones = dto.getTelecomAddress().getItem();
        
        TelPhone phone =  new TelPhone();
        phone.setValue(new URI("tel:(703)-555-5555ext123"));        
        phones.clear();
        phones.add(phone);
        CtepUtils.converPhoneNumberFormats(dto);
        assertEquals("tel:703-555-5555ext123", phones.iterator().next().getValue().toString());
        
        phone =  new TelPhone();
        phone.setValue(new URI("x-text-fax:(703)-555-5555ext123"));        
        phones.clear();
        phones.add(phone);
        CtepUtils.converPhoneNumberFormats(dto);
        assertEquals("x-text-fax:703-555-5555ext123", phones.iterator().next().getValue().toString());
        
        phone =  new TelPhone();
        phone.setValue(new URI("x-text-tel:(703)-555-5555ext123"));        
        phones.clear();
        phones.add(phone);
        CtepUtils.converPhoneNumberFormats(dto);
        assertEquals("x-text-tel:703-555-5555ext123", phones.iterator().next().getValue().toString());   
        
        phone =  new TelPhone();
        phone.setValue(new URI("tel:(703)555-5555ext123"));        
        phones.clear();
        phones.add(phone);
        CtepUtils.converPhoneNumberFormats(dto);
        assertEquals("tel:703-555-5555ext123", phones.iterator().next().getValue().toString());     
        
        phone =  new TelPhone();
        phone.setValue(new URI("x-text-fax:(703)555-5555ext123"));        
        phones.clear();
        phones.add(phone);
        CtepUtils.converPhoneNumberFormats(dto);
        assertEquals("x-text-fax:703-555-5555ext123", phones.iterator().next().getValue().toString());     
        
        phone =  new TelPhone();
        phone.setValue(new URI("x-text-tel:(703)555-5555ext123"));        
        phones.clear();
        phones.add(phone);
        CtepUtils.converPhoneNumberFormats(dto);
        assertEquals("x-text-tel:703-555-5555ext123", phones.iterator().next().getValue().toString());           
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testValidateAddresses() throws Exception {
        CtepUtils.validateAddresses(null);
        AbstractEnhancedOrganizationRole role = new AbstractEnhancedOrganizationRole() {
            @Override
            public PersistentObject getDuplicateOf() {               
                return null;
            }
            @Override
            public Set getChangeRequests() {
                return null;
            }};
        CtepUtils.validateAddresses(role);
        Set<Address> addrs = new HashSet<Address>();
        Address addr = new Address();
        addr.setStreetAddressLine("streetAddressLine");
        addr.setCityOrMunicipality("cityOrMunicipality");
        addr.setPostalCode("postalCode");
        addr.setCountry(new Country());
        addrs.add(addr);
        role.setPostalAddresses(addrs);
        CtepUtils.validateAddresses(role);
    }

    @Test(expected = CtepImportException.class)
    public void testValidateAddressesException() throws Exception {
        AbstractEnhancedOrganizationRole role = new AbstractEnhancedOrganizationRole() {
            @Override
            public PersistentObject getDuplicateOf() {               
                return null;
            }
            @Override
            public Set getChangeRequests() {
                return null;
            }};
        Set<Address> addrs = new HashSet<Address>();
        Address addr = new Address();
        addrs.add(addr);
        role.setPostalAddresses(addrs);
        CtepUtils.validateAddresses(role);
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testValidateOversightCommitteeAddresses() throws Exception {
        CtepUtils.validateAddresses(null);
        AbstractEnhancedOrganizationRole role = new AbstractEnhancedOrganizationRole() {
            @Override
            public PersistentObject getDuplicateOf() {               
                return null;
            }
            @Override
            public Set getChangeRequests() {
                return null;
            }};
        CtepUtils.validateAddresses(role);
        Set<Address> addrs = new HashSet<Address>();
        Address addr = new Address();
        addr.setStreetAddressLine("streetAddressLine");
        addr.setCityOrMunicipality("cityOrMunicipality");
        addr.setPostalCode("postalCode");
        addr.setCountry(new Country());
        addrs.add(addr);
        role.setPostalAddresses(addrs);
        CtepUtils.validateAddresses(role);
    }

    @Test(expected = CtepImportException.class)
    public void testValidateOversightCommitteeAddressesException() throws Exception {
        AbstractEnhancedOrganizationRole role = new AbstractEnhancedOrganizationRole() {
            @Override
            public PersistentObject getDuplicateOf() {               
                return null;
            }
            @Override
            public Set getChangeRequests() {
                return null;
            }};
        Set<Address> addrs = new HashSet<Address>();
        Address addr = new Address();
        addrs.add(addr);
        role.setPostalAddresses(addrs);
        CtepUtils.validateAddresses(role);
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testValidateAddress() throws Exception {
        CtepUtils.validateAddress(null);
        Address addr = new Address();
        addr.setStreetAddressLine("streetAddressLine");
        addr.setCityOrMunicipality("cityOrMunicipality");
        addr.setPostalCode("postalCode");
        addr.setCountry(new Country());
        CtepUtils.validateAddress(addr);
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testValidateAddressException() {
        Address addr = new Address();
        addr.setCityOrMunicipality("cityOrMunicipality");
        addr.setPostalCode("postalCode");
        addr.setCountry(new Country());
        try {
            CtepUtils.validateAddress(addr);
            fail();
        } catch (CtepImportException e) {
            assertEquals("street address missing", e.getShortMessage());
        }
        addr.setStreetAddressLine("streetAddressLine");
        addr.setCityOrMunicipality(" ");
        try {
            CtepUtils.validateAddress(addr);
            fail();
        } catch (CtepImportException e) {
            assertEquals("city missing", e.getShortMessage());
        }
        addr.setCityOrMunicipality("cityOrMunicipality");
        addr.setCountry(null);
        try {
            CtepUtils.validateAddress(addr);
            fail();
        } catch (CtepImportException e) {
            assertEquals("country missing", e.getShortMessage());
        }
   }
}
