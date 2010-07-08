package gov.nih.nci.pa.iso.convert;

import gov.nih.nci.pa.domain.StudyProtocolStage;
import gov.nih.nci.pa.enums.ActualAnticipatedTypeCode;
import gov.nih.nci.pa.enums.PhaseCode;
import gov.nih.nci.pa.enums.PrimaryPurposeCode;
import gov.nih.nci.pa.enums.RecruitmentStatusCode;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.enums.SummaryFourFundingCategoryCode;
import gov.nih.nci.pa.iso.dto.StudyProtocolStageDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.IntConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;

import org.apache.commons.collections.CollectionUtils;
/**
 *
 * @author Vrushali
 *
 */
@SuppressWarnings ({"PMD.ExcessiveMethodLength" })
public class StudyProtocolStageConverter {
    /**
     *
     * @param studyProtocolStage sp
     * @return dto
     */
    public static StudyProtocolStageDTO convertFromDomainToDTO(StudyProtocolStage studyProtocolStage) {
        return convertFromDomainToDTO(studyProtocolStage, new StudyProtocolStageDTO());
    }
    /**
     *
     * @param studyProtocolStage domain
     * @param studyProtocolStageDTO isoDTO
     * @return dto
     */
    public static StudyProtocolStageDTO convertFromDomainToDTO(
            StudyProtocolStage studyProtocolStage, StudyProtocolStageDTO studyProtocolStageDTO) {
        studyProtocolStageDTO.setIdentifier(IiConverter.convertToIi(studyProtocolStage.getId()));
        studyProtocolStageDTO.setNctIdentifier(StConverter.convertToSt(studyProtocolStage.getNctIdentifier()));
        studyProtocolStageDTO.setOfficialTitle(StConverter.convertToSt(studyProtocolStage.getOfficialTitle()));
        studyProtocolStageDTO.setPhaseCode(CdConverter.convertToCd(
                studyProtocolStage.getPhaseCode()));
        studyProtocolStageDTO.setPhaseOtherText(StConverter.convertToSt(studyProtocolStage.getPhaseOtherText()));
        studyProtocolStageDTO.setPrimaryPurposeCode(CdConverter.convertToCd(
                studyProtocolStage.getPrimaryPurposeCode()));
        studyProtocolStageDTO.setPrimaryPurposeOtherText(StConverter.convertToSt(
                studyProtocolStage.getPrimaryPurposeOtherText()));
        studyProtocolStageDTO.setLocalProtocolIdentifier(StConverter.convertToSt(
                studyProtocolStage.getLocalProtocolIdentifier()));
        studyProtocolStageDTO.setLeadOrganizationIdentifier(IiConverter.convertToPoOrganizationIi(
                studyProtocolStage.getLeadOrganizationIdentifier()));
        studyProtocolStageDTO.setPiIdentifier(IiConverter.convertToPoPersonIi(studyProtocolStage.getPiIdentifier()));
        studyProtocolStageDTO.setSponsorIdentifier(IiConverter.convertToIi(
                studyProtocolStage.getSponsorIdentifier()));
        studyProtocolStageDTO.setResponsiblePartyType(StConverter.convertToSt(
                studyProtocolStage.getResponsiblePartyType()));
        studyProtocolStageDTO.setResponsibleIdentifier(IiConverter.convertToIi(
                studyProtocolStage.getResponsibleIdentifier()));
        studyProtocolStageDTO.setContactEmail(StConverter.convertToSt(studyProtocolStage.getContactEmail()));
        studyProtocolStageDTO.setContactPhone(StConverter.convertToSt(studyProtocolStage.getContactPhone()));
        studyProtocolStageDTO.setSummaryFourOrgIdentifier(IiConverter.convertToPoOrganizationIi(
                studyProtocolStage.getSummaryFourOrgIdentifier()));
        studyProtocolStageDTO.setSummaryFourFundingCategoryCode(CdConverter.convertToCd(
                studyProtocolStage.getSummaryFourFundingCategoryCode()));
        studyProtocolStageDTO.setProgramCodeText(StConverter.convertToSt(studyProtocolStage.getProgramCodeText()));
        studyProtocolStageDTO.setTrialStatusCode(CdConverter.convertToCd(studyProtocolStage.getTrialStatusCode()));
        studyProtocolStageDTO.setTrialStatusDate(TsConverter.convertToTs(studyProtocolStage.getTrialStatusDate()));
        studyProtocolStageDTO.setStatusReason(StConverter.convertToSt(studyProtocolStage.getReason()));
        studyProtocolStageDTO.setPrimaryCompletionDate(TsConverter.convertToTs(
                studyProtocolStage.getPrimaryCompletionDate()));
        studyProtocolStageDTO.setPrimaryCompletionDateTypeCode(
                CdConverter.convertToCd(studyProtocolStage.getPrimaryCompletionDateTypeCode()));
        studyProtocolStageDTO.setStartDate(TsConverter.convertToTs(studyProtocolStage.getStartDate()));
        studyProtocolStageDTO.setStartDateTypeCode(CdConverter.convertToCd(
                studyProtocolStage.getStartDateTypeCode()));
        studyProtocolStageDTO.setTrialType(StConverter.convertToSt(studyProtocolStage.getTrialType()));
        studyProtocolStageDTO.setFdaRegulatedIndicator(BlConverter.convertToBl(
                studyProtocolStage.getFdaRegulatedIndicator()));
        studyProtocolStageDTO.setSection801Indicator(BlConverter.convertToBl(
                studyProtocolStage.getSection801Indicator()));
        studyProtocolStageDTO.setDelayedpostingIndicator(BlConverter.convertToBl(
                studyProtocolStage.getDelayedpostingIndicator()));
        studyProtocolStageDTO.setDataMonitoringCommitteeAppointedIndicator(
                BlConverter.convertToBl(studyProtocolStage.getDataMonitoringCommitteeAppointedIndicator()));
        studyProtocolStageDTO.setOversightAuthorityCountryId(IiConverter.convertToIi(
                studyProtocolStage.getOversightAuthorityCountryId()));
        studyProtocolStageDTO.setOversightAuthorityOrgId(IiConverter.convertToIi(
                studyProtocolStage.getOversightAuthorityOrgId()));
        studyProtocolStageDTO.setProprietaryTrialIndicator(BlConverter.convertToBl(
                studyProtocolStage.getProprietaryTrialIndicator()));
        studyProtocolStageDTO.setNciDesignatedCancerCenterIndicator(BlConverter.convertToBl(
                studyProtocolStage.getNciDesignatedCancerCenterIndicator()));
        studyProtocolStageDTO.setUserLastCreated(StConverter.convertToSt(
                studyProtocolStage.getUserLastCreated()));
        studyProtocolStageDTO.setCtgovXmlRequiredIndicator(BlConverter.convertToBl(
                studyProtocolStage.getCtgovXmlRequiredIndicator()));
        studyProtocolStageDTO.setSubmitterOrganizationIdentifier(IiConverter.convertToIi(
                studyProtocolStage.getSubmitterOrganizationIdentifier()));
        studyProtocolStageDTO.setSiteProtocolIdentifier(IiConverter.convertToIi(
                studyProtocolStage.getSiteProtocolIdentifier()));
        studyProtocolStageDTO.setSitePiIdentifier(IiConverter.convertToIi(
                studyProtocolStage.getSitePiIdentifier()));
        studyProtocolStageDTO.setSiteTargetAccrual(IntConverter.convertToInt(
                studyProtocolStage.getSiteTargetAccrual()));
        studyProtocolStageDTO.setSiteSummaryFourOrgIdentifier(IiConverter.convertToIi(
                studyProtocolStage.getSiteSummaryFourOrgIdentifier()));
        studyProtocolStageDTO.setSiteSummaryFourFundingTypeCode(
                CdConverter.convertToCd(studyProtocolStage.getSiteSummaryFourFundingTypeCode()));
        studyProtocolStageDTO.setSiteProgramCodeText(StConverter.convertToSt(
                studyProtocolStage.getSiteProgramCodeText()));
        studyProtocolStageDTO.setSiteRecruitmentStatus(
                CdConverter.convertToCd(studyProtocolStage.getSiteRecruitmentStatus()));
        studyProtocolStageDTO.setSiteRecruitmentStatusDate(TsConverter.convertToTs(
                studyProtocolStage.getSiteRecruitmentStatusDate()));
        studyProtocolStageDTO.setOpendedForAccrualDate(TsConverter.convertToTs(
                studyProtocolStage.getOpendedForAccrualDate()));
        studyProtocolStageDTO.setClosedForAccrualDate(TsConverter.convertToTs(
                studyProtocolStage.getClosedForAccrualDate()));
        studyProtocolStageDTO.setPiInitiatedIndicator(BlConverter.convertToBl(
                studyProtocolStage.getPiInitiatedIndicator()));
        studyProtocolStageDTO.setSiteNciDesignatedCancerCenterIndicator(BlConverter.convertToBl(
                studyProtocolStage.getSiteNciDesignatedCancerCenterIndicator()));
        if (CollectionUtils.isNotEmpty(studyProtocolStage.getOtherIdentifiers())) {
            studyProtocolStageDTO.getSecondaryIdentifierList().addAll(studyProtocolStage.getOtherIdentifiers());
        }
        return studyProtocolStageDTO;
    }
    /**
     *
     * @param studyProtocolStageDTO isoDTO
     * @return domain
     */
    public static StudyProtocolStage convertFromDTOToDomain(StudyProtocolStageDTO studyProtocolStageDTO) {
        return convertFromDTOToDomain(studyProtocolStageDTO , new StudyProtocolStage());
    }
    /**
     *
     * @param studyProtocolStageDTO isoDTO
     * @param studyProtocolStage domain
     * @return domain
     */
    public static StudyProtocolStage convertFromDTOToDomain(
            StudyProtocolStageDTO studyProtocolStageDTO, StudyProtocolStage studyProtocolStage) {
        studyProtocolStage.setId(IiConverter.convertToLong(studyProtocolStageDTO.getIdentifier()));
        studyProtocolStage.setNctIdentifier(StConverter.convertToString(studyProtocolStageDTO.getNctIdentifier()));
        studyProtocolStage.setOfficialTitle(StConverter.convertToString(studyProtocolStageDTO.getOfficialTitle()));
        studyProtocolStage.setPhaseCode(PhaseCode.getByCode(CdConverter.convertCdToString(
                studyProtocolStageDTO.getPhaseCode())));
        studyProtocolStage.setPhaseOtherText(StConverter.convertToString(studyProtocolStageDTO.getPhaseOtherText()));
        studyProtocolStage.setPrimaryPurposeCode(PrimaryPurposeCode.getByCode(CdConverter.convertCdToString(
                studyProtocolStageDTO.getPrimaryPurposeCode())));
        studyProtocolStage.setPrimaryPurposeOtherText(StConverter.convertToString(
                studyProtocolStageDTO.getPrimaryPurposeOtherText()));
        studyProtocolStage.setLocalProtocolIdentifier(StConverter.convertToString(
                studyProtocolStageDTO.getLocalProtocolIdentifier()));
        studyProtocolStage.setLeadOrganizationIdentifier(IiConverter.convertToString(
                studyProtocolStageDTO.getLeadOrganizationIdentifier()));
        studyProtocolStage.setPiIdentifier(IiConverter.convertToString(studyProtocolStageDTO.getPiIdentifier()));
        studyProtocolStage.setSponsorIdentifier(IiConverter.convertToString(
                studyProtocolStageDTO.getSponsorIdentifier()));
        studyProtocolStage.setResponsiblePartyType(StConverter.convertToString(
                studyProtocolStageDTO.getResponsiblePartyType()));
        studyProtocolStage.setResponsibleIdentifier(IiConverter.convertToString(
                studyProtocolStageDTO.getResponsibleIdentifier()));
        studyProtocolStage.setContactEmail(StConverter.convertToString(studyProtocolStageDTO.getContactEmail()));
        studyProtocolStage.setContactPhone(StConverter.convertToString(studyProtocolStageDTO.getContactPhone()));
        studyProtocolStage.setSummaryFourOrgIdentifier(IiConverter.convertToString(
                studyProtocolStageDTO.getSummaryFourOrgIdentifier()));
        studyProtocolStage.setSummaryFourFundingCategoryCode(SummaryFourFundingCategoryCode.getByCode(
                CdConverter.convertCdToString(studyProtocolStageDTO.getSummaryFourFundingCategoryCode())));
        studyProtocolStage.setProgramCodeText(StConverter.convertToString(studyProtocolStageDTO.getProgramCodeText()));
        studyProtocolStage.setTrialStatusCode(StudyStatusCode.getByCode(CdConverter.convertCdToString(
                studyProtocolStageDTO.getTrialStatusCode())));
        studyProtocolStage.setTrialStatusDate(TsConverter.convertToTimestamp(
                studyProtocolStageDTO.getTrialStatusDate()));
        studyProtocolStage.setReason(StConverter.convertToString(studyProtocolStageDTO.getStatusReason()));
        studyProtocolStage.setPrimaryCompletionDate(TsConverter.convertToTimestamp(
                studyProtocolStageDTO.getPrimaryCompletionDate()));
        studyProtocolStage.setPrimaryCompletionDateTypeCode(ActualAnticipatedTypeCode.getByCode(
                CdConverter.convertCdToString(studyProtocolStageDTO.getPrimaryCompletionDateTypeCode())));
        studyProtocolStage.setStartDate(TsConverter.convertToTimestamp(studyProtocolStageDTO.getStartDate()));
        studyProtocolStage.setStartDateTypeCode(ActualAnticipatedTypeCode.getByCode(CdConverter.convertCdToString(
                studyProtocolStageDTO.getStartDateTypeCode())));
        studyProtocolStage.setTrialType(StConverter.convertToString(studyProtocolStageDTO.getTrialType()));
        studyProtocolStage.setFdaRegulatedIndicator(BlConverter.covertToBoolean(
                studyProtocolStageDTO.getFdaRegulatedIndicator()));
        studyProtocolStage.setSection801Indicator(BlConverter.covertToBoolean(
                studyProtocolStageDTO.getSection801Indicator()));
        studyProtocolStage.setDelayedpostingIndicator(BlConverter.covertToBoolean(
                studyProtocolStageDTO.getDelayedpostingIndicator()));
        studyProtocolStage.setDataMonitoringCommitteeAppointedIndicator(
                BlConverter.covertToBoolean(studyProtocolStageDTO.getDataMonitoringCommitteeAppointedIndicator()));
        studyProtocolStage.setOversightAuthorityCountryId(IiConverter.convertToString(
                studyProtocolStageDTO.getOversightAuthorityCountryId()));
        studyProtocolStage.setOversightAuthorityOrgId(IiConverter.convertToString(
                studyProtocolStageDTO.getOversightAuthorityOrgId()));
        studyProtocolStage.setProprietaryTrialIndicator(BlConverter.covertToBoolean(
                studyProtocolStageDTO.getProprietaryTrialIndicator()));
        studyProtocolStage.setNciDesignatedCancerCenterIndicator(BlConverter.covertToBool(
                studyProtocolStageDTO.getNciDesignatedCancerCenterIndicator()));
        studyProtocolStage.setUserLastCreated(StConverter.convertToString(
                studyProtocolStageDTO.getUserLastCreated()));
        studyProtocolStage.setCtgovXmlRequiredIndicator(BlConverter.covertToBoolean(
                studyProtocolStageDTO.getCtgovXmlRequiredIndicator()));
        studyProtocolStage.setSubmitterOrganizationIdentifier(IiConverter.convertToString(
                studyProtocolStageDTO.getSubmitterOrganizationIdentifier()));
        studyProtocolStage.setSiteProtocolIdentifier(IiConverter.convertToString(
                studyProtocolStageDTO.getSiteProtocolIdentifier()));
        studyProtocolStage.setSitePiIdentifier(IiConverter.convertToString(
                studyProtocolStageDTO.getSitePiIdentifier()));
        studyProtocolStage.setSiteTargetAccrual(IntConverter.convertToInteger(
                studyProtocolStageDTO.getSiteTargetAccrual()));
        studyProtocolStage.setSiteSummaryFourOrgIdentifier(IiConverter.convertToString(
                studyProtocolStageDTO.getSiteSummaryFourOrgIdentifier()));
        studyProtocolStage.setSiteSummaryFourFundingTypeCode(SummaryFourFundingCategoryCode.getByCode(
                CdConverter.convertCdToString(studyProtocolStageDTO.getSiteSummaryFourFundingTypeCode())));
        studyProtocolStage.setSiteProgramCodeText(StConverter.convertToString(
                studyProtocolStageDTO.getSiteProgramCodeText()));
        studyProtocolStage.setSiteRecruitmentStatus(RecruitmentStatusCode.getByCode(
                CdConverter.convertCdToString(studyProtocolStageDTO.getSiteRecruitmentStatus())));
        studyProtocolStage.setSiteRecruitmentStatusDate(TsConverter.convertToTimestamp(
                studyProtocolStageDTO.getSiteRecruitmentStatusDate()));
        studyProtocolStage.setOpendedForAccrualDate(TsConverter.convertToTimestamp(
                studyProtocolStageDTO.getOpendedForAccrualDate()));
        studyProtocolStage.setClosedForAccrualDate(TsConverter.convertToTimestamp(
                studyProtocolStageDTO.getClosedForAccrualDate()));
        studyProtocolStage.setPiInitiatedIndicator(BlConverter.covertToBoolean(
                studyProtocolStageDTO.getPiInitiatedIndicator()));
        studyProtocolStage.setSiteNciDesignatedCancerCenterIndicator(BlConverter.covertToBoolean(
                studyProtocolStageDTO.getSiteNciDesignatedCancerCenterIndicator()));
        if (CollectionUtils.isNotEmpty(studyProtocolStageDTO.getSecondaryIdentifierList())) {
            studyProtocolStage.getOtherIdentifiers().addAll(studyProtocolStageDTO.getSecondaryIdentifierList());
        }
        return studyProtocolStage;
    }
}
