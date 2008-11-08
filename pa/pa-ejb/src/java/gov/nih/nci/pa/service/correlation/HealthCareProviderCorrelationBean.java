package gov.nih.nci.pa.service.correlation;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.domain.HealthCareProvider;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.Person;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.HibernateUtil;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.services.correlation.HealthCareProviderDTO;
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
 * A Service bean for maintaining A person who directly or indirectly administers interventions that are designed to. 
 * the physical or emotional status of another person. A person licensed, certified or otherwise authorized or 
 * permitted by law to administer health care in the ordinary course of business or practice of a profession, 
 * including a health care facility. 
 * 
 * @author Naveen Amiruddin
 * @since 04/11/2008
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
@SuppressWarnings({ "PMD.AvoidDuplicateLiterals", "PMD.ExcessiveMethodLength",
    "PMD.CyclomaticComplexity", "PMD.ExcessiveClassLength", "PMD.NPathComplexity" })
    
public class HealthCareProviderCorrelationBean {

    private static final Logger LOG  = Logger.getLogger(HealthCareProviderCorrelationBean.class);
    /**
     * This method assumes Organization and Person record exists in PO.
     * @param orgPoIdentifier po primary org id
     * @param personPoIdentifer po primary person id
     * @return long id 
     * @throws PAException pe 
     */
    public Long createHealthCareProviderCorrelationBeans(String orgPoIdentifier, 
                                           String personPoIdentifer) 
    throws PAException {
        LOG.debug("Entering createHealthCareProviderCorrelationBean");
        
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
            poOrg = PoServiceBeanLookup.getOrganizationEntityService().
                getOrganization(IiConverter.converToPoOrganizationIi(orgPoIdentifier));
        } catch (NullifiedEntityException e) {
//            Map m = e.getNullifiedEntities();
           LOG.error("This Organization is no longer available instead use " , e);
           throw new PAException("This Organization is no longer available instead use " , e);
        }
        
        // Step 2 : get the PO Person
        PersonDTO poPer = null;
        try {
            poPer = PoServiceBeanLookup.getPersonEntityService().
                getPerson(IiConverter.converToPoPersonIi(personPoIdentifer));
        } catch (NullifiedEntityException e) {
//            Map m = e.getNullifiedEntities();
            LOG.error("This Person is no longer available instead use " , e);
            throw new PAException("This Person is no longer available instead use " , e);
         }
        
        
        // Step 2 : check if PO has hcp correlation if not create one 
        HealthCareProviderDTO hcpDTO = new HealthCareProviderDTO();
        List<HealthCareProviderDTO> hcpDTOs = null;
        hcpDTO.setOrganizationIdentifier(IiConverter.converToPoOrganizationIi(orgPoIdentifier));
        hcpDTO.setPersonIdentifier(IiConverter.converToPoPersonIi(personPoIdentifer));
        try {
            hcpDTOs = PoServiceBeanLookup.getHealthCareProviderCorrelationService().search(hcpDTO);
        } catch (NullifiedRoleException e) {
            LOG.error("check with scott");
            // @todo: this should not happen, check with 
        }
        if (hcpDTOs != null && hcpDTOs.size() > 1) {
            LOG.error("PO HealthCareProvider Correlation should not have more than 1 role for a given org and person ");
            throw new PAException(
                    "PO HealthCareProvider Correlation should not have more than 1 role for a given org and person ");
        }
        if (hcpDTOs == null || hcpDTOs.isEmpty()) {
            try {
                Ii ii = PoServiceBeanLookup.getHealthCareProviderCorrelationService().createCorrelation(hcpDTO);
                hcpDTO = PoServiceBeanLookup.getHealthCareProviderCorrelationService().getCorrelation(ii);
            } catch (NullifiedRoleException e) {
                LOG.error("Validation exception during for HealthCareProvider " , e);
                throw new PAException("Validation exception during get HealthCareProvider " , e);
            } catch (EntityValidationException e) {
                LOG.error("Validation exception during create HealthCareProvider " , e);
                throw new PAException("Validation exception during create HealthCareProvider " , e);
            } 
        } else {
            hcpDTO = hcpDTOs.get(0);
        }

        // Step 3 : check for pa org, if not create one
        Organization paOrg = corrUtils.getPAOrganizationByIndetifers(null , orgPoIdentifier);
        if (paOrg == null) {
            paOrg = corrUtils.createPAOrganization(poOrg);
        }
        // Step 4 : check for pa person, if not create one
        Person paPer = corrUtils.getPAPersonByIndetifers(null , personPoIdentifer);
        if (paPer == null) {
            paPer = corrUtils.createPAPerson(poPer);
        }
        
        // Step 6 : Check of PA has hcp , if not create one
        HealthCareProvider hcp = new HealthCareProvider();
        hcp.setIdentifier(hcpDTO.getIdentifier().getExtension());
        hcp = getPAHealthCareProvider(hcp);
        if (hcp == null) {
            // create a new crs
            hcp = new HealthCareProvider();
            hcp.setPerson(paPer);
            hcp.setOrganization(paOrg);
            hcp.setIdentifier(hcpDTO.getIdentifier().getExtension());
            hcp.setStatusCode(corrUtils.convertPORoleStatusToPARoleStatus(hcpDTO.getStatus()));
            createPAHealthCareProvider(hcp);
        }
        LOG.debug("Leaving createClinicalResearchStaffCorrelation");
        return hcp.getId();
    }

    /**
     * 
     * @param hcp HealthCareProvider
     * @return  HealthCareProvider
     * @throws PAException
     */
    private HealthCareProvider getPAHealthCareProvider(HealthCareProvider hcp) 
    throws PAException {
        if (hcp == null) {
            LOG.error("HealthCareProvider  cannot be null");
            throw new PAException("HealthCareProvider cannot be null");
        }
        if (hcp.getPerson() != null && hcp.getOrganization() == null 
                || hcp.getPerson() == null && hcp.getOrganization() != null) {
            LOG.error("Both person and organization should be specified and it cannot be either");
            throw new PAException("Both person and organization should be specified and it cannot be either");
        }
        HealthCareProvider hcpOut = null;
        Session session = null;
        List<HealthCareProvider> queryList = new ArrayList<HealthCareProvider>();
        StringBuffer hql = new StringBuffer();
        hql.append(" select hcp from HealthCareProvider hcp  " 
                + "join hcp.person as per  "
                + "join hcp.organization as org where 1 = 1 ");
        if (hcp.getId() != null) {
            hql.append(" and hcp.id = ").append(hcp.getId());
        }
        if (hcp.getPerson() != null && hcp.getPerson().getId() != null 
                && hcp.getOrganization() != null && hcp.getOrganization().getId() != null) {
            hql.append(" and per.id = ").append(hcp.getPerson().getId());
            hql.append(" and org.id = ").append(hcp.getOrganization().getId());
        }
        if (hcp.getIdentifier() != null) {
            hql.append(" and hcp.identifier = '").append(hcp.getIdentifier()).append('\'');
        }
        try {
            session = HibernateUtil.getCurrentSession();
            Query query = null;
        
        query = session.createQuery(hql.toString());
        queryList = query.list();
        
        if (queryList.size() > 1) {
            LOG.error("HealthCareProvider should not be more than 1 for any given criteria");
            throw new PAException("HealthCareProvider should not be more than 1 for any given criteria");
            
        }
    }  catch (HibernateException hbe) {
        LOG.error(" Error while retrieving HealthCareProvider" , hbe);
        throw new PAException(" Error while retrieving HealthCareProvider" , hbe);
    } finally {
        session.flush();
    }
    
    if (!queryList.isEmpty()) {
        hcpOut = queryList.get(0);
    }
    return hcpOut;
    }
    
    /**
     * 
     * @param hcp HealthCareProvider
     * @return HealthCareProvider
     * @throws PAException PAException
     */
    private HealthCareProvider createPAHealthCareProvider(HealthCareProvider hcp) throws PAException {
        if (hcp == null) {
            LOG.error(" HealthCareProvider should not be null ");
            throw new PAException(" HealthCareProvider should not be null ");
        }     
        LOG.debug("Entering HealthCareProvider ");
        Session session = null;
        
        try {
            session = HibernateUtil.getCurrentSession();
            session.save(hcp);
        } catch (HibernateException hbe) {
            
            LOG.error(" Hibernate exception while creating HealthCareProvider " , hbe);
            throw new PAException(" Hibernate exception while creating HealthCareProvider" , hbe);
        } finally {
            session.flush();
        }
        
        LOG.debug("Leaving create HealthCareProvider ");
        return hcp;
    }
    
}
