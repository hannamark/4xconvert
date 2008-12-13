package gov.nih.nci.pa.service.correlation;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.domain.ClinicalResearchStaff;
import gov.nih.nci.pa.domain.HealthCareProvider;
import gov.nih.nci.pa.domain.OrganizationalContact;
import gov.nih.nci.pa.domain.Person;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.HibernateUtil;
import gov.nih.nci.services.correlation.ClinicalResearchStaffDTO;
import gov.nih.nci.services.correlation.HealthCareProviderDTO;
import gov.nih.nci.services.correlation.NullifiedRoleException;
import gov.nih.nci.services.correlation.OrganizationalContactDTO;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.person.PersonDTO;

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
 * Syncrhonization service bean for Person and its structural roles.
 * 
 * @author Naveen Amiruddin
 * @since 07/07/2007 copyright NCI 2007. All rights reserved. This code may not
 *        be used without the express written permission of the copyright
 *        holder, NCI.
 */
@Stateless
@SuppressWarnings({ "PMD.TooManyMethods" })
public class PersonSynchronizationServiceBean implements PersonSynchronizationServiceRemote {

    private static final Logger LOG  = Logger.getLogger(PersonSynchronizationServiceBean.class);
    
    private SessionContext ejbContext;
    
    @Resource
    void setSessionContext(SessionContext ctx) {
    this.ejbContext = ctx;
    }
    
    /**
     * 
     * @param perIdentifer ii of Person
     * @throws PAException on error
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public void synchronizePerson(Ii perIdentifer) throws PAException {

        PersonDTO personDto = null;
        LOG.debug("Entering synchronizePerson");
        try {
            personDto = PoPaServiceBeanLookup.getPersonEntityService().getPerson(perIdentifer);
            updatePerson(personDto);
        } catch (NullifiedEntityException e) {
           LOG.error("This Organization is nullified " + perIdentifer.getExtension());
           nulifyPerson(perIdentifer);
        }
        LOG.debug("Leaving synchronizePerson");
    }

    /***
     * 
     * @param crsIdentifer po ClinicalResearchStaff identifier
     * @throws PAException on error
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public void synchronizeClinicalResearchStaff(Ii crsIdentifer) throws PAException {

        ClinicalResearchStaffDTO crsDto = null;
        LOG.debug("Entering synchronizeClinicalResearchStaff");
        try {
            crsDto = PoPaServiceBeanLookup.getClinicalResearchStaffCorrelationService().getCorrelation(crsIdentifer);
            updateClinicalResearchStaff(crsDto);
        } catch (NullifiedRoleException e) {
           LOG.error("This ClinicalResearchStaff is nullified " + crsIdentifer.getExtension());
           nulifyClinicalResearchStaff(crsIdentifer);
        }
        LOG.debug("Leaving synchronizeClinicalResearchStaff");
    }

    /***
     * 
     * @param hcpIdentifer po HealthCareProvider identifier
     * @throws PAException on error
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public void synchronizeHealthCareProvider(Ii hcpIdentifer) throws PAException {

        HealthCareProviderDTO hcpDto = null;
        LOG.debug("Entering synchronizeHealthCareProvider");
        try {
            hcpDto = PoPaServiceBeanLookup.getHealthCareProviderCorrelationService().getCorrelation(hcpIdentifer);
            updateHealthCareProvider(hcpDto);
        } catch (NullifiedRoleException e) {
           LOG.error("This HealthCareProvider is nullified " + hcpIdentifer.getExtension());
           nulifyHealthCareProvider(hcpIdentifer);
        }
        LOG.debug("Leaving synchronizeHealthCareProvider");
    }
    

    /***
     * OrganizationalContact.
     * @param ocIdentifer oc HealthCareProvider identifier
     * @throws PAException on error
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public void synchronizeOrganizationalContact(Ii ocIdentifer) throws PAException {

        OrganizationalContactDTO ocDto = null;
        LOG.debug("Entering synchronizeOrganizationalContact");
        try {
            ocDto = PoPaServiceBeanLookup.getOrganizationalContactCorrelationService().getCorrelation(ocIdentifer);
            updateOrganizationalContact(ocDto);
        } catch (NullifiedRoleException e) {
           LOG.error("This OrganizationalContact is nullified " + ocIdentifer.getExtension());
           nulifyOrganizationalContact(ocIdentifer);
        }
        LOG.debug("Leaving synchronizeOrganizationalContact");
    }
    
    private void nulifyPerson(Ii personIdentifer) throws PAException {
        LOG.debug("Entering nulifyPerson");
        CorrelationUtils cUtils = new CorrelationUtils();
        Person per = cUtils.getPAPersonByIndetifers(null, personIdentifer.getExtension());
        if (per != null) {
            // delete the Person and all of on delete cascade will delete the entire child
            Session session = null;
            try {
                session = HibernateUtil.getCurrentSession();
                Person person = (Person) session.get(Person.class, per.getId());
                session.delete(person);
                session.flush();
            } catch (HibernateException hbe) {
                throw new PAException("Hibernate exception while deleting Person for id = " + per.getId() , hbe);
            }
        }
        LOG.debug("Leaving nulifyPerson");

    }
    
    private void updatePerson(PersonDTO perDto) throws PAException {
        LOG.debug("Entering updatePerson");
        CorrelationUtils cUtils = new CorrelationUtils();
        Person per = cUtils.getPAPersonByIndetifers(null, perDto.getIdentifier().getExtension());
        if (per != null) {
            // update the organization
            Session session = null;
            try {
                session = HibernateUtil.getCurrentSession();
                Person person = (Person) session.get(Person.class, per.getId());
                per = cUtils.convertPOToPAPerson(perDto);
                person.setFirstName(per.getFirstName());
                person.setLastName(per.getLastName());
                person.setMiddleName(per.getMiddleName());
                person.setStatusCode(per.getStatusCode());
                person.setDateLastUpdated(new Timestamp((new Date()).getTime()));
                if (ejbContext != null) {
                    per.setUserLastUpdated(ejbContext.getCallerPrincipal().getName());
                }
                session.update(person);
                session.flush();
            } catch (HibernateException hbe) {
                throw new PAException("Hibernate exception while updating Person for id = " + per.getId() , hbe);
            }
        }
        LOG.debug("Leaving updateOrganization");
    }
    

    private void nulifyClinicalResearchStaff(Ii crsdentifer) throws PAException {
        LOG.debug("Entering nulifyClinicalResearchStaff");
        ClinicalResearchStaff crs = new ClinicalResearchStaff();
        CorrelationUtils cUtils = new CorrelationUtils();
        crs.setIdentifier(crsdentifer.getExtension());
        crs = cUtils.getPAClinicalResearchStaff(crs);
        if (crs != null) {
            // delete the hcf and all of on delete cascade will delete the entire child
            Session session = null;
            try {
                session = HibernateUtil.getCurrentSession();
                ClinicalResearchStaff clinicalResearchStaff = 
                        (ClinicalResearchStaff) session.get(ClinicalResearchStaff.class, crs.getId());
                session.delete(clinicalResearchStaff);
                session.flush();
            } catch (HibernateException hbe) {
                throw new PAException("Hibernate exception while deleting ClinicalResearchStaff for id = " 
                        + crs.getId() , hbe);
            }
        }
        LOG.debug("Leaving nulifyClinicalResearchStaff");
    }
    
    private void updateClinicalResearchStaff(ClinicalResearchStaffDTO crsDto) throws PAException {
        LOG.debug("Entering updateClinicalResearchStaff");
        CorrelationUtils cUtils = new CorrelationUtils();
        ClinicalResearchStaff crs = new ClinicalResearchStaff();
        crs.setIdentifier(crsDto.getIdentifier().getExtension());
        crs = cUtils.getPAClinicalResearchStaff(crs);
        if (crs != null) {
            // update the organization
            Session session = null;
            try {
                session = HibernateUtil.getCurrentSession();
                ClinicalResearchStaff clinicalResearchStaff = (ClinicalResearchStaff) 
                    session.get(ClinicalResearchStaff.class, crs.getId());
                clinicalResearchStaff.setStatusCode(cUtils.convertPORoleStatusToPARoleStatus(crsDto.getStatus()));
                clinicalResearchStaff.setDateLastUpdated(new Timestamp((new Date()).getTime()));
                if (ejbContext != null) {
                    clinicalResearchStaff.setUserLastUpdated(ejbContext.getCallerPrincipal().getName());
                }
                session.update(clinicalResearchStaff);
                session.flush();
            } catch (HibernateException hbe) {
                throw new PAException("Hibernate exception while updating ClinicalResearchStaff for id = " 
                        + crs.getId() , hbe);
            }
        }
        LOG.debug("Leaving updateClinicalResearchStaff");
    }
    
    private void nulifyHealthCareProvider(Ii hcpIdentifer) throws PAException {
        LOG.debug("Entering nulifyupdateHealthCareProvider");
        HealthCareProvider hcp = new HealthCareProvider();
        CorrelationUtils cUtils = new CorrelationUtils();
        hcp.setIdentifier(hcpIdentifer.getExtension());
        hcp = cUtils.getPAHealthCareProvider(hcp);
        if (hcp != null) {
            // delete the hcf and all of on delete cascade will delete the entire child
            Session session = null;
            try {
                session = HibernateUtil.getCurrentSession();
                HealthCareProvider healthCareProvider = 
                        (HealthCareProvider) session.get(HealthCareProvider.class, hcp.getId());
                session.delete(healthCareProvider);
                session.flush();
            } catch (HibernateException hbe) {
                throw new PAException("Hibernate exception while deleting HealthCareProvider for id = " 
                        + hcp.getId() , hbe);
            }
        }
        LOG.debug("Leaving nulifyHealthCareProvider");
    }
    
    private void updateHealthCareProvider(HealthCareProviderDTO hcpDto) throws PAException {
        LOG.debug("Entering updateHealthCareProvider");
        CorrelationUtils cUtils = new CorrelationUtils();
        HealthCareProvider hcp = new HealthCareProvider();
        hcp.setIdentifier(hcpDto.getIdentifier().getExtension());
        hcp = cUtils.getPAHealthCareProvider(hcp);
        if (hcp != null) {
            // update the organization
            Session session = null;
            try {
                session = HibernateUtil.getCurrentSession();
                HealthCareProvider healthCareProvider = (HealthCareProvider) 
                    session.get(HealthCareProvider.class, hcp.getId());
                healthCareProvider.setStatusCode(cUtils.convertPORoleStatusToPARoleStatus(hcpDto.getStatus()));
                healthCareProvider.setDateLastUpdated(new Timestamp((new Date()).getTime()));
                if (ejbContext != null) {
                    healthCareProvider.setUserLastUpdated(ejbContext.getCallerPrincipal().getName());
                }
                session.update(healthCareProvider);
                session.flush();
            } catch (HibernateException hbe) {
                throw new PAException("Hibernate exception while updating ClinicalResearchStaff for id = " 
                        + hcp.getId() , hbe);
            }
        }
        LOG.debug("Leaving updateClinicalResearchStaff");
    }


    private void nulifyOrganizationalContact(Ii ocIdentifer) throws PAException {
        LOG.debug("Entering nulifyOrganizationalContact");
        OrganizationalContact oc = new OrganizationalContact();
        CorrelationUtils cUtils = new CorrelationUtils();
        oc.setIdentifier(ocIdentifer.getExtension());
        oc = cUtils.getPAOrganizationalContact(oc);
        if (oc != null) {
            // delete the hcf and all of on delete cascade will delete the entire child
            Session session = null;
            try {
                session = HibernateUtil.getCurrentSession();
                OrganizationalContact organizationalContact = 
                        (OrganizationalContact) session.get(OrganizationalContact.class, oc.getId());
                session.delete(organizationalContact);
                session.flush();
            } catch (HibernateException hbe) {
                throw new PAException("Hibernate exception while deleting OrganizationalContact for id = " 
                        + oc.getId() , hbe);
            }
        }
        LOG.debug("Leaving nulifyOrganizationalContact");
    }
    
    private void updateOrganizationalContact(OrganizationalContactDTO ocDto) throws PAException {
        LOG.debug("Entering updateOrganizationalContact");
        CorrelationUtils cUtils = new CorrelationUtils();
        OrganizationalContact oc = new OrganizationalContact();
        oc.setIdentifier(ocDto.getIdentifier().getExtension());
        oc = cUtils.getPAOrganizationalContact(oc);
        if (oc != null) {
            // update the organization
            Session session = null;
            try {
                session = HibernateUtil.getCurrentSession();
                OrganizationalContact organizationalContact = (OrganizationalContact) 
                    session.get(OrganizationalContact.class, oc.getId());
                organizationalContact.setStatusCode(cUtils.convertPORoleStatusToPARoleStatus(ocDto.getStatus()));
                organizationalContact.setDateLastUpdated(new Timestamp((new Date()).getTime()));
                if (ejbContext != null) {
                    organizationalContact.setUserLastUpdated(ejbContext.getCallerPrincipal().getName());
                }
                session.update(organizationalContact);
                session.flush();
            } catch (HibernateException hbe) {
                throw new PAException("Hibernate exception while updating OrganizationalContact for id = " 
                        + oc.getId() , hbe);
            }
        }
        LOG.debug("Leaving updateOrganizationalContact");
    }
    
}
