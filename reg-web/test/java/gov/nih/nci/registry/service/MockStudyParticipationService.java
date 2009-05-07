/**
 * 
 */
package gov.nih.nci.registry.service;

import java.util.ArrayList;
import java.util.List;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.enums.StudyParticipationFunctionalCode;
import gov.nih.nci.pa.iso.dto.StudyIndldeDTO;
import gov.nih.nci.pa.iso.dto.StudyParticipationDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyParticipationServiceRemote;

/**
 * @author Vrushali
 *
 */
public class MockStudyParticipationService implements
        StudyParticipationServiceRemote {
     static List<StudyParticipationDTO> list;
     static{
         list = new ArrayList<StudyParticipationDTO>();
         StudyParticipationDTO  dto = new StudyParticipationDTO();
         dto.setIdentifier(IiConverter.convertToIi("1"));
         dto.setFunctionalCode(CdConverter.convertToCd(StudyParticipationFunctionalCode.IDENTIFIER_ASSIGNER));
         dto.setLocalStudyProtocolIdentifier(StConverter.convertToSt("localStudyProtocolIdentifier"));
         dto.setStudyProtocolIdentifier(IiConverter.convertToIi("1"));
         list.add(dto);
     }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.RolePaService#getByStudyProtocol(gov.nih.nci.coppa.iso.Ii, java.lang.Object)
     */
    public List<StudyParticipationDTO> getByStudyProtocol(Ii studyProtocolIi,
            StudyParticipationDTO dto) throws PAException {
        
        List<StudyParticipationDTO> matchDtosList = new ArrayList<StudyParticipationDTO>();
        for (StudyParticipationDTO studyPDto: list) {
            if(studyPDto.getStudyProtocolIdentifier().getExtension()
                    .equals(studyProtocolIi.getExtension())
                    && studyPDto.getFunctionalCode().getCode().equals(dto.getFunctionalCode().getCode())) {
                matchDtosList.add(studyPDto);
            }
        }
        return matchDtosList;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.RolePaService#getByStudyProtocol(gov.nih.nci.coppa.iso.Ii, java.util.List)
     */
    public List<StudyParticipationDTO> getByStudyProtocol(Ii studyProtocolIi,
            List<StudyParticipationDTO> dto) throws PAException {
        // TODO Auto-generated method stub
        return null;
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
    public List<StudyParticipationDTO> getByStudyProtocol(Ii ii)
            throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.StudyPaService#getCurrentByStudyProtocol(gov.nih.nci.coppa.iso.Ii)
     */
    public List<StudyParticipationDTO> getCurrentByStudyProtocol(
            Ii studyProtocolIi) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.BasePaService#create(java.lang.Object)
     */
    public StudyParticipationDTO create(StudyParticipationDTO dto)
            throws PAException {
        // TODO Auto-generated method stub
        return new StudyParticipationDTO();
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
    public StudyParticipationDTO get(Ii ii) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.BasePaService#update(java.lang.Object)
     */
    public StudyParticipationDTO update(StudyParticipationDTO dto)
            throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

}
