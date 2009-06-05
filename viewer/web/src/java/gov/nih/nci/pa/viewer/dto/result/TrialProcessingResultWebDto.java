package gov.nih.nci.pa.viewer.dto.result;

import gov.nih.nci.pa.enums.MilestoneCode;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IntConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.report.dto.result.TrialProcessingResultDto;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Hugh Reinhart
 * @since 06/04/2009
 */
public class TrialProcessingResultWebDto {

    private Integer submissionNumber;
    private String milestoneCode;
    private String milestoneDays;
    private String cumulativeDays;

    /**
     * Static method for generating a list of web dto's from a list of service dto's.
     * @param serviceDtoList service dto list
     * @return web dto list
     */
    public static List<TrialProcessingResultWebDto> getWebList(List<TrialProcessingResultDto> serviceDtoList) {
        List<TrialProcessingResultWebDto> resultList = new ArrayList<TrialProcessingResultWebDto>();
        for (TrialProcessingResultDto dto : serviceDtoList) {
            resultList.add(new TrialProcessingResultWebDto(dto));
        }
        return resultList;
    }

    /**
     * Constructor using service dto.
     * @param dto the service iso dto
     */
    public TrialProcessingResultWebDto(TrialProcessingResultDto dto) {
        if (dto == null) { return; }
        cumulativeDays = StConverter.convertToString(dto.getCumulativeDays());
        milestoneCode = MilestoneCode.valueOf(CdConverter.convertCdToString(dto.getMilestoneCode())).getCode();
        milestoneDays = StConverter.convertToString(dto.getMilestoneDays());
        submissionNumber = IntConverter.convertToInteger(dto.getSubmissionNumber());
    }

    /**
     * @return the submissionNumber
     */
    public Integer getSubmissionNumber() {
        return submissionNumber;
    }
    /**
     * @param submissionNumber the submissionNumber to set
     */
    public void setSubmissionNumber(Integer submissionNumber) {
        this.submissionNumber = submissionNumber;
    }
    /**
     * @return the milestoneCode
     */
    public String getMilestoneCode() {
        return milestoneCode;
    }
    /**
     * @param milestoneCode the milestoneCode to set
     */
    public void setMilestoneCode(String milestoneCode) {
        this.milestoneCode = milestoneCode;
    }

    /**
     * @return the milestoneDays
     */
    public String getMilestoneDays() {
        return milestoneDays;
    }

    /**
     * @param milestoneDays the milestoneDays to set
     */
    public void setMilestoneDays(String milestoneDays) {
        this.milestoneDays = milestoneDays;
    }

    /**
     * @return the cumulativeDays
     */
    public String getCumulativeDays() {
        return cumulativeDays;
    }

    /**
     * @param cumulativeDays the cumulativeDays to set
     */
    public void setCumulativeDays(String cumulativeDays) {
        this.cumulativeDays = cumulativeDays;
    }
}
