package gov.nih.nci.pa.iso.convert;


import gov.nih.nci.pa.domain.InterventionalStudyProtocol;
import gov.nih.nci.pa.domain.ObservationalStudyProtocol;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.enums.AccrualReportingMethodCode;
import gov.nih.nci.pa.enums.ActualAnticipatedTypeCode;
import gov.nih.nci.pa.enums.PhaseCode;
import gov.nih.nci.pa.enums.PrimaryPurposeCode;
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
        return convertFromDomainToDTO(studyProtocol, new StudyProtocolDTO());
    }
    
    /**
    *
    * @param studyProtocolDTO studyProtocolDTO
    * @return StudyProtocol StudyProtocol
    */
   public static StudyProtocol convertFromDTOToDomain(StudyProtocolDTO studyProtocolDTO) {
       return convertFromDTOToDomain(studyProtocolDTO , new StudyProtocol());
   }
    

    /**
     * 
     * @param studyProtocol sp 
     * @param studyProtocolDTO spDTO
     * @return StudyProtocolDTO sp
     */
    public static StudyProtocolDTO convertFromDomainToDTO(
            StudyProtocol studyProtocol , StudyProtocolDTO studyProtocolDTO) {
        studyProtocolDTO.setAcronym(StConverter.convertToSt(studyProtocol.getAcronym()));
        studyProtocolDTO.setAccrualReportingMethodCode(
                CdConverter.convertToCd(studyProtocol.getAccrualReportingMethodCode()));
        studyProtocolDTO.setAssignedIdentifier(IiConverter.convertToIi(studyProtocol.getIdentifier()));
        studyProtocolDTO.setDataMonitoringCommitteeAppointedIndicator(
                BlConverter.convertToBl(studyProtocol.getDataMonitoringCommitteeAppointedIndicator()));
        studyProtocolDTO.setDelayedpostingIndicator(
                BlConverter.convertToBl(studyProtocol.getDelayedpostingIndicator()));

        studyProtocolDTO.setExpandedAccessIndicator(
                BlConverter.convertToBl(studyProtocol.getExpandedAccessIndicator()));
        studyProtocolDTO.setFdaRegulatedIndicator(BlConverter.convertToBl(studyProtocol.getFdaRegulatedIndicator()));
        studyProtocolDTO.setReviewBoardApprovalRequiredIndicator(
                BlConverter.convertToBl(studyProtocol.getReviewBoardApprovalRequiredIndicator()));
        studyProtocolDTO.setOfficialTitle(StConverter.convertToSt(studyProtocol.getOfficialTitle()));
        studyProtocolDTO.setMaximumTargetAccrualNumber(
                IntConverter.convertToInt(studyProtocol.getMaximumTargetAccrualNumber()));
        studyProtocolDTO.setIdentifier(IiConverter.converToProtocolIi(studyProtocol.getId()));
        studyProtocolDTO.setPhaseCode(CdConverter.convertToCd(studyProtocol.getPhaseCode()));
        studyProtocolDTO.setPhaseOtherText(StConverter.convertToSt(studyProtocol.getPhaseOtherText()));
        studyProtocolDTO.setPrimaryCompletionDate(TsConverter.convertToTs(studyProtocol.getPrimaryCompletionDate()));
        studyProtocolDTO.setPrimaryCompletionDateTypeCode(
                CdConverter.convertToCd(studyProtocol.getPrimaryCompletionDateTypeCode()));
        studyProtocolDTO.setPrimaryPurposeCode(CdConverter.convertToCd(studyProtocol.getPrimaryPurposeCode()));
        studyProtocolDTO.setPrimaryPurposeOtherText(
                StConverter.convertToSt(studyProtocol.getPrimaryPurposeOtherText()));
        studyProtocolDTO.setPublicDescription(
                StConverter.convertToSt(studyProtocol.getPublicDescription()));
        studyProtocolDTO.setPublicTitle(
                StConverter.convertToSt(studyProtocol.getPublicTitle()));
        studyProtocolDTO.setRecordVerificationDate(TsConverter.convertToTs(studyProtocol.getRecordVerificationDate()));
        studyProtocolDTO.setScientificDescription(StConverter.convertToSt(studyProtocol.getScientificDescription()));
        studyProtocolDTO.setSection801Indicator(BlConverter.convertToBl(studyProtocol.getSection801Indicator()));
        studyProtocolDTO.setStartDate(TsConverter.convertToTs(studyProtocol.getStartDate()));
        studyProtocolDTO.setStartDateTypeCode(CdConverter.convertToCd(studyProtocol.getStartDateTypeCode()));
        studyProtocolDTO.setKeywordText(StConverter.convertToSt(studyProtocol.getKeywordText()));
        studyProtocolDTO.setAcceptHealthyVolunteersIndicator(BlConverter.convertToBl(
                studyProtocol.getAcceptHealthyVolunteersIndicator()));
        if (studyProtocol instanceof ObservationalStudyProtocol) {
            studyProtocolDTO.setStudyProtocolType(StConverter.convertToSt("ObservationalStudyProtocol"));
        } else if (studyProtocol instanceof InterventionalStudyProtocol) {
            studyProtocolDTO.setStudyProtocolType(StConverter.convertToSt("InterventionalStudyProtocol"));
        } else {
            studyProtocolDTO.setStudyProtocolType(StConverter.convertToSt(studyProtocol.getClass().getName()));
        }
        
        return studyProtocolDTO;
    }

    /**
    *
    * @param studyProtocolDTO studyProtocolDTO
    * @param studyProtocol studyProtocol
    * @return StudyProtocol StudyProtocol
    */
   public static StudyProtocol convertFromDTOToDomain(StudyProtocolDTO studyProtocolDTO , 
           StudyProtocol studyProtocol) {

       studyProtocol.setId(IiConverter.convertToLong(studyProtocolDTO.getIdentifier()));
       studyProtocol.setAcronym(StConverter.convertToString(studyProtocolDTO.getAcronym()));
       if (studyProtocolDTO.getAssignedIdentifier() != null) {
           studyProtocol.setIdentifier(studyProtocolDTO.getAssignedIdentifier().getExtension());
       }
       if (studyProtocolDTO.getAccrualReportingMethodCode() != null) {
           studyProtocol.setAccrualReportingMethodCode(
                   AccrualReportingMethodCode.getByCode(studyProtocolDTO.getAccrualReportingMethodCode().getCode()));
       }
       if (studyProtocolDTO.getAssignedIdentifier() != null) {
           studyProtocol.setIdentifier(IiConverter.convertToString(studyProtocolDTO.getAssignedIdentifier()));
       }
       studyProtocol.setDataMonitoringCommitteeAppointedIndicator(
               BlConverter.covertToBoolean(studyProtocolDTO.getDataMonitoringCommitteeAppointedIndicator()));
       studyProtocol.setDelayedpostingIndicator(
               BlConverter.covertToBoolean(studyProtocolDTO.getDelayedpostingIndicator()));
       studyProtocol.setExpandedAccessIndicator(
               BlConverter.covertToBoolean(studyProtocolDTO.getExpandedAccessIndicator()));
       studyProtocol.setFdaRegulatedIndicator(
               BlConverter.covertToBoolean(studyProtocolDTO.getFdaRegulatedIndicator()));
       studyProtocol.setReviewBoardApprovalRequiredIndicator(
               BlConverter.covertToBoolean(studyProtocolDTO.getReviewBoardApprovalRequiredIndicator()));
       studyProtocol.setMaximumTargetAccrualNumber(
               IntConverter.convertToInteger(studyProtocolDTO.getMaximumTargetAccrualNumber()));
       studyProtocol.setOfficialTitle(StConverter.convertToString(studyProtocolDTO.getOfficialTitle()));
       if (studyProtocolDTO.getPhaseCode() != null) {
           studyProtocol.setPhaseCode(PhaseCode.getByCode(studyProtocolDTO.getPhaseCode().getCode()));
       }
       studyProtocol.setPhaseOtherText(StConverter.convertToString(studyProtocolDTO.getPhaseOtherText()));
       if (studyProtocolDTO.getPrimaryCompletionDate() != null) {
           studyProtocol.setPrimaryCompletionDate(
                   TsConverter.convertToTimestamp(studyProtocolDTO.getPrimaryCompletionDate()));
       }
       if (studyProtocolDTO.getPrimaryCompletionDateTypeCode() != null) {
           studyProtocol.setPrimaryCompletionDateTypeCode(ActualAnticipatedTypeCode.getByCode(
                   studyProtocolDTO.getPrimaryCompletionDateTypeCode().getCode()));
       }
       if (studyProtocolDTO.getPrimaryPurposeCode() != null) {
           studyProtocol.setPrimaryPurposeCode(PrimaryPurposeCode.getByCode(
                   studyProtocolDTO.getPrimaryPurposeCode().getCode()));
       }
       studyProtocol.setPrimaryPurposeOtherText(StConverter.convertToString(
               studyProtocolDTO.getPrimaryPurposeOtherText()));
       studyProtocol.setPublicDescription(StConverter.convertToString(
               studyProtocolDTO.getPublicDescription()));
       studyProtocol.setPublicTitle(StConverter.convertToString(studyProtocolDTO.getPublicTitle()));
       if (studyProtocolDTO.getRecordVerificationDate() != null) {
           studyProtocol.setRecordVerificationDate(
                   TsConverter.convertToTimestamp(studyProtocolDTO.getRecordVerificationDate()));
       }
       
       studyProtocol.setSection801Indicator(BlConverter.covertToBoolean(studyProtocolDTO.getSection801Indicator()));
       
       studyProtocol.setScientificDescription(StConverter.convertToString(
               studyProtocolDTO.getScientificDescription()));
       if (studyProtocolDTO.getStartDate() != null) {
           studyProtocol.setStartDate(
                   TsConverter.convertToTimestamp(studyProtocolDTO.getStartDate()));
       }
       if (studyProtocolDTO.getStartDateTypeCode() != null) {
           studyProtocol.setStartDateTypeCode(ActualAnticipatedTypeCode.getByCode(
                   studyProtocolDTO.getStartDateTypeCode().getCode()));

       }
       if (studyProtocolDTO.getKeywordText() != null) {
       studyProtocol.setKeywordText(studyProtocolDTO.getKeywordText().getValue());
       }
       studyProtocol.setAcceptHealthyVolunteersIndicator(BlConverter.covertToBoolean(
               studyProtocolDTO.getAcceptHealthyVolunteersIndicator()));
       return studyProtocol;
   }
    
}
