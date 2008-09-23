package gov.nih.nci.pa.test.integration;


import gov.nih.nci.pa.service.StudyProtocolServiceRemote;
import gov.nih.nci.pa.service.util.SessionManagerRemote;
import gov.nih.nci.pa.test.util.TestPropertiesPaEar;

import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * Original written by Scott Miller.  Modified for PA.
 * @author Hugh Reinhart
 *
 */
public class RemoteServiceHelper {

    static final String PROTOCOL_SERVICE_BEAN_REMOTE = "pa/ProtocolServiceBean/remote";
    static final String TRIALDESIGN_SERVICE_BEAN_REMOTE = "pa/TrialDesignServiceBean/remote";
    static final String SESSIONMANAGER_BEAN_REMOTE = "pa/SessionManagerBean/remote";
    public static String username = "ejbclient";
    public static String password = "pass";

    static InitialContext ctx;
    
    private static Object lookup(String resource) throws NamingException {
        if (ctx == null) {
            Properties env = new Properties();
            env.setProperty("java.naming.factory.initial", "org.jnp.interfaces.NamingContextFactory");
            env.setProperty("java.naming.factory.url.pkgs", "org.jboss.naming:org.jnp.interfaces");
            env.setProperty("java.naming.provider.url", TestPropertiesPaEar.getJavaNamingProvider());
            ctx = new InitialContext(env);
        }
        return ctx.lookup(resource);
    }

    public static void close() throws NamingException {
        if (ctx != null) {
            ctx.close();
            ctx = null;
        }
    }
    
    /**
     * Get the pa protocol service.
     * @return the service.
     * @throws NamingException on error.
     */
    public static StudyProtocolServiceRemote getProtocolService() throws NamingException {
        return (StudyProtocolServiceRemote) lookup(PROTOCOL_SERVICE_BEAN_REMOTE);
    }

    /**
     * Get the SessionManagerRemote service.
     * @return the service.
     * @throws NamingException on error.
     */
    public static SessionManagerRemote getSessionManagerService() throws NamingException {
        return (SessionManagerRemote) lookup(SESSIONMANAGER_BEAN_REMOTE);
    }
    

}
