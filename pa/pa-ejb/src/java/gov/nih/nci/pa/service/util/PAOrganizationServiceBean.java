package gov.nih.nci.pa.service.util;

import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.dto.OrganizationDTO;
import gov.nih.nci.pa.enums.StudyParticipationFunctionalCode;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.HibernateUtil;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
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
@SuppressWarnings({  "PMD.CyclomaticComplexity" , "PMD.ExcessiveMethodLength" })
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
    
    /**
     * This expects only id and identifier.
     * @param organization organization
     * @return Organization
     * @throws PAException PAException
     */
    public Organization getOrganizationByIndetifers(Organization organization) throws PAException {
        Organization org = null;
        
        if (organization.getId() == null && organization.getIdentifier() == null) {
            LOG.error(" Id or poIdentifer should not be null ");
            throw new PAException(" Id or poIdentifer should not be null ");
        }
        LOG.info("Entering getOrganizationByIndetifers");
        Session session = null;
        List<Organization> queryList = new ArrayList<Organization>();
        try {
            session = HibernateUtil.getCurrentSession();

            Query query = null;
            
            // step 1: form the hql
            StringBuffer hql = new StringBuffer();
            hql.append(" select org from Organization org  where 1 = 1 ");
            if (organization.getId() != null) {
                hql.append(" and org.id = ").append(organization.getId());
            }
            if (organization.getIdentifier() != null) {
                hql.append(" and org.identifier = '").append(organization.getIdentifier()).append('\'');
            }

           LOG.info(" query getOrganizationByPoIndetifer = " + hql);
            
            // step 2: construct query object
            query = session.createQuery(hql.toString());
            queryList = query.list();
            
            if (queryList.size() > 1) {
                LOG.error(" Organization  should not be more than 1 record for a Po Indetifer = "  
                        + organization.getIdentifier());
                throw new PAException(" Organization  should not be more than 1 " 
                        + "record for a Po Indetifer = " + organization.getIdentifier());
                
            }
        }  catch (HibernateException hbe) {
            LOG.error(" Organization  should not be more than 1 record for a Po Indetifer = "  
                    + organization.getIdentifier());
            throw new PAException(" Organization  should not be more than 1 " 
                    + "record for a Po Indetifer = " + organization.getIdentifier(), hbe);
        } finally {
            session.flush();
        }
        
        if (!queryList.isEmpty()) {
            org = queryList.get(0);
        }
        LOG.info("Leaving getOrganizationByIndetifers");
        return org;
    }
    

    /**
     * 
     * @param organization Organization
     * @return Organization
     * @throws PAException PAException
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public Organization createOrganization(Organization organization) throws PAException {
        if (organization == null) {
            LOG.error(" organization should not be null ");
            throw new PAException(" organization should not be null ");
        }     
        LOG.debug("Entering createOrganization ");
        Session session = null;
        
        try {
            session = HibernateUtil.getCurrentSession();
            session.save(organization);
        } catch (HibernateException hbe) {
            
            LOG.error(" Hibernate exception while createOrganization " , hbe);
            throw new PAException(" Hibernate exception while createOrganization " , hbe);
        } finally {
            session.flush();
        }
        
        LOG.debug("Leaving createStudyResourcing ");
        return organization;
        
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
