/**
 * 
 */
package gov.nih.nci.registry.service;

import java.util.ArrayList;
import java.util.List;

import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.iso.dto.StudyParticipationContactDTO;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyParticipationContactServiceRemote;

/**
 * @author Vrushali
 *
 */
public class MockStudyParticipationContactService extends MockAbstractRoleIsoService implements
        StudyParticipationContactServiceRemote {

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.StudyParticipationContactService#getByStudyParticipation(gov.nih.nci.coppa.iso.Ii)
     */
    public List<StudyParticipationContactDTO> getByStudyParticipation(
            Ii studyParticipationIi) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.RolePaService#getByStudyProtocol(gov.nih.nci.coppa.iso.Ii, java.lang.Object)
     */
    public List<StudyParticipationContactDTO> getByStudyProtocol(
            Ii studyProtocolIi, StudyParticipationContactDTO dto)
            throws PAException {
        List<StudyParticipationContactDTO> returnList = new ArrayList<StudyParticipationContactDTO>();
        return returnList;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.RolePaService#getByStudyProtocol(gov.nih.nci.coppa.iso.Ii, java.util.List)
     */
    public List<StudyParticipationContactDTO> getByStudyProtocol(
            Ii studyProtocolIi, List<StudyParticipationContactDTO> dto)
            throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.StudyPaService#copy(gov.nih.nci.coppa.iso.Ii, gov.nih.nci.coppa.iso.Ii)
     */
    public void copy(Ii fromStudyProtocolIi, Ii toStudyProtocolIi)
            throws PAException {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.StudyPaService#getByStudyProtocol(gov.nih.nci.coppa.iso.Ii)
     */
    public List<StudyParticipationContactDTO> getByStudyProtocol(Ii ii)
            throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.StudyPaService#getCurrentByStudyProtocol(gov.nih.nci.coppa.iso.Ii)
     */
    public StudyParticipationContactDTO getCurrentByStudyProtocol(
            Ii studyProtocolIi) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.BasePaService#create(java.lang.Object)
     */
    public StudyParticipationContactDTO create(StudyParticipationContactDTO dto)
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
    public StudyParticipationContactDTO get(Ii ii) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.BasePaService#update(java.lang.Object)
     */
    public StudyParticipationContactDTO update(StudyParticipationContactDTO dto)
            throws PAException {
        // TODO Auto-generated method stub
        return null;
    }



}
