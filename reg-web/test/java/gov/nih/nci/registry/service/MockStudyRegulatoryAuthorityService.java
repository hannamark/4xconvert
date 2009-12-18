/**
 * 
 */
package gov.nih.nci.registry.service;

import java.util.List;
import java.util.Map;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.domain.Country;
import gov.nih.nci.pa.domain.RegulatoryAuthority;
import gov.nih.nci.pa.dto.CountryRegAuthorityDTO;
import gov.nih.nci.pa.dto.RegulatoryAuthOrgDTO;
import gov.nih.nci.pa.iso.dto.StudyRegulatoryAuthorityDTO;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyRegulatoryAuthorityServiceLocal;
import gov.nih.nci.pa.service.util.RegulatoryInformationServiceRemote;

/**
 * @author Vrushali
 *
 */
public class MockStudyRegulatoryAuthorityService implements
        StudyRegulatoryAuthorityServiceLocal {

    public StudyRegulatoryAuthorityDTO update(StudyRegulatoryAuthorityDTO sraDTO)
            throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    public StudyRegulatoryAuthorityDTO getCurrentByStudyProtocol(
            Ii studyProtocolIi) throws PAException {
        if (studyProtocolIi != null && studyProtocolIi.getExtension().equalsIgnoreCase("3")) {
          return new StudyRegulatoryAuthorityDTO();  
        } 
        return null;
    }

    public Map<Ii, Ii> copy(Ii fromStudyProtocolIi, Ii toStudyProtocolIi)
            throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    public List<StudyRegulatoryAuthorityDTO> getByStudyProtocol(Ii ii)
            throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    public StudyRegulatoryAuthorityDTO create(StudyRegulatoryAuthorityDTO dto)
            throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    public void delete(Ii ii) throws PAException {
        // TODO Auto-generated method stub
        
    }

    public StudyRegulatoryAuthorityDTO get(Ii ii) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    public void validate(StudyRegulatoryAuthorityDTO dto) throws PAException {
        // TODO Auto-generated method stub
        
    }

    }
