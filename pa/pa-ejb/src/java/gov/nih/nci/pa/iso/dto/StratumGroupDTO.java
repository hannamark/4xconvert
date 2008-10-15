package gov.nih.nci.pa.iso.dto;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.iso.St;

/**
*
* @author Kalpana Guthikonda
* @since 10/13/2008
* copyright NCI 2008.  All rights reserved.
* This code may not be used without the express written permission of the
* copyright holder, NCI.
*/
public class StratumGroupDTO extends BaseDTO {

    private St description;
    private St groupNumberText;
    private Ii studyProtocolIi;
    
    
    /**
     * @return description
     */
    public St getDescription() {
        return description;
    }
    /**
     * @param description description
     */
    public void setDescription(St description) {
        this.description = description;
    }
    /**
     * @return groupNumberText
     */
    public St getGroupNumberText() {
        return groupNumberText;
    }
    /**
     * @param groupNumberText groupNumberText
     */
    public void setGroupNumberText(St groupNumberText) {
        this.groupNumberText = groupNumberText;
    }
    /**
     * @return studyProtocolIi
     */
    public Ii getStudyProtocolIi() {
        return studyProtocolIi;
    }
    /**
     * @param studyProtocolIi studyProtocolIi
     */
    public void setStudyProtocolIi(Ii studyProtocolIi) {
        this.studyProtocolIi = studyProtocolIi;
    }
}
