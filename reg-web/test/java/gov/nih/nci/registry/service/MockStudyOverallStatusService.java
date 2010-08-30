/**
 *
 */
package gov.nih.nci.registry.service;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.iso.dto.StudyOverallStatusDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyOverallStatusServiceLocal;
import gov.nih.nci.pa.util.PAUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Vrushali
 *
 */
public class MockStudyOverallStatusService extends MockAbstractBaseIsoService<StudyOverallStatusDTO> implements
        StudyOverallStatusServiceLocal {
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

    public Map<Ii , Ii> copy(Ii fromStudyProtocolIi, Ii toStudyProtocolIi)
            throws PAException {
           return null;
        // TODO Auto-generated method stub

    }

    public List<StudyOverallStatusDTO> getByStudyProtocol(Ii ii)
            throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    public StudyOverallStatusDTO getCurrentByStudyProtocol(
            Ii studyProtocolIi) throws PAException {
        StudyOverallStatusDTO listMatchingDto = null;
        for (StudyOverallStatusDTO sp: list) {
            if(sp.getIdentifier().getExtension().equals(studyProtocolIi.getExtension())) {
                listMatchingDto = sp;
            }
        }
        return listMatchingDto;
    }

    @Override
    public StudyOverallStatusDTO create(StudyOverallStatusDTO dto)
            throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void delete(Ii ii) throws PAException {
        // TODO Auto-generated method stub

    }

    @Override
    public StudyOverallStatusDTO get(Ii ii) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public StudyOverallStatusDTO update(StudyOverallStatusDTO dto)
            throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    public void validate(StudyOverallStatusDTO statusDto,
            StudyProtocolDTO studyProtocolDTO) throws PAException {
        // TODO Auto-generated method stub
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.StudyOverallStatusServiceLocal#isTrialStatusOrDateChanged(gov.nih.nci.pa.iso.dto.StudyOverallStatusDTO, gov.nih.nci.iso21090.Ii)
     */
    public boolean isTrialStatusOrDateChanged(StudyOverallStatusDTO newStatusDto, Ii studyProtocolIi)
            throws PAException {
        // TODO Auto-generated method stub
        return false;
    }

}
