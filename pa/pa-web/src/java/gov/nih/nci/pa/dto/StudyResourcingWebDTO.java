/**
 * 
 */
package gov.nih.nci.pa.dto;

import gov.nih.nci.pa.iso.dto.StudyResourcingDTO;

/**
 * @author Hong Gao
 *
 */
public class StudyResourcingWebDTO {
    
    //private String typeCode;
    private String summaryFourFundingCategoryCode;
    private String studyProtocolIi;
    private String id;
    
    /**
     * @param iso
     * StudyProtocolWebDTO object
     */
    public StudyResourcingWebDTO(StudyResourcingDTO iso) {
        super();
        this.summaryFourFundingCategoryCode = iso.getTypeCode().getCode();
        this.id = iso.getIdentifier().getExtension();
    }
    /** .
     *  Default Constructor
     */
    public StudyResourcingWebDTO() {
        super();
    }
    /**
     * @return the studyProtocolIi
     */
    public String getStudyProtocolIi() {
        return studyProtocolIi;
    }
    /**
     * @param studyProtocolIi the studyProtocolIi to set
     */
    public void setStudyProtocolIi(String studyProtocolIi) {
        this.studyProtocolIi = studyProtocolIi;
    }

    /**
     * @return the summaryFourFundingCategoryCode
     */
    public String getSummaryFourFundingCategoryCode() {
        return summaryFourFundingCategoryCode;
    }
    /**
     * @param summaryFourFundingCategoryCode the summaryFourFundingCategoryCode to set
     */
    public void setSummaryFourFundingCategoryCode(
            String summaryFourFundingCategoryCode) {
        this.summaryFourFundingCategoryCode = summaryFourFundingCategoryCode;
    }
    /**
     * @return the id
     */
    public String getId() {
        return id;
    }
    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

}
