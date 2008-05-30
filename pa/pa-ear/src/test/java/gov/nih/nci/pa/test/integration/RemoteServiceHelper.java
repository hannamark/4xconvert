package gov.nih.nci.pa.test.integration;


import gov.nih.nci.pa.service.ProtocolServiceRemote;

import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * @author Hugh Reinhart
 *
 */
public class RemoteServiceHelper {

    static final String PROTOCOL_SERVICE_BEAN_REMOTE = "pa/ProtocolServiceBean/remote";

    static InitialContext ctx;
    
    private static Object lookup(String resource) throws NamingException {
        if (ctx == null) {
            Properties env = new Properties();
            env.setProperty("java.naming.factory.initial", "org.jnp.interfaces.NamingContextFactory");
            env.setProperty("java.naming.factory.url.pkgs", "org.jboss.naming:org.jnp.interfaces");
            env.setProperty("java.naming.provider.url", "localhost");
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
    public static ProtocolServiceRemote getProtocolService() throws NamingException {
        return (ProtocolServiceRemote) lookup(PROTOCOL_SERVICE_BEAN_REMOTE);
    }

}
