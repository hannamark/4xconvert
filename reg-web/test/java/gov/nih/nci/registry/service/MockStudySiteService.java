/**
 * 
 */
package gov.nih.nci.registry.service;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.enums.StudySiteFunctionalCode;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudySiteServiceRemote;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Vrushali
 *
 */
public class MockStudySiteService extends MockAbstractRoleIsoService implements
        StudySiteServiceRemote {
     static List<StudySiteDTO> list;
     static{
         list = new ArrayList<StudySiteDTO>();
         StudySiteDTO  dto = new StudySiteDTO();
         dto.setIdentifier(IiConverter.convertToIi("1"));
         dto.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.IDENTIFIER_ASSIGNER));
         dto.setLocalStudyProtocolIdentifier(StConverter.convertToSt("localStudyProtocolIdentifier"));
         dto.setStudyProtocolIdentifier(IiConverter.convertToIi("1"));
         list.add(dto);
     }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.RolePaService#getByStudyProtocol(gov.nih.nci.coppa.iso.Ii, java.lang.Object)
     */
    public List<StudySiteDTO> getByStudyProtocol(Ii studyProtocolIi,
            StudySiteDTO dto) throws PAException {
        
        List<StudySiteDTO> matchDtosList = new ArrayList<StudySiteDTO>();
        for (StudySiteDTO studyPDto: list) {
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
    public List<StudySiteDTO> getByStudyProtocol(Ii studyProtocolIi,
            List<StudySiteDTO> dto) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.StudyPaService#copy(gov.nih.nci.coppa.iso.Ii, gov.nih.nci.coppa.iso.Ii)
     */
    public Map<Ii , Ii> copy(Ii fromStudyProtocolIi, Ii toStudyProtocolIi)
            throws PAException {
        // TODO Auto-generated method stub
    	return null;

    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.StudyPaService#getByStudyProtocol(gov.nih.nci.coppa.iso.Ii)
     */
    public List<StudySiteDTO> getByStudyProtocol(Ii ii)
            throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.StudyPaService#getCurrentByStudyProtocol(gov.nih.nci.coppa.iso.Ii)
     */
    public StudySiteDTO getCurrentByStudyProtocol(
            Ii studyProtocolIi) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.BasePaService#create(java.lang.Object)
     */
    public StudySiteDTO create(StudySiteDTO dto)
            throws PAException {
        // TODO Auto-generated method stub
        return new StudySiteDTO();
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
    public StudySiteDTO get(Ii ii) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.BasePaService#update(java.lang.Object)
     */
    public StudySiteDTO update(StudySiteDTO dto)
            throws PAException {
        // TODO Auto-generated method stub
        return null;
    }


}
