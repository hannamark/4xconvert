package gov.nih.nci.pa.iso.convert;

import gov.nih.nci.pa.domain.StudyProtocolStage;
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
public class StudyProtocolStageConverter extends AbstractConverter<StudyProtocolStageDTO, StudyProtocolStage> {

    /**
     * {@inheritDoc}
     */
    @Override
    public StudyProtocolStageDTO convertFromDomainToDto(StudyProtocolStage studyProtocolStage) {
        StudyProtocolStageDTO studyProtocolStageDTO = new StudyProtocolStageDTO();
        AbstractStudyProtocolConverter.convertFromDomainToDTO(studyProtocolStage, studyProtocolStageDTO);
        studyProtocolStageDTO.setIdentifier(IiConverter.convertToIi(studyProtocolStage.getId()));
        studyProtocolStageDTO.setNctIdentifier(StConverter.convertToSt(studyProtocolStage.getNctIdentifier()));
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
        studyProtocolStageDTO.setTrialStatusCode(CdConverter.convertToCd(studyProtocolStage.getTrialStatusCode()));
        studyProtocolStageDTO.setTrialStatusDate(TsConverter.convertToTs(studyProtocolStage.getTrialStatusDate()));
        studyProtocolStageDTO.setStatusReason(StConverter.convertToSt(studyProtocolStage.getReason()));
        studyProtocolStageDTO.setTrialType(StConverter.convertToSt(studyProtocolStage.getTrialType()));
        studyProtocolStageDTO.setOversightAuthorityCountryId(IiConverter.convertToIi(
                studyProtocolStage.getOversightAuthorityCountryId()));
        studyProtocolStageDTO.setOversightAuthorityOrgId(IiConverter.convertToIi(
                studyProtocolStage.getOversightAuthorityOrgId()));
        studyProtocolStageDTO.setNciDesignatedCancerCenterIndicator(BlConverter.convertToBl(
                studyProtocolStage.getNciDesignatedCancerCenterIndicator()));
        studyProtocolStageDTO.setSubmitterOrganizationIdentifier(IiConverter.convertToIi(studyProtocolStage
                .getSubmitterOrganizationIdentifier()));
        setSiteFields(studyProtocolStage, studyProtocolStageDTO);
        studyProtocolStageDTO.setOpendedForAccrualDate(TsConverter.convertToTs(
                studyProtocolStage.getOpendedForAccrualDate()));
        studyProtocolStageDTO.setClosedForAccrualDate(TsConverter.convertToTs(
                studyProtocolStage.getClosedForAccrualDate()));
        studyProtocolStageDTO.setPiInitiatedIndicator(BlConverter.convertToBl(
                studyProtocolStage.getPiInitiatedIndicator()));
        studyProtocolStageDTO.setSiteNciDesignatedCancerCenterIndicator(BlConverter.convertToBl(
                studyProtocolStage.getSiteNciDesignatedCancerCenterIndicator()));
        setSecondaryIdentifiers(studyProtocolStage, studyProtocolStageDTO);
        return studyProtocolStageDTO;
    }

    private static void setSiteFields(StudyProtocolStage studyProtocolStage,
            StudyProtocolStageDTO studyProtocolStageDTO) {
        studyProtocolStageDTO.setSiteProtocolIdentifier(IiConverter.convertToIi(studyProtocolStage
                .getSiteProtocolIdentifier()));
        studyProtocolStageDTO.setSitePiIdentifier(IiConverter.convertToIi(studyProtocolStage.getSitePiIdentifier()));
        studyProtocolStageDTO
                .setSiteTargetAccrual(IntConverter.convertToInt(studyProtocolStage.getSiteTargetAccrual()));
        studyProtocolStageDTO.setSiteSummaryFourOrgIdentifier(IiConverter.convertToIi(studyProtocolStage
                .getSiteSummaryFourOrgIdentifier()));
        studyProtocolStageDTO.setSiteSummaryFourFundingTypeCode(CdConverter.convertToCd(studyProtocolStage
                .getSiteSummaryFourFundingTypeCode()));
        studyProtocolStageDTO.setSiteProgramCodeText(StConverter.convertToSt(studyProtocolStage
                .getSiteProgramCodeText()));
        studyProtocolStageDTO.setSiteRecruitmentStatus(CdConverter.convertToCd(studyProtocolStage
                .getSiteRecruitmentStatus()));
        studyProtocolStageDTO.setSiteRecruitmentStatusDate(TsConverter.convertToTs(studyProtocolStage
                .getSiteRecruitmentStatusDate()));
    }

    private static void setSecondaryIdentifiers(StudyProtocolStage studyProtocolStage,
            StudyProtocolStageDTO studyProtocolStageDTO) {
        // TODO - as part of PO-2434 this should be moved to the AbstractStudyProtocolConverter
        // once the AbstractStudyProtocolDTO owns the SecondaryIdentifiers.
        if (CollectionUtils.isNotEmpty(studyProtocolStage.getOtherIdentifiers())) {
            studyProtocolStageDTO.getSecondaryIdentifierList().addAll(studyProtocolStage.getOtherIdentifiers());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudyProtocolStage convertFromDtoToDomain(StudyProtocolStageDTO studyProtocolStageDTO) {
        StudyProtocolStage studyProtocolStage = new StudyProtocolStage();
        AbstractStudyProtocolConverter.convertFromDTOToDomain(studyProtocolStageDTO, studyProtocolStage);
        studyProtocolStage.setId(IiConverter.convertToLong(studyProtocolStageDTO.getIdentifier()));
        studyProtocolStage.setNctIdentifier(StConverter.convertToString(studyProtocolStageDTO.getNctIdentifier()));
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
        studyProtocolStage.setTrialStatusCode(StudyStatusCode.getByCode(CdConverter.convertCdToString(
                studyProtocolStageDTO.getTrialStatusCode())));
        studyProtocolStage.setTrialStatusDate(TsConverter.convertToTimestamp(
                studyProtocolStageDTO.getTrialStatusDate()));
        studyProtocolStage.setReason(StConverter.convertToString(studyProtocolStageDTO.getStatusReason()));
        studyProtocolStage.setTrialType(StConverter.convertToString(studyProtocolStageDTO.getTrialType()));
        studyProtocolStage.setOversightAuthorityCountryId(IiConverter.convertToString(
                studyProtocolStageDTO.getOversightAuthorityCountryId()));
        studyProtocolStage.setOversightAuthorityOrgId(IiConverter.convertToString(
                studyProtocolStageDTO.getOversightAuthorityOrgId()));
        studyProtocolStage.setNciDesignatedCancerCenterIndicator(BlConverter.convertToBool(
                studyProtocolStageDTO.getNciDesignatedCancerCenterIndicator()));

        studyProtocolStage.setSubmitterOrganizationIdentifier(IiConverter.convertToString(
                studyProtocolStageDTO.getSubmitterOrganizationIdentifier()));
        setSiteFields(studyProtocolStageDTO, studyProtocolStage);
        studyProtocolStage.setOpendedForAccrualDate(TsConverter.convertToTimestamp(
                studyProtocolStageDTO.getOpendedForAccrualDate()));
        studyProtocolStage.setClosedForAccrualDate(TsConverter.convertToTimestamp(
                studyProtocolStageDTO.getClosedForAccrualDate()));
        studyProtocolStage.setPiInitiatedIndicator(BlConverter.convertToBoolean(
                studyProtocolStageDTO.getPiInitiatedIndicator()));
        studyProtocolStage.setSiteNciDesignatedCancerCenterIndicator(BlConverter.convertToBoolean(
                studyProtocolStageDTO.getSiteNciDesignatedCancerCenterIndicator()));
        setOtherIdentifiers(studyProtocolStageDTO, studyProtocolStage);
        return studyProtocolStage;
    }

    private static void setSiteFields(StudyProtocolStageDTO studyProtocolStageDTO,
            StudyProtocolStage studyProtocolStage) {
        studyProtocolStage.setSiteProtocolIdentifier(IiConverter.convertToString(studyProtocolStageDTO
                .getSiteProtocolIdentifier()));
        studyProtocolStage
                .setSitePiIdentifier(IiConverter.convertToString(studyProtocolStageDTO.getSitePiIdentifier()));
        studyProtocolStage.setSiteTargetAccrual(IntConverter.convertToInteger(studyProtocolStageDTO
                .getSiteTargetAccrual()));
        studyProtocolStage.setSiteSummaryFourOrgIdentifier(IiConverter.convertToString(studyProtocolStageDTO
                .getSiteSummaryFourOrgIdentifier()));
        studyProtocolStage.setSiteSummaryFourFundingTypeCode(SummaryFourFundingCategoryCode.getByCode(CdConverter
                .convertCdToString(studyProtocolStageDTO.getSiteSummaryFourFundingTypeCode())));
        studyProtocolStage.setSiteProgramCodeText(StConverter.convertToString(studyProtocolStageDTO
                .getSiteProgramCodeText()));
        studyProtocolStage.setSiteRecruitmentStatus(RecruitmentStatusCode.getByCode(CdConverter
                .convertCdToString(studyProtocolStageDTO.getSiteRecruitmentStatus())));
        studyProtocolStage.setSiteRecruitmentStatusDate(TsConverter.convertToTimestamp(studyProtocolStageDTO
                .getSiteRecruitmentStatusDate()));
    }

    private static void setOtherIdentifiers(StudyProtocolStageDTO studyProtocolStageDTO,
            StudyProtocolStage studyProtocolStage) {
        // TODO - as part of PO-2434 this should be moved to the AbstractStudyProtocolConverter
        // once the AbstractStudyProtocolDTO owns the SecondaryIdentifiers.
        if (CollectionUtils.isNotEmpty(studyProtocolStageDTO.getSecondaryIdentifierList())) {
            studyProtocolStage.getOtherIdentifiers().addAll(studyProtocolStageDTO.getSecondaryIdentifierList());
        }
    }
}
