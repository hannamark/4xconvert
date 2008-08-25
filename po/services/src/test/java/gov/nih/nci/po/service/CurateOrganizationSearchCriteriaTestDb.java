package gov.nih.nci.po.service;

import gov.nih.nci.po.util.PoXsnapshotHelper;
import gov.nih.nci.services.organization.OrganizationDTO;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.po.data.bo.EntityStatus;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.util.PoHibernateUtil;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

public class CurateOrganizationSearchCriteriaTestDb extends AbstractHibernateTestCase {
    private static final Logger LOG = Logger.getLogger(CurateOrganizationSearchCriteriaTestDb.class);

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
    public void findByStatusNEW() {
        @SuppressWarnings("unchecked")
        List<Organization> results = sc.getQuery("", false).list();
        assertEquals(1, results.size());
        assertEquals(orgId, results.get(0).getId().longValue());
    }
    
    @Test
    public void findNoneByStatusNotNEW() {
        List<Organization> list = PoHibernateUtil.getCurrentSession().createCriteria(Organization.class).list();
        Organization o = list.iterator().next();
        assertTrue(EntityStatus.NEW.canTransitionTo(EntityStatus.CURATED));
        assertTrue(EntityStatus.CURATED.canTransitionTo(EntityStatus.DEPRECATED));
        o.setStatusCode(EntityStatus.CURATED);
        PoHibernateUtil.getCurrentSession().update(o);
        PoHibernateUtil.getCurrentSession().flush();
        
        @SuppressWarnings("unchecked")
        List<Organization> results = sc.getQuery("", false).list();
        assertEquals(0, results.size());
    }
    
    @Test
    public void findByStatusNotNEWButHasCRs() throws EntityValidationException {
        List<Organization> list = PoHibernateUtil.getCurrentSession().createCriteria(Organization.class).list();
        Organization o = list.iterator().next();
        assertTrue(EntityStatus.NEW.canTransitionTo(EntityStatus.CURATED));
        assertTrue(EntityStatus.CURATED.canTransitionTo(EntityStatus.DEPRECATED));
        o.setStatusCode(EntityStatus.CURATED);
        PoHibernateUtil.getCurrentSession().update(o);
        PoHibernateUtil.getCurrentSession().flush();
        
        OrganizationDTO cr = PoXsnapshotHelper.createSnapshot(o);
        EjbTestHelper.getOrganizationEntityServiceBean().updateOrganization(cr);
        PoHibernateUtil.getCurrentSession().flush();
        PoHibernateUtil.getCurrentSession().clear();
        
        @SuppressWarnings("unchecked")
        List<Organization> results = sc.getQuery("", false).list();
        assertEquals(1, results.size());
    }

}
