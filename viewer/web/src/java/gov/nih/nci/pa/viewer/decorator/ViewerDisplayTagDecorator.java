package gov.nih.nci.pa.viewer.decorator;

import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.viewer.dto.result.TrialListResultWebDto;

import org.displaytag.decorator.TableDecorator;

/**
 * @author Hugh Reinhart
 * @since 05/07/2009
 */
public class ViewerDisplayTagDecorator extends TableDecorator {
    /**
     *
     * @return formated date
     */
    public String getDateLastCreated() {
        String dateLastCreated = ((TrialListResultWebDto) this.getCurrentRowObject()).getDateLastCreated();
        if (dateLastCreated != null) {
            return PAUtil.normalizeDateString(dateLastCreated);
        } else {
            return "";
        }
    }
}
