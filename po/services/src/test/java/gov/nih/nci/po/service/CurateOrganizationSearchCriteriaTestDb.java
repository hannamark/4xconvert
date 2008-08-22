package gov.nih.nci.po.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.po.data.bo.EntityStatus;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.data.bo.OrganizationCR;
import gov.nih.nci.po.util.PoHibernateUtil;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
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
        Query query = PoHibernateUtil.getCurrentSession().createQuery("from Organization o");
        List<Organization> list = query.list();
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
    public void findByStatusNotNEWButHasCRs() {
        Query query = PoHibernateUtil.getCurrentSession().createQuery("from Organization o");
        List<Organization> list = query.list();
        Organization o = list.iterator().next();
        assertTrue(EntityStatus.NEW.canTransitionTo(EntityStatus.CURATED));
        assertTrue(EntityStatus.CURATED.canTransitionTo(EntityStatus.DEPRECATED));
        o.setStatusCode(EntityStatus.CURATED);
        PoHibernateUtil.getCurrentSession().update(o);
        PoHibernateUtil.getCurrentSession().flush();
        
        OrganizationCR cr = new OrganizationCR(o);
        PoHibernateUtil.getCurrentSession().save(cr);
        PoHibernateUtil.getCurrentSession().flush();
        PoHibernateUtil.getCurrentSession().clear();
        
        @SuppressWarnings("unchecked")
        List<Organization> results = sc.getQuery("", false).list();
        assertEquals(1, results.size());
    }

}
