package gov.nih.nci.pa.iso.convert;

import gov.nih.nci.pa.domain.StudyResourcing;
import gov.nih.nci.pa.enums.SummaryFourFundingCategoryCode;
import gov.nih.nci.pa.iso.dto.StudyResourcingDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
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
        srDTO.setIi(IiConverter.convertToIi(studyResourcing.getId()));
        srDTO.setOrganizationIdentifier(IiConverter.convertToIi(studyResourcing.getOrganizationIdentifier()));
        srDTO.setResourceProviderIdentifier(IiConverter.convertToIi(studyResourcing.getResourceProviderIdentifier()));
        srDTO.setSummary4ReportedResourceIndicator(
                BlConverter.convertToBl(studyResourcing.getSummary4ReportedResourceIndicator()));
        srDTO.setTypeCode(CdConverter.convertToCd(studyResourcing.getTypeCode()));
        srDTO.setUserLastUpdated(StConverter.convertToSt(studyResourcing.getUserLastUpdated()));
        
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
        return studyResourcing;
    }

}
