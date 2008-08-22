package gov.nih.nci.po.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.po.data.bo.EntityStatus;
import gov.nih.nci.po.data.bo.Person;
import gov.nih.nci.po.data.bo.PersonCR;
import gov.nih.nci.po.util.PoHibernateUtil;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.junit.Before;
import org.junit.Test;

public class CuratePersonSearchCriteriaTestDb extends AbstractHibernateTestCase {
    private static final Logger LOG = Logger.getLogger(CuratePersonSearchCriteriaTestDb.class);

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
    public void findByStatusNEW() {
        @SuppressWarnings("unchecked")
        List<Person> results = sc.getQuery("", false).list();
        assertEquals(1, results.size());
        assertEquals(orgId, results.get(0).getId().longValue());
    }
    
    @Test
    public void findNoneByStatusNotNEW() {
        Query query = PoHibernateUtil.getCurrentSession().createQuery("from Person p");
        List<Person> list = query.list();
        Person o = list.iterator().next();
        assertTrue(EntityStatus.NEW.canTransitionTo(EntityStatus.CURATED));
        assertTrue(EntityStatus.CURATED.canTransitionTo(EntityStatus.DEPRECATED));
        o.setStatusCode(EntityStatus.CURATED);
        PoHibernateUtil.getCurrentSession().update(o);
        PoHibernateUtil.getCurrentSession().flush();
        
        @SuppressWarnings("unchecked")
        List<Person> results = sc.getQuery("", false).list();
        assertEquals(0, results.size());
    }
    
    @Test
    public void findByStatusNotNEWButHasCRs() {
        Query query = PoHibernateUtil.getCurrentSession().createQuery("from Person o");
        List<Person> list = query.list();
        Person o = list.iterator().next();
        assertTrue(EntityStatus.NEW.canTransitionTo(EntityStatus.CURATED));
        assertTrue(EntityStatus.CURATED.canTransitionTo(EntityStatus.DEPRECATED));
        o.setStatusCode(EntityStatus.CURATED);
        PoHibernateUtil.getCurrentSession().update(o);
        PoHibernateUtil.getCurrentSession().flush();
        
        PersonCR cr = new PersonCR(o);
        PoHibernateUtil.getCurrentSession().save(cr);
        PoHibernateUtil.getCurrentSession().flush();
        PoHibernateUtil.getCurrentSession().clear();
        
        @SuppressWarnings("unchecked")
        List<Person> results = sc.getQuery("", false).list();
        assertEquals(1, results.size());
    }

}
