package gov.nih.nci.pa.action;


import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;


import gov.nih.nci.pa.service.IProtocolService;
import gov.nih.nci.pa.service.ProtocolSearchCriteria;
import gov.nih.nci.pa.service.SessionManagerRemote;
import gov.nih.nci.pa.util.JNDIUtil;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 
 * @author hjayanna
 * 
 */
@SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.ImmutableField" })
public class ProtocolSearchAction extends ActionSupport {
    
    private ProtocolSearchCriteria srchCri = new ProtocolSearchCriteria();
    private static final long TEST = 4;
  
    /**
     * @return action result
     */ 
    public String execute() {
        return SUCCESS;
    }
    
    /**
     * 
     * @return res
     */
    @SuppressWarnings({ "PMD.ReplaceHashtableWithMap" })
    public String query() {
        SessionManagerRemote sessionManager;
        InitialContext ctx;
        //IProtocolService pServ = (IProtocolService) getEJB("pa/ProtocolServiceBean/remote");
        Hashtable<String, Object> env = new Hashtable<String, Object>();
        env.put(Context.SECURITY_PRINCIPAL, "ejbclient");
        env.put(Context.SECURITY_CREDENTIALS, "pass");
        env.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.security.jndi.JndiLoginInitialContextFactory");
        try {
            ctx = new InitialContext(env);
        } catch (NamingException e) {
                return ERROR;
        }
        sessionManager = (SessionManagerRemote) JNDIUtil.lookup(ctx, "pa/SessionManagerBean/remote");
        String retURL = "http://www.google.com/";
        sessionManager.startSession("username", retURL);
        IProtocolService pServ = (IProtocolService) getEJB("pa/ProtocolServiceBean/remote");
        pServ.getProtocolLongTitleText(Long.valueOf(TEST));
        return SUCCESS;
    }
    
    /**
     * 
     * @param jndiName String
     * @return object
     */
    public static Object getEJB(String jndiName) {
        Object object = null;
        try {
            InitialContext ctx = new InitialContext();
            object = ctx.lookup(jndiName);
        } catch (Exception e) {
            //e.printStackTrace();
            object = null;
        }
        return object;
    }

}