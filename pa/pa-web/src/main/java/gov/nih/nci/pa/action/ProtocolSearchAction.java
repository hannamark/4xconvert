package gov.nih.nci.pa.action;

import javax.naming.InitialContext;

import gov.nih.nci.pa.service.IProtocolService;
import gov.nih.nci.pa.service.ProtocolSearchCriteria;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 
 * @author hjayanna
 * 
 */
@SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.ImmutableField" })
public class ProtocolSearchAction extends ActionSupport {
    
    private ProtocolSearchCriteria srchCri = new ProtocolSearchCriteria();
    private static final long TEST = 234234; 
  
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
    public String query() {
        IProtocolService pServ = (IProtocolService) getEJB("pa/ProtocolServiceBean/local");
        pServ.getProtocolLongTitleText(TEST);
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