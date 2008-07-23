package gov.nih.nci.pa.dto;

import gov.nih.nci.pa.enums.YesNoCode;

/**
 * 
 * @author Harsha
 *
 */
public class DiseaseConditionDTO {
    private String diseaseName;
    private String diseaseCode;
    private YesNoCode leadIndicator;
    private String parentCode;
    
    /**
     * 
     * @return diseaseName.
     */
    public String getDiseaseName() {
        return diseaseName;
    }
    
    /**
     * 
     * @param diseaseName String
     */
    public void setDiseaseName(String diseaseName) {
        this.diseaseName = diseaseName;
    }
    
    /**
     * 
     * @return diseaseCode
     */
    public String getDiseaseCode() {
        return diseaseCode;
    }
    
    /**
     * 
     * @param diseaseCode String
     */
    public void setDiseaseCode(String diseaseCode) {
        this.diseaseCode = diseaseCode;
    }
    
    /**
     * 
     * @return isLeadIndicator
     */
    public YesNoCode getLeadIndicator() {
        return leadIndicator;
    }
    
    /**
     * 
     * @param leadIndicator boolean
     */
    public void setLeadIndicator(YesNoCode leadIndicator) {
        this.leadIndicator = leadIndicator;
    }

    /**
     * 
     * @return parentCode
     */
    public String getParentCode() {
        return parentCode;
    }

    /**
     * 
     * @param parentCode String
     */
    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

}
