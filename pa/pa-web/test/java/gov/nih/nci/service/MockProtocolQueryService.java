/**
 * 
 */
package gov.nih.nci.service;

import java.util.ArrayList;
import java.util.List;

import gov.nih.nci.pa.dto.StudyProtocolQueryCriteria;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.util.ProtocolQueryServiceLocal;
import gov.nih.nci.pa.util.PAUtil;

/**
 * @author Vrushali
 *
 */
public class MockProtocolQueryService implements ProtocolQueryServiceLocal {
    static List<StudyProtocolQueryDTO> list;
    static {
        list = new ArrayList<StudyProtocolQueryDTO>();
        StudyProtocolQueryDTO spQueryDTO = new StudyProtocolQueryDTO();
        spQueryDTO.setStudyProtocolId(1L);
        spQueryDTO.setNciIdentifier("NCI-2009-00001");
        spQueryDTO.setLocalStudyProtocolIdentifier("localStudyProtocolIdentifier");
        spQueryDTO.setOfficialTitle("officialTitle");
        spQueryDTO.setStudyStatusCode(StudyStatusCode.ACTIVE);
        spQueryDTO.setStudyStatusDate(PAUtil.dateStringToTimestamp("4/15/2009"));
        list.add(spQueryDTO);
        spQueryDTO = new StudyProtocolQueryDTO();
        spQueryDTO.setStudyProtocolId(2L);
        spQueryDTO.setNciIdentifier("NCI-2009-00001");
        spQueryDTO.setLocalStudyProtocolIdentifier("DupTestinglocalStudyProtocolId");
        spQueryDTO.setOfficialTitle("officialTitle");
        spQueryDTO.setStudyStatusCode(StudyStatusCode.ACTIVE);
        spQueryDTO.setStudyStatusDate(PAUtil.dateStringToTimestamp("4/15/2009"));
        list.add(spQueryDTO);
        
    }
    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.util.ProtocolQueryServiceLocal#getStudyProtocolByCriteria(gov.nih.nci.pa.dto.StudyProtocolQueryCriteria)
     */
    public List<StudyProtocolQueryDTO> getStudyProtocolByCriteria(
            StudyProtocolQueryCriteria sc) throws PAException {
        if(sc.getOfficialTitle() != null && sc.getOfficialTitle().equalsIgnoreCase("ThrowException")) {
            throw new PAException("Test");
        }
        List<StudyProtocolQueryDTO> returnList = new ArrayList<StudyProtocolQueryDTO>();
        for (StudyProtocolQueryDTO sp: list) {
            if(sp.getLocalStudyProtocolIdentifier().equals(sc.getLeadOrganizationTrialIdentifier())) {
                // TODO need to check with OrgId too
                returnList.add(sp);
            }
        }
        return returnList;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.util.ProtocolQueryServiceLocal#getTrialSummaryByStudyProtocolId(java.lang.Long)
     */
    public StudyProtocolQueryDTO getTrialSummaryByStudyProtocolId(
            Long studyProtocolId) throws PAException {
     
        for (StudyProtocolQueryDTO sp: list) {
            if(sp.getStudyProtocolId().equals(studyProtocolId)) {
                return sp;
            }
        }
        return null;
    }

}