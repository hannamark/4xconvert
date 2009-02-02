package gov.nih.nci.pa.iso.convert;

import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.domain.StudyResourcing;
import gov.nih.nci.pa.enums.MonitorCode;
import gov.nih.nci.pa.enums.SummaryFourFundingCategoryCode;
import gov.nih.nci.pa.iso.dto.StudyResourcingDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.IntConverter;
import gov.nih.nci.pa.iso.util.StConverter;

/**
 * Convert StudyResourcing from domain to DTO.
 *
 * @author Naveen Amiruddin
 * @since 09/09/2008
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
@SuppressWarnings({  "PMD.NPathComplexity" , "PMD.CyclomaticComplexity" })
public class StudyResourcingConverter {

    /**
     *
     * @param studyResourcing sr
     * @return InterventionalStudyProtocolDTO InterventionalStudyProtocolDTO
     */
    public static StudyResourcingDTO convertFromDomainToDTO(StudyResourcing studyResourcing) {
        StudyResourcingDTO srDTO = new StudyResourcingDTO();
        srDTO.setIdentifier(IiConverter.converToStudyResourcingIi(studyResourcing.getId()));
        srDTO.setOrganizationIdentifier(IiConverter.convertToIi(studyResourcing.getOrganizationIdentifier()));
        srDTO.setResourceProviderIdentifier(IiConverter.convertToIi(studyResourcing.getResourceProviderIdentifier()));
        srDTO.setSummary4ReportedResourceIndicator(
                BlConverter.convertToBl(studyResourcing.getSummary4ReportedResourceIndicator()));
        srDTO.setTypeCode(CdConverter.convertToCd(studyResourcing.getTypeCode()));
        srDTO.setFundingMechanismCode(CdConverter.convertStringToCd(studyResourcing.getFundingMechanismCode()));
        srDTO.setFundingTypeCode(CdConverter.convertStringToCd(studyResourcing.getFundingTypeCode()));
        srDTO.setNciDivisionProgramCode(CdConverter.convertToCd(studyResourcing.getNciDivisionProgramCode()));
        srDTO.setNihInstitutionCode(CdConverter.convertStringToCd(studyResourcing.getNihInstituteCode()));
        srDTO.setSuffixGrantYear(StConverter.convertToSt(studyResourcing.getSuffixGrantYear()));
        srDTO.setSuffixOther(StConverter.convertToSt(studyResourcing.getSuffixOther()));
        srDTO.setSerialNumber(IntConverter.convertToInt(studyResourcing.getSerialNumber()));
        srDTO.setStudyProtocolIi(IiConverter.converToStudyProtocolIi(studyResourcing.getStudyProtocol().getId()));
        //@tdo: date range
        return srDTO;
    }

    /**
     *
     * @param studyResourcingDTO studyResourcingDTO
     * @return StudyResourcing
     */
    public static StudyResourcing convertFromDTOToDomain(StudyResourcingDTO studyResourcingDTO) {
        StudyResourcing studyResourcing = new StudyResourcing();
        StudyProtocol spBo = new StudyProtocol();
        spBo.setId(IiConverter.convertToLong(studyResourcingDTO.getStudyProtocolIi()));
        if (studyResourcingDTO.getIdentifier() != null) {
            studyResourcing.setId(IiConverter.convertToLong(studyResourcingDTO.getIdentifier()));
        }
        studyResourcing.setStudyProtocol(spBo);
        if (studyResourcingDTO.getOrganizationIdentifier() != null) {
            studyResourcing.setOrganizationIdentifier(studyResourcingDTO.getOrganizationIdentifier().getExtension());
        }
        if (studyResourcingDTO.getResourceProviderIdentifier() != null) {
            studyResourcing.setResourceProviderIdentifier(
                    studyResourcingDTO.getResourceProviderIdentifier().getExtension());
        }
        if (studyResourcingDTO.getSummary4ReportedResourceIndicator() != null) {
            studyResourcing.setSummary4ReportedResourceIndicator(
                    studyResourcingDTO.getSummary4ReportedResourceIndicator().getValue());
        }
        if (studyResourcingDTO.getTypeCode() != null) {
            studyResourcing.setTypeCode(SummaryFourFundingCategoryCode.getByCode(
                    studyResourcingDTO.getTypeCode().getCode()));
        }
        if (studyResourcingDTO.getFundingMechanismCode() != null) {
            studyResourcing.setFundingMechanismCode(CdConverter.convertCdToString(
                    studyResourcingDTO.getFundingMechanismCode()));
        }
        if (studyResourcingDTO.getNciDivisionProgramCode() != null) {
            studyResourcing.setNciDivisionProgramCode(
                    MonitorCode.getByCode(studyResourcingDTO.getNciDivisionProgramCode().getCode()));
        }
        if (studyResourcingDTO.getNihInstitutionCode() != null) {
            studyResourcing.setNihInstituteCode(studyResourcingDTO.getNihInstitutionCode().getCode());
        }
        if (studyResourcingDTO.getFundingTypeCode() != null) {
            studyResourcing.setFundingTypeCode(studyResourcingDTO.getFundingTypeCode().getCode());
        }
        if (studyResourcingDTO.getSuffixOther() != null) {
            studyResourcing.setSuffixOther(studyResourcingDTO.getSuffixOther().getValue());
        }
        if (studyResourcingDTO.getSuffixGrantYear() != null) {
            studyResourcing.setSuffixGrantYear(StConverter.convertToString(studyResourcingDTO.getSuffixGrantYear()));
        }
        if (studyResourcingDTO.getSerialNumber() != null) {
            studyResourcing.setSerialNumber(studyResourcingDTO.getSerialNumber().getValue());
        }
        return studyResourcing;
    }

}
