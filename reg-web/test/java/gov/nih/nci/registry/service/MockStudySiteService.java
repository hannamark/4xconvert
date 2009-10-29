/**
 * 
 */
package gov.nih.nci.registry.service;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.pa.enums.StudySiteFunctionalCode;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudySiteServiceRemote;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Vrushali
 *
 */
public class MockStudySiteService extends MockAbstractRoleIsoService<StudySiteDTO> implements
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

    public List<StudySiteDTO> search(StudySiteDTO dto, LimitOffset pagingParams)
            throws PAException, TooManyResultsException {
        // TODO Auto-generated method stub
        return null;
    }

}
