package gov.nih.nci.pa.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author Harsha
 *
 */
public class RegulatoryInformationDTO implements Serializable{    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String trialOversgtAuthCountry;  
    private String trialOversgtAuthOrgName;   
    
    private String indTrialIndicator;  
    private String ideTrialIndicator; 
    private String fdaRegulatedInterventionIndicator;  
    private String section801Indicator;
    private String delayedPostingIndicator;  
    private String dataMonitoringIndicator;
    
    private boolean indTrialIndicatorP;  
    private boolean ideTrialIndicatorP; 
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
	 * @return the indTrialIndicator
	 */
	public String getIndTrialIndicator() {
		return indTrialIndicator;
	}
	/**
	 * @param indTrialIndicator the indTrialIndicator to set
	 */
	public void setIndTrialIndicator(String indTrialIndicator) {
		this.indTrialIndicatorP = convertToboolean(indTrialIndicator);
		this.indTrialIndicator = indTrialIndicator;
	}
	/**
	 * @return the ideTrialIndicator
	 */
	public String getIdeTrialIndicator() {
		return ideTrialIndicator;
	}
	/**
	 * @param ideTrialIndicator the ideTrialIndicator to set
	 */
	public void setIdeTrialIndicator(String ideTrialIndicator) {
		this.ideTrialIndicatorP = convertToboolean(ideTrialIndicator);
		this.ideTrialIndicator = ideTrialIndicator;
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
		this.fdaRegulatedInterventionIndicatorP =convertToboolean(fdaRegulatedInterventionIndicator);
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
	 * @return the indTrialIndicatorP
	 */
	public boolean isIndTrialIndicatorP() {
		return indTrialIndicatorP;
	}
	/**
	 * @param indTrialIndicatorP the indTrialIndicatorP to set
	 */
	public void setIndTrialIndicatorP(boolean indTrialIndicatorP) {	
		this.indTrialIndicatorP = indTrialIndicatorP;
	}
	/**
	 * @return the ideTrialIndicatorP
	 */
	public boolean isIdeTrialIndicatorP() {
		return ideTrialIndicatorP;
	}
	/**
	 * @param ideTrialIndicatorP the ideTrialIndicatorP to set
	 */
	public void setIdeTrialIndicatorP(boolean ideTrialIndicatorP) {
		this.ideTrialIndicatorP = ideTrialIndicatorP;
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
	
	private static boolean convertToboolean(String string){
		if (string.equalsIgnoreCase("Yes")){
			return true;
		} else {
			return false;
		}
	}	
}
