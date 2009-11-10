/**
 * 
 */
package gov.nih.nci.registry.service;

import java.util.List;

import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.pa.iso.dto.StudyContactDTO;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyContactServiceLocal;
import gov.nih.nci.pa.service.StudyContactServiceRemote;

/**
 * @author Vrushali
 *
 */
public class MockStudyContactService extends MockAbstractRoleIsoService<StudyContactDTO> implements StudyContactServiceLocal {

    public List<StudyContactDTO> search(StudyContactDTO dto,
            LimitOffset pagingParams) throws PAException,
            TooManyResultsException {
        // TODO Auto-generated method stub
        return null;
    }




}
