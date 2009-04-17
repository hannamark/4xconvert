package gov.nih.nci.service;

import java.util.ArrayList;
import java.util.List;

import gov.nih.nci.pa.dto.PaPersonDTO;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.util.PAHealthCareProviderRemote;

/**
 * @author Harsha
 *
 */
public class MockPAHealthCareProviderService implements PAHealthCareProviderRemote {

    public PaPersonDTO getIdentifierBySPCId(Long id) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    public List<PaPersonDTO> getPersonsByStudyParticpationId(Long id, String roleCd) throws PAException {
        PaPersonDTO paPersonDTO = new PaPersonDTO();
        paPersonDTO.setFullName("John Investigator");
        List <PaPersonDTO>ary = new ArrayList<PaPersonDTO>();
        ary.add(paPersonDTO);
        return ary;
    }
}
