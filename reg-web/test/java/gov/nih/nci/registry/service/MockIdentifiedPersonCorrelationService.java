/**
 * 
 */
package gov.nih.nci.registry.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.services.correlation.IdentifiedPersonCorrelationServiceRemote;
import gov.nih.nci.services.correlation.IdentifiedPersonDTO;
import gov.nih.nci.services.correlation.NullifiedRoleException;

/**
 * @author Vrushali
 *
 */
public class MockIdentifiedPersonCorrelationService implements
        IdentifiedPersonCorrelationServiceRemote {
    static List<IdentifiedPersonDTO> personList;
    static {
        personList = new ArrayList<IdentifiedPersonDTO>();
        IdentifiedPersonDTO dto = new IdentifiedPersonDTO();
        dto.setIdentifier(IiConverter.convertToIi("1"));
        dto.setPlayerIdentifier(IiConverter.convertToIi("2"));
        dto.setScoperIdentifier(IiConverter.convertToIi("1"));
        dto.setAssignedId(IiConverter.convertToIi("1"));
        dto.setStatus(CdConverter.convertStringToCd("code"));
        personList.add(dto);
    }
    /* (non-Javadoc)
     * @see gov.nih.nci.services.CorrelationService#createCorrelation(gov.nih.nci.services.PoDto)
     */
    public Ii createCorrelation(IdentifiedPersonDTO arg0)
            throws EntityValidationException {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.services.CorrelationService#getCorrelation(gov.nih.nci.coppa.iso.Ii)
     */
    public IdentifiedPersonDTO getCorrelation(Ii arg0)
            throws NullifiedRoleException {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.services.CorrelationService#getCorrelations(gov.nih.nci.coppa.iso.Ii[])
     */
    public List<IdentifiedPersonDTO> getCorrelations(Ii[] arg0)
            throws NullifiedRoleException {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.services.CorrelationService#search(gov.nih.nci.services.PoDto)
     */
    public List<IdentifiedPersonDTO> search(IdentifiedPersonDTO arg0) {
        List<IdentifiedPersonDTO> matchingDtosList = new ArrayList<IdentifiedPersonDTO>();
        for(IdentifiedPersonDTO dto: personList){
            if(dto.getAssignedId().getExtension().equals(arg0.getAssignedId().getExtension())){
                matchingDtosList.add(dto);
            }
        }
        return matchingDtosList;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.services.CorrelationService#updateCorrelation(gov.nih.nci.services.PoDto)
     */
    public void updateCorrelation(IdentifiedPersonDTO arg0)
            throws EntityValidationException {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see gov.nih.nci.services.CorrelationService#updateCorrelationStatus(gov.nih.nci.coppa.iso.Ii, gov.nih.nci.coppa.iso.Cd)
     */
    public void updateCorrelationStatus(Ii arg0, Cd arg1)
            throws EntityValidationException {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see gov.nih.nci.services.CorrelationService#validate(gov.nih.nci.services.PoDto)
     */
    public Map<String, String[]> validate(IdentifiedPersonDTO arg0) {
        // TODO Auto-generated method stub
        return null;
    }

}
