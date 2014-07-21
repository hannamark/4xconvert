package gov.nih.nci.webservices.rest.client.util;

import gov.nih.nci.services.correlation.ClinicalResearchStaffCorrelationServiceRemote;
import gov.nih.nci.services.correlation.HealthCareFacilityCorrelationServiceRemote;
import gov.nih.nci.services.correlation.HealthCareProviderCorrelationServiceRemote;
import gov.nih.nci.services.correlation.IdentifiedOrganizationCorrelationServiceRemote;
import gov.nih.nci.services.correlation.IdentifiedPersonCorrelationServiceRemote;
import gov.nih.nci.services.correlation.OrganizationalContactCorrelationServiceRemote;
import gov.nih.nci.services.correlation.OversightCommitteeCorrelationServiceRemote;
import gov.nih.nci.services.correlation.ResearchOrganizationCorrelationServiceRemote;
import gov.nih.nci.services.family.FamilyServiceRemote;
import gov.nih.nci.services.organization.OrganizationEntityServiceRemote;
import gov.nih.nci.services.person.PersonEntityServiceRemote;
import gov.nih.nci.webservices.rest.client.FamilyRestServiceClient;
import gov.nih.nci.webservices.rest.client.FamilyRestServiceClientImpl;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * A class for all Po look-ups.
 * 
 * @author vinodh.rc
 * 
 */
public class PoRestServiceLocator implements PoServiceLocator {

    private static final Logger LOG = Logger
            .getLogger(PoRestServiceLocator.class);
    /**
     * ApplicationContext instance
     */
    private static ApplicationContext ctx;

    /**
     * {@inheritDoc}
     */
    @Override
    public FamilyRestServiceClient getFamilyService() {
        return lookup(FamilyRestServiceClientImpl.class);
    }

    /**
     * Returns the spring bean for the className
     * 
     * @param className
     * @return T instance of the class represented by className
     */
    private <T> T lookup(Class<T> className) {
        ApplicationContext context = getContext();
        T client = (T) context.getBean(className);

        return client;
    }

    /**
     * @return the context - ApplicationContext
     */
    public static synchronized ApplicationContext getContext() {
        if (ctx == null) {
            try {
                ctx = new ClassPathXmlApplicationContext(
                        "classpath:rest-client-context.xml");
            } catch (BeansException e) {
                LOG.error(e);
            }
        }
        return ctx;
    }

    @Override
    public OrganizationEntityServiceRemote getOrganizationEntityService() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public HealthCareFacilityCorrelationServiceRemote getHealthCareFacilityCorrelationService() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ResearchOrganizationCorrelationServiceRemote getResearchOrganizationCorrelationService() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public OversightCommitteeCorrelationServiceRemote getOversightCommitteeCorrelationService() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public PersonEntityServiceRemote getPersonEntityService() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ClinicalResearchStaffCorrelationServiceRemote getClinicalResearchStaffCorrelationService() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public HealthCareProviderCorrelationServiceRemote getHealthCareProviderCorrelationService() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public OrganizationalContactCorrelationServiceRemote getOrganizationalContactCorrelationService() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IdentifiedOrganizationCorrelationServiceRemote getIdentifiedOrganizationEntityService() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IdentifiedPersonCorrelationServiceRemote getIdentifiedPersonEntityService() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public FamilyServiceRemote getFamilyServiceRemote() {
        // TODO Auto-generated method stub
        return null;
    }

}
