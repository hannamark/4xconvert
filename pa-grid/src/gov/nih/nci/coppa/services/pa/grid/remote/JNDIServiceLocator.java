package gov.nih.nci.coppa.services.pa.grid.remote;

import gov.nih.nci.pa.service.ArmServiceRemote;

import java.util.Properties;

import javax.naming.CommunicationException;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class JNDIServiceLocator implements ServiceLocator {
    private static final Logger logger = LogManager.getLogger(JNDIServiceLocator.class);
    private static final int MAX_RETRIES = 2;
    private static JNDIServiceLocator instance = new JNDIServiceLocator();
    private InitialContext context;

    private JNDIServiceLocator() {
        try {
            Properties props = new Properties();
            props.load(JNDIServiceLocator.class.getClassLoader().getResourceAsStream("jndi.properties"));
            context = new InitialContext(props);
        } catch (Exception e) {
            logger.error("Unable to load jndi properties.", e);
            throw new RuntimeException("Unable to load jndi properties.", e);
        }
    }

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
        ArmServiceRemote result = (ArmServiceRemote) lookup("pa/ArmServiceBean/remote"); // FIXME: check
        return result;
    }
}
