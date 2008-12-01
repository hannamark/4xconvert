package gov.nih.nci.pa.service;

import gov.nih.nci.pa.domain.Disease;
import gov.nih.nci.pa.iso.convert.DiseaseConverter;
import gov.nih.nci.pa.iso.dto.DiseaseDTO;

import javax.ejb.Stateless;

/**
* @author Hugh Reinhart
* @since 11/30/2008
* copyright NCI 2008.  All rights reserved.
* This code may not be used without the express written permission of the
* copyright holder, NCI.
*/
@Stateless
public class DiseaseServiceBean 
        extends AbstractBaseIsoService<DiseaseDTO, Disease, DiseaseConverter> 
        implements DiseaseServiceRemote {
}
