package gov.nih.nci.registry.service;

import java.util.ArrayList;
import java.util.List;

import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.iso.dto.StudySiteContactDTO;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.RolePaService;

public class MockAbstractRoleIsoService<DTO> extends MockAbstractStudyIsoService<DTO> implements RolePaService<DTO>{

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.AbstractRoleIsoService#cascadeRoleStatus(gov.nih.nci.coppa.iso.Ii)
     */
    public void cascadeRoleStatus(Ii ii, Cd roleStatusCode) throws PAException {
        // TODO Auto-generated method stub
        
    }

    public List<DTO> getByStudyProtocol(Ii studyProtocolIi, DTO dto)
            throws PAException {
        List<DTO> returnList = new ArrayList<DTO>();
        return returnList;
    }

    public List<DTO> getByStudyProtocol(Ii studyProtocolIi, List<DTO> dto)
            throws PAException {
        List<DTO> returnList = new ArrayList<DTO>();
        return returnList;
    }
}
