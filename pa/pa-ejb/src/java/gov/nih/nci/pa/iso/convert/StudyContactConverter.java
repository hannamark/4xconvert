/**
 * 
 */
package gov.nih.nci.pa.iso.convert;

import gov.nih.nci.coppa.iso.DSet;
import gov.nih.nci.coppa.iso.Tel;
import gov.nih.nci.pa.domain.ClinicalResearchStaff;
import gov.nih.nci.pa.domain.HealthCareProvider;
import gov.nih.nci.pa.domain.StudyContact;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.enums.StatusCode;
import gov.nih.nci.pa.enums.StudyContactRoleCode;
import gov.nih.nci.pa.iso.dto.StudyContactDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.PAUtil;

import java.util.ArrayList;
import java.util.List;

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
            dto.setHealthCareProviderIi(IiConverter.convertToIi(bo.getHealthCareProvider().getId()));
        }
        if (bo.getClinicalResearchStaff() != null) {
            dto.setClinicalResearchStaffIi(IiConverter.convertToIi(bo.getClinicalResearchStaff().getId()));
        }
        dto.setRoleCode(CdConverter.convertToCd(bo.getRoleCode()));
        dto.setIdentifier(IiConverter.convertToIi(bo.getId()));
        dto.setStatusCode(CdConverter.convertToCd(bo.getStatusCode()));
        dto.setStatusDateRangeLow(TsConverter.convertToTs(bo.getStatusDateRangeLow()));
        dto.setStudyProtocolIi(IiConverter.convertToIi(bo.getStudyProtocol().getId()));
        // handle phone and email
        DSet<Tel> telAddresses = new DSet<Tel>();
        ArrayList<String> emailList = new ArrayList<String>();
        emailList.add(bo.getEmail());
        DSetConverter.convertListToDSet(emailList, "EMAIL", telAddresses);
        ArrayList<String> telList = new ArrayList<String>();
        telList.add(bo.getPhone());
        DSetConverter.convertListToDSet(telList, "PHONE", telAddresses);
        dto.setTelecomAddresses(telAddresses);        
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
        return convertFromDtoToDomain(dto , new StudyContact());
    }
    
    /**
     * Create a new domain object from a given DTO.
     * @param dto StudyContactDTO
     * @param bo StudyContact
     * @return StudyContact StudyContact
     * @throws PAException PAException
     */
    public static StudyContact convertFromDtoToDomain(
            StudyContactDTO dto , StudyContact bo) throws PAException {
        StudyProtocol spBo = new StudyProtocol();
        spBo.setId(IiConverter.convertToLong(dto.getStudyProtocolIi()));
        HealthCareProvider hfBo = null;
        ClinicalResearchStaff crs = null;
        if (!PAUtil.isIiNull(dto.getHealthCareProviderIi())) {
            hfBo = new HealthCareProvider();
            hfBo.setId(IiConverter.convertToLong(dto.getHealthCareProviderIi()));
            bo.setHealthCareProvider(hfBo);
        }
        if (!PAUtil.isIiNull(dto.getClinicalResearchStaffIi())) {
            crs = new ClinicalResearchStaff();
            crs.setId(IiConverter.convertToLong(dto.getClinicalResearchStaffIi()));
            bo.setClinicalResearchStaff(crs);
        }
        bo.setStatusCode(StatusCode.getByCode(dto.getStatusCode().getCode()));
        bo.setRoleCode(StudyContactRoleCode.getByCode(dto.getRoleCode().getCode()));
        bo.setStudyProtocol(spBo);
        List retList = null;
        if (dto.getTelecomAddresses() != null) {
            retList = DSetConverter.convertDSetToList(dto.getTelecomAddresses(), "EMAIL");
            bo.setEmail(retList.get(0).toString());
            retList = DSetConverter.convertDSetToList(dto.getTelecomAddresses(), "PHONE");
            bo.setPhone(retList.get(0).toString());
        }        
        return bo;
    }
    
}
