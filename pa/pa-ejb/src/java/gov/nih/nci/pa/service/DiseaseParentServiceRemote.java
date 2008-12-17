package gov.nih.nci.pa.service;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.iso.dto.DiseaseParentDTO;

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
public interface DiseaseParentServiceRemote extends BasePaService<DiseaseParentDTO> {
    /**
     * @param ii index of disease
     * @return list of DiseaseParent associations for parents of disease
     * @throws PAException exception
     */
    List<DiseaseParentDTO> getByParentDisease(Ii ii) throws PAException;
    /**
     * @param ii index of disease
     * @return list of DiseaseParent associations for children of disease
     * @throws PAException exception
     */
    List<DiseaseParentDTO> getByChildDisease(Ii ii) throws PAException;
    /**
     * @param iis array of indexes of diseases
     * @return list of DiseaseParent associations for children of disease
     * @throws PAException exception
     */
    List<DiseaseParentDTO> getByChildDisease(Ii[] iis) throws PAException;
}
