/**
 * 
 */
package gov.nih.nci.service;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.dto.StudyProtocolQueryCriteria;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.ActualAnticipatedTypeCode;
import gov.nih.nci.pa.iso.convert.StudyProtocolConverter;
import gov.nih.nci.pa.iso.dto.InterventionalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.ObservationalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyProtocolServiceRemote;
import gov.nih.nci.pa.util.PAUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hreinhart
 *
 */
public class MockStudyProtocolService implements StudyProtocolServiceRemote {

    ArrayList<StudyProtocol> spList;

   /**
     * 
     */
    public MockStudyProtocolService() {
        super();
        spList = new ArrayList<StudyProtocol>();
        StudyProtocol sp = new StudyProtocol();
        sp.setId(1L);
        sp.setStartDateTypeCode(ActualAnticipatedTypeCode.ACTUAL);
        sp.setStartDate(PAUtil.dateStringToTimestamp("1/1/2000"));
        sp.setPrimaryCompletionDateTypeCode(ActualAnticipatedTypeCode.ANTICIPATED);
        sp.setPrimaryCompletionDate(PAUtil.dateStringToTimestamp("4/15/2010"));
        spList.add(sp);
    }

    public StudyProtocolDTO getStudyProtocol(Ii ii) throws PAException {
        for (StudyProtocol sp: spList) {
            if(sp.getId().equals(IiConverter.convertToLong(ii))) {
                return StudyProtocolConverter.convertFromDomainToDTO(sp);
            }
        }
        return null;
    }

    public StudyProtocolDTO updateStudyProtocol(
            StudyProtocolDTO dto) throws PAException {
        for (StudyProtocol bo : spList) {
            if (bo.getId().equals(IiConverter.convertToLong(dto.getIdentifier()))) {
                bo.setStartDateTypeCode(ActualAnticipatedTypeCode.getByCode(dto.getStartDateTypeCode().getCode()));
                bo.setPrimaryCompletionDateTypeCode(ActualAnticipatedTypeCode.getByCode(dto.getPrimaryCompletionDateTypeCode().getCode()));
                bo.setStartDate(TsConverter.convertToTimestamp(dto.getStartDate()));
                bo.setPrimaryCompletionDate(TsConverter.convertToTimestamp(dto.getPrimaryCompletionDate()));
                return StudyProtocolConverter.convertFromDomainToDTO(bo);
            }
        }
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.StudyProtocolService#getInterventionalStudyProtocol(gov.nih.nci.coppa.iso.Ii)
     */
    public InterventionalStudyProtocolDTO getInterventionalStudyProtocol(Ii ii)
            throws PAException {
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.StudyProtocolService#getStudyProtocolByCriteria(gov.nih.nci.pa.dto.StudyProtocolQueryCriteria)
     */
    public List<StudyProtocolQueryDTO> getStudyProtocolByCriteria(
            StudyProtocolQueryCriteria sc) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.StudyProtocolService#getTrialSummaryByStudyProtocolId(java.lang.Long)
     */
    public StudyProtocolQueryDTO getTrialSummaryByStudyProtocolId(
            Long studyProtocolId) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.StudyProtocolService#updateInterventionalStudyProtocol(gov.nih.nci.pa.iso.dto.InterventionalStudyProtocolDTO)
     */
    public InterventionalStudyProtocolDTO updateInterventionalStudyProtocol(
            InterventionalStudyProtocolDTO ispDTO) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }
    
    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.StudyProtocolService#updateInterventionalStudyProtocol(gov.nih.nci.pa.iso.dto.InterventionalStudyProtocolDTO)
     */
    public ObservationalStudyProtocolDTO getObservationalStudyProtocol(Ii ii) throws PAException {
        return null;
    }
    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.StudyProtocolService#updateInterventionalStudyProtocol(gov.nih.nci.pa.iso.dto.InterventionalStudyProtocolDTO)
     */
    
    public Ii createInterventionalStudyProtocol(InterventionalStudyProtocolDTO ispDTO)
    throws PAException {
        return null;
    }

    public Ii createObservationalStudyProtocol(
            ObservationalStudyProtocolDTO ospDTO) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    public ObservationalStudyProtocolDTO updateObservationalStudyProtocol(
            ObservationalStudyProtocolDTO ospDTO) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }    
}
