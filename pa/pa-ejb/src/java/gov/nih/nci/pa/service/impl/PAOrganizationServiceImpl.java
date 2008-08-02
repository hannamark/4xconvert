package gov.nih.nci.pa.service.impl;

import gov.nih.nci.pa.dao.StudySiteDAO;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.dto.OrganizationDTO;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.PAOrganizationService;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;


/**
 * Implementer class for PAOrganizationService, which will be invoked by the EJB bean.
 * If need be, these methods can be exposed as web service
 * @author Harsha, Naveen
 */
public class PAOrganizationServiceImpl implements PAOrganizationService {
    
    private static final Logger LOG  = Logger.getLogger(PAOrganizationServiceImpl.class);
    /**
     * returns all the organization that have been associated with a protocol.
     * @return OrganizationDTO
     * @throws PAException pa exception
     */
    public List<OrganizationDTO> getOrganizationsAssociatedWithStudyProtocol() 
    throws PAException {
        List<Organization> organizations = null;
        List<OrganizationDTO> organizationDTOs = new ArrayList<OrganizationDTO>();
        OrganizationDTO oganizationDTO = null;
        try {
        LOG.debug("Entereing getOrganizationsAssociatedWithStudyProtocol");
        organizations = (new StudySiteDAO()).getOrganizationsAssociatedWithStudyProtocol();
        for (int i = 0; i < organizations.size(); i++) {
            oganizationDTO = new OrganizationDTO();
            oganizationDTO.setId(((Organization) organizations.get(i)).getId());
            oganizationDTO.setName(((Organization) organizations.get(i)).getName());
            organizationDTOs.add(oganizationDTO);
        }
        return organizationDTOs;
        } finally {
            LOG.debug("Leaving getOrganizationsAssociatedWithStudyProtocol");
        }
    }

}
