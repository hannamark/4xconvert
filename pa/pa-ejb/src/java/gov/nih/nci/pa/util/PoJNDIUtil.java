package gov.nih.nci.pa.util;

import java.io.IOException;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;

import org.apache.log4j.Logger;

/**
 * class for looking up po service.
 * @author NAmiruddin
 *
 */
public final class PoJNDIUtil {

    private static final Logger LOG = Logger.getLogger(PoJNDIUtil.class);
    private static final String RESOURCE_NAME = "jndi.properties";
    private static PoJNDIUtil theInstance = new PoJNDIUtil();
    private final InitialContext poCtx;

    private PoJNDIUtil() {
        try {
            Properties props = getProperties();
            poCtx = new InitialContext(props);
        } catch (Exception e) {
            LOG.error("Unable to initialize the JNDI Util.", e);
            throw new IllegalStateException(e);
        }
    }

    /**
     * 
     * @param name
     *            name to lookup
     * @return object in default context with given name
     */
    public static Object lookupPo(String name) {
        try {
            Properties env = new Properties();
            env.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.security.jndi.JndiLoginInitialContextFactory");
            //load the Credential again
            new InitialContext(env);            
            return theInstance.poCtx.lookup(name);
        } catch (NamingException ex) {
            LOG.error("------------------Here is what's in the context--(looking for " + name + ")----------");
            dump(theInstance.poCtx, 0);
            LOG.error("-----------------------------------------------------------");
            throw new IllegalStateException(ex);
        }
    }



    private static void dump(javax.naming.Context ctx, int indent) {
        try {
            NamingEnumeration<NameClassPair> en = ctx.list("");
            while (en.hasMore()) {
                NameClassPair ncp = en.next();
                String cn = ncp.getClassName();
                String n = ncp.getName();
                LOG.info("\t\t\t\t\t\t".substring(0, indent) + n + " : " + cn);
                try {
                    Object o = ctx.lookup(n);
                    if (o instanceof Context) {
                        dump((Context) o, indent + 1);
                    }
                } catch (Exception e) {
                    LOG.info(e);
                }
            }
        } catch (NamingException ex) {
            LOG.info(ex);
        }
    }
    
    /**
     * @return jndi (& jms) properties
     * @throws IOException
     *             on class load error
     */
    private static Properties getProperties() throws IOException {
        Properties props = new Properties();
        props.load(PoJNDIUtil.class.getClassLoader().getResourceAsStream(RESOURCE_NAME));
        props.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.security.jndi.JndiLoginInitialContextFactory");
        return props;
    }

    
}
