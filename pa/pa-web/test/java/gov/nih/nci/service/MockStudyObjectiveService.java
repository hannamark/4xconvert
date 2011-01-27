/**
 *
 */
package gov.nih.nci.service;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.iso.dto.StudyObjectiveDTO;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyObjectiveServiceLocal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Vrushali
 *
 */
public class MockStudyObjectiveService extends MockAbstractBaseIsoService <StudyObjectiveDTO> implements StudyObjectiveServiceLocal {

    /**
     * {@inheritDoc}
     */
    public Map<Ii, Ii>  copy(Ii fromStudyProtocolIi, Ii toStudyProtocolIi)
            throws PAException {
        return null;

    }

    /**
     * {@inheritDoc}
     */
    public List<StudyObjectiveDTO> getByStudyProtocol(Ii ii) throws PAException {
        return new ArrayList<StudyObjectiveDTO>();
    }

    /**
     * {@inheritDoc}
     */
    public StudyObjectiveDTO getCurrentByStudyProtocol(Ii studyProtocolIi) throws PAException {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudyObjectiveDTO create(StudyObjectiveDTO dto) throws PAException {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Ii ii) throws PAException {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudyObjectiveDTO get(Ii ii) throws PAException {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudyObjectiveDTO update(StudyObjectiveDTO dto) throws PAException {
        return null;
    }

}
