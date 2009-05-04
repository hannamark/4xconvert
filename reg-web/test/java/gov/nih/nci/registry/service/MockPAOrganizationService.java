/**
 * 
 */
package gov.nih.nci.registry.service;

import java.util.ArrayList;
import java.util.List;

import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.dto.PaOrganizationDTO;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.util.PAOrganizationServiceRemote;

/**
 * @author Vrushali
 *
 */
public class MockPAOrganizationService implements PAOrganizationServiceRemote {
    static List<Organization> orgList;
    static {
        orgList = new ArrayList<Organization>();
        Organization dto = new Organization();
        dto.setIdentifier("1");
        dto.setId(1L);
        dto.setName("OrgName");
        orgList.add(dto);
    }
    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.util.PAOrganizationServiceRemote#getOrganizationByIndetifers(gov.nih.nci.pa.domain.Organization)
     */
    public Organization getOrganizationByIndetifers(Organization organization)
            throws PAException {
        for (Organization org: orgList) {
            if( org.getIdentifier().equals(organization.getIdentifier())) {
                return org;
            }
        }
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.util.PAOrganizationServiceRemote#getOrganizationsAssociatedWithStudyProtocol(java.lang.String)
     */
    public List<PaOrganizationDTO> getOrganizationsAssociatedWithStudyProtocol(
            String organizationType) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

}
