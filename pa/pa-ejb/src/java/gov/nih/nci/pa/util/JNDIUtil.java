/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

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
 * Modified for PA.
 * 
 * @author Hugh Reinhart
 * @since 05/22/2008 copyright NCI 2008. All rights reserved. This code may not
 *        be used without the express written permission of the copyright
 *        holder, NCI.
 */
@SuppressWarnings("PMD.UnusedPrivateField")
public final class JNDIUtil {

    private static final Logger LOG = Logger.getLogger(JNDIUtil.class);
    private static final String RESOURCE_NAME = "jndi.properties";

    private static JNDIUtil theInstance = new JNDIUtil();
    private static InitialContext poCtx;
    private final InitialContext context;
    private final InitialContext contextRemote;
    private JNDIUtil() {
        try {
            Properties props = getProperties();
            context = new InitialContext(props);
            contextRemote = new InitialContext(props);
        } catch (Exception e) {
            LOG.error("Unable to initialize the JNDI Util.", e);
            throw new IllegalStateException(e);
        }
    }

    /**
     * @return jndi (& jms) properties
     * @throws IOException
     *             on class load error
     */
    public static Properties getProperties() throws IOException {
        Properties props = new Properties();
        props.load(JNDIUtil.class.getClassLoader().getResourceAsStream(RESOURCE_NAME));
        return props;
    }

    /**
     * @param name
     *            name to lookup
     * @return object in default context with given name
     */
    public static Object lookup(String name) {
        return lookup(theInstance.context, name);
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
            poCtx = new InitialContext(env);
            return lookup(theInstance.contextRemote, name);
        } catch (NamingException e) {
            LOG.error("Naming Exception at lookupPo() call for parameter" 
                    + name + " \n\t\t the message is " + e.getMessage());
           
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
