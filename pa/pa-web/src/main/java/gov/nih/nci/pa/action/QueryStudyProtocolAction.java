package gov.nih.nci.pa.action;

import gov.nih.nci.pa.dto.QueryStudyProtocolCriteria;
import gov.nih.nci.pa.dto.QueryStudyProtocolDTO;
import gov.nih.nci.pa.util.PaRegistry;

import java.util.ArrayList;
import java.util.List;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.validator.annotations.Validation;

/**
 * 
 * @author Harsha
 * 
 */
@SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.ImmutableField", "PMD.SingularField" })
@Validation
public class QueryStudyProtocolAction extends ActionSupport {    
    
    private List<QueryStudyProtocolDTO> records = new ArrayList<QueryStudyProtocolDTO>();
    
    private QueryStudyProtocolCriteria criteria = new QueryStudyProtocolCriteria();

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
            records = PaRegistry.getProtocolService().getStudyProtocolByCriteria(criteria);       
            return SUCCESS;
        } catch (Exception e) {
            addActionError(e.getLocalizedMessage());
            return ERROR;
        }
    }
    /**
     * 
     * @return records
     */
    public List<QueryStudyProtocolDTO>  getRecords() {
        return records;
    }

    /**
     * 
     * @return QueryStudyProtocolCriteria QueryStudyProtocolCriteria
     */
    public QueryStudyProtocolCriteria getCriteria() {
        return criteria;
    }
    
    /**
     * 
     * @param criteria QueryStudyProtocolCriteria
     */
    public void setCriteria(QueryStudyProtocolCriteria criteria) {
        this.criteria = criteria;
    }

}