package gov.nih.nci.po.service;

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
    public void findByStatusNEW() {
        @SuppressWarnings("unchecked")
        List<Person> results = sc.getQuery("", false).list();
        assertEquals(1, results.size());
        assertEquals(orgId, results.get(0).getId().longValue());
    }

    @Test
    public void findNoneByStatusNotNEW() {
        @SuppressWarnings("unchecked")
        List<Person> list = PoHibernateUtil.getCurrentSession().createCriteria(Person.class).list();
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
    public void findByStatusNotNEWButHasCRs() throws EntityValidationException {
        @SuppressWarnings("unchecked")
        List<Person> list = PoHibernateUtil.getCurrentSession().createCriteria(Person.class).list();
        Person o = list.iterator().next();
        assertTrue(EntityStatus.NEW.canTransitionTo(EntityStatus.CURATED));
        assertTrue(EntityStatus.CURATED.canTransitionTo(EntityStatus.DEPRECATED));
        o.setStatusCode(EntityStatus.CURATED);
        PoHibernateUtil.getCurrentSession().update(o);
        PoHibernateUtil.getCurrentSession().flush();

        PersonDTO cr = (PersonDTO) PoXsnapshotHelper.createSnapshot(o);
        EjbTestHelper.getPersonEntityServiceBean().updatePerson(cr);

        @SuppressWarnings("unchecked")
        List<Person> results = sc.getQuery("", false).list();
        assertEquals(1, results.size());
    }

}
