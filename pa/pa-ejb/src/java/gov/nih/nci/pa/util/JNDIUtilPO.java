package gov.nih.nci.pa.util;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;

import org.apache.log4j.Logger;

/**
 * JNDI lookup for PO only.
 * 
 * Harsha
 * 
 */
public final class JNDIUtilPO {
    private static final Logger LOG = Logger.getLogger(JNDIUtilPO.class);
    //private static final String RESOURCE_NAME = "jndi.properties";
    //private static JNDIUtilPO theInstance = new JNDIUtilPO();
   // private final InitialContext context;

    private JNDIUtilPO() {
        try {
            Properties env = new Properties();
            env.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.security.jndi.JndiLoginInitialContextFactory");
            env.setProperty(Context.SECURITY_PRINCIPAL, "ejbclient");
            env.setProperty(Context.SECURITY_CREDENTIALS, "pass");            
          //  context = new InitialContext(env);
        } catch (Exception e) {
            LOG.error("Unable to initialize the JNDI Util.", e);
            throw new IllegalStateException(e);
        }
    }

    /**
     * @param name
     *            name to lookup
     * @return object in default context with given name
     */
    public static Object lookup(String name) {
        try {
            Properties env = new Properties();
            env.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.security.jndi.JndiLoginInitialContextFactory");
            env.setProperty(Context.SECURITY_PRINCIPAL, "ejbclient");
            env.setProperty(Context.SECURITY_CREDENTIALS, "pass");  
            InitialContext context = new InitialContext(env);
            return lookup(context, name);
        } catch (NamingException e) {           
            LOG.error("Error during lookup() " + e);
        }
        return null;
    }

    /**
     * @param ctx
     *            context
     * @param name
     *            name to get
     * @return object in contect with given name
     */
    public static Object lookup(InitialContext ctx, String name) {
        try {
            return ctx.lookup(name);
        } catch (NamingException ex) {
            LOG.error("------------------Here is what's in the context--(looking for " + name + ")----------");
            dump(ctx, 0);
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
}
