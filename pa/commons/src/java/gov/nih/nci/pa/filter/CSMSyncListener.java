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
import org.cagrid.gaards.csm.stubs.types.CSMInternalFault;

/**
 * Handles kicking off the CSM sync thread.
 * @author aevansel
 */
@SuppressWarnings("PMD.AvoidThrowingRawExceptionTypes")
public class CSMSyncListener implements ServletContextListener {
    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(CSMSyncListener.class);
    private static Properties properties = new Properties();
    private Thread t;
    private RemoteGroupManager groupManager;

    /**
     * {@inheritDoc}
     */
    public void contextInitialized(ServletContextEvent context) {
        try {
            properties.load(getClass().getResourceAsStream("/WEB-INF/classes/csm.properties"));
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
            groupManager = new RemoteGroupManager(new AuthorizationManagerFactory(props), props, 
                    new GridGrouperRemoteGroupSynchronizer());
            t = new Thread(groupManager);
            t.start();
            LOG.info("CSM Sync Thread sucessfully started.");
        } catch (CSMInternalFault e) {
            LOG.error("Error starting CSM Sync Thread.", e);
        } catch (Exception e) {
            LOG.error("Error starting CSM Sync Thread.", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public void contextDestroyed(ServletContextEvent arg) {
        try {
            groupManager.shutdown();
            t.interrupt();
            LOG.info("CSM Sync Thread sucessfully shut down.");
        } catch (Exception e) {
            LOG.error("Error shutting down CSM Sync Thread.");
        }
    }
}
