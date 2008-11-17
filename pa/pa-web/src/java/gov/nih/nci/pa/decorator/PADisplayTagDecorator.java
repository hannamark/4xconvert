package gov.nih.nci.pa.decorator;

import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;

import java.util.Date;

import org.apache.commons.lang.time.FastDateFormat;
import org.displaytag.decorator.TableDecorator;

/**
 * tag decorator class for pa.
 * @author Naveen Amiruddin
 *
 */
public class PADisplayTagDecorator extends TableDecorator {

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
     * @return formated date
     */
    public String getAction() {
        DocumentWorkflowStatusCode dwfs = ((StudyProtocolQueryDTO) 
                this.getCurrentRowObject()).getDocumentWorkflowStatusCode();
        if (dwfs.equals(DocumentWorkflowStatusCode.ACCEPTED)) {
            return "Abstract";
        } else {
            return "";
        }
    }
    
    /**
     * 
     * @return xml generation
     */
    public String getGenerateXml() {
        DocumentWorkflowStatusCode dwfs = ((StudyProtocolQueryDTO) 
                this.getCurrentRowObject()).getDocumentWorkflowStatusCode();
        if (dwfs.equals(DocumentWorkflowStatusCode.ACCEPTED)) {
            return "Generate XML";
        } else {
            return "";
        }
    }

}
