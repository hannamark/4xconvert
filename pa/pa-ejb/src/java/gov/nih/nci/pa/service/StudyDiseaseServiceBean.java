package gov.nih.nci.pa.service;

import gov.nih.nci.pa.domain.StudyDisease;
import gov.nih.nci.pa.iso.convert.StudyDiseaseConverter;
import gov.nih.nci.pa.iso.dto.StudyDiseaseDTO;

import javax.ejb.Stateless;

/**
* @author Hugh Reinhart
* @since 11/30/2008
* copyright NCI 2008.  All rights reserved.
* This code may not be used without the express written permission of the
* copyright holder, NCI.
*/
@Stateless
public class StudyDiseaseServiceBean 
        extends AbstractStudyIsoService<StudyDiseaseDTO, StudyDisease, StudyDiseaseConverter>
        implements StudyDiseaseServiceRemote {
}
