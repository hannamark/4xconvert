package gov.nih.nci.registry.action;

import gov.nih.nci.pa.dto.StudyProtocolQueryCriteria;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.registry.util.Constants;
import gov.nih.nci.registry.util.RegistryServiceLocator;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.validator.annotations.Validation;

/**
 *
 * @author Bala Nair
 *
 */
@SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.ImmutableField", "PMD.SingularField" })
@Validation
public class SearchTrialAction extends ActionSupport {
    
    private List<StudyProtocolQueryDTO> records = null;
    
    private StudyProtocolQueryCriteria criteria = new StudyProtocolQueryCriteria();
    
    private Long studyProtocolId = null;


    /**  
     * @return res
     */ 
    public String execute() {
        return SUCCESS;
    }
    

    /**  
     * @return res
     */     
    public String showCriteria() {
        return "criteria";
    }
    
    /**  
     * @return res
     */
    public String query()  {
        try { 
            records = new ArrayList<StudyProtocolQueryDTO>();
            records = RegistryServiceLocator.getProtocolQueryService().getStudyProtocolByCriteria(criteria);       
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

    /**
     * 
     * @return studyProtocolId
     */
    public Long getStudyProtocolId() {
        return studyProtocolId;
    }

    /**
     * 
     * @param studyProtocolId studyProtocolId
     */
    public void setStudyProtocolId(Long studyProtocolId) {
        this.studyProtocolId = studyProtocolId;
    }

    /**  
     * @return res
     */
    public String view() {
        try {            
            StudyProtocolQueryDTO  studyProtocolQueryDTO = 
                RegistryServiceLocator.getProtocolQueryService().getTrialSummaryByStudyProtocolId(studyProtocolId);
            // put an entry in the session and store StudyProtocolQueryDTO 
            ServletActionContext.getRequest().getSession().setAttribute(
                    Constants.TRIAL_SUMMARY, studyProtocolQueryDTO);
            ServletActionContext.getRequest().getSession().setAttribute(
                    Constants.STUDY_PROTOCOL_II, IiConverter.convertToIi(studyProtocolId));
            return "view";
        } catch (PAException e) {
            addActionError(e.getLocalizedMessage());
            return ERROR;
        }        
    }


}