package gov.nih.nci.pa.service;

import gov.nih.nci.pa.domain.Arm;
import gov.nih.nci.pa.iso.convert.ArmConverter;
import gov.nih.nci.pa.iso.dto.ArmDTO;

import javax.ejb.Stateless;

/**
 * @author Hugh Reinhart
 * @since 11/05/2008
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
@Stateless
public class ArmServiceBean extends AbstractStudyIsoService<ArmDTO, Arm, ArmConverter> 
        implements ArmServiceRemote {
}
