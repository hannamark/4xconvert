package gov.nih.nci.pa.service.correlation;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.domain.HealthCareFacility;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.OversightCommittee;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.HibernateUtil;
import gov.nih.nci.services.correlation.HealthCareFacilityDTO;
import gov.nih.nci.services.correlation.NullifiedRoleException;
import gov.nih.nci.services.correlation.OversightCommitteeDTO;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.organization.OrganizationDTO;

import java.sql.Timestamp;
import java.util.Date;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 * Syncrhonization service bean for organization and its structural roles.
 * 
 * @author Naveen Amiruddin
 * @since 07/07/2007 copyright NCI 2007. All rights reserved. This code may not
 *        be used without the express written permission of the copyright
 *        holder, NCI.
 */
@Stateless
public class OrganizationSynchronizationServiceBean implements OrganizationSynchronizationServiceRemote {

    private static final Logger LOG  = Logger.getLogger(OrganizationSynchronizationServiceBean.class);
    
    private SessionContext ejbContext;
    
    @Resource
    void setSessionContext(SessionContext ctx) {
    this.ejbContext = ctx;
    }
    
    /**
     * 
     * @param orgIdentifer ii of organization
     * @throws PAException on error
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public void synchronizeOrganization(Ii orgIdentifer) throws PAException {

        OrganizationDTO orgDto = null;
        LOG.debug("Entering synchronizeOrganization");
        try {
            orgDto = PoPaServiceBeanLookup.getOrganizationEntityService().getOrganization(orgIdentifer);
            updateOrganization(orgDto);
        } catch (NullifiedEntityException e) {
           LOG.error("This Organization is nullified " + orgIdentifer.getExtension());
           nulifyOrganization(orgIdentifer);
        }
        LOG.debug("Leaving synchronizeOrganization");
    }
    
    /***
     * 
     * @param oscIdentifer po osc identifier
     * @throws PAException on error
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public void synchronizeOversightCommittee(Ii oscIdentifer) throws PAException {

        OversightCommitteeDTO oscDto = null;
        LOG.debug("Entering synchronizeOversightCommittee");
        try {
            oscDto = PoPaServiceBeanLookup.getOversightCommitteeCorrelationService().getCorrelation(oscIdentifer);
            updateOversightCommittee(oscDto);
        } catch (NullifiedRoleException e) {
           LOG.error("This OversightCommittee is nullified " + oscIdentifer.getExtension());
           nulifyOversightCommittee(oscIdentifer);
        }
        LOG.debug("Leaving synchronizeOversightCommittee");
    }
    
    
    /***
     * 
     * @param hcfIdentifer po hcf identifier
     * @throws PAException on error
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public void synchronizeHealthCareFacility(Ii hcfIdentifer) throws PAException {

        HealthCareFacilityDTO hcfDto = null;
        LOG.debug("Entering synchronizeHealthCareFacility");
        try {
            hcfDto = PoPaServiceBeanLookup.getHealthCareFacilityCorrelationService().getCorrelation(hcfIdentifer);
            updateHealthCareFacility(hcfDto);
        } catch (NullifiedRoleException e) {
           LOG.error("This HealthCareFacility is nullified " + hcfIdentifer.getExtension());
           nulifyHealthCareFacility(hcfIdentifer);
        }
        LOG.debug("Leaving synchronizeOrganization");
    }

    private void nulifyOrganization(Ii organizationIdentifer) throws PAException {
        LOG.debug("Entering nulifyOrganization");
        CorrelationUtils cUtils = new CorrelationUtils();
        Organization org = cUtils.getPAOrganizationByIndetifers(null, organizationIdentifer.getExtension());
        if (org != null) {
            // delete the organization and all of on delete cascade will delete the entire child
            Session session = null;
            try {
                session = HibernateUtil.getCurrentSession();
                Organization organization = (Organization) session.get(Organization.class, org.getId());
                session.delete(organization);
                session.flush();
            } catch (HibernateException hbe) {
                throw new PAException("Hibernate exception while deleting Organization for id = " + org.getId() , hbe);
            }
        }
        LOG.debug("Leaving nulifyOrganization");

    }
    
    private void updateOrganization(OrganizationDTO orgDto) throws PAException {
        LOG.debug("Entering updateOrganization");
        CorrelationUtils cUtils = new CorrelationUtils();
        Organization org = cUtils.getPAOrganizationByIndetifers(null, orgDto.getIdentifier().getExtension());
        if (org != null) {
            // update the organization
            Session session = null;
            try {
                session = HibernateUtil.getCurrentSession();
                Organization organization = (Organization) session.get(Organization.class, org.getId());
                org = cUtils.convertPOToPAOrganization(orgDto);
                organization.setCity(org.getCity());
                organization.setCountryName(org.getCountryName());
                organization.setName(org.getName());
                organization.setPostalCode(org.getPostalCode());
                organization.setState(org.getState());
                organization.setStatusCode(org.getStatusCode());
                organization.setDateLastUpdated(new Timestamp((new Date()).getTime()));
                if (ejbContext != null) {
                    organization.setUserLastUpdated(ejbContext.getCallerPrincipal().getName());
                }
                session.update(organization);
                session.flush();
            } catch (HibernateException hbe) {
                throw new PAException("Hibernate exception while deleting Organization for id = " + org.getId() , hbe);
            }
        }
        LOG.debug("Leaving updateOrganization");
    }

    private void nulifyHealthCareFacility(Ii hcIdentifer) throws PAException {
        LOG.debug("Entering nulifyHealthCareFacility");
        HealthCareFacility hcf = new HealthCareFacility();
        CorrelationUtils cUtils = new CorrelationUtils();
        hcf.setIdentifier(hcIdentifer.getExtension());
        hcf = cUtils.getPAHealthCareFacility(hcf);
        if (hcf != null) {
            // delete the hcf and all of on delete cascade will delete the entire child
            Session session = null;
            try {
                session = HibernateUtil.getCurrentSession();
                HealthCareFacility healthCareFacility = 
                        (HealthCareFacility) session.get(HealthCareFacility.class, hcf.getId());
                session.delete(healthCareFacility);
                session.flush();
            } catch (HibernateException hbe) {
                throw new PAException("Hibernate exception while deleting healthCareFacility for id = " 
                        + hcf.getId() , hbe);
            }
        }
        LOG.debug("Leaving nulifyHealthCareFacility");
    }
    
    private void updateHealthCareFacility(HealthCareFacilityDTO hcfDto) throws PAException {
        LOG.debug("Entering updateHealthCareFacility");
        CorrelationUtils cUtils = new CorrelationUtils();
        HealthCareFacility hcf = new HealthCareFacility();
        hcf.setIdentifier(hcfDto.getIdentifier().getExtension());
        hcf = cUtils.getPAHealthCareFacility(hcf);
        if (hcf != null) {
            // update the organization
            Session session = null;
            try {
                session = HibernateUtil.getCurrentSession();
                HealthCareFacility hcFacility = (HealthCareFacility) session.get(HealthCareFacility.class, hcf.getId());
                hcFacility.setStatusCode(cUtils.convertPORoleStatusToPARoleStatus(hcfDto.getStatus()));
                hcFacility.setDateLastUpdated(new Timestamp((new Date()).getTime()));
                if (ejbContext != null) {
                    hcFacility.setUserLastUpdated(ejbContext.getCallerPrincipal().getName());
                }
                session.update(hcFacility);
                session.flush();
            } catch (HibernateException hbe) {
                throw new PAException("Hibernate exception while updating HealthCareFacility for id = " 
                        + hcf.getId() , hbe);
            }
        }
        LOG.debug("Leaving updateHealthCareFacility");
    }


    private void nulifyOversightCommittee(Ii oscIdentifer) throws PAException {
        LOG.debug("Entering nulifyOversightCommittee");
        OversightCommittee osc = new OversightCommittee();
        CorrelationUtils cUtils = new CorrelationUtils();
        osc.setIdentifier(oscIdentifer.getExtension());
        osc = cUtils.getPAOversightCommittee(osc);
        if (osc != null) {
            // delete the hcf and all of on delete cascade will delete the entire child
            Session session = null;
            try {
                session = HibernateUtil.getCurrentSession();
                OversightCommittee oversightCommittee = 
                        (OversightCommittee) session.get(OversightCommittee.class, osc.getId());
                session.delete(oversightCommittee);
                session.flush();
            } catch (HibernateException hbe) {
                throw new PAException("Hibernate exception while deleting OversightCommittee for id = " 
                        + osc.getId() , hbe);
            }
        }
        LOG.debug("Leaving nulifyHealthCareFacility");
    }

    private void updateOversightCommittee(OversightCommitteeDTO oscDto) throws PAException {
        LOG.debug("Entering updateOversightCommittee");
        CorrelationUtils cUtils = new CorrelationUtils();
        OversightCommittee osc = new OversightCommittee();
        osc.setIdentifier(oscDto.getIdentifier().getExtension());
        osc = cUtils.getPAOversightCommittee(osc);
        if (osc != null) {
            // update the organization
            Session session = null;
            try {
                session = HibernateUtil.getCurrentSession();
                OversightCommittee osComittee = (OversightCommittee) session.get(OversightCommittee.class, osc.getId());
                osComittee.setStatusCode(cUtils.convertPORoleStatusToPARoleStatus(oscDto.getStatus()));
                osComittee.setDateLastUpdated(new Timestamp((new Date()).getTime()));
                if (ejbContext != null) {
                    osComittee.setUserLastUpdated(ejbContext.getCallerPrincipal().getName());
                }
                session.update(osComittee);
                session.flush();
            } catch (HibernateException hbe) {
                throw new PAException("Hibernate exception while updating OversightCommittee for id = " 
                        + osc.getId() , hbe);
            }
        }
        LOG.debug("Leaving updateOversightCommittee");
    }

}
