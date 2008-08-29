package gov.nih.nci.po.service;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;
import gov.nih.nci.po.data.bo.AbstractPerson;
import gov.nih.nci.po.data.bo.Address;
import gov.nih.nci.po.data.bo.Country;
import gov.nih.nci.po.data.bo.EntityStatus;
import gov.nih.nci.po.data.bo.Person;
import gov.nih.nci.po.data.bo.PersonCR;
import gov.nih.nci.po.data.bo.PhoneNumber;
import gov.nih.nci.po.data.bo.RaceCode;
import gov.nih.nci.po.data.bo.SexCode;
import gov.nih.nci.po.util.PoHibernateUtil;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author gax
 */
public class PersonCRServiceBeanTest extends AbstractHibernateTestCase {

    PersonCRServiceBean instance;
    Country country = new Country("name", "123", "US", "USA");

    @Before
    public void setup() {
        PoHibernateUtil.getCurrentSession().save(country);
        instance = EjbTestHelper.getPersonCRServiceBean();
    }

    private void fill(AbstractPerson o) {
        fill(o, country);
    }

    public static void fill(AbstractPerson o, Country country) {
        o.setFirstName("firstName");
        o.setLastName("lastName");
        o.setMiddleName("middleName");
        o.setPrefix("prefix");
        o.getRaces().add(RaceCode.AI_AN);
        o.setSex(SexCode.F);
        o.setSuffix("suffix");

        o.setStatusCode(EntityStatus.NEW);
        Address a = new Address("streetAddressLine", "cityOrMunicipality", "stateOrProvince", "postalCode", country);
        o.setPostalAddress(a);
        o.getFax().add(new PhoneNumber("123-123-1234"));
    }

    @Test
    public void testGetCR() {
        Person o = new Person();
        fill(o);
        o.setStatusDate(new Date());
        PoHibernateUtil.getCurrentSession().save(o);
        PersonCR ocr = new PersonCR(o);
        fill(ocr);
        Long id = (Long) PoHibernateUtil.getCurrentSession().save(ocr);

        PersonCR cr = instance.getCR(id);
        assertSame(ocr, cr);
    }

    @Test
    public void testGetTarget() {
        Person o = new Person();
        PersonCR ocr = new PersonCR(o);
        assertSame(o, instance.getTarget(ocr));
    }

    @Test
    public void testEntityUpdate() {
        class MyTracker extends RuntimeException {
            private static final long serialVersionUID = 1L;} {

        };
        PersonServiceBean service = new PersonServiceBean(){
            @Override
            public void update(Person updatedEntity) {
                throw new MyTracker();
            }
        };
        instance.setPersonServiceBean(service);
        Person entity = new Person();
        try{
            instance.entityUpdate(entity);
            fail();
        }catch(MyTracker x) {
        }
    }

}