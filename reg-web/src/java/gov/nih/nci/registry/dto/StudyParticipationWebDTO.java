package gov.nih.nci.registry.dto;

import gov.nih.nci.pa.iso.dto.StudyParticipationDTO;

/**
 * Class for holding attributes for StudyParticipation DTO.
 * @author Bala Nair
 */
public class StudyParticipationWebDTO {

    private String id;
    private String localProtocolIdentifier;

    /**
     * @param iso StudyResourcingDTO object
     */
    public StudyParticipationWebDTO(StudyParticipationDTO iso) {
        super();
        this.localProtocolIdentifier = iso.getLocalStudyProtocolIdentifier().getValue().toString();
        this.id = iso.getIi().getExtension();
    }

    /** .
     *  Default Constructor
     */
    public StudyParticipationWebDTO() {
        super();
    }

    /**
     * @return fundingMechanismCode
     */
    public String getLocalProtocolIdentifier() {
        return localProtocolIdentifier;
    }

    /**
     * @param localProtocolIdentifier localProtocolIdentifier
     */
    public void setLocalProtocolIdentifier(String localProtocolIdentifier) {
        this.localProtocolIdentifier = localProtocolIdentifier;
    }

    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id id
     */
    public void setId(String id) {
        this.id = id;
    }

}
