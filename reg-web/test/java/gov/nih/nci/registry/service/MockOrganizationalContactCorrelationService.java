/**
 * 
 */
package gov.nih.nci.registry.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.services.correlation.NullifiedRoleException;
import gov.nih.nci.services.correlation.OrganizationalContactCorrelationServiceRemote;
import gov.nih.nci.services.correlation.OrganizationalContactDTO;

/**
 * @author Vrushali
 *
 */
public class MockOrganizationalContactCorrelationService implements
        OrganizationalContactCorrelationServiceRemote {
    static List<OrganizationalContactDTO> list;
    static {
        list = new ArrayList<OrganizationalContactDTO>();
        OrganizationalContactDTO dto = new OrganizationalContactDTO();
        dto.setPlayerIdentifier(IiConverter.convertToIi("2"));
        dto.setIdentifier(IiConverter.convertToIi("1"));
        dto.setScoperIdentifier(IiConverter.convertToIi("1"));
        list.add(dto);
        dto = new OrganizationalContactDTO();
        dto.setPlayerIdentifier(IiConverter.convertToIi("3"));
        dto.setIdentifier(IiConverter.convertToIi("2"));
        dto.setScoperIdentifier(IiConverter.convertToIi("2"));
        list.add(dto);
    }
    /* (non-Javadoc)
     * @see gov.nih.nci.services.CorrelationService#createCorrelation(gov.nih.nci.services.PoDto)
     */
    public Ii createCorrelation(OrganizationalContactDTO arg0)
            throws EntityValidationException {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.services.CorrelationService#getCorrelation(gov.nih.nci.coppa.iso.Ii)
     */
    public OrganizationalContactDTO getCorrelation(Ii arg0)
            throws NullifiedRoleException {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.services.CorrelationService#getCorrelations(gov.nih.nci.coppa.iso.Ii[])
     */
    public List<OrganizationalContactDTO> getCorrelations(Ii[] arg0)
            throws NullifiedRoleException {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.services.CorrelationService#search(gov.nih.nci.services.PoDto)
     */
    public List<OrganizationalContactDTO> search(OrganizationalContactDTO arg0) {
        List<OrganizationalContactDTO> returnList = new ArrayList<OrganizationalContactDTO>();
        for (OrganizationalContactDTO dto : list){
            if(dto.getScoperIdentifier().getExtension().equals(arg0.getScoperIdentifier().getExtension()))
                returnList.add(dto);
        }
        return returnList;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.services.CorrelationService#updateCorrelation(gov.nih.nci.services.PoDto)
     */
    public void updateCorrelation(OrganizationalContactDTO arg0)
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
    public Map<String, String[]> validate(OrganizationalContactDTO arg0) {
        // TODO Auto-generated method stub
        return null;
    }

}
