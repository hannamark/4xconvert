package gov.nih.nci.pa.dtoconvert;

import gov.nih.nci.pa.util.IsoConverter;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.dto.StudyProtocolDTO;
import gov.nih.nci.pa.enums.AccrualReportingMethodCode;
import gov.nih.nci.pa.enums.MonitorCode;
import gov.nih.nci.pa.enums.SummaryFourFundingCategoryCode;

/**
 * Convert StudyProtocol domain to DTO.
 *
 * @author Naveen Amiruddin
 * @since 08/26/2008
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
public class StudyProtocolDTOConverter {

    /**
     * 
     * @param studyProtocol study Protocol
     * @return studyProtocolDTO
     */
    public static StudyProtocolDTO convertFromDomainToDTO(StudyProtocol studyProtocol) {
        StudyProtocolDTO studyProtocolDTO = new StudyProtocolDTO();
        studyProtocolDTO.setIi(IsoConverter.convertIdToIsoIi(studyProtocol.getId()));
        studyProtocolDTO.setAccrualReportingMethodCode(
                IsoConverter.convertEnumCodeToIsoCd(studyProtocol.getAccrualReportingMethodCode()));
        studyProtocolDTO.setMonitorCode(
                IsoConverter.convertEnumCodeToIsoCd(studyProtocol.getMonitorCode()));
        studyProtocolDTO.setAllocationCode(
                IsoConverter.convertEnumCodeToIsoCd(studyProtocol.getAllocationCode()));
        studyProtocolDTO.setControlConcurrencyTypeCode(
                IsoConverter.convertEnumCodeToIsoCd(studyProtocol.getControlConcurrencyTypeCode()));
        studyProtocolDTO.setControlTypeCode(
                IsoConverter.convertEnumCodeToIsoCd(studyProtocol.getControlTypeCode()));
        studyProtocolDTO.setSummaryFourFundingCategoryCode(
                IsoConverter.convertEnumCodeToIsoCd(studyProtocol.getSummaryFourFundingCategoryCode()));
        
        
        return studyProtocolDTO;
    }
    
    /**
     * 
     * @param studyProtocolDTO studyProtocolDTO 
     * @return StudyProtocol StudyProtocol
     */
    public static StudyProtocol convertFromDtoToDomain(StudyProtocolDTO studyProtocolDTO) {
        StudyProtocol studyProtocol = new StudyProtocol();
        studyProtocol.setId(IsoConverter.convertIitoLong(studyProtocolDTO.getIi()));
        
        studyProtocol.setAccrualReportingMethodCode(
                AccrualReportingMethodCode.getByCode(studyProtocolDTO.getAccrualReportingMethodCode().getCode()));
        
        studyProtocol.setMonitorCode(MonitorCode.getByCode(studyProtocolDTO.getMonitorCode().getCode()));
        studyProtocol.setSummaryFourFundingCategoryCode(SummaryFourFundingCategoryCode.
                getByCode(studyProtocolDTO.getSummaryFourFundingCategoryCode().getCode()));
        return studyProtocol;
    }
}
