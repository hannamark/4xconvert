package gov.nih.nci.registry.decorator;

import java.util.Date;
import org.displaytag.decorator.TableDecorator;
import org.apache.commons.lang.time.FastDateFormat;
import org.apache.struts2.ServletActionContext;

import gov.nih.nci.coppa.iso.Bl;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.iso.dto.StudyIndldeDTO;

/**
 * tag decorator registry.
 * 
 * @author Bala Nair
 * 
 */
public class RegistryDisplayTagDecorator extends TableDecorator {
      

    /**
     * 
     * @return formated date
     */
    public String getStudyStatusDate() {
        Date studyStatusDate = ((StudyProtocolQueryDTO) this.getCurrentRowObject()).getStudyStatusDate();
        if (studyStatusDate != null) {
            return FastDateFormat.getInstance("MM/dd/yyyy").format(studyStatusDate);
        } else {
            return "";
        }
    }

    /**
     * 
     * @return formated date
     */
    public String getDocumentWorkflowStatusDate() {
        Date documentWfStatusDate = ((StudyProtocolQueryDTO) this.getCurrentRowObject())
                .getDocumentWorkflowStatusDate();
        if (documentWfStatusDate != null) {
            return FastDateFormat.getInstance("MM/dd/yyyy").format(documentWfStatusDate);
        } else {
            return "";
        }
    }

    /**
     * 
     * @return masked processing status
     */
    public DocumentWorkflowStatusCode getDocumentWorkflowStatusCode() {
        String loginUser = null;
        DocumentWorkflowStatusCode documentWorkflowStatusCode = ((StudyProtocolQueryDTO) this.getCurrentRowObject())
                .getDocumentWorkflowStatusCode();
        String userCreated = ((StudyProtocolQueryDTO) this.getCurrentRowObject()).getUserLastCreated();
        loginUser = ServletActionContext.getRequest().getRemoteUser();
        if (loginUser != null && loginUser.equalsIgnoreCase(userCreated)) {
            return documentWorkflowStatusCode;
        } else {
            return null;
        }
    }
    
    /**
     * 
     * @return IND/IDE Expanded Access Indicator
     */
    public String getExpandedAccessIndicator() {

        Bl indicator = ((StudyIndldeDTO) this.getCurrentRowObject())
                                    .getExpandedAccessIndicator();
        if (indicator != null && (indicator.getValue()).booleanValue()) {
            return "Yes";
        } else {
            return "No";
        }
    }
}
