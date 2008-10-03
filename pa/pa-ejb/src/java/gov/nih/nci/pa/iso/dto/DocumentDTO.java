package gov.nih.nci.pa.iso.dto;

import gov.nih.nci.coppa.iso.Bl;
import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.iso.St;
/**
 *
 * @author Kalpana Guthikonda
 * @since 09/30/2008
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
public class DocumentDTO extends BaseDTO {
    private Cd typeCode;
    private Ii studyProtocolIi;
    private Bl activeIndicator;
    private St fileName;
    
    /**
     * @return typeCode
     */
    public Cd getTypeCode() {
        return typeCode;
    }    
    
    /**
     * @param typeCode typeCode
     */
    public void setTypeCode(Cd typeCode) {
        this.typeCode = typeCode;
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
    
    /**
     * @return activeIndicator
     */
    public Bl getActiveIndicator() {
        return activeIndicator;
    }
    
    /**
     * @param activeIndicator activeIndicator
     */
    public void setActiveIndicator(Bl activeIndicator) {
        this.activeIndicator = activeIndicator;
    }
    
    /**
     * @return fileName
     */
    public St getFileName() {
        return fileName;
    }
    
    /**
     * @param fileName fileName
     */
    public void setFileName(St fileName) {
        this.fileName = fileName;
    }
}
