package gov.nih.nci.pa.service.correlation;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.OrganizationalContact;
import gov.nih.nci.pa.domain.Person;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.HibernateUtil;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.services.correlation.NullifiedRoleException;
import gov.nih.nci.services.correlation.OrganizationalContactDTO;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.person.PersonDTO;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * A Service bean for maintaining Individuals who are employed and/or involved in any aspect of clinical research.
 * @author Naveen Amiruddin
 * @since 04/11/2008
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
@SuppressWarnings({ "PMD.AvoidDuplicateLiterals", "PMD.ExcessiveMethodLength",
    "PMD.CyclomaticComplexity", "PMD.ExcessiveClassLength", "PMD.NPathComplexity" })


public class OrganizationalContactCorrelationServiceBean {

    private static final Logger LOG  = Logger.getLogger(OrganizationalContactCorrelationServiceBean.class);
    /**
     * This method assumes Organization and Person record exists in PO.
     * @param orgPoIdentifier po primary org id
     * @param personPoIdentifer po primary person id
     * @return long id 
     * @throws PAException pe 
     */
    public void createOrganizationalContactCorrelations(String orgPoIdentifier, 
                                           String personPoIdentifer) throws PAException { {
        
        LOG.debug("Entering createClinicalResearchStaffCorrelation");
        
        CorrelationUtils corrUtils = new CorrelationUtils();
        if (orgPoIdentifier == null) {
            throw new PAException(" Organization PO Identifier is null");
        }
        if (personPoIdentifer == null) {
            throw new PAException(" Person PO Identifier is null");
        }

        // Step 1 : get the PO Organization
        OrganizationDTO poOrg = null;
        try {
            poOrg = PoRegistry.getOrganizationEntityService().
                getOrganization(IiConverter.converToPoOrganizationIi(orgPoIdentifier));
        } catch (NullifiedEntityException e) {
//            Map m = e.getNullifiedEntities();
           LOG.error("This Organization is no longer available instead use ");
           throw new PAException("This Organization is no longer available instead use ", e);
        }
        
        // Step 2 : get the PO Person
        PersonDTO poPer = null;
        try {
            poPer = PoRegistry.getPersonEntityService().getPerson(IiConverter.converToPoPersonIi(personPoIdentifer));
        } catch (NullifiedEntityException e) {
  //          Map m = e.getNullifiedEntities();
            LOG.error("This Person is no longer available instead use ");
            throw new PAException("This Person is no longer available instead use ", e);
         }
        
        
        // Step 2 : check if PO has oc correlation if not create one 
        OrganizationalContactDTO ocDTO = new OrganizationalContactDTO();
        List<OrganizationalContactDTO> ocDTOs = null;
        ocDTO.setOrganizationIdentifier(IiConverter.converToPoOrganizationIi(orgPoIdentifier));
        ocDTO.setPersonIdentifier(IiConverter.converToPoPersonIi(personPoIdentifer));
        try {
            ocDTOs = PoServiceBeanLookup.getOrganizationalContactCorrelationService().search(ocDTO);
        } catch (NullifiedRoleException e) {
            LOG.error("check with scoot", e);
            // @todo: this should not happen, check with 
        }
        if (ocDTOs != null && ocDTOs.size() > 1) {
            throw new PAException("PO oc Correlation should not have more than 1  ");
        }
        if (ocDTOs == null) {
            try {
                Ii ii = PoServiceBeanLookup.getOrganizationalContactCorrelationService().createCorrelation(ocDTO);
                ocDTO = PoServiceBeanLookup.getOrganizationalContactCorrelationService().getCorrelation(ii);
            } catch (NullifiedRoleException e) {
                LOG.error("Validation exception during get OrganizationalContact " , e);
                throw new PAException("Validation exception during get OrganizationalContact " , e);
            } catch (EntityValidationException e) {
                LOG.error("Validation exception during create OrganizationalContact " , e);
                throw new PAException("Validation exception during create OrganizationalContact " , e);
            } 
        } else {
            ocDTO = ocDTOs.get(0);
        }

        // Step 3 : check for pa org, if not create one
        Organization paOrg = corrUtils.getPAOrganizationByIndetifers(null , orgPoIdentifier);
        if (paOrg == null) {
            corrUtils.createPAOrganization(poOrg);
        }
        // Step 4 : check for pa person, if not create one
        Person paPer = corrUtils.getPAPersonByIndetifers(null , personPoIdentifer);
        if (paPer == null) {
            corrUtils.createPAPerson(poPer);
        }
        
        // Step 6 : Check of PA has oc , if not create one
        OrganizationalContact oc = new OrganizationalContact();
        oc.setIdentifier(ocDTO.getIdentifier().getExtension());
        oc = getPAOrganizationalContact(oc);
        if (oc == null) {
            // create a new oc
            oc = new OrganizationalContact();
            oc.setPerson(paPer);
            oc.setOrganization(paOrg);
            oc.setIdentifier(ocDTO.getIdentifier().getExtension());
            oc.setStatusCode(corrUtils.convertPORoleStatusToPARoleStatus(ocDTO.getStatus()));
            createPAOrganizationalContact(oc);
        }
                                           }
       // LOG.debug("Leaving createOrganizationalContactCorrelation");
        //return oc.getId();
        //return null;
    }

    /**
     * 
     * @param oc oc
     * @return oc
     * @throws PAException
     */
    private OrganizationalContact getPAOrganizationalContact(OrganizationalContact oc) 
    throws PAException {
        if (oc == null) {
            LOG.error("Clinicial Research Staff cannot be null");
            throw new PAException("Clinicial Research Staff cannot be null");
        }
        if (oc.getPerson() != null && oc.getOrganization() == null  
            || oc.getPerson() == null && oc.getOrganization() != null) {
            LOG.error("Both person and organization should be specified and it cannot be either");
            throw new PAException("Both person and organization should be specified and it cannot be either");
            
        }
        OrganizationalContact ocOut = null;
        Session session = null;
        List<OrganizationalContact> queryList = new ArrayList<OrganizationalContact>();
        StringBuffer hql = new StringBuffer();
        hql.append(" select oc from OrganizationalContact oc  " 
                + "join oc.person as per "
                + "join oc.organization as org where 1 = 1 ");
        if (oc.getId() != null) {
            hql.append(" and oc.id = ").append(oc.getId());
        }
        if (oc.getPerson() != null && oc.getOrganization()  != null 
                && oc.getPerson().getId() != null && oc.getOrganization().getId() != null) {
            hql.append(" and per.id = ").append(oc.getPerson().getId());
            hql.append(" and org.id = ").append(oc.getOrganization().getId());
        }
        if (oc.getIdentifier() != null) {
            hql.append(" and oc.identifier = '").append(oc.getIdentifier()).append('\'');
        }
        try {
            session = HibernateUtil.getCurrentSession();
            Query query = null;
        
        query = session.createQuery(hql.toString());
        queryList = query.list();
        
        if (queryList.size() > 1) {
            LOG.error(" Clinical Reasrch Staff should be more than 1 for any given criteria");
            throw new PAException(" Clinical Reasrch Staff should be more than 1 for any given criteria");
            
        }
    }  catch (HibernateException hbe) {
        LOG.error(" Error while retrieving Clinicial Research Staff" , hbe);
        throw new PAException(" Error while retrieving Clinicial Research Staff" , hbe);
    } finally {
        session.flush();
    }
    
    if (!queryList.isEmpty()) {
        ocOut = queryList.get(0);
    }
    return ocOut;
    }
    
    /**
     * 
     * @param oc OrganizationalContact 
     * @return OrganizationalContact
     * @throws PAException PAException
     */
    private OrganizationalContact createPAOrganizationalContact(OrganizationalContact oc) throws PAException {
        if (oc == null) {
            LOG.error(" OrganizationalContact should not be null ");
            throw new PAException(" OrganizationalContact should not be null ");
        }     
        LOG.debug("Entering createOrganizationalContact ");
        Session session = null;
        
        try {
            session = HibernateUtil.getCurrentSession();
            session.save(oc);
        } catch (HibernateException hbe) {
            
            LOG.error(" Hibernate exception while createOrganizationalContact " , hbe);
            throw new PAException(" Hibernate exception while create OrganizationalContact" , hbe);
        } finally {
            session.flush();
        }
        
        LOG.debug("Leaving create OrganizationalContact ");
        return oc;
    }
    
}
