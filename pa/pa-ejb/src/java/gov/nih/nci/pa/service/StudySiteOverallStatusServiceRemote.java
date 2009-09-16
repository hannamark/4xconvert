package gov.nih.nci.pa.service;

import java.util.List;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.iso.dto.StudySiteOverallStatusDTO;

import javax.ejb.Remote;
/**
 * 
 * @author Vrushali
 *
 */
@Remote
public interface StudySiteOverallStatusServiceRemote {
    /**
     * 
     * @param dto d
     * @return dto
     * @throws PAException e
     */
    StudySiteOverallStatusDTO create(StudySiteOverallStatusDTO dto) throws PAException;
    /**
     * 
     * @param studySiteIi ii
     * @return listOfDto
     * @throws PAException e
     */
    List<StudySiteOverallStatusDTO> getByStudySite(Ii studySiteIi) throws PAException;
}
