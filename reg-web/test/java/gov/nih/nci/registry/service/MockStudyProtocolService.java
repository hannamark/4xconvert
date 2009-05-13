package gov.nih.nci.registry.service;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.domain.InterventionalStudyProtocol;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.enums.ActualAnticipatedTypeCode;
import gov.nih.nci.pa.iso.convert.InterventionalStudyProtocolConverter;
import gov.nih.nci.pa.iso.convert.StudyProtocolConverter;
import gov.nih.nci.pa.iso.dto.InterventionalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.ObservationalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyProtocolServiceRemote;
import gov.nih.nci.pa.util.PAUtil;

import java.util.ArrayList;
import java.util.List;

public class MockStudyProtocolService implements StudyProtocolServiceRemote {

    static List<StudyProtocol> list;
    static List<InterventionalStudyProtocol> isplist;

    static {
        list = new ArrayList<StudyProtocol>();
        StudyProtocol sp = new InterventionalStudyProtocol();
        sp.setId(1L);
        sp.setStartDateTypeCode(ActualAnticipatedTypeCode.ACTUAL);
        sp.setStartDate(PAUtil.dateStringToTimestamp("01/20/2008"));
        sp.setPrimaryCompletionDateTypeCode(ActualAnticipatedTypeCode.ANTICIPATED);
        sp.setPrimaryCompletionDate(PAUtil.dateStringToTimestamp("01/20/2010"));
        sp.setOfficialTitle("officialTitle");
        list.add(sp);
        sp = new InterventionalStudyProtocol();
        sp.setId(3L);
        sp.setStartDateTypeCode(ActualAnticipatedTypeCode.ACTUAL);
        sp.setStartDate(PAUtil.dateStringToTimestamp("01/20/2008"));
        sp.setPrimaryCompletionDateTypeCode(ActualAnticipatedTypeCode.ANTICIPATED);
        sp.setPrimaryCompletionDate(PAUtil.dateStringToTimestamp("01/20/2010"));
        sp.setOfficialTitle("officialTitle");
        list.add(sp);
        
        isplist = new ArrayList<InterventionalStudyProtocol>();
        InterventionalStudyProtocol isp = new InterventionalStudyProtocol();
        isp.setId(1L);
        isp.setStartDateTypeCode(ActualAnticipatedTypeCode.ACTUAL);
        isp.setStartDate(PAUtil.dateStringToTimestamp("1/1/2000"));
        isp.setPrimaryCompletionDateTypeCode(ActualAnticipatedTypeCode.ANTICIPATED);
        isp.setPrimaryCompletionDate(PAUtil.dateStringToTimestamp("4/15/2010"));
        isp.setOfficialTitle("officialTitle");
        isplist.add(isp);
    }
    
    public List<StudyProtocolDTO> search(StudyProtocolDTO spDTO) throws PAException{
    	return null;
    }

    public StudyProtocolDTO getStudyProtocol(Ii ii) throws PAException {
        for (StudyProtocol sp: list) {
            if(sp.getId().equals(IiConverter.convertToLong(ii))) {
                return StudyProtocolConverter.convertFromDomainToDTO(sp);
            }
        }
        return null;
    }

    public Ii createInterventionalStudyProtocol(
            InterventionalStudyProtocolDTO ispDTO) throws PAException {
        // TODO Auto-generated method stub
        return IiConverter.convertToIi("2");
    }

    public Ii createObservationalStudyProtocol(
            ObservationalStudyProtocolDTO ospDTO) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    public void deleteStudyProtocol(Ii ii) throws PAException {
        // TODO Auto-generated method stub
        
    }

    public InterventionalStudyProtocolDTO getInterventionalStudyProtocol(Ii ii)
            throws PAException {
        for (InterventionalStudyProtocol isp: isplist) {
            if(isp.getId().equals(IiConverter.convertToLong(ii))) {
                return InterventionalStudyProtocolConverter.convertFromDomainToDTO(isp);
            }
        }
        return null;
    }

    public ObservationalStudyProtocolDTO getObservationalStudyProtocol(Ii ii)
            throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    public InterventionalStudyProtocolDTO updateInterventionalStudyProtocol(
            InterventionalStudyProtocolDTO ispDTO) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    public ObservationalStudyProtocolDTO updateObservationalStudyProtocol(
            ObservationalStudyProtocolDTO ospDTO) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    public StudyProtocolDTO updateStudyProtocol(
            StudyProtocolDTO studyProtocolDTO) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

}
