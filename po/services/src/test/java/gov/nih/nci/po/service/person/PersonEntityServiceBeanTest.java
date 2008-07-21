package gov.nih.nci.po.service.person;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.po.data.bo.Address;
import gov.nih.nci.po.data.bo.ContactInfo;
import gov.nih.nci.po.data.bo.Email;
import gov.nih.nci.po.data.bo.Person;
import gov.nih.nci.po.data.bo.URL;
import gov.nih.nci.po.data.common.Country;
import gov.nih.nci.po.data.common.CurationStatus;
import gov.nih.nci.po.dto.entity.PersonDTO;
import gov.nih.nci.po.service.AbstractHibernateTestCase;
import gov.nih.nci.po.service.EjbTestHelper;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.po.util.PoHibernateUtil;

import java.util.Date;

import java.util.Map;
import org.apache.commons.lang.time.DateUtils;
import org.junit.Before;
import org.junit.Test;

public class PersonEntityServiceBeanTest extends AbstractHibernateTestCase {

    private PersonEntityServiceRemote remote;
    private static Country USA = new Country("United States", "001", "US", "USA");

//    private Country validCountry;

    /**
     * setup the service.
     */
    @Before
    public void setupService() {
        remote = EjbTestHelper.getPersonEntityServiceBeanAsRemote();
    }


    @Test
    public void getPerson() {

        PoHibernateUtil.getCurrentSession().save(USA);
        Person per = makePerson();
        long id = (Long)PoHibernateUtil.getCurrentSession().save(per);
        PersonDTO result = remote.getPerson(id);
        assertEquals(per.getId(), result.getId());
        assertEquals(per.getFirstName(), result.getFirstName());
        assertEquals(per.getLastName(), result.getLastName());
    }

    private Person makePerson() {
        long now = System.currentTimeMillis();
        long longAgo = now - (DateUtils.MILLIS_PER_DAY * 365L * 50L);
        ContactInfo ci = makeContactInfo();
        Person p = new Person(ci);
        ci.setPerson(p);
        p.setDateOfBirth(new Date(longAgo));
        p.setCurationStatus(CurationStatus.NEW);
        p.setPrefix("Dr.");
        p.setFirstName("Dixie");
        p.setMiddleName("Dorothy");
        p.setLastName("Tavela");
        p.setPreferredContactInfo(ci);
        return p;
    }

    private ContactInfo makeContactInfo() {
        ContactInfo ci = new ContactInfo(makeAddress());
        ci.setTitle("Chief of Staff Title");
        ci.getUrl().add(new URL("http://www.example.com"));
        ci.getEmail().add(new Email("test@example.com"));
        return ci;
    }

    private Address makeAddress() {
    	Address ad = new Address("1601 18th Street NW", "Washington", "DC", "20016", USA);
        return ad;
    }

    @Test
    public void createPerson() {

        try {
            PersonDTO dto = new PersonDTO();
            dto.setId(99L);
            dto.setFirstName("Firsty");
            dto.setLastName("McGillicutty");
            dto.setMiddleName("Andrew");
            long id = remote.createPerson(dto);
            assertNull(dto.getId()); // make sure this id was not used
            Person p = (Person) PoHibernateUtil.getCurrentSession().get(Person.class, id);
            assertEquals(dto.getLastName(), p.getLastName());
            assertEquals(dto.getFirstName(), p.getFirstName());
        } catch (EntityValidationException e) {
            // as the DTO to BO conversion gets more completem, we should not
            // have to catch this exception.
            Map<String, String[]> errors = e.getErrors();
            assertEquals(4, errors.size()) ;
            assertTrue(errors.containsKey("preferredContactInfo.mailingAddress.streetAddressLine"));
            assertTrue(errors.containsKey("preferredContactInfo.mailingAddress.postalCode"));
            assertTrue(errors.containsKey("preferredContactInfo.mailingAddress.cityOrMunicipality"));
            assertTrue(errors.containsKey("preferredContactInfo.mailingAddress.country"));
        }

    }

    @Test
    public void validate() {
        
        PersonDTO dto = new PersonDTO();
        dto.setId(99L);
        dto.setFirstName("Firsty");
        dto.setMiddleName("Andrew");
        Map<String, String[]> errors = remote.validate(dto);
        System.out.println(errors);
        assertEquals(5, errors.size()) ;
        assertTrue(errors.containsKey("lastName"));
        assertTrue(errors.containsKey("preferredContactInfo.mailingAddress.streetAddressLine"));
        assertTrue(errors.containsKey("preferredContactInfo.mailingAddress.postalCode"));
        assertTrue(errors.containsKey("preferredContactInfo.mailingAddress.cityOrMunicipality"));
        assertTrue(errors.containsKey("preferredContactInfo.mailingAddress.country"));

    }
}
