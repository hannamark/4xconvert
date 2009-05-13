/**
 * 
 */
package gov.nih.nci.registry.service;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.iso.dto.StudyIndldeDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyIndldeServiceRemote;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Vrushali
 *
 */
public class MockStudyIndldeService implements StudyIndldeServiceRemote {
    static List<StudyIndldeDTO> listLndDtos = new ArrayList<StudyIndldeDTO>();
    static {
        StudyIndldeDTO indDto = new StudyIndldeDTO();
        indDto.setIndldeTypeCode(CdConverter.convertStringToCd("IND"));
        indDto.setIndldeNumber(StConverter.convertToSt("Ind no"));
        indDto.setGrantorCode(CdConverter.convertStringToCd("CDER"));
        indDto.setHolderTypeCode(CdConverter.convertStringToCd("Investigator"));
        indDto.setExpandedAccessIndicator(BlConverter.convertToBl(Boolean.FALSE));
        indDto.setIdentifier(IiConverter.convertToIi("1"));
        indDto.setExpandedAccessStatusCode(CdConverter.convertStringToCd("expandedAccessType"));
        indDto.setNciDivProgHolderCode(CdConverter.convertStringToCd("programCode"));
        indDto.setNihInstHolderCode(CdConverter.convertStringToCd(""));
        indDto.setStudyProtocolIdentifier(IiConverter.convertToIi("1"));
        listLndDtos.add(indDto);
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
    public List<StudyIndldeDTO> getByStudyProtocol(Ii ii) throws PAException {
       List<StudyIndldeDTO> matchIndList = new ArrayList<StudyIndldeDTO>();
        for (StudyIndldeDTO indDto: listLndDtos) {
            if(indDto.getStudyProtocolIdentifier().getExtension()
                    .equals(ii.getExtension())) {
                matchIndList.add(indDto);
            }
        }
        return matchIndList;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.StudyPaService#getCurrentByStudyProtocol(gov.nih.nci.coppa.iso.Ii)
     */
    public List<StudyIndldeDTO> getCurrentByStudyProtocol(Ii studyProtocolIi)
            throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.BasePaService#create(java.lang.Object)
     */
    public StudyIndldeDTO create(StudyIndldeDTO dto) throws PAException {
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
    public StudyIndldeDTO get(Ii ii) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.BasePaService#update(java.lang.Object)
     */
    public StudyIndldeDTO update(StudyIndldeDTO dto) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

}
