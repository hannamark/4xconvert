package gov.nih.nci.po.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.po.data.bo.Country;
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

    @Before
    public void init() {
        countryService = EjbTestHelper.getConfigurationBean();

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
        Country c = countryService.getCountryByAlpha3("ZZZ");
        assertEquals("ZZZ", c.getAlpha3());
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testGetCountryByAlpha3BadCode() {
        countryService.getCountryByAlpha3("XXX");
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testGetCountryByAlpha3NullCode() {
        countryService.getCountryByAlpha3(null);
    }
}