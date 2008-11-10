package gov.nih.nci.pa.iso.dto;

import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.iso.St;
/**
 * @author Kalpana Guthikonda
 * @since 11/07/2008
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
public class DocumentWorkflowStatusDTO extends StudyDTO {
    private St commonText;
    private Cd statusCode;
    private Ii studyProtocolIi;
    
    /**
     * @return commonText
     */
    public St getCommonText() {
        return commonText;
    }
    /**
     * @param commonText commonText
     */
    public void setCommonText(St commonText) {
        this.commonText = commonText;
    }
    /**
     * @return statusCode
     */
    public Cd getStatusCode() {
        return statusCode;
    }
    /**
     * @param statusCode statusCode
     */
    public void setStatusCode(Cd statusCode) {
        this.statusCode = statusCode;
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
