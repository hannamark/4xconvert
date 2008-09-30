package gov.nih.nci.po.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.fail;
import gov.nih.nci.po.data.bo.AbstractOrganization;
import gov.nih.nci.po.data.bo.Address;
import gov.nih.nci.po.data.bo.Country;
import gov.nih.nci.po.data.bo.Email;
import gov.nih.nci.po.data.bo.EntityStatus;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.data.bo.OrganizationCR;
import gov.nih.nci.po.util.PoHibernateUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
        o.setAbbreviatedName("abbreviatedName");
        o.setDescription("description");
        o.setName("name");
        o.setStatusCode(EntityStatus.PENDING);
        Address a = new Address("streetAddressLine", "cityOrMunicipality", "stateOrProvince", "postalCode", country);
        o.setPostalAddress(a);
        o.getEmail().add(new Email("foo@example.com"));
    }

    private void fill(AbstractOrganization o) {
        fill(o, country);
    }

    @Test
    public void testGetCR() {
        Organization o = new Organization();
        fill(o);
        o.setStatusDate(new Date());
        PoHibernateUtil.getCurrentSession().save(o);
        OrganizationCR ocr = new OrganizationCR(o);
        fill(ocr);
        ocr.setAbbreviatedName("changed abbr.");
        Long id = (Long) PoHibernateUtil.getCurrentSession().save(ocr);

        PoHibernateUtil.getCurrentSession().flush();
        PoHibernateUtil.getCurrentSession().clear();

        OrganizationCR cr = instance.getById(id);
        assertEquals(ocr.getAbbreviatedName(), cr.getAbbreviatedName());
        assertEquals(1, cr.getTarget().getChangeRequests().size());
    }

    @Test
    public void processCRsBad() {
        List<OrganizationCR> list = new ArrayList<OrganizationCR>();
        list.add(new OrganizationCR(null));
        try{
            instance.processCRs(list);
            fail();
        }catch(IllegalArgumentException nb){
            // null target.
        }

        OrganizationCR ocr1 = new OrganizationCR(new Organization());
        OrganizationCR ocr2 = new OrganizationCR(new Organization());
        fill(ocr1.getTarget());
        fill(ocr1);
        fill(ocr2.getTarget());
        fill(ocr2);
        PoHibernateUtil.getCurrentSession().save(ocr1.getTarget());
        PoHibernateUtil.getCurrentSession().save(ocr2.getTarget());
        PoHibernateUtil.getCurrentSession().save(ocr1);
        PoHibernateUtil.getCurrentSession().save(ocr2);
        list.clear();
        list.add(ocr1);
        list.add(ocr2);
        assertNotSame(ocr1.getTarget(), ocr2.getTarget());
        try{
            instance.processCRs(list);
            fail();
        }catch(IllegalArgumentException nb){
            // different target.
        }
    }

    @Test
    @SuppressWarnings("unchecked")
    public void processCRs() {
        Organization o = new Organization();
        o.setStatusDate(new Date());
        fill(o);
        PoHibernateUtil.getCurrentSession().save(o);
        OrganizationCR ocr1 = new OrganizationCR(o);
        OrganizationCR ocr2 = new OrganizationCR(o);
        fill(ocr1);
        fill(ocr2);
        PoHibernateUtil.getCurrentSession().save(ocr1);
        PoHibernateUtil.getCurrentSession().save(ocr2);
        List<OrganizationCR> list = PoHibernateUtil.getCurrentSession().createCriteria(OrganizationCR.class).list();
        o = list.get(0).getTarget();
        o.setAbbreviatedName("new abbreviatedName");
        instance.processCRs(list);
        PoHibernateUtil.getCurrentSession().flush();
        PoHibernateUtil.getCurrentSession().clear();
        // check if org was updated.
        Organization o2 = (Organization) PoHibernateUtil.getCurrentSession().createCriteria(Organization.class).uniqueResult();
        assertEquals(o.getId(), o2.getId());
        assertNotSame(o, o2);
        assertEquals("new abbreviatedName", o2.getAbbreviatedName());
        // check if CRs are no longer available.
        // see https://jira.5amsolutions.com/browse/PO-492
        list = PoHibernateUtil.getCurrentSession().createCriteria(OrganizationCR.class).list();
        assertEquals(0, list.size());
    }

}