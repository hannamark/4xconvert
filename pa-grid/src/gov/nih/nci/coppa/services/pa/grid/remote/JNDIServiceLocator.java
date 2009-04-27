package gov.nih.nci.coppa.services.pa.grid.remote;

import gov.nih.nci.pa.service.ArmServiceRemote;
import gov.nih.nci.pa.service.StudyProtocolServiceRemote;
import gov.nih.nci.pa.service.StudyRegulatoryAuthorityServiceRemote;
import gov.nih.nci.pa.service.StudyResourcingServiceRemote;
import gov.nih.nci.pa.service.StudySiteAccrualStatusServiceRemote;

import java.util.Properties;

import javax.naming.CommunicationException;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Service locator that uses JNDI to look up services.
 */
public final class JNDIServiceLocator implements ServiceLocator {
    private static final Logger LOG = LogManager.getLogger(JNDIServiceLocator.class);
    private static final int MAX_RETRIES = 2;
    private static JNDIServiceLocator instance = new JNDIServiceLocator();
    private InitialContext context;

    private JNDIServiceLocator() {
        try {
            Properties props = new Properties();
            props.load(JNDIServiceLocator.class.getClassLoader().getResourceAsStream("jndi.properties"));
            context = new InitialContext(props);
        } catch (Exception e) {
            LOG.error("Unable to load jndi properties.", e);
            throw new RuntimeException("Unable to load jndi properties.", e);
        }
    }

    /**
     * Get the singleton instance of the service locator.
     * @return the singleton locator
     */
    public static JNDIServiceLocator getInstance() {
        return instance;
    }

    private Object lookup(String name) throws NamingException {
        Object object = null;
        int i = 0;
        while (object == null && i < MAX_RETRIES) {
            try {
                object = context.lookup(name);
            } catch (CommunicationException com) {
                instance = new JNDIServiceLocator();
            }
            i++;
        }

        return object;
    }

    /**
     * {@inheritDoc}
     */
    public ArmServiceRemote getArmService() throws NamingException {
        ArmServiceRemote result = (ArmServiceRemote) lookup("pa/ArmServiceBean/remote");
        return result;
    }

    /**
     * {@inheritDoc}
     */
    public StudyProtocolServiceRemote getStudyProtocolService() throws NamingException {
        StudyProtocolServiceRemote result = (StudyProtocolServiceRemote) lookup("pa/StudyProtocolServiceBean/remote");
        return result;
    }

    /**
     * {@inheritDoc}
     */
    public StudyResourcingServiceRemote getStudyResourcingService() throws NamingException {
        StudyResourcingServiceRemote result =
            (StudyResourcingServiceRemote) lookup("pa/StudyResourcingServiceBean/remote");
        return result;
    }

    /**
     * {@inheritDoc}
     */
    public StudyRegulatoryAuthorityServiceRemote getStudyRegulatoryAuthorityService() throws NamingException {
        StudyRegulatoryAuthorityServiceRemote result =
            (StudyRegulatoryAuthorityServiceRemote) lookup("pa/StudyRegulatoryAuthorityServiceBean/remote");
        return result;
    }

    /**
     * {@inheritDoc}
     */
    public StudySiteAccrualStatusServiceRemote getStudySiteAccrualStatusService()
    throws NamingException {
        StudySiteAccrualStatusServiceRemote result =
            (StudySiteAccrualStatusServiceRemote) lookup("pa/StudySiteAccrualStatusServiceBean/remote");
        return result;
    }
}
