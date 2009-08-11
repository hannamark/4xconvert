/**
 * 
 */
package gov.nih.nci.service;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.enums.MilestoneCode;
import gov.nih.nci.pa.iso.dto.StudyMilestoneDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyMilestoneServiceRemote;
import gov.nih.nci.pa.util.PAUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Vrushali
 *
 */
public class MockStudyMilestoneService implements StudyMilestoneServiceRemote {
    static List<StudyMilestoneDTO> mileList;
    static {
        mileList = new ArrayList<StudyMilestoneDTO>();
        StudyMilestoneDTO dto = new StudyMilestoneDTO();
        dto.setIdentifier(IiConverter.convertToIi("1"));
        dto.setMilestoneCode(CdConverter.convertToCd(MilestoneCode.QC_START));
        dto.setStudyProtocolIdentifier(IiConverter.convertToIi("1"));
        dto.setMilestoneDate(TsConverter.convertToTs(PAUtil.dateStringToTimestamp("06/19/2009")));
        mileList.add(dto);
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
    public List<StudyMilestoneDTO> getByStudyProtocol(Ii ii) throws PAException {
        List<StudyMilestoneDTO> returnList = new ArrayList<StudyMilestoneDTO>();
        for(StudyMilestoneDTO dto : mileList){
            if(dto.getStudyProtocolIdentifier().getExtension().equals(ii.getExtension())){
                returnList.add(dto);
            }
        }
        return returnList;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.StudyPaService#getCurrentByStudyProtocol(gov.nih.nci.coppa.iso.Ii)
     */
    public StudyMilestoneDTO getCurrentByStudyProtocol(Ii studyProtocolIi)
            throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.BasePaService#create(java.lang.Object)
     */
    public StudyMilestoneDTO create(StudyMilestoneDTO dto) throws PAException {
        if(dto.getStudyProtocolIdentifier().getExtension().equals("9")) {
            throw new PAException("test");
        }
        return dto;
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
    public StudyMilestoneDTO get(Ii ii) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.BasePaService#update(java.lang.Object)
     */
    public StudyMilestoneDTO update(StudyMilestoneDTO dto) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

}
