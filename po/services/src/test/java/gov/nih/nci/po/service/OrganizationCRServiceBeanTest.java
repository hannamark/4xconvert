package gov.nih.nci.po.service;

import static org.junit.Assert.assertEquals;
import gov.nih.nci.po.data.bo.AbstractOrganization;
import gov.nih.nci.po.data.bo.Address;
import gov.nih.nci.po.data.bo.Country;
import gov.nih.nci.po.data.bo.Email;
import gov.nih.nci.po.data.bo.EntityStatus;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.data.bo.OrganizationCR;
import gov.nih.nci.po.data.bo.URL;
import gov.nih.nci.po.util.PoHibernateUtil;

import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author gax
 */
public class OrganizationCRServiceBeanTest extends AbstractHibernateTestCase {

    OrganizationCRServiceBean instance;
    Country country = new Country("name", "123", "US", "USA");

    @Before
    public void setup() {
        PoHibernateUtil.getCurrentSession().save(country);
        instance = EjbTestHelper.getOrganizationCRServiceBean();
    }

    public static void fill(AbstractOrganization o, Country country) {
        o.setName("name");
        o.setStatusCode(EntityStatus.PENDING);
        Address a = new Address("streetAddressLine", "cityOrMunicipality", "stateOrProvince", "postalCode", country);
        o.setPostalAddress(a);
        o.getEmail().add(new Email("foo@example.com"));
        o.getUrl().add(new URL("http://example.com"));
    }

    private void fill(AbstractOrganization o) {
        fill(o, country);
    }

    @Test
    public void testGetCR() {
        Organization o = new Organization();
        fill(o);
        PoHibernateUtil.getCurrentSession().save(o);
        OrganizationCR ocr = new OrganizationCR(o);
        fill(ocr);
        Long id = (Long) PoHibernateUtil.getCurrentSession().save(ocr);

        PoHibernateUtil.getCurrentSession().flush();
        PoHibernateUtil.getCurrentSession().clear();

        OrganizationCR cr = instance.getById(id);
        assertEquals(1, cr.getTarget().getChangeRequests().size());
    }
}