package gov.nih.nci.pa.service;

import gov.nih.nci.pa.iso.dto.DiseaseDTO;

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
public interface DiseaseServiceRemote extends BasePaService<DiseaseDTO> {
    /**
     * @param searchCriteria search string
     * @return all diseases with preferred names or alternate names matching search string
     * @throws PAException exception
     */
    List<DiseaseDTO> search(DiseaseDTO searchCriteria) throws PAException;
}
