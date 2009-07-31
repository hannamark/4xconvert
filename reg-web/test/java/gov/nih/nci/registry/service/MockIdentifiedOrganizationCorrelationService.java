/**
 * 
 */
package gov.nih.nci.registry.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.services.correlation.IdentifiedOrganizationCorrelationServiceRemote;
import gov.nih.nci.services.correlation.IdentifiedOrganizationDTO;
import gov.nih.nci.services.correlation.NullifiedRoleException;

/**
 * @author Vrushali
 *
 */
public class MockIdentifiedOrganizationCorrelationService implements
        IdentifiedOrganizationCorrelationServiceRemote {
        static List<IdentifiedOrganizationDTO> orgList;
        static {
            orgList = new ArrayList<IdentifiedOrganizationDTO>();
            IdentifiedOrganizationDTO dto = new IdentifiedOrganizationDTO();
            dto.setAssignedId(IiConverter.convertToIi("1"));
            dto.setPlayerIdentifier(IiConverter.convertToIi("1"));
            dto.setScoperIdentifier(IiConverter.convertToIi("1"));
            dto.setStatus(CdConverter.convertStringToCd("code"));
            orgList.add(dto);
        }
    /* (non-Javadoc)
     * @see gov.nih.nci.services.CorrelationService#createCorrelation(gov.nih.nci.services.PoDto)
     */
    public Ii createCorrelation(IdentifiedOrganizationDTO arg0)
            throws EntityValidationException {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.services.CorrelationService#getCorrelation(gov.nih.nci.coppa.iso.Ii)
     */
    public IdentifiedOrganizationDTO getCorrelation(Ii arg0)
            throws NullifiedRoleException {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.services.CorrelationService#getCorrelations(gov.nih.nci.coppa.iso.Ii[])
     */
    public List<IdentifiedOrganizationDTO> getCorrelations(Ii[] arg0)
            throws NullifiedRoleException {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.services.CorrelationService#search(gov.nih.nci.services.PoDto)
     */
    public List<IdentifiedOrganizationDTO> search(IdentifiedOrganizationDTO arg0) {
        List<IdentifiedOrganizationDTO> matchingDto = new ArrayList<IdentifiedOrganizationDTO>();
        for(IdentifiedOrganizationDTO dto:orgList){
            if(dto.getAssignedId().getExtension().equals(arg0.getAssignedId().getExtension())){
                matchingDto.add(dto);
            }
        }
        return matchingDto;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.services.CorrelationService#updateCorrelation(gov.nih.nci.services.PoDto)
     */
    public void updateCorrelation(IdentifiedOrganizationDTO arg0)
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
    public Map<String, String[]> validate(IdentifiedOrganizationDTO arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    public List<IdentifiedOrganizationDTO> search(
            IdentifiedOrganizationDTO arg0, LimitOffset arg1)
            throws TooManyResultsException {
        // TODO Auto-generated method stub
        return null;
    }

}
