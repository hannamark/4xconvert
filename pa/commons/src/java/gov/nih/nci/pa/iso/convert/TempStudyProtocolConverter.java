package gov.nih.nci.pa.iso.convert;

import gov.nih.nci.pa.domain.TempStudyProtocol;
import gov.nih.nci.pa.enums.ActualAnticipatedTypeCode;
import gov.nih.nci.pa.enums.PhaseCode;
import gov.nih.nci.pa.enums.PrimaryPurposeCode;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.enums.SummaryFourFundingCategoryCode;
import gov.nih.nci.pa.iso.dto.TempStudyProtocolDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
/**
 * 
 * @author Vrushali
 *
 */
@SuppressWarnings ({"PMD.ExcessiveMethodLength" })
public class TempStudyProtocolConverter {
    /**
     * 
     * @param tempStudyProtocol sp
     * @return dto
     */
    public static TempStudyProtocolDTO convertFromDomainToDTO(TempStudyProtocol tempStudyProtocol) {
        return convertFromDomainToDTO(tempStudyProtocol, new TempStudyProtocolDTO());
    }
    /**
     * 
     * @param tempStudyProtocol domain
     * @param tempStudyProtocolDTO isoDTO
     * @return dto
     */
    public static TempStudyProtocolDTO convertFromDomainToDTO(
            TempStudyProtocol tempStudyProtocol, TempStudyProtocolDTO tempStudyProtocolDTO) {
        tempStudyProtocolDTO.setIdentifier(IiConverter.convertToIi(tempStudyProtocol.getId()));
        tempStudyProtocolDTO.setNctIdentifier(StConverter.convertToSt(tempStudyProtocol.getNctIdentifier()));
        tempStudyProtocolDTO.setCtepIdentifier(StConverter.convertToSt(tempStudyProtocol.getCtepIdentifier()));
        tempStudyProtocolDTO.setDcpIdentifier(StConverter.convertToSt(tempStudyProtocol.getDcpIdentifier()));
        tempStudyProtocolDTO.setOfficialTitle(StConverter.convertToSt(tempStudyProtocol.getOfficialTitle()));
        tempStudyProtocolDTO.setPhaseCode(CdConverter.convertToCd(
                tempStudyProtocol.getPhaseCode()));
        tempStudyProtocolDTO.setPhaseOtherText(StConverter.convertToSt(tempStudyProtocol.getPhaseOtherText()));
        tempStudyProtocolDTO.setPrimaryPurposeCode(CdConverter.convertToCd(
                tempStudyProtocol.getPrimaryPurposeCode()));
        tempStudyProtocolDTO.setPrimaryPurposeOtherText(StConverter.convertToSt(
                tempStudyProtocol.getPrimaryPurposeOtherText()));
        tempStudyProtocolDTO.setLocalProtocolIdentifier(StConverter.convertToSt(
                tempStudyProtocol.getLocalProtocolIdentifier()));
        tempStudyProtocolDTO.setLeadOrganizationIdentifier(IiConverter.convertToPoOrganizationIi(
                tempStudyProtocol.getLeadOrganizationIdentifier()));
        tempStudyProtocolDTO.setPiIdentifier(IiConverter.convertToPoPersonIi(tempStudyProtocol.getPiIdentifier()));
        tempStudyProtocolDTO.setSponsorIdentifier(IiConverter.convertToIi(
                tempStudyProtocol.getSponsorIdentifier()));
        tempStudyProtocolDTO.setResponsiblePartyType(StConverter.convertToSt(
                tempStudyProtocol.getResponsiblePartyType()));
        tempStudyProtocolDTO.setResponsibleIdentifier(IiConverter.convertToIi(
                tempStudyProtocol.getResponsibleIdentifier()));
        tempStudyProtocolDTO.setContactEmail(StConverter.convertToSt(tempStudyProtocol.getContactEmail()));
        tempStudyProtocolDTO.setContactPhone(StConverter.convertToSt(tempStudyProtocol.getContactPhone()));
        tempStudyProtocolDTO.setSummaryFourOrgIdentifier(IiConverter.convertToPoOrganizationIi(
                tempStudyProtocol.getSummaryFourOrgIdentifier()));
        tempStudyProtocolDTO.setSummaryFourFundingCategoryCode(CdConverter.convertToCd(
                tempStudyProtocol.getSummaryFourFundingCategoryCode()));
        tempStudyProtocolDTO.setProgramCodeText(StConverter.convertToSt(tempStudyProtocol.getProgramCodeText()));
        tempStudyProtocolDTO.setTrialStatusCode(CdConverter.convertToCd(tempStudyProtocol.getTrialStatusCode()));
        tempStudyProtocolDTO.setTrialStatusDate(TsConverter.convertToTs(tempStudyProtocol.getTrialStatusDate()));
        tempStudyProtocolDTO.setStatusReason(StConverter.convertToSt(tempStudyProtocol.getReason()));
        tempStudyProtocolDTO.setPrimaryCompletionDate(TsConverter.convertToTs(
                tempStudyProtocol.getPrimaryCompletionDate()));
        tempStudyProtocolDTO.setPrimaryCompletionDateTypeCode(
                CdConverter.convertToCd(tempStudyProtocol.getPrimaryCompletionDateTypeCode()));
        tempStudyProtocolDTO.setStartDate(TsConverter.convertToTs(tempStudyProtocol.getStartDate()));
        tempStudyProtocolDTO.setStartDateTypeCode(CdConverter.convertToCd(
                tempStudyProtocol.getStartDateTypeCode()));
        tempStudyProtocolDTO.setTrialType(StConverter.convertToSt(tempStudyProtocol.getTrialType()));
        tempStudyProtocolDTO.setFdaRegulatedIndicator(BlConverter.convertToBl(
                tempStudyProtocol.getFdaRegulatedIndicator()));
        tempStudyProtocolDTO.setSection801Indicator(BlConverter.convertToBl(
                tempStudyProtocol.getSection801Indicator()));
        tempStudyProtocolDTO.setDelayedpostingIndicator(BlConverter.convertToBl(
                tempStudyProtocol.getDelayedpostingIndicator()));
        tempStudyProtocolDTO.setDataMonitoringCommitteeAppointedIndicator(
                BlConverter.convertToBl(tempStudyProtocol.getDataMonitoringCommitteeAppointedIndicator()));
        tempStudyProtocolDTO.setOversightAuthorityCountryId(IiConverter.convertToIi(
                tempStudyProtocol.getOversightAuthorityCountryId()));
        tempStudyProtocolDTO.setOversightAuthorityOrgId(IiConverter.convertToIi(
                tempStudyProtocol.getOversightAuthorityOrgId()));
        tempStudyProtocolDTO.setProprietaryTrialIndicator(BlConverter.convertToBl(
                tempStudyProtocol.getProprietaryTrialIndicator()));
        tempStudyProtocolDTO.setNciDesignatedCancerCenterIndicator(BlConverter.convertToBl(
                tempStudyProtocol.getNciDesignatedCancerCenterIndicator()));
        tempStudyProtocolDTO.setUserLastCreated(StConverter.convertToSt(
                tempStudyProtocol.getUserLastCreated()));
        return tempStudyProtocolDTO;
    }
    /**
     * 
     * @param tempStudyProtocolDTO isoDTO
     * @return domain
     */
    public static TempStudyProtocol convertFromDTOToDomain(TempStudyProtocolDTO tempStudyProtocolDTO) {
        return convertFromDTOToDomain(tempStudyProtocolDTO , new TempStudyProtocol());
    }
    /**
     * 
     * @param tempStudyProtocolDTO isoDTO
     * @param tempStudyProtocol domain
     * @return domain
     */
    public static TempStudyProtocol convertFromDTOToDomain(
            TempStudyProtocolDTO tempStudyProtocolDTO, TempStudyProtocol tempStudyProtocol) {
        tempStudyProtocol.setId(IiConverter.convertToLong(tempStudyProtocolDTO.getIdentifier()));
        tempStudyProtocol.setNctIdentifier(StConverter.convertToString(tempStudyProtocolDTO.getNctIdentifier()));
        tempStudyProtocol.setCtepIdentifier(StConverter.convertToString(tempStudyProtocolDTO.getCtepIdentifier()));
        tempStudyProtocol.setDcpIdentifier(StConverter.convertToString(tempStudyProtocolDTO.getDcpIdentifier()));
        tempStudyProtocol.setOfficialTitle(StConverter.convertToString(tempStudyProtocolDTO.getOfficialTitle()));
        tempStudyProtocol.setPhaseCode(PhaseCode.getByCode(CdConverter.convertCdToString(
                tempStudyProtocolDTO.getPhaseCode())));
        tempStudyProtocol.setPhaseOtherText(StConverter.convertToString(tempStudyProtocolDTO.getPhaseOtherText()));
        tempStudyProtocol.setPrimaryPurposeCode(PrimaryPurposeCode.getByCode(CdConverter.convertCdToString(
                tempStudyProtocolDTO.getPrimaryPurposeCode())));
        tempStudyProtocol.setPrimaryPurposeOtherText(StConverter.convertToString(
                tempStudyProtocolDTO.getPrimaryPurposeOtherText()));
        tempStudyProtocol.setLocalProtocolIdentifier(StConverter.convertToString(
                tempStudyProtocolDTO.getLocalProtocolIdentifier()));
        tempStudyProtocol.setLeadOrganizationIdentifier(IiConverter.convertToString(
                tempStudyProtocolDTO.getLeadOrganizationIdentifier()));
        tempStudyProtocol.setPiIdentifier(IiConverter.convertToString(tempStudyProtocolDTO.getPiIdentifier()));
        tempStudyProtocol.setSponsorIdentifier(IiConverter.convertToString(
                tempStudyProtocolDTO.getSponsorIdentifier()));
        tempStudyProtocol.setResponsiblePartyType(StConverter.convertToString(
                tempStudyProtocolDTO.getResponsiblePartyType()));
        tempStudyProtocol.setResponsibleIdentifier(IiConverter.convertToString(
                tempStudyProtocolDTO.getResponsibleIdentifier()));
        tempStudyProtocol.setContactEmail(StConverter.convertToString(tempStudyProtocolDTO.getContactEmail()));
        tempStudyProtocol.setContactPhone(StConverter.convertToString(tempStudyProtocolDTO.getContactPhone()));
        tempStudyProtocol.setSummaryFourOrgIdentifier(IiConverter.convertToString(
                tempStudyProtocolDTO.getSummaryFourOrgIdentifier()));
        tempStudyProtocol.setSummaryFourFundingCategoryCode(SummaryFourFundingCategoryCode.getByCode(
                CdConverter.convertCdToString(tempStudyProtocolDTO.getSummaryFourFundingCategoryCode())));
        tempStudyProtocol.setProgramCodeText(StConverter.convertToString(tempStudyProtocolDTO.getProgramCodeText()));
        tempStudyProtocol.setTrialStatusCode(StudyStatusCode.getByCode(CdConverter.convertCdToString(
                tempStudyProtocolDTO.getTrialStatusCode())));
        tempStudyProtocol.setTrialStatusDate(TsConverter.convertToTimestamp(
                tempStudyProtocolDTO.getTrialStatusDate()));
        tempStudyProtocol.setReason(StConverter.convertToString(tempStudyProtocolDTO.getStatusReason()));
        tempStudyProtocol.setPrimaryCompletionDate(TsConverter.convertToTimestamp(
                tempStudyProtocolDTO.getPrimaryCompletionDate()));
        tempStudyProtocol.setPrimaryCompletionDateTypeCode(ActualAnticipatedTypeCode.getByCode(
                CdConverter.convertCdToString(tempStudyProtocolDTO.getPrimaryCompletionDateTypeCode())));
        tempStudyProtocol.setStartDate(TsConverter.convertToTimestamp(tempStudyProtocolDTO.getStartDate()));
        tempStudyProtocol.setStartDateTypeCode(ActualAnticipatedTypeCode.getByCode(CdConverter.convertCdToString(
                tempStudyProtocolDTO.getStartDateTypeCode())));
        tempStudyProtocol.setTrialType(StConverter.convertToString(tempStudyProtocolDTO.getTrialType()));
        tempStudyProtocol.setFdaRegulatedIndicator(BlConverter.covertToBoolean(
                tempStudyProtocolDTO.getFdaRegulatedIndicator()));
        tempStudyProtocol.setSection801Indicator(BlConverter.covertToBoolean(
                tempStudyProtocolDTO.getSection801Indicator()));
        tempStudyProtocol.setDelayedpostingIndicator(BlConverter.covertToBoolean(
                tempStudyProtocolDTO.getDelayedpostingIndicator()));
        tempStudyProtocol.setDataMonitoringCommitteeAppointedIndicator(
                BlConverter.covertToBoolean(tempStudyProtocolDTO.getDataMonitoringCommitteeAppointedIndicator()));
        tempStudyProtocol.setOversightAuthorityCountryId(IiConverter.convertToString(
                tempStudyProtocolDTO.getOversightAuthorityCountryId()));
        tempStudyProtocol.setOversightAuthorityOrgId(IiConverter.convertToString(
                tempStudyProtocolDTO.getOversightAuthorityOrgId()));
        tempStudyProtocol.setProprietaryTrialIndicator(BlConverter.covertToBoolean(
                tempStudyProtocolDTO.getProprietaryTrialIndicator()));
        tempStudyProtocol.setNciDesignatedCancerCenterIndicator(BlConverter.covertToBool(
                tempStudyProtocolDTO.getNciDesignatedCancerCenterIndicator()));
        tempStudyProtocol.setUserLastCreated(StConverter.convertToString(
                tempStudyProtocolDTO.getUserLastCreated()));
        return tempStudyProtocol;
    }
}
