package gov.nih.nci.po.service;

import static org.junit.Assert.assertEquals;
import gov.nih.nci.po.data.bo.Address;
import gov.nih.nci.po.data.bo.Country;
import gov.nih.nci.po.data.bo.Email;
import gov.nih.nci.po.data.bo.EntityStatus;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.data.bo.Person;
import gov.nih.nci.po.data.bo.PhoneNumber;
import gov.nih.nci.po.data.bo.URL;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

public class OrganizationServiceBean_Search_OrganizationEntityServiceSearchCriteriaTest extends OrganizationServiceBeanTest {
    private static final Logger LOG = Logger
            .getLogger(OrganizationServiceBean_Search_OrganizationEntityServiceSearchCriteriaTest.class);

    OrgEntityServiceSearchCriteria sc;

    @Before
    public void initData2() {
        sc = new OrgEntityServiceSearchCriteria();
    }
    
    @Override
    public long createOrganization() throws EntityValidationException {
        return super.createOrganization(getBasicOrganization());
    }
    
    @Test
    public void findByName() throws EntityValidationException {
        OrgEntityServiceSearchCriteria sc = new OrgEntityServiceSearchCriteria();
        createOrganization("testName", "defaultCity", "defaultOrgCode", "defaultDescription");
        createOrganization("abc", "defaultCity", "defaultOrgCode", "defaultDescription");
        Organization o = new Organization();
        sc.setOrganization(o);
        
        
        o.setName("%Nam%");
        assertEquals(1, getOrgServiceBean().count(sc));
        assertEquals(1, getOrgServiceBean().search(sc).size()); 
        
        o.setName("%a%");
        assertEquals(2, getOrgServiceBean().count(sc));
        assertEquals(2, getOrgServiceBean().search(sc).size()); 
        
        o.setName("foobar");
        assertEquals(0, getOrgServiceBean().count(sc));
        assertEquals(0, getOrgServiceBean().search(sc).size());
    }
    
    @Test
    public void findByDesc() throws EntityValidationException {
        OrgEntityServiceSearchCriteria sc = new OrgEntityServiceSearchCriteria();
        createOrganization("testName", "defaultCity", "defaultOrgCode", "defaultDescription");
        Organization o = new Organization();
        sc.setOrganization(o);
        
        o.setDescription("%Desc%");
        assertEquals(1, getOrgServiceBean().count(sc));
        assertEquals(1, getOrgServiceBean().search(sc).size()); 
        
        o.setDescription("foobar");
        assertEquals(0, getOrgServiceBean().count(sc));
        assertEquals(0, getOrgServiceBean().search(sc).size());
    }
    
    @Test
    public void findByAbbrvName() throws EntityValidationException {
        OrgEntityServiceSearchCriteria sc = new OrgEntityServiceSearchCriteria();
        createOrganization("testName", "defaultCity", "defaultOrgCode", "defaultDescription");
        Organization o = new Organization();
        sc.setOrganization(o);
        
        o.setAbbreviatedName("%Org%");
        assertEquals(1, getOrgServiceBean().count(sc));
        assertEquals(1, getOrgServiceBean().search(sc).size()); 
        
        o.setAbbreviatedName("foobar");
        assertEquals(0, getOrgServiceBean().count(sc));
        assertEquals(0, getOrgServiceBean().search(sc).size());
    }

    @Test
    public void findByEmail() throws EntityValidationException {
        /* create a person with two email addresses */
        createOrganization();

        sc.setOrganization(new Organization());
        /* find a person with a matching email address */
        sc.getOrganization().getEmail().add(new Email("abc"));
        assertEquals(1, getOrgServiceBean().count(sc));
        assertEquals(1, getOrgServiceBean().search(sc).size());
        /* then find a person with another matching email address */
        sc.getOrganization().getEmail().add(new Email("def"));
        assertEquals(1, getOrgServiceBean().count(sc));
        assertEquals(1, getOrgServiceBean().search(sc).size());

        sc.getOrganization().getEmail().add(new Email("ghi"));
        assertEquals(0, getOrgServiceBean().count(sc));
        assertEquals(0, getOrgServiceBean().search(sc).size());

        sc.setOrganization(new Organization());
        sc.getOrganization().getEmail().add(new Email("ghi"));
        assertEquals(0, getOrgServiceBean().count(sc));
        assertEquals(0, getOrgServiceBean().search(sc).size());
    }

    @Test
    public void findByPhone() throws EntityValidationException {
        /* create a person with two phone numbers */
        createOrganization();

        sc.setOrganization(new Organization());
        sc.getOrganization().getPhone().add(new PhoneNumber("111"));
        assertEquals(1, getOrgServiceBean().count(sc));
        assertEquals(1, getOrgServiceBean().search(sc).size());

        sc.getOrganization().getPhone().add(new PhoneNumber("123"));
        assertEquals(1, getOrgServiceBean().count(sc));
        assertEquals(1, getOrgServiceBean().search(sc).size());

        sc.getOrganization().getPhone().add(new PhoneNumber("345"));
        assertEquals(0, getOrgServiceBean().count(sc));
        assertEquals(0, getOrgServiceBean().search(sc).size());

        sc.setOrganization(new Organization());
        sc.getOrganization().getPhone().add(new PhoneNumber("345"));
        assertEquals(0, getOrgServiceBean().count(sc));
        assertEquals(0, getOrgServiceBean().search(sc).size());
    }

    @Test
    public void findByFax() throws EntityValidationException {
        /* create a person with two phone numbers */
        createOrganization();

        sc.setOrganization(new Organization());
        sc.getOrganization().getFax().add(new PhoneNumber("222"));
        assertEquals(1, getOrgServiceBean().count(sc));
        assertEquals(1, getOrgServiceBean().search(sc).size());

        sc.getOrganization().getFax().add(new PhoneNumber("234"));
        assertEquals(1, getOrgServiceBean().count(sc));
        assertEquals(1, getOrgServiceBean().search(sc).size());

        sc.getOrganization().getFax().add(new PhoneNumber("456"));
        assertEquals(0, getOrgServiceBean().count(sc));
        assertEquals(0, getOrgServiceBean().search(sc).size());

        sc.setOrganization(new Organization());
        sc.getOrganization().getFax().add(new PhoneNumber("456"));
        assertEquals(0, getOrgServiceBean().count(sc));
        assertEquals(0, getOrgServiceBean().search(sc).size());
    }

    @Test
    public void findByTty() throws EntityValidationException {
        /* create a person with two phone numbers */
        createOrganization();

        sc.setOrganization(new Organization());
        sc.getOrganization().getTty().add(new PhoneNumber("333"));
        assertEquals(1, getOrgServiceBean().count(sc));
        assertEquals(1, getOrgServiceBean().search(sc).size());

        sc.getOrganization().getTty().add(new PhoneNumber("345"));
        assertEquals(1, getOrgServiceBean().count(sc));
        assertEquals(1, getOrgServiceBean().search(sc).size());

        sc.getOrganization().getTty().add(new PhoneNumber("567"));
        assertEquals(0, getOrgServiceBean().count(sc));
        assertEquals(0, getOrgServiceBean().search(sc).size());

        sc.setOrganization(new Organization());
        sc.getOrganization().getTty().add(new PhoneNumber("567"));
        assertEquals(0, getOrgServiceBean().count(sc));
        assertEquals(0, getOrgServiceBean().search(sc).size());
    }

    @Test
    public void findByUrl() throws EntityValidationException {
        /* create a person with two phone numbers */
        createOrganization();

        sc.setOrganization(new Organization());
        sc.getOrganization().getUrl().add(new URL("abc"));
        assertEquals(1, getOrgServiceBean().count(sc));
        assertEquals(1, getOrgServiceBean().search(sc).size());

        sc.getOrganization().getUrl().add(new URL("def"));
        assertEquals(1, getOrgServiceBean().count(sc));
        assertEquals(1, getOrgServiceBean().search(sc).size());

        sc.getOrganization().getUrl().add(new URL("ghi"));
        assertEquals(0, getOrgServiceBean().count(sc));
        assertEquals(0, getOrgServiceBean().search(sc).size());

        sc.setOrganization(new Organization());
        sc.getOrganization().getUrl().add(new URL("ghi"));
        assertEquals(0, getOrgServiceBean().count(sc));
        assertEquals(0, getOrgServiceBean().search(sc).size());
    }
    
    private void createOrgsWithAddresses() throws EntityValidationException {
        Organization p = getBasicOrganization();
        Address pa = new Address("P.O. Box 12345", "New-City", "VA", "12345-6789", getDefaultCountry());
        pa.setDeliveryAddressLine("c/o Mark Wild");
        p.setPostalAddress(pa);
        createOrganization(p);
        
        p = getBasicOrganization();
        pa = new Address("4567 First Ave.", "Harper's Ferry", "WV", "98765-4321", getDefaultCountry());
        pa.setDeliveryAddressLine("c/o John Doe");
        p.setPostalAddress(pa);
        createOrganization(p);
        
        p = getBasicOrganization();
        pa = new Address("P.O. Box 12345", "Old-City", "PA", "12345-6789", getDefaultCountry());
        p.setPostalAddress(pa);
        createOrganization(p);
    }

    @Test
    public void findByAddressStreetAddressLine() throws EntityValidationException {
        createOrgsWithAddresses();

        sc.setOrganization(new Organization());
        sc.getOrganization().setPostalAddress(new Address());
        sc.getOrganization().getPostalAddress().setStreetAddressLine("123");
        assertEquals(2, getOrgServiceBean().count(sc));
        assertEquals(2, getOrgServiceBean().search(sc).size());

        sc.getOrganization().getPostalAddress().setStreetAddressLine("P.O.");
        assertEquals(2, getOrgServiceBean().count(sc));
        assertEquals(2, getOrgServiceBean().search(sc).size());

        sc.getOrganization().getPostalAddress().setStreetAddressLine("345");
        assertEquals(2, getOrgServiceBean().count(sc));
        assertEquals(2, getOrgServiceBean().search(sc).size());
        
        sc.getOrganization().getPostalAddress().setStreetAddressLine("Z");
        assertEquals(0, getOrgServiceBean().count(sc));
        assertEquals(0, getOrgServiceBean().search(sc).size());
    }
    
    @Test
    public void findByAddressDeliveryAddressLine() throws EntityValidationException {
        createOrgsWithAddresses();
        
        sc.setOrganization(new Organization());
        sc.getOrganization().setPostalAddress(new Address());
        sc.getOrganization().getPostalAddress().setDeliveryAddressLine("c/o");
        assertEquals(2, getOrgServiceBean().count(sc));
        assertEquals(2, getOrgServiceBean().search(sc).size());
        
        sc.getOrganization().getPostalAddress().setDeliveryAddressLine("John");
        assertEquals(1, getOrgServiceBean().count(sc));
        assertEquals(1, getOrgServiceBean().search(sc).size());
        
        sc.getOrganization().getPostalAddress().setDeliveryAddressLine("Mark Wild");
        assertEquals(1, getOrgServiceBean().count(sc));
        assertEquals(1, getOrgServiceBean().search(sc).size());
        
        sc.getOrganization().getPostalAddress().setDeliveryAddressLine("%");
        assertEquals(2, getOrgServiceBean().count(sc));
        assertEquals(2, getOrgServiceBean().search(sc).size());
    }
    
    @Test
    public void findByAddressCity() throws EntityValidationException {
        createOrgsWithAddresses();
        
        sc.setOrganization(new Organization());
        sc.getOrganization().setPostalAddress(new Address());
        sc.getOrganization().getPostalAddress().setCityOrMunicipality("-");
        assertEquals(2, getOrgServiceBean().count(sc));
        assertEquals(2, getOrgServiceBean().search(sc).size());
        
        sc.getOrganization().getPostalAddress().setCityOrMunicipality("City");
        assertEquals(2, getOrgServiceBean().count(sc));
        assertEquals(2, getOrgServiceBean().search(sc).size());
        
        sc.getOrganization().getPostalAddress().setCityOrMunicipality("'");
        assertEquals(1, getOrgServiceBean().count(sc));
        assertEquals(1, getOrgServiceBean().search(sc).size());
        
        sc.getOrganization().getPostalAddress().setCityOrMunicipality("%");
        assertEquals(3, getOrgServiceBean().count(sc));
        assertEquals(3, getOrgServiceBean().search(sc).size());
    }
    
    @Test
    public void findByAddressPostalCode() throws EntityValidationException {
        createOrgsWithAddresses();
        
        sc.setOrganization(new Organization());
        sc.getOrganization().setPostalAddress(new Address());
        sc.getOrganization().getPostalAddress().setPostalCode("-");
        assertEquals(3, getOrgServiceBean().count(sc));
        assertEquals(3, getOrgServiceBean().search(sc).size());
        
        sc.getOrganization().getPostalAddress().setPostalCode("123");
        assertEquals(2, getOrgServiceBean().count(sc));
        assertEquals(2, getOrgServiceBean().search(sc).size());
        
        sc.getOrganization().getPostalAddress().setPostalCode("987");
        assertEquals(1, getOrgServiceBean().count(sc));
        assertEquals(1, getOrgServiceBean().search(sc).size());
        
        sc.getOrganization().getPostalAddress().setPostalCode("%");
        assertEquals(3, getOrgServiceBean().count(sc));
        assertEquals(3, getOrgServiceBean().search(sc).size());
    }
    
    @Test
    public void findByAddressCountry() throws EntityValidationException {
        createOrgsWithAddresses();
        
        sc.setOrganization(new Organization());
        sc.getOrganization().setPostalAddress(new Address());
        sc.getOrganization().getPostalAddress().setCountry(new Country(null, null, null, "USA"));
        assertEquals(3, getOrgServiceBean().count(sc));
        assertEquals(3, getOrgServiceBean().search(sc).size());
        
        sc.getOrganization().getPostalAddress().setCountry(new Country(null, null, null, "USA"));
        assertEquals(3, getOrgServiceBean().count(sc));
        assertEquals(3, getOrgServiceBean().search(sc).size());

    }

    
    @Test
    public void findByStatusCode() throws EntityValidationException {
        createOrgsWithAddresses();
        
        sc.setOrganization(new Organization());
        sc.getOrganization().setStatusCode(EntityStatus.CURATED);
        assertEquals(0, getOrgServiceBean().count(sc));
        assertEquals(0, getOrgServiceBean().search(sc).size());
        
        sc.setOrganization(new Organization());
        sc.getOrganization().setStatusCode(EntityStatus.NEW);
        assertEquals(3, getOrgServiceBean().count(sc));
        assertEquals(3, getOrgServiceBean().search(sc).size());
    }
}
