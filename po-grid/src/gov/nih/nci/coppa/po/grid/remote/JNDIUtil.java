package gov.nih.nci.coppa.po.grid.remote;

import gov.nih.nci.services.correlation.ClinicalResearchStaffCorrelationServiceRemote;
import gov.nih.nci.services.correlation.HealthCareFacilityCorrelationServiceRemote;
import gov.nih.nci.services.correlation.IdentifiedOrganizationCorrelationServiceRemote;
import gov.nih.nci.services.organization.OrganizationEntityServiceRemote;
import gov.nih.nci.services.person.PersonEntityServiceRemote;

import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class JNDIUtil {
    private static final Logger logger = LogManager.getLogger(JNDIUtil.class);

    private static JNDIUtil instance = new JNDIUtil();
    private InitialContext context;

    private JNDIUtil() {
        try {
            Properties props = new Properties();
            props.load(JNDIUtil.class.getClassLoader().getResourceAsStream("jndi.properties"));
            context = new InitialContext(props);
        } catch (Exception e) {
            logger.error("Unable to load jndi properties.", e);
            throw new RuntimeException("Unable to load jndi properties.", e);
        }
    }

    public static JNDIUtil getInstance() {
        return instance;
    }

    public PersonEntityServiceRemote getPersonService() throws NamingException {
        PersonEntityServiceRemote object = (PersonEntityServiceRemote) context
                .lookup("po/PersonEntityServiceBean/remote");
        return object;
    }

    public OrganizationEntityServiceRemote getOrganizationService() throws NamingException {
        OrganizationEntityServiceRemote object = (OrganizationEntityServiceRemote) context
                .lookup("po/OrganizationEntityServiceBean/remote");
        return object;
    }
    public HealthCareFacilityCorrelationServiceRemote getHealthCareFacilityService() throws NamingException {
        HealthCareFacilityCorrelationServiceRemote object = (HealthCareFacilityCorrelationServiceRemote) context
                .lookup("po/HealthCareFacilityCorrelationServiceBean/remote");
        return object;
    }
    public ClinicalResearchStaffCorrelationServiceRemote getClinicalResearchStaffService() throws NamingException {
        ClinicalResearchStaffCorrelationServiceRemote object =(ClinicalResearchStaffCorrelationServiceRemote) context
                .lookup("po/ClinicalResearchStaffCorrelationServiceBean/remote");
        return object;
    }
    public IdentifiedOrganizationCorrelationServiceRemote getIdentifiedOrganizationService() throws NamingException {
    	IdentifiedOrganizationCorrelationServiceRemote object =(IdentifiedOrganizationCorrelationServiceRemote) context
    	        .lookup("po/IdentifiedOrganizationCorrelationServiceBean/remote");
    	return object;
    }
}
