/**
 * 
 */
package gov.nih.nci.service;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.domain.StudyOverallStatus;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.iso.convert.StudyOverallStatusConverter;
import gov.nih.nci.pa.iso.dto.StudyOverallStatusDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyOverallStatusServiceRemote;
import gov.nih.nci.pa.util.PAUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hreinhart
 *
 */
public class MockStudyOverallStatusService implements StudyOverallStatusServiceRemote {

    ArrayList<StudyOverallStatus> sosList;
    
    /**
     * 
     */
    public MockStudyOverallStatusService() {
        super();
        sosList = new ArrayList<StudyOverallStatus>();
        StudyProtocol sp = new StudyProtocol();
        sp.setId(1L);
        
        StudyOverallStatus sos = new StudyOverallStatus();
        sos.setId(1L);
        sos.setStatusCode(StudyStatusCode.APPROVED);
        sos.setStatusDate(PAUtil.dateStringToTimestamp("1/1/2008"));
        sos.setStudyProtocol(sp);
        sosList.add(sos);

        sos = new StudyOverallStatus();
        sos.setId(2L);
        sos.setStatusCode(StudyStatusCode.TEMPORARILY_CLOSED_TO_ACCRUAL);
        sos.setStatusDate(PAUtil.dateStringToTimestamp("2/1/2008"));
        sos.setStudyProtocol(sp);
        sosList.add(sos);
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.StudyOverallStatusService#getCurrentStudyOverallStatusByStudyProtocol(gov.nih.nci.coppa.iso.Ii)
     */
    public List<StudyOverallStatusDTO> getCurrentByStudyProtocol(
            Ii studyProtocolId) throws PAException {
        
        List<StudyOverallStatusDTO> dtoList = getByStudyProtocol(studyProtocolId);
        List<StudyOverallStatusDTO> returnList = new ArrayList<StudyOverallStatusDTO>();
        if(dtoList.size() > 0) {
            returnList.add(dtoList.get(dtoList.size() - 1));
        }
        return returnList;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.StudyOverallStatusService#getStudyOverallStatusByStudyProtocol(gov.nih.nci.coppa.iso.Ii)
     */
    public List<StudyOverallStatusDTO> getByStudyProtocol(
            Ii studyProtocolId) throws PAException {
        ArrayList<StudyOverallStatusDTO> result = new ArrayList<StudyOverallStatusDTO>();
        for (StudyOverallStatus sos : sosList) {
            if(sos.getStudyProtocol().getId().equals(IiConverter.convertToLong(studyProtocolId))) {
                result.add(StudyOverallStatusConverter.convertFromDomainToDTO(sos));
            }
        }
        return result;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.StudyOverallStatusService#updateStudyOverallStatus(gov.nih.nci.pa.iso.dto.StudyOverallStatusDTO)
     */
    public StudyOverallStatusDTO create(
            StudyOverallStatusDTO studyOverallStatusDTO) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.StudyOverallStatusServiceRemote#getStudyOverallStatus(gov.nih.nci.coppa.iso.Ii)
     */
    public StudyOverallStatusDTO get(Ii ii)
            throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.StudyOverallStatusServiceRemote#updateStudyOverallStatus(gov.nih.nci.pa.iso.dto.StudyOverallStatusDTO)
     */
    public StudyOverallStatusDTO update(
            StudyOverallStatusDTO dto) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.BasePaService#delete(gov.nih.nci.coppa.iso.Ii)
     */
    public void delete(Ii ii) throws PAException {
        // TODO Auto-generated method stub
        
    }

}
