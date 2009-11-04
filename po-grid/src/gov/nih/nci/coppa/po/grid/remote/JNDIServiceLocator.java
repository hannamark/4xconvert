package gov.nih.nci.coppa.po.grid.remote;

import gov.nih.nci.coppa.services.grid.remote.InvokeCoppaServiceException;
import gov.nih.nci.services.BusinessServiceRemote;
import gov.nih.nci.services.CorrelationService;
import gov.nih.nci.services.EntityNodeDto;
import gov.nih.nci.services.PoDto;
import gov.nih.nci.services.correlation.ClinicalResearchStaffCorrelationServiceRemote;
import gov.nih.nci.services.correlation.ClinicalResearchStaffDTO;
import gov.nih.nci.services.correlation.CorrelationNodeDTO;
import gov.nih.nci.services.correlation.HealthCareFacilityCorrelationServiceRemote;
import gov.nih.nci.services.correlation.HealthCareFacilityDTO;
import gov.nih.nci.services.correlation.HealthCareProviderCorrelationServiceRemote;
import gov.nih.nci.services.correlation.HealthCareProviderDTO;
import gov.nih.nci.services.correlation.IdentifiedOrganizationCorrelationServiceRemote;
import gov.nih.nci.services.correlation.IdentifiedOrganizationDTO;
import gov.nih.nci.services.correlation.IdentifiedPersonCorrelationServiceRemote;
import gov.nih.nci.services.correlation.IdentifiedPersonDTO;
import gov.nih.nci.services.correlation.OrganizationalContactCorrelationServiceRemote;
import gov.nih.nci.services.correlation.OrganizationalContactDTO;
import gov.nih.nci.services.correlation.OversightCommitteeCorrelationServiceRemote;
import gov.nih.nci.services.correlation.OversightCommitteeDTO;
import gov.nih.nci.services.correlation.PatientCorrelationServiceRemote;
import gov.nih.nci.services.correlation.PatientDTO;
import gov.nih.nci.services.correlation.ResearchOrganizationCorrelationServiceRemote;
import gov.nih.nci.services.correlation.ResearchOrganizationDTO;
import gov.nih.nci.services.organization.OrganizationEntityServiceRemote;
import gov.nih.nci.services.person.PersonEntityServiceRemote;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.naming.CommunicationException;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Service locator that uses JNDI to look up services.
 */
public final class JNDIServiceLocator implements ServiceLocator {
    private static final Logger LOG = LogManager.getLogger(JNDIServiceLocator.class);
    private static final int MAX_RETRIES = 2;
    private static JNDIServiceLocator instance = new JNDIServiceLocator();
    private InitialContext context;
    private static Map<Class<?>, Method> values = new HashMap<Class<?>, Method>();

    static {
        try {
            /*
             * Cache the Method instead of the actual Remote instance as it would be very difficult to handle
             * NamingException, etc..
             */
            values.put(ClinicalResearchStaffDTO.class, getInstance().getClass().getMethod(
                    "getClinicalResearchStaffService"));
            values.put(HealthCareFacilityDTO.class, getInstance().getClass().getMethod("getHealthCareFacilityService"));
            values.put(HealthCareProviderDTO.class, getInstance().getClass().getMethod("getHealthCareProviderService"));
            values.put(IdentifiedOrganizationDTO.class, getInstance().getClass().getMethod(
                    "getIdentifiedOrganizationService"));
            values.put(IdentifiedPersonDTO.class, getInstance().getClass().getMethod("getIdentifiedPersonService"));
            values.put(ResearchOrganizationDTO.class, getInstance().getClass().getMethod(
                    "getResearchOrganizationService"));
            values.put(OversightCommitteeDTO.class, getInstance().getClass().getMethod("getOversightCommitteeService"));
            values.put(OrganizationalContactDTO.class, getInstance().getClass().getMethod(
                    "getOrganizationalContactService"));
            values.put(PatientDTO.class, getInstance().getClass().getMethod(
                "getPatientService"));
            values.put(EntityNodeDto.class, getInstance().getClass().getMethod(
                "getBusinessService"));
            values.put(CorrelationNodeDTO.class, getInstance().getClass().getMethod(
                "getBusinessService"));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    private JNDIServiceLocator() {
        try {
            Properties props = new Properties();
            props.load(JNDIServiceLocator.class.getClassLoader().getResourceAsStream("jndi.properties"));
            context = new InitialContext(props);
        } catch (Exception e) {
            LOG.error("Unable to load jndi properties.", e);
            throw new RuntimeException("Unable to load jndi properties.", e);
        }
    }

    /**
     * Get the singleton instance of the service locator.
     * @return the singleton locator
     */
    public static JNDIServiceLocator getInstance() {
        return instance;
    }

    private Object lookup(String name) throws NamingException {
        Object object = null;
        int i = 0;
        while (object == null && i < MAX_RETRIES) {
             try {
                 object = context.lookup(name);
             } catch (CommunicationException com) {
                 instance = new JNDIServiceLocator();
             }
             i++;
        }

        return object;
    }

    /**
     * {@inheritDoc}
     */
    public PersonEntityServiceRemote getPersonService() throws NamingException {
        PersonEntityServiceRemote object = (PersonEntityServiceRemote) lookup("po/PersonEntityServiceBean/remote");
        return object;
    }

    /**
     * {@inheritDoc}
     */
    public OrganizationEntityServiceRemote getOrganizationService() throws NamingException {
        OrganizationEntityServiceRemote object = (OrganizationEntityServiceRemote)
            lookup("po/OrganizationEntityServiceBean/remote");
        return object;
    }

    /**
     * {@inheritDoc}
     */
    public HealthCareFacilityCorrelationServiceRemote getHealthCareFacilityService() throws NamingException {
        HealthCareFacilityCorrelationServiceRemote object = (HealthCareFacilityCorrelationServiceRemote)
            lookup("po/HealthCareFacilityCorrelationServiceBean/remote");
        return object;
    }

    /**
     * {@inheritDoc}
     */
    public ClinicalResearchStaffCorrelationServiceRemote getClinicalResearchStaffService() throws NamingException {
        ClinicalResearchStaffCorrelationServiceRemote object = (ClinicalResearchStaffCorrelationServiceRemote)
            lookup("po/ClinicalResearchStaffCorrelationServiceBean/remote");
        return object;
    }

    /**
     * {@inheritDoc}
     */
    public HealthCareProviderCorrelationServiceRemote getHealthCareProviderService() throws NamingException {
        HealthCareProviderCorrelationServiceRemote object = (HealthCareProviderCorrelationServiceRemote)
            lookup("po/HealthCareProviderCorrelationServiceBean/remote");
        return object;
    }

    /**
     * {@inheritDoc}
     */
    public IdentifiedOrganizationCorrelationServiceRemote getIdentifiedOrganizationService() throws NamingException {
        IdentifiedOrganizationCorrelationServiceRemote object = (IdentifiedOrganizationCorrelationServiceRemote)
            lookup("po/IdentifiedOrganizationCorrelationServiceBean/remote");
        return object;
    }

    /**
     * {@inheritDoc}
     */
    public IdentifiedPersonCorrelationServiceRemote getIdentifiedPersonService() throws NamingException {
        IdentifiedPersonCorrelationServiceRemote object = (IdentifiedPersonCorrelationServiceRemote)
            lookup("po/IdentifiedPersonCorrelationServiceBean/remote");
        return object;
    }

    /**
     * {@inheritDoc}
     */
    public ResearchOrganizationCorrelationServiceRemote getResearchOrganizationService() throws NamingException {
        ResearchOrganizationCorrelationServiceRemote object = (ResearchOrganizationCorrelationServiceRemote)
            lookup("po/ResearchOrganizationCorrelationServiceBean/remote");
        return object;
    }

    /**
     * {@inheritDoc}
     */
    public OversightCommitteeCorrelationServiceRemote getOversightCommitteeService() throws NamingException {
        OversightCommitteeCorrelationServiceRemote object = (OversightCommitteeCorrelationServiceRemote)
            lookup("po/OversightCommitteeCorrelationServiceBean/remote");
        return object;
    }

    /**
     * {@inheritDoc}
     */
    public OrganizationalContactCorrelationServiceRemote getOrganizationalContactService() throws NamingException {
        OrganizationalContactCorrelationServiceRemote object = (OrganizationalContactCorrelationServiceRemote)
            lookup("po/OrganizationalContactCorrelationServiceBean/remote");
        return object;
    }
    
    /**
     * {@inheritDoc}
     */
    public PatientCorrelationServiceRemote getPatientService() throws NamingException {
        PatientCorrelationServiceRemote object = (PatientCorrelationServiceRemote)
            lookup("po/PatientCorrelationServiceBean/remote");
        return object;
    }
    
    /**
     * {@inheritDoc}
     */
    public BusinessServiceRemote getBusinessService() throws NamingException {
        BusinessServiceRemote object = (BusinessServiceRemote)
            lookup("po/BusinessServiceBean/remote");
        return object;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public <Z extends PoDto> CorrelationService getService(Class<Z> type) throws NamingException {
        Method serviceMethod = values.get(type);
        CorrelationService service = null;
        try {
            service = (CorrelationService) serviceMethod.invoke(this);
        } catch (Exception e) {
            throw new InvokeCoppaServiceException("Unable to invoke method, " + serviceMethod.getName());
        }
        if (service == null) {
            throw new IllegalArgumentException("Unable to locate service for type, " + type);
        }
        return service;
    }
}
