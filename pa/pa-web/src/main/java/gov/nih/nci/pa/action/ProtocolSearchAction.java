package gov.nih.nci.pa.action;

import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.displaytag.properties.SortOrderEnum;
import gov.nih.nci.pa.dto.ProtocolDTO;
import gov.nih.nci.pa.service.IProtocolService;
import gov.nih.nci.pa.service.ProtocolSearchCriteria;
import gov.nih.nci.pa.service.SessionManagerRemote;
import gov.nih.nci.pa.util.JNDIUtil;
import com.fiveamsolutions.nci.commons.web.displaytag.PaginatedList;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 
 * @author Harsha
 * 
 */
@SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.ImmutableField", "PMD.SingularField" })
public class ProtocolSearchAction extends ActionSupport {
    
    private final PaginatedList<ProtocolDTO> records =
        new PaginatedList<ProtocolDTO>(0, new java.util.ArrayList<ProtocolDTO>(), 20, 1,
                null, null, SortOrderEnum.DESCENDING);

    private ProtocolSearchCriteria criteria = new ProtocolSearchCriteria();
  
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
    @SuppressWarnings({ "PMD.ReplaceHashtableWithMap", "PMD.SystemPrintln" })  
    
    public String query() {
        SessionManagerRemote sessionManager;
        InitialContext ctx;
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
        String retURL = "http://";
        sessionManager.startSession("username", retURL);
        IProtocolService pServ = (IProtocolService) getEJB("pa/ProtocolServiceBean/local");
        java.util.List<ProtocolDTO> p = pServ.getProtocol(criteria);
        records.setList(p);
        return SUCCESS;
    }
    
    /**
     * 
     * @return prot
     */
    public PaginatedList<ProtocolDTO>  getRecords() {
        return records;
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
            object = null;
        }
        return object;
    }

    /**
     * @return form bean
     */
    public ProtocolSearchCriteria getCriteria() {
        return criteria;
    }

    /**
     * @param criteria form bean
     */
    public void setCriteria(ProtocolSearchCriteria criteria) {
        this.criteria = criteria;
    }

}