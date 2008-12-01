package gov.nih.nci.po.service;

import gov.nih.nci.po.data.bo.ClinicalResearchStaff;
import gov.nih.nci.po.data.bo.ClinicalResearchStaffCR;
import gov.nih.nci.po.data.bo.HealthCareProvider;
import gov.nih.nci.po.data.bo.HealthCareProviderCR;
import gov.nih.nci.po.data.bo.OrganizationalContact;
import gov.nih.nci.po.data.bo.OrganizationalContactCR;
import gov.nih.nci.po.data.bo.RoleStatus;
import gov.nih.nci.po.service.correlation.ClinicalResearchStaffServiceTest;
import gov.nih.nci.po.service.correlation.HealthCareProviderServiceTest;
import gov.nih.nci.po.service.correlation.OrganizationalContactServiceTest;
import gov.nih.nci.services.correlation.ClinicalResearchStaffDTO;
import gov.nih.nci.services.correlation.HealthCareProviderDTO;
import gov.nih.nci.services.correlation.OrganizationalContactDTO;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.po.data.bo.EntityStatus;
import gov.nih.nci.po.data.bo.Person;
import gov.nih.nci.po.util.PoHibernateUtil;
import gov.nih.nci.po.util.PoXsnapshotHelper;
import gov.nih.nci.services.person.PersonDTO;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class CuratePersonSearchCriteriaTestDb extends AbstractHibernateTestCase {
    CuratePersonSearchCriteria sc = new CuratePersonSearchCriteria();
    private PersonServiceBeanTest pst;

    private long orgId;

    @Before
    public void setupData() throws EntityValidationException {
        pst = new PersonServiceBeanTest();
        pst.loadData();
        pst.setUpData();
        orgId = pst.createPerson();
    }

    @Test
    public void findByStatusPENDING() {
        @SuppressWarnings("unchecked")
        List<Person> results = sc.getQuery("", false).list();
        assertEquals(1, results.size());
        assertEquals(orgId, results.get(0).getId().longValue());
    }

    @Test
    public void findNoneByStatusNotPENDING() {
        @SuppressWarnings("unchecked")
        List<Person> list = PoHibernateUtil.getCurrentSession().createCriteria(Person.class).list();
        Person o = list.iterator().next();
        assertTrue(EntityStatus.PENDING.canTransitionTo(EntityStatus.ACTIVE));
        assertTrue(EntityStatus.ACTIVE.canTransitionTo(EntityStatus.INACTIVE));
        o.setStatusCode(EntityStatus.ACTIVE);
        PoHibernateUtil.getCurrentSession().update(o);
        PoHibernateUtil.getCurrentSession().flush();

        @SuppressWarnings("unchecked")
        List<Person> results = sc.getQuery("", false).list();
        assertEquals(0, results.size());
    }

    @Test
    public void findByStatusNotPENDINGButHasCRs() throws EntityValidationException {
        @SuppressWarnings("unchecked")
        List<Person> list = PoHibernateUtil.getCurrentSession().createCriteria(Person.class).list();
        Person o = list.iterator().next();
        assertTrue(EntityStatus.PENDING.canTransitionTo(EntityStatus.ACTIVE));
        assertTrue(EntityStatus.ACTIVE.canTransitionTo(EntityStatus.INACTIVE));
        o.setStatusCode(EntityStatus.ACTIVE);
        PoHibernateUtil.getCurrentSession().update(o);
        PoHibernateUtil.getCurrentSession().flush();

        PersonDTO cr = (PersonDTO) PoXsnapshotHelper.createSnapshot(o);
        EjbTestHelper.getPersonEntityServiceBean().updatePerson(cr);

        @SuppressWarnings("unchecked")
        List<Person> results = sc.getQuery("", false).list();
        assertEquals(1, results.size());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void findByOrganizationalContact() throws Exception {

        OrganizationalContactServiceTest test = new OrganizationalContactServiceTest();
        test.setDefaultCountry(pst.getDefaultCountry());
        test.setUpData();
        test.testSimpleCreateAndGet();
        OrganizationalContact ro = (OrganizationalContact) PoHibernateUtil.getCurrentSession().createCriteria(OrganizationalContact.class).uniqueResult();
        assertEquals(RoleStatus.PENDING, ro.getStatus());
        ro.getPlayer().setStatusCode(EntityStatus.ACTIVE);
        PoHibernateUtil.getCurrentSession().update(ro.getPlayer());

        List<Person> results = sc.getQuery("", false).list();
        assertEquals(2, results.size());

        // exclude the old org
        Person o = (Person) PoHibernateUtil.getCurrentSession().load(Person.class, orgId);
        o.setStatusCode(EntityStatus.ACTIVE);
        PoHibernateUtil.getCurrentSession().update(o);
        results = sc.getQuery("", false).list();
        assertEquals(1, results.size());

        // exclude the role
        ro.setStatus(RoleStatus.ACTIVE);
        PoHibernateUtil.getCurrentSession().update(ro);
        results = sc.getQuery("", false).list();
        assertEquals(0, results.size());

        // add change request to the role.
        OrganizationalContactDTO rodto = (OrganizationalContactDTO) PoXsnapshotHelper.createSnapshot(ro);
        EjbTestHelper.getOrganizationalContactCorrelationServiceRemote().updateCorrelation(rodto);
        PoHibernateUtil.getCurrentSession().flush();
        results = sc.getQuery("", false).list();
        assertEquals(1, results.size());

        // exclude the role's CR
        OrganizationalContactCR rocr = (OrganizationalContactCR) PoHibernateUtil.getCurrentSession().createCriteria(OrganizationalContactCR.class).uniqueResult();
        rocr.setProcessed(true);
        PoHibernateUtil.getCurrentSession().update(ro);
        results = sc.getQuery("", false).list();
        assertEquals(0, results.size());

        // nullified role
        ro.setStatus(RoleStatus.NULLIFIED);
        PoHibernateUtil.getCurrentSession().update(ro);
        rocr.setProcessed(false);
        PoHibernateUtil.getCurrentSession().update(ro);
        results = sc.getQuery("", false).list();
        assertEquals(0, results.size());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void findByClinicalResearchStaff() throws Exception {

        ClinicalResearchStaffServiceTest test = new ClinicalResearchStaffServiceTest();
        test.setDefaultCountry(pst.getDefaultCountry());
        test.setUpData();
        test.testSimpleCreateAndGet();
        ClinicalResearchStaff ro = (ClinicalResearchStaff) PoHibernateUtil.getCurrentSession().createCriteria(ClinicalResearchStaff.class).uniqueResult();
        assertEquals(RoleStatus.PENDING, ro.getStatus());
        ro.getPlayer().setStatusCode(EntityStatus.ACTIVE);
        PoHibernateUtil.getCurrentSession().update(ro.getPlayer());

        List<Person> results = sc.getQuery("", false).list();
        assertEquals(2, results.size());

        // exclude the old org
        Person o = (Person) PoHibernateUtil.getCurrentSession().load(Person.class, orgId);
        o.setStatusCode(EntityStatus.ACTIVE);
        PoHibernateUtil.getCurrentSession().update(o);
        results = sc.getQuery("", false).list();
        assertEquals(1, results.size());

        // exclude the role
        ro.setStatus(RoleStatus.ACTIVE);
        PoHibernateUtil.getCurrentSession().update(ro);
        results = sc.getQuery("", false).list();
        assertEquals(0, results.size());

        // add change request to the role.
        ClinicalResearchStaffDTO rodto = (ClinicalResearchStaffDTO) PoXsnapshotHelper.createSnapshot(ro);
        EjbTestHelper.getClinicalResearchStaffCorrelationServiceRemote().updateCorrelation(rodto);
        PoHibernateUtil.getCurrentSession().flush();
        results = sc.getQuery("", false).list();
        assertEquals(1, results.size());

        // exclude the role's CR
        ClinicalResearchStaffCR rocr = (ClinicalResearchStaffCR) PoHibernateUtil.getCurrentSession().createCriteria(ClinicalResearchStaffCR.class).uniqueResult();
        rocr.setProcessed(true);
        PoHibernateUtil.getCurrentSession().update(ro);
        results = sc.getQuery("", false).list();
        assertEquals(0, results.size());

        // nullified role
        ro.setStatus(RoleStatus.NULLIFIED);
        PoHibernateUtil.getCurrentSession().update(ro);
        rocr.setProcessed(false);
        PoHibernateUtil.getCurrentSession().update(ro);
        results = sc.getQuery("", false).list();
        assertEquals(0, results.size());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void findByHealthCareProvider() throws Exception {

        HealthCareProviderServiceTest test = new HealthCareProviderServiceTest();
        test.setDefaultCountry(pst.getDefaultCountry());
        test.setUpData();
        test.testSimpleCreateAndGet();
        HealthCareProvider ro = (HealthCareProvider) PoHibernateUtil.getCurrentSession().createCriteria(HealthCareProvider.class).uniqueResult();
        assertEquals(RoleStatus.PENDING, ro.getStatus());
        ro.getPlayer().setStatusCode(EntityStatus.ACTIVE);
        PoHibernateUtil.getCurrentSession().update(ro.getPlayer());

        List<Person> results = sc.getQuery("", false).list();
        assertEquals(2, results.size());

        // exclude the old org
        Person o = (Person) PoHibernateUtil.getCurrentSession().load(Person.class, orgId);
        o.setStatusCode(EntityStatus.ACTIVE);
        PoHibernateUtil.getCurrentSession().update(o);
        results = sc.getQuery("", false).list();
        assertEquals(1, results.size());

        // exclude the role
        ro.setStatus(RoleStatus.ACTIVE);
        PoHibernateUtil.getCurrentSession().update(ro);
        results = sc.getQuery("", false).list();
        assertEquals(0, results.size());

        // add change request to the role.
        HealthCareProviderDTO rodto = (HealthCareProviderDTO) PoXsnapshotHelper.createSnapshot(ro);
        EjbTestHelper.getHealthCareProviderCorrelationServiceRemote().updateCorrelation(rodto);
        PoHibernateUtil.getCurrentSession().flush();
        results = sc.getQuery("", false).list();
        assertEquals(1, results.size());

        // exclude the role's CR
        HealthCareProviderCR rocr = (HealthCareProviderCR) PoHibernateUtil.getCurrentSession().createCriteria(HealthCareProviderCR.class).uniqueResult();
        rocr.setProcessed(true);
        PoHibernateUtil.getCurrentSession().update(ro);
        results = sc.getQuery("", false).list();
        assertEquals(0, results.size());

        // nullified role
        ro.setStatus(RoleStatus.NULLIFIED);
        PoHibernateUtil.getCurrentSession().update(ro);
        rocr.setProcessed(false);
        PoHibernateUtil.getCurrentSession().update(ro);
        results = sc.getQuery("", false).list();
        assertEquals(0, results.size());
    }
}
