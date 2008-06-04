package gov.nih.nci.pa.action;

import gov.nih.nci.pa.service.ProtocolSearchCriteria;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 
 * @author hjayanna
 * 
 */
public class ProtocolSearchAction extends ActionSupport {
    
    private ProtocolSearchCriteria srchCri = new ProtocolSearchCriteria();
    
  
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
        
        return SUCCESS;
    }
}