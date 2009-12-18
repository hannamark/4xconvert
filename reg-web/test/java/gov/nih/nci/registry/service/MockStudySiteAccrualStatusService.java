/**
 * 
 */
package gov.nih.nci.registry.service;

import java.util.ArrayList;
import java.util.List;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.iso.dto.DocumentDTO;
import gov.nih.nci.pa.iso.dto.StudySiteAccrualStatusDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudySiteAccrualStatusServiceLocal;
import gov.nih.nci.pa.util.PAUtil;

/**
 * @author Vrushali
 *
 */
public class MockStudySiteAccrualStatusService implements
        StudySiteAccrualStatusServiceLocal {
    static List<StudySiteAccrualStatusDTO> dtoList;
    static {
        dtoList = new ArrayList<StudySiteAccrualStatusDTO>();
        StudySiteAccrualStatusDTO dto = new StudySiteAccrualStatusDTO();
        dto.setIdentifier(IiConverter.convertToStudySiteAccuralStatusIi(1L));
        dto.setStatusCode(CdConverter.convertStringToCd("PENDING"));
        dto.setStatusDate(TsConverter.convertToTs(PAUtil.dateStringToTimestamp("12/16/2009")));
        dto.setStudySiteIi(IiConverter.convertToStudySiteIi(1L));
        dtoList.add(dto);
        
    }
    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.StudySiteAccrualStatusService#createStudySiteAccrualStatus(gov.nih.nci.pa.iso.dto.StudySiteAccrualStatusDTO)
     */
    public StudySiteAccrualStatusDTO createStudySiteAccrualStatus(
            StudySiteAccrualStatusDTO dto) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.StudySiteAccrualStatusService#getCurrentStudySiteAccrualStatusByStudySite(gov.nih.nci.coppa.iso.Ii)
     */
    public StudySiteAccrualStatusDTO getCurrentStudySiteAccrualStatusByStudySite(
            Ii studySiteIi) throws PAException {
        StudySiteAccrualStatusDTO  returnDto = new StudySiteAccrualStatusDTO();
        for (StudySiteAccrualStatusDTO dto :dtoList) {
            if (PAUtil.isIiNotNull(studySiteIi) && PAUtil.isIiNotNull(dto.getStudySiteIi()) 
                    && studySiteIi.getExtension().equalsIgnoreCase(dto.getStudySiteIi().getExtension())) {
                returnDto = dto;
            }
        }
        return returnDto;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.StudySiteAccrualStatusService#getStudySiteAccrualStatus(gov.nih.nci.coppa.iso.Ii)
     */
    public StudySiteAccrualStatusDTO getStudySiteAccrualStatus(Ii ii)
            throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.StudySiteAccrualStatusService#getStudySiteAccrualStatusByStudySite(gov.nih.nci.coppa.iso.Ii)
     */
    public List<StudySiteAccrualStatusDTO> getStudySiteAccrualStatusByStudySite(
            Ii studySiteIi) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.StudySiteAccrualStatusService#updateStudySiteAccrualStatus(gov.nih.nci.pa.iso.dto.StudySiteAccrualStatusDTO)
     */
    public StudySiteAccrualStatusDTO updateStudySiteAccrualStatus(
            StudySiteAccrualStatusDTO dto) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

}
