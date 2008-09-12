package gov.nih.nci.pa.iso.convert;

import gov.nih.nci.pa.domain.InterventionalStudyProtocol;
import gov.nih.nci.pa.enums.AccrualReportingMethodCode;
import gov.nih.nci.pa.enums.ActualAnticipatedTypeCode;
import gov.nih.nci.pa.enums.AllocationCode;
import gov.nih.nci.pa.enums.MonitorCode;
import gov.nih.nci.pa.enums.PhaseCode;
import gov.nih.nci.pa.iso.dto.InterventionalStudyProtocolDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;


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
        ispDTO.setAcronym(StConverter.convertToSt(isp.getAcronym()));
        ispDTO.setAccrualReportingMethodCode(
                CdConverter.convertToCd(isp.getAccrualReportingMethodCode()));
        ispDTO.setExpandedAccessIndicator(
                BlConverter.convertToBl(isp.getExpandedAccessIndicator()));
        ispDTO.setIdentifier(IiConverter.convertToIi(isp.getIdentifier()));
        ispDTO.setIi(IiConverter.convertToIi(isp.getId()));
        ispDTO.setMonitorCode(
                CdConverter.convertToCd(isp.getMonitorCode()));
        ispDTO.setOfficialTitle(StConverter.convertToSt(isp.getOfficialTitle()));
        ispDTO.setPhaseCode(CdConverter.convertToCd(isp.getPhaseCode()));
        
        ispDTO.setPrimaryCompletionDateTypeCode(
                CdConverter.convertToCd(isp.getPrimaryCompletionDateTypeCode()));
        ispDTO.setStartDateTypeCode(CdConverter.convertToCd(isp.getStartDateTypeCode()));
        ispDTO.setAllocationCode(CdConverter.convertToCd(isp.getAllocationCode()));
        ispDTO.setDelayedpostingIndicator(BlConverter.convertToBl(isp.getDelayedpostingIndicator()));
        ispDTO.setFdaRegulatedIndicator(BlConverter.convertToBl(isp.getFdaRegulatedIndicator()));
        ispDTO.setSection801Indicator(BlConverter.convertToBl(isp.getSection801Indicator()));
        ispDTO.setDataMonitoringCommitteInd(BlConverter.convertToBl(
                isp.getDataMonitoringCommitteeAppointedIndicator()));
        ispDTO.setIndIdeIndicator(BlConverter.convertToBl(isp.getIndIdeIndicator()));
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
        if (ispDTO.getAccrualReportingMethodCode() != null) {
            isp.setAccrualReportingMethodCode(
                    AccrualReportingMethodCode.getByCode(ispDTO.getAccrualReportingMethodCode().getCode()));
        }
        isp.setAcronym(StConverter.convertToString(ispDTO.getAcronym()));
        if (ispDTO.getIdentifier() != null) {
            isp.setIdentifier(ispDTO.getIdentifier().getExtension());
        }
        isp.setExpandedAccessIndicator(
                BlConverter.covertToBoolean(ispDTO.getExpandedAccessIndicator()));
        isp.setId(IiConverter.convertToLong(ispDTO.getIi()));
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
        //@todo: calculate TS convertion
        return isp;
    }


}
