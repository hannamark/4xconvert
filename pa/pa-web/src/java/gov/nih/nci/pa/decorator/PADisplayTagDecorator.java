package gov.nih.nci.pa.decorator;

import java.util.Date;
import org.displaytag.decorator.TableDecorator;
import org.apache.commons.lang.time.FastDateFormat;

import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;

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

}
