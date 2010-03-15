package gov.nih.nci.pa.service.util;

import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.StudyProtocolStage;
import gov.nih.nci.pa.service.PAException;

import java.util.List;

import javax.ejb.Local;

/**
 * 
 * @author Vrushali
 *
 */
@Local
public interface StudyProtocolStageServiceLocal {
    /**
     * 
     * @param ii primary id of StudyProtocol
     * @return StudyProtocolDTO
     * @throws PAException PAException
     */
    StudyProtocolStage get(Ii ii) throws PAException;
    /**
     * for creating a new ISP.
     * @param studyProtocolStage for studyProtocolStage
     * @return ii ii
     * @throws PAException exception
     */
    Ii create(StudyProtocolStage studyProtocolStage) throws PAException;
    /**
     * 
     * @param dto criteria
     * @param pagingParams p
     * @return list of dto
     * @throws PAException on err
     * @throws TooManyResultsException on err
     */
    List<StudyProtocolStage> search(StudyProtocolStage dto, LimitOffset pagingParams) throws PAException,
    TooManyResultsException; 
    /**
     * 
     * @param isoDTO dto
     * @return dto
     * @throws PAException e
     */
    StudyProtocolStage update(StudyProtocolStage isoDTO) throws PAException; 
    /**
     * 
     * @param ii ii to delete
     * @return 
     * @throws PAException e
     */
    void delete(Ii ii) throws PAException;

}
