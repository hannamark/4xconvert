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
public class MockStudySiteContactService extends MockAbstractRoleIsoService implements
        StudySiteContactServiceRemote {

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.StudySiteContactService#getByStudySite(gov.nih.nci.coppa.iso.Ii)
     */
    public List<StudySiteContactDTO> getByStudySite(
            Ii studySiteIi) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.RolePaService#getByStudyProtocol(gov.nih.nci.coppa.iso.Ii, java.lang.Object)
     */
    public List<StudySiteContactDTO> getByStudyProtocol(
            Ii studyProtocolIi, StudySiteContactDTO dto)
            throws PAException {
        List<StudySiteContactDTO> returnList = new ArrayList<StudySiteContactDTO>();
        return returnList;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.RolePaService#getByStudyProtocol(gov.nih.nci.coppa.iso.Ii, java.util.List)
     */
    public List<StudySiteContactDTO> getByStudyProtocol(
            Ii studyProtocolIi, List<StudySiteContactDTO> dto)
            throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.StudyPaService#copy(gov.nih.nci.coppa.iso.Ii, gov.nih.nci.coppa.iso.Ii)
     */
    public Map<Ii , Ii> copy(Ii fromStudyProtocolIi, Ii toStudyProtocolIi)
            throws PAException {
    	return null;
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.StudyPaService#getByStudyProtocol(gov.nih.nci.coppa.iso.Ii)
     */
    public List<StudySiteContactDTO> getByStudyProtocol(Ii ii)
            throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.StudyPaService#getCurrentByStudyProtocol(gov.nih.nci.coppa.iso.Ii)
     */
    public StudySiteContactDTO getCurrentByStudyProtocol(
            Ii studyProtocolIi) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.BasePaService#create(java.lang.Object)
     */
    public StudySiteContactDTO create(StudySiteContactDTO dto)
            throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.BasePaService#delete(gov.nih.nci.coppa.iso.Ii)
     */
    public void delete(Ii ii) throws PAException {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.BasePaService#get(gov.nih.nci.coppa.iso.Ii)
     */
    public StudySiteContactDTO get(Ii ii) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.BasePaService#update(java.lang.Object)
     */
    public StudySiteContactDTO update(StudySiteContactDTO dto)
            throws PAException {
        // TODO Auto-generated method stub
        return null;
    }



}
