package gov.nih.nci.coppa.pa.util;


import gov.nih.nci.security.AuthorizationManager;
import gov.nih.nci.security.authorization.domainobjects.Group;
import gov.nih.nci.security.authorization.instancelevel.InstanceLevelSecurityHelper;

import java.util.Iterator;

import org.apache.log4j.Logger;
import org.hibernate.ConnectionReleaseMode;
import org.hibernate.FlushMode;
import org.hibernate.HibernateException;
import org.hibernate.Interceptor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.NamingStrategy;
import org.hibernate.context.ManagedSessionContext;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.mapping.Collection;

/**
 * Class to help initialize hibernate in the nci environment.
 * Originally created by caArray team.  Modified for PA.
 */
public class HibernateHelper {

    private static final Logger LOG = Logger.getLogger(HibernateHelper.class);

    private static final Class<?>[] CSM_CLASSES = {gov.nih.nci.security.authorization.domainobjects.Application.class,
        gov.nih.nci.security.authorization.domainobjects.Group.class,
        gov.nih.nci.security.authorization.domainobjects.Privilege.class,
        gov.nih.nci.security.authorization.domainobjects.ProtectionElement.class,
        gov.nih.nci.security.authorization.domainobjects.ProtectionGroup.class,
        gov.nih.nci.security.authorization.domainobjects.Role.class,
        gov.nih.nci.security.authorization.domainobjects.User.class,
        gov.nih.nci.security.authorization.domainobjects.UserGroupRoleProtectionGroup.class,
        gov.nih.nci.security.authorization.domainobjects.UserProtectionElement.class,
        gov.nih.nci.security.authorization.domainobjects.FilterClause.class };

    private Configuration configuration;
    private SessionFactory sessionFactory;

    /**
     * Default constructor.
     */
    public HibernateHelper() {
        this(null, null, null);
    }

    /**
     * Constructor to build the hibernate helper.
     * @param authManager the auth manager, if one is needed, null otherwise.
     * @param namingStrategy the name strategy, if one is needed, null otherwise.
     * @param interceptor the interceptor, if one is needed, null otherwise.
     */
    public HibernateHelper(AuthorizationManager authManager, NamingStrategy namingStrategy, Interceptor interceptor) {
        try {
            configuration = new AnnotationConfiguration();
            initializeConfig(namingStrategy, interceptor);
            configuration = configuration.configure();
            // We call buildSessionFactory twice, because it appears that the annotated classes are
            // not 'activated' in the config until we build. The filters required the classes to
            // be present, so we throw away the first factory and use the second. If this is
            // removed, you'll likely see a NoClassDefFoundError in the unit tests
            configuration.buildSessionFactory();
            if (authManager != null) {
                InstanceLevelSecurityHelper.addFilters(authManager, configuration);
            }
            @SuppressWarnings("unchecked")
            Iterator<Collection> it = configuration.getCollectionMappings();
            final String role = Group.class.getName() + ".users";
            while (it.hasNext()) {
                Collection c = it.next();
                if (c.getRole().equals(role)) {
                    c.setLazy(true);
                    c.setExtraLazy(true);
                }
            }
            sessionFactory = configuration.buildSessionFactory();
        } catch (HibernateException e) {
            LOG.error(e.getMessage(), e);
            throw new ExceptionInInitializerError(e);
        }
    }

    private void initializeConfig(NamingStrategy namingStrategy, Interceptor interceptor) {
        if (namingStrategy != null) {
            configuration.setNamingStrategy(namingStrategy);
        }
        for (Class<?> cls : CSM_CLASSES) {
            configuration.addClass(cls);
        }
        if (interceptor != null) {
            configuration.setInterceptor(interceptor);
        }
    }

    /**
     * @return the configuration
     */
    public Configuration getConfiguration() {
        return this.configuration;
    }

    /**
     * @return the sessionFactory
     */
    public SessionFactory getSessionFactory() {
        return this.sessionFactory;
    }

    /**
     * Get the session that is bound to the current context.
     * @return the current session
     */
    public Session getCurrentSession() {
        return getSessionFactory().getCurrentSession();
    }

    /**
     * Starts a transaction on the current Hibernate session. Intended for use in
     * unit tests - DAO / Service layer logic should rely on container-managed transactions
     *
     * @return a Hibernate session.
     */
    public Transaction beginTransaction() {
        return getSessionFactory().getCurrentSession().beginTransaction();
    }

    /**
     * Checks if the transaction is active and then rolls it back.
     *
     * @param tx the Transaction to roll back.
     */
    public void rollbackTransaction(Transaction tx) {
        if ((tx != null) && (tx.isActive())) {
            tx.rollback();
        }
    }

    /**
     * Open a hibernate session and bind it as the current session via
     * {@link ManagedSessionContext#bind(org.hibernate.classic.Session)}. The hibernate property
     * "hibernate.current_session_context_class" must be set to "managed" for this to have effect This method should be
     * called from within an Interceptor or Filter type class that is setting up the scope of the Session. This method
     * should then call {@link HibernateUtil#unbindAndCleanupSession()} when the scope of the Session is expired.
     *
     * @see ManagedSessionContext#bind(org.hibernate.classic.Session)
     */
    public void openAndBindSession() {
        SessionFactoryImplementor sessionFactoryImplementor = (SessionFactoryImplementor) getSessionFactory();
        org.hibernate.classic.Session currentSession = sessionFactoryImplementor.openSession(null, true, false,
                ConnectionReleaseMode.AFTER_STATEMENT);
        currentSession.setFlushMode(FlushMode.COMMIT);
        ManagedSessionContext.bind(currentSession);
    }

    /**
     * Close the current session and unbind it via {@link ManagedSessionContext#unbind(SessionFactory)}. The hibernate
     * property "hibernate.current_session_context_class" must be set to "managed" for this to have effect. This method
     * should be called from within an Interceptor or Filter type class that is setting up the scope of the Session,
     * when this scope is about to expire.
     */
    public void unbindAndCleanupSession() {
        org.hibernate.classic.Session currentSession = ManagedSessionContext.unbind(getSessionFactory());
        currentSession.close();
    }
}
