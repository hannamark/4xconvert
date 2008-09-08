package gov.nih.nci.pa.service;

import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.dto.OrganizationDTO;
import gov.nih.nci.pa.enums.StudyParticipationFunctionalCode;
import gov.nih.nci.pa.util.HibernateUtil;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;


/** 
* Bean implementation for providing access to the client.
* 
* @author Naveen Amiruddin
* @since 06/26/2008
* copyright NCI 2007.  All rights reserved.
* This code may not be used without the express written permission of the
* copyright holder, NCI.
*/
@Stateless
public class PAOrganizationServiceBean implements 
    PAOrganizationServiceRemote {
    
    private static final Logger LOG  = Logger.getLogger(PAOrganizationServiceRemote.class);
    
    /**
     * returns distinct organization that have been associated with a protocol.
     * @return OrganizationDTO
     * @throws PAException pa exception
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<OrganizationDTO> getOrganizationsAssociatedWithStudyProtocol() 
    throws PAException {
        LOG.debug("Entereing getOrganizationsAssociatedWithStudyProtocol");
        List<Organization> organizations = generateDistinctOrganizationQuery();
        List<OrganizationDTO> organizationDTOs = createOrganizationDTO(organizations);
        LOG.debug("Leaving getOrganizationsAssociatedWithStudyProtocol");
        return organizationDTOs;  
            
    }
    
    @SuppressWarnings("PMD.ConsecutiveLiteralAppends")
    private List<Organization> generateDistinctOrganizationQuery()  
    throws PAException {
        LOG.debug("Entering generateDistinctOrganizationQuery ");

        Session session = null;
        List<Organization> organizations = null;
        
        try {
            session = HibernateUtil.getCurrentSession();
            StringBuffer hql = new StringBuffer();
            hql.append(
                      " Select distinct o from Organization o  " 
                    + " join o.healthCareFacilities as hcfs " 
                    + " join hcfs.studyParticipations as sps " 
                    + " join sps.studyProtocol as sp " 
                    + "  where sps.functionalCode = '"  
                    +   StudyParticipationFunctionalCode.LEAD_ORAGANIZATION  + "'");

            organizations =  session.createQuery(hql.toString()).list();
        } catch (HibernateException hbe) {
            LOG.error("  Hibernate exception while fetching " 
                     + "getOrganizationsAssociatedWithStudyProtocol ", hbe);
            throw new PAException(" Hibernate exception while fetching " 
                    + "getOrganizationsAssociatedWithStudyProtocol ", hbe);
        } finally {
            LOG.debug("Leaving generateDistinctOrganizationQuery ");
        }
        return organizations;
    }
    
    private List<OrganizationDTO> createOrganizationDTO(List<Organization> organizations) {
        LOG.debug("Entereing createOrganizationDTO");
        
        List<OrganizationDTO> organizationDTOs = new ArrayList<OrganizationDTO>();
        OrganizationDTO oganizationDTO = null;
        for (int i = 0; i < organizations.size(); i++) {
            oganizationDTO = new OrganizationDTO();
            oganizationDTO.setId(((Organization) organizations.get(i)).getId());
            oganizationDTO.setName(((Organization) organizations.get(i)).getName());
            organizationDTOs.add(oganizationDTO);
        }
        LOG.debug("Leaving createOrganizationDTO");
        return organizationDTOs;
    }
}
