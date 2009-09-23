/**
 * 
 */
package gov.nih.nci.registry.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.iso.dto.StudySiteContactDTO;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudySiteContactServiceRemote;

/**
 * @author Vrushali
 *
 */
public class MockStudySiteContactService extends MockAbstractRoleIsoService<StudySiteContactDTO> implements
        StudySiteContactServiceRemote {

    public List<StudySiteContactDTO> getByStudySite(Ii studySiteIi)
            throws PAException {
        // TODO Auto-generated method stub
        return null;
    }



}
