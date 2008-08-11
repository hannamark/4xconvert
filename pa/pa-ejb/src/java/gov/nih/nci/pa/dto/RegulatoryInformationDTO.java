package gov.nih.nci.pa.dto;

import java.io.Serializable;

/**
 * 
 * @author Harsha
 * @since 08/05/2008
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
@SuppressWarnings("PMD.TooManyFields")
public class RegulatoryInformationDTO implements Serializable {    
 
    private static final long serialVersionUID = 1L;
    private String trialOversgtAuthCountry;  
    private String trialOversgtAuthOrgName;   
    
    private String indideTrialIndicator;  
    private String ideTrialIndicator; 
    private String fdaRegulatedInterventionIndicator;  
    private String section801Indicator;
    private String delayedPostingIndicator;  
    private String dataMonitoringIndicator;
    
    private boolean indideIndicatorP;
    private boolean fdaRegulatedInterventionIndicatorP;  
    private boolean section801IndicatorP;
    private boolean delayedPostingIndicatorP;  
    private boolean dataMonitoringIndicatorP;
    private long protocolID;
    private long selectedAuthOrgId;
    private long countryId;
        /**
         * @return the countryId
         */
        public long getCountryId() {
                return countryId;
        }
        /**
         * @param countryId the countryId to set
         */
        public void setCountryId(long countryId) {
                this.countryId = countryId;
        }
        /**
         * @return the selectedAuthOrgId
         */
        public long getSelectedAuthOrgId() {
                return selectedAuthOrgId;
        }
        /**
         * @param selectedAuthOrgId the selectedAuthOrgId to set
         */
        public void setSelectedAuthOrgId(long selectedAuthOrgId) {
                this.selectedAuthOrgId = selectedAuthOrgId;
        }
        /**
         * @return the trialOversgtAuthCountry
         */
        public String getTrialOversgtAuthCountry() {
                return trialOversgtAuthCountry;
        }
        /**
         * @param trialOversgtAuthCountry the trialOversgtAuthCountry to set
         */
        public void setTrialOversgtAuthCountry(String trialOversgtAuthCountry) {
                this.trialOversgtAuthCountry = trialOversgtAuthCountry;
        }
        /**
         * @return the trialOversgtAuthOrgName
         */
        public String getTrialOversgtAuthOrgName() {
                return trialOversgtAuthOrgName;
        }
        /**
         * @param trialOversgtAuthOrgName the trialOversgtAuthOrgName to set
         */
        public void setTrialOversgtAuthOrgName(String trialOversgtAuthOrgName) {
                this.trialOversgtAuthOrgName = trialOversgtAuthOrgName;
        }

        /**
         * @return the indideTrialIndicator
         */
        public String getIndideTrialIndicator() {
            return indideTrialIndicator;
            
        }
        /**
         * @param indideTrialIndicator the indideTrialIndicator to set
         */
        public void setIndideTrialIndicator(String indideTrialIndicator) {
            this.indideIndicatorP = convertToboolean(indideTrialIndicator);
            this.indideTrialIndicator = indideTrialIndicator;
        }
        /**
         * @return the ideTrialIndicator
         */
        public String getIdeTrialIndicator() {
                return ideTrialIndicator;
        }
      
        /**
         * @return the fdaRegulatedInterventionIndicator
         */
        public String getFdaRegulatedInterventionIndicator() {
                return fdaRegulatedInterventionIndicator;
        }
        /**
         * @param fdaRegulatedInterventionIndicator the fdaRegulatedInterventionIndicator to set
         */
        public void setFdaRegulatedInterventionIndicator(
                        String fdaRegulatedInterventionIndicator) {
                this.fdaRegulatedInterventionIndicatorP = convertToboolean(fdaRegulatedInterventionIndicator);
                this.fdaRegulatedInterventionIndicator = fdaRegulatedInterventionIndicator;
        }
        /**
         * @return the section801Indicator
         */
        public String getSection801Indicator() {
                return section801Indicator;
        }
        /**
         * @param section801Indicator the section801Indicator to set
         */
        public void setSection801Indicator(String section801Indicator) {
                this.section801IndicatorP =  convertToboolean(section801Indicator);
                this.section801Indicator = section801Indicator;
        }
        /**
         * @return the delayedPostingIndicator
         */
        public String getDelayedPostingIndicator() {
                return delayedPostingIndicator;
        }
        /**
         * @param delayedPostingIndicator the delayedPostingIndicator to set
         */
        public void setDelayedPostingIndicator(String delayedPostingIndicator) {
                this.delayedPostingIndicatorP = convertToboolean(delayedPostingIndicator);
                this.delayedPostingIndicator = delayedPostingIndicator;
        }
        /**
         * @return the dataMonitoringIndicator
         */
        public String getDataMonitoringIndicator() {
                return dataMonitoringIndicator;
        }
        /**
         * @param dataMonitoringIndicator the dataMonitoringIndicator to set
         */
        public void setDataMonitoringIndicator(String dataMonitoringIndicator) {        
                this.dataMonitoringIndicatorP =  convertToboolean(dataMonitoringIndicator);
                this.dataMonitoringIndicator = dataMonitoringIndicator;
        }
       
     
        /**
         * @return the fdaRegulatedInterventionIndicatorP
         */
        public boolean isFdaRegulatedInterventionIndicatorP() {
                return fdaRegulatedInterventionIndicatorP;
        }
        /**
         * @param fdaRegulatedInterventionIndicatorP the fdaRegulatedInterventionIndicatorP to set
         */
        public void setFdaRegulatedInterventionIndicatorP(
                        boolean fdaRegulatedInterventionIndicatorP) {
                this.fdaRegulatedInterventionIndicatorP = fdaRegulatedInterventionIndicatorP;
        }
        /**
         * @return the section801IndicatorP
         */
        public boolean isSection801IndicatorP() {
                return section801IndicatorP;
        }
        /**
         * @param section801IndicatorP the section801IndicatorP to set
         */
        public void setSection801IndicatorP(boolean section801IndicatorP) {
                this.section801IndicatorP = section801IndicatorP;
        }
        /**
         * @return the delayedPostingIndicatorP
         */
        public boolean isDelayedPostingIndicatorP() {
                return delayedPostingIndicatorP;
        }
        /**
         * @param delayedPostingIndicatorP the delayedPostingIndicatorP to set
         */
        public void setDelayedPostingIndicatorP(boolean delayedPostingIndicatorP) {
                this.delayedPostingIndicatorP = delayedPostingIndicatorP;
        }
        /**
         * @return the dataMonitoringIndicatorP
         */
        public boolean isDataMonitoringIndicatorP() {
                return dataMonitoringIndicatorP;
        }
        /**
         * @param dataMonitoringIndicatorP the dataMonitoringIndicatorP to set
         */
        public void setDataMonitoringIndicatorP(boolean dataMonitoringIndicatorP) {
                this.dataMonitoringIndicatorP = dataMonitoringIndicatorP;
        }
        /**
         * @return the protocolID
         */
        public long getProtocolID() {
                return protocolID;
        }
        /**
         * @param protocolID the protocolID to set
         */
        public void setProtocolID(long protocolID) {
                this.protocolID = protocolID;
        }
        /**
         * @return the indideIndicatorP
         */
        public boolean isIndideIndicatorP() {
            return indideIndicatorP;
        }
        /**
         * @param indideIndicatorP the indideIndicatorP to set
         */
        public void setIndideIndicatorP(boolean indideIndicatorP) {
            this.indideIndicatorP = indideIndicatorP;
        }       
        private static boolean convertToboolean(String string) {
            return string.equalsIgnoreCase("Yes") ? true : false; 
        }          
}
