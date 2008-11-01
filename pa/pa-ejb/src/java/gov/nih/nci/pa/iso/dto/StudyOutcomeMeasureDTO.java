package gov.nih.nci.pa.iso.dto;

import gov.nih.nci.coppa.iso.Bl;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.iso.St;

/**
 * 
 * @author Kalpana Guthikonda
 * @since 10/29/2008
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI. 
 */
public class StudyOutcomeMeasureDTO extends BaseDTO {
    private St name;
    private St timeFrame;
    private Bl primaryIndicator;
    private Bl safetyIndicator;
    private Ii studyProtocolIi;
    
    /**
     * @return name
     */
    public St getName() {
        return name;
    }
    /**
     * @param name name
     */
    public void setName(St name) {
        this.name = name;
    }
    /**
     * @return timeFrame
     */
    public St getTimeFrame() {
        return timeFrame;
    }
    /**
     * @param timeFrame timeFrame
     */
    public void setTimeFrame(St timeFrame) {
        this.timeFrame = timeFrame;
    }
    /**
     * @return primaryIndicator
     */
    public Bl getPrimaryIndicator() {
        return primaryIndicator;
    }
    /**
     * @param primaryIndicator primaryIndicator
     */
    public void setPrimaryIndicator(Bl primaryIndicator) {
        this.primaryIndicator = primaryIndicator;
    }
    /**
     * @return safetyIndicator
     */
    public Bl getSafetyIndicator() {
        return safetyIndicator;
    }
    /**
     * @param safetyIndicator safetyIndicator
     */
    public void setSafetyIndicator(Bl safetyIndicator) {
        this.safetyIndicator = safetyIndicator;
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
