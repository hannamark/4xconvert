package gov.nih.nci.pa.service;

import gov.nih.nci.pa.domain.Arm;
import gov.nih.nci.pa.iso.convert.ArmConverter;
import gov.nih.nci.pa.iso.dto.ArmDTO;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.util.PAUtil;

import javax.ejb.Stateless;

/**
 * @author Hugh Reinhart
 * @since 11/05/2008
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
@Stateless
@SuppressWarnings("PMD.CyclomaticComplexity")
public class ArmServiceBean extends AbstractStudyIsoService<ArmDTO, Arm, ArmConverter> 
        implements ArmServiceRemote {
    private void businessRules(ArmDTO dto)  throws PAException {
        if (dto == null) {
            return;
        }
        if (PAUtil.isEmpty(StConverter.convertToString(dto.getName()))) {
            serviceError("The arm/group label (name) must be set.  ");
        }
        if (PAUtil.isEmpty(StConverter.convertToString(dto.getDescriptionText()))) {
            serviceError("The arm/group description must be set.  ");
        }
    }

    /**
     * @param dto Arm transer object
     * @return created Arm
     * @throws PAException exception
     */
    @Override
    public ArmDTO create(ArmDTO dto) throws PAException {
        businessRules(dto);
        return super.create(dto);
    }

    /**
     * @param dto Arm transer object
     * @return updated Arm
     * @throws PAException exception
     */
    @Override
    public ArmDTO update(ArmDTO dto) throws PAException {
        businessRules(dto);
        return super.update(dto);
    }
}
