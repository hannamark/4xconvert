package gov.nih.nci.pa.action;

import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.PaRegistry;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

/**
 * Action class for viewing study protocol.
 *
 * @author Naveen Amiruddin
 * @since 07/09/2007
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
public class StudyProtocolViewAction extends ActionSupport {   
    
    private Long studyProtocolId = null;
    
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
    public String getStudyProtocol() {
        try {            
            StudyProtocolQueryDTO  studyProtocolQueryDTO = 
                PaRegistry.getProtocolQueryService().getTrialSummaryByStudyProtocolId(studyProtocolId);
            // put an entry in the session and store StudyProtocolQueryDTO 
            ServletActionContext.getRequest().getSession().setAttribute(
                    Constants.TRIAL_SUMMARY, studyProtocolQueryDTO);
            ServletActionContext.getRequest().getSession().setAttribute(
                    Constants.STUDY_PROTOCOL_II, IiConverter.convertToIi(studyProtocolId));
            ServletActionContext.getRequest().getSession().setAttribute(
                    Constants.DOC_WFS_MENU, setMenuLinks(studyProtocolQueryDTO.getDocumentWorkflowStatusCode())); 
            return SUCCESS;
        } catch (PAException e) {
            addActionError(e.getLocalizedMessage());
            return ERROR;
        }        
    }
    
    private String setMenuLinks(DocumentWorkflowStatusCode dwsCode) {
        String action = "";
        if (DocumentWorkflowStatusCode.REJECTED.equals(dwsCode)) {
            action = DocumentWorkflowStatusCode.REJECTED.getCode();
        } else if (DocumentWorkflowStatusCode.SUBMITTED.equals(dwsCode)) {
            action = DocumentWorkflowStatusCode.SUBMITTED.getCode();
        } else {
            action = DocumentWorkflowStatusCode.ACCEPTED.getCode();
        }
        return action;
    }


}
