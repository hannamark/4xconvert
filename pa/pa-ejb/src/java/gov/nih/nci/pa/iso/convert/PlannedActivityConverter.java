/**
 * 
 */
package gov.nih.nci.pa.iso.convert;

import gov.nih.nci.pa.domain.Intervention;
import gov.nih.nci.pa.domain.PlannedActivity;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.enums.ActionCategoryCode;
import gov.nih.nci.pa.enums.ActionSubcategoryCode;
import gov.nih.nci.pa.iso.dto.PlannedActivityDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.PAException;

import java.util.Date;

/**
 * @author Hugh Reinhart
 * @since 10/29/2008
 
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without the express written permission of the copyright holder, NCI.
 */
public class PlannedActivityConverter {
    /**
     * 
     * @param bo StudyProtocol domain object
     * @return dto
     * @throws PAException PAException
     */
    public static PlannedActivityDTO convertFromDomainToDTO(
            PlannedActivity bo) throws PAException {
        PlannedActivityDTO dto = new PlannedActivityDTO();
        dto.setAlternateName(StConverter.convertToSt(bo.getAlternateName()));
        dto.setCategoryCode(CdConverter.convertToCd(bo.getCategoryCode()));
        dto.setDescriptionText(StConverter.convertToSt(bo.getDescriptionText()));
        dto.setIdentifier(IiConverter.convertToIi(bo.getId()));
        dto.setInterventionIdentifier(IiConverter.convertToIi(bo.getIntervention().getId()));
        dto.setLeadProductIndicator(BlConverter.convertToBl(bo.getLeadProductIndicator()));
        dto.setName(StConverter.convertToSt(bo.getName()));
        dto.setStudyProtocolIdentifier(IiConverter.convertToIi(bo.getStudyProtocol().getId()));
        dto.setSubcategoryCode(CdConverter.convertToCd(bo.getSubcategoryCode()));
        return dto;
    }

    /**
     * Create a new domain object from a given dto.
     * @param dto PlannedActivityDTO
     * @return StudyProtocol StudyProtocol
     * @throws PAException PAException
     */
    public static PlannedActivity convertFromDtoToDomain(
            PlannedActivityDTO dto) throws PAException {
        StudyProtocol spBo = new StudyProtocol();
        spBo.setId(IiConverter.convertToLong(dto.getStudyProtocolIdentifier()));

        Intervention invBo = new Intervention();
        invBo.setId(IiConverter.convertToLong(dto.getInterventionIdentifier()));
        
        PlannedActivity bo = new PlannedActivity();
        bo.setAlternateName(StConverter.convertToString(dto.getAlternateName()));
        bo.setCategoryCode(ActionCategoryCode.getByCode(CdConverter.convertCdToString(dto.getCategoryCode())));
        bo.setDateLastUpdated(new Date());
        bo.setDescriptionText(StConverter.convertToString(dto.getDescriptionText()));
        bo.setId(IiConverter.convertToLong(dto.getIdentifier()));
        bo.setIntervention(invBo);
        bo.setLeadProductIndicator(BlConverter.covertToBoolean(dto.getLeadProductIndicator()));
        bo.setName(StConverter.convertToString(dto.getName()));
        bo.setStudyProtocol(spBo);
        bo.setSubcategoryCode(ActionSubcategoryCode.getByCode(CdConverter.convertCdToString(dto.getSubcategoryCode())));
        return bo;
    }

}


















