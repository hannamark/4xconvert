package gov.nih.nci.pa.util;

import java.lang.reflect.Method;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * <p>
 * A <code>Job</code> that invokes a method on an EJB3 EJB. Based on the EJBInvokerJob.
 * </p>
 * <p>
 * 
 * Expects the properties corresponding to the following keys to be in the <code>JobDataMap</code> when it executes:
 * 
 * <ul>
 * <li><code>EJB_JNDI_NAME_KEY</code>- the JNDI name (location) of the EJB</li>
 * <li><code>EJB_INTERFACE_NAME_KEY</code>- the name of the EJB's business interface</li>
 * <li><code>EJB_METHOD_KEY</code>- the name of the method to invoke on the EJB.</li>
 * <li><code>EJB_ARGS_KEY</code>- an Object[] of the args to pass to the method (optional, if left out,
 * there are no arguments).</li>
 * <li><code>EJB_ARG_TYPES_KEY</code>- a Class[] of the types of the args to pass to the method
 * (optional, if left out, the types will be derived by calling getClass() on each of the arguments).</li>
 * </ul>
 * 
 * <br/>
 * The following keys can also be used at need:
 * <ul>
 * <li><code>INITIAL_CONTEXT_FACTORY</code> - the context factory used to build the context.</li>
 * <li><code>PROVIDER_URL</code> - the name of the environment property for specifying configuration
 * information for the service provider to use.</li>
 * </ul>
 * </p>
 * <p>
 * The result of the EJB method invocation will be available to <code>Job/TriggerListener</code>s via
 * <code>{@link org.quartz.JobExecutionContext#getResult()}</code>.
 * </p>
 * 
 * @author Adrian Brennan
 * @author Andrew Collins
 * @author James House
 * @author Joel Shellman
 * @author <a href="mailto:bonhamcm@thirdeyeconsulting.com">Chris Bonham</a>
 */
@SuppressWarnings({ "PMD.CyclomaticComplexity", "PMD.ExcessiveMethodLength" , "PMD.TooManyMethods",
    "PMD.AvoidDuplicateLiterals", "PMD.ExcessiveClassLength", "PMD.TooManyFields", "PMD.NPathComplexity" })
public class EJBInvokerJob implements Job {

    private static final Logger LOG  = Logger.getLogger(EJBInvokerJob.class); 
    /** The Constant EJB_JNDI_NAME_KEY. */
    public static final String EJB_JNDI_NAME_KEY = "ejb";
    
    /** The Constant EJB_INTERFACE_NAME_KEY. */
    public static final String EJB_INTERFACE_NAME_KEY = "interfaceName";
    
    /** The Constant EJB_METHOD_KEY. */
    public static final String EJB_METHOD_KEY = "method";

    /** The Constant EJB_ARG_TYPES_KEY. */
    public static final String EJB_ARG_TYPES_KEY = "argTypes";
    
    /** The Constant EJB_ARGS_KEY. */
    public static final String EJB_ARGS_KEY = "args";

    /** The Constant INITIAL_CONTEXT_FACTORY. */
    public static final String INITIAL_CONTEXT_FACTORY = "java.naming.factory.initial";
    
    /** The Constant PROVIDER_URL. */
    public static final String PROVIDER_URL = "java.naming.provider.url";
    
    /** The Constant PRINCIPAL. */
    public static final String PRINCIPAL = "java.naming.security.principal";
    
    /** The Constant CREDENTIALS. */
    public static final String CREDENTIALS = "java.naming.security.credentials";


    /** The initial context. */
    private InitialContext initialContext = null;

    /**
     * Instantiates a new eJB invoker job.
     */
    public EJBInvokerJob() {
        // do nothing
    }

    /** execute. 
     * @param context JobExecutionContext
     * @throws JobExecutionException exception
     */
    @SuppressWarnings({"PMD" })
    public void execute(JobExecutionContext context) throws JobExecutionException {

        LOG.info("Entering the EJBInvoker - execute method");
        JobDataMap dataMap = context.getMergedJobDataMap();
        
        String ejbJNDIName = dataMap.getString(EJB_JNDI_NAME_KEY);
        String methodName = dataMap.getString(EJB_METHOD_KEY);
        Object[] arguments = (Object[]) dataMap.get(EJB_ARGS_KEY);

        if (null == ejbJNDIName || ejbJNDIName.length() == 0) {
            throw new JobExecutionException("must specify ejb JNDI name");
        }

        if (arguments == null) {
            arguments = new Object[0];
        }

        Object ejb = locateEjb(dataMap);
        
        Class[] argTypes = (Class[]) dataMap.get(EJB_ARG_TYPES_KEY);

        if (argTypes == null) {
            argTypes = new Class[arguments.length];
            for (int i = 0; i < arguments.length; i++) {
                argTypes[i] = arguments[i].getClass();
            }
        }

        try {
            Method methodToExecute = ejb.getClass().getDeclaredMethod(methodName, argTypes);
            Object returnObj = methodToExecute.invoke(ejb, arguments);

            context.setResult(returnObj);
        } catch (Exception e) {
            LOG.info(e.getLocalizedMessage());
            throw new JobExecutionException(e);
        } finally {

            // Don't close jndiContext until after method execution because
            // WebLogic requires context to be open to keep the user credentials
            // available. See JIRA Issue: QUARTZ-401

            if (initialContext != null) {
                try {
                    initialContext.close();
                } catch (NamingException e) {

                   LOG.error(e);
                }
            }
        }
    }
    /**
     * Locate ejb.
     * 
     * @param dataMap the data map
     * 
     * @return the t
     * 
     * @throws JobExecutionException the job execution exception
     */
    @SuppressWarnings("unchecked")
    private <T> T locateEjb(JobDataMap dataMap) throws JobExecutionException {
        
        String ejbJNDIName = dataMap.getString(EJB_JNDI_NAME_KEY);

        Object object = null;

        try {
            initialContext = getInitialContext(dataMap);

            object = initialContext.lookup(ejbJNDIName);

            if (object == null) {
                throw new JobExecutionException("Cannot find " + ejbJNDIName);
            }
        } catch (NamingException e) {
            throw new JobExecutionException(e);
        }
        
        String ejbInterfaceName = dataMap.getString(EJB_INTERFACE_NAME_KEY);
        
        Class ejbInterface = null;
        
        try {
            ejbInterface = Class.forName(ejbInterfaceName);
        } catch (ClassNotFoundException e) {
            LOG.error("Exception occured performing class.forname on" + EJB_INTERFACE_NAME_KEY);
            throw new JobExecutionException(e);
        }
        
        if (!ejbInterface.isAssignableFrom(object.getClass())) {
            object = PortableRemoteObject.narrow(object, ejbInterface);
        }

        return (T) object;
    }



    /**
     * Gets the initial context.
     * 
     * @param jobDataMap the job data map
     * 
     * @return the initial context
     * 
     * @throws NamingException the naming exception
     */
    @SuppressWarnings({"PMD" })
    private InitialContext getInitialContext(JobDataMap jobDataMap) throws NamingException {
        LOG.info("Getting Initial Context");
        Hashtable<String, String> params = new Hashtable<String, String>();

        String initialContextFactory = jobDataMap.getString(INITIAL_CONTEXT_FACTORY);

        if (initialContextFactory != null) {
            params.put(Context.INITIAL_CONTEXT_FACTORY, initialContextFactory);
        }

        String providerUrl = jobDataMap.getString(PROVIDER_URL);

        if (providerUrl != null) {
            params.put(Context.PROVIDER_URL, providerUrl);
        }

        String principal = jobDataMap.getString(PRINCIPAL);

        if (principal != null) {
            params.put(Context.SECURITY_PRINCIPAL, principal);
        }

        String credentials = jobDataMap.getString(CREDENTIALS);

        if (credentials != null) {
            params.put(Context.SECURITY_CREDENTIALS, credentials);
        }

        return (params.size() == 0) ? new InitialContext() : new InitialContext(params);

    }

}
