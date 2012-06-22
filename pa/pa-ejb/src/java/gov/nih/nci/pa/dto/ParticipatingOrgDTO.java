package gov.nih.nci.pa.dto;

import gov.nih.nci.pa.enums.FunctionalRoleStatusCode;
import gov.nih.nci.pa.enums.RecruitmentStatusCode;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * @author Hugh Reinhart
 * @since Jun 18, 2012
 */
public class ParticipatingOrgDTO implements Serializable {

    private static final long serialVersionUID = 5370561621744531528L;

    private Long studySiteId;
    private String poId;
    private String name;
    private FunctionalRoleStatusCode statusCode;
    private RecruitmentStatusCode recruitmentStatus;
    private Timestamp recruitmentStatusDate;
    private Integer targetAccrualNumber;
    private String programCodeText;
    private List<PaPersonDTO> primaryContacts;
    private List<PaPersonDTO> principalInvestigators;
    private List<PaPersonDTO> subInvestigators;

    /**
     * @return the studySiteId
     */
    public Long getStudySiteId() {
        return studySiteId;
    }

    /**
     * @param studySiteId the studySiteId to set
     */
    public void setStudySiteId(Long studySiteId) {
        this.studySiteId = studySiteId;
    }

    /**
     * @return the poId
     */
    public String getPoId() {
        return poId;
    }

    /**
     * @param poId the poId to set
     */
    public void setPoId(String poId) {
        this.poId = poId;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the statusCode
     */
    public FunctionalRoleStatusCode getStatusCode() {
        return statusCode;
    }

    /**
     * @param statusCode the statusCode to set
     */
    public void setStatusCode(FunctionalRoleStatusCode statusCode) {
        this.statusCode = statusCode;
    }

    /**
     * @return the recruitmentStatus
     */
    public RecruitmentStatusCode getRecruitmentStatus() {
        return recruitmentStatus;
    }

    /**
     * @param recruitmentStatus the recruitmentStatus to set
     */
    public void setRecruitmentStatus(RecruitmentStatusCode recruitmentStatus) {
        this.recruitmentStatus = recruitmentStatus;
    }

    /**
     * @return the recruitmentStatusDate
     */
    public Timestamp getRecruitmentStatusDate() {
        return recruitmentStatusDate;
    }

    /**
     * @param recruitmentStatusDate the recruitmentStatusDate to set
     */
    public void setRecruitmentStatusDate(Timestamp recruitmentStatusDate) {
        this.recruitmentStatusDate = recruitmentStatusDate;
    }

    /**
     * @return the targetAccrualNumber
     */
    public Integer getTargetAccrualNumber() {
        return targetAccrualNumber;
    }

    /**
     * @param targetAccrualNumber the targetAccrualNumber to set
     */
    public void setTargetAccrualNumber(Integer targetAccrualNumber) {
        this.targetAccrualNumber = targetAccrualNumber;
    }

    /**
     * @return the programCodeText
     */
    public String getProgramCodeText() {
        return programCodeText;
    }

    /**
     * @param programCodeText the programCodeText to set
     */
    public void setProgramCodeText(String programCodeText) {
        this.programCodeText = programCodeText;
    }

    /**
     * @return the primaryContacts
     */
    public List<PaPersonDTO> getPrimaryContacts() {
        return primaryContacts;
    }

    /**
     * @param primaryContacts the primaryContacts to set
     */
    public void setPrimaryContacts(List<PaPersonDTO> primaryContacts) {
        this.primaryContacts = primaryContacts;
    }

    /**
     * @return the principalInvestigators
     */
    public List<PaPersonDTO> getPrincipalInvestigators() {
        return principalInvestigators;
    }

    /**
     * @param principalInvestigators the principalInvestigators to set
     */
    public void setPrincipalInvestigators(List<PaPersonDTO> principalInvestigators) {
        this.principalInvestigators = principalInvestigators;
    }

    /**
     * @return the subInvestigators
     */
    public List<PaPersonDTO> getSubInvestigators() {
        return subInvestigators;
    }

    /**
     * @param subInvestigators the subInvestigators to set
     */
    public void setSubInvestigators(List<PaPersonDTO> subInvestigators) {
        this.subInvestigators = subInvestigators;
    }
}
