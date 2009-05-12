/**
 *
 */
package gov.nih.nci.pa.report.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * @author hreinhart
 *
 */
public interface CtrpHibernateHelper {

    /**
     * @return the sessionFactory
     */
    SessionFactory getSessionFactory();

    /**
     * Get the session that is bound to the current context.
     * @return the current session
     */
    Session getCurrentSession();

    /**
     * @return the configuration
     */
    Configuration getConfiguration();

    /**
     * Open a hibernate session and bind it as the current session.
     */
    void openAndBindSession();

    /**
     * Close the current session and unbind it.
     */
    void unbindAndCleanupSession();


}
