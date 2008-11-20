package gov.nih.nci.pa.iso.convert;

import gov.nih.nci.pa.domain.RegulatoryAuthority;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.domain.StudyRegulatoryAuthority;
import gov.nih.nci.pa.iso.dto.StudyRegulatoryAuthorityDTO;
import gov.nih.nci.pa.iso.util.IiConverter;

/**
 * Convert StudyRegulatoryInformationConverter domain to DTO.
 *
 * @author Harsha
 * @since 09/11/2008
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
public class StudyRegulatoryAuthorityConverter {
    
    /**
     * 
     * @param sra as a parameter
     * @return StudyProtocolDTO is returned as DTO
     */
    public static StudyRegulatoryAuthorityDTO convertFromDomainToDTO(StudyRegulatoryAuthority sra) {
        StudyRegulatoryAuthorityDTO sriDTO = new StudyRegulatoryAuthorityDTO();
        sriDTO.setProtocolId(IiConverter.convertToIi(sra.getStudyProtocol().getId()));
        sriDTO.setRegulatoryAuthorityId(IiConverter.convertToIi(sra.getRegulatoryAuthority().getId()));
        sriDTO.setIdentifier(IiConverter.convertToIi(sra.getId()));
        return sriDTO;
    }
    
    /**
     * 
     * @param dto to be converted
     * @return StudyRegulatoryAuthority as domain object 
     */
    public static StudyRegulatoryAuthority convertFromDTOToDomain(StudyRegulatoryAuthorityDTO dto) {
        StudyRegulatoryAuthority authority = new StudyRegulatoryAuthority();
        StudyProtocol  prot = new StudyProtocol();
        prot.setId(IiConverter.convertToLong(dto.getProtocolId()));
        RegulatoryAuthority ra = new RegulatoryAuthority();
        ra.setId(IiConverter.convertToLong(dto.getRegulatoryAuthorityId()));
        authority.setRegulatoryAuthority(ra);
        authority.setStudyProtocol(prot);
        return authority;
    }

}
