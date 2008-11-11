package gov.nih.nci.registry.decorator;

import java.util.Date;
import org.displaytag.decorator.TableDecorator;
import org.apache.commons.lang.time.FastDateFormat;
import org.apache.struts2.ServletActionContext;

import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;

/**
 * tag decorator registry.
 * @author Bala Nair
 *
 */
public class RegistryDisplayTagDecorator extends TableDecorator {
    
    private String loginUser;
    private String userLastCreated;

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
        Date documentWfStatusDate = ((StudyProtocolQueryDTO)
                this.getCurrentRowObject()).getDocumentWorkflowStatusDate();
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
       DocumentWorkflowStatusCode documentWorkflowStatusCode = ((StudyProtocolQueryDTO)
               this.getCurrentRowObject()).getDocumentWorkflowStatusCode();
       if (documentWorkflowStatusCode != null) {
           loginUser =  ServletActionContext.getRequest().getRemoteUser();
           if (((StudyProtocolQueryDTO) this.getCurrentRowObject()).getUserLastCreated() == null) {
               return null;
           } else {
           userLastCreated = ((StudyProtocolQueryDTO) this.getCurrentRowObject()).getUserLastCreated();
           }
       if (loginUser.equalsIgnoreCase(userLastCreated)) {
               return documentWorkflowStatusCode;
           } else {
               return null; 
           }
       } else {
           return null;
       }
   }

}
