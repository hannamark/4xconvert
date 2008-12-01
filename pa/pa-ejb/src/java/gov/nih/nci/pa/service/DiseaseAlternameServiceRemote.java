package gov.nih.nci.pa.service;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.iso.dto.DiseaseAlternameDTO;

import java.util.List;

import javax.ejb.Remote;

/**
* @author Hugh Reinhart
* @since 11/30/2008
* copyright NCI 2008.  All rights reserved.
* This code may not be used without the express written permission of the
* copyright holder, NCI.
*/
@Remote
public interface DiseaseAlternameServiceRemote extends BasePaService<DiseaseAlternameDTO> {
    /**
     * @param ii index of the disease
     * @return list of alternate names for disease
     * @throws PAException exception
     */
     List<DiseaseAlternameDTO> getByDisease(Ii ii) throws PAException;
}
