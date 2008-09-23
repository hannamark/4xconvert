package gov.nih.nci.pa.action;

import gov.nih.nci.pa.dto.StudyProtocolQueryCriteria;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
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
public class StudyProtocolQueryAction extends ActionSupport {    
    
    private List<StudyProtocolQueryDTO> records = null;
    
    private StudyProtocolQueryCriteria criteria = new StudyProtocolQueryCriteria();


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
            records = new ArrayList<StudyProtocolQueryDTO>();
            records = PaRegistry.getProtocolQueryService().getStudyProtocolByCriteria(criteria);       
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
    public List<StudyProtocolQueryDTO>  getRecords() {
        return records;
    }

    /**
     * 
     * @return StudyProtocolQueryCriteria StudyProtocolQueryCriteria
     */
    public StudyProtocolQueryCriteria getCriteria() {
        return criteria;
    }
    
    /**
     * 
     * @param criteria StudyProtocolQueryCriteria
     */
    public void setCriteria(StudyProtocolQueryCriteria criteria) {
        this.criteria = criteria;
    }


    
    

}