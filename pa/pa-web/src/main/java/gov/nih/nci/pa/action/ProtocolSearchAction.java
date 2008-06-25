package gov.nih.nci.pa.action;

import java.util.ArrayList;
import java.util.List;

import gov.nih.nci.pa.dto.ProtocolDTO;
import gov.nih.nci.pa.service.ProtocolSearchCriteria;
import gov.nih.nci.pa.util.PaRegistry;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.validator.annotations.Validation;

/**
 * 
 * @author Harsha
 * 
 */
@SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.ImmutableField", "PMD.SingularField" })
@Validation
public class ProtocolSearchAction extends ActionSupport {    
    
    private List<ProtocolDTO> records = new ArrayList<ProtocolDTO>();
    
    private ProtocolSearchCriteria criteria = new ProtocolSearchCriteria();

    /**  
     * @return res
     */ 
    public String execute() {
        return SUCCESS;
    }
    
    /**  
     * @return res
     */
    public String query()  {
        try {            
            records = PaRegistry.getProtocolService().getProtocol(criteria);       
            return SUCCESS;
        } catch (Exception e) {
            addActionError(e.getLocalizedMessage());
            return ERROR;
        }
    }
    /**
     * 
     * @return prot
     */
    public List<ProtocolDTO>  getRecords() {
        return records;
    }

    /**
     * 
     * @return ProtocolSearchCriteria psc
     */
    public ProtocolSearchCriteria getCriteria() {
        return criteria;
    }
    
    /**
     * 
     * @param criteria ProtocolSearchCriteria
     */
    public void setCriteria(ProtocolSearchCriteria criteria) {
        this.criteria = criteria;
    }

}