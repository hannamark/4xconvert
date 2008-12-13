package gov.nih.nci.pa.service.correlation;

import gov.nih.nci.coppa.iso.DSet;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.iso.Tel;
import gov.nih.nci.coppa.iso.TelPhone;
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

import java.net.URI;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
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
     * @return id id
     * @throws PAException pe 
     */
    public Long createOrganizationalContactCorrelations(String orgPoIdentifier, 
                                           String personPoIdentifer) throws PAException { 
        
        LOG.debug("Entering createClinicalResearchStaffCorrelation");
        
        CorrelationUtils corrUtils = new CorrelationUtils();
        if (orgPoIdentifier == null) {
            throw new PAException(" Organization PO Identifier is null");
        }
        if (personPoIdentifer == null) {
            throw new PAException(" Person PO Identifier is null");
        }

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
        
        
        // Step 2 : check if PO has oc correlation if not create one 
        OrganizationalContactDTO ocDTO = new OrganizationalContactDTO();
        List<OrganizationalContactDTO> ocDTOs = null;
        ocDTO.setScoperIdentifier(IiConverter.converToPoOrganizationIi(orgPoIdentifier));
        ocDTO.setPlayerIdentifier(IiConverter.converToPoPersonIi(personPoIdentifer));
        ocDTOs = PoPaServiceBeanLookup.getOrganizationalContactCorrelationService().search(ocDTO);
        if (ocDTOs != null && ocDTOs.size() > 1) {
            throw new PAException("PO oc Correlation should not have more than 1  ");
        }
        if (ocDTOs == null || ocDTOs.isEmpty()) {
            try {
                //================================================
                // Create dummy phone number, required by PO-0.6
                // TODO remove this code once PO allows nulls
                TelPhone o = new TelPhone();
                o.setValue(URI.create("tel:111-111-1111"));
                Set<Tel> item = new HashSet<Tel>();
                item.add(o);
                DSet<Tel> telecomAddress = new DSet<Tel>();
                telecomAddress.setItem(item);
                ocDTO.setTelecomAddress(telecomAddress);
                //================================================
                
                Ii ii = PoPaServiceBeanLookup.getOrganizationalContactCorrelationService().createCorrelation(ocDTO);
                ocDTO = PoPaServiceBeanLookup.getOrganizationalContactCorrelationService().getCorrelation(ii);
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
            paOrg = corrUtils.createPAOrganization(poOrg);
        }
        // Step 4 : check for pa person, if not create one
        Person paPer = corrUtils.getPAPersonByIndetifers(null , personPoIdentifer);
        if (paPer == null) {
            paPer = corrUtils.createPAPerson(poPer);
        }
        
        // Step 6 : Check of PA has oc , if not create one
        OrganizationalContact oc = new OrganizationalContact();
        oc.setIdentifier(ocDTO.getIdentifier().getExtension());
        oc = corrUtils.getPAOrganizationalContact(oc);
        if (oc == null) {
            // create a new oc
            oc = new OrganizationalContact();
            oc.setPerson(paPer);
            oc.setOrganization(paOrg);
            oc.setIdentifier(ocDTO.getIdentifier().getExtension());
            oc.setStatusCode(corrUtils.convertPORoleStatusToPARoleStatus(ocDTO.getStatus()));
            createPAOrganizationalContact(oc);
        }
        LOG.debug("Leaving createOrganizationalContactCorrelation");
        return oc.getId();
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
