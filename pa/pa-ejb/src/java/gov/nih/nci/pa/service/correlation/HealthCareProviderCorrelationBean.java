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

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
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
            poOrg = PoPaServiceBeanLookup.getOrganizationEntityService().
                getOrganization(IiConverter.converToPoOrganizationIi(orgPoIdentifier));
        } catch (NullifiedEntityException e) {
//            Map m = e.getNullifiedEntities();
           LOG.error("This Organization is no longer available instead use " , e);
           throw new PAException("This Organization is no longer available instead use " , e);
        }
        
        // Step 2 : get the PO Person
        PersonDTO poPer = null;
        try {
            poPer = PoPaServiceBeanLookup.getPersonEntityService().
                getPerson(IiConverter.converToPoPersonIi(personPoIdentifer));
        } catch (NullifiedEntityException e) {
//            Map m = e.getNullifiedEntities();
            LOG.error("This Person is no longer available instead use " , e);
            throw new PAException("This Person is no longer available instead use " , e);
         }
        
        
        // Step 2 : check if PO has hcp correlation if not create one 
        HealthCareProviderDTO hcpDTO = new HealthCareProviderDTO();
        List<HealthCareProviderDTO> hcpDTOs = null;
        hcpDTO.setScoperIdentifier(IiConverter.converToPoOrganizationIi(orgPoIdentifier));
        hcpDTO.setPlayerIdentifier(IiConverter.converToPoPersonIi(personPoIdentifer));
        hcpDTOs = PoPaServiceBeanLookup.getHealthCareProviderCorrelationService().search(hcpDTO);
        if (hcpDTOs != null && hcpDTOs.size() > 1) {
            LOG.error("PO HealthCareProvider Correlation should not have more than 1 role for a given org and person ");
            throw new PAException(
                    "PO HealthCareProvider Correlation should not have more than 1 role for a given org and person ");
        }
        if (hcpDTOs == null || hcpDTOs.isEmpty()) {
            try {
                Ii ii = PoPaServiceBeanLookup.getHealthCareProviderCorrelationService().createCorrelation(hcpDTO);
                hcpDTO = PoPaServiceBeanLookup.getHealthCareProviderCorrelationService().getCorrelation(ii);
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
        hcp = corrUtils.getPAHealthCareProvider(hcp);
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
