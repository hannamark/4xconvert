package gov.nih.nci.service;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.iso.dto.StudySiteAccrualStatusDTO;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudySiteAccrualStatusServiceRemote;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hreinhart
 *
 */
public class MockStudySiteAccrualStatusService implements
        StudySiteAccrualStatusServiceRemote {

    /**
     * @param dto
     * @return
     * @throws PAException
     */
    public StudySiteAccrualStatusDTO createStudySiteAccrualStatus(
            StudySiteAccrualStatusDTO dto) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @param studyParticipationIi
     * @return
     * @throws PAException
     */
    public List<StudySiteAccrualStatusDTO> getCurrentStudySiteAccrualStatusByStudyParticipation(
            Ii studyParticipationIi) throws PAException {
        List<StudySiteAccrualStatusDTO> result = new ArrayList<StudySiteAccrualStatusDTO>();
        return result;
    }

    /**
     * @param ii
     * @return
     * @throws PAException
     */
    public StudySiteAccrualStatusDTO getStudySiteAccrualStatus(Ii ii)
            throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @param studyParticipationIi
     * @return
     * @throws PAException
     */
    public List<StudySiteAccrualStatusDTO> getStudySiteAccrualStatusByStudyParticipation(
            Ii studyParticipationIi) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @param dto
     * @return
     * @throws PAException
     */
    public StudySiteAccrualStatusDTO updateStudySiteAccrualStatus(
            StudySiteAccrualStatusDTO dto) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

}
