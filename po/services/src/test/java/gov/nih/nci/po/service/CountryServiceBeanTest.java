package gov.nih.nci.po.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.po.data.bo.Country;
import gov.nih.nci.po.data.bo.State;
import gov.nih.nci.po.util.PoHibernateUtil;

import java.util.List;

import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author gax
 */
public class CountryServiceBeanTest extends AbstractHibernateTestCase {

    private CountryServiceBean countryService;
    private Country c;
    private State state;

    @Before
    public void init() {
        countryService = EjbTestHelper.getCountryServiceBean();

        List<Country> l = countryService.getCountries();
        assertTrue(l.isEmpty());

        Session s = PoHibernateUtil.getCurrentSession();
        c = new Country("Super Country", "999", "ZZ", "ZZZ");
        s.save(c);

        countryService.reloadCountries();

        l = countryService.getCountries();
        assertEquals(1, l.size());
        assertEquals(c, l.get(0));
    }

    @Test
    public void getCountries() {

        Country retrievedCountry = countryService.getCountry(c.getId());
        assertEquals(c, retrievedCountry);

        retrievedCountry = countryService.getCountryByAlpha2("ZZ");
        assertEquals(c, retrievedCountry);

        retrievedCountry = countryService.getCountryByAlpha2("XX");
        assertEquals(null, retrievedCountry);
    }

    @Test
    public void testGetCountryByAlpha3() {
        Country c2 = countryService.getCountryByAlpha3("ZZZ");
        assertEquals("ZZZ", c2.getAlpha3());
    }
    
    @Test
    public void testGetCountryByName() {
        Country c2 = countryService.getCountryByName("Super Country");
        assertEquals("Super Country", c2.getName());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetCountryByAlpha3BadCode() {
        countryService.getCountryByAlpha3("XXX");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetCountryByAlpha3NullCode() {
        countryService.getCountryByAlpha3(null);
    }

    @Test
    public void testGetStateByCodeForCountry() {
        Session s = PoHibernateUtil.getCurrentSession();
        state = new State();
        state.setCode("VA");
        state.setName("VIRGINIA");
        state.setCountry(c);
        s.save(state);
        
        Country c2 = new Country("Super Country2", "888", "AA", "AAA");
        s.save(c2);
        
        s.flush();
        s.clear();
        
        State result = null;
        
        result = countryService.getStateByCode(c, "VA");
        assertNotNull(result);
        assertEquals("VA", result.getCode());
        
        result = countryService.getStateByCode(c, "AA");
        assertNull(result);
        
        result = countryService.getStateByCode(c2, "VA");
        assertNull(result);
        
        result = countryService.getStateByCode(null, "VA");
        assertNull(result);

    }
}