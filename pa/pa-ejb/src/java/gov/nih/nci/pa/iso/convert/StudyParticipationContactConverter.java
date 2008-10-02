/**
 * 
 */
package gov.nih.nci.pa.iso.convert;

import gov.nih.nci.coppa.iso.DSet;
import gov.nih.nci.coppa.iso.St;
import gov.nih.nci.pa.domain.StudyParticipation;
import gov.nih.nci.pa.domain.StudyParticipationContact;
import gov.nih.nci.pa.domain.StudyParticipationContactTelecomAddress;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.enums.StatusCode;
import gov.nih.nci.pa.enums.StudyContactRoleCode;
import gov.nih.nci.pa.iso.dto.StudyParticipationContactDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.PAException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Convert StudyParticipationContact domain to DTO.
 *
 * @author Hugh Reinhart
 * @since 09/23/2008
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
public class StudyParticipationContactConverter {
    /**
     * 
     * @param bo StudyProtocol domain object
     * @return dto
     * @throws PAException PAException
     */
    public static StudyParticipationContactDTO convertFromDomainToDTO(
            StudyParticipationContact bo) throws PAException {
        StudyParticipationContactDTO dto = new StudyParticipationContactDTO();
        dto.setIi(IiConverter.convertToIi(bo.getId()));
//        dto.setPostalAddress(AddressConverterUtil.create(bo.getAddressLine(), bo.getDeliveryAddressLine(),
//                bo.getCity(), bo.getState(), bo.getPostalCode(), 
//                (bo.getCountry() == null) ? "" : bo.getCountry().getAlpha3()));
        dto.setPrimaryIndicator(BlConverter.convertToBl(bo.getPrimaryIndicator()));
        dto.setRoleCode(CdConverter.convertToCd(bo.getRoleCode()));
        dto.setStatusCode(CdConverter.convertToCd(bo.getStatusCode()));
        dto.setStatusDateRangeLow(TsConverter.convertToTs(bo.getStatusDateRangeLow()));
        dto.setStudyParticipationIi(IiConverter.convertToIi(bo.getStudyParticipation().getId()));
        dto.setStudyProtocolIi(IiConverter.convertToIi(bo.getStudyProtocol().getId()));

        Set<St> telSet = new HashSet<St>();
        List<StudyParticipationContactTelecomAddress> tas = bo.getTelecomAddresses();
        if (tas != null) {
            for (StudyParticipationContactTelecomAddress ta : tas) {
                telSet.add(StConverter.convertToSt(ta.getTelecomAddress()));
            }
            DSet<St> telDSet = new DSet<St>();
            telDSet.setItem(telSet);
            dto.setTelecomAddresses(telDSet);
        }
        
        dto.setUserLastUpdated(StConverter.convertToSt(bo.getUserLastUpdated()));
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
        StudyProtocol protocolBo = new StudyProtocol();
        protocolBo.setId(IiConverter.convertToLong(dto.getStudyProtocolIi()));
        StudyParticipation participationBo = new StudyParticipation();
        participationBo.setId(IiConverter.convertToLong(dto.getStudyParticipationIi()));

        StudyParticipationContact bo = new StudyParticipationContact();
//        bo.setAddressLine(addressLine)
//        bo.setCity(city)
//        bo.setCountry(country)
//        bo.setDeliveryAddressLine(deliveryAddressLine)
        bo.setId(IiConverter.convertToLong(dto.getIi()));
//        bo.setPostalCode(postalCode)
        bo.setPrimaryIndicator(BlConverter.covertToBoolean(dto.getPrimaryIndicator()));
        bo.setRoleCode(StudyContactRoleCode.getByCode(dto.getRoleCode().getCode()));
//        bo.setState(state)
        bo.setStatusCode(StatusCode.getByCode(dto.getStatusCode().getCode()));
        bo.setStatusDateRangeLow(TsConverter.convertToTimestamp(dto.getStatusDateRangeLow()));
        bo.setStudyParticipation(participationBo);
        bo.setStudyProtocol(protocolBo);
//        bo.setTelecomAddresses(telecomAddresses)
        bo.setUserLastUpdated(StConverter.convertToString(dto.getUserLastUpdated()));
        return bo;
    }

}
