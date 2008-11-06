package gov.nih.nci.pa.service;

import gov.nih.nci.pa.iso.dto.ArmDTO;

import javax.ejb.Remote;

/**
 * @author Hugh Reinhart
 * @since 11/05/2008
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
@Remote
public interface ArmServiceRemote extends StudyPaService<ArmDTO> {

}
