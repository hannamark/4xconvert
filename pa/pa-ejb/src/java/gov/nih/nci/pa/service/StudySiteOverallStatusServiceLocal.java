package gov.nih.nci.pa.service;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.iso.dto.StudySiteOverallStatusDTO;

import java.util.List;

import javax.ejb.Local;

/**
 * @author Vrushali
 *
 */
@Local
public interface StudySiteOverallStatusServiceLocal {
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
    /**
     * 
     * @param studySiteIdentifier ii
     * @return dto
     * @throws PAException e
     */
    StudySiteOverallStatusDTO getCurrentByStudySite(Ii studySiteIdentifier) throws PAException;
    /**
     * 
     * @param dto dto to validate 
     * @throws PAException e
     */
    void validate(StudySiteOverallStatusDTO dto) throws PAException;
}
