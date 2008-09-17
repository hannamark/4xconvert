package gov.nih.nci.pa.iso.convert;

import gov.nih.nci.pa.domain.StudyResourcing;
import gov.nih.nci.pa.enums.MonitorCode;
import gov.nih.nci.pa.enums.InstitutionCode;
import gov.nih.nci.pa.enums.SummaryFourFundingCategoryCode;
import gov.nih.nci.pa.iso.dto.StudyResourcingDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;

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
        srDTO.setIi(IiConverter.convertToIi(studyResourcing.getId()));
        srDTO.setOrganizationIdentifier(IiConverter.convertToIi(studyResourcing.getOrganizationIdentifier()));
        srDTO.setResourceProviderIdentifier(IiConverter.convertToIi(studyResourcing.getResourceProviderIdentifier()));
        srDTO.setSummary4ReportedResourceIndicator(
                BlConverter.convertToBl(studyResourcing.getSummary4ReportedResourceIndicator()));
        srDTO.setTypeCode(CdConverter.convertToCd(studyResourcing.getTypeCode()));
        srDTO.setUserLastUpdated(StConverter.convertToSt(studyResourcing.getUserLastUpdated()));
        srDTO.setFundingMechanismCode(CdConverter.convertStringToCd(studyResourcing.getFundingMechanismCode()));
        srDTO.setFundingTypeCode(studyResourcing.getFundingTypeCode());
        srDTO.setMonitorCode(CdConverter.convertToCd(studyResourcing.getMonitorCode()));
        srDTO.setInstitutionCode(CdConverter.convertToCd(studyResourcing.getInstitutionCode()));
        srDTO.setSuffixgrantYear(TsConverter.convertToTs(studyResourcing.getSuffixgrantYear()));
        srDTO.setSuffixOther(StConverter.convertToSt(studyResourcing.getSuffixOther()));
        srDTO.setId(studyResourcing.getId());
        srDTO.setSerialNumber(StConverter.convertToSt(studyResourcing.getSerialNumber()));
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
        if (studyResourcingDTO.getIi() != null) {
            studyResourcing.setId(Long.valueOf(studyResourcingDTO.getIi().getExtension()));
        }
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
        if (studyResourcingDTO.getUserLastUpdated() != null) {
            studyResourcing.setUserLastUpdated(studyResourcingDTO.getUserLastUpdated().getValue());
        }
        if (studyResourcingDTO.getFundingMechanismCode() != null) {
            studyResourcing.setFundingMechanismCode(CdConverter.convertCdToString(
                    studyResourcingDTO.getFundingMechanismCode()));
        }
        if (studyResourcingDTO.getMonitorCode() != null) {
            studyResourcing.setMonitorCode(MonitorCode.getByCode(studyResourcingDTO.getMonitorCode().getCode()));
        }
        if (studyResourcingDTO.getInstitutionCode() != null) {
            studyResourcing.setInstitutionCode(InstitutionCode.getByCode(
                    studyResourcingDTO.getInstitutionCode().getCode()));
        }
        if (studyResourcingDTO.getFundingTypeCode() != null) {
            studyResourcing.setFundingTypeCode(studyResourcingDTO.getFundingTypeCode());
        }
        if (studyResourcingDTO.getSuffixOther() != null) {
            studyResourcing.setSuffixOther(studyResourcingDTO.getSuffixOther().getValue());
        }
        if (studyResourcingDTO.getSuffixgrantYear() != null) {
            studyResourcing.setSuffixgrantYear(TsConverter.convertToTimestamp(studyResourcingDTO.getSuffixgrantYear()));
        }
        if (studyResourcingDTO.getSerialNumber() != null) {
            studyResourcing.setSerialNumber(studyResourcingDTO.getSerialNumber().getValue());
        }
        return studyResourcing;
    }

}
