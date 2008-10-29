package gov.nih.nci.pa.iso.convert;

import gov.nih.nci.pa.domain.InterventionalStudyProtocol;
import gov.nih.nci.pa.enums.AccrualReportingMethodCode;
import gov.nih.nci.pa.enums.ActualAnticipatedTypeCode;
import gov.nih.nci.pa.enums.AllocationCode;
import gov.nih.nci.pa.enums.BlindingSchemaCode;
import gov.nih.nci.pa.enums.DesignConfigurationCode;
import gov.nih.nci.pa.enums.MonitorCode;
import gov.nih.nci.pa.enums.PhaseCode;
import gov.nih.nci.pa.enums.PrimaryPurposeCode;
import gov.nih.nci.pa.enums.StudyClassificationCode;
import gov.nih.nci.pa.iso.dto.InterventionalStudyProtocolDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.IntConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;


/**
 * Convert InterventionalStudyProtocol domain to DTO.
 *
 * @author Naveen Amiruddin
 * @since 08/26/2008
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
@SuppressWarnings({"PMD.NPathComplexity" , "PMD.CyclomaticComplexity", "PMD.ExcessiveMethodLength" })
public class InterventionalStudyProtocolConverter extends
        StudyProtocolConverter {

    /**
     *
     * @param isp InterventionalStudyProtocol
     * @return InterventionalStudyProtocolDTO InterventionalStudyProtocolDTO
     */
    public static InterventionalStudyProtocolDTO convertFromDomainToDTO(InterventionalStudyProtocol isp) {
        InterventionalStudyProtocolDTO ispDTO = new InterventionalStudyProtocolDTO();
        ispDTO.setAssignedIdentifier(IiConverter.convertToIi(isp.getIdentifier()));
        ispDTO.setIdentifier(IiConverter.convertToIi(isp.getId()));

        ispDTO.setAcronym(StConverter.convertToSt(isp.getAcronym()));
        ispDTO.setAccrualReportingMethodCode(
                CdConverter.convertToCd(isp.getAccrualReportingMethodCode()));
        ispDTO.setExpandedAccessIndicator(
                BlConverter.convertToBl(isp.getExpandedAccessIndicator()));

        ispDTO.setMonitorCode(
                CdConverter.convertToCd(isp.getMonitorCode()));
        ispDTO.setOfficialTitle(StConverter.convertToSt(isp.getOfficialTitle()));
        ispDTO.setPhaseCode(CdConverter.convertToCd(isp.getPhaseCode()));
        ispDTO.setStartDateTypeCode(CdConverter.convertToCd(isp.getStartDateTypeCode()));
        ispDTO.setStartDate(TsConverter.convertToTs(isp.getStartDate()));
        ispDTO.setPrimaryCompletionDateTypeCode(
                CdConverter.convertToCd(isp.getPrimaryCompletionDateTypeCode()));
        ispDTO.setPrimaryCompletionDate(TsConverter.convertToTs(isp.getPrimaryCompletionDate()));
        ispDTO.setAllocationCode(CdConverter.convertToCd(isp.getAllocationCode()));
        ispDTO.setDelayedpostingIndicator(BlConverter.convertToBl(isp.getDelayedpostingIndicator()));
        ispDTO.setFdaRegulatedIndicator(BlConverter.convertToBl(isp.getFdaRegulatedIndicator()));
        ispDTO.setSection801Indicator(BlConverter.convertToBl(isp.getSection801Indicator()));
        ispDTO.setDataMonitoringCommitteInd(BlConverter.convertToBl(
                isp.getDataMonitoringCommitteeAppointedIndicator()));
        ispDTO.setIndIdeIndicator(BlConverter.convertToBl(isp.getIndIdeIndicator()));
        ispDTO.setNumberOfInterventionGroups(IntConverter.convertToInt(isp.getNumberOfInterventionGroups()));
        ispDTO.setDesignConfigurationCode(CdConverter.convertToCd(isp.getDesignConfigurationCode()));
        ispDTO.setBlindingSchemaCode(CdConverter.convertToCd(isp.getBlindingSchemaCode()));
        ispDTO.setPrimaryPurposeCode(CdConverter.convertToCd(isp.getPrimaryPurposeCode()));
        ispDTO.setPrimaryPurposeOtherText(
                StConverter.convertToSt(isp.getPrimaryPurposeOtherText()));
        ispDTO.setPhaseOtherText(StConverter.convertToSt(isp.getPhaseOtherText()));
        ispDTO.setStudyClassificationCode(
                CdConverter.convertToCd(isp.getStudyClassificationCode()));
        ispDTO.setMaximumTargetAccrualNumber(
                IntConverter.convertToInt(isp.getMaximumTargetAccrualNumber()));
        //ispDTO.setBlindingRoleCode(CdConverter.convertToCd(isp.getBlindingRoleCode()));
        //@todo: convert iso timestamp
        return ispDTO;
    }

    /**
     *
     * @param ispDTO InterventionalStudyProtocolDTO
     * @return InterventionalStudyProtocol InterventionalStudyProtocol
     */
    public static InterventionalStudyProtocol convertFromDTOToDomain(InterventionalStudyProtocolDTO ispDTO) {
        InterventionalStudyProtocol isp = new InterventionalStudyProtocol();
        if (ispDTO.getIdentifier() != null) {
            isp.setId(IiConverter.convertToLong(ispDTO.getIdentifier()));
        }
        if (ispDTO.getAssignedIdentifier() != null) {
            isp.setIdentifier(ispDTO.getAssignedIdentifier().getExtension());
        }

        if (ispDTO.getAccrualReportingMethodCode() != null) {
            isp.setAccrualReportingMethodCode(
                    AccrualReportingMethodCode.getByCode(ispDTO.getAccrualReportingMethodCode().getCode()));
        }
        isp.setAcronym(StConverter.convertToString(ispDTO.getAcronym()));
        isp.setExpandedAccessIndicator(
                BlConverter.covertToBoolean(ispDTO.getExpandedAccessIndicator()));
        if (ispDTO.getMonitorCode() != null) {
            isp.setMonitorCode(MonitorCode.getByCode(ispDTO.getMonitorCode().getCode()));
        }

        isp.setOfficialTitle(StConverter.convertToString(ispDTO.getOfficialTitle()));
        if (ispDTO.getPhaseCode() != null) {
            isp.setPhaseCode(PhaseCode.getByCode(ispDTO.getPhaseCode().getCode()));
        }
        if (ispDTO.getPrimaryCompletionDateTypeCode() != null) {
            isp.setPrimaryCompletionDateTypeCode(ActualAnticipatedTypeCode.getByCode(
                    ispDTO.getPrimaryCompletionDateTypeCode().getCode()));
        }
        if (ispDTO.getStartDateTypeCode() != null) {
            isp.setStartDateTypeCode(ActualAnticipatedTypeCode.getByCode(
                    ispDTO.getStartDateTypeCode().getCode()));

        }
        if (ispDTO.getStartDate() != null) {
            isp.setStartDate(
                    TsConverter.convertToTimestamp(ispDTO.getStartDate()));
        }
        if (ispDTO.getPrimaryCompletionDate() != null) {
            isp.setPrimaryCompletionDate(
                    TsConverter.convertToTimestamp(ispDTO.getPrimaryCompletionDate()));
        }
        if (ispDTO.getAllocationCode() != null) {
            isp.setAllocationCode(AllocationCode.getByCode(ispDTO.getAllocationCode().getCode()));
        }
        if (ispDTO.getDelayedpostingIndicator() != null) {
            isp.setDelayedpostingIndicator(BlConverter.covertToBoolean(ispDTO.getDelayedpostingIndicator()));
        }
        if (ispDTO.getFdaRegulatedIndicator() != null) {
            isp.setFdaRegulatedIndicator(BlConverter.covertToBoolean(ispDTO.getFdaRegulatedIndicator()));
        }
        if (ispDTO.getSection801Indicator() != null) {
            isp.setSection801Indicator(BlConverter.covertToBoolean(ispDTO.getSection801Indicator()));
        }
        if (ispDTO.getDataMonitoringCommitteInd() != null) {
            isp.setDataMonitoringCommitteeAppointedIndicator((
                    BlConverter.covertToBoolean(ispDTO.getDataMonitoringCommitteInd())));
        }
        if (ispDTO.getIndIdeIndicator() != null) {
            isp.setIndIdeIndicator(BlConverter.covertToBoolean(ispDTO.getIndIdeIndicator()));
        }
        if (ispDTO.getDesignConfigurationCode() != null) {
            isp.setDesignConfigurationCode(DesignConfigurationCode.getByCode(
                    ispDTO.getDesignConfigurationCode().getCode()));
        } 
        if (ispDTO.getBlindingSchemaCode() != null) {
            isp.setBlindingSchemaCode(BlindingSchemaCode.getByCode(
                    ispDTO.getBlindingSchemaCode().getCode()));
        } 
        if (ispDTO.getNumberOfInterventionGroups() != null) {
            isp.setNumberOfInterventionGroups(ispDTO.getNumberOfInterventionGroups().getValue());
        } 
        if (ispDTO.getPrimaryPurposeCode() != null) {
            isp.setPrimaryPurposeCode(PrimaryPurposeCode.getByCode(
                    ispDTO.getPrimaryPurposeCode().getCode()));
        }
        if (ispDTO.getPrimaryPurposeOtherText() != null) {
            isp.setPrimaryPurposeOtherText(StConverter.convertToString(
                    ispDTO.getPrimaryPurposeOtherText()));
        }
        if (ispDTO.getPhaseOtherText() != null) {
            isp.setPhaseOtherText(StConverter.convertToString(ispDTO.getPhaseOtherText()));
        }
        if (ispDTO.getMaximumTargetAccrualNumber() != null) {
            isp.setMaximumTargetAccrualNumber(ispDTO.getMaximumTargetAccrualNumber().getValue());
        } 
        if (ispDTO.getStudyClassificationCode() != null) {
            isp.setStudyClassificationCode(
                    StudyClassificationCode.getByCode(ispDTO.getStudyClassificationCode().getCode()));
        }
        /*if (ispDTO.getBlindingRoleCode() != null) {
            isp.setBlindingRoleCode(BlindingRoleCode.getByCode(
                    ispDTO.getBlindingRoleCode().getCode()));
        }*/        
        //@todo: calculate TS convertion
        return isp;
    }


}
