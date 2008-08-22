/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.nih.nci.po.util;

import java.io.IOException;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;

import org.apache.log4j.Logger;

/**
 *
 * @author gax
 */
public final class JNDIUtil {

    private static final Logger LOG = Logger.getLogger(JNDIUtil.class);
    private static final String RESOURCE_NAME = "jndi.properties";

    private static JNDIUtil theInstance = new JNDIUtil();

    private final InitialContext context;

    private JNDIUtil() {
        try {
            Properties props = getProperties();
            context = new InitialContext(props);
        } catch (Exception e) {
            LOG.error("Unable to initialize the JNDI Util.", e);
            throw new IllegalStateException(e);
        }
    }

    /**
     * @return jndi (& jms) properties
     * @throws IOException on class load error
     */
    public static Properties getProperties() throws IOException {
        Properties props = new Properties();
        props.load(JNDIUtil.class.getClassLoader().getResourceAsStream(RESOURCE_NAME));
        return props;
    }

    /**
     * @param name name to lookup
     * @return object in default context with given name
     */
    public static Object lookup(String name) {
        return lookup(theInstance.context, name);
    }

    /**
     * @param ctx context
     * @param name name to get
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
