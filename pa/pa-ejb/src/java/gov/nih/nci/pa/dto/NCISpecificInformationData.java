package gov.nih.nci.pa.dto;

import java.io.Serializable;



/**
 * Class used to hold criteria used in searching protocols.
 * 
 * <pre>
 * Attr.                         Corresponding bo attribute
 * =====                         ==========================
 * studyProtocolID                    domain.studyProtocol.id
 * monitorCode                   domain.StudyProtocol.monitorCode

 * </pre>
 * 
 * @author Gnaveh extend code written by Hugh Reinhart
 * @since 05/27/2008 copyright NCI 2007. All rights reserved. This code may not
 *        be used without the express written permission of the copyright
 *        holder, NCI.
 */
public class NCISpecificInformationData implements Serializable {    
  

    private static final long serialVersionUID = 1L;

    private String studyProtocolID;
    private String monitorCode;
    
    private String reportingDataSetMethodCode;
    private String summaryFourFundingCategoryCode;
    
    private String summary4Name;
    
 
    /**
     * @return studyProtocolID
     */
    public String getStudyProtocolID() {
        return studyProtocolID;
    }
    
    /**
     * @param studyProtocolID studyProtocolID
     */
    public void setStudyProtocolID(String studyProtocolID) {
        this.studyProtocolID = studyProtocolID;
    }
    
    
    /**
     * @return monitorCode 
     */
    public String getMonitorCode() {
        return monitorCode;
    }
    
    /**
     * @param monitorCode ref
     */
    public void setMonitorCode(String monitorCode) {
        this.monitorCode = monitorCode;
    }

    /**
     * @return reportingDataSetMethodCode
     */
    public String getReportingDataSetMethodCode() {
        return reportingDataSetMethodCode;
    }
    
    /**
     * @param reportingDataSetMethodCode ref
     */
    public void setReportingDataSetMethodCode(String reportingDataSetMethodCode) {
        this.reportingDataSetMethodCode = reportingDataSetMethodCode;
    }
    /**
     * 
     * @return summaryFourFundingCategoryCode
     */
    public String getSummaryFourFundingCategoryCode() {
      return summaryFourFundingCategoryCode;
    }

   /**
    * 
    * @param summaryFourFundingCategoryCode ref
    */
    public void setSummaryFourFundingCategoryCode(
      String summaryFourFundingCategoryCode) {
        this.summaryFourFundingCategoryCode = summaryFourFundingCategoryCode;
    }


    /**
     * @return Summary4Name
     */
    public String getSummary4Name() {
        return summary4Name;
    }

    /**
     * 
     * @param summary4Name ref
     */
    public void setSummary4Name(String summary4Name) {
        this.summary4Name = summary4Name;
   }
}