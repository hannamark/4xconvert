package gov.nih.nci.pa.domain;

import gov.nih.nci.pa.test.util.TestSchema;
import gov.nih.nci.pa.util.HibernateUtil;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


/**
 * 
 * @author NAmiruddin
 *
 */
@SuppressWarnings("PMD")
public  class CommonTest {

    static {
        TestSchema.reset();
    }
    /**
     * .
     */
    protected Session session;
    /**
     * .
     */
    protected Transaction transaction;

    
    /**
     * .
     * @return Session
     */
    public Session getSession() {
        return session;
    }

    /**
     * 
     * @param session session
     */
    public void setSession(Session session) {
        this.session = session;
    }

    /**
     * 
     * @return transaction
     */
    public Transaction getTransaction() {
        return transaction;
    }

    /**
     * 
     * @param transaction transaction
     */
    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    /**
     * 
     */
    @Before
    public void setUp() {
        
        TestSchema.reset();
        session = HibernateUtil.getHibernateHelper().getSessionFactory().openSession();

        transaction = session.beginTransaction();
    }
    
    /**
     * 
     */
    @After
    public void tearDown() {
        try {
            transaction.commit();
            session.close();
        } catch (Exception e) {
            //HibernateUtil.getHibernateHelper().rollbackTransaction(transaction);
        }
    }
    
    /**
     * 
     */
    @Test
    public void dummy() {
        
    }

    
}
