package gov.nih.nci.pa.iso.convert;


import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.enums.AccrualReportingMethodCode;
import gov.nih.nci.pa.enums.ActualAnticipatedTypeCode;
import gov.nih.nci.pa.enums.MonitorCode;
import gov.nih.nci.pa.enums.PhaseCode;
import gov.nih.nci.pa.enums.PrimaryPurposeCode;
import gov.nih.nci.pa.enums.StudyClassificationCode;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.IntConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;


/**
 * Convert StudyProtocol domain to DTO.
 *
 * @author Naveen Amiruddin
 * @since 08/26/2008
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
@SuppressWarnings({"PMD.CyclomaticComplexity", "PMD.NPathComplexity", "PMD.ExcessiveMethodLength"  })
public class StudyProtocolConverter {

    /**
     *
     * @param studyProtocol study Protocol
     * @return studyProtocolDTO
     */
    public static StudyProtocolDTO convertFromDomainToDTO(StudyProtocol studyProtocol) {
        StudyProtocolDTO studyProtocolDTO = new StudyProtocolDTO();

        studyProtocolDTO.setAssignedIdentifier(IiConverter.convertToIi(studyProtocol.getIdentifier()));
        studyProtocolDTO.setIdentifier(IiConverter.convertToIi(studyProtocol.getId()));
        studyProtocolDTO.setAcronym(StConverter.convertToSt(studyProtocol.getAcronym()));
        studyProtocolDTO.setAccrualReportingMethodCode(
                CdConverter.convertToCd(studyProtocol.getAccrualReportingMethodCode()));
        studyProtocolDTO.setExpandedAccessIndicator(
                BlConverter.convertToBl(studyProtocol.getExpandedAccessIndicator()));
        studyProtocolDTO.setMonitorCode(
                CdConverter.convertToCd(studyProtocol.getMonitorCode()));
        studyProtocolDTO.setOfficialTitle(StConverter.convertToSt(studyProtocol.getOfficialTitle()));
        studyProtocolDTO.setPhaseCode(CdConverter.convertToCd(studyProtocol.getPhaseCode()));

        studyProtocolDTO.setPrimaryCompletionDateTypeCode(
                CdConverter.convertToCd(studyProtocol.getPrimaryCompletionDateTypeCode()));
        studyProtocolDTO.setStartDateTypeCode(CdConverter.convertToCd(studyProtocol.getStartDateTypeCode()));
        studyProtocolDTO.setStartDate(TsConverter.convertToTs(studyProtocol.getStartDate()));
        studyProtocolDTO.setPrimaryCompletionDate(TsConverter.convertToTs(studyProtocol.getPrimaryCompletionDate()));
        studyProtocolDTO.setPrimaryPurposeCode(CdConverter.convertToCd(studyProtocol.getPrimaryPurposeCode()));
        studyProtocolDTO.setPrimaryPurposeOtherText(
                StConverter.convertToSt(studyProtocol.getPrimaryPurposeOtherText()));
        studyProtocolDTO.setPhaseOtherText(StConverter.convertToSt(studyProtocol.getPhaseOtherText()));
        studyProtocolDTO.setStudyClassificationCode(
                CdConverter.convertToCd(studyProtocol.getStudyClassificationCode()));
        studyProtocolDTO.setMaximumTargetAccrualNumber(
                IntConverter.convertToInt(studyProtocol.getMaximumTargetAccrualNumber()));
        return studyProtocolDTO;
    }

    /**
     *
     * @param studyProtocolDTO studyProtocolDTO
     * @return StudyProtocol StudyProtocol
     */
    public static StudyProtocol convertFromDTOToDomain(StudyProtocolDTO studyProtocolDTO) {
        StudyProtocol studyProtocol = new StudyProtocol();
        if (studyProtocolDTO.getIdentifier() != null) {
            studyProtocol.setId(Long.valueOf(studyProtocolDTO.getIdentifier().getExtension()));
        }
        if (studyProtocolDTO.getAssignedIdentifier() != null) {
            studyProtocol.setIdentifier(studyProtocolDTO.getAssignedIdentifier().getExtension());
        }

        if (studyProtocolDTO.getAccrualReportingMethodCode() != null) {
            studyProtocol.setAccrualReportingMethodCode(
                    AccrualReportingMethodCode.getByCode(studyProtocolDTO.getAccrualReportingMethodCode().getCode()));
        }
        studyProtocol.setAcronym(StConverter.convertToString(studyProtocolDTO.getAcronym()));
        studyProtocol.setExpandedAccessIndicator(
                BlConverter.covertToBoolean(studyProtocolDTO.getExpandedAccessIndicator()));
        studyProtocol.setId(IiConverter.convertToLong(studyProtocolDTO.getIdentifier()));
        if (studyProtocolDTO.getMonitorCode() != null) {
            studyProtocol.setMonitorCode(MonitorCode.getByCode(studyProtocolDTO.getMonitorCode().getCode()));
        }

        studyProtocol.setOfficialTitle(StConverter.convertToString(studyProtocolDTO.getOfficialTitle()));
        if (studyProtocolDTO.getPhaseCode() != null) {
            studyProtocol.setPhaseCode(PhaseCode.getByCode(studyProtocolDTO.getPhaseCode().getCode()));
        }
        if (studyProtocolDTO.getPrimaryCompletionDateTypeCode() != null) {
            studyProtocol.setPrimaryCompletionDateTypeCode(ActualAnticipatedTypeCode.getByCode(
                    studyProtocolDTO.getPrimaryCompletionDateTypeCode().getCode()));
        }
        if (studyProtocolDTO.getStartDateTypeCode() != null) {
            studyProtocol.setStartDateTypeCode(ActualAnticipatedTypeCode.getByCode(
                    studyProtocolDTO.getStartDateTypeCode().getCode()));

        }
        if (studyProtocolDTO.getStartDate() != null) {
            studyProtocol.setStartDate(
                    TsConverter.convertToTimestamp(studyProtocolDTO.getStartDate()));
        }
        if (studyProtocolDTO.getPrimaryCompletionDate() != null) {
            studyProtocol.setPrimaryCompletionDate(
                    TsConverter.convertToTimestamp(studyProtocolDTO.getPrimaryCompletionDate()));
        }
        if (studyProtocolDTO.getPrimaryPurposeCode() != null) {
            studyProtocol.setPrimaryPurposeCode(PrimaryPurposeCode.getByCode(
                    studyProtocolDTO.getPrimaryPurposeCode().getCode()));
        }
        if (studyProtocolDTO.getPrimaryPurposeOtherText() != null) {
            studyProtocol.setPrimaryPurposeOtherText(StConverter.convertToString(
                    studyProtocolDTO.getPrimaryPurposeOtherText()));
        }
        if (studyProtocolDTO.getPhaseOtherText() != null) {
            studyProtocol.setPhaseOtherText(StConverter.convertToString(studyProtocolDTO.getPhaseOtherText()));
        }
        if (studyProtocolDTO.getMaximumTargetAccrualNumber() != null) {
            studyProtocol.setMaximumTargetAccrualNumber(studyProtocolDTO.getMaximumTargetAccrualNumber().getValue());
        } 
        if (studyProtocolDTO.getStudyClassificationCode() != null) {
            studyProtocol.setStudyClassificationCode(
                    StudyClassificationCode.getByCode(studyProtocolDTO.getStudyClassificationCode().getCode()));
        }
        return studyProtocol;
    }
}
