

package gov.nih.nci.po.data.convert;

import gov.nih.nci.po.data.bo.Country;
import gov.nih.nci.po.service.AbstractHibernateTestCase;
import gov.nih.nci.po.util.PoHibernateUtil;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author gax
 */
public class PoCountryResolverTest extends AbstractHibernateTestCase {

    PoCountryResolver resolver;
    
    @Before 
    public void setup() {
        resolver = new PoCountryResolver();
        Country c = new Country("name", "111", "US", "USA");
        PoHibernateUtil.getCurrentSession().save(c);
        PoHibernateUtil.getCurrentSession().flush();
    }
    
    @Test
    public void testGetCountryByAlpha3() {
        Country c = resolver.getCountryByAlpha3("USA");
        assertEquals("USA", c.getAlpha3());
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testGetCountryByAlpha3BadCode() {
        resolver.getCountryByAlpha3("XXX");
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testGetCountryByAlpha3NullCode() {
        resolver.getCountryByAlpha3(null);
    }

}