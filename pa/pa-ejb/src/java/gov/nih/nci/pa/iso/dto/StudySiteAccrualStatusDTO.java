package gov.nih.nci.pa.iso.dto;

import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.iso.Ts;

/**
 * StudyOverallStatusDTO for transferring StudyOverallStatus object .
 * @author Hugh Reinhart
 * @since 08/26/2008
 
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the copyright holder, NCI.
 */
public class StudySiteAccrualStatusDTO extends BaseDTO {
    private static final long serialVersionUID = 123456276552L;
    
    Cd statusCode;
    Ts statusDate;
    Ii studyParticipationIi;
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
     * @return the studyParticipationIi
     */
    public Ii getStudyParticipationIi() {
        return studyParticipationIi;
    }
    /**
     * @param studyParticipationIi the studyParticipationIi to set
     */
    public void setStudyParticipationIi(Ii studyParticipationIi) {
        this.studyParticipationIi = studyParticipationIi;
    }
    
}
