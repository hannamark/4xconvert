package gov.nih.nci.po.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.po.data.bo.EntityStatus;
import gov.nih.nci.po.data.bo.HealthCareFacility;
import gov.nih.nci.po.data.bo.HealthCareFacilityCR;
import gov.nih.nci.po.data.bo.IdentifiedOrganization;
import gov.nih.nci.po.data.bo.IdentifiedOrganizationCR;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.data.bo.OversightCommittee;
import gov.nih.nci.po.data.bo.OversightCommitteeCR;
import gov.nih.nci.po.data.bo.ResearchOrganization;
import gov.nih.nci.po.data.bo.ResearchOrganizationCR;
import gov.nih.nci.po.data.bo.RoleStatus;
import gov.nih.nci.po.service.correlation.HealthCareFacilityServiceTest;
import gov.nih.nci.po.service.correlation.IdentifiedOrganizationServiceTest;
import gov.nih.nci.po.service.correlation.OversightCommitteeServiceTest;
import gov.nih.nci.po.service.correlation.ResearchOrganizationServiceTest;
import gov.nih.nci.po.util.PoHibernateUtil;
import gov.nih.nci.po.util.PoXsnapshotHelper;
import gov.nih.nci.services.correlation.HealthCareFacilityDTO;
import gov.nih.nci.services.correlation.IdentifiedOrganizationDTO;
import gov.nih.nci.services.correlation.OversightCommitteeDTO;
import gov.nih.nci.services.correlation.ResearchOrganizationDTO;
import gov.nih.nci.services.organization.OrganizationDTO;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class CurateOrganizationSearchCriteriaTestDb extends AbstractHibernateTestCase {
    CurateOrganizationSearchCriteria sc = new CurateOrganizationSearchCriteria();
    private OrganizationServiceBeanTest ost;

    private long orgId;


    @Before
    public void setupData() throws EntityValidationException {
        ost = new OrganizationServiceBeanTest();
        ost.loadData();
        ost.setUpData();
        orgId = ost.createOrganization();
    }

    @Test
    public void findByStatusPENDING() {
        @SuppressWarnings("unchecked")
        List<Organization> results = sc.getQuery("", false).list();
        assertEquals(1, results.size());
        assertEquals(orgId, results.get(0).getId().longValue());
    }

    @Test
    public void findNoneByStatusNotPENDING() {
        @SuppressWarnings("unchecked")
        List<Organization> list = PoHibernateUtil.getCurrentSession().createCriteria(Organization.class).list();
        Organization o = list.iterator().next();
        assertTrue(EntityStatus.PENDING.canTransitionTo(EntityStatus.ACTIVE));
        assertTrue(EntityStatus.ACTIVE.canTransitionTo(EntityStatus.INACTIVE));
        o.setStatusCode(EntityStatus.ACTIVE);
        PoHibernateUtil.getCurrentSession().update(o);
        PoHibernateUtil.getCurrentSession().flush();

        @SuppressWarnings("unchecked")
        List<Organization> results = sc.getQuery("", false).list();
        assertEquals(0, results.size());
    }

    @Test
    public void findByStatusNotPENDINGButHasCRs() throws EntityValidationException {
        @SuppressWarnings("unchecked")
        List<Organization> list = PoHibernateUtil.getCurrentSession().createCriteria(Organization.class).list();
        Organization o = list.iterator().next();
        assertTrue(EntityStatus.PENDING.canTransitionTo(EntityStatus.ACTIVE));
        assertTrue(EntityStatus.ACTIVE.canTransitionTo(EntityStatus.INACTIVE));
        o.setStatusCode(EntityStatus.ACTIVE);
        PoHibernateUtil.getCurrentSession().update(o);
        PoHibernateUtil.getCurrentSession().flush();

        OrganizationDTO cr = (OrganizationDTO) PoXsnapshotHelper.createSnapshot(o);
        EjbTestHelper.getOrganizationEntityServiceBean().updateOrganization(cr);
        PoHibernateUtil.getCurrentSession().flush();
        PoHibernateUtil.getCurrentSession().clear();

        @SuppressWarnings("unchecked")
        List<Organization> results = sc.getQuery("", false).list();
        assertEquals(1, results.size());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void findByIdentifiedOrganization() throws Exception {

        IdentifiedOrganizationServiceTest test = new IdentifiedOrganizationServiceTest();
        test.setDefaultCountry(ost.getDefaultCountry());
        test.setUpData();

        test.testSimpleCreateAndGet();
        IdentifiedOrganization ro = (IdentifiedOrganization) PoHibernateUtil.getCurrentSession().createCriteria(IdentifiedOrganization.class).uniqueResult();
        assertEquals(RoleStatus.PENDING, ro.getStatus());
        ro.getPlayer().setStatusCode(EntityStatus.ACTIVE);
        PoHibernateUtil.getCurrentSession().update(ro.getPlayer());

        List<Organization> results = sc.getQuery("", false).list();
        assertEquals(2, results.size());

        // exclude the old org
        Organization o = (Organization) PoHibernateUtil.getCurrentSession().load(Organization.class, orgId);
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
        IdentifiedOrganizationDTO rodto = (IdentifiedOrganizationDTO) PoXsnapshotHelper.createSnapshot(ro);
        EjbTestHelper.getIdentifiedOrganizationServiceBeanAsRemote().updateCorrelation(rodto);
        PoHibernateUtil.getCurrentSession().flush();
        results = sc.getQuery("", false).list();
        assertEquals(1, results.size());

        // exclude the role's CR
        IdentifiedOrganizationCR rocr = (IdentifiedOrganizationCR) PoHibernateUtil.getCurrentSession().createCriteria(IdentifiedOrganizationCR.class).uniqueResult();
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
    public void findByHealthCareFacility() throws Exception {

        HealthCareFacilityServiceTest test = new HealthCareFacilityServiceTest();
        test.setDefaultCountry(ost.getDefaultCountry());
        test.setUpData();

        test.testSimpleCreateAndGet();
        HealthCareFacility ro = (HealthCareFacility) PoHibernateUtil.getCurrentSession().createCriteria(HealthCareFacility.class).uniqueResult();
        assertEquals(RoleStatus.PENDING, ro.getStatus());
        ro.getPlayer().setStatusCode(EntityStatus.ACTIVE);
        PoHibernateUtil.getCurrentSession().update(ro.getPlayer());

        List<Organization> results = sc.getQuery("", false).list();
        assertEquals(3, results.size());

        // exclude the old org
        assertEquals(3, PoHibernateUtil.getCurrentSession().createSQLQuery("update organization set status = 'ACTIVE'").executeUpdate());
        results = sc.getQuery("", false).list();
        assertEquals(1, results.size());

        // exclude the role
        ro.setStatus(RoleStatus.ACTIVE);
        PoHibernateUtil.getCurrentSession().update(ro);
        results = sc.getQuery("", false).list();
        assertEquals(0, results.size());

        // add change request to the role.
        HealthCareFacilityDTO rodto = (HealthCareFacilityDTO) PoXsnapshotHelper.createSnapshot(ro);
        EjbTestHelper.getHealthCareFacilityCorrelationServiceRemote().updateCorrelation(rodto);
        PoHibernateUtil.getCurrentSession().flush();
        results = sc.getQuery("", false).list();
        assertEquals(1, results.size());

        // exclude the role's CR
        HealthCareFacilityCR rocr = (HealthCareFacilityCR) PoHibernateUtil.getCurrentSession().createCriteria(HealthCareFacilityCR.class).uniqueResult();
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
    public void findByOversightCommittee() throws Exception {

        OversightCommitteeServiceTest test = new OversightCommitteeServiceTest();
        test.setDefaultCountry(ost.getDefaultCountry());
        test.setUpData();
        test.setupType();
        test.testSimpleCreateAndGet();
        OversightCommittee ro = (OversightCommittee) PoHibernateUtil.getCurrentSession().createCriteria(OversightCommittee.class).uniqueResult();
        assertEquals(RoleStatus.PENDING, ro.getStatus());
        ro.getPlayer().setStatusCode(EntityStatus.ACTIVE);
        PoHibernateUtil.getCurrentSession().update(ro.getPlayer());

        List<Organization> results = sc.getQuery("", false).list();
        assertEquals(2, results.size());

        // exclude the old org
        Organization o = (Organization) PoHibernateUtil.getCurrentSession().load(Organization.class, orgId);
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
        OversightCommitteeDTO rodto = (OversightCommitteeDTO) PoXsnapshotHelper.createSnapshot(ro);
        EjbTestHelper.getOversightCommitteeCorrelationServiceRemote().updateCorrelation(rodto);
        PoHibernateUtil.getCurrentSession().flush();
        results = sc.getQuery("", false).list();
        assertEquals(1, results.size());

        // exclude the role's CR
        OversightCommitteeCR rocr = (OversightCommitteeCR) PoHibernateUtil.getCurrentSession().createCriteria(OversightCommitteeCR.class).uniqueResult();
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
    public void findByResearchOrganization() throws Exception {

        ResearchOrganizationServiceTest test = new ResearchOrganizationServiceTest();
        test.setDefaultCountry(ost.getDefaultCountry());
        test.setUpData();
        test.setupType();
        test.testSimpleCreateAndGet();
        ResearchOrganization ro = (ResearchOrganization) PoHibernateUtil.getCurrentSession().createCriteria(ResearchOrganization.class).uniqueResult();
        assertEquals(RoleStatus.PENDING, ro.getStatus());
        ro.getPlayer().setStatusCode(EntityStatus.ACTIVE);
        PoHibernateUtil.getCurrentSession().update(ro.getPlayer());

        List<Organization> results = sc.getQuery("", false).list();
        assertEquals(2, results.size());

        // exclude the old org
        Organization o = (Organization) PoHibernateUtil.getCurrentSession().load(Organization.class, orgId);
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
        ResearchOrganizationDTO rodto = (ResearchOrganizationDTO) PoXsnapshotHelper.createSnapshot(ro);
        EjbTestHelper.getResearchOrganizationCorrelationServiceRemote().updateCorrelation(rodto);
        PoHibernateUtil.getCurrentSession().flush();
        results = sc.getQuery("", false).list();
        assertEquals(1, results.size());

        // exclude the role's CR
        ResearchOrganizationCR rocr = (ResearchOrganizationCR) PoHibernateUtil.getCurrentSession().createCriteria(ResearchOrganizationCR.class).uniqueResult();
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
