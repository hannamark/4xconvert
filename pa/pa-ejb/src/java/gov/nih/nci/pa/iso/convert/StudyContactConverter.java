/**
 * 
 */
package gov.nih.nci.pa.iso.convert;

import gov.nih.nci.pa.domain.HealthCareProvider;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.domain.StudyContact;
import gov.nih.nci.pa.enums.StudyContactRoleCode;
import gov.nih.nci.pa.iso.dto.StudyContactDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.PAUtil;

/**
 * Convert StudyParticipation domain to DTO.
 *
 * @author Bala Nair
 * @since 10/23/2008
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
public class StudyContactConverter {
    /**
     * 
     * @param bo StudyContact domain object
     * @return dto
     * @throws PAException PAException
     */
    public static StudyContactDTO convertFromDomainToDTO(
                                StudyContact bo) throws PAException {
        
        StudyContactDTO dto = new StudyContactDTO();
   
        dto.setPrimaryIndicator(BlConverter.convertToBl(
                    bo.getPrimaryIndicator()));
        
        if (bo.getHealthCareProvider() != null) {
            dto.setHealthCareProvider(IiConverter.convertToIi(bo.getHealthCareProvider().getId()));
        }
        dto.setRoleCode(CdConverter.convertToCd(bo.getStudyContactRoleCode()));
        dto.setIdentifier(IiConverter.convertToIi(bo.getId()));
        dto.setStatusCode(CdConverter.convertToCd(bo.getStatusCode()));
        dto.setStatusDateRangeLow(TsConverter.convertToTs(bo.getStatusDateRangeLow()));
        dto.setStudyProtocolIi(IiConverter.convertToIi(bo.getStudyProtocol().getId()));
        dto.setUserLastUpdated(StConverter.convertToSt(bo.getUserLastUpdated()));
        return dto;
    }

    /**
     * Create a new domain object from a given DTO.
     * @param dto StudyContactDTO
     * @return StudyContact StudyContact
     * @throws PAException PAException
     */
    public static StudyContact convertFromDtoToDomain(
            StudyContactDTO dto) throws PAException {
        StudyContact bo = new StudyContact();
        StudyProtocol spBo = new StudyProtocol();
        spBo.setId(IiConverter.convertToLong(dto.getStudyProtocolIi()));
        HealthCareProvider hfBo = null;
        if (!PAUtil.isIiNull(dto.getHealthCareProvider())) {
            hfBo = new HealthCareProvider();
            hfBo.setId(IiConverter.convertToLong(dto.getHealthCareProvider()));
            bo.setHealthCareProvider(hfBo);
        }
        bo.setStudyContactRoleCode(StudyContactRoleCode.getByCode(dto.getRoleCode().getCode()));
        bo.setStudyProtocol(spBo);
        return bo;
    }
}
