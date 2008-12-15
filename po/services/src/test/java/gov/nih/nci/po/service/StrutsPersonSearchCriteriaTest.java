
package gov.nih.nci.po.service;

import gov.nih.nci.po.data.bo.ClinicalResearchStaff;
import gov.nih.nci.po.data.bo.Email;
import gov.nih.nci.po.data.bo.IdentifiedPerson;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.data.bo.Person;
import gov.nih.nci.po.service.correlation.ClinicalResearchStaffServiceTest;
import gov.nih.nci.po.service.correlation.IdentifiedPersonServiceTest;
import gov.nih.nci.po.service.external.CtepOrganizationImporter;
import gov.nih.nci.po.util.PoHibernateUtil;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author gax
 */
public class StrutsPersonSearchCriteriaTest extends AbstractBeanTest {

    @Test
    public void testFirstLastEmailName() throws Exception {
        PersonServiceBeanTest pst = new PersonServiceBeanTest();
        pst.setDefaultCountry(getDefaultCountry());
        pst.setUpData();
        Long id = pst.createPerson();
        id = pst.createPerson();
        Person p = (Person) PoHibernateUtil.getCurrentSession().load(Person.class, id);
        p.setFirstName("firstName");
        p.setLastName("lastName");
        Email e = new Email("email@5amsolution.com");
        p.getEmail().add(e);
        PoHibernateUtil.getCurrentSession().update(p);

        StrutsPersonSearchCriteria criteria = new StrutsPersonSearchCriteria();
        List<Person> l = criteria.getQuery("", false).list();
        assertEquals(2, l.size());

        criteria.setFirstName("xxx");
        l = criteria.getQuery("", false).list();
        assertEquals(0, l.size());

        criteria.setFirstName("firstname");
        l = criteria.getQuery("", false).list();
        assertEquals(1, l.size());

        criteria.setFirstName(null);
        criteria.setLastName("lastName");
        l = criteria.getQuery("", false).list();
        assertEquals(1, l.size());

        criteria.setFirstName(null);
        criteria.setLastName("last");
        l = criteria.getQuery("", false).list();
        assertEquals(1, l.size());

        criteria.setFirstName(null);
        criteria.setLastName("xxx");
        Number n = (Number) criteria.getQuery("", true).uniqueResult();
        assertEquals(0, n.intValue());

        criteria = new StrutsPersonSearchCriteria();
        criteria.setEmail(e.getValue());
        l = criteria.getQuery("", false).list();
        assertEquals(1, l.size());
    }

    
    @Test
    public void testRoleEmail() throws Exception {

        PersonServiceBeanTest pst = new PersonServiceBeanTest();
        pst.setDefaultCountry(getDefaultCountry());
        pst.setUpData();
        Long id = pst.createPerson();
        id = pst.createPerson();
        Person p = (Person) PoHibernateUtil.getCurrentSession().load(Person.class, id);
        
        ClinicalResearchStaffServiceTest crst = new ClinicalResearchStaffServiceTest();
        crst.setDefaultCountry(getDefaultCountry());
        crst.setUpData();
        crst.testSimpleCreateAndGet();
        ClinicalResearchStaff c = (ClinicalResearchStaff) PoHibernateUtil.getCurrentSession().createCriteria(ClinicalResearchStaff.class).uniqueResult();
        Email e2 = new Email("crs@5amsolutions.com");
        c.getEmail().add(e2);
        PoHibernateUtil.getCurrentSession().update(c);
        
        StrutsPersonSearchCriteria criteria = new StrutsPersonSearchCriteria();
        criteria.setEmail(e2.getValue());
        List<Person> l = criteria.getQuery("", false).list();
        assertEquals(1, l.size());
    }

    @Test
    public void testRoleOrg() throws Exception {

        PersonServiceBeanTest pst = new PersonServiceBeanTest();
        pst.setDefaultCountry(getDefaultCountry());
        pst.setUpData();
        Long id = pst.createPerson();
        Person p = (Person) PoHibernateUtil.getCurrentSession().load(Person.class, id);

        ClinicalResearchStaffServiceTest crst = new ClinicalResearchStaffServiceTest();
        crst.setDefaultCountry(getDefaultCountry());
        crst.setUpData();
        crst.testSimpleCreateAndGet();
        ClinicalResearchStaff c = (ClinicalResearchStaff) PoHibernateUtil.getCurrentSession().createCriteria(ClinicalResearchStaff.class).uniqueResult();

        StrutsPersonSearchCriteria criteria = new StrutsPersonSearchCriteria();
        List<Person> l = criteria.getQuery("", false).list();
        assertEquals(2, l.size());

        Organization o = c.getScoper();
        o.setName("foo Bar and Co.");
        PoHibernateUtil.getCurrentSession().update(o);
        criteria.setOrg("foo");
        l = criteria.getQuery("", false).list();
        assertEquals(1, l.size());
    }

    @Test
    public void testCTEPId() throws Exception {

        PersonServiceBeanTest pst = new PersonServiceBeanTest();
        pst.setDefaultCountry(getDefaultCountry());
        pst.setUpData();
        Long id = pst.createPerson();
        Person p = (Person) PoHibernateUtil.getCurrentSession().load(Person.class, id);

        IdentifiedPersonServiceTest crst = new IdentifiedPersonServiceTest();
        crst.setDefaultCountry(getDefaultCountry());
        crst.setUpData();
        crst.testSimpleCreateAndGet();
        IdentifiedPerson c = (IdentifiedPerson) PoHibernateUtil.getCurrentSession().createCriteria(IdentifiedPerson.class).uniqueResult();

        StrutsPersonSearchCriteria criteria = new StrutsPersonSearchCriteria();
        List<Person> l = criteria.getQuery("", false).list();
        assertEquals(2, l.size());

        criteria.setCtepId("MY_CTEP_ID");
        l = criteria.getQuery("", false).list();
        assertEquals(0, l.size());

        c.getAssignedIdentifier().setExtension("MY_CTEP_ID");
        c.getAssignedIdentifier().setRoot(CtepOrganizationImporter.CTEP_ROOT);
        PoHibernateUtil.getCurrentSession().update(c);
        l = criteria.getQuery("", false).list();
        assertEquals(1, l.size());
    }
}