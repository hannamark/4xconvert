package gov.nih.nci.pa.service;




import gov.nih.nci.pa.util.HibernateUtil;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Transaction;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.junit.After;
import org.junit.Before;

/**
 * @author Scott Miller
 *
 */
public abstract class AbstractPaHibernateTestCase {

    private static final Logger LOG = Logger.getLogger(AbstractPaHibernateTestCase.class);

    protected Transaction transaction;

    /**
     * In JUnit3x you would normally override the setUp() and add your own functionality locally however, in JUnit4 to
     * override simply define your method and give it the <code>@Before annotation</code>. Doing so will cause that method to be invoked after the parent class's setUp().
     */
    @Before
    final public void setUp() {
        transaction = HibernateUtil.getHibernateHelper().beginTransaction();
    }

    /**
     * In JUnit3x you would normally override the tearDown() and add your own functionality locally however, in JUnit4
     * to override simply define your method and give it the <code>@After annotation</code>. Doing so will cause that method to be invoked after the parent class's tearDown().
     */
    @After
    final public void tearDown() {
        try {
            transaction.commit();
        } catch (Exception e) {
            HibernateUtil.getHibernateHelper().rollbackTransaction(transaction);
        }
    }

    @Before
    @SuppressWarnings({ "unchecked", "deprecation" })
    final public void initDbIfNeeded() throws HibernateException, SQLException {
        // First drop the audit sequence (& associated table, see
        // http://opensource.atlassian.com/projects/hibernate/browse/HHH-2472)
        Transaction tx = HibernateUtil.getHibernateHelper().beginTransaction();
        Statement s = HibernateUtil.getCurrentSession().connection().createStatement();
        try {
            s.execute("drop sequence AUDIT_ID_SEQ");
            s.execute("drop table if exists dual_AUDIT_ID_SEQ");
        } catch (SQLException e) {
            // expected
        }
        tx.commit();

        tx = HibernateUtil.getHibernateHelper().beginTransaction();
        List<Long> counts = HibernateUtil.getCurrentSession().createQuery(
                "select count(*) from " + Object.class.getName()).list();
        // create sequence and fake table for selecting from
        s = HibernateUtil.getCurrentSession().connection().createStatement();
        s.execute("create sequence AUDIT_ID_SEQ");
        s.execute("create table dual_AUDIT_ID_SEQ(test boolean)");
        tx.commit();
       
    }
}
