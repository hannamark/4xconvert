package gov.nih.nci.po.service;

import static org.junit.Assert.assertEquals;
import gov.nih.nci.po.data.bo.Address;
import gov.nih.nci.po.data.bo.Country;
import gov.nih.nci.po.data.bo.Email;
import gov.nih.nci.po.data.bo.EntityStatus;
import gov.nih.nci.po.data.bo.Person;
import gov.nih.nci.po.data.bo.PhoneNumber;
import gov.nih.nci.po.data.bo.URL;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

public class PersonServiceBean_Search_PersonEntityServiceSearchCriteriaTest extends PersonServiceBeanTest {
    private static final Logger LOG = Logger
            .getLogger(PersonServiceBean_Search_PersonEntityServiceSearchCriteriaTest.class);

    PersonEntityServiceSearchCriteria sc;

    @Before
    public void initData2() {
        sc = new PersonEntityServiceSearchCriteria();
    }

    @Test
    public void findByFirstName() throws EntityValidationException {
        PersonEntityServiceSearchCriteria sc = new PersonEntityServiceSearchCriteria();
        createPerson();
        createPerson();
        sc.setPerson(new Person());

        sc.getPerson().setFirstName("%Nam%");
        assertEquals(2, getPersonServiceBean().count(sc));
        assertEquals(2, getPersonServiceBean().search(sc).size());

        sc.getPerson().setFirstName("foobar");
        assertEquals(0, getPersonServiceBean().count(sc));
        assertEquals(0, getPersonServiceBean().search(sc).size());
    }

    @Test
    public void findByEmail() throws EntityValidationException {
        /* create a person with two email addresses */
        createPerson();

        sc.setPerson(new Person());
        /* find a person with a matching email address */
        sc.getPerson().getEmail().add(new Email("abc"));
        assertEquals(1, getPersonServiceBean().count(sc));
        assertEquals(1, getPersonServiceBean().search(sc).size());
        /* then find a person with another matching email address */
        sc.getPerson().getEmail().add(new Email("def"));
        assertEquals(1, getPersonServiceBean().count(sc));
        assertEquals(1, getPersonServiceBean().search(sc).size());

        sc.getPerson().getEmail().add(new Email("ghi"));
        assertEquals(0, getPersonServiceBean().count(sc));
        assertEquals(0, getPersonServiceBean().search(sc).size());

        sc.setPerson(new Person());
        sc.getPerson().getEmail().add(new Email("ghi"));
        assertEquals(0, getPersonServiceBean().count(sc));
        assertEquals(0, getPersonServiceBean().search(sc).size());
    }

    @Test
    public void findByPhone() throws EntityValidationException {
        /* create a person with two phone numbers */
        createPerson();

        sc.setPerson(new Person());
        sc.getPerson().getPhone().add(new PhoneNumber("111"));
        assertEquals(1, getPersonServiceBean().count(sc));
        assertEquals(1, getPersonServiceBean().search(sc).size());

        sc.getPerson().getPhone().add(new PhoneNumber("123"));
        assertEquals(1, getPersonServiceBean().count(sc));
        assertEquals(1, getPersonServiceBean().search(sc).size());

        sc.getPerson().getPhone().add(new PhoneNumber("345"));
        assertEquals(0, getPersonServiceBean().count(sc));
        assertEquals(0, getPersonServiceBean().search(sc).size());

        sc.setPerson(new Person());
        sc.getPerson().getPhone().add(new PhoneNumber("345"));
        assertEquals(0, getPersonServiceBean().count(sc));
        assertEquals(0, getPersonServiceBean().search(sc).size());
    }

    @Test
    public void findByFax() throws EntityValidationException {
        /* create a person with two phone numbers */
        createPerson();

        sc.setPerson(new Person());
        sc.getPerson().getFax().add(new PhoneNumber("222"));
        assertEquals(1, getPersonServiceBean().count(sc));
        assertEquals(1, getPersonServiceBean().search(sc).size());

        sc.getPerson().getFax().add(new PhoneNumber("234"));
        assertEquals(1, getPersonServiceBean().count(sc));
        assertEquals(1, getPersonServiceBean().search(sc).size());

        sc.getPerson().getFax().add(new PhoneNumber("456"));
        assertEquals(0, getPersonServiceBean().count(sc));
        assertEquals(0, getPersonServiceBean().search(sc).size());

        sc.setPerson(new Person());
        sc.getPerson().getFax().add(new PhoneNumber("456"));
        assertEquals(0, getPersonServiceBean().count(sc));
        assertEquals(0, getPersonServiceBean().search(sc).size());
    }

    @Test
    public void findByTty() throws EntityValidationException {
        /* create a person with two phone numbers */
        createPerson();

        sc.setPerson(new Person());
        sc.getPerson().getTty().add(new PhoneNumber("333"));
        assertEquals(1, getPersonServiceBean().count(sc));
        assertEquals(1, getPersonServiceBean().search(sc).size());

        sc.getPerson().getTty().add(new PhoneNumber("345"));
        assertEquals(1, getPersonServiceBean().count(sc));
        assertEquals(1, getPersonServiceBean().search(sc).size());

        sc.getPerson().getTty().add(new PhoneNumber("567"));
        assertEquals(0, getPersonServiceBean().count(sc));
        assertEquals(0, getPersonServiceBean().search(sc).size());

        sc.setPerson(new Person());
        sc.getPerson().getTty().add(new PhoneNumber("567"));
        assertEquals(0, getPersonServiceBean().count(sc));
        assertEquals(0, getPersonServiceBean().search(sc).size());
    }

    @Test
    public void findByUrl() throws EntityValidationException {
        /* create a person with two phone numbers */
        createPerson();

        sc.setPerson(new Person());
        sc.getPerson().getUrl().add(new URL("abc"));
        assertEquals(1, getPersonServiceBean().count(sc));
        assertEquals(1, getPersonServiceBean().search(sc).size());

        sc.getPerson().getUrl().add(new URL("def"));
        assertEquals(1, getPersonServiceBean().count(sc));
        assertEquals(1, getPersonServiceBean().search(sc).size());

        sc.getPerson().getUrl().add(new URL("ghi"));
        assertEquals(0, getPersonServiceBean().count(sc));
        assertEquals(0, getPersonServiceBean().search(sc).size());

        sc.setPerson(new Person());
        sc.getPerson().getUrl().add(new URL("ghi"));
        assertEquals(0, getPersonServiceBean().count(sc));
        assertEquals(0, getPersonServiceBean().search(sc).size());
    }
    
    private void createPeopleWithAddresses() throws EntityValidationException {
        Person p = getBasicPerson();
        Address pa = new Address("P.O. Box 12345", "New-City", "VA", "12345-6789", getDefaultCountry());
        pa.setDeliveryAddressLine("c/o Mark Wild");
        p.setPostalAddress(pa);
        createPerson(p);
        
        p = getBasicPerson();
        pa = new Address("4567 First Ave.", "Harper's Ferry", "WV", "98765-4321", getDefaultCountry());
        pa.setDeliveryAddressLine("c/o John Doe");
        p.setPostalAddress(pa);
        createPerson(p);
        
        p = getBasicPerson();
        pa = new Address("P.O. Box 12345", "Old-City", "PA", "12345-6789", getDefaultCountry());
        p.setPostalAddress(pa);
        createPerson(p);
    }

    @Test
    public void findByAddressStreetAddressLine() throws EntityValidationException {
        createPeopleWithAddresses();

        sc.setPerson(new Person());
        sc.getPerson().setPostalAddress(new Address());
        sc.getPerson().getPostalAddress().setStreetAddressLine("123");
        assertEquals(2, getPersonServiceBean().count(sc));
        assertEquals(2, getPersonServiceBean().search(sc).size());

        sc.getPerson().getPostalAddress().setStreetAddressLine("P.O.");
        assertEquals(2, getPersonServiceBean().count(sc));
        assertEquals(2, getPersonServiceBean().search(sc).size());

        sc.getPerson().getPostalAddress().setStreetAddressLine("345");
        assertEquals(2, getPersonServiceBean().count(sc));
        assertEquals(2, getPersonServiceBean().search(sc).size());
        
        sc.getPerson().getPostalAddress().setStreetAddressLine("Z");
        assertEquals(0, getPersonServiceBean().count(sc));
        assertEquals(0, getPersonServiceBean().search(sc).size());
    }
    
    @Test
    public void findByAddressDeliveryAddressLine() throws EntityValidationException {
        createPeopleWithAddresses();
        
        sc.setPerson(new Person());
        sc.getPerson().setPostalAddress(new Address());
        sc.getPerson().getPostalAddress().setDeliveryAddressLine("c/o");
        assertEquals(2, getPersonServiceBean().count(sc));
        assertEquals(2, getPersonServiceBean().search(sc).size());
        
        sc.getPerson().getPostalAddress().setDeliveryAddressLine("John");
        assertEquals(1, getPersonServiceBean().count(sc));
        assertEquals(1, getPersonServiceBean().search(sc).size());
        
        sc.getPerson().getPostalAddress().setDeliveryAddressLine("Mark Wild");
        assertEquals(1, getPersonServiceBean().count(sc));
        assertEquals(1, getPersonServiceBean().search(sc).size());
        
        sc.getPerson().getPostalAddress().setDeliveryAddressLine("%");
        assertEquals(2, getPersonServiceBean().count(sc));
        assertEquals(2, getPersonServiceBean().search(sc).size());
    }
    
    @Test
    public void findByAddressCity() throws EntityValidationException {
        createPeopleWithAddresses();
        
        sc.setPerson(new Person());
        sc.getPerson().setPostalAddress(new Address());
        sc.getPerson().getPostalAddress().setCityOrMunicipality("-");
        assertEquals(2, getPersonServiceBean().count(sc));
        assertEquals(2, getPersonServiceBean().search(sc).size());
        
        sc.getPerson().getPostalAddress().setCityOrMunicipality("City");
        assertEquals(2, getPersonServiceBean().count(sc));
        assertEquals(2, getPersonServiceBean().search(sc).size());
        
        sc.getPerson().getPostalAddress().setCityOrMunicipality("'");
        assertEquals(1, getPersonServiceBean().count(sc));
        assertEquals(1, getPersonServiceBean().search(sc).size());
        
        sc.getPerson().getPostalAddress().setCityOrMunicipality("%");
        assertEquals(3, getPersonServiceBean().count(sc));
        assertEquals(3, getPersonServiceBean().search(sc).size());
    }
    
    @Test
    public void findByAddressPostalCode() throws EntityValidationException {
        createPeopleWithAddresses();
        
        sc.setPerson(new Person());
        sc.getPerson().setPostalAddress(new Address());
        sc.getPerson().getPostalAddress().setPostalCode("-");
        assertEquals(3, getPersonServiceBean().count(sc));
        assertEquals(3, getPersonServiceBean().search(sc).size());
        
        sc.getPerson().getPostalAddress().setPostalCode("123");
        assertEquals(2, getPersonServiceBean().count(sc));
        assertEquals(2, getPersonServiceBean().search(sc).size());
        
        sc.getPerson().getPostalAddress().setPostalCode("987");
        assertEquals(1, getPersonServiceBean().count(sc));
        assertEquals(1, getPersonServiceBean().search(sc).size());
        
        sc.getPerson().getPostalAddress().setPostalCode("%");
        assertEquals(3, getPersonServiceBean().count(sc));
        assertEquals(3, getPersonServiceBean().search(sc).size());
    }
    
    @Test
    public void findByAddressCountry() throws EntityValidationException {
        createPeopleWithAddresses();
        
        sc.setPerson(new Person());
        sc.getPerson().setPostalAddress(new Address());
        sc.getPerson().getPostalAddress().setCountry(new Country(null, null, null, "USA"));
        assertEquals(3, getPersonServiceBean().count(sc));
        assertEquals(3, getPersonServiceBean().search(sc).size());
        
        sc.getPerson().getPostalAddress().setCountry(new Country(null, null, null, "USA"));
        assertEquals(3, getPersonServiceBean().count(sc));
        assertEquals(3, getPersonServiceBean().search(sc).size());

    }
    
    @Test
    public void findByStatusCode() throws EntityValidationException {
        createPeopleWithAddresses();
        
        sc.setPerson(new Person());
        sc.getPerson().setStatusCode(EntityStatus.CURATED);
        assertEquals(0, getPersonServiceBean().count(sc));
        assertEquals(0, getPersonServiceBean().search(sc).size());
        
        sc.setPerson(new Person());
        sc.getPerson().setStatusCode(EntityStatus.NEW);
        assertEquals(3, getPersonServiceBean().count(sc));
        assertEquals(3, getPersonServiceBean().search(sc).size());
    }

}
