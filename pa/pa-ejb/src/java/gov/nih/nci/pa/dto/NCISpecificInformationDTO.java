package gov.nih.nci.pa.dto;

import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.enums.MonitorCode;
import gov.nih.nci.pa.enums.ReportingDataSetMethodCode;
import gov.nih.nci.pa.enums.SummaryFourFundingCategoryCode;

import java.io.Serializable;

/**
 * NCISpecificInformationDTO for transferring NCI Specific information object .
 * @author Gil Naveh
 * @since 06/2008
 
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without the express written permission of the copyright holder, NCI.
 */
public class NCISpecificInformationDTO implements Serializable {

 
    private static final long serialVersionUID = 1L;
    
    private Long studyProtocolID;
    private MonitorCode monitorCode;
    private ReportingDataSetMethodCode reportingDataSetMethodCode;
    private SummaryFourFundingCategoryCode summaryFourFundingCategoryCode;


    /**
     * Default constructor.
    */
    public NCISpecificInformationDTO() {
        // empty constructor
    }

    /**
     * Constructor for creating a dto from a domain object.
     * @param sp domain object
     */
    public NCISpecificInformationDTO(StudyProtocol sp) {
        this.studyProtocolID = sp.getId();
        this.monitorCode = sp.getMonitorCode();       
    }
    
 
    /**
     * 
     * @return serialVersionUID
     */
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

     /**
     * @return study Protocol ID
     */
    public Long getStudyProtocolID() {
      return studyProtocolID;
    }
    
    /**
     * @param studyProtocolID unique Study_Site id
     */
    public void setStudyProtocolID(Long studyProtocolID) {
      this.studyProtocolID = studyProtocolID;
    }
   /**
    * 
    * @return monitorCode
   */
    public MonitorCode getMonitorCode() {
      return monitorCode;
    }
   /**
    * 
    * @param monitorCode for study site
    */
    public void setMonitorCode(MonitorCode monitorCode) {
      this.monitorCode = monitorCode;
    }
    
    /**
     * 
     * @return ReportingDataSetMethodCode
     */
    public ReportingDataSetMethodCode getReportingDataSetMethodCode() {
       return reportingDataSetMethodCode;
    }
   /**
    * 
    * @param reportingDataSetMethodCode for 
    */
    public void setReportingDataSetMethodCode(
       ReportingDataSetMethodCode reportingDataSetMethodCode) {
       this.reportingDataSetMethodCode = reportingDataSetMethodCode;
    }
    /**
     * 
     * @return summaryFourFundingCategoryCode
     */
    public SummaryFourFundingCategoryCode getSummaryFourFundingCategoryCode() {
      return summaryFourFundingCategoryCode;
    }
    /**
     * 
     * @param summaryFourFundingCategoryCode ref
     */
    public void setSummaryFourFundingCategoryCode(
      SummaryFourFundingCategoryCode summaryFourFundingCategoryCode) {
        this.summaryFourFundingCategoryCode = summaryFourFundingCategoryCode;
    }
}
