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
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.PAUtil;

/**
 * @author Hugh Reinhart
 * @since 10/29/2008
 
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without the express written permission of the copyright holder, NCI.
 */
public class PlannedActivityConverter extends AbstractConverter<PlannedActivityDTO, PlannedActivity> {
    /**
     * 
     * @param bo StudyProtocol domain object
     * @return dto
     * @throws PAException PAException
     */
    @Override
    public PlannedActivityDTO convertFromDomainToDto(PlannedActivity bo) throws PAException {
        PlannedActivityDTO dto = new PlannedActivityDTO();
        dto.setCategoryCode(CdConverter.convertToCd(bo.getCategoryCode()));
        dto.setIdentifier(IiConverter.convertToIi(bo.getId()));
        if (bo.getIntervention() != null) {
            dto.setInterventionIdentifier(IiConverter.convertToIi(bo.getIntervention().getId()));
        }
        dto.setLeadProductIndicator(BlConverter.convertToBl(bo.getLeadProductIndicator()));
        if (bo.getStudyProtocol() != null) {
            dto.setStudyProtocolIdentifier(IiConverter.convertToIi(bo.getStudyProtocol().getId()));
        }
        dto.setSubcategoryCode(CdConverter.convertToCd(bo.getSubcategoryCode()));
        return dto;
    }

    /**
     * Create a new domain object from a given dto.
     * @param dto PlannedActivityDTO
     * @return StudyProtocol StudyProtocol
     * @throws PAException PAException
     */
    @Override
    public PlannedActivity convertFromDtoToDomain(PlannedActivityDTO dto) throws PAException {
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
        bo.setCategoryCode(ActivityCategoryCode.getByCode(CdConverter.convertCdToString(dto.getCategoryCode())));
        bo.setId(IiConverter.convertToLong(dto.getIdentifier()));
        bo.setIntervention(invBo);
        bo.setLeadProductIndicator(BlConverter.covertToBoolean(dto.getLeadProductIndicator()));
        bo.setStudyProtocol(spBo);
        bo.setSubcategoryCode(ActivitySubcategoryCode.
                getByCode(CdConverter.convertCdToString(dto.getSubcategoryCode())));
        return bo;
    }

}
