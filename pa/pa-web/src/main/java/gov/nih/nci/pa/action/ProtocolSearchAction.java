package gov.nih.nci.pa.action;


import gov.nih.nci.pa.dto.ProtocolDTO;
import gov.nih.nci.pa.service.ProtocolSearchCriteria;
import gov.nih.nci.pa.util.HibernateUtil;
import gov.nih.nci.pa.util.PaRegistry;

import org.displaytag.properties.SortOrderEnum;

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
        HibernateUtil.getHibernateHelper().openAndBindSession();
        java.util.List<ProtocolDTO> protocols = PaRegistry.getProtocolService().getProtocol(criteria);
        records.setList(protocols);
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