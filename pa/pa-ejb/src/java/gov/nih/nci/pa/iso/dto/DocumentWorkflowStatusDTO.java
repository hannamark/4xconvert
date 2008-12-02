package gov.nih.nci.pa.iso.dto;

import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.coppa.iso.St;
import gov.nih.nci.coppa.iso.Ts;

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
    private Ts statusDateRange;
    
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
     * @return the statusDateRange
     */
    public Ts getStatusDateRange() {
        return statusDateRange;
    }
    /**
     * @param statusDateRange the statusDateRange to set
     */
    public void setStatusDateRange(Ts statusDateRange) {
        this.statusDateRange = statusDateRange;
    }
}
