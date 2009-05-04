/**
 * 
 */
package gov.nih.nci.registry.service;

import java.util.ArrayList;
import java.util.List;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.iso.dto.StudyOverallStatusDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyOverallStatusServiceRemote;
import gov.nih.nci.pa.util.PAUtil;

/**
 * @author Vrushali
 *
 */
public class MockStudyOverallStatusService implements
        StudyOverallStatusServiceRemote {
    static List<StudyOverallStatusDTO> list;
    static {
        list = new ArrayList<StudyOverallStatusDTO>();
        StudyOverallStatusDTO statusDTO = new StudyOverallStatusDTO();
        statusDTO.setIdentifier(IiConverter.convertToIi(1L));
        statusDTO.setStatusCode(CdConverter.convertStringToCd("Active"));
        statusDTO.setStatusDate(TsConverter.convertToTs(
                PAUtil.dateStringToTimestamp("01/20/2008")));
        statusDTO.setReasonText(null);
        list.add(statusDTO);
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
    public List<StudyOverallStatusDTO> getByStudyProtocol(Ii ii)
            throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.StudyPaService#getCurrentByStudyProtocol(gov.nih.nci.coppa.iso.Ii)
     */
    public List<StudyOverallStatusDTO> getCurrentByStudyProtocol(
            Ii studyProtocolIi) throws PAException {
        List<StudyOverallStatusDTO> listMatchingDto = new ArrayList<StudyOverallStatusDTO>();
        for (StudyOverallStatusDTO sp: list) {
            if(sp.getIdentifier().getExtension().equals(studyProtocolIi.getExtension())) {
                listMatchingDto.add(sp);
            }
        }
        return listMatchingDto;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.BasePaService#create(java.lang.Object)
     */
    public StudyOverallStatusDTO create(StudyOverallStatusDTO dto)
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
    public StudyOverallStatusDTO get(Ii ii) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.BasePaService#update(java.lang.Object)
     */
    public StudyOverallStatusDTO update(StudyOverallStatusDTO dto)
            throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

}
