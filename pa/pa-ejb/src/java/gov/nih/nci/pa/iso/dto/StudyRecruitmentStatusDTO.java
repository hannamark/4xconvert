/**
 * 
 */
package gov.nih.nci.pa.iso.dto;

import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.iso.Ts;

/**
 * StudyRecruitmentStatusDTO for transferring StudyRecruitmentStatus object .
 * @author Hugh Reinhart
 * @since 07/22/2007
 
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the copyright holder, NCI.
 */
public class StudyRecruitmentStatusDTO extends BaseDTO {
    private static final long serialVersionUID = 1234562452L;
    
    Cd statusCode;
    Ts statusDate;
    Ii studyProtocolIi;
    
    /**
     * @return the statusCode
     */
    public Cd getStatusCode() {
        return statusCode;
    }
    /**
     * @param statusCode the statusCode to set
     */
    public void setStatusCode(Cd statusCode) {
        this.statusCode = statusCode;
    }
    /**
     * @return the statusDate
     */
    public Ts getStatusDate() {
        return statusDate;
    }
    /**
     * @param statusDate the statusDate to set
     */
    public void setStatusDate(Ts statusDate) {
        this.statusDate = statusDate;
    }
    /**
     * @return the studyProtocolidentifier
     */
    public Ii getStudyProtocolIi() {
        return studyProtocolIi;
    }
    /**
     * @param studyProtocolidentifier the studyProtocolidentifier to set
     */
    public void setStudyProtocolIi(Ii studyProtocolidentifier) {
        this.studyProtocolIi = studyProtocolidentifier;
    }

}
