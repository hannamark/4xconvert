package gov.nih.nci.pa.service.correlation;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.domain.ClinicalResearchStaff;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.Person;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.HibernateUtil;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.services.correlation.ClinicalResearchStaffDTO;
import gov.nih.nci.services.correlation.NullifiedRoleException;
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

public class ClinicalResearchStaffCorrelationServiceBean {
    
    private static final Logger LOG  = Logger.getLogger(ClinicalResearchStaffCorrelationServiceBean.class);
    /**
     * This method assumes Organization and Person record exists in PO.
     * @param orgPoIdentifier po primary org id
     * @param personPoIdentifer po primary person id
     * @return long id 
     * @throws PAException pe 
     */
    public Long createClinicalResearchStaffCorrelations(String orgPoIdentifier, 
                                           String personPoIdentifer) 
    throws PAException {
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
            poOrg = PoPaServiceBeanLookup.getOrganizationEntityService().
                getOrganization(IiConverter.converToPoOrganizationIi(orgPoIdentifier));
        } catch (NullifiedEntityException e) {
//            Map m = e.getNullifiedEntities();
           LOG.error("This Organization is no longer available instead use ");
           throw new PAException("This Organization is no longer available instead use ", e);
        }
        
        // Step 2 : get the PO Person
        PersonDTO poPer = null;
        try {
            poPer = PoPaServiceBeanLookup.getPersonEntityService().
                getPerson(IiConverter.converToPoPersonIi(personPoIdentifer));
        } catch (NullifiedEntityException e) {
  //          Map m = e.getNullifiedEntities();
            LOG.error("This Person is no longer available instead use ");
            throw new PAException("This Person is no longer available instead use ", e);
         }
        
        
        // Step 2 : check if PO has crs correlation if not create one 
        ClinicalResearchStaffDTO crsDTO = new ClinicalResearchStaffDTO();
        List<ClinicalResearchStaffDTO> crsDTOs = null;
        crsDTO.setOrganizationIdentifier(IiConverter.converToPoOrganizationIi(orgPoIdentifier));
        crsDTO.setPersonIdentifier(IiConverter.converToPoPersonIi(personPoIdentifer));
        try {
            crsDTOs = PoPaServiceBeanLookup.getClinicalResearchStaffCorrelationService().search(crsDTO);
        } catch (NullifiedRoleException e) {
            LOG.error("check with scoot", e);
            // @todo: this should not happen, check with 
        }
        if (crsDTOs != null && crsDTOs.size() > 1) {
            throw new PAException("PO CRS Correlation should not have more than 1  ");
        }
        if (crsDTOs == null || crsDTOs.isEmpty()) {
            try {
                Ii ii = PoPaServiceBeanLookup.getClinicalResearchStaffCorrelationService().createCorrelation(crsDTO);
                crsDTO = PoPaServiceBeanLookup.getClinicalResearchStaffCorrelationService().getCorrelation(ii);
            } catch (NullifiedRoleException e) {
                LOG.error("Validation exception during get ClinicalResearchStaff " , e);
                throw new PAException("Validation exception during get ClinicalResearchStaff " , e);
            } catch (EntityValidationException e) {
                LOG.error("Validation exception during create ClinicalResearchStaff " , e);
                throw new PAException("Validation exception during create ClinicalResearchStaff " , e);
            } 
        } else {
            crsDTO = crsDTOs.get(0);
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
        
        // Step 6 : Check of PA has crs , if not create one
        ClinicalResearchStaff crs = new ClinicalResearchStaff();
        crs.setIdentifier(crsDTO.getIdentifier().getExtension());
        crs = getPAClinicalResearchStaff(crs);
        if (crs == null) {
            // create a new crs
            crs = new ClinicalResearchStaff();
            crs.setPerson(paPer);
            crs.setOrganization(paOrg);
            crs.setIdentifier(crsDTO.getIdentifier().getExtension());
            crs.setStatusCode(corrUtils.convertPORoleStatusToPARoleStatus(crsDTO.getStatus()));
            createPAClinicalResearchStaff(crs);
        }
        LOG.debug("Leaving createClinicalResearchStaffCorrelation");
        return crs.getId();
    }

    /**
     * 
     * @param crs crs
     * @return crs
     * @throws PAException
     */
    private ClinicalResearchStaff getPAClinicalResearchStaff(ClinicalResearchStaff crs) 
    throws PAException {
        if (crs == null) {
            LOG.error("Clinicial Research Staff cannot be null");
            throw new PAException("Clinicial Research Staff cannot be null");
        }
        if (crs.getPerson() != null && crs.getOrganization() == null  
            || crs.getPerson() == null && crs.getOrganization() != null) {
            LOG.error("Both person and organization should be specified and it cannot be either");
            throw new PAException("Both person and organization should be specified and it cannot be either");
            
        }
        ClinicalResearchStaff crsOut = null;
        Session session = null;
        List<ClinicalResearchStaff> queryList = new ArrayList<ClinicalResearchStaff>();
        StringBuffer hql = new StringBuffer();
        hql.append(" select crs from ClinicalResearchStaff crs  " 
                + "join crs.person as per "
                + "join crs.organization as org where 1 = 1 ");
        if (crs.getId() != null) {
            hql.append(" and crs.id = ").append(crs.getId());
        }
        if (crs.getPerson() != null && crs.getOrganization()  != null 
                && crs.getPerson().getId() != null && crs.getOrganization().getId() != null) {
            hql.append(" and per.id = ").append(crs.getPerson().getId());
            hql.append(" and org.id = ").append(crs.getOrganization().getId());
        }
        if (crs.getIdentifier() != null) {
            hql.append(" and crs.identifier = '").append(crs.getIdentifier()).append('\'');
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
        crsOut = queryList.get(0);
    }
    return crsOut;
    }
    
    /**
     * 
     * @param crs ClinicalResearchStaff 
     * @return ClinicalResearchStaff
     * @throws PAException PAException
     */
    private ClinicalResearchStaff createPAClinicalResearchStaff(ClinicalResearchStaff crs) throws PAException {
        if (crs == null) {
            LOG.error(" ClinicalResearchStaff should not be null ");
            throw new PAException(" ClinicalResearchStaff should not be null ");
        }     
        LOG.debug("Entering createClinicalResearchStaff ");
        Session session = null;
        
        try {
            session = HibernateUtil.getCurrentSession();
            session.save(crs);
        } catch (HibernateException hbe) {
            
            LOG.error(" Hibernate exception while createClinicalResearchStaff " , hbe);
            throw new PAException(" Hibernate exception while create ClinicalResearchStaff" , hbe);
        } finally {
            session.flush();
        }
        
        LOG.debug("Leaving create ClinicalResearchStaff ");
        return crs;
    }
    
}
