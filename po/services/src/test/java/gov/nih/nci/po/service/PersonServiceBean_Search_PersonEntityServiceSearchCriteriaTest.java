package gov.nih.nci.po.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import gov.nih.nci.po.data.bo.Address;
import gov.nih.nci.po.data.bo.Country;
import gov.nih.nci.po.data.bo.Email;
import gov.nih.nci.po.data.bo.EntityStatus;
import gov.nih.nci.po.data.bo.Person;
import gov.nih.nci.po.data.bo.PhoneNumber;
import gov.nih.nci.po.data.bo.URL;
import gov.nih.nci.po.util.PoHibernateUtil;

import java.util.List;

import javax.jms.JMSException;

import org.hibernate.Query;
import org.junit.Before;
import org.junit.Test;

import com.fiveamsolutions.nci.commons.search.OneCriterionRequiredException;

public class PersonServiceBean_Search_PersonEntityServiceSearchCriteriaTest extends PersonServiceBeanTest {
    AnnotatedBeanSearchCriteria<Person> sc;
    private Person personSc;

    @Before
    public void initData2() {
        personSc = new Person();
        sc = new AnnotatedBeanSearchCriteria<Person>(personSc);
    }

    @Test
    public void verifyNullifiedEntityNotReturned() throws Exception {
        long id = createPerson();

        personSc.setFirstName("%Nam%");
        assertEquals(1, getPersonServiceBean().count(sc));

        Person p = (Person) PoHibernateUtil.getCurrentSession().get(Person.class, id);
        p.setStatusCode(EntityStatus.NULLIFIED);
        PoHibernateUtil.getCurrentSession().saveOrUpdate(p);
        PoHibernateUtil.getCurrentSession().flush();
        PoHibernateUtil.getCurrentSession().clear();

        assertEquals(0, getPersonServiceBean().count(sc));
    }

    @Test
    public void findByFirstName() throws EntityValidationException, JMSException {
        createPerson();
        createPerson();

        personSc.setFirstName("%NAM");
        assertEquals(2, getPersonServiceBean().count(sc));
        assertEquals(2, getPersonServiceBean().search(sc).size());

        personSc.setFirstName("fOoBaR");
        assertEquals(0, getPersonServiceBean().count(sc));
        assertEquals(0, getPersonServiceBean().search(sc).size());
    }

    @Test
    public void findByEmail() throws EntityValidationException, JMSException {
        /* create a person with two email addresses */
        createPerson();

        personSc.getEmail().add(new Email("aBc"));
        assertEquals(1, getPersonServiceBean().count(sc));
        assertEquals(1, getPersonServiceBean().search(sc).size());

        initData2();
        personSc.getEmail().add(new Email("dEf"));
        assertEquals(1, getPersonServiceBean().count(sc));
        assertEquals(1, getPersonServiceBean().search(sc).size());

        initData2();
        personSc.getEmail().add(new Email("gHi"));
        assertEquals(0, getPersonServiceBean().count(sc));
        assertEquals(0, getPersonServiceBean().search(sc).size());

        initData2();
        personSc.getEmail().add(new Email("AbC"));
        personSc.getEmail().add(new Email("Ghi"));
        assertEquals(1, getPersonServiceBean().count(sc));
        assertEquals(1, getPersonServiceBean().search(sc).size());
    }

    @Test
    public void findByPhone() throws EntityValidationException, JMSException {
        /* create a person with two phone numbers */
        createPerson();

        personSc.getPhone().add(new PhoneNumber("111"));
        assertEquals(1, getPersonServiceBean().count(sc));
        assertEquals(1, getPersonServiceBean().search(sc).size());

        initData2();
        personSc.getPhone().add(new PhoneNumber("123"));
        assertEquals(1, getPersonServiceBean().count(sc));
        assertEquals(1, getPersonServiceBean().search(sc).size());

        initData2();
        personSc.getPhone().add(new PhoneNumber("345"));
        assertEquals(0, getPersonServiceBean().count(sc));
        assertEquals(0, getPersonServiceBean().search(sc).size());

        initData2();
        personSc.getPhone().add(new PhoneNumber("111"));
        personSc.getPhone().add(new PhoneNumber("345"));
        assertEquals(1, getPersonServiceBean().count(sc));
        assertEquals(1, getPersonServiceBean().search(sc).size());
    }

    @Test
    public void findByFax() throws EntityValidationException, JMSException {
        /* create a person with two phone numbers */
        createPerson();

        initData2();
        personSc.getFax().add(new PhoneNumber("222"));
        assertEquals(1, getPersonServiceBean().count(sc));
        assertEquals(1, getPersonServiceBean().search(sc).size());

        initData2();
        personSc.getFax().add(new PhoneNumber("234"));
        assertEquals(1, getPersonServiceBean().count(sc));
        assertEquals(1, getPersonServiceBean().search(sc).size());

        initData2();
        personSc.getFax().add(new PhoneNumber("456"));
        assertEquals(0, getPersonServiceBean().count(sc));
        assertEquals(0, getPersonServiceBean().search(sc).size());

        initData2();
        personSc.getFax().add(new PhoneNumber("456"));
        personSc.getFax().add(new PhoneNumber("234"));
        assertEquals(1, getPersonServiceBean().count(sc));
        assertEquals(1, getPersonServiceBean().search(sc).size());
    }

    @Test
    public void findByTty() throws EntityValidationException, JMSException {
        /* create a person with two phone numbers */
        createPerson();

        initData2();
        personSc.getTty().add(new PhoneNumber("333"));
        assertEquals(1, getPersonServiceBean().count(sc));
        assertEquals(1, getPersonServiceBean().search(sc).size());

        initData2();
        personSc.getTty().add(new PhoneNumber("345"));
        assertEquals(1, getPersonServiceBean().count(sc));
        assertEquals(1, getPersonServiceBean().search(sc).size());

        initData2();
        personSc.getTty().add(new PhoneNumber("567"));
        assertEquals(0, getPersonServiceBean().count(sc));
        assertEquals(0, getPersonServiceBean().search(sc).size());

        initData2();
        personSc.getTty().add(new PhoneNumber("567"));
        personSc.getTty().add(new PhoneNumber("333"));
        assertEquals(1, getPersonServiceBean().count(sc));
        assertEquals(1, getPersonServiceBean().search(sc).size());
    }

    @Test
    public void findByUrl() throws EntityValidationException, JMSException {
        /* create a person with two phone numbers */
        createPerson();

        initData2();
        personSc.getUrl().add(new URL("http://www.eXAmpLe.com/abc"));
        assertEquals(1, getPersonServiceBean().count(sc));
        assertEquals(1, getPersonServiceBean().search(sc).size());

        initData2();
        personSc.getUrl().add(new URL("http://www.example.COM/def"));
        assertEquals(1, getPersonServiceBean().count(sc));
        assertEquals(1, getPersonServiceBean().search(sc).size());

        initData2();
        personSc.getUrl().add(new URL("ghi"));
        assertEquals(0, getPersonServiceBean().count(sc));
        assertEquals(0, getPersonServiceBean().search(sc).size());

        initData2();
        personSc.getUrl().add(new URL("ghi"));
        personSc.getUrl().add(new URL("http://www.examplE.com/def"));
        assertEquals(1, getPersonServiceBean().count(sc));
        assertEquals(1, getPersonServiceBean().search(sc).size());
    }

    private void createPeopleWithAddresses() throws EntityValidationException, JMSException {
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
        pa = new Address("P.O. Box 12346", "Old-City", "PA", "12346-6789", getDefaultCountry());
        p.setPostalAddress(pa);
        createPerson(p);
    }

    @Test
    public void findByAddressStreetAddressLine() throws EntityValidationException, JMSException {
        createPeopleWithAddresses();
        // contains
        personSc.setPostalAddress(new Address());
        personSc.getPostalAddress().setStreetAddressLine("BoX");
        assertEquals(2, getPersonServiceBean().count(sc));
        assertEquals(2, getPersonServiceBean().search(sc).size());
        // starts with (included in contains)
        personSc.getPostalAddress().setStreetAddressLine("P.o.");
        assertEquals(2, getPersonServiceBean().count(sc));
        assertEquals(2, getPersonServiceBean().search(sc).size());

        personSc.getPostalAddress().setStreetAddressLine("P.o. BoX 12346");
        assertEquals(1, getPersonServiceBean().count(sc));
        assertEquals(1, getPersonServiceBean().search(sc).size());
        // no match at all
        personSc.getPostalAddress().setStreetAddressLine("Z");
        assertEquals(0, getPersonServiceBean().count(sc));
        assertEquals(0, getPersonServiceBean().search(sc).size());
    }

    @Test
    public void findByAddressDeliveryAddressLine() throws EntityValidationException, JMSException {
        createPeopleWithAddresses();
        // starts with (part of contains)
        personSc.setPostalAddress(new Address());
        personSc.getPostalAddress().setDeliveryAddressLine("c/O");
        assertEquals(2, getPersonServiceBean().count(sc));
        assertEquals(2, getPersonServiceBean().search(sc).size());
        // contains
        personSc.getPostalAddress().setDeliveryAddressLine("JoHn");
        assertEquals(1, getPersonServiceBean().count(sc));
        assertEquals(1, getPersonServiceBean().search(sc).size());
        // full match
        personSc.getPostalAddress().setDeliveryAddressLine("c/o JoHn");
        assertEquals(1, getPersonServiceBean().count(sc));
        assertEquals(1, getPersonServiceBean().search(sc).size());
        // no match
        personSc.getPostalAddress().setDeliveryAddressLine("c/oJoHn");
        assertEquals(0, getPersonServiceBean().count(sc));
        assertEquals(0, getPersonServiceBean().search(sc).size());

        try {
            personSc.getPostalAddress().setDeliveryAddressLine("");
            assertEquals(2, getPersonServiceBean().count(sc));
            assertEquals(2, getPersonServiceBean().search(sc).size());
            fail();
        } catch (OneCriterionRequiredException e) {

        }
    }

    @Test
    public void findByAddressCity() throws EntityValidationException, JMSException {
        createPeopleWithAddresses();

        // contains
        personSc.setPostalAddress(new Address());
        personSc.getPostalAddress().setCityOrMunicipality("1-1");
        assertEquals(0, getPersonServiceBean().count(sc));
        assertEquals(0, getPersonServiceBean().search(sc).size());

        // contains
        personSc.setPostalAddress(new Address());
        personSc.getPostalAddress().setCityOrMunicipality("-");
        assertEquals(2, getPersonServiceBean().count(sc));
        assertEquals(2, getPersonServiceBean().search(sc).size());

        personSc.getPostalAddress().setCityOrMunicipality("NeW");
        assertEquals(1, getPersonServiceBean().count(sc));
        assertEquals(1, getPersonServiceBean().search(sc).size());

        personSc.getPostalAddress().setCityOrMunicipality("HaRper'");
        assertEquals(1, getPersonServiceBean().count(sc));
        assertEquals(1, getPersonServiceBean().search(sc).size());

        try {
            personSc.getPostalAddress().setCityOrMunicipality("");
            assertEquals(3, getPersonServiceBean().count(sc));
            assertEquals(3, getPersonServiceBean().search(sc).size());
            fail();
        } catch (OneCriterionRequiredException e) {

        }
    }

    @Test
    public void findByAddressPostalCode() throws EntityValidationException, JMSException {
        createPeopleWithAddresses();

        personSc.setPostalAddress(new Address());
        personSc.getPostalAddress().setPostalCode("-");
        assertEquals(3, getPersonServiceBean().count(sc));
        assertEquals(3, getPersonServiceBean().search(sc).size());

        personSc.getPostalAddress().setPostalCode("1234");
        assertEquals(2, getPersonServiceBean().count(sc));
        assertEquals(2, getPersonServiceBean().search(sc).size());

        personSc.getPostalAddress().setPostalCode("987");
        assertEquals(1, getPersonServiceBean().count(sc));
        assertEquals(1, getPersonServiceBean().search(sc).size());

        try {
            personSc.getPostalAddress().setPostalCode("");
            assertEquals(3, getPersonServiceBean().count(sc));
            assertEquals(3, getPersonServiceBean().search(sc).size());
            fail();
        } catch (OneCriterionRequiredException e) {

        }
    }

    @Test
    public void findByAddressCountry() throws EntityValidationException, JMSException {
        createPeopleWithAddresses();

        personSc.setPostalAddress(new Address());

        personSc.getPostalAddress().setCountry(getDefaultCountry());
        assertEquals(3, getPersonServiceBean().count(sc));
        assertEquals(3, getPersonServiceBean().search(sc).size());

        Country country = new Country("BBB States", "999", "BB", "BBB");
        PoHibernateUtil.getCurrentSession().save(country);
        PoHibernateUtil.getCurrentSession().flush();
        personSc.getPostalAddress().setCountry(country);
        assertEquals(0, getPersonServiceBean().count(sc));
        assertEquals(0, getPersonServiceBean().search(sc).size());

    }

    @Test
    public void findByStatusCode() throws EntityValidationException, JMSException {
        createPeopleWithAddresses();

        personSc.setStatusCode(EntityStatus.ACTIVE);
        assertEquals(0, getPersonServiceBean().count(sc));
        assertEquals(0, getPersonServiceBean().search(sc).size());

        personSc.setStatusCode(EntityStatus.PENDING);
        assertEquals(3, getPersonServiceBean().count(sc));
        assertEquals(3, getPersonServiceBean().search(sc).size());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void findByStatusCodeEnsureDeprecatedAreNotReturned() throws EntityValidationException, JMSException {
        createPeopleWithAddresses();
        Query query = PoHibernateUtil.getCurrentSession().createQuery("from Person p");
        List<Person> list = query.list();
        Person p = list.iterator().next();
        assertTrue(EntityStatus.PENDING.canTransitionTo(EntityStatus.NULLIFIED));
        p.setStatusCode(EntityStatus.NULLIFIED);
        PoHibernateUtil.getCurrentSession().update(p);
        PoHibernateUtil.getCurrentSession().flush();
        PoHibernateUtil.getCurrentSession().clear();

        personSc.setStatusCode(EntityStatus.PENDING);
        assertEquals(2, getPersonServiceBean().count(sc));
        assertEquals(2, getPersonServiceBean().search(sc).size());
    }
}
