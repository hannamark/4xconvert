package gov.nih.nci.po.data.bo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import gov.nih.nci.po.data.common.Country;
import gov.nih.nci.po.service.AbstractHibernateTestCase;
import gov.nih.nci.po.util.PoHibernateUtil;

import java.io.Serializable;

import org.hibernate.validator.InvalidStateException;
import org.junit.Before;
import org.junit.Test;


public class AddressTest extends AbstractHibernateTestCase {
    private Country validCountry;

    @Before
    public void init() {
        Country country = new Country("USA", "840", "US", "USA");
        Serializable cid = PoHibernateUtil.getCurrentSession().save(country);
        validCountry = (Country) PoHibernateUtil.getCurrentSession().get(Country.class, cid);
        assertNotNull(validCountry);
        assertEquals(country, validCountry);
    }

    @Test
    public void createAddress() {
        Address address = new Address("a", "b", "c", "d", validCountry);
        Serializable id = PoHibernateUtil.getCurrentSession().save(address);
        assertNotNull(id);
        Address saved = (Address) PoHibernateUtil.getCurrentSession().get(Address.class, id);
        assertNotNull(saved);
        assertEquals(address, saved);
    }

    private void saveExpectedException(Address address, String propertyName) {
        try {
            PoHibernateUtil.getCurrentSession().save(address);
            PoHibernateUtil.getCurrentSession().flush();
            fail("expected to not-null constraint violation on param1");
        } catch (org.hibernate.PropertyValueException e) {
            assertTrue(e.getMessage().endsWith(Address.class.getName() + "." + propertyName));
        } catch (InvalidStateException e) {
            assertTrue(e.getMessage().endsWith("validation failed for: " + address.getClass().getName()));
        }
    }

    @Test
    public void createAddressWithMissingStreetAddressLine() {
        Address address = new Address(null, "b", "c", "d", validCountry);
        saveExpectedException(address, "streetAddressLine");
    }

    @Test
    public void createAddressWithMissingCityOrMunicipality() {
        Address address = new Address("a", null, "c", "d", validCountry);
        saveExpectedException(address, "cityOrMunicipality");
    }

    @Test
    public void createAddressWithMissingStateOrProvince() {
        Address address = new Address("a", "b", null, "d", validCountry);
        PoHibernateUtil.getCurrentSession().save(address);
    }

    @Test
    public void createAddressWithMissingPostalCode() {
        Address address = new Address("a", "b", "c", null, validCountry);
        saveExpectedException(address, "postalCode");
    }

    @Test
    public void createAddressWithMissingCountry() {
        Address address = new Address("a", "b", "c", "d", null);
        saveExpectedException(address, "country");
    }

    @Test
    public void createAddressWithEmptyStreetAddressLine() {
        Address address = new Address("", "b", "c", "d", validCountry);
        saveExpectedException(address, "streetAddressLine");
    }

    @Test
    public void createAddressWithEmptyCityOrMunicipality() {
        Address address = new Address("a", "", "c", "d", validCountry);
        saveExpectedException(address, "cityOrMunicipality");
    }

    @Test
    public void createAddressWithEmptyStateOrProvince() {
        Address address = new Address("a", "b", "", "d", validCountry);
        PoHibernateUtil.getCurrentSession().save(address);
    }

    @Test
    public void createAddressWithEmptyPostalCode() {
        Address address = new Address("a", "b", "c", "", validCountry);
        saveExpectedException(address, "postalCode");
    }
}
