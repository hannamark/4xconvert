package gov.nih.nci.pa.dto;




/**
 * Class for holding attributes for NCI SpecificInformation Dto.
 * @author Naveen Amiruddin
 * @since 05/27/2008 copyright NCI 2007. All rights reserved. This code may not
 *        be used without the express written permission of the copyright
 *        holder, NCI.
 */
public class NCISpecificInformationWebDTO {    
  
    private String monitorCode;
    private String accrualReportingMethodCode;
    private String summaryFourFundingCategoryCode;
    private String organizationName;
    private String organizationIi;
    
    /**
     * @return monitorCode 
     */
    public String getMonitorCode() {
        return monitorCode;
    }
    
    /**
     * @param monitorCode monitorCode
     */
    public void setMonitorCode(String monitorCode) {
        this.monitorCode = monitorCode;
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
     * 
     * @return accrualReportingMethodCode
     */
    public String getAccrualReportingMethodCode() {
        return accrualReportingMethodCode;
    }
    
    /**
     * 
     * @param accrualReportingMethodCode accrualReportingMethodCode
     */
    public void setAccrualReportingMethodCode(String accrualReportingMethodCode) {
        this.accrualReportingMethodCode = accrualReportingMethodCode;
    }
    /**
     * 
     * @return organizationName 
     */
    public String getOrganizationName() {
        return organizationName;
    }
    /**
     * 
     * @param organizationName organizationName
     */
    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }
    /**
     * 
     * @return organizationIi
     */
    public String getOrganizationIi() {
        return organizationIi;
    }
    /**
     * 
     * @param organizationIi organizationIi
     */
    public void setOrganizationIi(String organizationIi) {
        this.organizationIi = organizationIi;
    }
    
    



}