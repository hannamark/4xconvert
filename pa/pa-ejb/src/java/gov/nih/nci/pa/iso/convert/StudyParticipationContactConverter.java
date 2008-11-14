/**
 *
 */
package gov.nih.nci.pa.iso.convert;

import gov.nih.nci.coppa.iso.DSet;
import gov.nih.nci.coppa.iso.Tel;
import gov.nih.nci.pa.domain.ClinicalResearchStaff;
import gov.nih.nci.pa.domain.HealthCareProvider;
import gov.nih.nci.pa.domain.OrganizationalContact;
import gov.nih.nci.pa.domain.StudyParticipation;
import gov.nih.nci.pa.domain.StudyParticipationContact;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.enums.StatusCode;
import gov.nih.nci.pa.enums.StudyParticipationContactRoleCode;
import gov.nih.nci.pa.iso.dto.StudyParticipationContactDTO;
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
 * Convert StudyParticipationContact domain to DTO.
 *
 * @author Hugh Reinhart, Harsha
 * @since 09/23/2008
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
@SuppressWarnings("PMD.CyclomaticComplexity")
public class StudyParticipationContactConverter {
    /**
     *
     * @param bo StudyProtocol domain object
     * @return dto
     * @throws PAException PAException
     */
    public static StudyParticipationContactDTO convertFromDomainToDTO(
            StudyParticipationContact bo) throws PAException {
        if (bo == null) {
            throw new PAException("Tried to pass null as argument to converter "
                    + "StudyParticipationContactDTO.convertFromDomainToDTO().  ");
        }
        StudyParticipationContactDTO dto = new StudyParticipationContactDTO();
        dto.setIdentifier(IiConverter.convertToIi(bo.getId()));
//        dto.setPostalAddress(AddressConverterUtil.create(bo.getAddressLine(), bo.getDeliveryAddressLine(),
//                bo.getCity(), bo.getState(), bo.getPostalCode(),
//                (bo.getCountry() == null) ? "" : bo.getCountry().getAlpha3()));
        dto.setPrimaryIndicator(BlConverter.convertToBl(bo.getPrimaryIndicator()));
        dto.setRoleCode(CdConverter.convertToCd(bo.getRoleCode()));
        dto.setStatusCode(CdConverter.convertToCd(bo.getStatusCode()));
        dto.setStatusDateRangeLow(TsConverter.convertToTs(bo.getStatusDateRangeLow()));
        if (bo.getStudyParticipation() != null) {
            dto.setStudyParticipationIi(IiConverter.convertToIi(bo.getStudyParticipation().getId()));
        }
        if (bo.getStudyProtocol() != null) {
            dto.setStudyProtocolIi(IiConverter.convertToIi(bo.getStudyProtocol().getId()));
        }
        if (bo.getHealthCareProvider() != null) {
            dto.setHealthCareProviderIi(IiConverter.convertToIi(bo.getHealthCareProvider().getId()));
        }
        if (bo.getClinicalResearchStaff() != null) {
            dto.setClinicalResearchStaffIi(IiConverter.convertToIi(bo.getClinicalResearchStaff().getId()));
        }
        if (bo.getOrganizationalContact() != null) {
            dto.setOrganizationalContactIi(IiConverter.convertToIi(bo.getOrganizationalContact().getId()));
        }

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
     * Create a new domain object from a given dto.
     * @param dto StudyParticipationContactDTO
     * @return StudyProtocol StudyProtocol
     * @throws PAException PAException
     */
    public static StudyParticipationContact convertFromDtoToDomain(
            StudyParticipationContactDTO dto) throws PAException {
        
        StudyParticipationContact bo = new StudyParticipationContact();
        bo.setId(IiConverter.convertToLong(dto.getIdentifier()));
        bo.setPrimaryIndicator(BlConverter.covertToBoolean(dto.getPrimaryIndicator()));
        bo.setRoleCode(StudyParticipationContactRoleCode.getByCode(dto.getRoleCode().getCode()));
        bo.setStatusCode(StatusCode.getByCode(dto.getStatusCode().getCode()));
        bo.setStatusDateRangeLow(TsConverter.convertToTimestamp(dto.getStatusDateRangeLow()));
        
        
        List retList = null;
        if (dto.getTelecomAddresses() != null) {
            retList = DSetConverter.convertDSetToList(dto.getTelecomAddresses(), "EMAIL");
            bo.setEmail(retList.get(0).toString());
            retList = DSetConverter.convertDSetToList(dto.getTelecomAddresses(), "PHONE");
            bo.setPhone(retList.get(0).toString());
        }

        if (!PAUtil.isIiNull(dto.getClinicalResearchStaffIi())) {
            ClinicalResearchStaff crs = new ClinicalResearchStaff();
            crs.setId(IiConverter.convertToLong(dto.getClinicalResearchStaffIi()));
            bo.setClinicalResearchStaff(crs);
        }

        if (!PAUtil.isIiNull(dto.getHealthCareProviderIi())) {
            HealthCareProvider healthCareProvider = new HealthCareProvider();
            healthCareProvider.setId(IiConverter.convertToLong(dto.getHealthCareProviderIi()));
            bo.setHealthCareProvider(healthCareProvider);
        }
        
        if (!PAUtil.isIiNull(dto.getOrganizationalContactIi())) {
            OrganizationalContact oc = new OrganizationalContact();
            oc.setId(IiConverter.convertToLong(dto.getOrganizationalContactIi()));
            bo.setOrganizationalContact(oc);
        }
        
        StudyProtocol protocolBo = new StudyProtocol();
        protocolBo.setId(IiConverter.convertToLong(dto.getStudyProtocolIi()));
        bo.setStudyProtocol(protocolBo);
        
        StudyParticipation participationBo = new StudyParticipation();
        participationBo.setId(IiConverter.convertToLong(dto.getStudyParticipationIi()));
        bo.setStudyParticipation(participationBo);
        
        return bo;
    }

}
