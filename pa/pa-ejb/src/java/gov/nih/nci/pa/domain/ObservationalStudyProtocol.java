package gov.nih.nci.pa.domain;

import gov.nih.nci.pa.enums.BiospecimenRetentionCode;
import gov.nih.nci.pa.enums.SamplingMethodCode;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;

/**
 * An action plan for InterventionalStudyProtocol.
 *
 * @author Naveen Amiruddin
 * @since 09/07/2008
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */

@Entity
@DiscriminatorColumn(
        name = "InterventionalStudyProtocol",
        discriminatorType = DiscriminatorType.STRING
    )

public class ObservationalStudyProtocol extends StudyProtocol {
    
    private String biospecimenDescription;
    private BiospecimenRetentionCode biospecimenRetentionCode;
    private Integer groupNumber;
    private SamplingMethodCode samplingMethodCode;
    /**
     * 
     * @return biospecimenDescription
     */
    public String getBiospecimenDescription() {
        return biospecimenDescription;
    }
    /**
     * 
     * @param biospecimenDescription biospecimenDescription
     */
    public void setBiospecimenDescription(String biospecimenDescription) {
        this.biospecimenDescription = biospecimenDescription;
    }
    /**
     * 
     * @return biospecimenRetentionCode
     */
    public BiospecimenRetentionCode getBiospecimenRetentionCode() {
        return biospecimenRetentionCode;
    }
    /**
     * 
     * @param biospecimenRetentionCode biospecimenRetentionCode
     */
    public void setBiospecimenRetentionCode(
            BiospecimenRetentionCode biospecimenRetentionCode) {
        this.biospecimenRetentionCode = biospecimenRetentionCode;
    }
    /**
     * 
     * @return biospecimenRetentionCode
     */
    public Integer getGroupNumber() {
        return groupNumber;
    }
    /**
     * 
     * @param groupNumber groupNumber
     */
    public void setGroupNumber(Integer groupNumber) {
        this.groupNumber = groupNumber;
    }
    /**
     * 
     * @return samplingMethodCode
     */
    public SamplingMethodCode getSamplingMethodCode() {
        return samplingMethodCode;
    }
    /**
     * 
     * @param samplingMethodCode samplingMethodCode
     */
    public void setSamplingMethodCode(SamplingMethodCode samplingMethodCode) {
        this.samplingMethodCode = samplingMethodCode;
    }
    
    

}
