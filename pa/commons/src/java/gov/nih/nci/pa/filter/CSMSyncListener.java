package gov.nih.nci.pa.filter;

import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.cagrid.gaards.csm.service.AuthorizationManagerFactory;
import org.cagrid.gaards.csm.service.CSMProperties;
import org.cagrid.gaards.csm.service.DatabaseProperties;
import org.cagrid.gaards.csm.service.GridGrouperRemoteGroupSynchronizer;
import org.cagrid.gaards.csm.service.RemoteGroupManager;

/**
 * Handles kicking off the CSM sync thread.
 * @author aevansel
 */
public class CSMSyncListener implements ServletContextListener {
    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(CSMSyncListener.class);
    private static Properties properties = new Properties();

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("PMD.AvoidThrowingRawExceptionTypes")
    public void contextInitialized(ServletContextEvent context) {
        try {
            try {
                properties.load(getClass().getResourceAsStream(
                        "/WEB-INF/classes/csm.properties"));
            } catch (RuntimeException e) {
                LOG.warn("Unable to load /WEB-INF/classes/csm.properties; now trying /csm.properties...");
                properties.load(Thread.currentThread().getContextClassLoader()
                        .getResourceAsStream("/csm.properties"));
            }
        } catch (Exception e) {
            throw new RuntimeException("ERROR LOADING CSM PROPERTIES FILE!", e);
        }

        DatabaseProperties dbProps = new DatabaseProperties();
        dbProps.setConnectionURL(properties.getProperty("csm.db.connection.url"));
        dbProps.setDriver(properties.getProperty("csm.db.driver"));
        dbProps.setHibernateDialect(properties.getProperty("csm.db.hibernate.dialect"));
        dbProps.setUserId(properties.getProperty("csm.db.user"));
        dbProps.setPassword(properties.getProperty("csm.db.password"));
        dbProps.setRemoteGroupDatabaseCreationPolicy(properties.getProperty("csm.remote.group.db.creation.policy"));

        CSMProperties props = new CSMProperties();
        props.setSecondsBetweenRemoteGroupSyncs(
                Long.parseLong(properties.getProperty("csm.remote.group.sync.seconds")));
        props.setDatabaseProperties(dbProps);
        try {
            RemoteGroupManager groupManager = new RemoteGroupManager(new AuthorizationManagerFactory(props), props,
                    new GridGrouperRemoteGroupSynchronizer());
            Thread t = new Thread(groupManager);
            t.setDaemon(true);
            t.start();
            LOG.info("CSM Sync Thread sucessfully started.");
        } catch (Exception e) {
            LOG.error("Error starting CSM Sync Thread.", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public void contextDestroyed(ServletContextEvent arg) {
        //NOTE: Not killing the thread here since it causes errors when trying to shutdown the jvm.
    }
}
