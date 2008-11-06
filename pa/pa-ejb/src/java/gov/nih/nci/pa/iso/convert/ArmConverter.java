/**
 * 
 */
package gov.nih.nci.pa.iso.convert;

import gov.nih.nci.coppa.iso.DSet;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.domain.Arm;
import gov.nih.nci.pa.domain.PlannedActivity;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.iso.dto.ArmDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.PAUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Hugh Reinhart
 * @since 11/05/2008
 
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without the express written permission of the copyright holder, NCI.
 */
public class ArmConverter extends AbstractConverter<ArmDTO, Arm> {
    /**
     * 
     * @param bo StudyProtocol domain object
     * @return dto
     * @throws PAException PAException
     */
    @Override
    public ArmDTO convertFromDomainToDto(Arm bo) throws PAException {
        ArmDTO dto = new ArmDTO();
        dto.setDescriptionText(StConverter.convertToSt(bo.getDescriptionText()));
        dto.setIdentifier(IiConverter.convertToIi(bo.getId()));
        Set<Ii> intSet = new HashSet<Ii>();
        for (PlannedActivity pa : bo.getInterventions()) {
            intSet.add(IiConverter.convertToIi(pa.getId()));
        }
        DSet<Ii> interventions = new DSet<Ii>();
        interventions.setItem(intSet);
        dto.setInterventions(interventions);
        dto.setName(StConverter.convertToSt(bo.getName()));
        if (bo.getStudyProtocol() != null) {
            dto.setStudyProtocolIdentifier(IiConverter.convertToIi(bo.getStudyProtocol().getId()));
        }
        dto.setTypeCode(CdConverter.convertToCd(bo.getTypeCode()));
        return dto;
    }

    /**
     * Create a new domain object from a given dto.
     * @param dto ArmDTO
     * @return StudyProtocol StudyProtocol
     * @throws PAException PAException
     */
    @Override
    public Arm convertFromDtoToDomain(
            ArmDTO dto) throws PAException {
        StudyProtocol spBo = null;
        if (!PAUtil.isIiNull(dto.getStudyProtocolIdentifier())) {
            spBo = new StudyProtocol();
            spBo.setId(IiConverter.convertToLong(dto.getStudyProtocolIdentifier()));
        }        
        Arm bo = new Arm();
        bo.setDateLastCreated(null);
        bo.setDescriptionText(StConverter.convertToString(dto.getDescriptionText()));
        bo.setId(IiConverter.convertToLong(dto.getIdentifier()));
        Collection<PlannedActivity> interventions = new ArrayList<PlannedActivity>();
        Collection<Ii> paIiSet = new ArrayList<Ii>();
        if (dto.getInterventions() != null) {
            paIiSet = dto.getInterventions().getItem();
        }
        for (Ii paIi : paIiSet) {
            PlannedActivity pa = new PlannedActivity();
            pa.setId(IiConverter.convertToLong(paIi));
            interventions.add(pa);
        }
        bo.setInterventions(interventions);
        bo.setName(StConverter.convertToString(dto.getName()));
        bo.setStudyProtocol(spBo);
        return bo;
    }
}
