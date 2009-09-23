package gov.nih.nci.service;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyPaService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MockAbstractStudyIsoService<DTO> 
    extends MockAbstractBaseIsoService<DTO> implements StudyPaService<DTO> {

    public Map<Ii, Ii> copy(Ii fromStudyProtocolIi, Ii toStudyProtocolIi)
            throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    public List<DTO> getByStudyProtocol(Ii ii) throws PAException {
        // TODO Auto-generated method stub
        return new ArrayList<DTO>();
    }
    
    
}
