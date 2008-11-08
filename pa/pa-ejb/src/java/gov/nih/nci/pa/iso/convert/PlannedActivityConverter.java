/**
 * 
 */
package gov.nih.nci.pa.iso.convert;

import gov.nih.nci.pa.domain.Intervention;
import gov.nih.nci.pa.domain.PlannedActivity;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.enums.ActivityCategoryCode;
import gov.nih.nci.pa.enums.ActivitySubcategoryCode;
import gov.nih.nci.pa.iso.dto.PlannedActivityDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.PAUtil;

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
        if (bo.getIntervention() != null) {
            dto.setInterventionIdentifier(IiConverter.convertToIi(bo.getIntervention().getId()));
        }
        dto.setLeadProductIndicator(BlConverter.convertToBl(bo.getLeadProductIndicator()));
        dto.setName(StConverter.convertToSt(bo.getName()));
        if (bo.getStudyProtocol() != null) {
            dto.setStudyProtocolIdentifier(IiConverter.convertToIi(bo.getStudyProtocol().getId()));
        }
        dto.setSubcategoryCode(CdConverter.convertToCd(bo.getSubcategoryCode()));
        dto.setUserLastUpdated(StConverter.convertToSt(bo.getUserLastUpdated()));
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
        StudyProtocol spBo = null;
        if (!PAUtil.isIiNull(dto.getStudyProtocolIdentifier())) {
            spBo = new StudyProtocol();
            spBo.setId(IiConverter.convertToLong(dto.getStudyProtocolIdentifier()));
        }

        Intervention invBo = null;
        if (!PAUtil.isIiNull(dto.getInterventionIdentifier())) {
            invBo = new Intervention();
            invBo.setId(IiConverter.convertToLong(dto.getInterventionIdentifier()));
        }
        
        PlannedActivity bo = new PlannedActivity();
        bo.setAlternateName(StConverter.convertToString(dto.getAlternateName()));
        bo.setCategoryCode(ActivityCategoryCode.getByCode(CdConverter.convertCdToString(dto.getCategoryCode())));
        bo.setDescriptionText(StConverter.convertToString(dto.getDescriptionText()));
        bo.setId(IiConverter.convertToLong(dto.getIdentifier()));
        bo.setIntervention(invBo);
        bo.setLeadProductIndicator(BlConverter.covertToBoolean(dto.getLeadProductIndicator()));
        bo.setName(StConverter.convertToString(dto.getName()));
        bo.setStudyProtocol(spBo);
        bo.setSubcategoryCode(ActivitySubcategoryCode.
                getByCode(CdConverter.convertCdToString(dto.getSubcategoryCode())));
        bo.setUserLastUpdated(StConverter.convertToString(dto.getUserLastUpdated()));
        return bo;
    }

}
